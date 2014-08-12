/**
 * 
 */
Editors.controller('batteryAbstractController',['$rootScope','$scope','$state','sections',function($rootScope,$scope,$state, sections){
			 $rootScope.batteries = [];
			 $scope.sections = sections;
			 
			 
             $scope.save = function(battery){
                 $state.go('home');
             };

             $scope.cancel = function(){
                 $state.go('home');
             };
         }]);