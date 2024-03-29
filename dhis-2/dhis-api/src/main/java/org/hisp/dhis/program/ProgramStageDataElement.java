/*
 * Copyright (c) 2004-2009, University of Oslo
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
package org.hisp.dhis.program;

import java.io.Serializable;

import org.hisp.dhis.dataelement.DataElement;

/**
 * @author Viet Nguyen
 * 
 * @version $Id$
 */
public class ProgramStageDataElement
    implements Serializable
{
    /**
     * Determines if a de-serialized file is compatible with this class.
     */
    private static final long serialVersionUID = -5670110591005778814L;

    /**
     * Part of composite key
     */
    private ProgramStage programStage;

    /**
     * Part of composite key
     */
    private DataElement dataElement;

    /*
     * True if this dataElement is mandatory in the dataEntryForm for this
     * programStage
     */
    private boolean compulsory = false;

    private Boolean allowProvidedElsewhere;

    private Integer sortOrder;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public ProgramStageDataElement( ProgramStage programStage, DataElement dataElement, boolean compulsory )
    {
        this.programStage = programStage;
        this.dataElement = dataElement;
        this.compulsory = compulsory;
    }

    public ProgramStageDataElement( ProgramStage programStage, DataElement dataElement, boolean compulsory,
        Integer sortOrder )
    {
        this.programStage = programStage;
        this.dataElement = dataElement;
        this.compulsory = compulsory;
        this.sortOrder = sortOrder;
    }

    public ProgramStageDataElement()
    {
    }

    public ProgramStage getProgramStage()
    {
        return programStage;
    }

    public void setProgramStage( ProgramStage programStage )
    {
        this.programStage = programStage;
    }

    public DataElement getDataElement()
    {
        return dataElement;
    }

    public void setDataElement( DataElement dataElement )
    {
        this.dataElement = dataElement;
    }

    public Boolean getAllowProvidedElsewhere()
    {
        return allowProvidedElsewhere;
    }

    public void setAllowProvidedElsewhere( Boolean allowProvidedElsewhere )
    {
        this.allowProvidedElsewhere = allowProvidedElsewhere;
    }

    public boolean isCompulsory()
    {
        return compulsory;
    }

    public void setCompulsory( boolean compulsory )
    {
        this.compulsory = compulsory;
    }

    public Integer getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder( Integer sortOrder )
    {
        this.sortOrder = sortOrder;
    }

    // -------------------------------------------------------------------------
    // hashCode, equals and toString
    // -------------------------------------------------------------------------

    @Override
    public int hashCode()
    {
        return programStage.hashCode() + dataElement.hashCode();
    }

    @Override
    public boolean equals( Object object )
    {

        if ( object == null )
        {
            return false;
        }

        if ( getClass() != object.getClass() )
        {
            return false;
        }

        final ProgramStageDataElement other = (ProgramStageDataElement) object;

        return dataElement.getId() == other.getDataElement().getId()
            && programStage.getId() == other.getProgramStage().getId();
    }

}
