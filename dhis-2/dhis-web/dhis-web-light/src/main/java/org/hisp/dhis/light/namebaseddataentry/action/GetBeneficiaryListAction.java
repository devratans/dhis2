/*
 * Copyright (c) 2004-2011, University of Oslo
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

package org.hisp.dhis.light.namebaseddataentry.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hisp.dhis.api.mobile.ActivityReportingService;
import org.hisp.dhis.api.mobile.model.Activity;
import org.hisp.dhis.api.mobile.model.ActivityPlan;
import org.hisp.dhis.api.mobile.model.Beneficiary;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.organisationunit.OrganisationUnitService;
import com.opensymphony.xwork2.Action;

public class GetBeneficiaryListAction
    implements Action
{

    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

    private ActivityReportingService activityReportingService;

    public void setActivityReportingService( ActivityReportingService activityReportingService )
    {
        this.activityReportingService = activityReportingService;
    }

    private OrganisationUnitService organisationUnitService;

    public void setOrganisationUnitService( OrganisationUnitService organisationUnitService )
    {
        this.organisationUnitService = organisationUnitService;
    }

    // -------------------------------------------------------------------------
    // Input & Output
    // -------------------------------------------------------------------------

    private boolean current;

    public boolean isCurrent()
    {
        return current;
    }

    public void setCurrent( boolean current )
    {
        this.current = current;
    }

    private boolean validated = false;

    public boolean isValidated()
    {
        return validated;
    }

    public void setValidated( boolean validated )
    {
        this.validated = validated;
    }

    private Set<Beneficiary> beneficiaries;

    private OrganisationUnit organisationUnit;

    public OrganisationUnit getOrganisationUnit()
    {
        return this.organisationUnit;
    }

    private Integer organisationUnitId;

    public void setOrganisationUnitId( Integer organisationUnitId )
    {
        this.organisationUnitId = organisationUnitId;
    }

    public Integer getOrganisationUnitId( Integer organisationUnitId )
    {
        return this.organisationUnitId;
    }

    private ActivityPlan activityPlan;

    public ActivityPlan getActivityPlan()
    {
        return this.activityPlan;
    }

    private List<Activity> activities;

    public List<Activity> getActivities()
    {
        return this.activities;
    }

    public Set<Beneficiary> getBeneficiaries()
    {
        return this.beneficiaries;
    }

    // -------------------------------------------------------------------------
    // Action Implementation
    // -------------------------------------------------------------------------

    @Override
    public String execute()
        throws Exception
    {
        beneficiaries = new HashSet<Beneficiary>();

        organisationUnit = organisationUnitService.getOrganisationUnit( organisationUnitId );

        if ( current )
        {
            activityPlan = activityReportingService.getCurrentActivityPlan( organisationUnit, "" );
        }
        else
        {
            activityPlan = activityReportingService.getAllActivityPlan( organisationUnit, "" );
        }

        if ( activityPlan == null )
            return SUCCESS;

        activities = activityPlan.getActivitiesList();

        if ( activities == null )
            return SUCCESS;
        
        Map<Integer, String> compareMap = new HashMap<Integer, String>();
        for ( Activity activity : activities )
        {
            if (compareMap.get( activity.getBeneficiary().getId() ) == null) {
                beneficiaries.add( activity.getBeneficiary() );
                compareMap.put( activity.getBeneficiary().getId(), "" );
            }
            
        }
        return SUCCESS;
    }

}
