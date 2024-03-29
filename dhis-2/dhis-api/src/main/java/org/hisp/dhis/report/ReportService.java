package org.hisp.dhis.report;

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

import java.io.OutputStream;
import java.util.Collection;

import net.sf.jasperreports.engine.JasperPrint;

import org.hisp.dhis.i18n.I18nFormat;
import org.hisp.dhis.period.Period;

/**
 * @author Lars Helge Overland
 */
public interface ReportService
{
    final String ID = ReportService.class.getName();

    final String REPORTTYPE_PDF = "pdf";
    final String REPORTTYPE_XLS = "xls";

    final String PARAM_RELATIVE_PERIODS = "periods";
    final String PARAM_ORG_UNITS = "organisationunits";
    final String PARAM_ORGANISATIONUNIT_LEVEL = "organisationunit_level";
    final String PARAM_ORGANISATIONUNIT_LEVEL_COLUMN = "organisationunit_level_column";
    final String PARAM_ORGANISATIONUNIT_COLUMN_NAME = "organisationunit_name";
    final String PARAM_PERIOD_NAME = "period_name";
    
    /**
     * Renders a Jasper Report. 
     * 
     * Will make the following params available:
     * 
     * "periods" String of relative period ids (String)
     * "organisationunits" String of selected organisation unit ids (String)
     * "period_name" Name of the selected period (String)
     * "organisationunit_name" Name of the selected organisation unit (String)
     * "organisationunit_level" Level of the selected organisation unit (int)
     * "organisationunit_level_column" Name of the relevant level column in  (String)
     *     table _orgunitstructure.
     * 
     * @param out the OutputStream to write the report to.
     * @param reportUid the uid of the report to render.
     * @param period the period to use as parameter.
     * @param organisationUnitUid the uid of the org unit to use as parameter.
     * @param type the type of the report, can be "xls" and "pdf".
     * @param format the I18nFormat to use.
     */
    JasperPrint renderReport( OutputStream out, String reportUid, Period period,
                       String organisationUnitUid, String type, I18nFormat format );

    /**
     * Saves a Report.
     *
     * @param report the Report to save.
     * @return the generated identifier.
     */
    int saveReport( Report report );

    /**
     * Retrieves the Report with the given identifier.
     *
     * @param id the identifier of the Report to retrieve.
     * @return the Report.
     */
    Report getReport( int id );

    /**
     * Retrieves the Report with the given uid.
     *
     * @param uid the uid of the Report to retrieve.
     * @return the Report.
     */
    Report getReport( String uid );

    /**
     * Returns the total number of reports. 
     * @return the total number of reports.
     */
    int getReportCount();
    
    /**
     * Returns the number of reports which names are like the given name.
     * Returns the number of reports which names are like the given name.
     */
    int getReportCountByName( String name );

    /**
     * Retrieves the given number of maximum reports starting at the given start
     * index. Reports are sorted on the name property.
     * 
     * @param first the start index.
     * @param max the maximum number of reports.
     * @return a collection of reports.
     */
    Collection<Report> getReportsBetween( int first, int max );

    /**
     * Retrieves the given number of maximum reports starting at the given start
     * index. Reports are sorted on the name property.
     * 
     * @param first the start index.
     * @param max the maximum number of reports.
     * @return a collection of reports.
     */
    Collection<Report> getReportsBetweenByName( String name, int first, int max );

    /**
     * Deletes a Report.
     *
     * @param report the Report to delete.
     */
    void deleteReport( Report report );

    /**
     * Retrieves all Reports.
     *
     * @return a Collection of Reports.
     */
    Collection<Report> getAllReports();

    /**
     * Retrieves the Report with the given name.
     *
     * @param name the name.
     * @return the Report.
     */
    Report getReportByName( String name );

    /**
     * Retrieves all Reports with the given identifiers.
     *
     * @param identifiers the Collection of identifiers.
     * @return a Collection of Reports.
     */
    Collection<Report> getReports( final Collection<Integer> identifiers );
}
