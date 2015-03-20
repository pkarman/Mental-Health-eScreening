$(document).ready(function() {
	// Tab
	tabsLoad("systemConfiguration");

	var cancelBtn = '#cancel';
	
	// Cancel Button
	$(cancelBtn).click(function() {    
		window.location.href = "alertListView";
	});
	
	// Save Alert
	$(this).on('click', '#save', function() {
		var alertNameInput = "#alertName";
		var valid = false;
		if ( $(alertNameInput).val() ){
			valid = true		
		}else{
			valid = false;
			$("#errorMsg").removeClass("hide");
		}
		
		if  (valid){
			var data_aid = getParameterByName('aid');
			var data_name = $("#alertName").val();
			data = {"stateId": data_aid , "stateName" : data_name };
			
			$.ajax({
				url: "alertTypes/update",
				type: "POST",
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(data),
				success: function(){
					window.location.href = "alertListView?msg=s";
					$("#successMsg").removeClass("hide");
				},
				error: function (xhr, ajaxOptions, thrownError) {
					$("#errorMsg").removeClass("hide");
				}
			})
		}
	});		




	// Query String
	function getParameterByName(name) {
		name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
		var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
			results = regex.exec(location.search);
		return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	}
	
	var aid = getParameterByName('aid');
	var hTitle = "#hTitle";
	if(aid == "" || typeof aid === 'undefined'){
		$(hTitle).html("Add Alert");
	}else if(aid != ""){
		$(hTitle).html("Edit Alert");
	}else{
		$(hTitle).html("Edit Alert");
	}
});