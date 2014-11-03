$(document).ready(function() {
  // Print Report
  var veteran_summary_modal       = ".veteran_summary_modal";
  var veteran_summary_modal_body  = ".veteran_summary_modal .modal-body";
  var printable                   = ".printable";
  var print                       = ".print";
    
  $(veteran_summary_modal).on("click", print, function(){
     //get the modal box content and load it into the printable div
     $(printable).show();        
     $(printable).html($(veteran_summary_modal_body).html());
  
     // Work around to solve Chrome image loading
      setTimeout(function() {
          window.print();
          $(printable).hide();
      }, 1000);
  });
  
  
  

  // JH - 508 Set the other page elements hide while modal show to help AT tools
  var modalBlock    = '.modal';
  var outerPageDiv  = '#outerPageDiv';
  
  $(modalBlock).on('shown.bs.modal', function (e) {
      $(outerPageDiv).attr('aria-hidden', 'true');
  });
  $(modalBlock).on('hidden.bs.modal', function (e) {
      $(outerPageDiv).attr('aria-hidden', 'false');
  });


  


        
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