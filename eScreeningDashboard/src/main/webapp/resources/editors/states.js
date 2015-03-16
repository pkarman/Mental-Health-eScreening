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

				/** -------- RULES WORKFLOW -------- **/

				.state('rules',{
					url:'/rules',
					templateUrl:'resources/editors/views/rules/rules.html',
					controller: 'RulesController',
					resolve: {
						rules: ['RuleService', function(RuleService) {
							return RuleService.getList();
						}]
					}
				})

				.state('rules.detail',{
					url:'/:id',
					templateUrl:'resources/editors/views/rules/rules.detail.html',
					controller: 'RulesDetailController',
					resolve: {
						rule: function($stateParams, rules, RuleService) {
							return ($stateParams.id) ? rules.get($stateParams.id) : RuleService.one();
						},
						consults: ['EventService', function(EventService) {
							return EventService.getList({type: 1});
						}],
						healthFactors: ['EventService', function(EventService) {
							return EventService.getList({type: 2});
						}],
						dashboardAlerts: ['EventService', function(EventService) {
							return EventService.getList({type: 3});
						}],
						questions: ['EventService', function(EventService) {
							return EventService.getList({type: 4});
						}]
					}
				})

				/** -------- END RULES WORKFLOW -------- **/
	
	            /** -------- SECTIONS WORKFLOW -------- **/
	            
	            .state('sections',{
	                url:'/sections',
	                templateUrl:'resources/editors/views/sections/sectionseditor.html',
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
                    batteries:function($q, MessageFactory, BatteryService){
	                		var deferred = $q.defer();
	                		console.log('VIEW STATE Battery:: Resolve Batteries');
	                		BatteryService.query(BatteryService.setQueryBatterySearchCriteria()).then(function(existingBatteries){
	                			deferred.resolve(existingBatteries);
	                		},function(responseError){

                            MessageFactory.set('danger', responseError.getMessage(), true, true);
	                			console.log('Batteries Query Error:: ' + JSON.stringify($rootScope.errors));
	                			deferred.reject(responseError.getMessage());
	                		});
	                		return deferred.promise;
	                	},
                    sections: ['ManageSectionService',  function(ManageSectionService) {
                        return ManageSectionService.getList();
                    }]
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
                    battery:function($q, $stateParams, MessageFactory, BatteryService){
                            var deferred = $q.defer();
                            if(Object.isDefined($stateParams.batteryId) && $stateParams.batteryId.trim().length > 0) {
                                BatteryService.query(BatteryService.setQueryBatterySearchCriteria($stateParams.batteryId)).then(function (existingBattery) {
                                    deferred.resolve(existingBattery);
                                }, function (responseError) {
                                MessageFactory.set('danger', responseError.getMessage(), true, true);
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
                    controller: 'ModulesTemplatesController'
                })
                
                .state('batteries.templateeditor', {
                    url: '/:relatedObjId/:relatedObjName/type/:typeId/template/:templateId',
                    templateUrl: 'resources/editors/views/templates/templateeditor.html',
                    controller: 'ModulesTemplatesEditController',
                    resolve: {
                        template: ['$stateParams', '$q', 'MessageFactory', 'TemplateService', 'TemplateTypeService',
                            function ($stateParams, $q, MessageFactory, TemplateService, TemplateTypeService) {
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
                                        }, function(response) {
                                            MessageFactory.set('danger', response.data.error.errorMessages[0].description || response.data.error.errorMessages[0].developerMessage || "There was an error", true, false);
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
                .state('modules',{
                    url:'/modules',
                templateUrl: 'resources/editors/views/modules/modules.html',
                    data: {
                        displayName: false
                    },
                resolve: {
                    surveys: ['SurveyService', function(SurveyService) {
                        return SurveyService.getList();
                    }]
                },
                controller:'ModulesController'
                })

            .state('modules.detail', {
                url: '/details/:surveyId',
                templateUrl: 'resources/editors/views/modules/modules.detail.html',
                    data: {
                    displayName: 'Modules-Editor: Add/Edit'
                    },
                    resolve: {
                    survey: ['$stateParams', 'SurveyService', 'surveys', function($stateParams, SurveyService, surveys) {
                        return ($stateParams.surveyId) ? surveys.get($stateParams.surveyId) : SurveyService.one();
                    }],
                    surveySections: ['ManageSectionService',  function(ManageSectionService) {
                        return ManageSectionService.getList();
                    }],
					clinicalReminders: ['ClinicalReminderService', function(ClinicalReminderService) {
						return ClinicalReminderService.getList();
					}],
					surveyPages: ['$stateParams', 'survey', function($stateParams, survey) {
						return (survey.id) ? survey.getList('pages') : survey.all('pages');
					}]
                },
                controller: 'ModulesDetailController'
            })

            .state('modules.detail.list', {
                url:'/list',
                templateUrl:'resources/editors/views/modules/modules.detail.list.html',
                data: { displayName:false },
                resolve: {
                    questionTypes: ['Restangular', function (Restangular) {
                        //return Restangular.all("measureType").getList();

                        return [
                            {id: 0, name: "freeText", displayName: "Free Text"},
                            {id: 1, name: "selectOne", displayName: "Select One"},
                            {id: 2, name: "selectMulti", displayName: "Select Multi"},
                            {id: 3, name: "selectOneMatrix", displayName: "Select One Matrix"},
                            {id: 4, name: "selectMultiMatrix", displayName: "Select Multi Matrix"},
                            {id: 5, name: "tableQuestion", displayName: "Table"},
                            {id: 6, name: "instruction", displayName: "Instructions"}
                        ];

                        }]
                    },
                controller:'ModulesDetailListController'
                })

            .state('modules.detail.question', {
                params: {'questionId': {}},
                abstract: true,
                template: '<div ui-view></div>',
                controller: ['$scope', '$state', '$stateParams', function($scope, $state, $stateParams) {
                    if (!$scope.question) {
						$state.go('modules.detail', { surveyId: $stateParams.surveyId });
                    }
                }]
            })

            .state('modules.detail.question.text', {
                url:'/text/:questionId',
                template:'<text-question question="question"></text-question>',
                data: {
                    displayName: 'Modules-Editor: Add/Edit - Questions, Type: Free Text/Read-Only'
                }
            })

            .state('modules.detail.question.simple', {
                url:'/simple/:questionId',
                template:'<simple-question question="question" survey="survey"></simple-question>',
                data: {
                    displayName: 'Modules-Editor: Add/Edit - Questions, Type: Simple'
                }
            })

            .state('modules.detail.question.matrix', {
                url:'/matrix/:questionId',
                template:'<matrix-question question="question" survey="survey"></matrix-question>',
                data: {
                    displayName: 'Modules-Editor: Add/Edit - Questions, Type: Matrix'
                }
            })

            .state('modules.detail.question.instructions', {
                url:'/instructions/:questionId',
                templateUrl:'resources/editors/views/modules/modules.detail.instructions.html',
                data: {
                    displayName: 'Modules-Editor: Add/Edit - Questions, Type: Page Instructions'
                },
                controller:'ModulesDetailInstructionsController'
            })

            .state('modules.detail.question.table', {
                url:'/table/:questionId',
                templateUrl:'resources/editors/views/modules/modules.detail.table.html',
                data: {
                    displayName: 'Modules-Editor: Add/Edit - Questions, Type: Table Question'
                },
                controller: 'ModulesDetailTableController'
            })

                .state('modules.templates',{
                url:'/:selectedSurveyId/:selectedSurveyName/templates/:saved',
                data: {
                    displayName: 'Manage Templates'
                },
                    templateUrl:'resources/editors/views/templates/templatesselection.html',
                    resolve: {
                        templateTypes: ['$rootScope', '$stateParams', '$q', 'TemplateTypeService', function($rootScope, $stateParams, $q, TemplateTypeService) {
                            
                            var deferred = $q.defer();

                            if(Object.isDefined($stateParams) &&
                            Object.isDefined($stateParams.selectedSurveyId) &&
                            $stateParams.selectedSurveyId > -1) {

                            TemplateTypeService.getTemplateTypes({surveyId: $stateParams.selectedSurveyId}).then(function (templateTypes) {
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
                                id : $stateParams.selectedSurveyId,
                                name : decodeURIComponent($stateParams.selectedSurveyName),
                                type: "module"
                            });
                        	return deferred.promise;
                        }]
                    },
                controller: 'ModulesTemplatesController'
                })
                .state('modules.formulasList',{
                    url:'/:moduleId/module_formulas_list',
                    templateUrl:'resources/editors/views/formulas/module_formulas_list.html',
                    controller: 'ModuleFormulasListController'
                })
                .state('modules.formulasEdit',{
                    url:'/module_formulas_edit',
                    templateUrl:'resources/editors/views/formulas/module_formulas_edit.html',
                    controller: 'ModuleFormulasEditController'
                })

                .state('modules.templateeditor', {
                url: "/:selectedSurveyId/:selectedSurveyName/type/:typeId/template/:templateId",
                    templateUrl: 'resources/editors/views/templates/templateeditor.html',
                data: {
                    displayName: 'Template Editor'
                },
                controller: "ModulesTemplatesEditController",
                    resolve: {
                    template: ['$stateParams', '$q', 'MessageFactory', 'TemplateService', 'TemplateTypeService', function ($stateParams, $q, MessageFactory, TemplateService, TemplateTypeService) {
                                var deferred = $q.defer();
                                if (Object.isDefined($stateParams)
                            && Object.isDefined($stateParams.selectedSurveyId)
                            && $stateParams.selectedSurveyId > -1
                                    && Object.isDefined($stateParams.typeId)) {
                                    
                                    if(Object.isDefined($stateParams.templateId) 
                                            && $stateParams.templateId != -1 
                                            && $stateParams.templateId.length > 0){
                                        console.log("Getting template from server with ID: " + $stateParams.templateId);
                                        
                                        TemplateService.get($stateParams.templateId).then(function (template) {
                                            deferred.resolve(template);
                                }, function(response) {
                                    MessageFactory.set('danger', response.data.error.errorMessages[0].description || response.data.error.errorMessages[0].developerMessage || "There was an error", true, false);
                                        });
                                    }
                                    else{
                                console.log("Creating empty template for module " + $stateParams.selectedSurveyName + " of template type " + $stateParams.typeId);
                                        var selectedTemplateType = TemplateTypeService.getSelectedType();
                                        if(Object.isDefined(selectedTemplateType)){
                                            var emptyTemplate =  new EScreeningDashboardApp.models.Template({type: selectedTemplateType});
                                            deferred.resolve(emptyTemplate);

                                    deferred.resolve(emptyTemplate);
                                        }
                                        else {
                                    console.log("There is no currently selected template type. Redirecting to module template list.");
                                            deferred.resolve({});
                                        }
                                    }
                                }
                                return deferred.promise;
                            }],
                            relatedObj: ['$stateParams', '$q', function($stateParams, $q) {
                            	var deferred = $q.defer();
                            	deferred.resolve({
                            id : $stateParams.selectedSurveyId,
                            name : decodeURIComponent($stateParams.selectedSurveyName),
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
                                };

                                $scope.viewTS = function(){
                                    $scope.teCollapsed = $scope.stCollapsed = false;
                                    $scope.tsCollapsed = true;
                                };

                                $scope.deleteClick = function(){
                                    alert('Will trigger Delete functionality.')
                                };

                                $scope.addFormula = function(){
                                    alert('Add Formula - Will trigger Formulas Flow');
                                };

                                $scope.addVariable = function(){
                                    alert('Add Variable - Will trigger Variables Flow');
                                };

                                $scope.viewTE = function(){
                                    $scope.tsCollapsed = $scope.stCollapsed = false;
                                    $scope.teCollapsed = true;
                                };

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
                                    });
                                };
                            }]
                        }).result.then(function(result){
                            if (result){
                                return $state.transitionTo('modules.detail');
                            }
                        });
                    }
                });

}]);
