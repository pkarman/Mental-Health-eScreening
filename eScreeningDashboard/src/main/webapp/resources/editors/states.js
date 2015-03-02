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
                                            MessageFactory.set('danger', response.data.errorMessages[0].description || "There was an error", true, true);
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
                })
            .state('modules.templates', {
                    templateUrl:'resources/editors/views/templates/templatesselection.html',
                    resolve: {
                        templateTypes: ['$rootScope', '$stateParams', '$q', 'TemplateTypeService', function($rootScope, $stateParams, $q, TemplateTypeService) {
                            
                            var deferred = $q.defer();
                            if(Object.isDefined($stateParams) &&
                                    deferred.resolve(templateTypes);
                                }, function(responseError) {
                                    deferred.reject(responseError.data);
                                });
                            }
                    }]
                },
            })
            .state('modules.templates', {
                templateUrl:'resources/editors/views/templates/templatesselection.html',
                resolve: {
                    templateTypes: ['$rootScope', '$stateParams', '$q', 'TemplateTypeService', function($rootScope, $stateParams, $q, TemplateTypeService) {

                        var deferred = $q.defer();
                        if(Object.isDefined($stateParams) &&
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
                                    console.log(template);
                                    deferred.resolve(template);
                                }, function(response) {
                                    MessageFactory.set('danger', response.data.errorMessages[0].description || "There was an error", true, true);
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
            });

}]);
