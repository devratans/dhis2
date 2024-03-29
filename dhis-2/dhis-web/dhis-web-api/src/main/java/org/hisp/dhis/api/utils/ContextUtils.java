package org.hisp.dhis.api.utils;

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

import javassist.util.proxy.ProxyObject;
import org.hisp.dhis.common.IdentifiableObject;
import org.hisp.dhis.dxf2.metadata.ExchangeClasses;
import org.hisp.dhis.setting.SystemSettingManager;
import org.hisp.dhis.system.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import static org.apache.commons.lang.StringUtils.trimToNull;
import static org.hisp.dhis.setting.SystemSettingManager.KEY_CACHE_STRATEGY;

/**
 * @author Lars Helge Overland
 */
@Component
public class ContextUtils
{
    public static final String CONTENT_TYPE_PDF = "application/pdf";
    public static final String CONTENT_TYPE_ZIP = "application/zip";
    public static final String CONTENT_TYPE_GZIP = "application/gzip";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_HTML = "text/html";
    public static final String CONTENT_TYPE_TEXT = "text/plain";
    public static final String CONTENT_TYPE_XML = "application/xml";
    public static final String CONTENT_TYPE_CSV = "application/csv";
    public static final String CONTENT_TYPE_PNG = "image/png";
    public static final String CONTENT_TYPE_JPG = "image/jpeg";
    public static final String CONTENT_TYPE_EXCEL = "application/vnd.ms-excel";
    public static final String CONTENT_TYPE_JAVASCRIPT = "application/javascript";

    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_EXPIRES = "Expires";
    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String HEADER_CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
    public static final String HEADER_LOCATION = "Location";
    
    @Autowired
    private SystemSettingManager systemSettingManager;

    public enum CacheStrategy
    {
        NO_CACHE,
        CACHE_1_HOUR,
        CACHE_6AM_TOMORROW,
        CACHE_TWO_WEEKS,
        RESPECT_SYSTEM_SETTING
    }

    public void configureResponse( HttpServletResponse response, String contentType, CacheStrategy cacheStrategy )
    {
        configureResponse( response, contentType, cacheStrategy, null, false );
    }
    
    public void configureResponse( HttpServletResponse response, String contentType, CacheStrategy cacheStrategy,
                                   String filename, boolean attachment )
    {
        if ( contentType != null )
        {
            response.setContentType( contentType );
        }

        if ( cacheStrategy.equals( CacheStrategy.RESPECT_SYSTEM_SETTING ) )
        {
            String strategy = trimToNull( (String) systemSettingManager.getSystemSetting( KEY_CACHE_STRATEGY ) );

            cacheStrategy = strategy != null ? CacheStrategy.valueOf( strategy ) : CacheStrategy.NO_CACHE;
        }

        if ( cacheStrategy == null || cacheStrategy.equals( CacheStrategy.NO_CACHE ) )
        {
            // -----------------------------------------------------------------
            // Cache set to expire after 1 second as IE 8 will not save cached
            // responses to disk over SSL, was "no-cache".
            // -----------------------------------------------------------------

            response.setHeader( HEADER_CACHE_CONTROL, "max-age=1" );
            response.setHeader( HEADER_EXPIRES, DateUtils.getExpiredHttpDateString() );
        }
        else if ( cacheStrategy.equals( CacheStrategy.CACHE_1_HOUR ) )
        {
            Calendar cal = Calendar.getInstance();
            cal.add( Calendar.HOUR_OF_DAY, 1 );
            
            response.setHeader( HEADER_CACHE_CONTROL, "public, max-age=3600" );
            response.setHeader( HEADER_EXPIRES, DateUtils.getHttpDateString( cal.getTime() ) );            
        }
        else if ( cacheStrategy.equals( CacheStrategy.CACHE_6AM_TOMORROW ) )
        {
            response.setHeader( HEADER_CACHE_CONTROL, "public, max-age=" + DateUtils.getSecondsUntilTomorrow( 6 ) );
            response.setHeader( HEADER_EXPIRES, DateUtils.getHttpDateString( DateUtils.getDateForTomorrow( 6 ) ) );
        }
        else if ( cacheStrategy.equals( CacheStrategy.CACHE_TWO_WEEKS ) )
        {
            Calendar cal = Calendar.getInstance();
            cal.add( Calendar.DAY_OF_YEAR, 14 );

            response.setHeader( HEADER_CACHE_CONTROL, "public, max-age=1209600" );
            response.setHeader( HEADER_EXPIRES, DateUtils.getHttpDateString( cal.getTime() ) );
        }

        if ( filename != null )
        {
            String type = attachment ? "attachment" : "inline";

            response.setHeader( HEADER_CONTENT_DISPOSITION, type + "; filename=\"" + filename + "\"" );
        }
    }

    public static void okResponse( HttpServletResponse response, String message )
    {
        setResponse( response, HttpServletResponse.SC_OK, message );
    }
    
    public static void createdResponse( HttpServletResponse response, String message, String location )
    {
        if ( location != null )
        {
            response.addHeader( HEADER_LOCATION, location );
        }
        
        setResponse( response, HttpServletResponse.SC_CREATED, message );
    }
    
    public static void notFoundResponse( HttpServletResponse response, String message )
    {
        setResponse( response, HttpServletResponse.SC_NOT_FOUND, message );
    }

    public static void conflictResponse( HttpServletResponse response, String message )
    {
        setResponse( response, HttpServletResponse.SC_CONFLICT, message );
    }

    public static void errorResponse( HttpServletResponse response, String message )
    {
        setResponse( response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message );
    }
    
    private static void setResponse( HttpServletResponse response, int statusCode, String message )
    {
        response.setStatus( statusCode );
        response.setContentType( CONTENT_TYPE_TEXT );

        try
        {
            PrintWriter writer = response.getWriter();
            writer.println( message );
            writer.flush();
        } 
        catch ( IOException ex )
        {
            // Ignore
        }
    }

    public static HttpServletRequest getRequest()
    {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String getPathWithUid( IdentifiableObject identifiableObject )
    {
        return getPath( identifiableObject.getClass() ) + "/" + identifiableObject.getUid();
    }

    public static String getPath( Class<?> clazz )
    {
        if ( ProxyObject.class.isAssignableFrom( clazz ) )
        {
            clazz = clazz.getSuperclass();
        }

        String resourcePath = ExchangeClasses.getExportMap().get( clazz );

        return getRootPath( getRequest() ) + "/" + resourcePath;
    }

    public static String getRootPath( HttpServletRequest request )
    {
        StringBuilder builder = new StringBuilder();
        String xForwardedProto = request.getHeader( "X-Forwarded-Proto" );
        String xForwardedPort = request.getHeader( "X-Forwarded-Port" );

        if ( xForwardedProto != null && (xForwardedProto.equalsIgnoreCase( "http" ) || xForwardedProto.equalsIgnoreCase( "https" )) )
        {
            builder.append( xForwardedProto );
        }
        else
        {
            builder.append( request.getScheme() );
        }

        builder.append( "://" ).append( request.getServerName() );

        int port;

        try
        {
            port = Integer.parseInt( xForwardedPort );
        } 
        catch ( NumberFormatException e )
        {
            port = request.getServerPort();
        }

        if ( port != 80 && port != 443 )
        {
            builder.append( ":" ).append( port );
        }

        builder.append( request.getContextPath() );
        builder.append( request.getServletPath() );

        return builder.toString();
    }
}
