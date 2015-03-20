$(document).ready(function() {
	// Tab
	tabsLoad("systemConfiguration");
	
	var data_aid = getParameterByName('aid');
	
	if( data_aid != "" ){
		editValue(data_aid);
	}

	var cancelBtn = '#cancel';
	
	// Cancel Button
	$(cancelBtn).click(function() {    
		window.location.href = "alertListView";
	});

    $('#alertName').keypress(function(e){
      if(e.keyCode==13){
      	save();
	  }
    });

	
	// Save Alert
	$(this).on('click', '#save', function() {
		save();
	});		


	function save(){
	
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
			
	}



	// Query String
	function getParameterByName(name) {
		name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
		var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
			results = regex.exec(location.search);
		return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	}
	


	
	function editValue(id){
		$.ajax({
			url: "alertTypes/",
			type: "GET",
			success: function(data){
				  var returnedData = $.grep(data.payload, function(element, index){
				  return element.stateId == data_aid;
				});
				
				$("#alertName").val(returnedData[0].stateName); 				 
			},
			error: function (xhr, ajaxOptions, thrownError) {
				$("#errorMsg").removeClass("hide");
			}
		})
	}
	
});