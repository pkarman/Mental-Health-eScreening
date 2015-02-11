$(document).ready(function() {
	// Load tab
	tabsLoad("createBattery");
  
	// date time picker config
	$('.form_date').datetimepicker({
		weekStart : 1,
		todayBtn : 0,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		forceParse : 0,
		startDate: '-140y'
	});
	
	
});