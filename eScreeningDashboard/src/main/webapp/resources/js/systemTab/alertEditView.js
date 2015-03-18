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
		var data_aid = $(this).attr("data-aid");
		var data_name = $("#alertName").val();
		alert("Saving" + data_name);
		data = "{stateId:'" + data_aid + "', stateName:'"+ data_name +"'}";
		
		$.ajax({
			url: "alertTypes/update",
			type: "POST",
			dataType: "json",
			data: JSON.stringify(data),
			success: function(){
				window.location.href = "alertListView?msg=s";
				$("#successMsg").removeClass("hide");
        	},
			error: function (xhr, ajaxOptions, thrownError) {
				$("#errorMsg").removeClass("hide");
 		    }
		})
	});
		
});