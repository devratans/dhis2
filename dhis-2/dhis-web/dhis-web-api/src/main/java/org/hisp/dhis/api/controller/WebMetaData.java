package org.hisp.dhis.api.controller;

/*
 * Copyright (c) 2012, University of Oslo
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
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.hisp.dhis.common.Dxf2Namespace;
import org.hisp.dhis.common.LinkableObject;
import org.hisp.dhis.common.Pager;
import org.hisp.dhis.dxf2.metadata.MetaData;

/**
 * @author Morten Olav Hansen <mortenoh@gmail.com>
 */
public class WebMetaData
    extends MetaData
{
    private Pager pager;

    private LinkableObject linkableObject;

    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public Pager getPager()
    {
        return pager;
    }

    public void setPager( Pager pager )
    {
        this.pager = pager;
    }

    @JsonProperty
    @JacksonXmlProperty( isAttribute = true, namespace = Dxf2Namespace.NAMESPACE )
    public String getLink()
    {
        if ( linkableObject == null )
        {
            return null;
        }

        return linkableObject.getHref();
    }

    public void setLink( String link )
    {
        if ( linkableObject != null )
        {
            linkableObject.setHref( link );
        }
    }
}
