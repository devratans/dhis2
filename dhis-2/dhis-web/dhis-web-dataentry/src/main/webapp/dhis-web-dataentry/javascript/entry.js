/**
 * This file depends on form.js.
 * 
 * Format for the span/input identifiers for selectors:
 * 
 * {dataelementid}-{optioncomboid}-val // data value 
 * {dataelementid}-dataelement name of data element 
 * {optioncomboid}-optioncombo // name of category option combo 
 * {dataelementid}-cell // table cell for data element name
 * {dataelementid}-{optioncomboid}-min // min value for data value
 * {dataelementid}-{optioncomboid}-max // max value for data value
 */

// -----------------------------------------------------------------------------
// Save
// -----------------------------------------------------------------------------

var FORMULA_PATTERN = /\[.+?\]/g;
var SEPARATOR = '.';

function updateDataElementTotals()
{
	var currentTotals = [];
	
	$( 'input[name="total"]' ).each( function( index )
	{
		var targetId = $( this ).attr( 'dataelementid' );
		
		var totalValue = new Number();
		
		$( 'input[name="entryfield"]' ).each( function( index )
		{	
			var key = $( this ).attr( 'id' );
			var entryFieldId = key.substring( 0, key.indexOf( '-' ) );
			
			if ( targetId && $( this ).attr( 'value' ) && targetId == entryFieldId )
			{
				totalValue += new Number( $( this ).attr( 'value' ) );
			}
		} );
		
		$( this ).attr( 'value', totalValue );
	} );
}

/**
 * Updates all indicator input fields with the calculated value based on the
 * values in the input entry fields in the form.
 */
function updateIndicators()
{
    $( 'input[name="indicator"]' ).each( function( index )
    {
        var indicatorId = $( this ).attr( 'indicatorid' );

        var formula = indicatorFormulas[indicatorId];
        
        if ( isDefined( formula ) )
        {        
	        var expression = generateExpression( formula );
	
	        if ( expression )
	        {
		        var value = eval( expression );
		        
		        value = isNaN( value ) ? '-' : roundTo( value, 1 );
		
		        $( this ).attr( 'value', value );
	        }
        }
        else
        {
        	log( 'Indicator does not exist in form: ' + indicatorId );
        }
    } );
}

/**
 * Parses the expression and substitues the operand identifiers with the value
 * of the corresponding input entry field.
 */
function generateExpression( expression )
{
    var matcher = expression.match( FORMULA_PATTERN );

    for ( k in matcher )
    {
        var match = matcher[k];

        // Remove brackets from expression to simplify extraction of identifiers

        var operand = match.replace( /[\[\]]/g, '' );

        var dataElementId = operand.substring( 0, operand.indexOf( SEPARATOR ) );
        var categoryOptionComboId = operand.substring( operand.indexOf( SEPARATOR ) + 1, operand.length );

        var fieldId = '#' + dataElementId + '-' + categoryOptionComboId + '-val';

        var value = '0';
        
        if ( $( fieldId ).length )
        {
            value = $( fieldId ).val() ? $( fieldId ).val() : '0';
        }

        expression = expression.replace( match, value );
        
        // TODO signed numbers
    }

    return expression;
}

function saveDynamicVal( code, optionComboId, fieldId )
{
    var dataElementId = $( '#' + code + '-dynselect option:selected' ).val();
    
    if ( !isDefined( dataElementId ) || dataElementId == -1 )
    {
    	log( 'There is no select list in form or no option selected for code: ' + code );
    	return;
    }
    
    saveVal( dataElementId, optionComboId, fieldId );
}

function saveVal( dataElementId, optionComboId, fieldId )
{
	dataElementId = parseInt( dataElementId );
	optionComboId = parseInt( optionComboId );
    fieldId = '#' + fieldId;
	
    var dataElementName = getDataElementName( dataElementId );
    var value = $( fieldId ).val();
    var type = getDataElementType( dataElementId );

    $( fieldId ).css( 'background-color', COLOR_YELLOW );

    var periodId = $( '#selectedPeriodId' ).val();

	if ( value == null )
	{
		value = '';
	}

    if ( value != '' )
    {
        if ( type == 'string' || type == 'int' || type == 'number' || type == 'positiveNumber' || type == 'negativeNumber' )
        {
            if ( value.length > 255 )
            {
                return alertField( fieldId, i18n_value_too_long + ': ' + dataElementName );
            }
            if ( type == 'int' && !isInt( value ) )
            {
                return alertField( fieldId, i18n_value_must_integer + ': ' + dataElementName );
            }
            if ( type == 'number' && !isRealNumber( value ) )
            {
                return alertField( fieldId, i18n_value_must_number + ': ' + dataElementName );
            }
            if ( type == 'positiveNumber' && !isPositiveInt( value ) )
            {
                return alertField( fieldId, i18n_value_must_positive_integer + ': ' + dataElementName );
            }
            if ( type == 'negativeNumber' && !isNegativeInt( value ) )
            {
                return alertField( fieldId, i18n_value_must_negative_integer + ': ' + dataElementName );
            }
            if ( isValidZeroNumber( value ) )
            {
                // If value = 0 and zero not significant for data element, skip

                if ( significantZeros.indexOf( dataElementId ) == -1 )
                {
                    $( fieldId ).css( 'background-color', COLOR_GREEN );
                    return false;
                }
            }

            var minString = currentMinMaxValueMap[dataElementId + '-' + optionComboId + '-min'];
            var maxString = currentMinMaxValueMap[dataElementId + '-' + optionComboId + '-max'];

            if ( minString && maxString ) // TODO if only one exists?
            {
                var valueNo = new Number( value );
                var min = new Number( minString );
                var max = new Number( maxString );

                if ( valueNo < min )
                {
                    var valueSaver = new ValueSaver( dataElementId, optionComboId, currentOrganisationUnitId, periodId,
                            value, fieldId, COLOR_ORANGE );
                    valueSaver.save();

                    window.alert( i18n_value_of_data_element_less + ': ' + min + '\n\n' + dataElementName );
                    return;
                }

                if ( valueNo > max )
                {
                    var valueSaver = new ValueSaver( dataElementId, optionComboId, currentOrganisationUnitId, periodId,
                            value, fieldId, COLOR_ORANGE );
                    valueSaver.save();

                    window.alert( i18n_value_of_data_element_greater + ': ' + max + '\n\n' + dataElementName );
                    return;
                }
            }
        }
    }

    var valueSaver = new ValueSaver( dataElementId, optionComboId, 
    	currentOrganisationUnitId, periodId, value, fieldId, COLOR_GREEN );
    valueSaver.save();

    updateIndicators(); // Update indicators for custom form
    updateDataElementTotals(); // Update data element totals for custom forms
}

function saveBoolean( dataElementId, optionComboId, fieldId )
{
	dataElementId = parseInt( dataElementId );
	optionComboId = parseInt( optionComboId );
    fieldId = '#' + fieldId;
    
    var value = $( fieldId + ' option:selected' ).val();

    $( fieldId ).css( 'background-color', COLOR_YELLOW );

    var periodId = $( '#selectedPeriodId' ).val();

    var valueSaver = new ValueSaver( dataElementId, optionComboId, 
    	currentOrganisationUnitId, periodId, value, fieldId, COLOR_GREEN );
    valueSaver.save();
}

/**
 * Supportive method.
 */
function alertField( fieldId, alertMessage )
{
    $( fieldId ).css( 'background-color', COLOR_YELLOW );
    $( fieldId ).select();
    $( fieldId ).focus();    
    window.alert( alertMessage );

    return false;
}

// -----------------------------------------------------------------------------
// Saver objects
// -----------------------------------------------------------------------------

function ValueSaver( dataElementId, optionComboId, organisationUnitId, periodId, value, fieldId, resultColor )
{
    var dataValue = {
        'dataElementId' : dataElementId,
        'optionComboId' : optionComboId,
        'organisationUnitId' : organisationUnitId,
        'periodId' : periodId,
        'value' : value,
    };

    this.save = function()
    {
        storageManager.saveDataValue( dataValue );

        $.ajax( {
            url: 'saveValue.action',
            data: dataValue,
            dataType: 'json',
            success: handleSuccess,
            error: handleError
        } );
    };

    function handleSuccess( json )
    {
        var code = json.c;

        if ( code == 0 ) // Value successfully saved on server
        {
        	storageManager.clearDataValueJSON( dataValue );
            markValue( fieldId, resultColor );
        }
        else if ( code == 2 )
        {
            markValue( fieldId, COLOR_RED );
            window.alert( i18n_saving_value_failed_dataset_is_locked );
        }
        else // Server error during save
        {
            markValue( fieldId, COLOR_RED );
            window.alert( i18n_saving_value_failed_status_code + '\n\n' + code );
        }
    }

    function handleError( jqXHR, textStatus, errorThrown )
    {
        setHeaderMessage( i18n_offline_notification );
        markValue( fieldId, resultColor );
    }

    function markValue( fieldId, color )
    {
        $( fieldId ).css( 'background-color', color );
    }
}
