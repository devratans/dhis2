package org.hisp.dhis.jdbc.batchhandler;

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

import org.amplecode.quick.JdbcConfiguration;
import org.amplecode.quick.batchhandler.AbstractBatchHandler;
import org.hisp.dhis.dataset.DataSet;

/**
 * @author Lars Helge Overland
 * @version $Id: DataSetBatchHandler.java 5062 2008-05-01 18:10:35Z larshelg $
 */
public class DataSetBatchHandler
    extends AbstractBatchHandler<DataSet>
{
    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public DataSetBatchHandler( JdbcConfiguration config )
    {
        super( config, false, false );
    }

    // -------------------------------------------------------------------------
    // AbstractBatchHandler implementation
    // -------------------------------------------------------------------------

    protected void setTableName()
    {
        statementBuilder.setTableName( "dataset" );
    }

    @Override
    protected void setAutoIncrementColumn()
    {
        statementBuilder.setAutoIncrementColumn( "datasetid" );
    }

    @Override
    protected void setIdentifierColumns()
    {
        statementBuilder.setIdentifierColumn( "datasetid" );
    }

    @Override
    protected void setIdentifierValues( DataSet dataSet )
    {
        statementBuilder.setIdentifierValue( dataSet.getId() );
    }

    protected void setUniqueColumns()
    {
        statementBuilder.setUniqueColumn( "name" );
        statementBuilder.setUniqueColumn( "shortName" );
        statementBuilder.setUniqueColumn( "code" );
    }

    protected void setUniqueValues( DataSet dataSet )
    {
        statementBuilder.setUniqueValue( dataSet.getName() );
        statementBuilder.setUniqueValue( dataSet.getShortName() );
        statementBuilder.setUniqueValue( dataSet.getCode() );
    }

    protected void setColumns()
    {
        statementBuilder.setColumn( "uid" );
        statementBuilder.setColumn( "name" );
        statementBuilder.setColumn( "shortname" );
        statementBuilder.setColumn( "code" );
        statementBuilder.setColumn( "periodtypeid" );
        statementBuilder.setColumn( "sortorder" );
        statementBuilder.setColumn( "mobile" );
        statementBuilder.setColumn( "allowfutureperiods" );
        statementBuilder.setColumn( "dataentryform" );
        statementBuilder.setColumn( "expirydays" );
        statementBuilder.setColumn( "skipaggregation" );
        statementBuilder.setColumn( "fieldcombinationrequired" );
        statementBuilder.setColumn( "validcompleteonly" );
        statementBuilder.setColumn( "skipoffline" );
    }

    protected void setValues( DataSet dataSet )
    {
        statementBuilder.setValue( dataSet.getUid() );
        statementBuilder.setValue( dataSet.getName() );
        statementBuilder.setValue( dataSet.getShortName() );
        statementBuilder.setValue( dataSet.getCode() );
        statementBuilder.setValue( dataSet.getPeriodType().getId() );
        statementBuilder.setValue( dataSet.getSortOrder() );
        statementBuilder.setValue( dataSet.isMobile() );
        statementBuilder.setValue( dataSet.isAllowFuturePeriods() );
        statementBuilder.setValue( dataSet.getDataEntryForm() != null ? dataSet.getDataEntryForm().getId() : null );
        statementBuilder.setValue( dataSet.getExpiryDays() );       
        statementBuilder.setValue( dataSet.isSkipAggregation() );
        statementBuilder.setValue( dataSet.isFieldCombinationRequired() );
        statementBuilder.setValue( dataSet.isValidCompleteOnly() );
        statementBuilder.setValue( dataSet.isSkipOffline() );
    }
}
