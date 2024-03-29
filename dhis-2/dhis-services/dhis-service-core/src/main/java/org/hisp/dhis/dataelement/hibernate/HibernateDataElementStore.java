package org.hisp.dhis.dataelement.hibernate;

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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hisp.dhis.common.hibernate.HibernateIdentifiableObjectStore;
import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.dataelement.DataElementCategoryCombo;
import org.hisp.dhis.dataelement.DataElementStore;
import org.hisp.dhis.dataset.DataSet;
import org.hisp.dhis.system.util.ConversionUtils;
import org.springframework.jdbc.core.RowCallbackHandler;

/**
 * @author Torgeir Lorange Ostby
 * @version $Id: HibernateDataElementStore.java 5243 2008-05-25 10:18:58Z
 *          larshelg $
 */
public class HibernateDataElementStore
    extends HibernateIdentifiableObjectStore<DataElement>
    implements DataElementStore
{
    // -------------------------------------------------------------------------
    // DataElement
    // -------------------------------------------------------------------------

    @SuppressWarnings( "unchecked" )
    public Collection<DataElement> searchDataElementsByName( String key )
    {
        Session session = sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria( DataElement.class );
        criteria.add( Restrictions.ilike( "name", "%" + key + "%" ) );

        return criteria.list();
    }

    @SuppressWarnings( "unchecked" )
    public Collection<DataElement> getAggregateableDataElements()
    {
        Session session = sessionFactory.getCurrentSession();

        Set<String> types = new HashSet<String>();

        types.add( DataElement.VALUE_TYPE_INT );
        types.add( DataElement.VALUE_TYPE_BOOL );

        Criteria criteria = session.createCriteria( DataElement.class );

        criteria.add( Restrictions.in( "type", types ) );

        return criteria.list();
    }

    @SuppressWarnings( "unchecked" )
    public Collection<DataElement> getAllActiveDataElements()
    {
        Session session = sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria( DataElement.class );
        criteria.add( Restrictions.eq( "active", true ) );

        return criteria.list();
    }

    @SuppressWarnings( "unchecked" )
    public Collection<DataElement> getDataElementsByAggregationOperator( String aggregationOperator )
    {
        Session session = sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria( DataElement.class );
        criteria.add( Restrictions.eq( "aggregationOperator", aggregationOperator ) );

        return criteria.list();
    }

    @SuppressWarnings( "unchecked" )
    public Collection<DataElement> getDataElementsByType( String type )
    {
        Session session = sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria( DataElement.class );
        criteria.add( Restrictions.eq( "type", type ) );

        return criteria.list();
    }

    @SuppressWarnings( "unchecked" )
    public Collection<DataElement> getDataElementsByDomainType( String domainType )
    {
        Session session = sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria( DataElement.class );
        criteria.add( Restrictions.eq( "domainType", domainType ) );

        return criteria.list();
    }

    @SuppressWarnings( "unchecked" )
    public Collection<DataElement> getDataElementByCategoryCombo( DataElementCategoryCombo categoryCombo )
    {
        Session session = sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria( DataElement.class );
        criteria.add( Restrictions.eq( "categoryCombo", categoryCombo ) );

        return criteria.list();
    }

    @SuppressWarnings( "unchecked" )
    public Collection<DataElement> getDataElementsWithGroupSets()
    {
        String hql = "from DataElement d where d.groupSets.size > 0";

        return getQuery( hql ).setCacheable( true ).list();
    }

    public void setZeroIsSignificantForDataElements( Collection<Integer> dataElementIds )
    {
        Session session = sessionFactory.getCurrentSession();

        String sql = "update DataElement set zeroIsSignificant = false";

        Query query = session.createQuery( sql );

        query.executeUpdate();

        if ( !dataElementIds.isEmpty() )
        {
            sql = "update DataElement set zeroIsSignificant=true where id in (:dataElementIds)";

            query = session.createQuery( sql );
            query.setParameterList( "dataElementIds", dataElementIds );

            query.executeUpdate();
        }
    }

    @SuppressWarnings( "unchecked" )
    public Collection<DataElement> getDataElementsByZeroIsSignificant( boolean zeroIsSignificant )
    {
        Criteria criteria = getCriteria();
        criteria.add( Restrictions.eq( "zeroIsSignificant", zeroIsSignificant ) );
        criteria.add( Restrictions.eq( "type", DataElement.VALUE_TYPE_INT ) );
        criteria.setCacheable( true );

        return criteria.list();
    }

    @SuppressWarnings( "unchecked" )
    public Collection<DataElement> getDataElementsWithoutGroups()
    {
        String hql = "from DataElement d where d.groups.size = 0";

        return getQuery( hql ).setCacheable( true ).list();
    }

    @SuppressWarnings( "unchecked" )
    public Collection<DataElement> getDataElementsWithoutDataSets()
    {
        String hql = "from DataElement d where d.dataSets.size = 0 and d.domainType =:domainType";

        return getQuery( hql ).setParameter( "domainType", "aggregate" ).setCacheable( true ).list();
    }

    @SuppressWarnings( "unchecked" )
    public Collection<DataElement> getDataElementsWithDataSets()
    {
        String hql = "from DataElement d where d.dataSets.size > 0";

        return getQuery( hql ).setCacheable( true ).list();
    }

    @SuppressWarnings( "unchecked" )
    public Collection<DataElement> getDataElementsByDataSets( Collection<DataSet> dataSets )
    {
        String hql = "select distinct de from DataElement de join de.dataSets ds where ds.id in (:ids)";

        return sessionFactory.getCurrentSession().createQuery( hql ).setParameterList( "ids",
            ConversionUtils.getIdentifiers( DataSet.class, dataSets ) ).list();
    }

    public Map<Integer, Set<Integer>> getDataElementCategoryOptionCombos()
    {
        final String sql = "select de.dataelementid, coc.categoryoptioncomboid from dataelement de " +
            "join categorycombos_optioncombos coc on de.categorycomboid=coc.categorycomboid";
        
        final Map<Integer, Set<Integer>> sets = new HashMap<Integer, Set<Integer>>();
        
        jdbcTemplate.query( sql, new RowCallbackHandler()
        {
            @Override
            public void processRow( ResultSet rs )
                throws SQLException
            {
                int dataElementId = rs.getInt( 1 );
                int categoryOptionComboId = rs.getInt( 2 );
                
                Set<Integer> set = sets.get( dataElementId ) != null ? sets.get( dataElementId ) : new HashSet<Integer>();
                
                set.add( categoryOptionComboId );                
                sets.put( dataElementId, set );
            }
        } );
        
        return sets;
    }
    
    @SuppressWarnings( "unchecked" )
    public Collection<DataElement> get( DataSet dataSet, String key, Integer max )
    {
        String hql = "select dataElement from DataSet dataSet inner join dataSet.dataElements as dataElement where dataSet.id = :dataSetId ";
        if( key != null )
        {
            hql += " and lower(dataElement.name) like lower('%" + key + "%') ";
        }
        
        Query query = getQuery( hql );
        query.setInteger( "dataSetId", dataSet.getId() );
        query.setMaxResults( max );
        
        return query.list();
    }
}
