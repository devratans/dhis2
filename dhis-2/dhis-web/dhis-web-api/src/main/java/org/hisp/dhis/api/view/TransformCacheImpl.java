package org.hisp.dhis.api.view;

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
 * * Neither the name of the <ORGANIZATION> nor the names of its contributors may
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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.amplecode.staxwax.transformer.LoggingErrorListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * 
 * @author bobj
 * @version created 02-Dec-2011
 */
@Component
public class TransformCacheImpl
    implements TransformCache
{
    static final String MODEL2HTML = "model2html.xsl";

    static final String HTMLXSLT_RESOURCE = "/templates/html/";

    static private TransformCache instance;

    private Templates htmlCachedTransform;

    private TransformCacheImpl()
        throws IOException, TransformerConfigurationException
    {
        ErrorListener errorListener = new LoggingErrorListener();

        TransformerFactory factory = TransformerFactory.newInstance();
        factory.setErrorListener( errorListener );

        Source model2html = new StreamSource( new ClassPathResource( HTMLXSLT_RESOURCE + MODEL2HTML ).getInputStream() );

        factory.setURIResolver( new ClassPathUriResolver( HTMLXSLT_RESOURCE ) );
        htmlCachedTransform = factory.newTemplates( model2html );
    }

    protected static TransformCache instance()
    {
        if ( instance == null )
        {
            try
            {
                instance = new TransformCacheImpl();
            }
            catch ( Exception ex )
            {
                Logger.getLogger( TransformCacheImpl.class.getName() ).log( Level.SEVERE, null, ex );
            }
        }
        return instance;
    }

    @Override
    public Transformer getHtmlTransformer()
        throws TransformerConfigurationException
    {
        return htmlCachedTransform.newTransformer();
    }
}
