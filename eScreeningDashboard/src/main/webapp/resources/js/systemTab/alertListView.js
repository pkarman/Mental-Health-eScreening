/*var app = angular.module('alertViewApp', []);

app.factory('programListService', function($http) {
	return {
		getProgramList : function() {
			return $http({
				method : "GET",
				url : "dashboard/programList",
				responseType : "json"
			}).then(function(result) {
				console.log("result.data" + result);
				return result.data;
			});
		}
	};
});

app.controller("tabletConfigurationController", function($scope, $element, $http, $window, programListService) {
	$scope.tabletConfigurationFormBean = {};
	$scope.tabletConfigurationFormBean.programId = "";

	programListService.getProgramList().then(function(data) {
		$scope.programList = data;
	});
});
*/

var app = angular.module('alertViewApp', []);
app.directive('reportTable', function() {
	return function(scope, element, attrs) {
        var options = {};
        options = {
	    	"bProcessing": true,
	    	"bServerSide": true,
	    	"bStateSave": true,
 			"fnStateSave": function (oSettings, oData) {
				// set local storage for paging
            	localStorage.setItem( 'dashboardDT', JSON.stringify(oData) );
        	},
        	"fnStateLoad": function (oSettings) {
            	// load local storage for paging
				return JSON.parse( localStorage.getItem('dashboardDT') );
        	},
	    	"bFilter": false,
	    	"bJQueryUI": true,
	    	"aLengthMenu": [[10,25,50], [10,25,50]],

	
	    	"sPaginationType": "full_numbers",
	    	"sServerMethod": "GET",
	    	"sAjaxSource": "http://localhost:8080/escreeningdashboard/dashboard/alertTypes/",
			"sAjaxDataProp": "status.payload",
	    	"fnServerData": scope.$eval(attrs.fnDataCallback)
        };

        var aoColumns = {};
        aoColumns = [
			{ "mData": "stateName",  "sClass": "col-md-10"},
			{ "mData": "stateId" , "sClass": "text-right col-md-2", "bSortable" : false , "mRender": function(data, type, row) { return '<a href="alertEditView?aid='+row.stateId+'" class="btn btn-default btn-xs cursor-pointer"><span class="glyphicon glyphicon-chevron-right"></span> Edit </a> &nbsp; &nbsp; <a href="alertEditView?aid='+row.stateId+'" class="btn btn-default btn-xs cursor-pointer"><span class="glyphicon glyphicon-remove-circle red-color"></span> Delete </a>'; }}];
		console.log("aoColumns");
		console.log(aoColumns);
        options["aoColumns"] = aoColumns;

        // apply the plugin
        var dataTable = element.dataTable(options);
		
        // watch for any changes to our data, rebuild the DataTable
        scope.$watch(attrs.aaData, function(value) {
            var val = value || null;
            if (val) {
                dataTable.fnClearTable();
                dataTable.fnAddData(scope.$eval(attrs.aaData));
            }
        });
    };
});


app.controller("alertsController", function($scope, $element, $http, $window) {
					
		$scope.alertFormBean = {};
		$scope.alertFormBean.programId = "";

		$scope.searchDatabase = function() {
			var oTable = $('#alertsTable').dataTable();
			oTable.dataTable().fnDraw(true);
		};

		
		$scope.getDataForSearch = function(sSource, aoData, fnCallback, oSettings) {
	
			aoData.push({
				"name" : "programId",
				"value" : $scope.alertFormBean.programId
			});
	
			oSettings.jqXHR = $.ajax({
				"dataType" : 'json',
				"type" : "GET",
				"url" : sSource,
				"data" : aoData,
				"success" : fnCallback
			});
		};
		
});



$(document).ready(function() {
	// Tab
	tabsLoad("systemConfiguration");		
});