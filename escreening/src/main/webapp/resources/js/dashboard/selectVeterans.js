$(document).ready(function() { 
    // Load current tab
    tabsLoad("createBattery");

	//var timestamp = "1392822000000";
	//new Date(unixTime*1000);
		
    // Date Picker Start - Call picker and focus for 508         
    var fromAssessmentDateGroup  = "#fromAssessmentDateGroup";
    var toAssessmentDateGroup    = "#toAssessmentDateGroup";
    $(fromAssessmentDateGroup).datepicker({
			showOn : 'button',
      format: 'mm/dd/yyyy',
			autoclose: true,
			startDate: 'd'
	});

	$(toAssessmentDateGroup).datepicker({
			showOn : 'button',
      format: 'mm/dd/yyyy',
			autoclose: true,
			startDate: 'd'
		});

		$('.id_header_tooltip').tooltip({
			'placement': 'top'
		});

		$('#selectedClinic').on('change', function() {
			$("#clinicId").val(this.value);
		});
	
		// Select/Deselect all vetIensCheckbox
		var selectAll = "#selectAll";
		var vetIensCheckbox = ".vetIensCheckbox";

		var vetIensCheckboxChecked  = ".vetIensCheckbox:checked";
		var createAssessmentButton  = ".createAssessmentButton";
		var vetIensCheckbox 		= ".vetIensCheckbox";
		var selectAllChecked  = '#selectAll:checked';


		
		// selectAll on click
		$(selectAll).click (function () {
			 var checkedStatus = this.checked;
			$(vetIensCheckbox).each(function () {
				$(this).prop('checked', checkedStatus);
			 });
		});

		// selectAll on change
		$(selectAll).change(function() {
			if($(this).is(":checked")) {
				$(createAssessmentButton).removeAttr('disabled');
			}else{
				$(createAssessmentButton).attr('disabled','disabled');
			}
	});
	
	// vetIensCheckbox checkbox on change
	$(vetIensCheckbox).change(function() {
		if($(vetIensCheckboxChecked).length >= 1) {
			$(createAssessmentButton).removeAttr('disabled');
		}else if($(vetIensCheckboxChecked).length <= 0) {
			$(createAssessmentButton).attr('disabled','disabled');
		}
		if($(vetIensCheckboxChecked).length < $(vetIensCheckbox).length){
			$('#selectAll').prop('checked', false)
		}else if ($(vetIensCheckboxChecked).length == $(vetIensCheckbox).length){
			$('#selectAll').prop('checked', true);
		}
	});


	// Add Ladda preloader
	Ladda.bind( '.createAssessmentButton', 20000 );


	/* Disabled for testing purpose
	$("#startDate").on("change focusout input", function() {
			var date = $('#startDate').val();
			var endDate 	= $('#endDate').val();
			date = vDate(date);
	
			if (date < new Date()  || date > vDate(endDate)) {
				$(".startDateError").removeClass("hide");
			}else{
				$(".startDateError").addClass("hide");
			}
	});

	$("#endDate").on("change focusout input", function() {
			var date 		= $('#endDate').val();
			var startDate 	= $('#startDate').val();
			date = vDate(date);
			console.log("startDate" );
			console.log(startDate);
			
			if (date < new Date() || date < vDate(startDate)) {
				$(".endDateError").removeClass("hide");
			}else{
				$(".endDateError").addClass("hide");
			}
	});


	function vDate(date){
		var parts = date.split('/');
		var date = new Date(parseInt(parts[2], 10),     // year
							parseInt(parts[1], 10) - 1, // month, starts with 0
							parseInt(parts[0], 10));    // day
		return date;
	}
	*/
	
	function formatClinic (clinic) {
		
		var markup = '<div class="clearfix">' +
		'<div class="col-sm-6">' + clinic.name + '</div>';
		
//		'<div class="col-sm-3"><i class="fa fa-code-fork"></i> ' + repo.forks_count + '</div>' +
//		'<div class="col-sm-2"><i class="fa fa-star"></i> ' + repo.stargazers_count + '</div>' +
//		'</div>';
//
//
//
//		markup += '</div></div>';

		return markup;
		//return '<option value="' + clinic.id + '">' + clinic.name + '</option>';
		
	}

	function formatClinicSelection (clinic) {
		return clinic.name;
	}
	
	$(".clinicSelect").select2({
		  placeholder: "Please Select VistA Clinic",
		  allowClear: true,
		  escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
		  minimumInputLength: 3,
		  ajax: {
			  url: function (params) {
				  return "clinics/" + params.term
			  },
			  dataType: 'json',
			  delay: 250,
			  data: function (params) {
				  return {
					  q: null,
					  page: params.page
				  };
			  },
			  processResults: function (data, page) {
				  //this really should be removed and the DropDownObjects should be made generic
				  var items = [];
				  $.each(data, function(index, clinic){
					  items.push({
						"id": clinic.stateId,
					   	"name": clinic.stateName,
					  });	
				  });
				  return {
					  results: items
				  };
			  },
			  cache: true,
			  templateResult: formatClinic, 
			  templateSelection: formatClinicSelection
		  }
	});								
});