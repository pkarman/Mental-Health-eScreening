
var app = angular.module('assessmentReportFormApp', []);

/**
 * Looks for the report-table attribute on a table to provide JQuery datatable features.
 * fnDataCallback: This attribute is used to bind the data to the datatable.
 */
app.directive('reportTable', function() {
	return function(scope, element, attrs) {

        var options = {};
        options = {
	    	"bProcessing": true,
	    	"bServerSide": true,
	    	"bFilter": false,
	    	"bJQueryUI": true,
	    	"aLengthMenu": [[10,25,50,100,250,500,750,1000], [10,25,50,100,250,500,750,1000]],
	    	"sPaginationType": "full_numbers",
	    	"sServerMethod": "POST",
	    	"sAjaxSource": "assessmentReport/services/assessments/search",
	    	"fnServerData": scope.$eval(attrs.fnDataCallback)
        };

        var aoColumns = {};
        aoColumns = [
			{ "mData": "veteranAssessmentId","sClass":"numeric", "sWidth":"45px"},
			{ "mData": "programName"},
			{ "mData": "clinicianName"},
			{ "mData": "createdBy", "sWidth":"75px"},
			{ "mData": "assessmentDate","sClass":"numeric", "sWidth":"158px"},
			{ "mData": "veteranId","sClass":"numeric", "sWidth":"100px"},
			{ "mData": "veteranName"},
			{ "mData": "assessmentStatusName", "sWidth":"80px"},
			{ "mData": "veteranAssessmentId", "bSortable": false, "sClass":"alignCenter", "mRender": function(data, type, full) { return '<a href="#" vaidurl="assessmentPreview?vaid='+full.veteranAssessmentId+'" class="assessmentPreviewIFramePush" data-title="Assessment Report Preview"><img class="imgSize" src="../resources/images/Assess_icon.png" title="Link to View Assessment"></a>&nbsp;&nbsp;<a href="#"  class="assessmentNotePush" vaid='+full.veteranAssessmentId+' data-title="Review Note"><img class="imgSize" src="../resources/images/review.png" title="Link to Review Notes"></a>&nbsp;&nbsp;<a class="assessmentPreviewIFramePush" href="assessments/'+full.veteranAssessmentId+'/assessmentAuditLog/report/pdf" vaidurl="assessments/'+full.veteranAssessmentId+'/assessmentAuditLog/report/pdf" data-title="Audit Log"><img class="imgSize" src="../resources/images/auditLog.png" title="Link to View Audit Log"></a>'; }}];

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

/**
 * Controller for the page.
 * @param $scope
 * @param $element
 * @param $http
 */
function assessmentReportController($scope,$element,$http,$window) {

	//console.log("inside the controller");

	// Initialize the model.
	$scope.assessmentReportFormBean = {};
	$scope.assessmentSearchResult = {};
	$scope.programList = [];
	$scope.clinicanList = [];
	$scope.createdByUserList = [];

	// Populate dropdown lists.
	updateProgramList();
	updateClinicianList();
	updateCreatedByUserList();
	initializeModelSync($scope);
	
	$scope.searchDatabase = function() {
		var oTable = $('#assessmentReportTable').dataTable();
		oTable.dataTable().fnDraw(true);
	};

	$scope.getDataForSearch = function( sSource, aoData, fnCallback, oSettings ) {
    	
    	aoData.push( { "name": "assessmentReportFormBean", "value": $scope.assessmentReportFormBean } );
    	aoData.push( { "name": "assessmentReportFormBean.veteranAssessmentId", "value": $scope.assessmentReportFormBean.veteranAssessmentId } );
    	aoData.push( { "name": "assessmentReportFormBean.veteranId", "value": $scope.assessmentReportFormBean.veteranId } );
    	aoData.push( { "name": "assessmentReportFormBean.programId", "value": $scope.assessmentReportFormBean.programId } );
    	aoData.push( { "name": "assessmentReportFormBean.clinicianId", "value": $scope.assessmentReportFormBean.clinicianId } );
    	aoData.push( { "name": "assessmentReportFormBean.showDeletedClinicians", "value": $scope.assessmentReportFormBean.showDeletedClinicians } );
    	aoData.push( { "name": "assessmentReportFormBean.createdByUserId", "value": $scope.assessmentReportFormBean.createdByUserId } );
    	aoData.push( { "name": "assessmentReportFormBean.showDeletedAssessmentCreators", "value": $scope.assessmentReportFormBean.showDeletedAssessmentCreators } );
    	aoData.push( { "name": "assessmentReportFormBean.fromAssessmentDate", "value": $scope.assessmentReportFormBean.fromAssessmentDate } );
    	aoData.push( { "name": "assessmentReportFormBean.toAssessmentDate", "value": $scope.assessmentReportFormBean.toAssessmentDate } );

    	oSettings.jqXHR = $.ajax( {
    		"dataType": 'json',
    		"type": "POST",
    		"url": sSource,
    		"data": aoData,
    		"success": fnCallback
    	} );
    };

    $scope.exportDataGrid = function(viewPath) {
		var oTable = $('#assessmentReportTable').dataTable();
		var oSettings = oTable.fnSettings();

		var params = [];
		params.push( { "name": "iSortCol_0", "value": oSettings.aaSorting[0][0] } );
		params.push( { "name": "sSortDir_0", "value": oSettings.aaSorting[0][1] } );
		params.push( { "name": "iDisplayStart", "value": oSettings._iDisplayStart } );
		params.push( { "name": "iDisplayLength", "value": oSettings._iDisplayLength } );
		params.push( { "name": "assessmentReportFormBean.veteranAssessmentId", "value": $scope.assessmentReportFormBean.veteranAssessmentId } );
		params.push( { "name": "assessmentReportFormBean.veteranId", "value": $scope.assessmentReportFormBean.veteranId } );
		params.push( { "name": "assessmentReportFormBean.programId", "value": $scope.assessmentReportFormBean.programId } );
		params.push( { "name": "assessmentReportFormBean.clinicianId", "value": $scope.assessmentReportFormBean.clinicianId } );
		params.push( { "name": "assessmentReportFormBean.showDeletedClinicians", "value": $scope.assessmentReportFormBean.showDeletedClinicians } );
		params.push( { "name": "assessmentReportFormBean.createdByUserId", "value": $scope.assessmentReportFormBean.createdByUserId } );
		params.push( { "name": "assessmentReportFormBean.showDeletedAssessmentCreators", "value": $scope.assessmentReportFormBean.showDeletedAssessmentCreators } );
		params.push( { "name": "assessmentReportFormBean.fromAssessmentDate", "value": $scope.assessmentReportFormBean.fromAssessmentDate } );
		params.push( { "name": "assessmentReportFormBean.toAssessmentDate", "value": $scope.assessmentReportFormBean.toAssessmentDate } );

		$window.open(viewPath + "?" + $.param(params));
	};

	$scope.refreshClinicianList = updateClinicianList;
	$scope.refreshCreatedByUserList = updateCreatedByUserList;
	

	/**
	 * Updates the Program dropdown box.
	 */
	function updateProgramList() {
		
		// Null out current selected item and clear list.
		$scope.assessmentReportFormBean.programId = "";
		$scope.programList = [];

		// Call the web service and update the model.
		$http({
			method: "POST",
			url: "assessmentReport/services/user/programs",
			responseType: "json"})
				.success(function(data, status, headers, config) {
					$scope.programList = data;
				})
				.error(function(data, status) {
					//
				});
	}

	/**
	 * Updates the clinician dropdown box. Method inspects the show all model property 
	 * when calling the web service.
	 */
	function updateClinicianList() {
		
		// Null out current selected item and clear list.
		$scope.assessmentReportFormBean.clinicianId = "";
		$scope.clinicanList = [];

		// Create the request parameters we will post.
		var requestPayload = {};
		if ($scope.assessmentReportFormBean.showDeletedClinicians) {
			requestPayload = $.param({ "includeAll": true });
		}
		else {
			requestPayload = $.param({ "includeAll": false });
		}

		// Call the web service and update the model.
		$http({
			method: "POST",
			url: "assessmentReport/services/user/clinicians",
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
		$scope.assessmentReportFormBean.createdByUserId = "";
		$scope.createdByUserList = [];
		
		// Create the request parameters we will post.
		var requestPayload = {};
		if ($scope.assessmentReportFormBean.showDeletedAssessmentCreators) {
			requestPayload = $.param({ "includeAll": true });
		}
		else {
			requestPayload = $.param({ "includeAll": false });
		}

		// Call the web service and update the model.
		$http({
			method: "POST",
			url: "assessmentReport/services/user/assessmentCreators",
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
		
		//console.log("Inside initializeModel()");
		
		$scope.assessmentReportFormBean = {};
		
		// Call the web service and update the model.
		$http({
			method: "GET",
			url: "assessmentReport/services/assessments/search/init",
			responseType: "json"})
				.success(function(data, status, headers, config) {
					$scope.assessmentReportFormBean = data;
				})
				.error(function(data, status) {
					//
				});
	}


	/**
	 * Initializes the model with any parameters initially passed to the controller synchronously.
	 */
	function initializeModelSync(scope) {
		
		//console.log("Inside initializeModelSync()");
		
		$scope.assessmentReportFormBean = {};
		
		$.ajax({
            async: false,
            url: "assessmentReport/services/assessments/search/init",
            type: "GET",
            success: function(data) {
            	scope.assessmentReportFormBean = data;
            }
		});
	}
};
