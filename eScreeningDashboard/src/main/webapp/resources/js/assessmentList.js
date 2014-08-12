
$( document ).ready(function() {
	$.getJSON( "assessments", function( assessments ) {
		
		  var dataLength = assessments.length;
		  if(dataLength>0){
			  $.each( assessments, function(i, item){
				  
				  var veteranName = checkForNull(assessments[i].veteranLastName);
				  if(assessments[i].veteranFirstName){
					  veteranName = veteranName + ", " + assessments[i].veteranFirstName;
				  }
				  
				  var cliName = checkForNull(assessments[i].clinicianLastName);
				  if(assessments[i].clinicianFirstName){
					  cliName = cliName + ", " + assessments[i].clinicianFirstName;
				  }
				  
				  $('<tr>').html("<td>" + veteranName +"</td>"+
	        					 "<td>" + cliName +"</td>"+
	        					 "<td>" + checkForNull(assessments[i].clinicName) +"</td>"+
	        					 "<td>" + checkForNull(assessments[i].programName) +"</td>"+
	        					 "<td>" + checkForNull(assessments[i].veteranSsnLastFour) + "</td>"+
	        					 "<td>" + " " + "</td>"+
	        					 "<td>" + checkForNull(assessments[i].duration) + "</td>"+
	        					 "<td>" + checkForNull(assessments[i].percentComplete) + "</td>"+
	        					 "<td>" + assessments[i].assessmentStatus + "</td>"
	        					 
				  	).appendTo('.footable');
				  
			  });
			  
			  $('.footable tr:odd ').css('background','#e8e8e8');
			  $('.footable tr:even ').css('background','#f5f5f5');
			  
			  $(".footable tr").live("click", function() { 
				  	$('.footable tr:odd ').css('background','#e8e8e8');
				  	$('.footable tr:even ').css('background','#f5f5f5');
				  	$(this).css('background','#a9caed');
				  });
		  }
		  
		  else if(dataLength<=0) {
			  $("#formContainer>*").hide();
			  $("#formContainer").append("<div style='height: 100px; width: 400px; margin: 50px auto; border: 1px solid red; background-color: #c0c0c0; font-weight: bold; text-align: center; padding-top: 30px; color: #800000; font-size: 20px;'>No Assessment List to Show</div>");
		  }
		  
		  
		  
	}); // JSON coding ends
	
	
	
	
});


function checkForNull(value){
	if(value != null){
		return value;
	  }
	  return "";
}



