$(document).ready(function() {
	/*
	setTimeout(function() {
      // Do something after 5 seconds
	  alert("Yes");
	  $('#timeout_modal').modal("show");
	}, 2000);
	*/
	
	

	/*
	$("#dialogLogoutTimer").dialog({
		width: 500,
		modal: true,
		draggable: false,
		closeOnEscape: false,
		buttons: {
          "Logout": function () {
        	  clearInterval(timer);
        	  $(this).dialog('close');
        	  autoSaveData(logout, true);
          },
          "Continue to the survey": function () {
        	  clearInterval(autoLogoutTimer);
        	  setTimer();
        	  $(this).dialog('close');
          }
        },
        open: function(event, ui) { 
            $(this).parent().children().children('.ui-dialog-titlebar-close').hide();
        	autoLogoutTimer = setInterval(function () {
        		$("#counter").html(autoLogoutCounter--);
        		if(autoLogoutCounter<0){
        			// call the save method
        			
        			console.log("HERE: "+autoLogoutCounter);
        			autoSaveData(function(){logout("timeout");}, true);
        			autoLogoutCounter = 20;
        		}
        	}, 1000);
        	$(this).parent().find(".ui-dialog-buttonpane .ui-button")
            .addClass("customButtonsDialog");
        	
        }
	});
	clearInterval(timer);
	*/
	
	
	
});