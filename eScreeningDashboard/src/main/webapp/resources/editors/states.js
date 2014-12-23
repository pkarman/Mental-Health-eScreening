/**
 * Created by Bryan Henderson on 4/16/2014.
 */
// Configure the routing. The $routeProvider will be automatically injected into
// the configurator.

// Make sure to include the 'ui.router' module as a dependency.
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
                                $rootScope.addMessage($rootScope.createErrorMessage(responseError.getMessage()));
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
	                            '   <div class="col-md-12" ui-view style="min-height:650px;"></div>'+
	                           '</div>',
	                resolve:{
	                	batteries:function($rootScope,$q,BatteryService){
	                		var deferred = $q.defer();
	                		console.log('VIEW STATE Battery:: Resolve Batteries');
	                		BatteryService.query(BatteryService.setQueryBatterySearchCriteria()).then(function(existingBatteries){
	                			deferred.resolve(existingBatteries);
	                		},function(responseError){
	                			$rootScope.addMessage($rootScope.createErrorMessage(responseError.getMessage()));
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
	
	            .state('batteries.list',{
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
                            if(Object.isDefined($stateParams.batteryId) && $stateParams.batteryId.trim().length > 0) {
                                BatteryService.query(BatteryService.setQueryBatterySearchCriteria($stateParams.batteryId)).then(function (existingBattery) {
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
	            
	            .state('batteries.templates',{
                    url:'/:relatedObjId/:relatedObjName/templates/:saved',
                    templateUrl:'resources/editors/views/templates/templatesselection.html',
                    resolve: {
                        templateTypes: ['$rootScope', '$stateParams', '$q', 'TemplateTypeService', function($rootScope, $stateParams, $q, TemplateTypeService) {
                            
                            var deferred = $q.defer();

                            if(Object.isDefined($stateParams) &&
                                Object.isDefined($stateParams.relatedObjId) &&
                                $stateParams.relatedObjId > -1) {

                                TemplateTypeService.getTemplateTypes({batteryId: $stateParams.relatedObjId}).then(function (templateTypes) {
                                    deferred.resolve(templateTypes);
                                }, function(responseError) {
                                    deferred.reject(responseError.data);
                                });
                            }

                            return deferred.promise;
                        }],
                        relatedObj: ['$stateParams', '$q', function($stateParams, $q) {
                        	var deferred = $q.defer();
                        	deferred.resolve({
                                id : $stateParams.relatedObjId,
                                name : decodeURIComponent($stateParams.relatedObjName),
                                type: "battery"
                            });
                        	return deferred.promise;
                        }]
                    },
                    controller: 'templateListController'
                })
                
                .state('batteries.templateeditor', {
                    url: "/:relatedObjId/:relatedObjName/type/:typeId/template/:templateId",
                    templateUrl: 'resources/editors/views/templates/templateeditor.html',
                    controller: "templateEditorController",
                    resolve: {
                        assessmentVariableService: ['AssessmentVariableService', function (AssessmentVariableService) {
                            return AssessmentVariableService;
                        }],
                        template: ['$rootScope', '$stateParams', '$q', 'TemplateService', 'TemplateTypeService',
                            function ($rootScope, $stateParams, $q, TemplateService, TemplateTypeService) {
                                var deferred = $q.defer();
                                if (Object.isDefined($stateParams)
                                    && Object.isDefined($stateParams.relatedObjId)
                                    && $stateParams.relatedObjId > -1
                                    && Object.isDefined($stateParams.typeId)) {
                                    
                                    if(Object.isDefined($stateParams.templateId) 
                                            && $stateParams.templateId != -1 
                                            && $stateParams.templateId.length > 0){
                                        console.log("Getting template from server with ID: " + $stateParams.templateId);
                                        
                                        TemplateService.get($stateParams.templateId).then(function (template) {
                                            deferred.resolve(template);
                                        });
                                    }
                                    else{
                                        console.log("Creating empty template for battery " + $stateParams.relatedObjName + " of template type " + $stateParams.typeId);
                                        var selectedTemplateType = TemplateTypeService.getSelectedType();
                                        if(Object.isDefined(selectedTemplateType)){
                                            var emptyTemplate =  new EScreeningDashboardApp.models.Template({type: selectedTemplateType});
                                            deferred.resolve(emptyTemplate);
                                        }
                                        else {
                                            console.log("There is no currently selected template type.");
                                            deferred.resolve({});
                                        }
                                    }
                                }
                                return deferred.promise;
                            }],
                            relatedObj: ['$stateParams', '$q', function($stateParams, $q) {
                            	var deferred = $q.defer();
                            	deferred.resolve({
                                    id : $stateParams.relatedObjId,
                                    name : decodeURIComponent($stateParams.relatedObjName),
                                    type: "battery"
                                });
                            	return deferred.promise;
                            }]
                    },
                    onExit: function (AssessmentVariableService) {
                        console.log("leaving batteries.templateeditor state.");
                        AssessmentVariableService.clearCachedResults();
                    }
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

                .state('modules.templates',{
                    url:'/:relatedObjId/:relatedObjName/templates/:saved',
                    templateUrl:'resources/editors/views/templates/templatesselection.html',
                    resolve: {
                        templateTypes: ['$rootScope', '$stateParams', '$q', 'TemplateTypeService', function($rootScope, $stateParams, $q, TemplateTypeService) {
                            
                            var deferred = $q.defer();

                            if(Object.isDefined($stateParams) &&
                                Object.isDefined($stateParams.relatedObjId) &&
                                $stateParams.relatedObjId > -1) {

                                TemplateTypeService.getTemplateTypes({surveyId: $stateParams.relatedObjId}).then(function (templateTypes) {
                                    deferred.resolve(templateTypes);
                                }, function(responseError) {
                                    deferred.reject(responseError.data);
                                });
                            }

                            return deferred.promise;
                        }],
                        relatedObj: ['$stateParams', '$q', function($stateParams, $q) {
                        	var deferred = $q.defer();
                        	deferred.resolve({
                                id : $stateParams.relatedObjId,
                                name : decodeURIComponent($stateParams.relatedObjName),
                                type: "module"
                            });
                        	return deferred.promise;
                        }]
                    },
                    controller: 'templateListController'
                })
                
                .state('modules.templateeditor', {
                    url: "/:relatedObjId/:relatedObjName/type/:typeId/template/:templateId",
                    templateUrl: 'resources/editors/views/templates/templateeditor.html',
                    controller: "templateEditorController",
                    resolve: {
                        assessmentVariableService: ['AssessmentVariableService', function (AssessmentVariableService) {
                            return AssessmentVariableService;
                        }],
                        template: ['$rootScope', '$stateParams', '$q', 'TemplateService', 'TemplateTypeService',
                            function ($rootScope, $stateParams, $q, TemplateService, TemplateTypeService) {
                                var deferred = $q.defer();
                                if (Object.isDefined($stateParams)
                                    && Object.isDefined($stateParams.relatedObjId)
                                    && $stateParams.relatedObjId > -1
                                    && Object.isDefined($stateParams.typeId)) {
                                    
                                    if(Object.isDefined($stateParams.templateId) 
                                            && $stateParams.templateId != -1 
                                            && $stateParams.templateId.length > 0){
                                        console.log("Getting template from server with ID: " + $stateParams.templateId);
                                        
                                        TemplateService.get($stateParams.templateId).then(function (template) {
                                            deferred.resolve(template);
                                        });
                                    }
                                    else{
                                        console.log("Creating empty template for module " + $stateParams.relatedObjName + " of template type " + $stateParams.typeId);
                                        var selectedTemplateType = TemplateTypeService.getSelectedType();
                                        if(Object.isDefined(selectedTemplateType)){
                                            var emptyTemplate =  new EScreeningDashboardApp.models.Template({type: selectedTemplateType});
                                            deferred.resolve(emptyTemplate);
                                        }
                                        else {
                                            console.log("There is no currently selected template type.");
                                            deferred.resolve({});
                                        }
                                    }
                                }
                                return deferred.promise;
                            }],
                            relatedObj: ['$stateParams', '$q', function($stateParams, $q) {
                            	var deferred = $q.defer();
                            	deferred.resolve({
                                    id : $stateParams.relatedObjId,
                                    name : decodeURIComponent($stateParams.relatedObjName),
                                    type: "module"
                                });
                            	return deferred.promise;
                            }]
                    },
                    onExit: function (AssessmentVariableService) {
                        console.log("leaving template.moduleeditor state.");
                        AssessmentVariableService.clearCachedResults();

                    }
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

                .state('template',{
                    abstract:true,
                    url:'/modules',
                    templateUrl:'resources/editors/views/modules/modulesabstract.html',
                    data: {
                        displayName: false
                    },
                    controller:['$rootScope','$scope','$state',
                    function($rootScope,$scope,$state){  /*placeholder*/  }]
                })

}]);
