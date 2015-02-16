var app = angular.module('selectVeteransFormApp', []);

/**
 * Looks for the report-table attribute on a table to provide JQuery datatable features.
 * fnDataCallback: This attribute is used to bind the data to the datatable.
 */

app.directive('reportTable', function() {
	return function(scope, element, attrs) {
		// check dataType to update datatable link	

    	var dataTable;
		//var sourceURL = "veteranSearch/veteransbyclinic/?clinicIen=&startDate=&endDate=";
		var sourceURL = "veteranSearch/veteransbyclinic/?clinicIen=32&startDate=2000/01/10&endDate=2015/05/01";
		
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
				{ "mData": "veteranIen" , "mRender":function(data, type, assessment){ return "<input type='checkbox' name='veteranIen' value=" + assessment.veteranIen + " />"}},
				{ "mData": "lastName"},
				{ "mData": "firstName"},
				{ "mData": "middleName"},
				{ "mData": "apptDate"},
	        	{ "mData": "apptTime"}];
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

	// Search
	$scope.searchDatabase = function() {
		alert("search start ");
		var oTable = $('#selectVeteransTable').dataTable();
		oTable.dataTable().fnDraw(true);
    	oTable.fnSettings().oLanguage.sEmptyTable = "<div class='alert alert-danger' aria-hidden='false' role='row'>No matching records found</div>";
		alert("search end ");
	};

	$scope.getDataForSearch = function( sSource, aoData, fnCallback, oSettings ) {		
		// aoData.push( { "name": "more_data_QQQ", "value": "my_value_QQQ" } );
		alert("Before ");
    	aoData.push( { "name": "more_data_ABC", "value": "my_value_ABC" } );
		aoData.push( { "name": "veteranSearchFormBean", "value": $scope.veteranSearchFormBean } );
    	aoData.push( { "name": "veteranSearchFormBean.lastName", "value": $scope.veteranSearchFormBean.lastName } );
		alert("In ");
				
    	oSettings.jqXHR = $.ajax( {
    		"dataType": 'json',
    		"type": "POST",
    		"url": sSource,
    		"data": aoData,
    		"success": fnCallback
    	} );
    };

    $scope.exportDataGrid = function(viewPath) {
		var oTable = $('#selectVeteransTable').dataTable();
		var oSettings = oTable.fnSettings();
				
				
		var params = [];
		params.push( { "name": "iSortCol_0", "value": oSettings.aaSorting[0][0] } );
		params.push( { "name": "sSortDir_0", "value": oSettings.aaSorting[0][1] } );
		params.push( { "name": "iDisplayStart", "value": oSettings._iDisplayStart } );
		params.push( { "name": "iDisplayLength", "value": oSettings._iDisplayLength } );
		params.push( { "name": "veteranSearchFormBean.veteranId", "value": $scope.veteranSearchFormBean.lastName } );


		$window.open(viewPath + "?" + $.param(params));
	};

	 
	console.log("inside the controller");

	// Initialize the model.

	$scope.clinicsList = [];
	
	// Populate dropdown lists.
	updateClinicList();
	initializeModelSync($scope);
	
	$scope.refreshClinicList = updateClinicList;

	/**
	 * Updates the Clinic dropdown box. Method inspects the show all model property 
	 * when calling the web service.
	 */
	function updateClinicList() {
		
		// Null out current selected item and clear list.
		$scope.veteranSearchFormBean.clinicId = "";
		//console.log(">> "+ $scope.veteranSearchFormBean.clinicId);
		$scope.clinicsList = [];

		// Create the request parameters we will post.
		var requestPayload = {};
		if ($scope.veteranSearchFormBean.showDeletedclinics) {
			requestPayload = $.param({ "includeAll": true });
		}
		else {
			requestPayload = $.param({ "includeAll": false });
		}

		// Call the web service and update the model.
		$http({
			method: "GET",
			url: "clinics/list",
			responseType: "json",
			headers: {'Content-Type': 'application/x-www-form-urlencoded'},
			data: requestPayload})
				.success(function(data, status, headers, config) {
					$scope.clinicsList = data;
				})
				.error(function(data, status) {
					//
				});
	}


	/**
	 * Initializes the model with any parameters initially passed to the controller. 
	 */
	function initializeModel() {
		$scope.veteranSearchFormBean = {};
	}

	$scope.callFilters = function () {	    
	    $scope.destroyDataTable($scope.filter.clinicId);
	}
	
	/**
	 * Initializes the model with any parameters initially passed to the controller synchronously.
	 */
	function initializeModelSync(scope) {
	}
};


 

$(document).ready(function() {
    // Load current tab
    tabsLoad("selectVeteran");

	//var timestamp = "1392822000000";
	//new Date(unixTime*1000);
	
	///alert("timestamp" + timestamp);
	//alert("D" + jsDate.toDateString());
	//alert("M" + jsDate.getMonth());
	//alert("Y" + jsDate.getYear());
		
    // Date Picker Start - Call picker and focus for 508         
    var fromAssessmentDateGroup  = "#fromAssessmentDateGroup";
    var toAssessmentDateGroup    = "#toAssessmentDateGroup";
    $(fromAssessmentDateGroup).datepicker({
			showOn : 'button',
      format: 'mm/dd/yyyy',
      autoclose: true
		});

		$(toAssessmentDateGroup).datepicker({
			showOn : 'button',
      format: 'mm/dd/yyyy',
			autoclose: true
		});
	
		$('.id_header_tooltip').tooltip({
			'placement': 'top'
		});
});