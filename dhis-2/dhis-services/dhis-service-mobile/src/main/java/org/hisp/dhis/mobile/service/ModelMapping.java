package org.hisp.dhis.mobile.service;

/*
 * Copyright (c) 2010, University of Oslo
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

import java.util.ArrayList;
import java.util.List;

import org.hisp.dhis.api.mobile.model.DataElement;
import org.hisp.dhis.api.mobile.model.Model;
import org.hisp.dhis.api.mobile.model.ModelList;
import org.hisp.dhis.api.mobile.model.OptionSet;
import org.hisp.dhis.dataelement.DataElementCategoryCombo;
import org.hisp.dhis.dataelement.DataElementCategoryOptionCombo;

public class ModelMapping
{
    public DataElement getDataElement( org.hisp.dhis.dataelement.DataElement dataElement )
    {
        DataElement de = new DataElement();
        de.setId( dataElement.getId() );
        de.setName( dataElement.getFormNameFallback() );
        de.setType( dataElement.getType() );

        de.setCategoryOptionCombos( getCategoryOptionCombos( dataElement ) );
        de.setOptionSet( getOptionSet( dataElement ) );
        return de;
    }

    public OptionSet getOptionSet( org.hisp.dhis.dataelement.DataElement dataElement )
    {
        org.hisp.dhis.option.OptionSet dhisOptionSet = dataElement.getOptionSet();
        OptionSet mobileOptionSet = new OptionSet();
        if ( dhisOptionSet != null )
        {
            mobileOptionSet.setId( dhisOptionSet.getId() );
            mobileOptionSet.setName( dhisOptionSet.getName() );
            mobileOptionSet.setOptions( dhisOptionSet.getOptions() );
        }
        else
        {
            return null;
        }

        return mobileOptionSet;

    }

    public ModelList getCategoryOptionCombos( org.hisp.dhis.dataelement.DataElement dataElement )
    {
        DataElementCategoryCombo categoryCombo = dataElement.getCategoryCombo();

        // Client DataElement
        ModelList deCateOptCombo = new ModelList();
        List<Model> listCateOptCombo = new ArrayList<Model>();
        deCateOptCombo.setModels( listCateOptCombo );

        for ( DataElementCategoryOptionCombo oneCatOptCombo : categoryCombo.getSortedOptionCombos() )
        {
            Model oneCateOptCombo = new Model();
            oneCateOptCombo.setId( oneCatOptCombo.getId() );
            oneCateOptCombo.setName( oneCatOptCombo.getName() );
            listCateOptCombo.add( oneCateOptCombo );
        }
        return deCateOptCombo;
    }

}
