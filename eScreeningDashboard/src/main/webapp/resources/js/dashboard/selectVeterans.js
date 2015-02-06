$(document).ready(function() {
    // Load current tab
    tabsLoad("selectVeteran");
	
    // Date Picker Start - Call picker and focus for 508         
    var fromAssessmentDateGroup  = "#fromAssessmentDateGroup";
    var toAssessmentDateGroup    = "#toAssessmentDateGroup";
    $(fromAssessmentDateGroup).datepicker({
			showOn : 'button',
      format: 'mm/dd/yyyy',
      autoclose: true
		});

		$(toAssessmentDateGroup).datepicker({
			showOn : 'button',
      format: 'mm/dd/yyyy',
			autoclose: true
		});
	
		$('.id_header_tooltip').tooltip({
			'placement': 'top'
		});
});