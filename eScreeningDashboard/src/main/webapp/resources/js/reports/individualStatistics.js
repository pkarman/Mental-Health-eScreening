module = angular.module('reportsFormApp', ["checklist-model"]);

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
        return {showSuccess: _showSuccess};
    };
});


module.factory('surveysListService', function ($http) {
    return {
        getSurveysList: function () {
            return $http({
                method: "GET",
                url: "listSurveys",
                responseType: "json"
            }).then(function (result) {
                return result.data;
            });
        },
        requestChartableData: function (reportRequestData) {
            return $http({
                method: "POST",
                url: "requestChartableData",
                data: reportRequestData,
                responseType: "json"
            });
        },
        requestNumericReport: function (reportRequestData) {
            return $http({
                method: "POST",
                url: "individualStatisticsNumeric",
                data: reportRequestData
                //headers: {'Content-Type': 'application/x-www-form-urlencoded'}  // set the headers so angular passing info as form data (not request payload)
            });
        },
        requestGraphicReport: function (svgData, chartableData, reportRequestData) {
            return $http({
                method: "POST",
                url: "individualStatisticsGraphic",
                data: {svgData: svgData, chartableData: chartableData.data, userReqData:reportRequestData}
                //headers: {'Content-Type': 'application/x-www-form-urlencoded'}  // set the headers so angular passing info as form data (not request payload)
            });
        }
    };
});

module.controller('reportsController', function ($scope, $http, $window, surveysListService) {
    // place holder for selected surveys
    $scope.report = {surveysList: []};
    // Load Surveys List Service
    surveysListService.getSurveysList().then(function (data) {
        $scope.surveysList = data;
    });

    var generateGraphs = function (chartableData) {
        var svgObjects = [];
        _.each(chartableData.data, function (dataMap) {
            var df = dataMap.dataFormat;
            var ds = dataMap.dataSet;
            graphGenerator(df, ds);
            svgObjects.push(svgObj());
        });

        return svgObjects;
    }

    $scope.save = function () {
        $scope.$broadcast('show-errors-check-validity');

        if ($scope.reportForm.$valid) {
            // collect data to send to server as a request data
            var reportRequestData = {
                lastName: $scope.report.lastName,
                ssnLastFour: $scope.report.ssnLastFour,
                fromDate: $scope.report.fromDate,
                toDate: $scope.report.toDate,
                surveysList: $scope.report.surveysList
            }
            if ($scope.report.reportType === 'reportTypeGraph') {
                surveysListService.requestChartableData(reportRequestData).then(function (chartableData) {
                    // produce d3 graphs as svg objects
                    var svgData = generateGraphs(chartableData);
                    surveysListService.requestGraphicReport(svgData, chartableData, reportRequestData);
                });

            } else {
                surveysListService.requestNumericReport(reportRequestData).then(function (data) {
                    console.log(data);
                });
            }
            $scope.reset();
        }
    };

    $scope.reset = function () {
        $scope.$broadcast('show-errors-reset');
        $scope.report = {};
    }

    $scope.selectAllSurvey = function () {
        if ($scope.report.selectAllSurvey) {
            $scope.report.surveysList = $scope.surveysList.map(function (item) {
                return item.surveyId;
            });
        } else {
            $scope.report.surveysList = [];
        }
    }
});

/* JQuery */
$(document).ready(function () {
    // Date Picker Start - Call picker and focus for 508         
    var fromDateGroup = "#fromDateGroup";
    var toDateGroup = "#toDateGroup";
    $(fromDateGroup).datepicker({
        showOn: 'button',
        format: 'mm/dd/yyyy',
        autoclose: true
    });

    $(toDateGroup).datepicker({
        showOn: 'button',
        format: 'mm/dd/yyyy',
        autoclose: true
    });

    $('.id_header_tooltip').tooltip({
        'placement': 'top'
    });

    // Select All
    var backToReports = "#backToReports";
    var reportsURL = "reports"
    var checkboxGroupSurvey = ".checkbox_group_survey";

    $(backToReports).click(function () {
        window.location = reportsURL;
    })

    /* NEED TO MOVE TO COMMON FILE TO REUSE */
    $('.selectAllSurvey').click(function (event) {  //on click
        //alert( $(this).attr("data-cbgroup") );
        if (this.checked) { // check select status
            $(checkboxGroupSurvey).each(function () { //loop through each checkbox
                this.checked = true;  //select all checkboxes with class "checkbox_group"            
            });
        } else {
            $(checkboxGroupSurvey).each(function () { //loop through each checkbox
                this.checked = false; //deselect all checkboxes with class "checkbox_group"                       
            });
        }
    });
});