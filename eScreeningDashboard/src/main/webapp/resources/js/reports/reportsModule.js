module = angular.module('reportsModule', ["checklist-model"]);

/* Validation */
module.directive('showErrors', ['$timeout', 'showErrorsConfig', function ($timeout, showErrorsConfig) {
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
    }]
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


module.factory('ReportsService', ['$http', function ($http) {
    var invokePost = function (restURL, data, responseType) {
        return $http({
            method: "POST",
            url: restURL,
            data: data,
            responseType: responseType
        });
    };

    var getSurveysList = function () {
        return $http({
            method: "GET",
            url: "listSurveys",
            responseType: "json"
        });
    };
    var getClinicsList = function () {
        return $http({
            method: "GET",
            url: "listClinics",
            responseType: "json"
        });
    };
    var requestChartableData = function (indivStatFormData) {
        return invokePost("requestChartableData", indivStatFormData, 'json');
    };
    var requestNumericReport = function (indivStatFormData, restURL) {
        return invokePost(restURL, indivStatFormData, 'arraybuffer');
    };
    var requestGraphicReport = function (data, restURL) {
        return invokePost(restURL, data, 'arraybuffer');
    };
    var savePdfData = function (pdfData, pdfDataFileName) {
        var file = new Blob([pdfData], {
            type: 'application/pdf'
        });
        //trick to download store a file having its URL
        var fileURL = URL.createObjectURL(file);
        var a = document.createElement('a');
        a.href = fileURL;
        a.target = '_blank';
        a.download = pdfDataFileName;
        document.body.appendChild(a);
        a.click();
    };
    var generateSvgObjects = function (chartableData) {
        var svgObjects = [];
        var verifiedData = _.filter(chartableData, function (data) {
            return data.dataFormat != undefined && data.dataFormat != null && data.dataSet != undefined && data.dataSet != null;
        })
        _.each(verifiedData, function (dataMap) {
            var df = dataMap.dataFormat;
            var ds = dataMap.dataSet;

            // function to use d3.js which create a svg object by manipulating the DOM
            graphGenerator(df, ds); // library function in chart.js
            var svgData = svgObj(); // library function in chart.js
            svgObjects.push(svgData);
        });
        return svgObjects;
    };

    return {
        getSurveysList: getSurveysList,
        getClinicsList: getClinicsList,
        requestChartableData: requestChartableData,
        requestNumericReport: requestNumericReport,
        requestGraphicReport: requestGraphicReport,
        savePdfData: savePdfData,
        generateSvgObjects: generateSvgObjects
    };
}]);

module.controller('indivStatsCtrl', ['$scope', '$http', 'ReportsService', function ($scope, $http, ReportsService) {
    // place holder for selected surveys
    $scope.report = {surveysList: []};
    // Load Surveys List Service
    ReportsService.getSurveysList()
        .success(function (data) {
            $scope.surveysList = data;
        }).error(function (data, status) {
            console.error('getSurveysList error', status, data);
        });

    $scope.save = function () {
        $scope.$broadcast('show-errors-check-validity');

        if ($scope.reportForm.$valid) {
            // create a model to represent user requested data on html form
            var indivStatFormData = {
                lastName: $scope.report.lastName,
                ssnLastFour: $scope.report.ssnLastFour,
                fromDate: $scope.report.fromDate,
                toDate: $scope.report.toDate,
                surveysList: $scope.report.surveysList,
                reportType:'indivStats'
            };

            if ($scope.report.reportType === 'reportTypeGraph') {
                // graph report has two steps.
                // 1>   indivStatFormData and collect the response, which is good enough to produce a numeric report
                // 2>   use the data which is good enough to produce numeric data and call it chartableData. We call it chartableData as we pass this data
                //      to generate svg Objects
                // 3>   use the svgObjects + chartableData + indivStatFormData and ask for pdf report
                ReportsService.requestChartableData(indivStatFormData)
                    .success(function (chartableData) {
                        // produce d3 graphs as svg objects
                        var svgData = ReportsService.generateSvgObjects(chartableData);

                        var data = {
                            svgData: svgData,
                            chartableData: chartableData,
                            userReqData: indivStatFormData
                        };

                        ReportsService.requestGraphicReport(data, "individualStatisticsGraphic")
                            .success(function (serverResponse) {
                                ReportsService.savePdfData(serverResponse, 'IndividualStatisticsWithGraphsOnlyReport.pdf');
                            }).error(function (data, status) {
                                console.error('requestGraphicReport error', status, data);
                            });

                    }).error(function (data, status) {
                        console.error('requestChartableData error', status, data);
                    });
            } else if ($scope.report.reportType === 'reportTypeNumeric') {
                ReportsService.requestNumericReport(indivStatFormData, 'individualStatisticsNumeric')
                    .success(function (serverResponse) {
                        ReportsService.savePdfData(serverResponse, 'IndividualStatisticsWithNumericOnlyReport.pdf');
                    }).error(function (data, status) {
                        console.error(' requestNumericReporterror:', status, data);
                    });
            } else {
                ReportsService.requestChartableData(indivStatFormData)
                    .success(function (chartableData) {
                        // produce d3 graphs as svg objects
                        var svgData = ReportsService.generateSvgObjects(chartableData);

                        var data = {
                            svgData: svgData,
                            chartableData: chartableData,
                            userReqData: indivStatFormData
                        };

                        ReportsService.requestGraphicReport(data, "individualStatisticsGraphicAndNumber")
                            .success(function (serverResponse) {
                                ReportsService.savePdfData(serverResponse, 'IndividualStatisticsGraphicAndNumberReport.pdf');
                            }).error(function (data, status) {
                                console.error('requestGraphicReport error', status, data);
                            });

                    }).error(function (data, status) {
                        console.error('requestChartableData error', status, data);
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
}]);

module.controller('avgScoresForPatientsByClinicCtrl', ['$scope', '$http', 'ReportsService', function ($scope, $http, ReportsService) {
    // place holder for selected surveys
    $scope.report = {surveysList: [], clinicsList: []};
    // Load Surveys List Service
    ReportsService.getSurveysList()
        .success(function (data) {
            $scope.surveysList = data;
        }).error(function (data, status) {
            console.error('getSurveysList error', status, data);
        });
    // Load Surveys List Service
    ReportsService.getClinicsList()
        .success(function (data) {
            $scope.clinicsList = data;
        }).error(function (data, status) {
            console.error('getClinicsList error', status, data);
        });
    $scope.save = function () {
        $scope.$broadcast('show-errors-check-validity');

        if ($scope.reportForm.$valid) {
            // create a model to represent user requested data on html form
            var avgScoresFormData = {
                fromDate: $scope.report.fromDate,
                toDate: $scope.report.toDate,
                displayOption:$scope.report.displayOption,
                surveysList: $scope.report.surveysList,
                clinicsList: $scope.report.clinicsList,
                reportType:'avgScoresForPatientsByClinic'
            };

            if ($scope.report.reportType === 'reportTypeGraph') {
                // graph report has two steps.
                // 1>   avgScoresFormData and collect the response, which is good enough to produce a numeric report
                // 2>   use the data which is good enough to produce numeric data and call it chartableData. We call it chartableData as we pass this data
                //      to generate svg Objects
                // 3>   use the svgObjects + chartableData + avgScoresFormData and ask for pdf report
                ReportsService.requestChartableData(avgScoresFormData)
                    .success(function (chartableData) {
                        // produce d3 graphs as svg objects
                        var svgData = ReportsService.generateSvgObjects(chartableData);

                        var data = {
                            svgData: svgData,
                            chartableData: chartableData,
                            userReqData: avgScoresFormData
                        };

                        ReportsService.requestGraphicReport(data, "avgScoresVetByClinicGraphic")
                            .success(function (serverResponse) {
                                ReportsService.savePdfData(serverResponse, 'AvgScoresWithGraphsOnlyReport.pdf');
                            }).error(function (data, status) {
                                console.error('requestGraphicReport error', status, data);
                            });

                    }).error(function (data, status) {
                        console.error('requestChartableData error', status, data);
                    });
            } else if ($scope.report.reportType === 'reportTypeNumeric') {
                ReportsService.requestNumericReport(avgScoresFormData, 'avgScoresVetByClinicNumeric')
                    .success(function (serverResponse) {
                        ReportsService.savePdfData(serverResponse, 'AvgScoresVetByClinicWithNumericOnlyReport.pdf');
                    }).error(function (data, status) {
                        console.error(' requestNumericReporterror:', status, data);
                    });
            } else {
                //todo implement a mix of above two options for reportTypeBoth
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
    $scope.selectAllClinic = function () {
        if ($scope.report.selectAllClinic) {
            $scope.report.clinicsList = $scope.clinicsList.map(function (item) {
                return item.clinicId;
            });
        } else {
            $scope.report.clinicsList = [];
        }
    }
}]);

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