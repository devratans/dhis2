package org.hisp.dhis.sms.config;

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

import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hisp.dhis.common.adapter.ParametersMapXmlAdapter;

public class GenericHttpGatewayConfig
    extends SmsGatewayConfig
{
    private static final long serialVersionUID = 6340853488475760213L;

    private String urlTemplate;

    Map<String, String> parameters;

    public GenericHttpGatewayConfig()
    {
    }

    public GenericHttpGatewayConfig( String urlTemplate, Map<String, String> parameters )
    {
        this.urlTemplate = urlTemplate;
        this.parameters = parameters;
    }

    public String getUrlTemplate()
    {
        return urlTemplate;
    }

    public void setUrlTemplate( String urlTemplate )
    {
        this.urlTemplate = urlTemplate;
    }

    @XmlJavaTypeAdapter( ParametersMapXmlAdapter.class )
    public Map<String, String> getParameters()
    {
        return parameters;
    }

    public void setParameters( Map<String, String> parameters )
    {
        this.parameters = parameters;
    }

    @Override
    public boolean isInbound()
    {
        return false;
    }

    @Override
    public boolean isOutbound()
    {
        return true;
    }

}
