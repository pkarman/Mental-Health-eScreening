$(document).ready(function() {

	var changePasswordForm			= $('#changePasswordForm');  		// Change Password Form
	var verification_message 		= $('#verification_message'); 		// Message
    var changePasswordInputBlock 	= $("#changePasswordInputBlock"); 	//
    var btnSaveChangePassword 		= $("#btnSaveChangePassword"); 		// save button
    
    
    function clearFormOnExit(){
    	$(changePasswordForm).each(function(){
    	    this.reset();
    	});
    };
    
    
    $("#btn_close").on('click', function(e) {
    	clearFormOnExit();
    	$(verification_message).addClass("hide");
    	$(changePasswordInputBlock).removeClass("hide");
    	$(btnSaveChangePassword)
    		.removeClass('hide');
    	$(btnSaveChangePassword).removeAttr('disabled');
   		$(btnSaveChangePassword).removeClass('disabled');
   		$(btnSaveChangePassword).text('Save');
    });
    
    $(changePasswordForm).submit(function(e){
    	
    	e.preventDefault();
    	
         var userId 				= $("#userId").val();
         var chnPassword 			= $("#chnPassword").val();
         var chnPasswordConfirmed 	= $("#chnPasswordConfirmed").val();
         

         
         // call change password function
         changePassword();
         
         
         //
          $(btnSaveChangePassword).button();
		  $(btnSaveChangePassword).button('loading');
		      
                 
    	function changePassword(){

    		$.ajax ({
	            url: "userEditView/users/resetpass",
	            type: "POST",
	            data: JSON.stringify({userId: userId, password: chnPassword, passwordConfirmed: chnPasswordConfirmed}),
	            dataType: "json",
	            contentType: "application/json; charset=utf-8",
	            success: function(r){
	                var hasError 		= r['hasError'];
		    	   	var userMessage 	= r['userMessage'];
		    	   	
		    	   	if(hasError == false){
		    	   		$(verification_message).removeClass("hide");
		    	   		$(verification_message).addClass("alert-success");
		    	   		$(verification_message).removeClass("alert-danger");
		    	   		$(verification_message).empty().html(userMessage);
		    	   		
		    	   		$("#btnSaveChangePassword, #changePasswordInputBlock").addClass("hide");
		    	   		$('#btn_close, #close_ico').text("Close");
		    	   	
		    	   		
		    	   		// Clear Form
		    	   		clearFormOnExit();

		    	   	}else{
		    	   		
		    	   		$(verification_message).removeClass("hide");
		    	   		$(verification_message).addClass("alert-danger");
		    	   		$(verification_message).removeClass("alert-success");
		    	   		$(verification_message).empty().html(userMessage);
		    	   		$(btnSaveChangePassword).removeAttr('disabled');
		    	   		$(btnSaveChangePassword).removeClass('disabled');
		    	   		$(btnSaveChangePassword).text('Save');
		    	   
		    	   		
		    	   		
						// Clear Form
		    	   		clearFormOnExit();


		    	   	}
		    	   	
		    	   	
		    	   	
	            }
	        });
		}
        
        
        
        
    });
    

});
