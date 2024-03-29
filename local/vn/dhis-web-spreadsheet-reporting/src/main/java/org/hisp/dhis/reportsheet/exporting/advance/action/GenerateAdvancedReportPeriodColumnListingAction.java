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

package org.hisp.dhis.reportsheet.exporting.advance.action;

import java.util.Collection;
import java.util.Set;

import org.apache.poi.ss.usermodel.Sheet;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.organisationunit.OrganisationUnitGroup;
import org.hisp.dhis.organisationunit.OrganisationUnitGroupService;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.reportsheet.PeriodColumn;
import org.hisp.dhis.reportsheet.ExportReport;
import org.hisp.dhis.reportsheet.ExportItem;
import org.hisp.dhis.reportsheet.ExportReportPeriodColumnListing;
import org.hisp.dhis.reportsheet.exporting.AbstractGenerateExcelReportSupport;
import org.hisp.dhis.reportsheet.utils.ExcelUtils;
import org.hisp.dhis.reportsheet.utils.ExpressionUtils;
import org.hisp.dhis.system.util.MathUtils;

/**
 * @author Chau Thu Tran
 * @author Tran Thanh Tri
 * @version $Id$
 */

public class GenerateAdvancedReportPeriodColumnListingAction
    extends AbstractGenerateExcelReportSupport
{
    // -------------------------------------------------------------------------
    // Dependency
    // -------------------------------------------------------------------------

    private OrganisationUnitGroupService organisationUnitGroupService;

    public void setOrganisationUnitGroupService( OrganisationUnitGroupService organisationUnitGroupService )
    {
        this.organisationUnitGroupService = organisationUnitGroupService;
    }

    // -------------------------------------------------------------------------
    // Input && Output
    // -------------------------------------------------------------------------

    private Integer organisationGroupId;

    public void setOrganisationGroupId( Integer organisationGroupId )
    {
        this.organisationGroupId = organisationGroupId;
    }

    // -------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------

    @Override
    protected void executeGenerateOutputFile( ExportReport exportReport, Period period )
        throws Exception
    {
        OrganisationUnitGroup organisationUnitGroup = organisationUnitGroupService
            .getOrganisationUnitGroup( organisationGroupId.intValue() );

        ExportReportPeriodColumnListing exportReportInstance = (ExportReportPeriodColumnListing) exportReport;

        this.installReadTemplateFile( exportReportInstance, period, organisationUnitGroup );

        for ( Integer sheetNo : exportReportService.getSheets( selectionManager.getSelectedReportId() ) )
        {
            Sheet sheet = this.templateWorkbook.getSheetAt( sheetNo - 1 );

            Collection<ExportItem> exportReportItems = exportReportInstance.getExportItemBySheet( sheetNo );

            this.generateOutPutFile( exportReportInstance.getPeriodColumns(), exportReportItems, organisationUnitGroup
                .getMembers(), sheet );
        }

        for ( Integer sheetNo : exportReportService.getSheets( selectionManager.getSelectedReportId() ) )
        {
            Sheet sheet = this.templateWorkbook.getSheetAt( sheetNo - 1 );

            this.recalculatingFormula( sheet );
        }
    }

    private void generateOutPutFile( Collection<PeriodColumn> periodColumns, Collection<ExportItem> exportItems,
        Set<OrganisationUnit> organisationUnits, Sheet sheet )
    {
        for ( ExportItem exportItem : exportItems )
        {
            int i = 0;
            for ( PeriodColumn p : periodColumns )
            {
                if ( p.getPeriodType().equals( exportItem.getPeriodType() ) )
                {
                    double value = 0.0;

                    for ( OrganisationUnit organisationUnit : organisationUnits )
                    {
                        if ( exportItem.getItemType().equalsIgnoreCase( ExportItem.TYPE.DATAELEMENT ) )
                        {
                            value += MathUtils.calculateExpression( ExpressionUtils.generateExpression( exportItem, p
                                .getStartdate(), p.getEnddate(), organisationUnit, dataElementService, categoryService,
                                aggregationService ) );
                        }
                        else if ( exportItem.getItemType().equalsIgnoreCase( ExportItem.TYPE.INDICATOR ) )
                        {
                            value += MathUtils.calculateExpression( ExpressionUtils.generateExpression( exportItem, p
                                .getStartdate(), p.getEnddate(), organisationUnit, dataElementService, categoryService,
                                aggregationService ) );
                        }
                    }

                    ExcelUtils.writeValueByPOI( exportItem.getRow(), p.getColumn(), String.valueOf( value ),
                        ExcelUtils.NUMBER, sheet, this.csNumber );

                    i++;
                }
            }
        }
    }
}
