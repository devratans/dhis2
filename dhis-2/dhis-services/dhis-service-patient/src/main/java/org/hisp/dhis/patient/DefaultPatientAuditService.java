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

import org.springframework.transaction.annotation.Transactional;

/**
 * @author Chau Thu Tran
 * 
 * @version DefaultPatientAuditService.java 9:08:54 AM Sep 26, 2012 $
 */
@Transactional
public class DefaultPatientAuditService
    implements PatientAuditService
{
    private PatientAuditStore patientAuditStore;

    public void setPatientAuditStore( PatientAuditStore patientAuditStore )
    {
        this.patientAuditStore = patientAuditStore;
    }

    @Override
    public int savePatientAudit( PatientAudit patientAudit )
    {
        return patientAuditStore.save( patientAudit );
    }

    @Override
    public void deletePatientAudit( PatientAudit patientAudit )
    {
        patientAuditStore.update( patientAudit );
    }

    @Override
    public PatientAudit getPatientAudit( int id )
    {
        return patientAuditStore.get( id );
    }

    @Override
    public Collection<PatientAudit> getAllPatientAudit()
    {
        return patientAuditStore.getAll();
    }

    @Override
    public Collection<PatientAudit> getPatientAudits( Patient patient )
    {
        return patientAuditStore.get( patient );
    }

    @Override
    public PatientAudit getPatientAudit( Patient patient, String visitor, Date date )
    {
        return patientAuditStore.get( patient, visitor, date );
    }

}
