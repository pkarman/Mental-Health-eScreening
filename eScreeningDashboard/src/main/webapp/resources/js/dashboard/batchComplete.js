$(document).ready(function() {
    // Load current tab
    tabsLoad("createBattery");
	
 	$("#dashbaordBtn").click(function() {
		window.location = "assessmentDashboard";	
    });
	
 	$("#returnToAppointmentsBtn").click(function() {
		window.location = "selectVeterans";	
    });
});