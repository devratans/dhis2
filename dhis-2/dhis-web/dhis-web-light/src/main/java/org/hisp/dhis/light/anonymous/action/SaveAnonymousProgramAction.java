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

package org.hisp.dhis.light.anonymous.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.dataelement.DataElementService;
import org.hisp.dhis.light.utils.NamebasedUtils;
import org.hisp.dhis.patientdatavalue.PatientDataValue;
import org.hisp.dhis.patientdatavalue.PatientDataValueService;
import org.hisp.dhis.program.Program;
import org.hisp.dhis.program.ProgramInstance;
import org.hisp.dhis.program.ProgramInstanceService;
import org.hisp.dhis.program.ProgramService;
import org.hisp.dhis.program.ProgramStage;
import org.hisp.dhis.program.ProgramStageDataElement;
import org.hisp.dhis.program.ProgramStageDataElementService;
import org.hisp.dhis.program.ProgramStageInstance;
import org.hisp.dhis.program.ProgramStageInstanceService;
import org.hisp.dhis.util.ContextUtils;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author Nguyen Kim Lai
 * 
 * @version $ SaveAnonymousProgramAction.java Jun 14, 2012 $
 */
public class SaveAnonymousProgramAction
    implements Action
{
    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

    private ProgramService programService;

    public void setProgramService( ProgramService programService )
    {
        this.programService = programService;
    }

    private ProgramStageInstanceService programStageInstanceService;

    public void setProgramStageInstanceService( ProgramStageInstanceService programStageInstanceService )
    {
        this.programStageInstanceService = programStageInstanceService;
    }

    private ProgramInstanceService programInstanceService;

    public void setProgramInstanceService( ProgramInstanceService programInstanceService )
    {
        this.programInstanceService = programInstanceService;
    }

    private PatientDataValueService patientDataValueService;

    public void setPatientDataValueService( PatientDataValueService patientDataValueService )
    {
        this.patientDataValueService = patientDataValueService;
    }

    private DataElementService dataElementService;

    public void setDataElementService( DataElementService dataElementService )
    {
        this.dataElementService = dataElementService;
    }

    private NamebasedUtils util;

    public NamebasedUtils getUtil()
    {
        return util;
    }

    public void setUtil( NamebasedUtils util )
    {
        this.util = util;
    }

    private ProgramStageDataElementService programStageDataElementService;

    public ProgramStageDataElementService getProgramStageDataElementService()
    {
        return programStageDataElementService;
    }

    public void setProgramStageDataElementService( ProgramStageDataElementService programStageDataElementService )
    {
        this.programStageDataElementService = programStageDataElementService;
    }

    // -------------------------------------------------------------------------
    // Input Output
    // -------------------------------------------------------------------------

    private Map<String, String> typeViolations = new HashMap<String, String>();

    public Map<String, String> getTypeViolations()
    {
        return typeViolations;
    }

    private Map<String, String> prevDataValues = new HashMap<String, String>();

    public Map<String, String> getPrevDataValues()
    {
        return prevDataValues;
    }

    private Integer programId;

    public Integer getProgramId()
    {
        return programId;
    }

    public void setProgramId( Integer programId )
    {
        this.programId = programId;
    }

    private ProgramStage programStage;

    public ProgramStage getProgramStage()

    {
        return programStage;
    }

    private Program program;

    public Program getProgram()

    {
        return program;
    }

    private ArrayList<ProgramStageDataElement> programStageDataElements = new ArrayList<ProgramStageDataElement>();

    public ArrayList<ProgramStageDataElement> getProgramStageDataElements()
    {
        return this.programStageDataElements;
    }
    
    public void setProgramStageDataElements( ArrayList<ProgramStageDataElement> programStageDataElements )
    {
        this.programStageDataElements = programStageDataElements;
    }

    static final Comparator<ProgramStageDataElement> OrderBySortOrder = new Comparator<ProgramStageDataElement>()
    {
        public int compare( ProgramStageDataElement i1, ProgramStageDataElement i2 )
        {
            return i1.getSortOrder().compareTo( i2.getSortOrder() );
        }
    };

    // -------------------------------------------------------------------------
    // Implementation Action
    // -------------------------------------------------------------------------

    @Override
    public String execute()
        throws Exception
    {

        program = programService.getProgram( programId );

        // -------------------------------------------------------------------------
        // Getting all data from UI
        // -------------------------------------------------------------------------

        programStage = program.getProgramStages().iterator().next();

        programStageDataElements = new ArrayList<ProgramStageDataElement>( programStage.getProgramStageDataElements() );
        Collections.sort( programStageDataElements, OrderBySortOrder );

        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(
            ServletActionContext.HTTP_REQUEST );

        Map<String, String> parameterMap = ContextUtils.getParameterMap( request );

        typeViolations.clear();

        prevDataValues.clear();

        // -------------------------------------------------------------------------
        // Validation
        // -------------------------------------------------------------------------

        for ( String key : parameterMap.keySet() )
        {
            if ( key.startsWith( "DE" ) )
            {
                Integer dataElementId = Integer.parseInt( key.substring( 2, key.length() ) );

                String value = parameterMap.get( key );

                DataElement dataElement = dataElementService.getDataElement( dataElementId );

                ProgramStageDataElement programStageDataElement = programStageDataElementService.get( programStage,
                    dataElement );

                value = value.trim();

                Boolean valueIsEmpty = (value == null || value.length() == 0);

                if ( !valueIsEmpty )
                {
                    String typeViolation = util.getTypeViolation( dataElement, value );

                    if ( typeViolation != null )
                    {
                        typeViolations.put( key, typeViolation );
                    }
                    prevDataValues.put( key, value );
                }
                else if ( valueIsEmpty && programStageDataElement.isCompulsory() )
                {
                    typeViolations.put( key, "is_empty" );
                    prevDataValues.put( key, value );
                }
            }
        }

        if ( !typeViolations.isEmpty() )
        {
            return ERROR;
        }

        ProgramInstance programInstance = new ProgramInstance();

        programInstance.setEnrollmentDate( new Date() );

        programInstance.setDateOfIncident( new Date() );

        programInstance.setProgram( program );

        programInstance.setCompleted( false );

        programInstanceService.addProgramInstance( programInstance );

        ProgramStageInstance programStageInstance = new ProgramStageInstance();

        programStageInstance.setProgramInstance( programInstance );

        programStageInstance.setProgramStage( programStage );

        programStageInstance.setDueDate( new Date() );

        programStageInstance.setExecutionDate( new Date() );

        programStageInstance.setCompleted( false );

        programStageInstanceService.addProgramStageInstance( programStageInstance );

        for ( ProgramStageDataElement programStageDataElement : programStageDataElements )
        {
            DataElement dataElement = programStageDataElement.getDataElement();

            PatientDataValue patientDataValue = new PatientDataValue();

            patientDataValue.setDataElement( dataElement );

            String id = "DE" + dataElement.getId();

            patientDataValue.setValue( parameterMap.get( id ) );

            patientDataValue.setProgramStageInstance( programStageInstance );

            patientDataValue.setProvidedElsewhere( false );

            patientDataValue.setTimestamp( new Date() );

            patientDataValueService.savePatientDataValue( patientDataValue );
        }

        return SUCCESS;
    }

}
