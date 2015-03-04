module = angular.module('reportsFormApp', []);

/* Validation */ 
module.directive('showErrors', function ($timeout, showErrorsConfig) {
      var getShowSuccess, linkFn;
      getShowSuccess = function (options) {
        var showSuccess;
        showSuccess = showErrorsConfig.showSuccess;
        if (options && options.showSuccess != null) {
          showSuccess = options.showSuccess;
        }
        return showSuccess;
      };
      linkFn = function (scope, el, attrs, formCtrl) {
        var blurred, inputEl, inputName, inputNgEl, options, showSuccess, toggleClasses;
        blurred = false;
        options = scope.$eval(attrs.showErrors);
        showSuccess = getShowSuccess(options);
        inputEl = el[0].querySelector('[name]');
        inputNgEl = angular.element(inputEl);
        inputName = inputNgEl.attr('name');
        if (!inputName) {
          throw 'show-errors element has no child input elements with a \'name\' attribute';
        }
        inputNgEl.bind('blur', function () {
          blurred = true;
          return toggleClasses(formCtrl[inputName].$invalid);
        });
        scope.$watch(function () {
          return formCtrl[inputName] && formCtrl[inputName].$invalid;
        }, function (invalid) {
          if (!blurred) {
            return;
          }
          return toggleClasses(invalid);
        });
        scope.$on('show-errors-check-validity', function () {
          return toggleClasses(formCtrl[inputName].$invalid);
        });
        scope.$on('show-errors-reset', function () {
          return $timeout(function () {
            el.removeClass('has-error');
            el.removeClass('has-success');
            return blurred = false;
          }, 0, false);
        });
        return toggleClasses = function (invalid) {
          el.toggleClass('has-error', invalid);
          if (showSuccess) {
            return el.toggleClass('has-success', !invalid);
          }
        };
      };
      return {
        restrict: 'A',
        require: '^form',
        compile: function (elem, attrs) {
          if (!elem.hasClass('form-group')) {
            throw 'show-errors element does not have the \'form-group\' class';
          }
          return linkFn;
        }
      };
    }
  );
  
  module.provider('showErrorsConfig', function () {
    var _showSuccess;
    _showSuccess = false;
    this.showSuccess = function (showSuccess) {
      return _showSuccess = showSuccess;
    };
    this.$get = function () {
      return { showSuccess: _showSuccess };
    };
  });



module.factory('surveysListService', function($http) {
	return {
		getSurveysList : function() {
			return $http({
				method : "GET",
				url : "reports/listSurveys",
				responseType : "json"
			}).then(function(result) {
				return result.data;
			});
		}
	};
});


module.controller('reportsController', function($scope,$http,$window, surveysListService) {

	// Load Surveys List Service 
	surveysListService.getSurveysList().then(function(data) {
		$scope.surveysList = data;
	});
  
  
  
  $scope.save = function() {
    $scope.$broadcast('show-errors-check-validity');
    
    if ($scope.reportForm.$valid) {
      alert('Report Validated');
      $scope.reset();
    }
  };
  
  $scope.reset = function() {
    $scope.$broadcast('show-errors-reset');
    $scope.report = { name: '', email: '' };
  }
});

/* JQuery */
$(document).ready(function() {

    // Date Picker Start - Call picker and focus for 508         
    var fromDateGroup  = "#fromDateGroup";
    var toDateGroup    = "#toDateGroup";
    $(fromDateGroup).datepicker({
			showOn : 'button',
      format: 'mm/dd/yyyy',
      autoclose: true
		});

		$(toDateGroup).datepicker({
			showOn : 'button',
      format: 'mm/dd/yyyy',
			autoclose: true
		});
	
		$('.id_header_tooltip').tooltip({
			'placement': 'top'
		});

	// Select All
	var backToReports 	= "#backToReports";
	var reportsURL  = "reports"
	
	$(backToReports).click(function() {
	   window.location = reportsURL;
	})
	
	/* NEED TO MOVE TO COMMON FILE TO REUSE */
	$('.selectAll').click(function(event) {  //on click 		
		//alert( $(this).attr("data-cbgroup") );
        if(this.checked) { // check select status
            $(".checkbox_group_survey").each(function() { //loop through each checkbox
                this.checked = true;  //select all checkboxes with class "checkbox_group"            
            });
        }else{
            $(".checkbox_group_survey").each(function() { //loop through each checkbox
                this.checked = false; //deselect all checkboxes with class "checkbox_group"                       
            });         
        }
    });
});