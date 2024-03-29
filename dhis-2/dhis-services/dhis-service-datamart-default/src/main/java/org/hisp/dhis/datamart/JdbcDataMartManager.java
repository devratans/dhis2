package org.hisp.dhis.datamart;

/*
 * Copyright (c) 2004-2012, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * Neither the name of the HISP project nor the names of its contributors may
 *   be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import static org.hisp.dhis.system.util.TextUtils.getCommaDelimitedString;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.amplecode.quick.StatementHolder;
import org.amplecode.quick.StatementManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hisp.dhis.dataelement.DataElementOperand;
import org.hisp.dhis.jdbc.StatementBuilder;

/**
 * @author Lars Helge Overland
 */
public class JdbcDataMartManager
    implements DataMartManager
{
    private static final Log log = LogFactory.getLog( JdbcDataMartManager.class );
    
    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

    private StatementManager statementManager;
    
    public void setStatementManager( StatementManager statementManager )
    {
        this.statementManager = statementManager;
    }
    
    private StatementBuilder statementBuilder;

    public void setStatementBuilder( StatementBuilder statementBuilder )
    {
        this.statementBuilder = statementBuilder;
    }

    // -------------------------------------------------------------------------
    // DataMartManager implementation
    // -------------------------------------------------------------------------

    @Override
    public Set<DataElementOperand> getOperandsWithData( Set<DataElementOperand> operands )
    {
        final Set<DataElementOperand> operandsWithData = new HashSet<DataElementOperand>();
        
        final StatementHolder holder = statementManager.getHolder();
        
        for ( DataElementOperand operand : operands )
        {
            final String sql = 
                "SELECT COUNT(*) FROM datavalue " + 
                "WHERE dataelementid=" + operand.getDataElementId() + " " +
                "AND categoryoptioncomboid=" + operand.getOptionComboId();
            
            Integer count = holder.queryForInteger( sql );
            
            if ( count != null && count > 0 )
            {
                operandsWithData.add( operand );
            }
        }
        
        return operandsWithData;
    }

    @Override
    public Map<Integer, String> getDataValueMap( DataElementOperand operand, int periodId )
    {
        final StatementHolder holder = statementManager.getHolder();
            
        try
        {
            final String sql =
                "SELECT sourceid, value " +
                "FROM datavalue " +
                "WHERE dataelementid = " + operand.getDataElementId() + " " +
                "AND categoryoptioncomboid = " + operand.getOptionComboId() + " " +
                "AND periodid = " + periodId;
            
            final ResultSet resultSet = holder.getStatement().executeQuery( sql );
            
            final Map<Integer, String> map = new HashMap<Integer, String>();
            
            while ( resultSet.next() )
            {
                map.put( resultSet.getInt( 1 ), resultSet.getString( 2 ) );
            }
            
            return map;
        }
        catch ( SQLException ex )
        {
            throw new RuntimeException( "Failed to get DataValues", ex );
        }
        finally
        {
            holder.close();
        }
    }

    // -------------------------------------------------------------------------
    // AggregatedDataValue
    // -------------------------------------------------------------------------

    @Override
    public Map<DataElementOperand, String> getAggregatedValueMap( int periodId, int organisationUnitId )
    {
        final StatementHolder holder = statementManager.getHolder();
            
        try
        {
            final String sql =
                "SELECT dataelementid, categoryoptioncomboid, value " +
                "FROM aggregateddatavalue " +
                "WHERE periodid = " + periodId + " " +
                "AND organisationunitid = " + organisationUnitId;
            
            final ResultSet resultSet = holder.getStatement().executeQuery( sql );
            
            final Map<DataElementOperand, String> map = new HashMap<DataElementOperand, String>();
            
            while ( resultSet.next() )
            {
                final DataElementOperand operand = new DataElementOperand( resultSet.getInt( 1 ), resultSet.getInt( 2 ) );
                
                map.put( operand, resultSet.getString( 3 ) );
            }
            
            return map;
        }
        catch ( SQLException ex )
        {
            throw new RuntimeException( "Failed to get AggregatedDataValues", ex );
        }
        finally
        {
            holder.close();
        }
    }

    @Override
    public void createDataValueIndex()
    {
        executeSilently( "CREATE INDEX aggregateddatavalue_index ON aggregateddatavalue (dataelementid, periodid, organisationunitid, categoryoptioncomboid, value)" );
        executeSilently( "CREATE INDEX aggregateddatavalue_period_index ON aggregateddatavalue (periodid, organisationunitid)" );
    }

    @Override
    public void createIndicatorValueIndex()
    {
        executeSilently( "CREATE INDEX aggregatedindicatorvalue_index ON aggregatedindicatorvalue (indicatorid, periodid, organisationunitid, value)" );
    }

    @Override
    public void dropDataValueIndex()
    {
        executeSilently( "DROP INDEX aggregateddatavalue_index" );
        executeSilently( "DROP INDEX aggregateddatavalue_period_index" );
    }

    @Override
    public void dropIndicatorValueIndex()
    {
        executeSilently( "DROP INDEX aggregatedindicatorvalue_index" );
    }

    @Override
    public void deleteAggregatedDataValues( Collection<Integer> periodIds )
    {
        final String sql =
            "DELETE FROM aggregateddatavalue " +
            "WHERE periodid IN ( " + getCommaDelimitedString( periodIds ) + " )";
        
        statementManager.getHolder().executeUpdate( sql );
    }

    @Override
    public void deleteAggregatedIndicatorValues( Collection<Integer> periodIds )
    {
        final String sql =
            "DELETE FROM aggregatedindicatorvalue " +
            "WHERE periodid IN ( " + getCommaDelimitedString( periodIds ) + " )";

        statementManager.getHolder().executeUpdate( sql );
    }

    // -------------------------------------------------------------------------
    // AggregatedOrgUnitDataValue
    // -------------------------------------------------------------------------

    @Override
    public Map<DataElementOperand, String> getAggregatedOrgUnitValueMap( int periodId, int organisationUnitId, int organisationUnitGroupId )
    {
        final StatementHolder holder = statementManager.getHolder();
            
        try
        {
            final String sql =
                "SELECT dataelementid, categoryoptioncomboid, value " +
                "FROM aggregatedorgunitdatavalue " +
                "WHERE periodid = " + periodId + " " +
                "AND organisationunitid = " + organisationUnitId + " " +
                "AND organisationunitgroupid = " + organisationUnitGroupId;
            
            final ResultSet resultSet = holder.getStatement().executeQuery( sql );
            
            final Map<DataElementOperand, String> map = new HashMap<DataElementOperand, String>();
            
            while ( resultSet.next() )
            {
                final DataElementOperand operand = new DataElementOperand( resultSet.getInt( 1 ), resultSet.getInt( 2 ) );
                
                map.put( operand, resultSet.getString( 3 ) );
            }
            
            return map;
        }
        catch ( SQLException ex )
        {
            throw new RuntimeException( "Failed to get AggregatedOrgUnitDataValues", ex );
        }
        finally
        {
            holder.close();
        }
    }
    
    @Override
    public void createOrgUnitDataValueIndex()
    {
        executeSilently( "CREATE INDEX aggregatedorgunitdatavalue_index ON aggregatedorgunitdatavalue (dataelementid, periodid, organisationunitid, organisationunitgroupid, categoryoptioncomboid, value)" );
        executeSilently( "CREATE INDEX aggregatedorgunitdatavalue_period_index ON aggregatedorgunitdatavalue (periodid, organisationunitid, organisationunitgroupid)" );
    }

    @Override
    public void createOrgUnitIndicatorValueIndex()
    {
        executeSilently( "CREATE INDEX aggregatedorgunitindicatorvalue_index ON aggregatedorgunitindicatorvalue (indicatorid, periodid, organisationunitid, organisationunitgroupid, value)" );        
    }

    @Override
    public void dropOrgUnitDataValueIndex()
    {
        executeSilently( "DROP INDEX aggregatedorgunitdatavalue_index" );
        executeSilently( "DROP INDEX aggregatedorgunitdatavalue_period_index" );
    }

    @Override
    public void dropOrgUnitIndicatorValueIndex()
    {
        executeSilently( "DROP INDEX aggregatedorgunitindicatorvalue_index" );
    }

    @Override
    public void deleteAggregatedOrgUnitDataValues( Collection<Integer> periodIds )
    {
        final String sql =
            "DELETE FROM aggregatedorgunitdatavalue " +
            "WHERE periodid IN ( " + getCommaDelimitedString( periodIds ) + " )";
        
        statementManager.getHolder().executeUpdate( sql );
    }

    @Override
    public void deleteAggregatedOrgUnitIndicatorValues( Collection<Integer> periodIds )
    {
        final String sql =
            "DELETE FROM aggregatedorgunitindicatorvalue " +
            "WHERE periodid IN ( " + getCommaDelimitedString( periodIds ) + " )";

        statementManager.getHolder().executeUpdate( sql );
    }

    // -------------------------------------------------------------------------
    // Data mart tables
    // -------------------------------------------------------------------------

    public void createTempAggregatedTables()
    {
        executeSilently( statementBuilder.getCreateAggregatedDataValueTable( true ) );
        executeSilently( statementBuilder.getCreateAggregatedIndicatorTable( true ) );
        executeSilently( statementBuilder.getCreateAggregatedOrgUnitDataValueTable( true ) );
        executeSilently( statementBuilder.getCreateAggregatedOrgUnitIndicatorTable( true ) );
    }
    
    public void dropTempAggregatedTables()
    {
        executeSilently( "drop table aggregateddatavalue_temp" );
        executeSilently( "drop table aggregatedindicatorvalue_temp" );
        executeSilently( "drop table aggregatedorgunitdatavalue_temp" );
        executeSilently( "drop table aggregatedorgunitindicatorvalue_temp" );
    }
    
    public void copyAggregatedDataValuesFromTemp()
    {
        executeSilently( "insert into aggregateddatavalue select * from aggregateddatavalue_temp" );
        
        executeSilently( statementBuilder.getVacuum( "aggregateddatavalue" ) );
    }

    public void copyAggregatedIndicatorValuesFromTemp()
    {
        executeSilently( "insert into aggregatedindicatorvalue select * from aggregatedindicatorvalue_temp" );
        
        executeSilently( statementBuilder.getVacuum( "aggregatedindicatorvalue" ) );
    }

    public void copyAggregatedOrgUnitDataValuesFromTemp()
    {
        executeSilently( "insert into aggregatedorgunitdatavalue select * from aggregatedorgunitdatavalue_temp" );
        
        executeSilently( statementBuilder.getVacuum( "aggregatedorgunitdatavalue" ) );
    }

    public void copyAggregatedOrgUnitIndicatorValuesFromTemp()
    {
        executeSilently( "insert into aggregatedorgunitindicatorvalue select * from aggregatedorgunitindicatorvalue_temp" );
        
        executeSilently( statementBuilder.getVacuum( "aggregatedorgunitindicatorvalue" ) );
    }

    private void executeSilently( String sql )
    {
        try
        {
            if ( sql != null )
            {
                statementManager.getHolder().executeUpdate( sql, true );
            }
        }
        catch ( Exception ex )
        {
            log.debug( ex.getMessage() );
        }
    }
}
