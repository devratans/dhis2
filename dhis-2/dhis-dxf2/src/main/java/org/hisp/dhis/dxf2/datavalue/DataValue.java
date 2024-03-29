package org.hisp.dhis.dxf2.datavalue;

/*
 * Copyright (c) 2011, University of Oslo
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


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.hisp.dhis.common.Dxf2Namespace;
import org.hisp.dhis.common.view.DetailedView;
import org.hisp.dhis.common.view.ExportView;

@JacksonXmlRootElement( localName = "dataValue", namespace = Dxf2Namespace.NAMESPACE )
public class DataValue
{
    //--------------------------------------------------------------------------
    // Properties
    //--------------------------------------------------------------------------

    protected String dataElement;

    protected String period;

    protected String orgUnit;

    protected String categoryOptionCombo;

    protected String value;
    
    protected String storedBy;

    protected String timestamp;

    protected String comment;

    protected Boolean followup;

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    public DataValue()
    {
    }

    
    //--------------------------------------------------------------------------
    // Getters and setters
    //--------------------------------------------------------------------------

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public String getDataElement()
    {
        return dataElement;
    }

    public void setDataElement( String dataElement )
    {
        this.dataElement = dataElement;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public String getPeriod()
    {
        return period;
    }

    public void setPeriod( String period )
    {
        this.period = period;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public String getOrgUnit()
    {
        return orgUnit;
    }

    public void setOrgUnit( String orgUnit )
    {
        this.orgUnit = orgUnit;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public String getCategoryOptionCombo()
    {
        return categoryOptionCombo;
    }

    public void setCategoryOptionCombo( String categoryOptionCombo )
    {
        this.categoryOptionCombo = categoryOptionCombo;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public String getStoredBy()
    {
        return storedBy;
    }

    public void setStoredBy( String storedBy )
    {
        this.storedBy = storedBy;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp( String timestamp )
    {
        this.timestamp = timestamp;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public String getComment()
    {
        return comment;
    }

    public void setComment( String comment )
    {
        this.comment = comment;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public Boolean getFollowup()
    {
        return followup;
    }

    public void setFollowup( Boolean followup )
    {
        this.followup = followup;
    }

    public void close()
    {
    }
}
