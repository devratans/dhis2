package org.hisp.dhis.attribute;

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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.hisp.dhis.common.BaseIdentifiableObject;
import org.hisp.dhis.common.Dxf2Namespace;
import org.hisp.dhis.common.IdentifiableObject;
import org.hisp.dhis.common.view.DetailedView;
import org.hisp.dhis.common.view.ExportView;

import java.util.HashSet;
import java.util.Set;

/**
 * @author mortenoh
 */
@JacksonXmlRootElement( localName = "attributeType", namespace = Dxf2Namespace.NAMESPACE )
public class Attribute
    extends BaseIdentifiableObject
{
    /**
     * Determines if a de-serialized file is compatible with this class.
     */
    private static final long serialVersionUID = 9085246849415991424L;

    private String valueType;

    private boolean dataElementAttribute;

    private Boolean dataElementGroupAttribute = false;

    private boolean indicatorAttribute;

    private Boolean indicatorGroupAttribute = false;

    private boolean organisationUnitAttribute;

    private Boolean organisationUnitGroupAttribute = false;

    private boolean userAttribute;

    private Boolean userGroupAttribute = false;

    private boolean mandatory;

    private Integer sortOrder;

    private Set<AttributeValue> attributeValues = new HashSet<AttributeValue>();

    public Attribute()
    {

    }

    public Attribute( String name, String valueType )
    {
        this.name = name;
        this.valueType = valueType;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        if ( !super.equals( o ) ) return false;

        Attribute attribute = (Attribute) o;

        if ( dataElementAttribute != attribute.dataElementAttribute ) return false;
        if ( indicatorAttribute != attribute.indicatorAttribute ) return false;
        if ( mandatory != attribute.mandatory ) return false;
        if ( organisationUnitAttribute != attribute.organisationUnitAttribute ) return false;
        if ( userAttribute != attribute.userAttribute ) return false;
        if ( dataElementGroupAttribute != null ? !dataElementGroupAttribute.equals( attribute.dataElementGroupAttribute ) : attribute.dataElementGroupAttribute != null )
            return false;
        if ( indicatorGroupAttribute != null ? !indicatorGroupAttribute.equals( attribute.indicatorGroupAttribute ) : attribute.indicatorGroupAttribute != null )
            return false;
        if ( organisationUnitGroupAttribute != null ? !organisationUnitGroupAttribute.equals( attribute.organisationUnitGroupAttribute ) : attribute.organisationUnitGroupAttribute != null )
            return false;
        if ( sortOrder != null ? !sortOrder.equals( attribute.sortOrder ) : attribute.sortOrder != null ) return false;
        if ( userGroupAttribute != null ? !userGroupAttribute.equals( attribute.userGroupAttribute ) : attribute.userGroupAttribute != null )
            return false;
        if ( valueType != null ? !valueType.equals( attribute.valueType ) : attribute.valueType != null ) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (valueType != null ? valueType.hashCode() : 0);
        result = 31 * result + (dataElementAttribute ? 1 : 0);
        result = 31 * result + (dataElementGroupAttribute != null ? dataElementGroupAttribute.hashCode() : 0);
        result = 31 * result + (indicatorAttribute ? 1 : 0);
        result = 31 * result + (indicatorGroupAttribute != null ? indicatorGroupAttribute.hashCode() : 0);
        result = 31 * result + (organisationUnitAttribute ? 1 : 0);
        result = 31 * result + (organisationUnitGroupAttribute != null ? organisationUnitGroupAttribute.hashCode() : 0);
        result = 31 * result + (userAttribute ? 1 : 0);
        result = 31 * result + (userGroupAttribute != null ? userGroupAttribute.hashCode() : 0);
        result = 31 * result + (mandatory ? 1 : 0);
        result = 31 * result + (sortOrder != null ? sortOrder.hashCode() : 0);

        return result;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public String getValueType()
    {
        return valueType;
    }

    public void setValueType( String valueType )
    {
        this.valueType = valueType;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isMandatory()
    {
        return mandatory;
    }

    public void setMandatory( boolean mandatory )
    {
        this.mandatory = mandatory;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isDataElementAttribute()
    {
        return dataElementAttribute;
    }

    public void setDataElementAttribute( boolean dataElementAttribute )
    {
        this.dataElementAttribute = dataElementAttribute;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isDataElementGroupAttribute()
    {
        return dataElementGroupAttribute == null ? false : dataElementGroupAttribute;
    }

    public void setDataElementGroupAttribute( Boolean dataElementGroupAttribute )
    {
        this.dataElementGroupAttribute = dataElementGroupAttribute;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isIndicatorAttribute()
    {
        return indicatorAttribute;
    }

    public void setIndicatorAttribute( boolean indicatorAttribute )
    {
        this.indicatorAttribute = indicatorAttribute;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isIndicatorGroupAttribute()
    {
        return indicatorGroupAttribute == null ? false : indicatorGroupAttribute;
    }

    public void setIndicatorGroupAttribute( Boolean indicatorGroupAttribute )
    {
        this.indicatorGroupAttribute = indicatorGroupAttribute;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isOrganisationUnitAttribute()
    {
        return organisationUnitAttribute;
    }

    public void setOrganisationUnitAttribute( boolean organisationUnitAttribute )
    {
        this.organisationUnitAttribute = organisationUnitAttribute;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isOrganisationUnitGroupAttribute()
    {
        return organisationUnitGroupAttribute == null ? false : organisationUnitGroupAttribute;
    }

    public void setOrganisationUnitGroupAttribute( Boolean organisationUnitGroupAttribute )
    {
        this.organisationUnitGroupAttribute = organisationUnitGroupAttribute;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isUserAttribute()
    {
        return userAttribute;
    }

    public void setUserAttribute( boolean userAttribute )
    {
        this.userAttribute = userAttribute;
    }

    @JsonProperty
    @JsonView( { DetailedView.class, ExportView.class } )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isUserGroupAttribute()
    {
        return userGroupAttribute == null ? false : userGroupAttribute;
    }

    public void setUserGroupAttribute( Boolean userGroupAttribute )
    {
        this.userGroupAttribute = userGroupAttribute;
    }

    public Set<AttributeValue> getAttributeValues()
    {
        return attributeValues;
    }

    public void setAttributeValues( Set<AttributeValue> attributeValues )
    {
        this.attributeValues = attributeValues;
    }

    public Integer getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder( Integer sortOrder )
    {
        this.sortOrder = sortOrder;
    }

    @Override
    public void mergeWith( IdentifiableObject other )
    {
        super.mergeWith( other );

        if ( other.getClass().isInstance( this ) )
        {
            Attribute attribute = (Attribute) other;

            valueType = attribute.getValueType() == null ? valueType : attribute.getValueType();
            dataElementAttribute = attribute.isDataElementAttribute();
            indicatorAttribute = attribute.isIndicatorAttribute();
            organisationUnitAttribute = attribute.isOrganisationUnitAttribute();
            userAttribute = attribute.isUserAttribute();
            mandatory = attribute.isMandatory();
            sortOrder = attribute.getSortOrder() == null ? sortOrder : attribute.getSortOrder();

            attributeValues.clear();
            attributeValues.addAll( attribute.getAttributeValues() );
        }
    }
}
