var app = angular.module('editVeteransAssessmentFormApp', []);

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
				"bLengthChange": false,
				"bInfo": false,
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
    tabsLoad("createBattery");

	// Tri State Check
   	var $check = $(".tri input[type=checkbox]"), el;
	$check
   .data('checked',0)
   .click(function(e) {
        el = $(this);  
        switch(el.data('checked')) {
            // unchecked, going indeterminate
            case 0:
                el.data('checked',1);
                el.prop('indeterminate',true);
                break;
            
            // indeterminate, going checked
            case 1:
                el.data('checked',2);
                el.prop('indeterminate',false);
                el.prop('checked',true);                
                break;
            
            // checked, going unchecked
            default:  
                el.data('checked',0);
                el.prop('indeterminate',false);
                el.prop('checked',false);
        }
    });
	
	
	/* Need to clean - From edit Veteran Assessment */
	var module_values = [];
	var reset_check = false;
	
	$(".module_list").find(':checked').each(function() {
		   module_values.push($(this).val());
	});
	
	$(".clear_all").on("click", function(e) {
		e.preventDefault();
		clearAllSelectins();
	});

	$(".clear_all_modules").on("click", function(e) {
		e.preventDefault();
		clearAllModulesSelectins();
	});		
	
	$(".reset").on("click", function(e) {
		e.preventDefault();
		$('input:checkbox').removeAttr('checked');
		$("tr").removeClass("highlight");
		for ( var i in module_values ) {
			$("input:checkbox[value="+module_values[i]+"]").prop('checked', true);
		}
		reset_check = false;
	});
	
	$(".battery_list li span").on("click", function(e) {
		e.preventDefault();
		if (this.className.indexOf("highlight") > -1) {
			$("tr").removeClass("highlight");
			var classes = $(this).attr('class').split(' ');

			for(var i=0; i<classes.length; i++){
			  $("."+classes[i]).closest("tr").addClass("highlight"); 
			}	
		}else{
			//$('input:checkbox').removeAttr('checked');
			$("." + $(this).attr('class')).prop('checked', true);
			$("tr").removeClass("highlight");
			var classes = $(this).attr('class').split(' ');

			for(var i=0; i<classes.length; i++){
				  $("."+classes[i]).closest("tr").addClass("highlight"); 
				}
		}
	});
	
	
	
	$(".battery_list input").on("click", function(e) {
			$(".module_list tr input").prop('checked', false);
			
			if(reset_check == false){
				for ( var i in module_values ) {
					$("input:checkbox[value="+module_values[i]+"]").prop('checked', true);
				}
			}
			
			$(".module_list  ." + $(this).attr('class')).prop('checked', true);
			$(".module_list tr").removeClass("highlight");
			
			var classes = $(this).attr('class').split(' ');

			
			for(var i=0; i<classes.length; i++){
				  $(".module_list ."+classes[i]).closest("tr").addClass("highlight"); 
			}
	});
	
	
	var selectedProgramId = $("#selectedProgramId").val();
	$li.hide().filter(".program_" + selectedProgramId).show();
	if((selectedProgramId == "") || (typeof selectedProgramId == "undefined" )){
		$li.show();	
	}
	
	
	
	/* Need to clean - From edit Veteran Assessment */
	
		$('#selectedProgramId').change(function() {
		$.ajax({
			url: 'editVeteranAssessment/programs/' + $('#selectedProgramId').val() + '/clinics',
			dataType: 'json',
			type: 'GET',
			success: function(data) {
				$('#selectedClinicId').empty(); // clear the current elements in select box
				$('#selectedClinicId').append($('<option></option>').attr('value', '').text('Please Select a Clinic'));
				for (row in data) {
					$('#selectedClinicId').append($('<option></option>').attr('value', data[row].stateId).text(data[row].stateName));
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//alert(errorThrown);
			}
		});

		$.ajax({
			url: 'editVeteranAssessment/programs/' + $('#selectedProgramId').val() + '/noteTitles',
			dataType: 'json',
			type: 'GET',
			success: function(data) {
				$('#selectedNoteTitleId').empty();
				$('#selectedNoteTitleId').append($('<option></option>').attr('value', '').text('Please Select a Note Title'));
				for (row in data) {
					$('#selectedNoteTitleId').append($('<option></option>').attr('value', data[row].stateId).text(data[row].stateName));
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//alert(errorThrown);
			}
		});

		$.ajax({
			url: 'editVeteranAssessment/programs/' + $('#selectedProgramId').val() + '/clinicians',
			dataType: 'json',
			type: 'GET',
			success: function(data) {
				$('#selectedClinicianId').empty();
				$('#selectedClinicianId').append($('<option></option>').attr('value', '').text('Please Select a Clinician'));
				for (row in data) {
					$('#selectedClinicianId').append($('<option></option>').attr('value', data[row].stateId).text(data[row].stateName));
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//alert(errorThrown);
			}
		});
		
		// Filter 
		$(".program_1").addClass("hide2");	        
	});
	
	
	
	// Filter Batteries that assigned to a specific program - JH
	function clearAllSelectins() {
		$('input:checkbox').removeAttr('checked');
		$('input:radio').removeAttr('checked');
		$("tr").removeClass("highlight");
		reset_check = true;
	}
	
	function clearAllModulesSelectins() {
		$('input:checkbox').removeAttr('checked');
		$("tr").removeClass("highlight");
		reset_check = true;
	}
	
	var $li = $('.battery_list').find('li');
	$("#selectedProgramId").on("change", function(e) {
		var selectedProgramId = $("#selectedProgramId").val();
		$li.hide().filter(".program_" + selectedProgramId).show();
		//clearAllSelectins(); // Clear All Module Selections
		
		if((selectedProgramId == "") || (typeof selectedProgramId == "undefined" )){
			$li.show();	
		}
	});
	
	$(function() {
		$('#selectedClinicId').change(function() {
		});
	});
			
	
});