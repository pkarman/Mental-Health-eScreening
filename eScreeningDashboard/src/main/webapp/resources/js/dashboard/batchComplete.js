var app = angular.module('batchCompleteFormApp', []);

/**
 * Looks for the report-table attribute on a table to provide JQuery datatable features.
 * fnDataCallback: This attribute is used to bind the data to the datatable.
 */

app.directive('reportTable', function() {
	return function(scope, element, attrs) {
		// check dataType to update datatable link	

    	var dataTable;
		//var sourceURL = "veteranSearch/veteransbyclinic/?clinicIen=&startDate=&endDate=";
		var sourceURL = "veteranSearch/veteransbyclinic/?clinicIen=32&startDate=2000/01/10&endDate=2015/05/01"; // temp URL
		
		selectVeteransTable(sourceURL);
		
		function selectVeteransTable(sourceURL){
			var options = {};
	        options = {			
				"ajax": {
					  url: sourceURL,
					  dataSrc: "payload"
					},
		    	"bProcessing": true,
		    	"bServerSide": true,
		    	"bFilter": false,
		    	"bJQueryUI": true,
				"bAutoWidth": false,
		    	"sPaginationType": "full_numbers",
		    	"sServerMethod": "POST",
				"fnServerData": scope.$eval(attrs.fnDataCallback)
	        };
	
	
	        var aoColumns = {};
	        aoColumns = [
				{ "mData": "ssn"},
				{ "mData": "lastName"},
				{ "mData": "firstName"},
				{ "mData": "middleName"},
				{ "mData": "dob"},
				{ "mData": "apptDate"},
				{ "mData": "apptTime"},
				{ "mData": "visitStatusName"},
				{ "mData": "action" , "mRender":function(data, type, assessment){ return "<a href=" + assessment.veteranIen + ">View</a>"}}
			];
	        options["aoColumns"] = aoColumns;

	        // apply the plugin
	        dataTable = element.DataTable(options);
	
	        // watch for any changes to our data, rebuild the DataTable
	        scope.$watch(attrs.aaData, function(value) {
	            var val = value || null;
	            if (val) {
	                dataTable.fnClearTable();
	                dataTable.fnAddData(scope.$eval(attrs.aaData));
	            }
	        });
		}
		
		/*
		scope.destroyDataTable = function(clinicId) {
			dataTable.fnDestroy();
			sourceURL = "veteranSearch/veteransbyclinic/?clinicIen=32&startDate=2000/01/10&endDate=2015/05/01" + clinicId;
		    selectVeteransTable(sourceURL);
		}
		*/
        
    };
});


/**
 * Controller for the page.
 * @param $scope
 * @param $element
 * @param $http
 */
function selectVeteransController($scope,$element,$http,$window) {	
	
	// Initialize the model.
	$scope.veteranSearchFormBean = {};
	 
	console.log("inside the controller");

	// Initialize the model.
	
	// Populate dropdown lists.
	initializeModelSync($scope);
	
	/**
	 * Initializes the model with any parameters initially passed to the controller synchronously.
	 */
	function initializeModelSync(scope) {
	
	}
};

$(document).ready(function() {
    // Load current tab
    tabsLoad("createBattery");
});