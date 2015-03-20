$(document).ready(function() {
	// Tab
	tabsLoad("systemConfiguration");
	var aid = getParameterByName('aid');
	
	// Error Messages
	var errorMessageAlertReq 	= "Alert name is required";
	var errorMessageAlertExist 	= "Alert name already exist";
	var errorMessageLoadingData = "Error Loading Data";
	
	// Labels
	var labelAddAlert 	= "Add Alert";
	var labelEditAlert 	= "Edit Alert";
	
	if( aid != "" ){
		editValue(aid);
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
		validateValue($("#alertName").val());
	});		


	function save(){
	
		var alertNameInput = "#alertName";
		var valid = false;
		if ( $(alertNameInput).val() ){
			valid = true		
		}else{
			valid = false;
			$("#errorMsg").removeClass("hide");
			$("#errorMsg").html(errorMessageAlertReq);
		}
		
		if  (valid){
			var aid = getParameterByName('aid');
			var data_name = $("#alertName").val();
			data = {"stateId": aid , "stateName" : data_name };
			
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
					$("#errorMsg").html(errorMessageAlertReq);
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
	
	// Edit Values
	function editValue(id){
		aid = id;
		$.ajax({
			url: "alertTypes/",
			type: "GET",
			success: function(data){
				  var returnedData = $.grep(data.payload, function(element, index){
				  return element.stateId == aid;
				});
				
				$("#alertName").val(returnedData[0].stateName); 				 
			},
			error: function (xhr, ajaxOptions, thrownError) {
				$("#errorMsg").removeClass("hide");
				$("#errorMsg").html(errorMessageAlertReq);
			}
		})
	}

	// Validate Values
	function validateValue(name){
		stateName = name;
		$.ajax({
			url: "alertTypes/",
			type: "GET",
			success: function(data){
				

				var recordExist = false;
				$.each(data.payload, function(i, v) {
					if (v.stateName == stateName) {
						recordExist =  true;
					}
				});
				if(recordExist == false){
					save();
				}else{
					$("#errorMsg").removeClass("hide");
					$("#errorMsg").html(errorMessageAlertExist);
				}
			},
			error: function (xhr, ajaxOptions, thrownError) {
				$("#errorMsg").removeClass("hide");
				$("#errorMsg").html(errorMessageLoadingData);
			}
		})
	}

	// Page Title add / edit
	var hTitle = "#hTitle";
	if(aid == "" || typeof aid === 'undefined'){
		$(hTitle).html(labelAddAlert);
	}else if(aid != ""){
		$(hTitle).html(labelEditAlert);
	}else{
		$(hTitle).html(labelEditAlert);
	}
});