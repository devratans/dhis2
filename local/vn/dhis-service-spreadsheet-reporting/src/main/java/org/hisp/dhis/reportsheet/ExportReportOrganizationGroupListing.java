package org.hisp.dhis.reportsheet;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hisp.dhis.organisationunit.OrganisationUnitGroup;
import org.hisp.dhis.organisationunit.OrganisationUnitLevel;

/**
 * @author Chau Thu Tran
 * @version $Id$
 */

public class ExportReportOrganizationGroupListing
    extends ExportReport
{
    private List<OrganisationUnitGroup> organisationUnitGroups;

    private Map<OrganisationUnitGroup, OrganisationUnitLevel> organisationUnitLevels;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public ExportReportOrganizationGroupListing()
    {
        super();
    }

    // -------------------------------------------------------------------------
    // Getters and setters
    // -------------------------------------------------------------------------

    public List<OrganisationUnitGroup> getOrganisationUnitGroups()
    {
        return organisationUnitGroups;
    }

    public Map<OrganisationUnitGroup, OrganisationUnitLevel> getOrganisationUnitLevels()
    {
        return organisationUnitLevels;
    }

    public void setOrganisationUnitLevels( Map<OrganisationUnitGroup, OrganisationUnitLevel> organisationUnitLevels )
    {
        this.organisationUnitLevels = organisationUnitLevels;
    }

    public void setOrganisationUnitGroups( List<OrganisationUnitGroup> organisationUnitGroups )
    {
        this.organisationUnitGroups = organisationUnitGroups;
    }

    @Override
    public String getReportType()
    {
        return ExportReport.TYPE.ORGANIZATION_GROUP_LISTING;
    }

    @Override
    public List<String> getItemTypes()
    {
        List<String> types = new ArrayList<String>();
        types.add( ExportItem.TYPE.DATAELEMENT );
        types.add( ExportItem.TYPE.ORGANISATION );
        types.add( ExportItem.TYPE.INDICATOR );
        types.add( ExportItem.TYPE.FORMULA_EXCEL );
        types.add( ExportItem.TYPE.SERIAL );

        return types;
    }
}
