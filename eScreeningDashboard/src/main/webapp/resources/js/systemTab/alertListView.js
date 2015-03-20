var app = angular.module('alertViewApp', []);
app.directive('reportTable', function() {
	return function(scope, element, attrs) {
        var options = {};
        options = {
	    	"bProcessing": true,
	    	"bServerSide": false,
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
	    	"sAjaxSource": "alertTypes/",
			"sAjaxDataProp": "payload",
	    	"fnServerData": scope.$eval(attrs.fnDataCallback)
        };

        var aoColumns = {};
        aoColumns = [
			{ "mData": "stateName",  "sClass": "col-md-10 text-left"},
			{ "mData": "stateId" , "sClass": "text-right col-md-2", "bSortable" : false , "mRender": function(data, type, row) { return '<a href="alertEditView?aid='+row.stateId+'" class="btn btn-default btn-xs cursor-pointer"><span class="glyphicon glyphicon-chevron-right"></span> Edit </a> &nbsp; &nbsp; <a href="#" class="btn btn-default btn-xs cursor-pointer deleteModal"  data-toggle="modal" data-target="#deleteModal" data-aid='+row.stateId+'><span class="glyphicon glyphicon-remove-circle red-color"></span> Delete </a>'; }}];
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
		
	// Delete Alert
	var data_aid;
	$(this).on('click', '.deleteModal', function() {
		data_aid  = $(this).attr("data-aid");
		$(".confirmDelete").attr("data-aid", data_aid );
	});

	$(this).on('click', '.confirmDelete', function() {
		data = "id=" + data_aid ;
		$.ajax({
			url: "alertTypes/delete",
			type: "POST",
			data: data,
			success: function(){
				window.location.href = "alertListView";
        	}
		})
	});

	// Query String
	function getParameterByName(name) {
		name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
		var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
			results = regex.exec(location.search);
		return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	}
	
	var msg = getParameterByName('msg');
	
	if(msg == "s"){
		$("#successMsg").removeClass("hide");
	}
});