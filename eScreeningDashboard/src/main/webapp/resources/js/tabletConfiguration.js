var app = angular.module('tabletConfigurationApp', []);

app.factory('programListService', function($http) {
	return {
		getProgramList : function() {
			return $http({
				method : "GET",
				url : "dashboard/programList",
				responseType : "json"
			}).then(function(result) {
				console.log("result.data" + result);
				return result.data;
			});
		}
	};
});

app.controller("tabletConfigurationController", function($scope, $element, $http, $window, programListService) {
	$scope.tabletConfigurationFormBean = {};
	$scope.tabletConfigurationFormBean.programId = "";

	programListService.getProgramList().then(function(data) {
		$scope.programList = data;
	});
});

// JQuery 
$(document).ready(function() {	
	var updateBtn = '#update';
	var cancelBtn = '#cancel';
	var tabletProgramBlock = "#tabletProgramBlock";
	//var tabletProgramLSText = localStorage.getItem("tabletProgramLSText");
	

	
	// Update Program
	$(updateBtn).click(function() {    
		var selectText 	= $('#programId :selected').text(),
			selectValue = $('#programId :selected').val();
			
		// When use select a program
		if (selectValue !== ""){
			// Set localStorage
			localStorage.setItem("tabletProgramLSText", selectText);
			localStorage.setItem("tabletProgramLSValue", selectValue);
			
			// Update Tablet Block with the selected Text
			$(tabletProgramBlock).text(localStorage.getItem("tabletProgramLSText"));
			
			// Show Success Message
			message = "#successMsg";
		}else{
			message = "#errorMsg";
		}
		// Hide all messages then show the correct one
		$("#successMsg").addClass("hide");
		$("#errorMsg").addClass("hide");
		$(message).removeClass("hide");
	});
	
	// Cancel
	$(cancelBtn).click(function() {    
		window.location.href = "assessmentLogin";
	});


	
  
	// Check browser support
	if (typeof(Storage) != "undefined") {
		if (localStorage.getItem("tabletProgramLSText") === null || localStorage.getItem("tabletProgramLSText") === undefined || localStorage.getItem("tabletProgramLSText") === "" || localStorage.getItem("tabletProgramLSText").length === 0) {
			$(tabletProgramBlock).text("[NONE]");
		}else{
			$(tabletProgramBlock).text(localStorage.getItem("tabletProgramLSText"));
		}
	} else {
		$(tabletProgramBlock).text("Sorry, your browser does not support Web Storage...");
	}   
});