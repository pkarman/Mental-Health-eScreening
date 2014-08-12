$(document).ready(function() {
		
		var module_values = [];
		var reset_check = false;
		
		$(".module_list").find(':checked').each(function() {
			   module_values.push($(this).val());
		});
		
		$(".clear_all").on("click", function(e) {
			e.preventDefault();
			clearAllSelectins();
		});

		$(".clear_all_modules").on("click", function(e) {
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
		});
		
		
		var selectedProgramId = $("#selectedProgramId").val();
		$li.hide().filter(".program_" + selectedProgramId).show();
		if((selectedProgramId == "") || (typeof selectedProgramId == "undefined" )){
			$li.show();	
		}
		
	});

	$(function() {
	    $('#selectedProgramId').change(function() {
	        $.ajax({
	            url: 'editVeteranAssessment/programs/' + $('#selectedProgramId').val() + '/clinics',
	            dataType: 'json',
	            type: 'GET',
	            success: function(data) {
	                $('#selectedClinicId').empty(); // clear the current elements in select box
                	$('#selectedClinicId').append($('<option></option>').attr('value', '').text('Please Select a Clinic'));
	                for (row in data) {
	                    $('#selectedClinicId').append($('<option></option>').attr('value', data[row].stateId).text(data[row].stateName));
	                }
	            },
	            error: function(jqXHR, textStatus, errorThrown) {
	                //alert(errorThrown);
	            }
	        });

	        $.ajax({
	            url: 'editVeteranAssessment/programs/' + $('#selectedProgramId').val() + '/noteTitles',
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
	            url: 'editVeteranAssessment/programs/' + $('#selectedProgramId').val() + '/clinicians',
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
	});

	// Filter Batteries that assigned to a specific program - JH
	function clearAllSelectins() {
		$('input:checkbox').removeAttr('checked');
		$('input:radio').removeAttr('checked');
		$("tr").removeClass("highlight");
		reset_check = true;
	}
	
	function clearAllModulesSelectins() {
		$('input:checkbox').removeAttr('checked');
		$("tr").removeClass("highlight");
		reset_check = true;
	}
	
	var $li = $('.battery_list').find('li');
	$("#selectedProgramId").on("change", function(e) {
		var selectedProgramId = $("#selectedProgramId").val();
		$li.hide().filter(".program_" + selectedProgramId).show();
		//clearAllSelectins(); // Clear All Module Selections
		
		if((selectedProgramId == "") || (typeof selectedProgramId == "undefined" )){
			$li.show();	
		}
	});
	
	$(function() {
	    $('#selectedClinicId').change(function() {
	    });
	});