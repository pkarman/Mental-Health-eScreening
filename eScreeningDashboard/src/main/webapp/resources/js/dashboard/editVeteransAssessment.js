$(document).ready(function() {
    // Load current tab
    tabsLoad("createBattery");
	
	// Load Selected Program 
	var $li = $('.battery_list').find('li');
	
	var selectedProgramId 	= "#selectedProgramId";
	var selectedClinicId 	= "#selectedClinicId";
	var clearAllBtn 		= ".clear_all";
	var clearAllModulesBtn 	= ".clear_all_modules"

	
	loadSelectedProgram();
	
	// Tri State Check
   	var $check = $(".tri input[type=checkbox]"), el;
	$check
   .data('checked',0)
   .click(function(e) {
        el = $(this);  
        switch(el.data('checked')) {
            // unchecked, going indeterminate
            case 0:
                el.data('checked',1);
                el.prop('indeterminate',true);
                break;
            
            // indeterminate, going checked
            case 1:
                el.data('checked',2);
                el.prop('indeterminate',false);
                el.prop('checked',true);                
                break;
            
            // checked, going unchecked
            default:  
                el.data('checked',0);
                el.prop('indeterminate',false);
                el.prop('checked',false);
        }
    });
	
	
	/* Need to clean - From edit Veteran Assessment */
	var module_values = [];
	var reset_check = false;
	
	$(".module_list").find(':checked').each(function() {
		   module_values.push($(this).val());
	});
	
	$(clearAllBtn).on("click", function(e) {
		e.preventDefault();
		clearAllSelectins();
	});

	$(clearAllModulesBtn).on("click", function(e) {
		e.preventDefault();
		clearAllModulesSelectins();
	});		
	
	$(".reset").on("click", function(e) {
		e.preventDefault();
		$('input:checkbox').removeAttr('checked');
		$("tr").removeClass("highlight");
		for ( var i in module_values ) {
			$("input:checkbox[value="+module_values[i]+"]").prop('checked', true);
		}
		reset_check = false;
	});
	
	$(".battery_list li span").on("click", function(e) {
		e.preventDefault();
		if (this.className.indexOf("highlight") > -1) {
			$("tr").removeClass("highlight");
			var classes = $(this).attr('class').split(' ');

			for(var i=0; i<classes.length; i++){
			  $("."+classes[i]).closest("tr").addClass("highlight"); 
			}	
		}else{
			//$('input:checkbox').removeAttr('checked');
			$("." + $(this).attr('class')).prop('checked', true);
			$("tr").removeClass("highlight");
			var classes = $(this).attr('class').split(' ');

			for(var i=0; i<classes.length; i++){
				  $("."+classes[i]).closest("tr").addClass("highlight"); 
			}
		}
	});
	
	
	


	
	/*
	$(".battery_list input").on("click", function(e) {
			$(".module_list tr input").prop('checked', false);
			
			if(reset_check == false){
				for ( var i in module_values ) {
					$("input:checkbox[value="+module_values[i]+"]").prop('checked', true);
				}
			}
			
			$(".module_list  ." + $(this).attr('class')).prop('checked', true);
			$(".module_list tr").removeClass("highlight");
			
			var classes = $(this).attr('class').split(' ');

			
			for(var i=0; i<classes.length; i++){
				  $(".module_list ."+classes[i]).closest("tr").addClass("highlight"); 
			}
	});*/
	
	
	/*
	var selectedProgramId = $("#selectedProgramId").val();
	$li.hide().filter(".program_" + selectedProgramId).show();
	if((selectedProgramId == "") || (typeof selectedProgramId == "undefined" )){
		$li.show();	
	}
	*/
	
	
	/* Need to clean - From edit Veteran Assessment */
	
		$(selectedProgramId).change(function() {
		$.ajax({
			url: 'editVeteranAssessment/programs/' + $(selectedProgramId).val() + '/clinics',
			dataType: 'json',
			type: 'GET',
			success: function(data) {
				$(selectedClinicId).empty(); // clear the current elements in select box
				$(selectedClinicId).append($('<option></option>').attr('value', '').text('Please Select a Clinic'));
				for (row in data) {
					$(selectedClinicId).append($('<option></option>').attr('value', data[row].stateId).text(data[row].stateName));
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//alert(errorThrown);
			}
		});

		$.ajax({
			url: 'editVeteranAssessment/programs/' + $(selectedProgramId).val() + '/noteTitles',
			dataType: 'json',
			type: 'GET',
			success: function(data) {
				$('#selectedNoteTitleId').empty();
				$('#selectedNoteTitleId').append($('<option></option>').attr('value', '').text('Please Select a Note Title'));
				for (row in data) {
					$('#selectedNoteTitleId').append($('<option></option>').attr('value', data[row].stateId).text(data[row].stateName));
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//alert(errorThrown);
			}
		});

		$.ajax({
			url: 'editVeteranAssessment/programs/' + $(selectedProgramId).val() + '/clinicians',
			dataType: 'json',
			type: 'GET',
			success: function(data) {
				$('#selectedClinicianId').empty();
				$('#selectedClinicianId').append($('<option></option>').attr('value', '').text('Please Select a Clinician'));
				for (row in data) {
					$('#selectedClinicianId').append($('<option></option>').attr('value', data[row].stateId).text(data[row].stateName));
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//alert(errorThrown);
			}
		});
		
		// Filter 
		$(".program_1").addClass("hide2");	        
	});
	


	
		

	$(".battery_list input").on("click", function(e) {
		 clearAllCheckbox();
		//$(".module_list tr input").prop('checked', false);
		// console.log($(this).attr("data-ref"));
		function check(x, i){
			if(x == 0){
				console.log("selectedSurveyIdList"+i);
				console.log("value "+ x);
				//element = "selectedSurveyIdList"+i;
				//document.getElementById(element).indeterminate = false;
				//$(element).prop("indeterminate", false);
			}
			if(x == 1){
				console.log("selectedSurveyIdList"+i);
				console.log("value "+ x);
				element = "selectedSurveyIdList"+i;
				document.getElementById(element).indeterminate = true;
				$(element).prop("indeterminate", true);    
			}
			if(x == 2){
				console.log("selectedSurveyIdList"+i);
				console.log("value "+ x);
				element = "#selectedSurveyIdList"+i;
				//document.getElementById(element).indeterminate = false;
				$(element).attr('checked', true);
			}
		}

		
		function dataFormat(data){
			data.replace('{','').replace('}','').replace(/\s/g,"").split(',');
			return data;			
		}
		var data = $(this).attr("data-ref")
		data = data.replace('{','').replace('}','').replace(/\s/g,"").split(',');
		
		console.log("data>>>>>>>>>> "+data);
		console.log("data length >>>>>>>>> "+data.length);
		
		for (i = 0; i < data.length; ++i) {   
			check(data[i], i+1);
		}


			
			
			/*
			if(reset_check == false){
				for ( var i in module_values ) {
					$("input:checkbox[value="+module_values[i]+"]").prop('checked', true);
				}
			}
			*/
			


			$(".module_list  ." + $(this).attr('class')).prop('checked', true);
			$(".module_list tr").removeClass("highlight");
			
			var classes = $(this).attr('class').split(' ');

			
			for(var i=0; i<classes.length; i++){
				  $(".module_list ."+classes[i]).closest("tr").addClass("highlight"); 
			}
			
	});
	
		
	// Filter Batteries that assigned to a specific program - JH
	function clearAllCheckbox() {
		var checkbox 	= 'input:checkbox';
		var tr 			= 'tr';
		
		$(checkbox).removeAttr('checked');
		$(tr).removeClass("highlight");
		$(checkbox).removeAttr('indeterminate');
		$(checkbox).prop("indeterminate", false); 
		reset_check = true;
	}
	
	function clearAllSelectins() {
		var checkbox 	= 'input:checkbox';
		var radio 		= 'input:radio';
		var tr 			= 'tr';
		
		$(checkbox).removeAttr('checked');
		$(radio).removeAttr('checked');
		$(tr).removeClass("highlight");
		$(checkbox).removeAttr('indeterminate');
		$(checkbox).prop("indeterminate", false); 
		reset_check = true;
	}
	
	function clearAllModulesSelectins() {
		var checkbox 	= 'input:checkbox';
		var tr 			= 'tr';

		$(checkbox).removeAttr('checked');
		$(tr).removeClass("highlight");
		reset_check = true;
	}
	
	$(selectedProgramId).on("change", function(e) {
		loadSelectedProgram();
	});
	

	
	function loadSelectedProgram(){
		var selectedProgramIdVal = $("#selectedProgramId").val();
		$li.hide().filter(".program_" + selectedProgramIdVal).show();
		//clearAllSelectins(); // Clear All Module Selections
		
		if((selectedProgramIdVal == "") || (typeof selectedProgramIdVal == "undefined" )){
			$li.show();	
		}
	}
	
	$(function() {
		$(selectedClinicId).change(function() {
		});
	});
			
	
});