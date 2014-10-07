/**
 * Created by Bryan Henderson on 4/16/2014.
 */
// Configure the routing. The $routeProvider will be automatically injected into
// the configurator.

// Make sure to include the `ui.router` module as a dependency.
angular.module('Editors')
    .config(
    [          '$stateProvider', '$urlRouterProvider',
        function ($stateProvider,   $urlRouterProvider) {

            /////////////////////////////
            // Redirects and Otherwise //
            /////////////////////////////

            // Use $urlRouterProvider to configure any redirects (when) and invalid urls (otherwise).
            $urlRouterProvider

                // The `when` method says if the url is ever the 1st param, then redirect to the 2nd param
                // Here we are just setting up some convenience urls.
                /*.when('/a?id', '/assessment-builder/:id')
                 */
                //.when('/c?id', '/contacts/:id')
                //.when('/user/:id', '/contacts/:id')
                //.when('/battery-select', '/')
                // If the url is ever invalid, e.g. '/asdf', then redirect to '/' aka the home state
                .otherwise('/');


            //////////////////////////
            // State Configurations //
            //////////////////////////

            // Use $stateProvider to configure your states.
            $stateProvider

                ///////////////////////
                // Home / Entry View //
                ///////////////////////

	            .state('home', {
	                url: '/',
	                templateUrl: 'resources/editors/views/home/entry.html',
	                data: {
	                    displayName: false
	                },
	                controller: 'entryController'
	            })
	
	            /** -------- SECTIONS WORKFLOW -------- **/
	            
	            .state('sections',{
	                url:'/sections',
	                templateUrl:'resources/editors/views/sections/sectionseditor.html',
	                resolve:{
	                	sections: function($rootScope, $q, SurveySectionService){
                            var deferred = $q.defer();

                            console.log('VIEW STATE SECTIONS:: Resolve sections');

                            SurveySectionService.query(SurveySectionService.setQuerySurveySectionSearchCriteria(null)).then(function (response){
                                deferred.resolve(response.getPayload());
                            }, function(responseError) {
                                $rootScope.errors.push(responseError.getMessage());
                                console.log('Sections Query Error:: ' + JSON.stringify($rootScope.errors));
                                deferred.reject(responseError.getMessage());
                            });


                            return deferred.promise;
	                	}
	                },
	                controller: 'sectionsController'
	            })

	             /** -------- END SECTIONS WORKFLOW -------- **/

	             /** -------- BATTERY WORKFLOW -------- **/

	            .state('batteries',{
	                abstract:true,
	                url:'/batteries',
	                template:'<div class="row">' +
	                            '   <div class="col-md-12" ui-view></div>'+
	                           '</div>',
	                resolve:{
	                	batteries:function($rootScope,$q,BatteryService){
	                		var deferred = $q.defer();
	                		console.log('VIEW STATE Battery:: Resolve Batteries');
	                		BatteryService.query(BatteryService.setQueryBatterySearchCriteria()).then(function(existingBatteries){
	                			console.log('Batteries:: ' + existingBatteries);
	                			deferred.resolve(existingBatteries);
	                		},function(responseError){
	                			$rootScope.errors.push(responseError.getMessage());
	                			console.log('Batteries Query Error:: ' + JSON.stringify($rootScope.errors));
	                			deferred.reject(responseError.getMessage());
	                		});
	                		return deferred.promise;
	                	},
                        sections: function($rootScope, $q, SurveySectionService){
                            var deferred = $q.defer();
                            SurveySectionService.query(SurveySectionService.setQuerySurveySectionSearchCriteria(null)).then(function (response){
                                deferred.resolve(response.getPayload());
                            }, function(responseError) {
                                $rootScope.addMessage($rootScope.createErrorMessage(responseError.getMessage()));
                                deferred.reject(responseError.getMessage());
                            });
                            return deferred.promise;
                        }
	                },


	                controller:'batteryAbstractController'
	            })
	
	            .state('batteries.batteryselection',{
	                url:'',
	                templateUrl:'resources/editors/views/batteries/batteryselect.html',
	                controller:'batterySelectionController'
	            })
	
	            .state('batteries.detail',{
	                url:'/details/:batteryId',
	                templateUrl:'resources/editors/views/batteries/batteryedit.html',
	                resolve:{
	                	battery:function($rootScope, $q, $stateParams, BatteryService){
                            var deferred = $q.defer();
                            console.log('VIEW STATE Battery:: Resolve Batteries');
                            if(Object.isDefined($stateParams.batteryId) && $stateParams.batteryId.trim().length > 0) {
                                BatteryService.query(BatteryService.setQueryBatterySearchCriteria($stateParams.batteryId)).then(function (existingBattery) {
                                    console.log('Battery:: ' + existingBattery);
                                    deferred.resolve(existingBattery);
                                }, function (responseError) {
                                    $rootScope.addMessage($rootScope.createErrorMessage(responseError.getMessage()));
                                    deferred.reject(responseError.getMessage());
                                });
                            } else {
                                deferred.resolve(new EScreeningDashboardApp.models.Battery());
                            }
                            return deferred.promise;
	                	  } 
	                },
	                controller:'batteryAddEditController'
	            })
	            
	            /** -------- END BATTERY WORKFLOW -------- **/

                //////////////////////////
                // Modules Editor Views //
                //////////////////////////
	            
	            /* ------ Workflow Frozen until completion of Formulas, Rules/Events, Templates.
	             * Nothing here should be considered canonical. - JBH
	             */
                .state('modules',{
                    abstract:true,
                    url:'/modules',
                    templateUrl:'resources/editors/views/modules/modulesabstract.html',
                    data: {
                        displayName: false
                    },
                    /*resolve: {
                        surveyId: ['$stateParams', function($stateParams){
                            return $stateParams.selectedSurveyId;
                        }]
                    },*/
                    controller:'moduleController'
                })

                .state('modules.list',{
                    url:'/list',
                    templateUrl:'resources/editors/views/modules/modulesselectview.html',
                    data: {
                        displayName: 'Modules-Editor: Selection'
                    },
                    resolve: {
                        surveys: ['$rootScope', '$q', 'SurveyService', function($rootScope, $q, SurveyService) {
                            var deferred = $q.defer();

                            SurveyService.query(SurveyService.setQuerySurveySearchCriteria(null)).then(function (existingSurveys){
                                deferred.resolve(existingSurveys);
                            }, function(responseError) {
                                $rootScope.addMessage($rootScope.createErrorMessage(responseError.getMessage()));
                                deferred.reject(responseError.getMessage());
                            });

                            return deferred.promise;
                        }]
                    },
                    controller:'modulesController'
                })

                .state('modules.detail',{
                    url:"/:selectedSurveyId/details",
                    templateUrl:'resources/editors/views/modules/moduleseditor.html',
                    data: {
                        displayName: 'Modules-Editor: Add/Edit'
                    },
                    resolve: {
                        pageQuestionItems: ['$rootScope', '$q', '$stateParams', 'SurveyPageService',  function($rootScope, $q, $stateParams, SurveyPageService) {
                            var deferred = $q.defer();

                            if($stateParams.selectedSurveyId > -1) {
                                SurveyPageService.query(SurveyPageService.setQuerySurveyPageSearchCriteria($stateParams.selectedSurveyId)).then(function (response) {
                                    var surveyPages = (Object.isArray(response.getPayload()))? response.getPayload() : Object.isDefined(response.getPayload())? [response.getPayload()] : [],
                                        pageQuestionItems = [],
                                        surveyPageConfig;

                                    surveyPages.forEach(function(surveyPage){
                                        surveyPageConfig = surveyPage.toUIObject();
                                        surveyPageConfig.questions = [];
                                        pageQuestionItems.push(new EScreeningDashboardApp.models.SurveyPageUIObjectItemWrapper({surveyPageUIObject: surveyPageConfig}));

                                        surveyPage.getQuestions().forEach(function(question){
                                             pageQuestionItems.push(new EScreeningDashboardApp.models.QuestionUIObjectItemWrapper({questionIUObject: question.toUIObject()}));
                                        });

                                    });

                                    deferred.resolve(pageQuestionItems);
                                }, function(responseError) {
                                    $rootScope.addMessage($rootScope.createErrorMessage(responseError.getMessage()));
                                    deferred.reject(responseError.getMessage());
                                });
                            } else {
                                deferred.resolve([]);
                            }

                            return deferred.promise;
                        }],
                        surveySectionDropDownMenuOptions: ['$rootScope', '$q', 'SurveySectionService',  function($rootScope, $q, SurveySectionService) {
                            var deferred = $q.defer();

                            SurveySectionService.query(SurveySectionService.setQuerySurveySectionSearchCriteria(null)).then(function (response){
                                var surveySectionDropDownMenuOptions = [];

                                response.getPayload().forEach(function (surveySection){
                                    if(Object.isDefined(surveySection)) {
                                        surveySectionDropDownMenuOptions.push(new EScreeningDashboardApp.models.MenuItemSurveySectionUIObjectWrapper(surveySection.toUIObject()));
                                    }
                                });

                                deferred.resolve(surveySectionDropDownMenuOptions);
                            }, function(responseError) {
                                $rootScope.addMessage($rootScope.createErrorMessage(responseError.getMessage()));
                                deferred.reject(responseError.getMessage());
                            });

                            return deferred.promise;
                        }]
                    },
                    controller:'addEditModuleController'
                })

                .state('modules.detail.empty',{
                    url:'/question/empty',
                    templateUrl:'resources/editors/views/questions/questionnull.html',
                    data:{displayName:false}
                })

                .state('modules.detail.selectQuestionType',{
                    url:'/question',
                    templateUrl:'resources/editors/views/questions/selectQuestionTypes.html',
                    data:{displayName:false},
                    resolve: {
                        questionTypeDropDownMenuOptions: ['$q', '$stateParams', function ($q, $stateParams) {
                            //TODO: Need to dynamically pull a unique list of validation type from the
                            //TODO: measure_validation table where measure_validation.validation_id = 1.
                            return [
                                {id: 0, name: "freeText", displayName: "Free Text"}
                                /*{id: 1, name: "readOnly", displayName: "Read-Only Text"},*/
                                /*{id: 1, name: "selectOne", displayName: "Select Single"},
                                {id: 2, name: "selectMulti", displayName: "Select Multiple"},
                                {id: 3, name: "selectOneMatrix", displayName: "Select Single Matrix"},
                                {id: 4, name: "selectMultiMatrix", displayName: "Select Multiple Matrix"},
                                {id: 5, name: "tableQuestion", displayName: "Table Question"},
                                {id: 6, name: "instruction", displayName: "Instructions"}*/
                            ];
                        }]
                    },
                    controller:'questionsController'
                })

                .state('modules.detail.editSelectOneQuestionType',{
                    url:'/selectOne/:selectedQuestionId',
                    templateUrl:'resources/editors/views/questions/selectsinglequestion.html',
                    data: {
                        displayName: 'Modules-Editor: Add/Edit - Questions, Type: Free Text/Read-Only'
                    }
                })

                .state('modules.detail.editSelectOneMatrixQuestionType',{
                    url:'/selectOneMatrix/:selectedQuestionId',
                    templateUrl:'resources/editors/views/questions/selectsinglematrixquestion.html',
                    data: {
                        displayName: 'Modules-Editor: Add/Edit - Questions, Type: Free Text/Read-Only'
                    }
                })

                .state('modules.detail.editFreeTextQuestionType', {
                    url:'/freeText/:selectedQuestionId',
                    templateUrl:'resources/editors/views/questions/freereadonlyquestion.html',
                    data: {
                        displayName: 'Modules-Editor: Add/Edit - Questions, Type: Free Text/Read-Only'
                    },
                    resolve: {
                        textFormatTypeMenuOptions: ['$q', '$stateParams', function ($q, $stateParams) {
                            //TODO: Need to dynamically pull a unique list of validation type from the
                            //TODO: measure_validation table where measure_validation.validation_id = 1.
                            return [
                                {id: null, code: null, name: "dataType", value: "email", description: null, dataType: null, createdDate: null},
                                {id: null, code: null, name: "dataType", value: "date", description: null, dataType: null, createdDate: null},
                                {id: null, code: null, name: "dataType", value: "number", description: null, dataType: null, createdDate: null}
                            ];
                        }]
                    },
                    controller:'freeTextReadOnlyQuestionController'
                })

                .state('modules.detail.editReadOnlyQuestionType',{
                    url:'/readOnly/:selectedQuestionId',
                    templateUrl:'resources/editors/views/questions/freereadonlyquestion.html',
                    data: {
                        displayName: 'Modules-Editor: Add/Edit - Questions, Type: Free Text/Read-Only'
                    },
                    resolve: {
                        textFormatTypeMenuOptions: ['$q', '$stateParams', function ($q, $stateParams) {
                            //TODO: Need to dynamically pull a unique list of validation type from the
                            //TODO: measure_validation table where measure_validation.validation_id = 1.
                            return [
                                {id: null, code: null, name: "dataType", value: "email", description: null, dataType: null, createdDate: null},
                                {id: null, code: null, name: "dataType", value: "date", description: null, dataType: null, createdDate: null},
                                {id: null, code: null, name: "dataType", value: "number", description: null, dataType: null, createdDate: null}
                            ];
                        }]
                    },
                    controller:'readOnlyQuestionController'
                })

                .state('modules.detail.editSelectMultipleQuestionType',{
                    url:'/selectMultiple/:selectedQuestionId',
                    templateUrl:'resources/editors/views/questions/selectsinglemultiplequestion.html',
                    data: {
                        displayName: 'Modules-Editor: Add/Edit - Questions, Type: Select Single/Multiple'
                    },
                    resolve: {
                        answerTypeMenuOptions: ['$q', '$stateParams', function ($q, $stateParams) {
                            //TODO: Need to dynamically pull a unique list of answer types from the database.
                            return [
                                "regular",
                                "other",
                                "none"
                            ];
                        }]
                    },
                    controller:'selectMultipleQuestionController'
                })

                .state('modules.detail.editSelectMultipleMatrixQuestionType',{
                    url:'/selectMultipleMatrix/:selectedQuestionId',
                    templateUrl:'resources/editors/views/questions/selectsinglemultiplematrixquestion.html',
                    data: {
                        displayName: 'Modules-Editor: Add/Edit - Questions, Type: Select Single/Multiple Matrix'
                    },
                    resolve: {
                        answerTypeMenuOptions: ['$q', '$stateParams', function ($q, $stateParams) {
                            //TODO: Need to dynamically pull a unique list of answer types from the database.
                            return [
                                {id: null, name: "Regular"},
                                {id: null, name: "Other"},
                                {id: null, name: "None"}
                            ];
                        }]
                    },
                    controller:'selectMultipleMatrixQuestionController'
                })

                .state('modules.detail.editTableQuestionType',{
                    url:'/table/:selectedQuestionId',
                    templateUrl:'resources/editors/views/questions/tablequestion.html',
                    data: {
                        displayName: 'Modules-Editor: Add/Edit - Questions, Type: Table Question'
                    }
                })

                .state('modules.detail.editInstructionQuestionType', {
                    url:'/instruction/:selectedQuestionId',
                    templateUrl:'resources/editors/views/questions/questioninstructions.html',
                    data: {
                        displayName: 'Modules-Editor: Add/Edit - Questions, Type: Page Instructions'
                    },
                    controller:'instructionQuestionController'
                })

                /////////////////////////////
                // Formulas Workflow Views //
                /////////////////////////////
                
                /* ---------- NOTE: Due to be refactored for Ticket #563.  No View state here is canonical. -JBH
                 * 
                 */
                .state('modules.detail.templatecaptureview',{
                    url:'/modules.addedt.templatecaptureview',
                    onEnter: function($stateParams, $state, $modal){
                        $modal.open({
                            templateUrl:'resources/editors/views/templates/templates.html',
                            windowClass:'modal modal-huge modal-content',
                            controller:['$scope', function($scope){
                                $scope.stCollapsed = true;
                                $scope.teCollapsed = false;
                                $scope.tsCollapsed = false;

                                $scope.viewTemplateSelect = function(){
                                    $scope.tsCollapsed = $scope.teCollapsed = false;
                                    $scope.stCollapsed = true;
                                }

                                $scope.viewTS = function(){
                                    $scope.teCollapsed = $scope.stCollapsed = false;
                                    $scope.tsCollapsed = true;
                                }

                                $scope.deleteClick = function(){
                                    alert('Will trigger Delete functionality.')
                                }

                                $scope.addFormula = function(){
                                    alert('Add Formula - Will trigger Formulas Flow');
                                }

                                $scope.addVariable = function(){
                                    alert('Add Variable - Will trigger Variables Flow');
                                }

                                $scope.viewTE = function(){
                                    $scope.tsCollapsed = $scope.stCollapsed = false;
                                    $scope.teCollapsed = true;
                                }

                                $scope.dismiss = function(){
                                    if ($scope.tsCollapsed){
                                       $scope.tsCollapsed=$scope.stCollapsed = false;
                                        $scope.teCollapsed = true;
                                    }
                                    else
                                        $scope.$close(true);
                                };
                                $scope.save = function(){
                                    item.update().then(function(){
                                        $scope.$close(true);
                                    })
                                };
                            }]
                        }).result.then(function(result){
                            if (result){
                                return $state.transitionTo('modules.detail');
                            }
                        })
                    }
                })
                .state('modules.detail.expressioneditor',{
                    url:'/modules.detail.expressioneditor',
                    onEnter: function($stateParams, $state, $modal) {
                        $modal.open({
                            templateUrl: 'resources/editors/views/formulas/formulas.html',
                            windowClass:'modal modal-huge modal-content',
                            controller: ['$scope', function($scope) {
                                $scope.qoCollapsed = false;
                                $scope.aoCollapsed = false;
                                $scope.csCollapsed = false;
                                $scope.fCollapsed = false;
                                $scope.fsCollapsed = false;
                                $scope.moCollapsed = true;

                                $scope.viewQOperator = function(){
                                    $scope.aoCollapsed = $scope.csCollapsed = $scope.fCollapsed = $scope.fsCollapsed = $scope.moCollapsed = false;
                                    $scope.qoCollapsed = true;
                                }

                                $scope.viewAOperator = function(){
                                    $scope.qoCollapsed = $scope.csCollapsed = $scope.fCollapsed = $scope.fsCollapsed = $scope.moCollapsed = false;
                                    $scope.aoCollapsed = true;
                                }

                                $scope.viewCOperator = function(){
                                    $scope.qoCollapsed = $scope.aoCollapsed = $scope.fCollapsed = $scope.fsCollapsed = $scope.moCollapsed = false;
                                    $scope.csCollapsed = true
                                }

                                $scope.viewFOperator = function(){
                                    $scope.qoCollapsed = $scope.aoCollapsed = $scope.csCollapsed = $scope.fsCollapsed = $scope.moCollapsed = false;
                                    $scope.fCollapsed = true;
                                }

                                $scope.viewSEditor = function(){
                                    $scope.qoCollapsed = $scope.aoCollapsed = $scope.fCollapsed = $scope.csCollapsed = $scope.moCollapsed = false;
                                    $scope.fsCollapsed = true;
                                }

                                $scope.viewMO = function(){
                                    $scope.qoCollapsed = $scope.aoCollapsed = $scope.fCollapsed = $scope.fsCollapsed = $scope.fsCollapsed = false;
                                    $scope.moCollapsed = true;
                                }

                                $scope.dismiss = function() {
                                    if (!$scope.moCollapsed){
                                        $scope.viewMO();
                                    }
                                    else
                                        $scope.$close(true);
                                };
                                $scope.save = function() {
                                    item.update().then(function() {
                                        $scope.$close(true);
                                    });
                                };
                            }]
                        }).result.then(function(result) {
                                if (result) {
                                    return $state.transitionTo("modules.detail");
                                }
                            });
                    }
                })

                .state('modules.detail.createvariable',{
                    abstract:true,
                    url:'/modules.detail.createvariable',

                    template:'<div class="col-md-12 well" style="min-height:300px;">' +
                        '<h4>Create Variable</h4>'+
                        '<div class="form-inline form-group pull-right">' +
                        '<label for="selType"  class="col-sm-2 form-label">Type</label>'+
                        '<select id="selType" ng-model="qType" ng-options="q.name for q in qTypes" name="selType" class="col-sm-2-offset col-sm-6 form-control"></select>'+
                        '</div>'+
                        '<div class="row">' +
                        '<div style="min-height:220px;" ui-view></div>' +
                        '</div>'+
                        '</div>',
                    controller: ['$rootScope','$scope','$state',
                        function($rootScope,$scope,$state) {
                        $scope.dismiss = function() {
                            $scope.$close(true);
                        };
                        $scope.save = function() {
                            item.update().then(function() {
                                $scope.$close(true);
                            });
                        };

                        $scope.qTypes = [
                            {name:"Question", value:1},
                            {name:"Answer", value:2},
                            {name:"Custom", value:3},
                            {name:"Formula", value:4}];

                        $scope.qType = $scope.qTypes[0];

                        $scope.$watch( 'qType', function( qType ) {
                            var url = 'modules.detail.createvariable.questionvariable';
                            switch(qType.name){
                                case 'Question':
                                    url = 'modules.detail.createvariable.questionvariable';
                                    break;
                                case 'Answer':
                                    url = 'modules.detail.createvariable.answervariable';
                                    break;
                                case 'Custom':
                                    url = 'modules.detail.createvariable.customvariable';
                                    break;
                                case 'Formula':
                                    url = 'modules.detail.createvariable.formulavariable';
                                    break;
                            }
                            $state.go(url);
                        });

                        $scope.goToAddEdit = function(){
                            $state.go('modules.detail');
                        }
                    }]
                })

                .state('modules.detail.createvariable.questionvariable',{
                    url:'',
                    template:'<div class="row">' +
                                '<div class="col-sm-10 well">' +
                                    '<h4>Question Variable</h4>'+
                                    '<div class="form-inline form group pull-right">' +
                                        '<button class="btn btn-primary" ng-click="goToModulesEdit">Done</button>'+
                                    '</div>'+
                                '</div>'+
                             '</div>',
                    controller:['$rootScope','$scope','$state',
                    function($rootScope, $scope, $state){
                        $scope.goToModulesEdit = function(){
                            $state.go('modules.detail');
                        }
                    }]
                })

                .state('modules.detail.createvariable.customvariable',{
                    url:'/modules.detail.createvariable.customvariable',
                    template:'<div class="row">' +
                                '<div class="col-sm-10 well">' +
                                    '<h4>Custom Variable</h4>'+
                                    '<div class="form-inline form group pull-right">' +
                                        '<button class="btn btn-primary" ng-click="goToModulesEdit">Done</button>'+
                                    '</div>'+
                                 '</div>'+
                              '</div>',
                    controller:['$rootScope','$scope','$state',
                        function($rootScope, $scope, $state) {
                            $scope.goToModulesEdit = function () {
                                $state.go('modules.detail');
                            }
                        }]
                })

                .state('modules.detail.createvariable.answervariable',{
                    url:'/modules.detail.createvariable.answervariable',
                    template:'<div class="row">' +
                                '<div class="col-sm-10 well">' +
                                    '<h4>Answer Variable</h4>'+
                                    '<div class="form-inline form group pull-right">' +
                                        '<button class="btn btn-primary" ng-click="goToModulesEdit">Done</button>'+
                                    '</div>'+
                                '</div>'+
                             '</div>',
                    controller:['$rootScope','$scope','$state',
                        function($rootScope, $scope, $state) {
                            $scope.goToModulesEdit = function () {
                                $state.go('modules.detail');
                            }
                        }]
                })

                .state('modules.detail.createvariable.formulavariable',{
                    url:'/moudles-editor.addedit.createvariable.formulavariable',
                    template:'<div class="row">' +
                                '<div class="col-sm-10 well">' +
                                    '<h4>Formula Variable</h4>'+
                                    '<div class="form-inline form group pull-right">' +
                                        '<button class="btn btn-primary" ng-click="goToModulesEdit">Done</button>'+
                                    '</div>'+
                                '</div>'+
                            '</div>',
                    controller:['$rootScope','$scope','$state',
                        function($rootScope, $scope, $state) {
                            $scope.goToModulesEdit = function () {
                                $state.go('modules.detail');
                            }
                        }]
                })

                /////////////////////////
                // Rules/Events Views //
                ////////////////////////
                .state('modules.detail.rulesandevents',{
                    url:'/modules.detail.rulesandevents',
                    onEnter: function($stateParams, $state, $modal) {
                        $modal.open({
                            templateUrl: 'resources/editors/views/rulesevents/rulesevents.html',
                            windowClass:'modal modal-huge modal-content',
                            controller: ['$rootScope','$scope','$state', function ($rootScope,$scope,$state) {
                                $scope.rsCollapsed = true;
                                $scope.hfCollapsed = false;
                                $scope.scCollapsed = false;
                                $scope.srCollapsed = false;

                                $scope.viewSEditor = function(){
                                    $scope.hfCollapsed = $scope.scCollapsed = $scope.srCollapsed = false;
                                    $scope.rsCollapsed = true;
                                }

                                $scope.viewHFSelect = function(){
                                    $scope.rsCollapsed = $scope.scCollapsed = $scope.srCollapsed = false;
                                    $scope.hfCollapsed = true;

                                }

                                $scope.viewSCSelect = function(){
                                    $scope.rsCollapsed = $scope.hfCollapsed = $scope.srCollapsed = false;
                                    $scope.scCollapsed = true;
                                }

                                $scope.viewSRSelect = function(){
                                    $scope.rsCollapsed = $scope.hfCollapsed = $scope.scCollapsed = false;
                                    $scope.srCollapsed = true;
                                }

                                $scope.dismiss = function () {
                                    if ($scope.hfCollapsed || $scope.scCollapsed || $scope.srCollapsed)
                                    {
                                        $scope.hfCollapsed = $scope.scCollapsed = $scope.srCollapsed = false;
                                        $scope.rsCollapsed = true;
                                    }
                                    else
                                        $scope.$close(true);
                                };
                                $scope.save = function () {
                                    item.update().then(function () {
                                        $scope.$close(true);
                                    });
                                };

                            }]
                        }).result.then(function (result) {
                                if (result) {
                                    return $state.transitionTo("modules.detail");
                                }
                            });
                    }
                })

                .state('modules.detail.statementeditor',{
                        url:'/modules.detail.rules.statementeditor',
                        template:'<div class="well"><h4>Rules/Events Statement Editor View</h4></div>',
                        controller:['$rootScope','$scope','$state',
                        function($rootScope,$scope,$state){

                        }]
                    })

                .state('modules.detail.select',{
                    url:'/modules.detail.rules.select',
                    template:'<div class="well"><h4>Rules/Events Select Item View</h4></div>',
                    controller:['$rootScope','$scope','$state',
                        function($rootScope,$scope,$state){

                    }]
                })

                .state('modules.detail.healthfactor',{
                    url:'modules.detail.healthfactor',
                    template:'<div class="well"><h4>Rules/Events Select Health Factor</h4></div>',
                    controller:['$rootScope','$scope','$state',
                    function($rootScope,$scope,$state){

                    }]
                })

                .state('modules.detail.selectconsult',{
                    url:'/modules.detail.selectconsult',
                    template:'<div class="well"><h4>Rules/Events Select Consult</h4></div>',
                    controller:['$rootScope','$scope','$state',
                    function($rootScope,$scope,$state){

                    }]
                })

                .state('modules.detail.ruletriggeredalert',{
                    url:'/modules.detail.ruletriggeredalert',
                    template:'<div class="well"><h4>Rules/Events Select Rule-Triggered Alert</h4></div>',
                    controller:['$rootScope','$scope','$state',
                    function($rootScope,$scope,$state){

                    }]
                })

                //////////////////////////////
                // Templates Workflow Views //
                //////////////////////////////
                //TODO: relocate this up with the other module states
                .state('modules.detail.templatelist',{
                    url:'/templatelist',  //remove the survey ID once we relocate this state when we add templatelist to the url it should look like module/45/templatelist but the state hierarchy is incorrect currently
                    data: {
                        displayName: 'Manage Templates'
                    },
                    templateUrl:'resources/editors/views/templates/templatesselection.html',
                    controller: 'templateListController',
                    resolve: {
                      templateTypes: ['$rootScope', '$stateParams', '$q', 'TemplateService', function($rootScope, $stateParams, $q, TemplateService) {
                          var deferred = $q.defer();
                          
                          if(Object.isDefined($stateParams) 
                                  && Object.isDefined($stateParams.selectedSurveyId)
                                  && $stateParams.selectedSurveyId > -1){
                          
                              TemplateService
                              	.getTypes({surveyId: $stateParams.selectedSurveyId})
                              	.then(
                              	    function (templateTypes){
                              	        deferred.resolve(templateTypes);
                              		}, 
                              		function(responseError) {
                              		    $rootScope.errors.push(responseError.getMessage());
                              		    console.log('Template Type Query Error:: ' + JSON.stringify($rootScope.errors));
                              		    deferred.reject(responseError.getMessage());
                              		});
                                  
//                              var templateTypes = [
//                                   	new EScreeningDashboardApp.models.TemplateType(
//                                   	        {templateTypeId:1, templateTypeName:"Test type 1", templateTypeDescription:"template type that doesn't exsit", givenTemplateExists:false }),
//                                   	new EScreeningDashboardApp.models.TemplateType(
//                                   	        {templateTypeId:2, templateTypeName:"Test type 2", templateTypeDescription:"template type that does exist", givenTemplateExists:true }),
//                                                   ];
//                              deferred.resolve(templateTypes);
                              
                              return deferred.promise;
                          }
                         
                          //TODO: rootscope is undefined here?
                          $rootScope.errors.push("No valid context found. Please contact support.");
                          console.log("Template Type List Error:: either a Module ID or a Battery ID must be given."); 
                          return $state.go('home');
                      }]
                  }
                })

                .state('templates.templateeditorview', {
                    url:'/templates.templateeditorview',
                    data: {
                        displayName: 'Templates: Editor'
                    },
                    /*template:'<div class="col-md-12" style="background-color:#ebebeb;min-height:400px;">' +
                        '<h3>Template Editor View</h3>' +
                        '<div class="form-inline form-group" style="background-color:#245269;">' +
                        '<button class="btn btn-primary" ng-click="goToAddEdit();">Add/Edit Template Statements</button>' +
                        '<button class="btn btn-success" ng-click="goToTemplateList();">Done (Return to Template List)</button>'+
                        '</div>' +
                        '</div>',*/
                    templateUrl:'resources/editors/views/templates/templateeditor.html',
                    controller:['$rootScope', '$scope', '$state',
                        function($rootScope, $scope, $state){
                            $scope.goToAddEdit = function(){
                                $state.go('templates.statementeditorview');
                            }

                            $scope.goToTemplateList = function(){
                                $state.go('templates.templatelistview');
                            }
                        }]
                })

                .state('templates.statementeditorview',{
                    url:'/templates.statementeditorview',
                    /*template:'<div class="col-md-12" style="background-color:#ebebeb;min-height:400px;">' +
                        '<h3>Templates Statement Editor View</h3>' +
                        '<div class="form-inline form-group" style="background-color:#245269;">' +
                        '<button class="btn btn-primary" ng-click="goToAddEdit();">Return to Template Editor</button>' +
                        '</div>' +
                        '</div>',*/
                    data: {
                        displayName: 'Templates: Statement Editor'
                    },
                    templateUrl:'resources/editors/views/templates/templatestatementeditor.html',
                    controller:['$rootScope', '$scope', '$state',
                        function($rootScope, $scope, $state){
                            $scope.goToAddEdit = function(){
                                $state.go('templates.templateeditorview');
                            }

                            $scope.goToSelection = function(){
                                $state.go('templates.templatelistview');
                            }
                        }]
                })
            /*.state('contacts', {

             // With abstract set to true, that means this state can not be explicitly activated.
             // It can only be implicitly activated by activating one of it's children.
             //abstract: true,

             // This abstract state will prepend '/contacts' onto the urls of all its children.
             url: '/contacts',

             // Example of loading a template from a file. This is also a top level state,
             // so this template file will be loaded and then inserted into the ui-view
             // within index.html.
             templateUrl: 'contacts.html',

             // Use `resolve` to resolve any asynchronous controller dependencies
             // *before* the controller is instantiated. In this case, since contacts
             // returns a promise, the controller will wait until contacts.all() is
             // resolved before instantiation. Non-promise return values are considered
             // to be resolved immediately.
             resolve: {
             contacts: ['contacts',
             function( contacts){
             return contacts.all();
             }]
             },

             // You can pair a controller to your template. There *must* be a template to pair with.
             controller: ['$scope', '$state', 'contacts', 'utils',
             function (  $scope,   $state,   contacts,   utils) {

             // Add a 'contacts' field in this abstract parent's scope, so that all
             // child state views can access it in their scopes. Please note: scope
             // inheritance is not due to nesting of states, but rather choosing to
             // nest the templates of those states. It's normal scope inheritance.
             $scope.contacts = contacts;

             $scope.goToRandom = function () {
             var randId = utils.newRandomKey($scope.contacts, "id", $state.params.contactId);

             // $state.go() can be used as a high level convenience method
             // for activating a state programmatically.
             $state.go('contacts.detail', { contactId: randId });
             };
             }]
             })

             /////////////////////
             // Contacts > List //
             /////////////////////

             // Using a '.' within a state name declares a child within a parent.
             // So you have a new state 'list' within the parent 'contacts' state.
             .state('contacts.list', {

             // Using an empty url means that this child state will become active
             // when its parent's url is navigated to. Urls of child states are
             // automatically appended to the urls of their parent. So this state's
             // url is '/contacts' (because '/contacts' + '').
             url: '',

             // IMPORTANT: Now we have a state that is not a top level state. Its
             // template will be inserted into the ui-view within this state's
             // parent's template; so the ui-view within contacts.html. This is the
             // most important thing to remember about templates.
             templateUrl: 'contacts.list.html'
             })

             ///////////////////////
             // Contacts > Detail //
             ///////////////////////

             // You can have unlimited children within a state. Here is a second child
             // state within the 'contacts' parent state.
             .state('contacts.detail', {

             // Urls can have parameters. They can be specified like :param or {param}.
             // If {} is used, then you can also specify a regex pattern that the param
             // must match. The regex is written after a colon (:). Note: Don't use capture
             // groups in your regex patterns, because the whole regex is wrapped again
             // behind the scenes. Our pattern below will only match numbers with a length
             // between 1 and 4.

             // Since this state is also a child of 'contacts' its url is appended as well.
             // So its url will end up being '/contacts/{contactId:[0-9]{1,8}}'. When the
             // url becomes something like '/contacts/42' then this state becomes active
             // and the $stateParams object becomes { contactId: 42 }.
             url: '/{contactId:[0-9]{1,4}}',

             // If there is more than a single ui-view in the parent template, or you would
             // like to target a ui-view from even higher up the state tree, you can use the
             // views object to configure multiple views. Each view can get its own template,
             // controller, and resolve data.

             // View names can be relative or absolute. Relative view names do not use an '@'
             // symbol. They always refer to views within this state's parent template.
             // Absolute view names use a '@' symbol to distinguish the view and the state.
             // So 'foo@bar' means the ui-view named 'foo' within the 'bar' state's template.
             views: {

             // So this one is targeting the unnamed view within the parent state's template.
             '': {
             templateUrl: 'contacts.detail.html',
             controller: ['$scope', '$stateParams', 'utils',
             function (  $scope,   $stateParams,   utils) {
             $scope.contact = utils.findById($scope.contacts, $stateParams.contactId);
             }]
             },

             // This one is targeting the ui-view="hint" within the unnamed root, aka index.html.
             // This shows off how you could populate *any* view within *any* ancestor state.
             'hint@': {
             template: 'This is contacts.detail populating the "hint" ui-view'
             },

             // This one is targeting the ui-view="menu" within the parent state's template.
             'menuTip': {
             // templateProvider is the final method for supplying a template.
             // There is: template, templateUrl, and templateProvider.
             templateProvider: ['$stateParams',
             function (        $stateParams) {
             // This is just to demonstrate that $stateParams injection works for templateProvider.
             // $stateParams are the parameters for the new state we're transitioning to, even
             // though the global '$stateParams' has not been updated yet.
             return '<hr><small class="muted">Contact ID: ' + $stateParams.contactId + '</small>';
             }]
             }
             }
             })

             //////////////////////////////
             // Contacts > Detail > Item //
             //////////////////////////////

             .state('contacts.detail.item', {

             // So following what we've learned, this state's full url will end up being
             // '/contacts/{contactId}/item/:itemId'. We are using both types of parameters
             // in the same url, but they behave identically.
             url: '/item/:itemId',
             views: {

             // This is targeting the unnamed ui-view within the parent state 'contact.detail'
             // We wouldn't have to do it this way if we didn't also want to set the 'hint' view below.
             // We could instead just set templateUrl and controller outside of the view obj.
             '': {
             templateUrl: 'contacts.detail.item.html',
             controller: ['$scope', '$stateParams', '$state', 'utils',
             function (  $scope,   $stateParams,   $state,   utils) {
             $scope.item = utils.findById($scope.contact.items, $stateParams.itemId);

             $scope.edit = function () {
             // Here we show off go's ability to navigate to a relative state. Using '^' to go upwards
             // and '.' to go down, you can navigate to any relative state (ancestor or descendant).
             // Here we are going down to the child state 'edit' (full name of 'contacts.detail.item.edit')
             $state.go('.edit', $stateParams);
             };
             }]
             },

             // Here we see we are overriding the template that was set by 'contact.detail'
             'hint@': {
             template: ' This is contacts.detail.item overriding the "hint" ui-view'
             }
             }
             })

             /////////////////////////////////////
             // Contacts > Detail > Item > Edit //
             /////////////////////////////////////

             // Notice that this state has no 'url'. States do not require a url. You can use them
             // simply to organize your application into "places" where each "place" can configure
             // only what it needs. The only way to get to this state is via $state.go (or transitionTo)
             .state('contacts.detail.item.edit', {
             views: {

             // This is targeting the unnamed view within the 'contact.detail' state
             // essentially swapping out the template that 'contact.detail.item' had
             // had inserted with this state's template.
             '@contacts.detail': {
             templateUrl: 'contacts.detail.item.edit.html',
             controller: ['$scope', '$stateParams', '$state', 'utils',
             function (  $scope,   $stateParams,   $state,   utils) {
             $scope.item = utils.findById($scope.contact.items, $stateParams.itemId);
             $scope.done = function () {
             // Go back up. '^' means up one. '^.^' would be up twice, to the grandparent.
             $state.go('^', $stateParams);
             };
             }]
             }
             }
             })*/
}]);
