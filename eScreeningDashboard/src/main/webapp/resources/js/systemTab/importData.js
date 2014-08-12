
$(document).ready(function() {
	var current_case			= "";
	var import_modal_label 		= $("#import_modal_label");
	var verification_message 	= $("#verification_message");
	var user_verification_link 	= $(".user_verification_link");
	var btn_verify 				= $("#btn_verify");
	var btn_close 				= $('#btn_close');


	$("#btn_close").on("click", function(){
		btn_close.text("Cancel");
		btn_verify.removeClass("hide");
		btn_verify.removeClass("disabled");
		btn_verify.removeAttr('disabled');
		btn_verify.text('Yes');
	});
	
	
	$(".btn_import").on("click", function(){
		current_case 	= $(this).attr("data-case"); 	// update modal case (case_clinic / case_clinic_reminder / case_note)
		current_content = $(this).attr("data-content"); // update modal content
		current_title 	= $(this).attr("data-title");	// update modal title
		//alert(current_case);
		$("#btn_verify").attr("data-case", current_case);
		import_modal_label.text(current_title);
		verification_message.text(current_content);
	});
	

	$('#verifyForm').submit(function(e){
	e.preventDefault();
	
	btn_verify.button();
	btn_verify.button('loading');
	
	
	switch(current_case) {
		case "case_clinic":
			current_url = 'importData/clinics/refresh';
			break;
		case "case_clinic_reminder":
			current_url = 'importData/clinicalReminders/refresh';
			break;
		case "case_note":
			current_url = 'importData/noteTitles/refresh';
			break;
		default:		
	}   

	  	
   $.ajax({
	  type : 'post',
	   url :  current_url, // in here you should put your query 
	   data :  '', // No pass // in php you should use $_POST['post_id'] to get this value 
	   success : function(r)
		   {
			var userMessage = r['userMessage'];
			var hasError = r['hasError'];

			if(hasError == "false"){
				verification_message.removeClass("hide");
				verification_message.addClass("alert-success");
				verification_message.removeClass("alert-danger");
				verification_message.empty().html(userMessage);
				user_verification_link.addClass("hide");
				btn_verify.addClass("hide");
				btn_close.text("Close");
			}else{
				verification_message.removeClass("hide");
				verification_message.addClass("alert-danger");
				verification_message.removeClass("alert-success");
				verification_message.empty().html(userMessage);
				btn_verify.removeAttr('disabled');
				btn_verify.removeClass('disabled');
				btn_verify.text('Import Again');
			}
	   }
});
   
	});
});