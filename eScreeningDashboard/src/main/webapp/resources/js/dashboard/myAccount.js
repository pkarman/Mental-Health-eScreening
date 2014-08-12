
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
                scope.pwdHasSpecial = (viewValue && /[@,!,#,$,%,&,*]/.test(viewValue)) ? 'valid' : undefined;

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