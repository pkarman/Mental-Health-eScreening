$(document).ready(function(e) {
  function loadData(){
    $.ajax({
      type : 'get',
      dataType: 'json',
      url : 'welcome',
      success : function(data){
    	  if(data.error){
    		  throw error;
    	  }
    	  else{
    		  $("#assessmentWelcome").html(data.message);  
    	  }
      },
      error: function(error){
    	  //TODO: This error logic should be combined with handleServerError and displayServerError in measures-integrated and pulled out into another js file as a service
    	  if(error.responseJSON && error.responseJSON.errorMessages && error.responseJSON.errorMessages.length && error.responseJSON.errorMessages.length > 0){
    		  var welcomeContainer = $("#assessmentWelcome").empty();
    		  error.responseJSON.errorMessages.forEach(function(errorMsg){welcomeContainer.append($("<div>" + errorMsg.description + "</div>"));});
    	  }
    	  else{
	    	  $("#assessmentWelcome").html("Can't load welcome message. Contact support.");
    	  }
        }
    });
  }
  
  loadData();

  $(document).on('click', '#refresh', function(e) { 
      loadData();
      e.preventDefault();
  });
});