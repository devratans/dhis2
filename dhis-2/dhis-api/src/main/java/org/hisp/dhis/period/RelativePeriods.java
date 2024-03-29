package org.hisp.dhis.period;

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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.hisp.dhis.common.Dxf2Namespace;
import org.hisp.dhis.i18n.I18nFormat;

import java.io.Serializable;
import java.util.*;

/**
 * @author Lars Helge Overland
 * @version $Id$
 */
@JacksonXmlRootElement( localName = "relativePeriods", namespace = Dxf2Namespace.NAMESPACE )
public class RelativePeriods
    implements Serializable
{
    /**
     * Determines if a de-serialized file is compatible with this class.
     */
    private static final long serialVersionUID = 2949655296199662273L;

    private static final List<Period> NO = new ArrayList<Period>();
    
    public static final String REPORTING_MONTH = "reporting_month";
    public static final String REPORTING_BIMONTH = "reporting_bimonth";
    public static final String REPORTING_QUARTER = "reporting_quarter";
    public static final String LAST_SIXMONTH = "last_sixmonth";
    public static final String THIS_YEAR = "year";
    public static final String LAST_YEAR = "last_year";
    public static final String THIS_FINANCIAL_YEAR = "financial_year";
    public static final String LAST_FINANCIAL_YEAR = "last_financial_year";

    public static final String[] MONTHS_THIS_YEAR = {
        "january",
        "february",
        "march",
        "april",
        "may",
        "june",
        "july",
        "august",
        "september",
        "october",
        "november",
        "december"};

    public static final String[] MONTHS_LAST_YEAR = {
        "january_last_year",
        "february_last_year",
        "march_last_year",
        "april_last_year",
        "may_last_year",
        "june_last_year",
        "july_last_year",
        "august_last_year",
        "september_last_year",
        "october_last_year",
        "november_last_year",
        "december_last_year"};

    public static final String[] MONTHS_LAST_12 = {
        "month1",
        "month2",
        "month3",
        "month4",
        "month5",
        "month6",
        "month7",
        "month8",
        "month9",
        "month10",
        "month11",
        "month12"};

    public static final String[] BIMONTHS_LAST_6 = {
        "bimonth1",
        "bimonth2",
        "bimonth3",
        "bimonth4",
        "bimonth5",
        "bimonth6"};

    public static final String[] QUARTERS_THIS_YEAR = {
        "quarter1",
        "quarter2",
        "quarter3",
        "quarter4"};

    public static final String[] SIXMONHTS_LAST_2 = {
        "sixmonth1",
        "sixmonth2"};

    public static final String[] QUARTERS_LAST_YEAR = {
        "quarter1_last_year",
        "quarter2_last_year",
        "quarter3_last_year",
        "quarter4_last_year" };

    public static final String[] LAST_5_YEARS = {
        "year_minus_4",
        "year_minus_3",
        "year_minus_2",
        "year_minus_1",
        "year_this" };
    
    public static final String[] LAST_5_FINANCIAL_YEARS = {
        "financial_year_minus_4",
        "financial_year_minus_3",
        "financial_year_minus_2",
        "financial_year_minus_1",
        "financial_year_this" };
    
    public static final String[] WEEKS_LAST_52 = {
        "w1", "w2", "w3", "w4", "w5", "w6", "w7", "w8", "w9", "w10", 
        "w11", "w12", "w13", "w14", "w15", "w16", "w17", "w18", "w19", "w20",
        "w21", "w22", "w23", "w24", "w25", "w26", "w27", "w28", "w29", "w30", 
        "w31", "w32", "w33", "w34", "w35", "w36", "w37", "w38", "w39", "w40",
        "w41", "w42", "w43", "w44", "w45", "w46", "w47", "w48", "w49", "w50", "w51", "w52" };
    
    private static final int MONTHS_IN_YEAR = 12;

    private int id;
    
    private boolean reportingMonth = false; // TODO rename to lastMonth

    private boolean reportingBimonth = false; // TODO rename to lastBimonth

    private boolean reportingQuarter = false; // TODO rename to lastQuarter

    private boolean lastSixMonth = false;

    private boolean monthsThisYear = false;

    private boolean quartersThisYear = false;

    private boolean thisYear = false;

    private boolean monthsLastYear = false;

    private boolean quartersLastYear = false;

    private boolean lastYear = false;

    private boolean last5Years = false;

    private boolean last12Months = false;
    
    private boolean last3Months = false;

    private boolean last6BiMonths = false;

    private boolean last4Quarters = false;

    private boolean last2SixMonths = false;
    
    private boolean thisFinancialYear = false;
    
    private boolean lastFinancialYear = false;
    
    private boolean last5FinancialYears = false;
    
    private boolean last52Weeks = false;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public RelativePeriods()
    {
    }

    /**
     * @param reportingMonth   reporting month
     * @param reportingBimonth reporting bi-month
     * @param reportingQuarter reporting quarter
     * @param monthsThisYear   months this year
     * @param quartersThisYear quarters this year
     * @param thisYear         this year
     * @param monthsLastYear   months last year
     * @param quartersLastYear quarters last year
     * @param lastYear         last year
     * @param last5Years       last 5 years
     * @param last12Months     last 12 months
     * @param last3Months      last 3 months
     * @param last6BiMonths    last 6 bi-months
     * @param last4Quarters    last 4 quarters
     * @param last2SixMonths   last 2 six-months
     */
    public RelativePeriods( boolean reportingMonth, boolean reportingBimonth, boolean reportingQuarter, boolean lastSixMonth,
                            boolean monthsThisYear, boolean quartersThisYear, boolean thisYear,
                            boolean monthsLastYear, boolean quartersLastYear, boolean lastYear, boolean last5Years,
                            boolean last12Months, boolean last3Months, boolean last6BiMonths, boolean last4Quarters, boolean last2SixMonths,
                            boolean thisFinancialYear, boolean lastFinancialYear, boolean last5FinancialYears, boolean last52Weeks )
    {
        this.reportingMonth = reportingMonth;
        this.reportingBimonth = reportingBimonth;
        this.reportingQuarter = reportingQuarter;
        this.lastSixMonth = lastSixMonth;
        this.monthsThisYear = monthsThisYear;
        this.quartersThisYear = quartersThisYear;
        this.thisYear = thisYear;
        this.monthsLastYear = monthsLastYear;
        this.quartersLastYear = quartersLastYear;
        this.lastYear = lastYear;
        this.last5Years = last5Years;
        this.last12Months = last12Months;
        this.last3Months = last3Months;
        this.last6BiMonths = last6BiMonths;
        this.last4Quarters = last4Quarters;
        this.last2SixMonths = last2SixMonths;
        this.thisFinancialYear = thisFinancialYear;
        this.lastFinancialYear = lastFinancialYear;
        this.last5FinancialYears = last5FinancialYears;
        this.last52Weeks = last52Weeks;
    }

    // -------------------------------------------------------------------------
    // Logic
    // -------------------------------------------------------------------------

    /**
     * Sets all options to false.
     */
    public RelativePeriods clear()
    {
        this.reportingMonth = false;
        this.reportingBimonth = false;
        this.reportingQuarter = false;
        this.lastSixMonth = false;
        this.monthsThisYear = false;
        this.quartersThisYear = false;
        this.thisYear = false;
        this.monthsLastYear = false;
        this.quartersLastYear = false;
        this.lastYear = false;
        this.last5Years = false;
        this.last12Months = false;
        this.last3Months = false;
        this.last6BiMonths = false;
        this.last4Quarters = false;
        this.last2SixMonths = false;
        this.thisFinancialYear = false;
        this.lastFinancialYear = false;
        this.last5FinancialYears = false;
        this.last52Weeks = false;

        return this;
    }

    /**
     * Returns the period type for the option with the lowest frequency.
     *
     * @return the period type.
     */
    public PeriodType getPeriodType()
    {
        if ( isLast52Weeks() )
        {
            return PeriodType.getPeriodTypeByName( WeeklyPeriodType.NAME );
        }
        
        if ( isReportingMonth() || isLast12Months() || isLast3Months() )
        {
            return PeriodType.getPeriodTypeByName( MonthlyPeriodType.NAME );
        }

        if ( isReportingBimonth() || isLast6BiMonths() )
        {
            return PeriodType.getPeriodTypeByName( BiMonthlyPeriodType.NAME );
        }

        if ( isReportingQuarter() || isLast4Quarters() )
        {
            return PeriodType.getPeriodTypeByName( QuarterlyPeriodType.NAME );
        }

        if ( isLastSixMonth() || isLast2SixMonths() )
        {
            return PeriodType.getPeriodTypeByName( SixMonthlyPeriodType.NAME );
        }
        
        if ( isThisFinancialYear() || isLastFinancialYear() || isLast5FinancialYears() )
        {
            return PeriodType.getPeriodTypeByName( FinancialJulyPeriodType.NAME );
        }

        return PeriodType.getPeriodTypeByName( YearlyPeriodType.NAME );
    }

    /**
     * Return the name of the reporting period.
     *
     * @param date   the start date of the reporting period.
     * @param format the i18n format.
     * @return the name of the reporting period.
     */
    public String getReportingPeriodName( Date date, I18nFormat format )
    {
        Period period = getPeriodType().createPeriod( date );
        return format.formatPeriod( period );
    }

    /**
     * Return the name of the reporting period. The current date is set to
     * todays date minus one month.
     *
     * @param format the i18n format.
     * @return the name of the reporting period.
     */
    public String getReportingPeriodName( I18nFormat format )
    {
        Period period = getPeriodType().createPeriod( subtractMonth( 1, new Date() ) );
        return format.formatPeriod( period );
    }

    /**
     * Gets the PeriodType with the highest frequency from a list of Periods.
     */
    public PeriodType getHighestFrequencyPeriodType( List<Period> periods )
    {
        PeriodType periodType = null;
        
        if ( periods != null && !periods.isEmpty() )
        {            
            PeriodType lowestFrequencyOrder = periods.get( 0 ).getPeriodType();
            
            for ( Period period : periods )
            {
                if ( period.getPeriodType().getFrequencyOrder() < lowestFrequencyOrder.getFrequencyOrder() )
                {
                    lowestFrequencyOrder = period.getPeriodType();
                }
            }
            
            return lowestFrequencyOrder;
        }
        
        return periodType;
    }

    /**
     * Gets a list of Periods rewinded from current date.
     */
    public List<Period> getRewindedRelativePeriods()
    {
        return getRewindedRelativePeriods( null, null, null, false );
    }

    /**
     * Gets a list of Periods rewinded from current date.
     */
    public List<Period> getRewindedRelativePeriods( Integer rewindedPeriods, Date date, I18nFormat format, boolean dynamicNames )
    {
        List<Period> periods = getRelativePeriods();        
        PeriodType periodType = getHighestFrequencyPeriodType( periods );
        
        Date rewindedDate = periodType.getRewindedDate( date, rewindedPeriods );        
        rewindedDate = subtractMonth( 1, rewindedDate );
        
        return getRelativePeriods( rewindedDate, format, dynamicNames );
    }

    /**
     * Gets a list of Periods relative to current date.
     */
    public List<Period> getRelativePeriods()
    {
        return getRelativePeriods( null, null, false );
    }

    /**
     * Gets a list of Periods based on the given input and the state of this
     * RelativePeriods. The current date is set to todays date minus one month.
     *
     * @param format the i18n format.
     * @return a list of relative Periods.
     */
    public List<Period> getRelativePeriods( I18nFormat format, boolean dynamicNames )
    {
        return getRelativePeriods( null, format, dynamicNames );
    }

    /**
     * Gets a list of Periods based on the given input and the state of this
     * RelativePeriods.
     *
     * @param date the date representing now. If null the current date will be
     *        used and an interval based on the period type will be subtracted 
     *        from the date.
     * @param format the i18n format.
     * @return a list of relative Periods.
     */
    public List<Period> getRelativePeriods( Date date, I18nFormat format, boolean dynamicNames )
    {        
        date = date == null ? subtractMonth( 1, new Date() ) : date;
        
        List<Period> periods = new ArrayList<Period>();

        if ( isReportingMonth() )
        {
            periods.add( getRelativePeriod( new MonthlyPeriodType(), REPORTING_MONTH, date, dynamicNames, format ) );
        }

        if ( isReportingBimonth() )
        {
            periods.add( getRelativePeriod( new BiMonthlyPeriodType(), REPORTING_BIMONTH, date, dynamicNames, format ) );
        }

        if ( isReportingQuarter() )
        {
            periods.add( getRelativePeriod( new QuarterlyPeriodType(), REPORTING_QUARTER, date, dynamicNames, format ) );
        }

        if ( isLastSixMonth() )
        {
            periods.add( getRelativePeriod( new SixMonthlyPeriodType(), LAST_SIXMONTH, date, dynamicNames, format ) );
        }

        if ( isMonthsThisYear() )
        {
            periods.addAll( getRelativePeriodList( new MonthlyPeriodType(), MONTHS_THIS_YEAR, date, dynamicNames, format ) );
        }

        if ( isQuartersThisYear() )
        {
            periods.addAll( getRelativePeriodList( new QuarterlyPeriodType(), QUARTERS_THIS_YEAR, date, dynamicNames, format ) );
        }

        if ( isThisYear() )
        {
            periods.add( getRelativePeriod( new YearlyPeriodType(), THIS_YEAR, date, dynamicNames, format ) );
        }

        if ( isLast5Years() )
        {
            periods.addAll( getRollingRelativePeriodList( new YearlyPeriodType(), LAST_5_YEARS, date, dynamicNames, format ) );
        }

        if ( isLast12Months() )
        {
            periods.addAll( getRollingRelativePeriodList( new MonthlyPeriodType(), MONTHS_LAST_12, date, dynamicNames, format ) );
        }

        if ( isLast3Months() )
        {
            periods.addAll( getRollingRelativePeriodList( new MonthlyPeriodType(), MONTHS_LAST_12, date, dynamicNames, format ).subList( 9, 12 ) );
        }
        
        if ( isLast6BiMonths() )
        {
            periods.addAll( getRollingRelativePeriodList( new BiMonthlyPeriodType(), BIMONTHS_LAST_6, date, dynamicNames, format ) );
        }

        if ( isLast4Quarters() )
        {
            periods.addAll( getRollingRelativePeriodList( new QuarterlyPeriodType(), QUARTERS_THIS_YEAR, date, dynamicNames, format ) );
        }

        if ( isLast2SixMonths() )
        {
            periods.addAll( getRollingRelativePeriodList( new SixMonthlyPeriodType(), SIXMONHTS_LAST_2, date, dynamicNames, format ) );
        }

        if ( isThisFinancialYear() )
        {
            periods.add( getRelativePeriod( new FinancialJulyPeriodType(), THIS_FINANCIAL_YEAR, date, dynamicNames, format ) );
        }

        if ( isLast5FinancialYears() )
        {
            periods.addAll( getRollingRelativePeriodList( new FinancialJulyPeriodType(), LAST_5_FINANCIAL_YEARS, date, dynamicNames, format ) );
        }

        if ( isLast52Weeks() )
        {
            periods.addAll( getRollingRelativePeriodList( new WeeklyPeriodType(), WEEKS_LAST_52, date, dynamicNames, format ) );
        }
        
        date = subtractMonth( MONTHS_IN_YEAR, date );

        if ( isMonthsLastYear() )
        {
            periods.addAll( getRelativePeriodList( new MonthlyPeriodType(), MONTHS_LAST_YEAR, date, dynamicNames, format ) );
        }

        if ( isQuartersLastYear() )
        {
            periods.addAll( getRelativePeriodList( new QuarterlyPeriodType(), QUARTERS_LAST_YEAR, date, dynamicNames, format ) );
        }

        if ( isLastYear() )
        {
            periods.add( getRelativePeriod( new YearlyPeriodType(), LAST_YEAR, date, dynamicNames, format ) );
        }
        
        if ( isLastFinancialYear() )
        {
            periods.add( getRelativePeriod( new FinancialJulyPeriodType(), LAST_FINANCIAL_YEAR, date, dynamicNames, format ) );
        }
        
        return periods;
    }
    
    /**
     * Returns periods for the last 6 months based on the given period types.
     * 
     * @param periodTypes a set of period type represented as names.
     * @return a list of periods.
     */
    public List<Period> getLast6Months( Set<String> periodTypes )
    {
        List<Period> periods = new ArrayList<Period>();
        
        Date date = subtractMonth( 1, new Date() );
        Date weekDate = subtractWeeks( 1, new Date() );

        periods.addAll( periodTypes.contains( WeeklyPeriodType.NAME ) ? new WeeklyPeriodType().generateRollingPeriods( weekDate ).subList( 26, 52 ) : NO );
        periods.addAll( periodTypes.contains( MonthlyPeriodType.NAME ) ? new MonthlyPeriodType().generateRollingPeriods( date ).subList( 6, 12 ) : NO );
        periods.addAll( periodTypes.contains( BiMonthlyPeriodType.NAME ) ? new BiMonthlyPeriodType().generateRollingPeriods( date ).subList( 3, 6 ) : NO );
        periods.addAll( periodTypes.contains( QuarterlyPeriodType.NAME ) ? new QuarterlyPeriodType().generateRollingPeriods( date ).subList( 2, 4 ) : NO );
        periods.addAll( periodTypes.contains( SixMonthlyPeriodType.NAME ) ? new SixMonthlyPeriodType().generateRollingPeriods( date ).subList( 1, 2 ) : NO );        
        periods.addAll( periodTypes.contains( YearlyPeriodType.NAME ) ? new YearlyPeriodType().generateRollingPeriods( date ).subList( 4, 5 ) : NO );
        periods.addAll( periodTypes.contains( FinancialJulyPeriodType.NAME ) ? new FinancialJulyPeriodType().generateRollingPeriods( date ).subList( 4, 5 ) : NO );
        
        return periods;
    }

    /**
     * Returns periods for the last 6 to 12 months based on the given period types.
     * 
     * @param periodTypes a set of period type represented as names.
     * @return a list of periods.
     */
    public List<Period> getLast6To12Months( Set<String> periodTypes )
    {
        List<Period> periods = new ArrayList<Period>();

        Date date = subtractMonth( 1, new Date() );
        Date weekDate = subtractWeeks( 1, new Date() );

        periods.addAll( periodTypes.contains( WeeklyPeriodType.NAME ) ? new WeeklyPeriodType().generateRollingPeriods( weekDate ).subList( 0, 26 ) : NO );
        periods.addAll( periodTypes.contains( MonthlyPeriodType.NAME ) ? new MonthlyPeriodType().generateRollingPeriods( date ).subList( 0, 6 ) : NO );
        periods.addAll( periodTypes.contains( BiMonthlyPeriodType.NAME ) ? new BiMonthlyPeriodType().generateRollingPeriods( date ).subList( 0, 3 ) : NO );
        periods.addAll( periodTypes.contains( QuarterlyPeriodType.NAME ) ? new QuarterlyPeriodType().generateRollingPeriods( date ).subList( 0, 2 ) : NO );
        periods.addAll( periodTypes.contains( SixMonthlyPeriodType.NAME ) ? new SixMonthlyPeriodType().generateRollingPeriods( date ).subList( 0, 1 ) : NO );        
        periods.addAll( periodTypes.contains( YearlyPeriodType.NAME ) ? new YearlyPeriodType().generateRollingPeriods( date ).subList( 3, 4 ) : NO );
        periods.addAll( periodTypes.contains( FinancialJulyPeriodType.NAME ) ? new FinancialJulyPeriodType().generateRollingPeriods( date ).subList( 3, 4 ) : NO );
        
        return periods;
    }
    
    /**
     * Returns a list of relative periods. The name will be dynamic depending on
     * the dynamicNames argument. The short name will always be dynamic.
     *
     * @param periodType   the period type.
     * @param periodNames  the array of period names.
     * @param date         the current date.
     * @param dynamicNames indication of whether dynamic names should be used.
     * @param format       the I18nFormat.
     * @return a list of periods.
     */
    private List<Period> getRelativePeriodList( CalendarPeriodType periodType, String[] periodNames, Date date, boolean dynamicNames, I18nFormat format )
    {
        return getRelativePeriodList( periodType.generatePeriods( date ), periodNames, dynamicNames, format );
    }

    /**
     * Returns a list of relative rolling periods. The name will be dynamic depending on
     * the dynamicNames argument. The short name will always be dynamic.
     *
     * @param periodType   the period type.
     * @param periodNames  the array of period names.
     * @param date         the current date.
     * @param dynamicNames indication of whether dynamic names should be used.
     * @param format       the I18nFormat.
     * @return a list of periods.
     */
    private List<Period> getRollingRelativePeriodList( CalendarPeriodType periodType, String[] periodNames, Date date, boolean dynamicNames, I18nFormat format )
    {
        return getRelativePeriodList( periodType.generateRollingPeriods( date ), periodNames, dynamicNames, format );
    }

    /**
     * Returns a list of relative periods. The name will be dynamic depending on
     * the dynamicNames argument. The short name will always be dynamic.
     *
     * @param relatives    the list of periods.
     * @param periodNames  the array of period names.
     * @param dynamicNames indication of whether dynamic names should be used.
     * @param format       the I18nFormat.
     * @return a list of periods.
     */
    private List<Period> getRelativePeriodList( List<Period> relatives, String[] periodNames, boolean dynamicNames, I18nFormat format )
    {
        List<Period> periods = new ArrayList<Period>();

        int c = 0;
        
        for ( Period period : relatives )
        {
            periods.add( setName( period, periodNames[c++], dynamicNames, format ) );
        }

        return periods;
    }

    /**
     * Returns relative period. The name will be dynamic depending on the
     * dynamicNames argument. The short name will always be dynamic.
     *
     * @param periodType   the period type.
     * @param periodName   the period name.
     * @param date         the current date.
     * @param dynamicNames indication of whether dynamic names should be used.
     * @param format       the I18nFormat.
     * @return a list of periods.
     */
    private Period getRelativePeriod( CalendarPeriodType periodType, String periodName, Date date, boolean dynamicNames, I18nFormat format )
    {
        return setName( periodType.createPeriod( date ), periodName, dynamicNames, format );
    }

    /**
     * Sets the name and short name of the given Period.
     *
     * @param period       the period.
     * @param periodName   the period name.
     * @param dynamicNames indication of whether dynamic names should be used.
     * @param format       the I18nFormat.
     * @return a period.
     */
    private Period setName( Period period, String periodName, boolean dynamicNames, I18nFormat format )
    {
        period.setName( dynamicNames && format != null ? format.formatPeriod( period ) : periodName );
        period.setShortName( format != null ? format.formatPeriod( period ) : null );
        return period;
    }

    /**
     * Returns a date.
     *
     * @param months the number of months to subtract from the current date.
     * @param date the date representing now, ignored if null.
     * @return a date.
     */
    private Date subtractMonth( int months, Date date )
    {
        Calendar cal = PeriodType.createCalendarInstance( date );
        cal.add( Calendar.MONTH, (months * -1) );

        return cal.getTime();
    }

    /**
     * Returns a date.
     *
     * @param months the number of weeks to subtract from the current date.
     * @param date the date representing now, ignored if null.
     * @return a date.
     */
    public Date subtractWeeks( int weeks, Date date )
    {
        Calendar cal = PeriodType.createCalendarInstance( date );
        cal.add( Calendar.DAY_OF_YEAR, (weeks * -7) );

        return cal.getTime();
    }

    // -------------------------------------------------------------------------
    // Getters & setters
    // -------------------------------------------------------------------------

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    @JsonProperty( value = "lastMonth" )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isReportingMonth()
    {
        return reportingMonth;
    }

    public RelativePeriods setReportingMonth( boolean reportingMonth )
    {
        this.reportingMonth = reportingMonth;
        return this;
    }

    @JsonProperty( value = "lastBimonth" )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isReportingBimonth()
    {
        return reportingBimonth;
    }

    public RelativePeriods setReportingBimonth( boolean reportingBimonth )
    {
        this.reportingBimonth = reportingBimonth;
        return this;
    }

    @JsonProperty( value = "lastQuarter" )
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isReportingQuarter()
    {
        return reportingQuarter;
    }

    public RelativePeriods setReportingQuarter( boolean reportingQuarter )
    {
        this.reportingQuarter = reportingQuarter;
        return this;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isLastSixMonth()
    {
        return lastSixMonth;
    }

    public void setLastSixMonth( boolean lastSixMonth )
    {
        this.lastSixMonth = lastSixMonth;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isMonthsThisYear()
    {
        return monthsThisYear;
    }

    public RelativePeriods setMonthsThisYear( boolean monthsThisYear )
    {
        this.monthsThisYear = monthsThisYear;
        return this;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isQuartersThisYear()
    {
        return quartersThisYear;
    }

    public RelativePeriods setQuartersThisYear( boolean quartersThisYear )
    {
        this.quartersThisYear = quartersThisYear;
        return this;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isThisYear()
    {
        return thisYear;
    }

    public RelativePeriods setThisYear( boolean thisYear )
    {
        this.thisYear = thisYear;
        return this;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isMonthsLastYear()
    {
        return monthsLastYear;
    }

    public RelativePeriods setMonthsLastYear( boolean monthsLastYear )
    {
        this.monthsLastYear = monthsLastYear;
        return this;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isQuartersLastYear()
    {
        return quartersLastYear;
    }

    public RelativePeriods setQuartersLastYear( boolean quartersLastYear )
    {
        this.quartersLastYear = quartersLastYear;
        return this;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isLastYear()
    {
        return lastYear;
    }

    public RelativePeriods setLastYear( boolean lastYear )
    {
        this.lastYear = lastYear;
        return this;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isLast5Years()
    {
        return last5Years;
    }

    public RelativePeriods setLast5Years( boolean last5Years )
    {
        this.last5Years = last5Years;
        return this;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isLast12Months()
    {
        return last12Months;
    }

    public RelativePeriods setLast12Months( boolean last12Months )
    {
        this.last12Months = last12Months;
        return this;
    }
    
    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isLast3Months()
    {
        return last3Months;
    }

    public RelativePeriods setLast3Months( boolean last3Months )
    {
        this.last3Months = last3Months;
        return this;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isLast6BiMonths()
    {
        return last6BiMonths;
    }

    public RelativePeriods setLast6BiMonths( boolean last6BiMonths )
    {
        this.last6BiMonths = last6BiMonths;
        return this;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isLast4Quarters()
    {
        return last4Quarters;
    }

    public RelativePeriods setLast4Quarters( boolean last4Quarters )
    {
        this.last4Quarters = last4Quarters;
        return this;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isLast2SixMonths()
    {
        return last2SixMonths;
    }

    public RelativePeriods setLast2SixMonths( boolean last2SixMonths )
    {
        this.last2SixMonths = last2SixMonths;
        return this;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isThisFinancialYear()
    {
        return thisFinancialYear;
    }

    public RelativePeriods setThisFinancialYear( boolean thisFinancialYear )
    {
        this.thisFinancialYear = thisFinancialYear;
        return this;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isLastFinancialYear()
    {
        return lastFinancialYear;
    }

    public RelativePeriods setLastFinancialYear( boolean lastFinancialYear )
    {
        this.lastFinancialYear = lastFinancialYear;
        return this;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isLast5FinancialYears()
    {
        return last5FinancialYears;
    }

    public RelativePeriods setLast5FinancialYears( boolean last5FinancialYears )
    {
        this.last5FinancialYears = last5FinancialYears;
        return this;
    }

    @JsonProperty
    @JacksonXmlProperty( namespace = Dxf2Namespace.NAMESPACE )
    public boolean isLast52Weeks()
    {
        return last52Weeks;
    }

    public RelativePeriods setLast52Weeks( boolean last52Weeks )
    {
        this.last52Weeks = last52Weeks;
        return this;
    }

    // -------------------------------------------------------------------------
    // Equals, hashCode, and toString
    // -------------------------------------------------------------------------

    @Override
    public int hashCode()
    {
        final int prime = 31;

        int result = 1;

        result = prime * result + (reportingMonth ? 1 : 0);
        result = prime * result + (reportingBimonth ? 1 : 0);
        result = prime * result + (reportingQuarter ? 1 : 0);
        result = prime * result + (lastSixMonth ? 1 : 0);
        result = prime * result + (monthsThisYear ? 1 : 0);
        result = prime * result + (quartersThisYear ? 1 : 0);
        result = prime * result + (thisYear ? 1 : 0);
        result = prime * result + (monthsLastYear ? 1 : 0);
        result = prime * result + (quartersLastYear ? 1 : 0);
        result = prime * result + (lastYear ? 1 : 0);
        result = prime * result + (last5Years ? 1 : 0);
        result = prime * result + (last12Months ? 1 : 0);
        result = prime * result + (last3Months ? 1 : 0);
        result = prime * result + (last6BiMonths ? 1 : 0);
        result = prime * result + (last4Quarters ? 1 : 0);
        result = prime * result + (last2SixMonths ? 1 : 0);
        result = prime * result + (thisFinancialYear ? 1 : 0);
        result = prime * result + (lastFinancialYear ? 1 : 0);
        result = prime * result + (last5FinancialYears ? 1 : 0);
        result = prime * result + (last52Weeks ? 1 : 0);

        return result;
    }

    @Override
    public boolean equals( Object object )
    {
        if ( this == object )
        {
            return true;
        }

        if ( object == null )
        {
            return false;
        }

        if ( getClass() != object.getClass() )
        {
            return false;
        }

        final RelativePeriods other = (RelativePeriods) object;

        if ( !reportingMonth == other.reportingMonth )
        {
            return false;
        }

        if ( !reportingBimonth == other.reportingBimonth )
        {
            return false;
        }

        if ( !reportingQuarter == other.reportingQuarter )
        {
            return false;
        }

        if ( !lastSixMonth == other.last2SixMonths )
        {
            return false;
        }

        if ( !monthsThisYear == other.monthsThisYear )
        {
            return false;
        }

        if ( !quartersThisYear == other.quartersThisYear )
        {
            return false;
        }

        if ( !thisYear == other.thisYear )
        {
            return false;
        }

        if ( !monthsLastYear == other.monthsLastYear )
        {
            return false;
        }

        if ( !quartersLastYear == other.quartersLastYear )
        {
            return false;
        }

        if ( !lastYear == other.lastYear )
        {
            return false;
        }

        if ( !last5Years == other.last5Years )
        {
            return false;
        }

        if ( !last12Months == other.last12Months )
        {
            return false;
        }
        
        if ( !last3Months == other.last3Months )
        {
            return false;
        }

        if ( !last6BiMonths == other.last6BiMonths )
        {
            return false;
        }

        if ( !last4Quarters == other.last4Quarters )
        {
            return false;
        }

        if ( !last2SixMonths == other.last2SixMonths )
        {
            return false;
        }

        if ( !thisFinancialYear == other.thisFinancialYear )
        {
            return false;
        }

        if ( !lastFinancialYear == other.lastFinancialYear )
        {
            return false;
        }

        if ( !last5FinancialYears == other.last5FinancialYears )
        {
            return false;
        }

        if ( !last52Weeks == other.last52Weeks )
        {
            return false;
        }

        return true;
    }
}
