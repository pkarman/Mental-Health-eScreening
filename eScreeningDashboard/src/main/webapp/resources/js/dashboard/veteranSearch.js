
var app = angular.module('veteranSearchFormApp', []);

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
	    	"sAutoWidth":true,
	    	"aLengthMenu": [[10,25,50,100,250,500,750,1000], [10,25,50,100,250,500,750,1000]],
	    	"sPaginationType": "full_numbers",
	    	"sServerMethod": "POST",
	    	"sAjaxSource": "veteranSearch/services/veterans/search",
	    	"fnServerData": scope.$eval(attrs.fnDataCallback)
        };

        var aoColumns = {};
        aoColumns = [
 			{ "mData": "veteranId","sClass":"numeric", "sWidth":"25px"},
 			{ "mData": "veteranName" },
 			{ "mData": "ssnLastFour","sClass":"numeric" },
 			{ "mData": "email" },
 			{ "mData": "gender" },
 			{ "mData": "lastAssessmentDate", "sType": "date","sClass":"numeric" },
 			{ "mData": "totalAssessments","sClass":"numeric", "sWidth":"25px", "mRender": function(data, type, full) { return '<a href="assessmentReport?vid='+full.veteranId+'" class="btn btn-primary btn-xs">View Total Assessments <span class="badge">' + data + '</span></a>'; } }
 		];
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
 * @param $window
 */
function veteranSearchController($scope,$element,$http,$window) {

	// Initialize the model.
	$scope.veteranSearchFormBean = {};

	// Search
	$scope.searchDatabase = function() {
		var oTable = $('.jqueryDataTable').dataTable();
		oTable.dataTable().fnDraw(true);
	};

	$scope.getDataForSearch = function( sSource, aoData, fnCallback, oSettings ) {
		
		aoData.push( { "name": "more_data_QQQ", "value": "my_value_QQQ" } );
		aoData.push( { "name": "veteranSearchFormBean", "value": $scope.veteranSearchFormBean } );
		aoData.push( { "name": "veteranSearchFormBean.veteranId", "value": $scope.veteranSearchFormBean.veteranId } );
		aoData.push( { "name": "veteranSearchFormBean.lastName", "value": $scope.veteranSearchFormBean.lastName } );
		aoData.push( { "name": "veteranSearchFormBean.ssnLastFour", "value": $scope.veteranSearchFormBean.ssnLastFour } );

		oSettings.jqXHR = $.ajax( {
			"dataType": 'json',
			"type": "POST",
			"url": sSource,
			"data": aoData,
			"success": fnCallback
		} );
	};

	$scope.exportDataGrid = function(viewPath) {
		var oTable = $('#veteranSearchTable').dataTable();
		var oSettings = oTable.fnSettings();

//		console.log( JSON.stringify(oSettings.aaSorting) );
//		console.log( oSettings.aaSorting[0][0] ); // column index
//		console.log( oSettings.aaSorting[0][1] ); // sort direction
//		console.log( oSettings._iDisplayStart );
//		console.log( oSettings._iDisplayLength );
//		console.log( $scope.veteranSearchFormBean.veteranId );
//		console.log( $scope.veteranSearchFormBean.lastName );
//		console.log( $scope.veteranSearchFormBean.ssnLastFour );

		var params = [];
		params.push( { "name": "iSortCol_0", "value": oSettings.aaSorting[0][0] } );
		params.push( { "name": "sSortDir_0", "value": oSettings.aaSorting[0][1] } );
		params.push( { "name": "iDisplayStart", "value": oSettings._iDisplayStart } );
		params.push( { "name": "iDisplayLength", "value": oSettings._iDisplayLength } );
		params.push( { "name": "veteranSearchFormBean.veteranId", "value": $scope.veteranSearchFormBean.veteranId } );
		params.push( { "name": "veteranSearchFormBean.lastName", "value": $scope.veteranSearchFormBean.lastName } );
		params.push( { "name": "veteranSearchFormBean.ssnLastFour", "value": $scope.veteranSearchFormBean.ssnLastFour } );

		$window.open(viewPath + "?" + $.param(params));
	};
}
