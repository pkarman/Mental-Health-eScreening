/**
 * 
 */
Editors.controller('entryController', ['$rootScope', '$scope', '$state', function($rootScope, $scope, $state){
			$scope.entryApp = '/escreeningdashboard/';
			
			$rootScope.errors = [];
            $rootScope.messages = [];
			
			$rootScope.clearErrors = function(){
				$rootScope.errors = [];
			};

            $rootScope.clearMessages = function () {
                $rootScope.messages = [];
            };
			
            $rootScope.batteries = [];

            $rootScope.selectedBattery = {};

            $rootScope.modules = [
                {id:1, title:'Identification', status:'Published', description:'Veteran\'s self identification module.', questions:[]},
                {id:2, title:'Demographics', status:'Editable', description:'Veteran\'s demographic information.', questions:[]},
                {id:3, title:'Service History',  status:'Editable', description:'Veteran\'s Military Service history module.', questions:[]},
                {id:4, title:'Spiritual Beliefs', status:'Published', description:'Veteran\'s spiritual beliefs.', questions:[]},
                {id:5, title:'General Physical Health', status:'Editable', description:'Veteran\'s physical health module.', questions:[]},
                {id:6, title:'General Mental Health', status:'Editable', description:'Veteran\'s mental health module (general).', questions:[]},
                {idx:7, title:'OEF/OIF PTSD', status:'Editable', description:'Post-Traumatic Stress Disorder module.', questions:[]},
                {id:8, title:'OEF/OIF Anxiety Spectrum', status:'Editable', description:'Anxiety Spectrum identification module.', questions:[]},
                {id:9, title:'OEF/OIF Something 1', status:'Published', description:'I\'m your friendly, neighborhood description!', questions:[]},
                {id:10, title:'OEF/OIF Something 2', status:'Editable', description:'I\'m your friendly, neighborhood description!', questions:[]},
                {id:11, title:'OEF/OIF Something 3', status:'Editable', description:'I\'m your friendly, neighborhood description!', questions:[]}
            ];



            $scope.goToModuleNew = function(){
                //alert('This navigation is not implemented in this demonstrator.');
            	console.log('ENTRY:: New Module View Selected.');
            	$state.go('modules.detail');
            };

            $scope.goToModuleEdit = function(){
                //alert('This navigation is not implemented in this demonstrator.');
            	console.log('ENTRY:: Edit Module Selection View Selected.');
            	$state.go('modules.list');
            };

            $scope.goToBatteryNew = function(){
            	console.log('ENTRY:: New Battery View Selected.');
                $state.go('batteries.detail');
            };

            $scope.goToBatteryEdit = function(){
            	console.log('ENTRY:: Edit Battery Selection View Selected.');
                $state.go('batteries.batteryselection');
            };

            $scope.goToSections = function(){
            	console.log('ENTRY:: Manage Sections View Selected.');
                $state.go('sections');
            };


            $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){
                $rootScope.clearErrors();
                $rootScope.clearMessages();
            });



        }]);