// -----------------------------------------------------------------------------
// View details
// -----------------------------------------------------------------------------

function showCatalogTypeAttributeDetails( catalogTypeAttributeId )
{
	jQuery.getJSON( 'getCatalogTypeAttributeDetails.action', { id: catalogTypeAttributeId },
		function ( json ) {
			setInnerHTML( 'nameField', json.catalogTypeAttribute.name );	
			setInnerHTML( 'descriptionField', json.catalogTypeAttribute.description );
			setInnerHTML( 'valueTypeField', json.catalogTypeAttribute.valueType );
			
			var mandatory = ( json.catalogTypeAttribute.mandatory == 'true') ? i18n_yes : i18n_no;
			setInnerHTML( 'mandatoryField', mandatory );
			
			showDetails();
	});
}
//-----------------------------------------------------------------------------
//Remove Patient Attribute
//-----------------------------------------------------------------------------
function removeCatalogTypeAttribute( catalogTypeAttributeId, name )
{
	removeItem( catalogTypeAttributeId, name, i18n_confirm_delete, 'removeCatalogTypeAttribute.action' );	
}
ATTRIBUTE_OPTION = 
{
	selectValueType : 	function (this_)
	{
		if ( jQuery(this_).val() == "COMBO" )
		{
			jQuery("#attributeComboRow").show();
			jQuery("#attributeNoCharRow").hide();
			if( jQuery("#attrOptionContainer").find("input").length ==0 ) 
			{
				ATTRIBUTE_OPTION.addOption();
				ATTRIBUTE_OPTION.addOption();
			}
		}else if ( jQuery(this_).val() == "DATE" )
		{
			jQuery("#attributeNoCharRow").hide();
		}else {
			jQuery("#attributeNoCharRow").show();
			jQuery("#attributeComboRow").hide();
		}
	},
	checkOnSubmit : function ()
	{
		if( jQuery("#valueType").val() != "COMBO" ) 
		{
			jQuery("#attrOptionContainer").children().remove();
			return true;
		}else {
			$("input","#attrOptionContainer").each(function(){ 
				if( !jQuery(this).val() )
					jQuery(this).remove();
			});
			if( $("input","#attrOptionContainer").length < 2)
			{
				alert(i118_at_least_2_option);
				return false;
			}else return true;
		}
	},
	addOption : function ()
	{
		jQuery("#attrOptionContainer").append(ATTRIBUTE_OPTION.createInput());
	},
	remove : function (this_, optionId)
	{
		
		if( jQuery(this_).siblings("input").attr("name") != "attrOptions")
		{
			jQuery.get("removeCatalogTypeAttributeOption.action?id="+optionId,function(data){
				if( data.response == "success")
				{
					jQuery(this_).parent().parent().remove();
					showSuccessMessage( data.message );
				}else 
				{
					showErrorMessage( data.message );
				}
			});
		}else
		{
			jQuery(this_).parent().parent().remove();
		}
	},
	removeInAddForm : function(this_)
	{
		jQuery(this_).parent().parent().remove();
	},
	createInput : function ()
	{
		return "<tr><td><input type='text' name='attrOptions' /><a href='#' style='text-decoration: none; margin-left:0.5em;' title='"+i18n_remove_option+"'  onClick='ATTRIBUTE_OPTION.remove(this,null)'>[ - ]</a></td></tr>";
	}
}