/**
 * 
 */
Editors.controller('batterySelectionController',['$rootScope','$scope','$state','$filter','$stateParams','$window', 'ngTableParams', 'BatteryService','batteries', function($rootScope,$scope,$state,$filter,$stateParams,$window,ngTableParams,BatteryService,batteries){
    				$scope.batteries = batteries;
    				$scope.orderedBatteries = [];
    				$scope.errors = [];
    				console.log('Batteries in Controller:: ' + $scope.batteries.length);
    				
    				$scope.$watch('batteries', function(newVal, oldVal){
    					if ($scope.batteries.length > 0){
    						for (var i=0;i<$scope.batteries.length;i++){
    							var bat = {
    									id: $scope.batteries[i].getId(),
    									name: $scope.batteries[i].getName(),
    									description: $scope.batteries[i].getDescription(),
    									isDisabled: $scope.batteries[i].isDisabled(),
    									createdDate: $scope.batteries[i].getCreatedDate(),
    									surveys: null
    							};
    							$scope.orderedBatteries.push(bat);
    						}
    					}
    				});

                    var data = $scope.orderedBatteries;
                    
                    

                    $scope.tableParams = new ngTableParams({
                        page: 1,            // show first page
                        count: 10,          // count per page
                        filter: {
                            name: ''       // initial filter
                        },
                        sorting: {
                            name: 'asc'     // initial sorting
                        }
                    }, {
                        total: data.length, // length of data
                        getData: function($defer, params) {
                            // use build-in angular filter
                            var filteredData = params.filter() ?
                                    $filter('filter')(data, params.filter()) :
                                    data;
                            var orderedData = params.sorting() ?
                                    $filter('orderBy')(filteredData, params.orderBy()) :
                                    data;

                            params.total(orderedData.length); // set total for recalc pagination
                            $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                        }
                    });

                    /* ---- Button Actions ---- */
                    $scope.editBattery = function(selectedBattery){
                       $state.go('batteries.batteryedit',{batteryId:selectedBattery.id});
                    };

                    $scope.addBattery = function(){
                       // $rootScope.module = $rootScope.createModule();
                    	$rootScope.selectedBattery = new EScreeningDashboardApp.models.Battery();
                        $state.go('batteries.batteryedit',{batteryId:-1});
                    };
                    
                    $scope.deleteBattery = function(battery){
                    	BatteryService.remove(BatteryService.setQueryBatterySearchCriteria(battery.id)).then(function(){
                			console.log('Remove Battery:: SUCCESS');
                		},function(responseError){
                			$scope.errors.push('Battery Deletion failed.');
                			console.log('Remove Query Error:: ' + JSON.stringify($scope.errors));
                		});
                    };

                    $scope.goToAddEdit = function(){
                        $state.go('batteries.batteryedit',{batteryId:1});
                    };
                }]);