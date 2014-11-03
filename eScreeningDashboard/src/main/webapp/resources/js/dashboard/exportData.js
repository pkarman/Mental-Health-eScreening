var app = angular.module('exportDataFormApp', []);

/**
 * Looks for the report-table attribute on a table to provide JQuery datatable features.
 * fnDataCallback: This attribute is used to bind the data to the datatable.
 */

app.directive('reportTable', function() {
	return function(scope, element, attrs) {
		// check dataType to update datatable link	
		function checkDataType(exportType, exportLogId){
			if (exportType == "Identified"){
				ngclick = "angular.element(this).scope().exportIdentifiedDataButton("+exportLogId+")";
				return ngclick;
			}else{
				ngclick = "angular.element(this).scope().exportDeidentifiedDataButton("+exportLogId+")";
				return ngclick;
			}
		}
        
    var dataTable;
		var sourceURL = "exportData/services/exports/exportLog";
		
		exportDataTable(sourceURL);
		
		
		function exportDataTable(sourceURL){
			var options = {};
	        options = {
		    	"bProcessing": true,
		    	"bServerSide": false,
		    	"bFilter": false,
		    	"bJQueryUI": true,
				"bAutoWidth": false,
		    	"sPaginationType": "full_numbers",
		    	"sServerMethod": "POST",
		    	"sAjaxSource": sourceURL,
		    	"fnServerData": scope.$eval(attrs.fnDataCallback)
	        };
	
	
	        var aoColumns = {};
	        aoColumns = [
				{ "mData": "exportedOn", "type": "select", "values": [ '1 week', '2 weeks']},
				{ "mData": "exportedBy"},
				{ "mData": "assignedClinician"},
				{ "mData": "createdByUser"},
				{ "mData": "exportType"},
				{ "mData": "assessmentStartDate", "bSortable": false,"mRender":function(data, type, assessment){
				       return "Start: " + assessment.assessmentStartDate + "<br > End: " +assessment.assessmentEndDate;}},
				{ "mData": "programName"},
				{ "mData": "veteranId","sClass":"numeric", "sWidth":"45px"},
				{ "mData": "comment", "sWidth":"150px", "sClass":"wrap", "bSortable": false},
	        	{ "mData": "exportLogId", "bSortable": false, "sClass":"alignCenter", "mRender": function(data, type, full) { return '<a href="#"  data-toggle="modal" data-target="#modal_confirmation" onclick="'+checkDataType(full.exportType, full.exportLogId)+'" ng-model="exportIdentifiedDataButton" data-exportLogId="'+full.exportLogId+'"  data-exportType="exportAgain" class="font-size-24"><span class="glyphicon glyphicon-download"></span></a>'; }}];
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
		
		scope.destroyDataTable = function(stateId) {
			dataTable.fnDestroy();
			sourceURL = "exportData/services/exports/exportLog/" + stateId;
		    exportDataTable(sourceURL);
		}
        
    };
});


/* Program List Service*/
app.factory('programListService', function($http) {
	return {
		getProgramList : function() {
			return $http({
				method : "POST",
				url : "exportData/services/user/programs",
				responseType : "json"
			}).then(function(result) {
				return result.data;
			});
		}
	};
});


/* Program List Service*/
app.factory('filterOptionService', function($http) {
	return {
		getFilterOptionList : function() {
			return $http({
				method : "POST",
				url : "exportData/services/exports/filterOptions",
				responseType : "json"
			}).then(function(result) {
				// console.log(">>>>>>> DATA >>> ");
				// console.log(result.data.stateId);
				return result.data;
				
			});
		}
	};
});

/**
 * Controller for the page.
 * @param $scope
 * @param $element
 * @param $http
 */
function exportDataController($scope,$element,$http,$window, programListService, filterOptionService) {	
	/*
		Modal view button for Identified Data
		Call different alert type and change the text
	*/
	$scope.exportIdentifiedDataButton = function(exportLogId){
		$scope.exportDataFormBean.exportDataType = 'identified';
		$("#alertType").removeClass("alert-warning").addClass("alert-danger");
		$("#alertData").text("Identified Data");
    $("#myModalLabel").text("Exporting Identified Data Confirmation");
		$("#comment").val("");
		$scope.exportDataFormBean.exportLogId = exportLogId;
    };
	
	
	/* Modal view button for De-Identified Data
	   Call different alert type and change the text */
		
	$scope.exportDeidentifiedDataButton = function(exportLogId){
		$scope.exportDataFormBean.exportDataType = 'deidentified';
		$("#alertType").removeClass("alert-danger").addClass("alert-warning");
		$("#alertData").text("De-Identified Data");
    $("#myModalLabel").text("Exporting De-Identified Data Confirmation");
		$("#comment").val("");
		$scope.exportDataFormBean.exportLogId = exportLogId;
    };
    

	$scope.launchDataDictionaryButton = function(){
		
	    $http({method: 'GET', url: 'exportData/services/exports/dataDictionary'}).
	      success(function(data, status, headers, config) {
	    	
	        window.open("exportData/services/exports/dataDictionary", "_self");
	        $('#modal_progress').modal('hide');
	        $("#progressMessages").html("");
	      }).
	      error(function(data, status, headers, config) {
	    	  $("#progressMessages").html("<div class='alert alert-danger' role='alert'>Error occurred.</div>");
	        //$("#modal_progress .progress").hide();
	      });
	};
	
	 
	//console.log("inside the controller");

	// Initialize the model.
	$scope.exportDataFormBean = {};
	$scope.assessmentSearchResult = {};
	$scope.clinicanList = [];
	$scope.createdByUserList = [];

	
	//$scope.exportDataFormBean.comment = "Welcome";
	
	
	// Populate dropdown lists.
	updateClinicianList();
	updateCreatedByUserList();
	initializeModelSync($scope);
	
	$scope.searchDatabase = function() {
		console.log("Export Started");
		var modal_confirmation = $('#modal_confirmation');
		
		if ( typeof $scope.exportDataFormBean.exportLogId === "undefined"){
			// if there export data for first time
			$scope.qstring = "";
			$scope.qstring = "fromAssessmentDate=" + $scope.exportDataFormBean.fromAssessmentDate;
			$scope.qstring = $scope.qstring + "&toAssessmentDate=" + $scope.exportDataFormBean.toAssessmentDate;
			$scope.qstring = $scope.qstring + "&clinicianId=" + $scope.exportDataFormBean.clinicianId;
			$scope.qstring = $scope.qstring + "&showDeletedClinicians=" + $scope.exportDataFormBean.showDeletedClinicians;
			$scope.qstring = $scope.qstring + "&createdByUserId=" + $scope.exportDataFormBean.createdByUserId;
			$scope.qstring = $scope.qstring + "&showDeletedAssessmentCreators=" + $scope.exportDataFormBean.showDeletedAssessmentCreators;
			$scope.qstring = $scope.qstring + "&programId=" + $scope.exportDataFormBean.programId;
			$scope.qstring = $scope.qstring + "&veteranId=" + $scope.exportDataFormBean.veteranId;
			$scope.qstring = $scope.qstring + "&comment=" + $scope.exportDataFormBean.comment;
			$scope.qstring = $scope.qstring + "&exportDataType=" + $scope.exportDataFormBean.exportDataType;
			console.log("---  V5  ---");
			console.log( $scope.qstring );
			modal_confirmation.modal('hide');
			window.open("exportData/services/exports/exportData?"+$scope.qstring, "_blank");
		}else{
			// if export data again
			$scope.qstring = "";
			$scope.qstring = $scope.qstring + "&comment=" + $scope.exportDataFormBean.comment;
			console.log("Export Again Section ");
			modal_confirmation.modal('hide');
			
			window.open("exportData/services/exports/downloadAgain/"+ $scope.exportDataFormBean.exportLogId+ "?" +$scope.qstring, "_blank");
		}
		
	};

	$scope.getDataForSearch = function( sSource, aoData, fnCallback, oSettings ) {
    	
    	aoData.push( { "name": "more_data_ABC", "value": "my_value_ABC" } );
    	aoData.push( { "name": "exportDataFormBean", "value": $scope.exportDataFormBean } );
    	aoData.push( { "name": "exportDataFormBean.veteranAssessmentId", "value": $scope.exportDataFormBean.veteranAssessmentId } );
    	aoData.push( { "name": "exportDataFormBean.veteranId", "value": $scope.exportDataFormBean.veteranId } );
    	aoData.push( { "name": "exportDataFormBean.clinicianId", "value": $scope.exportDataFormBean.clinicianId } );
    	aoData.push( { "name": "exportDataFormBean.showDeletedClinicians", "value": $scope.exportDataFormBean.showDeletedClinicians } );
    	aoData.push( { "name": "exportDataFormBean.createdByUserId", "value": $scope.exportDataFormBean.createdByUserId } );
    	aoData.push( { "name": "exportDataFormBean.showDeletedAssessmentCreators", "value": $scope.exportDataFormBean.showDeletedAssessmentCreators } );
    	aoData.push( { "name": "exportDataFormBean.fromAssessmentDate", "value": $scope.exportDataFormBean.fromAssessmentDate } );
    	aoData.push( { "name": "exportDataFormBean.toAssessmentDate", "value": $scope.exportDataFormBean.toAssessmentDate } );
		aoData.push( { "name": "exportDataFormBean.programId", "value": $scope.exportDataFormBean.programId } );
		

    	oSettings.jqXHR = $.ajax( {
    		"dataType": 'json',
    		"type": "POST",
    		"url": sSource,
    		"data": aoData,
    		"success": fnCallback
    	} );
    };

    $scope.exportDataGrid = function(viewPath) {
		var oTable = $('#exportDataTable').dataTable();
		var oSettings = oTable.fnSettings();

		var params = [];
		params.push( { "name": "iSortCol_0", "value": oSettings.aaSorting[0][0] } );
		params.push( { "name": "sSortDir_0", "value": oSettings.aaSorting[0][1] } );
		params.push( { "name": "iDisplayStart", "value": oSettings._iDisplayStart } );
		params.push( { "name": "iDisplayLength", "value": oSettings._iDisplayLength } );
		params.push( { "name": "exportDataFormBean.veteranAssessmentId", "value": $scope.exportDataFormBean.veteranAssessmentId } );
		params.push( { "name": "exportDataFormBean.veteranId", "value": $scope.exportDataFormBean.veteranId } );
		params.push( { "name": "exportDataFormBean.clinicianId", "value": $scope.exportDataFormBean.clinicianId } );
		params.push( { "name": "exportDataFormBean.showDeletedClinicians", "value": $scope.exportDataFormBean.showDeletedClinicians } );
		params.push( { "name": "exportDataFormBean.createdByUserId", "value": $scope.exportDataFormBean.createdByUserId } );
		params.push( { "name": "exportDataFormBean.showDeletedAssessmentCreators", "value": $scope.exportDataFormBean.showDeletedAssessmentCreators } );
		params.push( { "name": "exportDataFormBean.fromAssessmentDate", "value": $scope.exportDataFormBean.fromAssessmentDate } );
		params.push( { "name": "exportDataFormBean.toAssessmentDate", "value": $scope.exportDataFormBean.toAssessmentDate } );
		params.push( { "name": "exportDataFormBean.programId", "value": $scope.exportDataFormBean.programId } );

		$window.open(viewPath + "?" + $.param(params));
	};

	$scope.refreshClinicianList = updateClinicianList;
	$scope.refreshCreatedByUserList = updateCreatedByUserList;

	/**
	 * Updates the clinician dropdown box. Method inspects the show all model property 
	 * when calling the web service.
	 */
	function updateClinicianList() {
		
		// Null out current selected item and clear list.
		$scope.exportDataFormBean.clinicianId = "";
		//console.log(">> "+ $scope.exportDataFormBean.clinicianId);
		$scope.clinicanList = [];

		// Create the request parameters we will post.
		var requestPayload = {};
		if ($scope.exportDataFormBean.showDeletedClinicians) {
			requestPayload = $.param({ "includeAll": true });
		}
		else {
			requestPayload = $.param({ "includeAll": false });
		}

		// Call the web service and update the model.
		$http({
			method: "POST",
			url: "exportData/services/user/clinicians",
			responseType: "json",
			headers: {'Content-Type': 'application/x-www-form-urlencoded'},
			data: requestPayload})
				.success(function(data, status, headers, config) {
					$scope.clinicanList = data;
				})
				.error(function(data, status) {
					//
				});
	}

	/**
	 * Updates the Created by dropdown box. Method inspects the show all model property 
	 * when calling the web service.
	 */
	function updateCreatedByUserList() {
		
		// Null out current selected item and clear list.
		$scope.exportDataFormBean.createdByUserId = "";
		$scope.createdByUserList = [];
		
		// Create the request parameters we will post.
		var requestPayload = {};
		if ($scope.exportDataFormBean.showDeletedAssessmentCreators) {
			requestPayload = $.param({ "includeAll": true });
		}
		else {
			requestPayload = $.param({ "includeAll": false });
		}

		// Call the web service and update the model.
		$http({
			method: "POST",
			url: "exportData/services/user/assessmentCreators",
			responseType: "json",
			headers: {'Content-Type': 'application/x-www-form-urlencoded'},
			data: requestPayload})
				.success(function(data, status, headers, config) {
					$scope.createdByUserList = data;
				})
				.error(function(data, status) {
					//
				});
	}
  

	/**
	 * Initializes the model with any parameters initially passed to the controller. 
	 */
	function initializeModel() {
		$scope.exportDataFormBean = {};
	}


	programListService.getProgramList().then(function(data) {
		$scope.programList = data;
	});	


	filterOptionService.getFilterOptionList().then(function(data) {
		 $scope.filter = null;
		$scope.filterOptionList = data;
	});	
	
	
	
	$scope.callFilters = function () {	    
	    $scope.destroyDataTable($scope.filter.stateId);
	}
	
	/**
	 * Initializes the model with any parameters initially passed to the controller synchronously.
	 */
	function initializeModelSync(scope) {
	}
};


 $(document).ready(function() {

    // Load current tab
    tabsLoad("exportData");


    // JH - 508 Set the other page elements hide while modal show to help AT tools
    var modalBlock    = '.modal';
    var outerPageDiv  = '#outerPageDiv';
    
    $(modalBlock).on('shown.bs.modal', function (e) {
        $(outerPageDiv).attr('aria-hidden', 'true');
    });
    $(modalBlock).on('hidden.bs.modal', function (e) {
        $(outerPageDiv).attr('aria-hidden', 'false');
    });


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