package org.hisp.dhis.orgunitdistribution;

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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import org.hisp.dhis.DhisSpringTest;
import org.hisp.dhis.common.Grid;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.organisationunit.OrganisationUnitGroup;
import org.hisp.dhis.organisationunit.OrganisationUnitGroupService;
import org.hisp.dhis.organisationunit.OrganisationUnitGroupSet;
import org.hisp.dhis.organisationunit.OrganisationUnitService;
import org.junit.Test;

/**
 * @author Lars Helge Overland
 */
public class OrgUnitDistributionServiceTest
    extends DhisSpringTest
{
    private OrganisationUnitService organisationUnitService;

    private OrganisationUnitGroupService organisationUnitGroupService;
    
    private OrgUnitDistributionService distributionService;

    @Override
    public void setUpTest()
        throws Exception
    {
        organisationUnitService = (OrganisationUnitService) getBean( OrganisationUnitService.ID );

        organisationUnitGroupService = (OrganisationUnitGroupService) getBean( OrganisationUnitGroupService.ID );
        
        distributionService = (OrgUnitDistributionService) getBean( OrgUnitDistributionService.ID );
    }

    @Test
    public void testGetOrganisationUnitsByNameAndGroups()
    {
        OrganisationUnit unitA = createOrganisationUnit( 'A' );
        OrganisationUnit unitB = createOrganisationUnit( 'B', unitA );
        unitA.getChildren().add( unitB );
        OrganisationUnit unitC = createOrganisationUnit( 'C', unitA );
        unitA.getChildren().add( unitC );
        organisationUnitService.addOrganisationUnit( unitA );
        organisationUnitService.addOrganisationUnit( unitB );
        organisationUnitService.addOrganisationUnit( unitC );
        
        OrganisationUnitGroup groupA = createOrganisationUnitGroup( 'A' );
        OrganisationUnitGroup groupB = createOrganisationUnitGroup( 'B' );
        
        groupA.getMembers().add( unitA );
        groupA.getMembers().add( unitB );
        groupB.getMembers().add( unitC );
        
        organisationUnitGroupService.addOrganisationUnitGroup( groupA );
        organisationUnitGroupService.addOrganisationUnitGroup( groupB );
        
        OrganisationUnitGroupSet groupSet = createOrganisationUnitGroupSet( 'A' );
        groupSet.getOrganisationUnitGroups().add( groupA );
        groupSet.getOrganisationUnitGroups().add( groupB );
        
        organisationUnitGroupService.addOrganisationUnitGroupSet( groupSet );
        
        Grid grid = distributionService.getOrganisationUnitDistribution( groupSet, unitA, false );
        assertNotNull( grid );
        assertEquals( 4, grid.getWidth() ); // Including total
        assertEquals( 3, grid.getHeight() ); // Including total
    }   
}
