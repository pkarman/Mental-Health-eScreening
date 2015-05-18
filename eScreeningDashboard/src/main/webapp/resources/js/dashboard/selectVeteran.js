$(document).ready(function() {
	tabsLoad("createBattery");
    
	// JH - 508 Set the other page elements hide while modal show to help AT tools
	var modalBlock    = '.modal';
	var outerPageDiv  = '#outerPageDiv';
	
	$(modalBlock).on('shown.bs.modal', function (e) {
		$(outerPageDiv).attr('aria-hidden', 'true');
	});
	$(modalBlock).on('hidden.bs.modal', function (e) {
		$(outerPageDiv).attr('aria-hidden', 'false');
	});

	// t808 - Remove the local storage for mappingReturnURL in order to back to the regular redirection page
	localStorage.removeItem("mappingReturnURL");
});