package org.hisp.dhis.reportsheet.exporting.action;

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

import java.util.Collection;

import org.apache.poi.ss.usermodel.Sheet;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.reportsheet.ExportItem;
import org.hisp.dhis.reportsheet.ExportReport;
import org.hisp.dhis.reportsheet.ExportReportNormal;
import org.hisp.dhis.reportsheet.exporting.AbstractGenerateExcelReportSupport;
import org.hisp.dhis.reportsheet.utils.ExcelUtils;

/**
 * @author Tran Thanh Tri
 * @author Dang Duy Hieu
 * @version $Id$
 * @since 2009-09-18
 */
public class GenerateReportNormalAction
    extends AbstractGenerateExcelReportSupport
{
    @Override
    protected void executeGenerateOutputFile( ExportReport exportReport, Period period )
        throws Exception
    {
        OrganisationUnit organisationUnit = organisationUnitSelectionManager.getSelectedOrganisationUnit();

        ExportReportNormal exportReportInstance = (ExportReportNormal) exportReport;

        this.installReadTemplateFile( exportReportInstance, period, organisationUnit );

        Collection<ExportItem> exportReportItems = null;

        for ( Integer sheetNo : exportReportService.getSheets( selectionManager.getSelectedReportId() ) )
        {
            Sheet sheet = this.templateWorkbook.getSheetAt( sheetNo - 1 );

            exportReportItems = exportReportInstance.getExportItemBySheet( sheetNo );

            this.generateOutPutFile( exportReportItems, organisationUnit, sheet );
        }

        /**
         * Garbage
         */
        exportReportItems = null;
    }

    // -------------------------------------------------------------------------
    // Supportive method
    // -------------------------------------------------------------------------

    private void generateOutPutFile( Collection<ExportItem> exportReportItems, OrganisationUnit organisationUnit,
        Sheet sheet )
    {
        for ( ExportItem reportItem : exportReportItems )
        {
            if ( reportItem.getItemType().equalsIgnoreCase( ExportItem.TYPE.DATAELEMENT ) )
            {
                double value = getDataValue( reportItem, organisationUnit );

                ExcelUtils.writeValueByPOI( reportItem.getRow(), reportItem.getColumn(), String.valueOf( value ),
                    ExcelUtils.NUMBER, sheet, this.csNumber );
            }
            else if ( reportItem.getItemType().equalsIgnoreCase( ExportItem.TYPE.DATAELEMENT_VALUETYPE_TEXT ) )
            {
                String value = getTextValue( reportItem, organisationUnit );

                ExcelUtils.writeValueByPOI( reportItem.getRow(), reportItem.getColumn(), value, ExcelUtils.TEXT, sheet,
                    this.csText );
            }
            else if ( reportItem.getItemType().equalsIgnoreCase( ExportItem.TYPE.INDICATOR ) )
            {
                double value = getIndicatorValue( reportItem, organisationUnit );

                ExcelUtils.writeValueByPOI( reportItem.getRow(), reportItem.getColumn(), String.valueOf( value ),
                    ExcelUtils.NUMBER, sheet, this.csNumber );
            }
            else
            // EXCEL FORMULA
            {
                ExcelUtils.writeFormulaByPOI( reportItem.getRow(), reportItem.getColumn(), reportItem.getExpression(),
                    sheet, this.csFormula );
            }
        }
    }

}
