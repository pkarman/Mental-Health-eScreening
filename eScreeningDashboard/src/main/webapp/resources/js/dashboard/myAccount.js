var app = angular.module('passwordChangeFormApp', []);
app.directive('passwordValidate', function() {
    return {
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function(viewValue) {

                scope.pwdValidLength = (viewValue && viewValue.length >= 8 ? 'valid' : undefined);
                scope.pwdHasLetter = (viewValue && /[a-z]/.test(viewValue)) ? 'valid' : undefined;
                scope.pwdHasNumber = (viewValue && /\d/.test(viewValue)) ? 'valid' : undefined;
                scope.pwdHasCapital = (viewValue && /[A-Z]/.test(viewValue)) ? 'valid' : undefined;
                scope.pwdHasSpecial = (viewValue && /[@,!,#,$,%,&,?,^,*,(,)]/.test(viewValue)) ? 'valid' : undefined;

                if(scope.pwdValidLength && scope.pwdHasLetter && scope.pwdHasNumber &&  scope.pwdHasCapital &&  scope.pwdHasSpecial) {
                    ctrl.$setValidity('pwd', true);
                    return viewValue;
                } else {
                    ctrl.$setValidity('pwd', false);                    
                    return undefined;
                }

            });
        }
    };
});
app.directive("matchData", function() {
    return {
        require: "ngModel",
        link: function(scope, elem, attrs, ctrl) {
            var otherInput = elem.inheritedData("$formController")[attrs.matchData];
            ctrl.$parsers.push(function(value) {
                if(value === otherInput.$viewValue) {
                    ctrl.$setValidity("match", true);
                    return value;
                }
                else {
                	ctrl.$setValidity("match", false);
					return undefined;
                }
            });
            otherInput.$parsers.push(function(value) {
                ctrl.$setValidity("match", value === ctrl.$viewValue);
                return value;
            });
        }
    };
});


function myAccountController($scope,$element,$http) {

	// Initialize the model.
	$scope.myAccountFormBean = {};
	// Save
	$scope.submitMyForm = function(myAccountFormBean) {
		// Check validation first.
		if ($scope.changePasswordForm.$valid) {
			$http.post('myAccount/services/users/active/updatepass', myAccountFormBean)
			.success(function(data) {
				if(data="success"){
					$('#successDiv').html("<p style='color: white;background-color: green;width: 55%;'> Password was successfully updated.</p>");
			        $scope.changePasswordForm.submitted = false;
			        $("#currentPassword").val("");
			        $("#newPassword").val("");
			        $("#confirmedPassword").val("");
			        
			        setTimeout(function () {window.location.reload();}, 5000) // wait for 5 second before going to login screen.
					return;
				}
			})
			.error(function(data, status, headers, config) {
				if (data.code == 1001) {
					$scope.changePasswordForm.submitted = true;
					$scope.changePasswordForm.currentPassword.$setValidity("required", false);
					$("#pwdRequiredId").empty();
					$("#pwdRequiredId").html("Current password is incorrect");
				}
			});
		}
		else {
			$scope.changePasswordForm.submitted = true;
		}
	};
}


/* JQuery */
// ---------------------------
// JH Comment - TODO:  
// - Refactor all the JS code 
// - Move to external file
// - Cache all classes to var
// ---------------------------
  
$(document).ready(function() {
    tabsLoad("myAccount");
    
    /* The following code is  show and hide the angularjs validations for all  browsers. */
    var currentPassword = "#currentPassword";
    $("#currentPassword").click(function(){
      $(this).removeClass("ng-pristine");
      $("#newPassword").addClass("ng-pristine");
      $("#confirmedPassword").addClass("ng-pristine")
      
    });
    $("#newPassword").click(function(){
      $(this).removeClass("ng-pristine");
      $("#currentPassword").addClass("ng-pristine");
      $("#confirmedPassword").addClass("ng-pristine");
    });
    $("#confirmedPassword").click(function(){
        $(this).removeClass("ng-pristine");
        $("#currentPassword").addClass("ng-pristine");
        $("#newPassword").addClass("ng-pristine")
    });
    

    // Load my info call
    $.ajax({
    type : 'get',
    url : 'myAccount/services/users/active/myInfo', // in here you should put your query 
    data :  '',
    success : function(data)
       {								
         $('.acc_firstName').empty().html(data['firstName']);
         $('.acc_middleName').empty().html(data['middleName']);
         $('.acc_lastName').empty().html(data['lastName']);
         $('.acc_phoneNumber').empty().html(data['phoneNumber']);
         $('.acc_emailAddress').empty().html(data['emailAddress']);
         
         $('.acc_roleName').empty().html(data['roleName']);
         
         var cprsVerified = data['cprsVerified'];
         if(cprsVerified == "false"){
           $('.acc_cprsVerified').empty().html("Not Verified");
           $(".user_verification_link").removeClass("hide");
           $(".user_verification_link").attr("aria-hidden", "false");
         }else{
           $('.acc_cprsVerified').empty().html("Verified");
           $(".user_verification_link").addClass("hide");
           $(".user_verification_link").attr("aria-hidden", "true");
         }
       }
    });
         
    $('#verifyForm').submit(function(){  
      var accessCode = $('#cprsAccessCode').val();
      var verifyCode = $('#cprsVerifyCode').val();

      $('#btn_verify').button();
      $("#btn_verify").button('loading');
            
      $.ajax({
          type : 'post',
           url : 'myAccount/services/users/active/verifyVistaAccount', // in here you should put your query 
           data :  'accessCode='+ accessCode + '&verifyCode=' + verifyCode, // here you pass your id via ajax . vid & vien
           // in php you should use $_POST['post_id'] to get this value 
           success : function(r){
           var isSuccessful = r['isSuccessful'];
           var userMessage = r['userMessage'];
          
          //isSuccessful = "true";
          if(isSuccessful == "true"){
            $("#verification_message").removeClass("hide");
            $("#verification_message").addClass("alert-success");
            $("#verification_message").removeClass("alert-danger");
            $('#verification_message').empty().html(userMessage);
            $('.acc_cprsVerified').empty().html("Verified");
            $(".user_verification_link, #btn_verify").addClass("hide");
            $('#btn_close').text("Continue");
        $(':input','#verifyForm')
          .not(':button, :submit, :reset, :hidden')
          .val('');
          }else{
            $("#verification_message").removeClass("hide");
            $("#verification_message").addClass("alert-danger");
            $("#verification_message").removeClass("alert-success");
            $('#verification_message').empty().html(userMessage);
            $("#btn_verify").removeAttr('disabled');
            $("#btn_verify").removeClass('disabled');
            $("#btn_verify").text('Verify Now');
            $(':input','#verifyForm')
              .not(':button, :submit, :reset, :hidden')
              .val('');
          }
        }
    });
    
  });
     
   // Call verify from outside links ...
   var hash = window.location.hash;
   var verify_modal = '#verify_modal';
   
   if (hash == "#verify") {
      $(verify_modal).modal({
       keyboard: false,
       backdrop: 'static'
      });
   }    
    // Handle close event
    $(verify_modal).on('hidden.bs.modal', function () {	
      $("#cprsAccessCode").val("");
      $("#cprsVerifyCode").val("");
      $("#verification_message").addClass("hide");
    }); 
 });
 

