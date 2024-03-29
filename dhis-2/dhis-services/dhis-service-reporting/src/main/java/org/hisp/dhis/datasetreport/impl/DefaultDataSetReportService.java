package org.hisp.dhis.datasetreport.impl;

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

import static org.hisp.dhis.dataentryform.DataEntryFormService.DATAELEMENT_TOTAL_PATTERN;
import static org.hisp.dhis.dataentryform.DataEntryFormService.IDENTIFIER_PATTERN;
import static org.hisp.dhis.dataentryform.DataEntryFormService.INDICATOR_PATTERN;
import static org.hisp.dhis.dataentryform.DataEntryFormService.INPUT_PATTERN;
import static org.hisp.dhis.datasetreport.DataSetReportStore.SEPARATOR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import org.hisp.dhis.common.Grid;
import org.hisp.dhis.common.GridHeader;
import org.hisp.dhis.common.comparator.IdentifiableObjectNameComparator;
import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.dataelement.DataElementCategoryCombo;
import org.hisp.dhis.dataelement.DataElementCategoryOption;
import org.hisp.dhis.dataelement.DataElementCategoryOptionCombo;
import org.hisp.dhis.dataentryform.DataEntryForm;
import org.hisp.dhis.dataset.DataSet;
import org.hisp.dhis.dataset.Section;
import org.hisp.dhis.dataset.comparator.SectionOrderComparator;
import org.hisp.dhis.datasetreport.DataSetReportService;
import org.hisp.dhis.datasetreport.DataSetReportStore;
import org.hisp.dhis.datavalue.DataValue;
import org.hisp.dhis.datavalue.DataValueService;
import org.hisp.dhis.i18n.I18n;
import org.hisp.dhis.i18n.I18nFormat;
import org.hisp.dhis.indicator.Indicator;
import org.hisp.dhis.organisationunit.OrganisationUnit;
import org.hisp.dhis.period.Period;
import org.hisp.dhis.system.filter.AggregatableDataElementFilter;
import org.hisp.dhis.system.grid.GridUtils;
import org.hisp.dhis.system.grid.ListGrid;
import org.hisp.dhis.system.util.FilterUtils;

/**
 * @author Abyot Asalefew
 * @author Lars Helge Overland
 */
public class DefaultDataSetReportService
    implements DataSetReportService
{   
    private static final String NULL_REPLACEMENT = "";
    private static final String DEFAULT_HEADER = "Value";
    private static final String TOTAL_HEADER = "Total";
    private static final String SPACE = " ";

    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

    private DataValueService dataValueService;

    public void setDataValueService( DataValueService dataValueService )
    {
        this.dataValueService = dataValueService;
    }
    
    private DataSetReportStore dataSetReportStore;

    public void setDataSetReportStore( DataSetReportStore dataSetReportStore )
    {
        this.dataSetReportStore = dataSetReportStore;
    }

    // -------------------------------------------------------------------------
    // DataSetReportService implementation
    // -------------------------------------------------------------------------

    public String getCustomDataSetReport( DataSet dataSet, OrganisationUnit unit, Period period,
        boolean selectedUnitOnly, I18nFormat format )
    {
        Map<String, Double> valueMap = dataSetReportStore.getAggregatedValues( dataSet, period, unit, selectedUnitOnly );
        valueMap.putAll( dataSetReportStore.getAggregatedTotals( dataSet, period, unit ) );
        
        Map<String, Double> indicatorValueMap = dataSetReportStore.getAggregatedIndicatorValues( dataSet, period, unit );
        
        return prepareReportContent( dataSet.getDataEntryForm(), valueMap, indicatorValueMap, format );
    }
    
    public List<Grid> getCustomDataSetReportAsGrid( DataSet dataSet, OrganisationUnit unit, Period period,
        boolean selectedUnitOnly, I18nFormat format )
    {
        String html = getCustomDataSetReport( dataSet, unit, period, selectedUnitOnly, format );
        
        try
        {
            return GridUtils.fromHtml( html );
        }
        catch ( Exception ex )
        {
            throw new RuntimeException( "Failed to render custom data set report as grid", ex );
        }
    }

    public List<Grid> getSectionDataSetReport( DataSet dataSet, Period period, OrganisationUnit unit,
        boolean selectedUnitOnly, I18nFormat format, I18n i18n )
    {
        List<Section> sections = new ArrayList<Section>( dataSet.getSections() );
        Collections.sort( sections, new SectionOrderComparator() );

        Map<String, Double> valueMap = dataSetReportStore.getAggregatedValues( dataSet, period, unit, selectedUnitOnly );
        Map<String, Double> subTotalMap = dataSetReportStore.getAggregatedSubTotals( dataSet, period, unit );
        Map<String, Double> totalMap = dataSetReportStore.getAggregatedTotals( dataSet, period, unit );

        List<Grid> grids = new ArrayList<Grid>();

        // ---------------------------------------------------------------------
        // Create a grid for each section
        // ---------------------------------------------------------------------

        for ( Section section : sections )
        {
            Grid grid = new ListGrid().setTitle( section.getName() ).
                setSubtitle( unit.getName() + SPACE + format.formatPeriod( period ) );

            DataElementCategoryCombo categoryCombo = section.getCategoryCombo();

            // -----------------------------------------------------------------
            // Grid headers
            // -----------------------------------------------------------------

            grid.addHeader( new GridHeader( i18n.getString( "dataelement" ), false, true ) );

            List<DataElementCategoryOptionCombo> optionCombos = categoryCombo.getSortedOptionCombos();

            for ( DataElementCategoryOptionCombo optionCombo : optionCombos )
            {
                grid.addHeader( new GridHeader( optionCombo.isDefault() ? DEFAULT_HEADER : optionCombo.getName(),
                    false, false ) );
            }

            if ( categoryCombo.doSubTotals() && !selectedUnitOnly ) // Sub-total
            {
                for ( DataElementCategoryOption categoryOption : categoryCombo.getCategoryOptions() )
                {
                    grid.addHeader( new GridHeader( categoryOption.getName(), false, false ) );
                }
            }

            if ( categoryCombo.doTotal() && !selectedUnitOnly ) // Total
            {
                grid.addHeader( new GridHeader( TOTAL_HEADER, false, false ) );
            }

            // -----------------------------------------------------------------
            // Grid values
            // -----------------------------------------------------------------

            List<DataElement> dataElements = new ArrayList<DataElement>( section.getDataElements() );
            FilterUtils.filter( dataElements, new AggregatableDataElementFilter() );
            
            for ( DataElement dataElement : dataElements )
            {
                grid.addRow();
                grid.addValue( dataElement.getName() ); // Data element name

                for ( DataElementCategoryOptionCombo optionCombo : optionCombos ) // Values
                {
                    Double value = null;

                    if ( selectedUnitOnly )
                    {
                        DataValue dataValue = dataValueService.getDataValue( unit, dataElement, period, optionCombo );
                        value = dataValue != null && dataValue.getValue() != null ? Double.parseDouble( dataValue
                            .getValue() ) : null;
                    }
                    else
                    {
                        value = valueMap.get( dataElement.getId() + SEPARATOR + optionCombo.getId() );
                    }

                    grid.addValue( value );
                }

                if ( categoryCombo.doSubTotals() && !selectedUnitOnly ) // Sub-total
                {
                    for ( DataElementCategoryOption categoryOption : categoryCombo.getCategoryOptions() )
                    {
                        Double value = subTotalMap.get( dataElement.getId() + SEPARATOR + categoryOption.getId() );

                        grid.addValue( value );
                    }
                }

                if ( categoryCombo.doTotal() && !selectedUnitOnly ) // Total
                {
                    Double value = totalMap.get( String.valueOf( dataElement.getId() ) );

                    grid.addValue( value );
                }
            }

            grids.add( grid );
        }

        return grids;
    }

    public Grid getDefaultDataSetReport( DataSet dataSet, Period period, OrganisationUnit unit, boolean selectedUnitOnly, I18nFormat format, I18n i18n )
    {
        List<DataElement> dataElements = new ArrayList<DataElement>( dataSet.getDataElements() );

        Collections.sort( dataElements, IdentifiableObjectNameComparator.INSTANCE );
        FilterUtils.filter( dataElements, new AggregatableDataElementFilter() );

        Map<String, Double> valueMap = dataSetReportStore.getAggregatedValues( dataSet, period, unit, selectedUnitOnly );
        Map<String, Double> indicatorValueMap = dataSetReportStore.getAggregatedIndicatorValues( dataSet, period, unit );
        
        // ---------------------------------------------------------------------
        // Get category option combos
        // ---------------------------------------------------------------------

        Set<DataElementCategoryOptionCombo> optionCombos = new HashSet<DataElementCategoryOptionCombo>();

        for ( DataElement dataElement : dataElements )
        {
            optionCombos.addAll( dataElement.getCategoryCombo().getOptionCombos() );
        }

        List<DataElementCategoryOptionCombo> orderedOptionCombos = new ArrayList<DataElementCategoryOptionCombo>(
            optionCombos );

        Collections.sort( orderedOptionCombos, IdentifiableObjectNameComparator.INSTANCE );

        // ---------------------------------------------------------------------
        // Create a grid
        // ---------------------------------------------------------------------

        Grid grid = new ListGrid().setTitle( dataSet.getName() );
        grid.setSubtitle( unit.getName() + SPACE + format.formatPeriod( period ) );

        // ---------------------------------------------------------------------
        // Headers
        // ---------------------------------------------------------------------

        grid.addHeader( new GridHeader( i18n.getString( "name" ), false, true ) );
         
        for ( DataElementCategoryOptionCombo optionCombo : orderedOptionCombos )
        {
            grid.addHeader( new GridHeader( optionCombo.isDefault() ? DEFAULT_HEADER : optionCombo.getName(), false, false ) );
        }

        // ---------------------------------------------------------------------
        // Values
        // ---------------------------------------------------------------------

        for ( DataElement dataElement : dataElements )
        {
            grid.addRow();

            grid.addValue( dataElement.getName() );

            for ( DataElementCategoryOptionCombo optionCombo : orderedOptionCombos )
            {
                Double value = null;

                if ( selectedUnitOnly )
                {
                    DataValue dataValue = dataValueService.getDataValue( unit, dataElement, period, optionCombo );
                    value = dataValue != null && dataValue.getValue() != null ? Double.parseDouble( dataValue.getValue() ) : null;
                }
                else
                {
                    value = valueMap.get( dataElement.getId() + SEPARATOR + optionCombo.getId() );
                }

                grid.addValue( value );

            }
        }
        
        // ---------------------------------------------------------------------
        // Indicator values
        // ---------------------------------------------------------------------

        List<Indicator> indicators = new ArrayList<Indicator>( dataSet.getIndicators() );
        
        for ( Indicator indicator : indicators )
        {
            grid.addRow();

            grid.addValue( indicator.getName() );
            
            Double value = indicatorValueMap.get( String.valueOf( indicator.getId() ) );
            
            grid.addValue( value );
            
            for ( int i = 0; i < orderedOptionCombos.size() - 1; i++ )
            {
                grid.addValue( NULL_REPLACEMENT );
            }
        }
        
        return grid;
    }

    // -------------------------------------------------------------------------
    // Supportive methods
    // -------------------------------------------------------------------------

    /**
     * Puts in aggregated datavalues in the custom dataentry form and returns
     * whole report text.
     * 
     * @param dataEntryForm the data entry form.
     * @param a map with aggregated data values mapped to data element operands.
     * @return data entry form HTML code populated with aggregated data in the
     *         input fields.
     */
    private String prepareReportContent( DataEntryForm dataEntryForm, Map<String, Double> dataValues,
        Map<String, Double> indicatorValues, I18nFormat format )
    {
        StringBuffer buffer = new StringBuffer();

        Matcher inputMatcher = INPUT_PATTERN.matcher( dataEntryForm.getHtmlCode() );

        // ---------------------------------------------------------------------
        // Iterate through all matching data element fields.
        // ---------------------------------------------------------------------

        while ( inputMatcher.find() )
        {
            // -----------------------------------------------------------------
            // Get input HTML code
            // -----------------------------------------------------------------

            String inputHtml = inputMatcher.group( 1 );

            Matcher identifierMatcher = IDENTIFIER_PATTERN.matcher( inputHtml );
            Matcher dataElementTotalMatcher = DATAELEMENT_TOTAL_PATTERN.matcher( inputHtml );
            Matcher indicatorMatcher = INDICATOR_PATTERN.matcher( inputHtml );

            // -----------------------------------------------------------------
            // Find existing data or indicator value and replace input tag
            // -----------------------------------------------------------------

            if ( identifierMatcher.find() && identifierMatcher.groupCount() > 0 )
            {
                String dataElementId = identifierMatcher.group( 1 );
                String optionComboId = identifierMatcher.group( 2 );

                Double dataValue = dataValues.get( dataElementId + SEPARATOR + optionComboId );

                inputMatcher.appendReplacement( buffer, format.formatValue( dataValue ) );
            }
            else if ( dataElementTotalMatcher.find() && dataElementTotalMatcher.groupCount() > 0 )
            {
                String dataElementId = dataElementTotalMatcher.group( 1 );
                
                Double dataValue = dataValues.get( dataElementId );

                inputMatcher.appendReplacement( buffer, format.formatValue( dataValue ) );
            }
            else if ( indicatorMatcher.find() && indicatorMatcher.groupCount() > 0 )
            {
                String indicatorId = indicatorMatcher.group( 1 );

                Double indicatorValue = indicatorValues.get( indicatorId );

                inputMatcher.appendReplacement( buffer, format.formatValue( indicatorValue ) );
            }
        }

        inputMatcher.appendTail( buffer );

        return buffer.toString();
    }
}
