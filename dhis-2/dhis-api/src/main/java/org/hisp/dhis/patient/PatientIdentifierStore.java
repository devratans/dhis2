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

import org.hisp.dhis.common.GenericStore;
import org.hisp.dhis.organisationunit.OrganisationUnit;

/**
 * @author Abyot Asalefew Gizaw
 * @version $Id$
 */
public interface PatientIdentifierStore
    extends GenericStore<PatientIdentifier>
{
    String ID = PatientIdentifierStore.class.getName();

    PatientIdentifier get( Patient patient );

    PatientIdentifier get( String identifier, OrganisationUnit organisationUnit );
    
    PatientIdentifier get( PatientIdentifierType type, String identifier );

    Collection<PatientIdentifier> getByIdentifier( String identifier );
    
    Collection<PatientIdentifier> getByType( PatientIdentifierType identifierType );
    
    PatientIdentifier getPatientIdentifier(String identifier, Patient patient);

    PatientIdentifier getPatientIdentifier(PatientIdentifierType identifierType, Patient patient);
    
    Collection<PatientIdentifier> getPatientIdentifiers( Patient patient );
    
    Patient getPatient( PatientIdentifierType idenType, String value);
    
    Collection<Patient> getPatientsByIdentifier( String identifier, int min, int max );
    
    int countGetPatientsByIdentifier( String identifier );
        
    Collection<PatientIdentifier> get(Collection<PatientIdentifierType> identifierTypes, Patient patient);

}
