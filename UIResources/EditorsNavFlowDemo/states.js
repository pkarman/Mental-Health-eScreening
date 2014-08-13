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

                .state("home", {
                    // Use a url of "/" to set a states as the "index".
                    abstract: false,
                    url: "/",
                    template: '<div class="col-md-12" style="background-color:#ebebeb;min-height:400px;">' +
                                '<h1>Workflow Selection View</h1>' +
                                '<div class="well center-text" style="min-height:300px;margin:0 auto;" >' +
                                    '<div class="col-sm-12 form-inline text-center" style="border:1px solid #999999;min-height:260px; margin:0 auto;">' +
                                        '<div class="col-sm-offset-1 col-sm-3 btn-group-vertical" style="padding-top:80px;">'+
                                            '<button class="btn btn-primary" ng-click="goToBatteries();">New Battery</button>' +
                                            '<button class="btn btn-primary" ng-click="goToBatteryEdit();">Edit Battery</button>'+
                                        '</div>'+

                                        '<div class="col-sm-offset-1 col-sm-3 btn-group-vertical" style="padding-top:80px;">'+
                                            '<button class="btn btn-primary" ng-click="goToModules();">New Module</button>' +
                                            '<button class="btn btn-primary" ng-click="goToModuleEdit();">Edit Module</button>'+
                                        '</div>'+

                                        '<div class="col-sm-offset-1 col-sm-3 btn-group-vertical" style="padding-top:80px;">' +
                                            '<button class="btn btn-primary" ng-click="goToTemplates();">New Template</button>'+
                                            '<button class="btn btn-primary" ng-click="goToTemplateEdit();">Edit Template</button>'+
                                        '</div>'+
                                    '</div>'+
                                '</div>',
                    data: {
                        displayName: 'Home'
                    },
                    controller: ['$rootScope','$scope', '$state',
                        function ( $rootScope, $scope,   $state) {
                            $scope.goToBatteries = function(){
                                $state.go('battery-editor.addedit');
                            }

                            $scope.goToBatteryEdit = function(){
                                $state.go('battery-editor.selection');
                            }

                            $scope.goToModules = function(){
                                $state.go('modules-editor.addedit');
                            }

                            $scope.goToModuleEdit = function(){
                                $state.go('modules-editor.selection');
                            }

                            $scope.goToTemplates = function(){
                                $state.go('templates.templatelistview');
                            }

                            $scope.goToTemplateEdit = function(){
                                $state.go('templates.templateeditorview');
                            }

                        }]
                })


                //////////////////////////
                // Battery Editor Views //
                //////////////////////////

                .state('battery-editor',{
                    abstract:true,
                    url:'/battery-editor',
                    template: '<div class="col-md-12" style="min-height:400px;">' +
                                    '<div class="ui-view-container container-fluid">' +
                                        '<div class="" ui-view></div>' +
                                    '</div>' +
                               '</div>',
                    data: {
                        displayName: false
                    },
                    controller: ['$rootScope', '$scope', '$state',
                    function($rootScope, $scope, $state){

                    }]
                })

                .state('battery-editor.selection',{
                    url:'',
                    templateUrl:'views/batteries/batteryselect.html',
                    data: {
                        displayName: 'Battery-Editor: Selection'
                    },
                    controller:['$rootScope', '$scope', '$state',
                    function($rootScope, $scope, $state){
                        $scope.goToAddEdit = function(){
                            $state.go('battery-editor.addedit');
                        }

                        $scope.selectForDelete = function() {
                            alert('Deletes Battery.');
                        }
                    }]
                })

                .state('battery-editor.addedit',{
                    url:'/battery-editor.addedit',
                    templateUrl:'views/batteries/batteryedit.html',
                    data: {
                        displayName: 'Battery-Editor: Add/Edit'
                    },
                    controller:['$rootScope', '$scope', '$state',
                    function($rootScope, $scope, $state){
                        $scope.goToSelection = function(){
                            $state.go('battery-editor.selection');
                        }
                    }]
                })

                //////////////////////////
                // Modules Editor Views //
                //////////////////////////

                .state('modules-editor',{
                    abstract:true,
                    url:'/modules-editor',
                    template:'<div class="col-md-12" style="min-height:400px;">' +
                                '<div class="ui-view-container container-fluid">' +
                                    '<div class="" style="min-height:650px;" ui-view></div>' +
                                '</div>' +
                             '</div>',
                    data: {
                        displayName: false
                    },
                    controller:['$rootScope', '$scope', '$state',
                    function($rootScope, $scope, $state){

                    }]
                })

                .state('modules-editor.selection',{
                    url:'',
                    /*template:'<div class="col-md-12" style="background-color:#ebebeb;min-height:400px;">' +
                                '<h3>Modules Selection View</h3>' +
                                '<div class="form-inline form-group" style="background-color:#c9302c;min-height:280px;">' +
                                    '<button class="btn btn-primary" ng-click="goToAddEdit();">Add/Edit Module</button>' +
                                '</div>' +
                             '</div>',*/
                    templateUrl:'views/modules/modulesselectview.html',
                    data: {
                        displayName: 'Modules-Editor: Selection'
                    },
                    controller:['$rootScope', '$scope', '$state',
                    function($rootScope, $scope, $state){
                        $scope.goToAddEdit = function(){
                            $state.go('modules-editor.addedit');
                        }
                    }]
                })

                .state('modules-editor.addedit',{
                    url:'/modules-editor.addedit',
                    template:'<div class="col-md-12" style="min-height:600px;">' +
                                '<h4>Module' +
                                    '<div class="form-group form-inline pull-right">' +
                                        '<label>Title</label>'+
                                        '<input type="text" placeholder="Enter Name" class="form-control" style="min-width:200px;">&nbsp;&nbsp;'+
                                        '<label>Description</label>'+
                                        '<input type="text" placeholder="Enter Description" class="form-control" style="min-width:350px;">'+
                                    '</div>'+
                              '</h4>' +
                              '<hr>'+
                           '<div class="form-inline form-group">' +
                        '<div class="btn-toolbar">'+
                        '<div class="btn-group pull-left">'+
                           // '<a ui-sref="modules-editor.selection" class="btn btn-success"><span class="glyphicon glyphicon-arrow-left"></span> Return to Selection</a>'+

                        '</div>'+
                        '<div class="btn-group col-sm-6-offset">'+
                        '</div>'+
                        '<div class="btn-group pull-right">'+
                        '<a ui-sref="modules-editor.addedit.expressioneditor" class="btn btn-success">Add/Edit Formula</a>'+
                        '<a class="btn btn-warning" ui-sref="modules-editor.addedit.rulesandevents">Add/Edit Rule</a>'+
                        '<a ui-sref="modules-editor.addedit.templatecaptureview" class="btn btn-info">Add/Edit Template</a>'+
                        '</div>'+
                        '</div>'+
                                '<hr>'+
                                '</div>' +
                                '<div class="row">' +
                                    '<div class="col-md-4 text-center">' +
                                        '<div class="form-inline form-group center-block">' +
                                            '<label class="pull-left">Questions</label>' +
                                            '<button class="btn btn-primary" ng-click="goToQuestions();"><span class="glyphicon glyphicon-plus"></span> Add Question</button>'+
                                            '<button class="btn btn-success" popover-placement="top" popover="Will add a sortable Page Break object to the Questions List, and update page breaks."><span class="glyphicon glyphicon-plus"></span>  Add Page Break</button>'+
                                        '</div>' +
                                        '<div class="well" style="background-color:#fff;min-height:520px;">' +
                                            '<ul class="list-group">' +
                                                '<li class="list-group-item text-left">' +
                                                    '<span class="badge pull-left">1</span>&nbsp;&nbsp;' +
                                                    'Var: 000A2 What is your First Name? | Free Text' +
                                                    '<div class="form-group pull-right">' +
                                                        '<span class="glyphicon glyphicon-edit"></span><span class="glyphicon glyphicon-remove-circle" style="color:red;"></span>' +
                                                    '</div>' +
                                                '</li>' +
                                                '<li class="list-group-item text-left">' +
                                                    '<span class="badge pull-left">2</span>&nbsp;&nbsp;' +
                                                        'Var: 000A3 What is your gender? | Select Single' +
                                                    '<div class="form-group pull-right">' +
                                                        '<span class="glyphicon glyphicon-edit"></span><span class="glyphicon glyphicon-remove-circle" style="color:red;"></span>' +
                                                    '</div>' +
                                                '</li>'+
                                                '<li class="list-group-item text-left">' +
                                                    '<span class="badge pull-left">3</span>&nbsp;&nbsp;' +
                                                    'Var: 000A4 What are the ages of your children? | Table' +
                                                    '<div class="form-group pull-right">' +
                                                        '<span class="glyphicon glyphicon-edit"></span><span class="glyphicon glyphicon-remove-circle" style="color:red;"></span>' +
                                                    '</div>' +
                                                '</li>'+
                                                '<li class="list-group-item text-left">'+
                                                    '<span class="badge pull-left">4</span>&nbsp;&nbsp;' +
                                                    'Please fill out... | Instructions' +
                                                    '<div class="form-group pull-right">' +
                                                    '<span class="glyphicon glyphicon-edit"></span><span class="glyphicon glyphicon-remove-circle" style="color:red;"></span>' +
                                                    '</div>' +
                                                '</li>'+
                                                '<li class="list-group-item text-left">' +
                                                    '<span class="badge pull-left">5</span>&nbsp;&nbsp;' +
                                                    'Var: 000A5 What is your height? | Free Text' +
                                                    '<div class="form-group pull-right">' +
                                                        '<span class="glyphicon glyphicon-edit"></span><span class="glyphicon glyphicon-remove-circle" style="color:red;"></span>' +
                                                    '</div>' +
                                                '</li>'+
                                                '<li class="list-group-item text-left">'+
                                                    '<span class="badge pull-left">6</span>&nbsp;&nbsp;' +
                                                    'Var: 000A6 Address, Line 1 | Free Text' +
                                                    '<div class="form-group pull-right">' +
                                                        '<span class="glyphicon glyphicon-edit"></span><span class="glyphicon glyphicon-remove-circle" style="color:red;"></span>' +
                                                    '</div>' +
                                                '</li>'+
                                            '</ul>' +
                                        '</div>' +
                                    '</div>' +
                                    '<div class="col-md-8">' +
                                        '<div class="row">' +
                                            '<div class="ui-view-container container-fluid">' +
                                                '<div class="" style="min-height:520px;" ui-view></div>' +
                                            '</div>' +
                                        '</div>'+
                                    '</div>' +
                                '</div>' +
                             '</div>',
                    data: {
                        displayName: 'Modules-Editor: Add/Edit'
                    },
                    controller:['$rootScope', '$scope', '$state',
                    function($rootScope, $scope, $state){
                        $scope.goToSelection = function(){
                            $state.go('modules-editor.selection');
                        }

                        $scope.goToQuestions = function(){

                            $state.go('modules-editor.addedit.questions.freetextreadonly');
                        }

                        $scope.goToSections = function(){
                            $state.go('modules-editor.addedit.mapsection');
                        }

                        $scope.goToMapConsult = function(){
                            $state.go('modules-editor.addedit.questions.mapconsult');
                        }

                        $scope.goToFormulaExpression = function(){
                            $state.go('modules-editor.addedit.expressioneditor');
                        }

                        $scope.goToCreateVar = function(){
                            $state.go('modules-editor.addedit.createvariable.questionvariable');
                        }
                    }]
                })

                .state("modules-editor.addedit.mapsection", {
                    url: "/modules-editor.addedit.mapsection",
                    onEnter: function($stateParams, $state, $modal) {
                        $modal.open({
                            template: '<div class="container">' +
                                         '<div class="row">' +
                                            '<div>' +
                                                '<h3>Map Sections View</h3>' +
                                                '<button class="btn btn-warning" ng-click="dismiss();">Close</button>' +
                                            '</div>' +
                                        '</div>' +
                                    '</div>',
                            windowClass:'modal modal-huge modal-content',
                            controller: ['$scope', function($scope) {
                                $scope.dismiss = function() {
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
                                    return $state.transitionTo("modules-editor.addedit");
                                }
                            });
                    }
                })

                .state('modules-editor.addedit.blankslate',{
                    abstract:false,
                    url:'modules-editor.addedit.blankslate',
                    template:'<div class="col-md-12">' +
                                '<div class="row center-block">' +
                                    '<h4>Default Content TBD</h4>' +
                                '</div>' +
                             '</div>',
                    controller:['$rootScope', '$scope', '$state',
                    function($rootScope, $scope, $state){

                    }]
                })

                .state('modules-editor.addedit.questions',{
                    abstract:true,
                    url:'/modules-editor.addedit.questions',
                    template:'<div class="col-md-12" style="min-height:300px;">' +
                                '<div class="row">' +
                                    '<div class="col-sm-12">'+
                                        '<div class="form-inline form-group pull-right">' +
                                            '<label for="selType" class="col-sm-5 form-label">Question Type</label>' +
                                            '<div class="col-sm-5-offset col-sm-7">'+
                                                '<select id="selType" ng-model="qType" ng-options="q.name for q in qTypes" name="selType" class="form-control">' +
                                                '</select>' +
                                            '</div>'+
                                        '</div>' +
                                    '</div>'+
                                '</div>'  +
                                '<div class="row">' +
                                    '<div class="ui-view-container container-fluid">' +
                                        '<div class="" style="background-color:#fff;border:1px solid #666666;border-radius:5px;min-height:520px;padding:10px;" ui-view></div>' +
                                    '</div>' +
                                '</div>' +
                            '</div>',
                        data: {
                            displayName: false
                        },
                        controller:['$rootScope','$scope','$state',
                        function($rootScope, $scope, $state){

                            $scope.qTypes = [
                                {name:"Free/Read-Only Text", value:1},
                                {name:"Select Single/Multiple", value:2},
                                {name:"Select Single/Multiple Matrix", value:3},
                                {name:"Table Question", value:4},
                                {name:"Instructions", value:5}];

                            $scope.qType = $scope.qTypes[0];

                            $scope.$watch( 'qType', function( qType ) {
                                var url = 'modules-editor.addedit.questions.freetextreadonly';
                                switch(qType.name){
                                    case 'Free/Read-Only Text':
                                        url = 'modules-editor.addedit.questions.freetextreadonly';
                                        break;
                                    case 'Select Single/Multiple':
                                        url = 'modules-editor.addedit.questions.selectsinglemultiple';
                                        break;
                                    case 'Select Single/Multiple Matrix':
                                        url = 'modules-editor.addedit.questions.selectsinglemultiplematrix';
                                        break;
                                    case 'Table Question':
                                        url = 'modules-editor.addedit.questions.tablequestion';
                                        break;
                                    case 'Instructions':
                                        url = 'modules-editor.addedit.questions.instructions';
                                        break;
                                }
                                $state.go(url);
                            });

                            $scope.goToAddEdit = function(){
                                $state.go('modules-editor.addedit');
                            }
                        }]
                })

                .state('modules-editor.addedit.questions.freetextreadonly',{
                    url:'',
                    /*template:'<div class="well"><h4>Free/Read-Only Text Question</h4></div>',*/
                    templateUrl:'views/questions/freereadonlyquestion.html',
                    data: {
                        displayName: 'Modules-Editor: Add/Edit - Questions, Type: Free Text/Read-Only'
                    },
                    controller:['$rootScope', '$scope', '$state',
                    function($rootScope, $scope, $state){

                    }]
                })

                .state('modules-editor.addedit.questions.selectsinglemultiple',{
                    url:'/modules-editor.addedit.questions.selectsinglemultiple',
                    /*template:'<div class="well"><h4>Select Single/Multiple Question</h4></div>',*/
                    templateUrl:'views/questions/selectsinglemultiplequestion.html',
                    data: {
                        displayName: 'Modules-Editor: Add/Edit - Questions, Type: Select Single/Multiple'
                    },
                    controller:['$rootScope', '$scope', '$state',
                        function($rootScope, $scope, $state){

                        }]
                })

                .state('modules-editor.addedit.questions.selectsinglemultiplematrix',{
                    url:'/modules-editor.addedit.questions.selectsinglemultiplematrix',
                    /*template:'<div class="well"><h4>Select Single/Multiple Matrix Question</h4></div>',*/
                    templateUrl:'views/questions/selectsinglemultiplematrixquestion.html',
                    data: {
                        displayName: 'Modules-Editor: Add/Edit - Questions, Type: Select Single/Multiple Matrix'
                    },
                    controller:['$rootScope', '$scope', '$state',
                        function($rootScope, $scope, $state){

                        }]
                })

                .state('modules-editor.addedit.questions.tablequestion',{
                    url:'/modules-editor.addedit.questions.tablequestion',
                    /*template:'<div class="well"><h4>Table Question</h4></div>',*/
                    templateUrl:'views/questions/tablequestion.html',
                    data: {
                        displayName: 'Modules-Editor: Add/Edit - Questions, Type: Table Question'
                    },
                    controller:['$rootScope', '$scope', '$state',
                        function($rootScope, $scope, $state){

                        }]
                })

                .state('modules-editor.addedit.questions.instructions', {
                    url:'/modules-editor.addedit.questions.instructions',
                    /*template:'<div class="well"><h4>Page-Level Instructions</h4></div>',*/
                    templateUrl:'views/questions/questioninstructions.html',
                    data: {
                        displayName: 'Modules-Editor: Add/Edit - Questions, Type: Page Instructions'
                    },
                    controller:['$rootScope', '$scope', '$state',
                        function($rootScope, $scope, $state){
                            $scope.htmlVariable = '<b>I am</b> an example of an <b><i>Instruction!!!</i></b>';
                            $scope.htmlcontent = $scope.htmlVariable;
                        }]
                })

                /////////////////////////////
                // Formulas Workflow Views //
                /////////////////////////////
                .state('modules-editor.addedit.templatecaptureview',{
                    url:'/modules-editor.addedt.templatecaptureview',
                    onEnter: function($stateParams, $state, $modal){
                        $modal.open({
                            templateUrl:'views/templates/templates.html',
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
                                    return $state.transitionTo('modules-editor.addedit');
                                }
                            })
                    }
                })
                .state('modules-editor.addedit.expressioneditor',{
                    url:'/modules-editor.addedit.expressioneditor',
                    onEnter: function($stateParams, $state, $modal) {
                        $modal.open({
                            templateUrl: 'views/formulas/formulas.html',
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
                                    return $state.transitionTo("modules-editor.addedit");
                                }
                            });
                    }
                })

                .state('modules-editor.addedit.createvariable',{
                    abstract:true,
                    url:'/modules-editor.addedit.createvariable',

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
                            var url = 'modules-editor.addedit.createvariable.questionvariable';
                            switch(qType.name){
                                case 'Question':
                                    url = 'modules-editor.addedit.createvariable.questionvariable';
                                    break;
                                case 'Answer':
                                    url = 'modules-editor.addedit.createvariable.answervariable';
                                    break;
                                case 'Custom':
                                    url = 'modules-editor.addedit.createvariable.customvariable';
                                    break;
                                case 'Formula':
                                    url = 'modules-editor.addedit.createvariable.formulavariable';
                                    break;
                            }
                            $state.go(url);
                        });

                        $scope.goToAddEdit = function(){
                            $state.go('modules-editor.addedit');
                        }
                    }]
                })

                .state('modules-editor.addedit.createvariable.questionvariable',{
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
                            $state.go('modules-editor.addedit');
                        }
                    }]
                })

                .state('modules-editor.addedit.createvariable.customvariable',{
                    url:'/modules-editor.addedit.createvariable.customvariable',
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
                                $state.go('modules-editor.addedit');
                            }
                        }]
                })

                .state('modules-editor.addedit.createvariable.answervariable',{
                    url:'/modules-editor.addedit.createvariable.answervariable',
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
                                $state.go('modules-editor.addedit');
                            }
                        }]
                })

                .state('modules-editor.addedit.createvariable.formulavariable',{
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
                                $state.go('modules-editor.addedit');
                            }
                        }]
                })

                /////////////////////////
                // Rules/Events Views //
                ////////////////////////
                .state('modules-editor.addedit.rulesandevents',{
                    url:'/modules-editor.addedit.rulesandevents',
                    onEnter: function($stateParams, $state, $modal) {
                        $modal.open({
                            templateUrl: 'views/rulesevents/rulesevents.html',
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
                                    return $state.transitionTo("modules-editor.addedit");
                                }
                            });
                    }
                })

                .state('modules-editor.addedit.statementeditor',{
                        url:'/modules-editor.addedit.rules.statementeditor',
                        template:'<div class="well"><h4>Rules/Events Statement Editor View</h4></div>',
                        controller:['$rootScope','$scope','$state',
                        function($rootScope,$scope,$state){

                        }]
                    })

                .state('modules-editor.addedit.select',{
                    url:'/modules-editor.addedit.rules.select',
                    template:'<div class="well"><h4>Rules/Events Select Item View</h4></div>',
                    controller:['$rootScope','$scope','$state',
                        function($rootScope,$scope,$state){

                    }]
                })

                .state('modules-editor.addedit.healthfactor',{
                    url:'modules-editor.addedit.healthfactor',
                    template:'<div class="well"><h4>Rules/Events Select Health Factor</h4></div>',
                    controller:['$rootScope','$scope','$state',
                    function($rootScope,$scope,$state){

                    }]
                })

                .state('modules-editor.addedit.selectconsult',{
                    url:'/modules-editor.addedit.selectconsult',
                    template:'<div class="well"><h4>Rules/Events Select Consult</h4></div>',
                    controller:['$rootScope','$scope','$state',
                    function($rootScope,$scope,$state){

                    }]
                })

                .state('modules-editor.addedit.ruletriggeredalert',{
                    url:'/modules-editor.addedit.ruletriggeredalert',
                    template:'<div class="well"><h4>Rules/Events Select Rule-Triggered Alert</h4></div>',
                    controller:['$rootScope','$scope','$state',
                    function($rootScope,$scope,$state){

                    }]
                })

                //////////////////////////////
                // Templates Workflow Views //
                //////////////////////////////
                .state('templates',{
                    abstract:true,
                    url:'/templates',
                    data: {
                        displayName: false
                    },
                    template: '<div class="col-md-12" style="min-height:400px;">' +
                        '<div class="ui-view-container container-fluid">' +
                        '<div class="" ui-view></div>' +
                        '</div>' +
                        '</div>',
                    controller:['$rootScope','$scope','$state',
                    function($rootScope,$scope,$state){

                    }]
                })
                .state('templates.templatelistview',{
                    url:'',
                    /*template:'<div class="col-md-12" style="background-color:#ebebeb;min-height:400px;">' +
                        '<h3>Templates Selection View</h3>' +
                        '<div class="form-inline form-group" style="background-color:#245269;">' +
                        '<button class="btn btn-primary" ng-click="goToAddEdit();">Add/Edit Template</button>' +
                        '</div>' +
                        '</div>',*/
                    data: {
                        displayName: 'Templates: Selection'
                    },
                    templateUrl:'views/templates/templatesselection.html',
                    controller:['$rootScope', '$scope', '$state',
                        function($rootScope, $scope, $state){
                            $scope.goToAddEdit = function(){
                                $state.go('templates.templateeditorview');
                            }

                            $scope.deleteClick = function(){
                                alert('Triggers Template Delete functionality.');
                            }

                            $scope.goToHome = function(){
                                $state.go('/')
                            }
                        }]

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
                    templateUrl:'views/templates/templateeditor.html',
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
                    templateUrl:'views/templates/templatestatementeditor.html',
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
