/* JQuery */
$(document).ready(function() {
	tabsLoad("reports");	

    // JH - 508 Set the other page elements hide while modal show to help AT tools
    var modalBlock    = '.modal';
    var outerPageDiv  = '#outerPageDiv';

    $(modalBlock).on('shown.bs.modal', function (e) {
        $(outerPageDiv).attr('aria-hidden', 'true');
    });
    $(modalBlock).on('hidden.bs.modal', function (e) {
        $(outerPageDiv).attr('aria-hidden', 'false');
    });

    // Date Picker Start - Call picker and focus for 508         
    var fromDateGroup  = "#fromDateGroup";
    var toDateGroup    = "#toDateGroup";
    $(fromDateGroup).datepicker({
			showOn : 'button',
      format: 'mm/dd/yyyy',
      autoclose: true
		});

		$(toDateGroup).datepicker({
			showOn : 'button',
      format: 'mm/dd/yyyy',
			autoclose: true
		});
	
		$('.id_header_tooltip').tooltip({
			'placement': 'top'
	});
});