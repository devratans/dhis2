package org.hisp.dhis.integration.components;

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

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hisp.dhis.dxf2.metadata.ImportSummary;
import org.hisp.dhis.dxf2.metadata.MetaData;
import org.hisp.dhis.dxf2.utils.JacksonUtils;

import java.io.InputStream;

/**
 * @author bobj
 */
public class Dxf2MetaDataProducer
    extends DefaultProducer
{
    private static final Log log = LogFactory.getLog( Dxf2MetaDataProducer.class );

    public Dxf2MetaDataProducer( Dxf2MetaDataEndpoint endpoint )
    {
        super( endpoint );
    }

    @Override
    public void process( Exchange exchange ) throws Exception
    {
        log.info( this.getEndpoint().getEndpointUri() + " : " + exchange.getIn().getBody() );

        Dxf2MetaDataEndpoint endpoint = (Dxf2MetaDataEndpoint) this.getEndpoint();
        MetaData metadata = JacksonUtils.fromXml( (InputStream) exchange.getIn().getBody(), MetaData.class );

        ImportSummary summary = endpoint.getImportService().importMetaData( metadata, endpoint.getImportOptions() );
        exchange.getOut().setBody( JacksonUtils.toXmlAsString( summary ) );
    }
}
