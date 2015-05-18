$(document).ready(function() {
	$('#importVeteranButton').on('click', function(e) {
		$(this).hide();
	});
	// Redirect
	mappingReturnURL = localStorage.getItem("mappingReturnURL");
	if (mappingReturnURL == "assessmentSummary"){
		window.location.href = "assessmentSummary?vaid="+ localStorage.getItem("lsvid");
	}	  
});