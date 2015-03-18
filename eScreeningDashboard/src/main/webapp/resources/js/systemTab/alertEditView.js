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
		$.ajax({
			url: "alertTypes/update",
			type: "POST",
			data: "id=" + data_aid + "&name=" + data_name,
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