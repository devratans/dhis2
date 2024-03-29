package org.hisp.dhis.interceptor;

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

import static org.hisp.dhis.setting.SystemSettingManager.DEFAULT_COMPLETENESS_OFFSET;
import static org.hisp.dhis.setting.SystemSettingManager.DEFAULT_FACTOR_OF_DEVIATION;
import static org.hisp.dhis.setting.SystemSettingManager.DEFAULT_START_MODULE;
import static org.hisp.dhis.setting.SystemSettingManager.KEY_APPLICATION_TITLE;
import static org.hisp.dhis.setting.SystemSettingManager.KEY_APPLICATION_INTRO;
import static org.hisp.dhis.setting.SystemSettingManager.KEY_CACHE_STRATEGY;
import static org.hisp.dhis.setting.SystemSettingManager.KEY_COMPLETENESS_OFFSET;
import static org.hisp.dhis.setting.SystemSettingManager.KEY_FACTOR_OF_DEVIATION;
import static org.hisp.dhis.setting.SystemSettingManager.KEY_FLAG;
import static org.hisp.dhis.setting.SystemSettingManager.KEY_FLAG_IMAGE;
import static org.hisp.dhis.setting.SystemSettingManager.KEY_OMIT_INDICATORS_ZERO_NUMERATOR_DATAMART;
import static org.hisp.dhis.setting.SystemSettingManager.KEY_START_MODULE;
import static org.hisp.dhis.setting.SystemSettingManager.KEY_PHONE_NUMBER_AREA_CODE;
import static org.hisp.dhis.setting.SystemSettingManager.KEY_CONFIGURATION;

import java.util.HashMap;
import java.util.Map;

import org.hisp.dhis.configuration.ConfigurationService;
import org.hisp.dhis.setting.SystemSettingManager;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * @author Lars Helge Overland
 */
public class SystemSettingInterceptor
    implements Interceptor
{
    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

    private SystemSettingManager systemSettingManager;

    public void setSystemSettingManager( SystemSettingManager systemSettingManager )
    {
        this.systemSettingManager = systemSettingManager;
    }
    
    private ConfigurationService configurationService;

    public void setConfigurationService( ConfigurationService configurationService )
    {
        this.configurationService = configurationService;
    }

    // -------------------------------------------------------------------------
    // AroundInterceptor implementation
    // -------------------------------------------------------------------------

    public void destroy()
    {        
    }

    public void init()
    {
    }

    public String intercept( ActionInvocation invocation )
        throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put( KEY_CACHE_STRATEGY, systemSettingManager.getSystemSetting( KEY_CACHE_STRATEGY ) );
        map.put( KEY_APPLICATION_TITLE, systemSettingManager.getSystemSetting( KEY_APPLICATION_TITLE ) );
        map.put( KEY_APPLICATION_INTRO, systemSettingManager.getSystemSetting( KEY_APPLICATION_INTRO ) );
        map.put( KEY_FLAG, systemSettingManager.getSystemSetting( KEY_FLAG ) );
        map.put( KEY_FLAG_IMAGE, systemSettingManager.getFlagImage() );
        map.put( KEY_START_MODULE, systemSettingManager.getSystemSetting( KEY_START_MODULE, DEFAULT_START_MODULE ) );
        map.put( KEY_OMIT_INDICATORS_ZERO_NUMERATOR_DATAMART, systemSettingManager.getSystemSetting( KEY_OMIT_INDICATORS_ZERO_NUMERATOR_DATAMART, false ) );
        map.put( KEY_FACTOR_OF_DEVIATION, systemSettingManager.getSystemSetting( KEY_FACTOR_OF_DEVIATION, DEFAULT_FACTOR_OF_DEVIATION ) );
        map.put( KEY_COMPLETENESS_OFFSET, systemSettingManager.getSystemSetting( KEY_COMPLETENESS_OFFSET, DEFAULT_COMPLETENESS_OFFSET ) );
        map.put( KEY_PHONE_NUMBER_AREA_CODE, systemSettingManager.getSystemSetting( KEY_PHONE_NUMBER_AREA_CODE, "" ) );
        map.put( KEY_CONFIGURATION, configurationService.getConfiguration() );
        
        invocation.getStack().push( map );
        
        return invocation.invoke();
    }
}
