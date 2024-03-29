/*
 * Copyright (c) 2004-2009, University of Oslo
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

package org.hisp.dhis.patient;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hisp.dhis.common.GenericStore;
import org.hisp.dhis.common.Grid;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.program.Program;

/**
 * @author Abyot Asalefew Gizaw
 * @version $Id$
 */
public interface PatientStore
    extends GenericStore<Patient>
{
    String ID = PatientStore.class.getName();

    Collection<Patient> getByGender( String gender );

    Collection<Patient> getByBirthDate( Date birthDate );

    Collection<Patient> getByNames( String name, Integer min, Integer max );

    Collection<Patient> get( String firstName, String middleName, String lastName, Date birthdate, String gender );

    Collection<Patient> getByOrgUnit( OrganisationUnit organisationUnit, Integer min, Integer max );

    Collection<Patient> getByOrgUnitProgram( OrganisationUnit organisationUnit, Program program, Integer min,
        Integer max );

    Collection<Patient> getRepresentatives( Patient patient );

    int countListPatientByOrgunit( OrganisationUnit organisationUnit );

    int countGetPatientsByName( String name );

    int countGetPatientsByOrgUnitProgram( OrganisationUnit organisationUnit, Program program );

    void removeErollmentPrograms( Program program );

    Collection<Patient> search( List<String> searchKeys, OrganisationUnit orgunit, Integer min, Integer max );

    int countSearch( List<String> searchKeys, OrganisationUnit orgunit );

    Collection<String> getPatientPhoneNumbers( List<String> searchKeys, OrganisationUnit orgunit, Integer min,
        Integer max );

    Collection<Integer> getProgramStageInstances( List<String> searchKeys, OrganisationUnit orgunit, Integer min,
        Integer max );

    Grid getPatientEventReport( Grid grid, List<String> searchKeys, OrganisationUnit orgunit );
    
    Collection<Patient> getByPhoneNumber( String phoneNumber, Integer min, Integer max);
    
}
