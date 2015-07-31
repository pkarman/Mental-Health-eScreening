/**
 * assessmentCreate.js - The behavior module for assessmentCreate.jsp.
 *
 * @author Bryan Henderson
 * @date   11/22/2013
 *
 */
 var clinicSelected;
 var clinicianSelected;
 var vetLastName;
 var vetLastFour;
 var docMode = "existing";
 var submitReady = false;
 var prevAssessFoundTxt = "<p>Previous assessments found for the same candidate.&nbsp;&nbsp;" +
 		"Please choose a record from the following list to link the assessments or click on" +
 		"'New Candidate' to create new candidate record.</p>";
 var noFoundTxt = "<p>No assessments found for this candidate. Please click on 'New Candidate' " +
 		"to create a new candidate record.</p>";

 $(document).ready(function() {
	 
	 	docMode = $('#createAccessModeParam').val();
	 	
	 	if (docMode == "existing"){
		 	$('#cliniciansSelectorContainer').hide();
		 	$('#disabledClinic').hide();
		 	$('#disabledClinician').hide();
		 	$('#clinicSelect').show();
		 	$('#clinicianName').show();
		 	$('#createAssessmentButton').show();
		 	$('#resetFormButton').hide();
		 	$('#newCandidateButton').hide();
		 	$('#vetTableContainer').show();
		 	$('#createAssessmentButton').removeAttr('disabled').removeClass('bigGrayButton').click(function(e){e.preventDefault();});
	 	}else if (docMode == "new"){
		 	// Set up empty table.
		 	// Hide Vet Table
		 	$('table').footable();
		 	$('#vetTableContainer').hide();
		 	$('#cliniciansSelectorContainer').hide();
		 	$('#formContainer').hide();
		 	$('#docSelectLabel').hide();
		 	$('#docSelectInput').hide();
		 	$('#disabledClinic,#disabledClinician').hide();
		 	$('#resetFormButton').hide();
		 	$('#newCandidateButton').hide();
		 	$('#lastNameParam').attr('disabled', 'true');
		 	$('#ssnParam').attr('disabled', 'true');
		 	//$('#createAssessmentButton').attr('disabled', 'disabled').addClass('bigGrayButton').click(function(e){e.preventDefault();});
	 	}
	 	
	 	// Validation setup.
	 	$('form').h5Validate({click:true, kbSelectors:':text, select', focusout:true, change:true});
	 	
	 	// Determine Document mode, since we're getting this value on POST-back.
	 	docMode = $('#createAccessModeParam').val();
	 	// Form setup.
	 	
	 	
		$(window).load(function(){
			$('#accessMode').iphoneStyle({ resizeContainer: false, resizeHandle: false});
			$('#formContainer').fadeIn(1000);
		});

	 	/** Initialization Service Call **/
	 	$.ajax({
	 		url: "services/getclinicsForDropdown",
 	        type: 'GET',
 	        dataType: 'html',
 	        contentType: 'application/json',
 	        mimeType: 'application/json',
 	 
 	        success: function (data) {
 	        	
 	        	var clinics = $.parseJSON(data);
 	        	$('#clinicSelect').append($('<option value="-1">Please Select</option>'));
 	        	$.each(clinics, function (index, value) {
 	        		var append = $('<option>').attr('value', clinics[index].stateId).text(clinics[index].stateName);
 	        	   $("#clinicSelect").append($(append));
 	        	   // Attach event handler, and go away.

 	        	});
 	        },
 		
 	        error: function(jqXHR, textStatus, errorThrown) {
 	        	  console.log(textStatus, errorThrown);
 	        	}
	 	});
	 	
  	   $('#clinicSelect').change(function(){
 		   // Set the clinic.
 		   clinicSelected = $('#clinicSelect option:selected').val();
 		   var clinName = $('#clinicSelect option:selected').text();
 		   $('#clinicSelect').hide(500);
 		   $('#disabledClinic').text(clinName);
 		   $('#disabledClinic').show(500);
 		   $('#clinicIdParam').val(clinicSelected);
 		   // Show clinic selection.
 		   
 		  $.ajax({
	 			url: "services/clinics/" + clinicSelected + "/clinicians",
	 	        type: 'GET',
	 	        dataType: 'html',
	 	        contentType: 'application/json',
	 	        mimeType: 'application/json',
	 	 
	 	        success: function (data) {
   		 		var clinx = $.parseJSON(data);
   		 		var len = clinx.length;
   		 		
   		 		$('#clinicianName').empty();
   		 		$('#clinicianName').append($('<option value="-1">Please Select</option>'));
   		 		$.each(clinx, function(index, value){
   		 			if (index <= len){ 	 	     		 			
	     		 			var clinId = clinx[index].stateId;
	     		 			var name = clinx[index].stateName;
	     		 			var opt = $('<option value="' + clinId + '">' + name + '</option>');
	     		 			$('#clinicianName').append($(opt));
   		 			}

   		 		});
	 	        },
	 		
	 	        error: function(jqXHR, textStatus, errorThrown) {
	 	        	  console.log(textStatus, errorThrown);
	 	        }
	 		});
 		   $('#docSelectLabel,#docSelectInput,#resetForm,#resetFormButton').show(500);
 	   });
	 	
	 		$('#clinicianName').change(function(){
	 				var clinicianIdSelected = $('#clinicianName option:selected').val();
	 				var clinicianFullnameSelected = $('#clinicianName option:selected').text();
	 				clinicianSelected = {clinicianId:clinicianIdSelected, fullName:clinicianFullnameSelected};
		 			$('#clinicianIdParam').val(clinicianIdSelected);
		 				$('#clinicianName').hide(500);
		 				$('#disabledClinician').text(clinicianFullnameSelected);
		 				$('#disabledClinician').show(500);
		 				$('#lastNameParam').removeAttr('disabled');
		 				$('#ssnParam').removeAttr('disabled');
  		 			
  		   });
		 	
		 	$('#disabledClinic').click(function(){
		 		$(this).hide(500);
		 		$('#clinicSelect').show(500);
		 	});
		 	
		 	$('#disabledClinician').click(function(){
		 		$(this).hide(500);
		 		$('#clinicianName').val('');
		 		$('#clinicianName').show(500);
		 	});
		 	
		 	$('#resetFormButton').click(function(){
		 		$('#newCandidateButton, #disabledClinician, #disabledClinic, #resetFormButton').hide(500);
		 		$('#vetTableContainer').hide(500);
		 		$('#clinicianSelectorContainer').hide(500);
		 		if ($('#accessMode').prop('checked', true))
		 			$('#accessMode').click();
		 		$('#accessMode').iphoneStyle({ resizeContainer: false, resizeHandle: false});
		 		$('#ssnParam').val('');
		 		$('#lastNameParam').val('');
		 		$('#clinicSelect').attr('selectedIndex', '-1').find("option:selected").removeAttr("selected");
		 		$('#clinicSelect').show(500);
		 		$('#clinicianName').val('').show(500);
		 	});
		 	
		 	
	 	/** Form actions. **/
		$('#chooseCandidateButton').click(function(){
	       	$('#createAccessModeParam').val('existing');
			$('#assessmentCreateForm').submit();
		});
		
		$('#createAssessmentButton').click(function() {
        	$('#createAccessModeParam').val('new');
			$('#assessmentCreateForm').submit();
	    });
		
		$('#backButton').click(function() {
	    	window.location.href = 'home';
	    });
		
		$('#newCandidateButton').click(function(){
			$('#veteranIdParam').val("");
			$('#assessmentCreateForm').submit();
		});
		
		$('#lastNameParam').blur(function(){
			
		});
		
		$('#ssnParam').blur(function(){
			// /veterans/{lastName}/{lastfour}
			
			if($('#lastNameParam').val().length >= 4 && $('#ssnParam').val().length == 4)
			{
				var lastName = $('#lastNameParam').val();
				var lastFour = $('#ssnParam').val();
				$.ajax({
		 			url: "services/veterans/" + lastName + "/" + lastFour,
		 	        type: 'GET',
		 	        dataType: 'html',
		 	        contentType: 'application/json',
		 	        mimeType: 'application/json',
		 	 
		 	        success: function (data) {
		 	        	var vets = $.parseJSON(data);

		 	        	if (vets.length > 0)
		 	        	{
		 	        		$.each(vets, function(index, value){
		 	        			if(vets[index].firstName == null)vets[index].firstName = "";
		 	        			if(vets[index].lastName == null)vets[index].lastName = "";
		 	        			if(vets[index].middleName == null)vets[index].middleName = "";
		 	        			if(vets[index].ssnLastFour == null)vets[index].ssnLastFour = "";
		 	        			if(vets[index].emailAddress == null)vets[index].emailAddress = "";
		 	        			if(vets[index].phoneNumber == null)vets[index].phoneNumber = "";
			 	        		var tbRow = "<tr>" +
			 	        						"<td>" + vets[index].firstName + "</td>" +
			 	        						"<td>" + vets[index].lastName + "</td>" +
			 	        						"<td>" + vets[index].middleName + "</td>" +
			 	        						"<td>" + vets[index].ssnLastFour + "</td>" +
			 	        						"<td style='visibility:hidden;display:none;'>" + vets[index].veteranId + "</td>" +
			 	        						"<td>" + vets[index].emailAddress + "</td>" +
			 	        						"<td>" + vets[index].phoneNumber + "</td>" +
			 	        					"</tr>";
			 	        		$('table tbody').append($(tbRow));
			 	        		$('table tbody tr').children().click(function(){
			 	        			$('table tbody tr').css('background-color', '');
			 	        			var rowContent = $(this).parent('tr');
			 	        			rowContent.css('background-color', '#ebebeb');
			 	        			var veteranId = rowContent.find('td:eq(4)').html();
			 	        			var lName = rowContent.find('td:eq(1)').html();
			 	        			var ssn = rowContent.find('td:eq(3)').html();
			 	        			$('#veteranIdParam').val(veteranId);
			 	        			$('#lastNameParam').val(lName);
			 	        			$('#ssnParam').val(ssn);
			 	        			$('#vetTableContainer table').css('width', '70%');
			 	        			$('#chooseCandidateButton').show(500);
			 	        			//$('#createAssessmentButton').hide(500);
			 	        		});
			 	        		
			 	        	});
		 	        		// Trigger default show behavior for vet table.
		 	        		$('#chooseCandidateButton').hide();
		 	        		$('#newCandidateButton').show(500);
		 	        		$('#createAssessmentButton').hide(500);
		 	        		$('#vetTableContainer table').css('width', '100%');
		 	        		$('#vetTableContainer').show(500);
		 	        		
		 	        		// Trip the dialog.
	 	        			var textarea = prevAssessFoundTxt;
	 	        			$('#dialogMessage').append($(textarea));
		 	        		$('#dialogMessage').dialog({
		 	        			width:600,
		 	        			height:400,
 	        					modal: true,
 	        					buttons: {
 	        						Ok: function() {
 	        							$( this ).dialog( "close" );
 	        						}
 	        					}
		 	        		});
		 	        	}
		 	        	else
		 	        	{
		 	        		// NOTE:: Removing the dialog show for 'No User Found,' as well as the 'New Candidate' button show/click binding. - JBH
		 	        		// TODO:: Remove this condition if it is not needed.
		 	        		
		 	        		// Trip the dialog.
	 	        			/*var textarea = noFoundTxt;
	 	        			$('#dialogMessage').append($(textarea));
		 	        		$('#dialogMessage').dialog({
		 	        			width:600,
		 	        			height:400,
 	        					modal: true,
 	        					buttons: {
 	        						Ok: function() {
 	        							$( this ).dialog( "close" );
 	        						}
 	        					},
 	        					html:noFoundTxt
		 	        		});
		 	        		$('#newCandidateButton').text("New Candidate");*/
		 	        	}
		 	        	
		 	        	// NOTE:: This is extraneous, so has been commented. The event handler is already set, and this flow does not 
		 	        	// require showing the button. - JBH.
	 	        		/*$('#setNew,#newCandidateButton').show(500).click(function(){
	 	        			if (submitReady)
	 	        				$('#assessmentCreateForm').submit();
	 	        			else {
	 	        				// Trigger validation.
	 	        			}
	 	        		});*/
		 	        },
		 		
		 	        error: function(jqXHR, textStatus, errorThrown) {
		 	        	  console.log(textStatus, errorThrown);
		 	        }
		 		});
			}

		});
 });