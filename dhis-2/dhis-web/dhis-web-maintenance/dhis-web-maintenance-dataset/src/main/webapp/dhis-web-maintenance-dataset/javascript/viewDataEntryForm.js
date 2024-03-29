
var currentDynamicElementCode = null;
var currentCategoryComboId = null;
var currentCategoryComboName = null;

$( document ).ready( function() {
	
	leftBar.hideAnimated();

	$("#selectionDialog").dialog({
		minWidth: 595,
		minHeight: 263,
		position: [($("body").width() - 555) - 50, 50],
		zIndex: 10000
	});

	$("#selectionDialog").parent().bind("resize", function(e) {
		var dialog = $("#selectionDialog");
		var dataElementSelector = $("#dataElementSelector");
		var totalSelector = $("#totalSelector");
		var indicatorSelector = $("#indicatorSelector");
		var dynamicElementSelector = $("#dynamicElementSelector");

		dataElementSelector.height( dialog.height() - 97 );
		totalSelector.height( dialog.height() - 97 );
		indicatorSelector.height( dialog.height() - 97 );
		dynamicElementSelector.height( dialog.height() - 120 );
	});

	$(":button").button();
	$(":submit").button();

	$("#selectionDialog").bind("dialogopen", function(event, ui) {
		$("#insertDataElementsButton").button("disable");
	});

	$("#selectionDialog").bind("dialogclose", function(event, ui) {
		$("#insertDataElementsButton").button("enable");
	});

	$("#insertDataElementsButton").button("disable");

	$("#insertDataElementsButton").click(function() {
		$("#selectionDialog").dialog("open");
	});
	
	$("#startButton").button("option", "icons", { primary: "ui-icon-triangle-1-e" });
	$("#startButton").click( showDynamicElementInsert );
	$("#doneButton").click( showDynamicElementSelect );
	
	$("#insertDynamicElementButton").button("option", "icons", { primary: "ui-icon-plusthick" });
	$("#insertDynamicElementButton").click( insertDropDownList );
	
	showDataElements();

	$("#dataElementsButton").addClass("ui-state-active2");

	$("#dataElementsButton").click(function() {
		$("#dataElementsButton").addClass("ui-state-active2");
		$("#totalsButton").removeClass("ui-state-active2");
		$("#indicatorsButton").removeClass("ui-state-active2");
		$("#dynamicElementsButton").removeClass("ui-state-active2");

		showDataElements();
	});
	
	$("#totalsButton").click(function() {		
		$("#dataElementsButton").removeClass("ui-state-active2");
		$("#totalsButton").addClass("ui-state-active2");
		$("#indicatorsButton").removeClass("ui-state-active2");
		$("#dynamicElementsButton").removeClass("ui-state-active2");
		
		showTotals();
	});

	$("#indicatorsButton").click(function() {	
		$("#dataElementsButton").removeClass("ui-state-active2");
		$("#totalsButton").removeClass("ui-state-active2");
		$("#indicatorsButton").addClass("ui-state-active2");
		$("#dynamicElementsButton").removeClass("ui-state-active2");

		showIndicators();
	});

	$("#dynamicElementsButton").click(function() {	
		$("#dataElementsButton").removeClass("ui-state-active2");
		$("#totalsButton").removeClass("ui-state-active2");
		$("#indicatorsButton").removeClass("ui-state-active2");
		$("#dynamicElementsButton").addClass("ui-state-active2");

		showDynamicElements();
	});

	$("#insertButton").click(function() {
		if( $("#dataElementsTab").is(":visible") ) {
			insertDataElement();
		} 
		else if( $("#totalsTab").is(":visible") ) {
			insertTotal();
		}
		else if( $("#indicatorsTab").is(":visible") ) {
			insertIndicator();
		}
		else if( $("#dynamicElementsTab").is(":visible") ) {
			insertDynamicElement();
		}
	});

	$("#insertButton").button("option", "icons", { primary: "ui-icon-plusthick" });
	$("#saveButton").button("option", "icons", { primary: "ui-icon-disk" });
	$("#saveCloseButton").button("option", "icons", { primary: "ui-icon-disk" });
	$("#insertDataElementsButton").button("option", "icons", { primary: "ui-icon-newwin" });
	$("#cancelButton").button("option", "icons", { primary: "ui-icon-cancel" });
	$("#delete").button("option", "icons", { primary: "ui-icon-trash" });
	
	$("#dataElementsFilterButton").button({
		icons: {
			primary: "ui-icon-search"
		},
		text: false
	}).click(function() {
		filterSelectList( 'dataElementSelector', $("#dataElementsFilterInput").val() );
	});
	
	$("#totalsFilterButton").button({
		icons: {
			primary: "ui-icon-search"
		},
		text: false
	}).click(function() {
		filterSelectList( 'totalSelector', $("#totalsFilterInput").val() );
	});
	
	$("#indicatorsFilterButton").button({
		icons: {
			primary: "ui-icon-search"
		},
		text: false
	}).click(function() {
		filterSelectList( 'indicatorSelector', $("#indicatorsFilterInput").val() );
	});	

	$("#dataElementSelector").dhisAjaxSelect({
		source: "../dhis-web-commons-ajax-json/getDataElementOperands.action",
		iterator: "operands",
		handler: function(item) {
			var option = jQuery("<option />");
			option.text( item.operandName );
			option.data( "dataelement-id", item.dataElement.id );
			option.data( "dataelement-name", item.dataElement.name );
			option.data( "dataelement-type", item.dataElement.type );
			option.data( "optioncombo-id", item.categoryOptionCombo.id );
			option.data( "optioncombo-name", item.categoryOptionCombo.name );
			option.dblclick(insertDataElement);

			return option;
		},
		params: {
			dataSetId: dataSetId
		}
	});

	$("#totalSelector").dhisAjaxSelect({
		source: "../dhis-web-commons-ajax-json/getDataElements.action",
		iterator: "dataElements",
		handler: function(item) {
			var option = jQuery("<option />");
			option.text( item.name );
			option.data( "id", item.id );
			option.dblclick(insertTotal);

			return option;
		},
		params: {
			dataSetId: dataSetId
		}
	});

	$("#indicatorSelector").dhisAjaxSelect({
		source: "../dhis-web-commons-ajax-json/getIndicators.action",
		iterator: "indicators",
		handler: function(item) {
			var option = jQuery("<option />");
			option.text( item.name );
			option.data("id", item.id);
			option.dblclick(insertIndicator);

			return option;
		},
		params: {
			dataSetId: dataSetId
		}
	});
	
	$("#dynamicElementSelector").dblclick(insertDynamicElement);
	
	if( autoSave == 'true' )
	{
		window.setTimeout( "validateDataEntryFormTimeout( false );", 60000 );
	}
});

function showDataElements() {
	$("#dataElementsTab").show();
	$("#dataElementsFilter").show();
	$("#totalsTab").hide();
	$("#totalsFilter").hide();
	$("#indicatorsTab").hide();
	$("#indicatorsFilter").hide();
	$("#dynamicElementsTab").hide();
}

function showTotals() {
	$("#dataElementsTab").hide();
	$("#dataElementsFilter").hide();
	$("#totalsTab").show();
	$("#totalsFilter").show();
	$("#indicatorsTab").hide();
	$("#indicatorsFilter").hide();
	$("#dynamicElementsTab").hide();
}

function showIndicators() {
	$("#dataElementsTab").hide();
	$("#dataElementsFilter").hide();
	$("#totalsTab").hide();
	$("#totalsFilter").hide();
	$("#indicatorsTab").show();
	$("#indicatorsFilter").show();
	$("#dynamicElementsTab").hide();
}

function showDynamicElements() {
	$("#dataElementsTab").hide();
	$("#dataElementsFilter").hide();
	$("#totalsTab").hide();
	$("#totalsFilter").hide();
	$("#indicatorsTab").hide();
	$("#indicatorsFilter").hide();
	$("#dynamicElementsTab").show();	
}

function filterSelectList( select_id, filter )
{
	var select_selector = "#" + select_id;
	var select_hidden_id = select_id + "_ghost";
	var select_hidden_selector = "#" + select_hidden_id;

	var $select_options = $(select_selector).find("option"); 
	var $select_hidden_options = $(select_hidden_selector).find("option"); 

	if( $(select_hidden_selector).length === 0 ) {
		var $element = $("<select multiple=\"multiple\" id=\"" + select_hidden_id + "\" style=\"display: none\"></select>");
		$element.appendTo( "body" );
	}

	$select_options.each(function() {
		var val = $(this).val().toLowerCase();

		if(val.indexOf( filter ) == -1) {
			var $option = $(this).detach();
			$option.appendTo( select_hidden_selector );
		}
	});

	$select_hidden_options.each(function() {
		var val = $(this).val().toLowerCase();

		if(val.indexOf( filter ) != -1) {
			var $option = $(this).detach();
			$option.appendTo( select_selector );
		}
	});

	var $sorted = $(select_selector).find("option").sort(function(a, b) {
		var idxa = +$(a).data("idx");
		var idxb = +$(b).data("idx");

		if(idxa > idxb) return 1;
		else if(idxa < idxb) return -1;
		else return 0;
	});
	
	$(select_selector).empty();
	$sorted.appendTo( select_selector );
}

function insertDataElement() {
	var oEditor = $("#designTextarea").ckeditorGet();
	var $option = $("#dataElementSelector option:selected");

	if( $option.length !== 0 ) {
		var dataElementId = $option.data("dataelement-id");
		var dataElementName = $option.data("dataelement-name");
		var dataElementType = $option.data("dataelement-type");
		var optionComboId = $option.data("optioncombo-id");
		var optionComboName = $option.data("optioncombo-name");
	
		var titleValue = dataElementId + " - " + dataElementName + " - "
				+ optionComboId + " - " + optionComboName + " - " + dataElementType;
	
		var displayName = "[ " + dataElementName + " " + optionComboName + " ]";
		var dataEntryId = dataElementId + "-" + optionComboId + "-val";
	
		var html = "";
		
		var greyedField = $( "#greyedField" ).is( ":checked" ) ? " disabled=\"disabled\"" : "";
	
		if (dataElementType == "bool") {
			html = "<input title=\"" + titleValue
					+ "\" value=\"" + displayName + "\" id=\"" + dataEntryId
					+ "\" style=\"width:7em;text-align:center\"/>";
		} 
		else {
			html = "<input title=\"" + titleValue
					+ "\" value=\"" + displayName + "\" id=\"" + dataEntryId
					+ "\" style=\"width:7em;text-align:center\"" + greyedField + "/>";
		}
	
		if (!checkExisted(dataEntryId)) {
			oEditor.insertHtml(html);
		} else {
			setHeaderDelayMessage( i18n_dataelement_already_inserted );
		}
	} else {
		setHeaderDelayMessage( i18n_no_dataelement_was_selected );
	}
}

function insertTotal() {
	var oEditor = $("#designTextarea").ckeditorGet();
	var $option = $("#totalSelector option:selected");
	
	if( $option.length !== 0 )
	{
		var id = $option.data("id");
		var title = $option.val();
		var dataEntryId = 'total' + id;		
		var template = '<input id="' + dataEntryId + '" name="total" value="[' + title + ']" title="' + title + '" dataelementid="' + id + '" style="width:7em;text-align:center;" readonly="readonly" />';
		
		if(!checkExisted(dataEntryId)) {
			oEditor.insertHtml( template );
		} else {
			setHeaderDelayMessage( i18n_dataelement_already_inserted );
		}
	} else {
		setHeaderDelayMessage( i18n_no_dataelement_was_selected );
	}
}

function insertIndicator() {
	var oEditor = $("#designTextarea").ckeditorGet();
	var $option = $("#indicatorSelector option:selected");

	if( $option.length !== 0 ) {
		var id = $option.data("id");
		var title = $option.val();
		var template = '<input id="indicator' + id + '" value="[ ' + title + ' ]" title="' + title + '" name="indicator" indicatorid="' + id + '" style="width:7em;text-align:center;" readonly="readonly" />';

		if(!checkExisted("indicator" + id)) {
			oEditor.insertHtml( template );
		} else {
			setHeaderDelayMessage( i18n_indicator_already_inserted );
		}
	} else {
		setHeaderDelayMessage( i18n_no_indicator_was_selected );
	}
}

/**
 * A unique code is used to associate the data element drop down with the input
 * fields for each category option combo.  The format for select / drop down list
 * is:
 * 
 * id="<unique code>-dynselect" dyncselect="<category combo id>"
 */
function insertDropDownList() {
	var oEditor = $("#designTextarea").ckeditorGet();
	if ( currentDynamicElementCode && currentCategoryComboId ) {
		var id = currentDynamicElementCode + "-dynselect";
		var template = '<input id="' + id + '" dynselect="' + currentCategoryComboId + '" value="[ ' + currentCategoryComboName + ' ]" title="' + currentCategoryComboName + '" style="width:15em;" />';
		oEditor.insertHtml( template );
	}
}

/**
 * A unique code is used to associate the data element drop down with the input
 * fields for each category option combo. The format for input field identifier 
 * is:
 * 
 * id="<unique code>-<category option combo id>-dyninput"
 */
function insertDynamicElement() {
	var oEditor = $("#designTextarea").ckeditorGet();
	var $option = $("#dynamicElementSelector option:selected");
	
	if( $option.length !== 0 ) {
		var categoryOptionComboId = $option.val();
		var categoryOptionComboName = $option.text();
		var id = currentDynamicElementCode + "-" + categoryOptionComboId + "-dyninput";
		
		var template = '<input id="' + id + '" code="' + currentDynamicElementCode + '" value="[ ' + categoryOptionComboName + ' ]" title="' + categoryOptionComboName + '" style="width:7em;text-align:center;" />';
		oEditor.insertHtml( template );
	}
}

function showDynamicElementSelect() {
	$("#dynamicElementSelect").show();
	$("#dynamicElementInsert").hide();	
}

function showDynamicElementInsert() {
	$("#dynamicElementSelect").hide();
	$("#dynamicElementInsert").show();
	
	var categoryComboId = $("#categoryComboSelect option:selected").val();
	var categoryComboName = $("#categoryComboSelect option:selected").text();

	currentDynamicElementCode = getRandomCode();
	currentCategoryComboId = categoryComboId;
	currentCategoryComboName = categoryComboName;
	
	clearListById( "dynamicElementSelector" );
	
	var optionCombos = $.getJSON( "../dhis-web-commons-ajax-json/getCategoryOptionCombos.action?categoryComboId=" + categoryComboId, function( json ) {
		$.each( json.categoryOptionCombos, function( index, value ) {
			addOptionById( "dynamicElementSelector", value.id, value.name );
		} );
	} );	
}

function checkExisted(id) {
	var result = false;
	var html = $("#designTextarea").ckeditorGet().getData();
	var input = $(html).find("select, :text");
	input.each(function(i, item) {
		if (id == item.id)
			result = true;
	});

	return result;
}
