var valid = true;
var urlParams;
var docMode = "edit"; // Page Modes: create|edit
var currPage = "createUser";
var currLoginId;
var chPass = false;

$(document)
		.ready(
				function() {
					
					
					
					// Set the tooltip
					$('input').tooltip(
							{
								position : {
									my : "center bottom-20",
									at : "center top",
									using : function(position, feedback) {
										$(this).css(position);
										$("<div>").addClass("arrow").addClass(
												feedback.vertical).addClass(
												feedback.horizontal).appendTo(
												this);
									}
								}
							});
					$(window).load(function() {
						$('#createUserFormContainer').fadeIn(1000);
					});
					
					/*
					 * Create the calls/callbacks for GetUserStatusList and
					 * GetRoleList (#225/#226) within document.ready(). - BH,
					 * 11/18/2013 HTML ids are, #userRoleParam, #userStatusParam
					 */
					$.ajax({
						url : "services/GetRoleList",
						type : 'GET',
						dataType : 'html',
						contentType : 'application/json',
						mimeType : 'application/json',

						success : function(data) {
							var roles = $.parseJSON(data);
							var selected = "";
							var id = "1";
							$.each(roles, function(index, value) {
								var append = $('<option>').attr('value',
										roles[index].stateId).text(
										roles[index].stateName);
								$("#userRoleParamSelect").append($(append));
								if (roles[index].stateId == $('#userRoleParam')
										.val()) {
									selected = $(append);
									id = roles[index].stateId;
								}

							});
							if ($(selected))
								$('#userRoleParamSelect').val(id);
						},

						error : function(jqXHR, textStatus, errorThrown) {
							console.log(textStatus, errorThrown);
						}
					});

					$.ajax({
						url : "services/GetUserStatusList",
						type : 'GET',
						dataType : 'html',
						contentType : 'application/json',
						mimeType : 'application/json',

						success : function(data) {
							var states = $.parseJSON(data);
							var selected = "";
							var id = "1";

							$.each(states, function(index, value) {
								var append = $('<option>').attr('value',
										states[index].stateId).text(
										states[index].stateName);
								$("#userStatusParamSelect").append($(append));
								if (states[index].stateId == $(
										'#userStatusParam').val()) {
									selected = $(append);
									id = states[index].stateId;
								}
								if ($(selected))
									$('#userStatusParamSelect').val(id);
							});

						},

						error : function(jqXHR, textStatus, errorThrown) {
							console.log(textStatus, errorThrown);
						}
					});

					// set the display for the selected tab.
					$("#adminTab").removeClass('menuButtonUnselected');
					$("#adminTab").addClass('menuButtonSelected');

					// set the display for the selected sub menu tab.
					$("#userManagementTab").removeClass(
							'subMenuButtonUnselected');
					$("#userManagementTab").addClass('subMenuButtonSelected');

					// initialize the user datatable
					$(".jqueryDataTable")
							.dataTable(
									{
										"bProcessing" : false,
										"bServerSide" : false,
									
										"sAjaxSource" : "services/GetClinicsList",
										"bJQueryUI" : true,
										"aoColumns" : [

												{
													"mData" : "clinicId","sClass":"numeric", "sWidth": "60px"
												},
												{
													"mData" : null,"sClass":"alignCenter", "sWidth": "80px",
													"mRender" : function(data,
															type, full) {
														return '<input type="checkbox" onclick="checkboxClicked();">';
													}
												},
												{
													"mData" : "clinicName"
												},
												{
													"mData" : "programId",
													"bSearchable" : false,
													"bVisible" : false
												},
												{
													"mData" : "programName"
												},
												{
													"mData" : "locationId",
													"bSearchable" : false,
													"bVisible" : false
												},
												{
													"mData" : "locationName"
												}]
									}).fnSettings().aoDrawCallback
							.push({
								"fn" : function() {
									// get the rows.
									var rows = $(".jqueryDataTable tr:gt(0)");
									//var myrow = rows[1];
									//console.log(myrow);
									//$(myrow).find("td:eq(4)").prop('checked', true);
									//alert("IN");
									
									// Add checkboxes for user's clinics.
									if (docMode == 'edit') {
										var clinics = $('#clinicsParam').val();
										var clinicsArr = clinics.split(',');
										console.log(clinicsArr);

										rows
												.each(function(index) {
													var clinicID = $(
															rows[index]).find(
															"td:eq(0)").html();
													var checkBoxTD = $(
															rows[index]).find(
															"td:eq(1)");
													var inputElements = checkBoxTD
															.find(':input');
													for (var i = 0; i < clinicsArr.length; i++) {
														
														if (clinicsArr[i] == clinicID)
															inputElements.prop(
																	'checked',
																	true);
													}
													
													// $(".jqueryDataTable tr:gt(0)").find("td:eq(1) input").prop('checked', true);
												});
									}
								},
								'sName' : 'user'

							});

					$('#firstNameParam')
							.blur(
									function() {
										var text = $('#firstNameParam').val();
										if (!checkInputLen(text, 30)) {
											$('#firstNameParam')
													.attr('title',
															'First Name must be entered and be no more than 30 characters.');
											$('#firstNameParam').addClass(
													'error');
										} else {
											$('#firstNameParam').removeClass(
													'error');
											$('#firstNameParam').removeAttr(
													'title');
										}
									});

					$('#lastNameParam')
							.blur(
									function() {
										var text = $('#lastNameParam').val();
										if (!checkInputLen(text, 30)) {
											$('#lastNameParam')
													.attr('title',
															'Last Name must be entered and be no more than 30 characters.');
											$('#lastNameParam').addClass(
													'error');
										} else {
											$('#lastNameParam').removeClass(
													'error');
											$('#lastNameParam').removeAttr(
													'title');
										}
									});

					$('#userIdParam')
							.blur(
									function() {
										var text = $('#userIdParam').val();
										if (!checkInputLen(text, 30)) {
											$('#userIdParam')
													.attr('title',
															'Username must be entered and be no more than 30 characters.');
											$('#userIdParam').addClass('error');
										} else {
											$('#userIdParam').removeClass(
													'error');
											$('#userIdParam').removeAttr(
													'title');
										}
									});

					/*
					 * At least 8 chars Contains at least one digit Contains at
					 * least one lower alpha char and one upper alpha char
					 * Contains at least one char within a set of special char
					 * (@#%$^ etc.) Not containing blank, tab etc.
					 */

					// do not need to validate length of password
					$('#passwordParam')
							.blur(
									function() {
										if ((docMode == 'edit' && chPass)
												|| docMode == 'create') {
											var text = $('#passwordParam')
													.val().trim();
											var attrVal = '';

											if (!checkInputLen(text, 30))
												attrVal += "Password must be entered.\n";
											else if (!validatePassword(text))
												attrVal += "Password must contain at least one digit, one uppercase letter, and one lowercase letter, one special character (@#%$^ etc.), and be at least 8 characters.\n";
											if (attrVal.length > 0) {
												$('#passwordParam').attr(
														'title', attrVal);
												$('#passwordParam').addClass(
														'error');
											} else {
												$('#passwordParam')
														.removeClass('error');
												$('#passwordParam').removeAttr(
														'title');
											}
										}
									});

					$('#password2Param').blur(
							function() {
								// alert('password2Param.blur called.');
								$('#password2Param').val(
										$('#password2Param').val().trim());
								if ((docMode == 'edit' && chPass)
										|| docMode == 'create') {
									// alert('Edit Mode failure');
									if ($('#password2Param').val() !== $(
											'#passwordParam').val()) {
										$('#password2Param').attr('title',
												'Passwords must match.');
										$('#password2Param').addClass('error');
									} else {
										$('#password2Param').removeClass(
												'error');
										$('#password2Param')
												.removeAttr('title');
									}
								}
							});

					$('#passwordParam').keypress(function(evt) {
						var k = evt ? evt.which : window.event.keyCode;
						if (k == 32)
							return false;
					});

					$('#password2Param').keypress(function(evt) {
						var k = evt ? evt.which : window.event.keyCode;
						if (k == 32)
							return false;
					});

					$('#emailAddressParam')
							.blur(
									function() {
										var text = $('#emailAddressParam')
												.val();
										if (!checkInputLen(text, 50)) {
											$('#emailAddressParam')
													.attr('title',
															'Email address must be entered.');
											$('#emailAddressParam').addClass(
													'error');
										} else if (!validateEmail(text)) {
											$('#emailAddressParam')
													.attr(
															'title',
															'A valid email address (ex: example@example.com) must be entered and may not exceed 50 characters.');
											$('#emailAddressParam').addClass(
													'error');
										} else {
											$('#emailAddressParam')
													.removeClass('error');
											$('#emailAddressParam').removeAttr(
													'title');
										}
									});

					$('#phoneNumberParam')
							.blur(
									function() {
										var text = $('#phoneNumberParam').val();
										if (!checkInputLen(text, 10)
												|| text.length <= 0) {
											$('#phoneNumberParam')
													.attr(
															'title',
															"Phone number is required, and should be in the format '1234567890' (10 numeric characters), with no dashes or parentheses.");
											$('#phoneNumberParam').addClass(
													'error');
										} else {
											if (!validatePhoneNumber(text)) {
												$('#phoneNumberParam')
														.attr(
																'title',
																"Phone number is required, and should be in the format '1234567890' (10 numeric characters), with no dashes or parentheses.");
												$('#phoneNumberParam')
														.addClass('error');
											} else {
												$('#phoneNumberParam')
														.removeClass('error');
												$('#phoneNumberParam')
														.removeAttr('title');
											}

										}
									});

					$("#userRoleParamSelect")
							.live('change',
									function(){
										$('#saveButton').attr("onclick","createUser('"+docMode+"')");
								    });

					/**
					 * Add hidden inputs to collect form values for User Role
					 * and User Status.
					 */
					// var roleHidden = $('<input>').attr('type',
					// "hidden").attr('id', "selectedUserRoleHidden");
					// $('#userForm').append($(roleHidden));
					// var statusHidden=
					// $('<input>').attr('type',"hidden").attr('id',"selectedUserStatusHidden");
					// $('#userForm').append($statusHidden);
					$('#changePwordBtn').click(function(e) {
						e.preventDefault();
						$('#passwordLI, #password2LI').show(1000);
						chPass = true;
					});
					// $('#createUserFormContainer').show(500);
				});

function checkboxClicked() {
	console.log("Clicked");
	// clear any previous selections
	var clinics = '';

	var rows = $(".jqueryDataTable tr:gt(0)"); // skip the header row
	
	console.log(rows);
	
	
	rows.each(function(index) {
		var clinicID = $(rows[index]).find("td:eq(0)").html();
		var checkBoxTD = $(rows[index]).find("td:eq(1)");
		var inputElements = checkBoxTD.find(':input');
		var ischecked = inputElements.is(':checked');
		
		console.log("clinicID>>>>>>> "+clinicID);
		
		if (ischecked) {
			if (clinics.length > 0) {
				clinics = clinics + ',' + clinicID;
				console.log("rows"+console.log(clinics));
			} else {
				clinics = clinicID;
				console.log("else rows"+console.log(clinics));
			}
		}
	});
	$("#clinicsParam").val(clinics);
	console.log("Clicked - Passed 2");
}

function validateForm() {
	var valid = true;

	// required fields
	var fnValid = checkInputLen($('#firstNameParam').val(), 30);
	if (!fnValid) {
		!fnValid ? $('#firstNameParam')
				.addClass('error')
				.attr('title',
						"First Name is required and must be no more than 30 characters.")
				: $('#firstNameParam').removeClass('error').removeAttr('title');
		valid = false;
	}

	var lnValid = checkInputLen($('#lastNameParam').val(), 30);
	if (!lnValid) {
		!lnValid ? $('#lastNameParam')
				.addClass('error')
				.attr('title',
						"Last Name is required and must be no more than 30 characters.")
				: $('#lastNameParam').removeClass('error').removeAttr('title');
		valid = false;
	}

	var unameValid = checkInputLen($('#userIdParam').val(), 30);
	if (!unameValid) {
		!unameValid ? $('#userIdParam').addClass('error').attr('title',
				"Username is required and must be no more than 30 characters.")
				: $('#userIdParam').removeClass('error').removeAttr('title');
		valid = false;
	}

	// TODO finish these
	if ((docMode == 'edit' && chPass) || docMode == 'create') {
		var pword1Valid = validatePword1();
		if (!pword1Valid) {
			valid = false;
		}

		var pword2Valid = validatePword2();
		if (!pword2Valid) {
			valid = false;
		}
	}

	var emailValid = validateEml();
	if (!emailValid) {
		!emailValid ? $('#emailAddressParam')
				.addClass('error')
				.attr(
						'title',
						"Email is required, must be in a email@provider.com format, and must be no more than 50 characters.")
				: $('#emailAddressParam').removeClass('error').removeAttr(
						'title');
		valid = false;
	}

	var phone1Valid = checkInputLen($('#phoneNumberParam').val(), 10);
	if (!phone1Valid) {
		!phone1Valid ? $('#phoneNumberParam')
				.addClass('error')
				.attr('title',
						"Phone number must be provided and must be no more that 10 characters.")
				: $('#phoneNumberParam').removeClass('error').removeAttr(
						'title');
		valid = false;
	}

	// non required fields
	var email2Valid = validateEml2();
	if (!email2Valid) {
		!email2Valid ? $('#emailAddress2Param')
				.addClass('error')
				.attr(
						'title',
						"Email must be in a email@provider.com format, and must be no more than 50 characters.")
				: $('#emailAddress2Param').removeClass('error').removeAttr(
						'title');
		valid = false;
	}

	// cleanup
	if (valid) {
		$('input[type="text"]').removeClass('error').removeAttr('title');
		$('input[type="password"]').removeClass('error').removeAttr('title');
	}

	return valid;
}

function validateEml() {
	var valid = true;
	var attrText = "";
	var text = $('#emailAddressParam').val();
	if (!checkInputLen(text, 50)) {
		attrText += "Email address must be entered.\n";
		valid = false;
	} else if (!validateEmail(text)) {
		attrText += "A valid email address (ex: example@example.com) must be entered.";
		valid = false;
	}

	if (attrText.length > 0) {
		$('#emailAddressParam').addClass('error');
		$('#emailAddressParam').attr('title', attrText);
	} else {
		$('#emailAddressParam').removeClass('error');
		$('#emailAddressParam').removeAttr('title');
	}
	return valid;
}

function validateEml2() {
	var valid = true;
	var attrText = "";
	var text = $('#emailAddress2Param').val();
	if (text.length == 0) {
		$('#emailAddress2Param').removeClass('error');
		$('#emailAddress2Param').removeAttr('title');
		return valid; // this is not a required parameter
	}

	if (!checkInputLen(text, 50)) {
		attrText += "Email address must not exceed 50 characters.\n";
		valid = false;
	} else if (!validateEmail(text)) {
		attrText += "A valid email address (ex: example@example.com) must be entered.";
		valid = false;
	}

	if (attrText.length > 0) {
		$('#emailAddress2Param').addClass('error');
		$('#emailAddress2Param').attr('title', attrText);
	} else {
		$('#emailAddress2Param').removeClass('error');
		$('#emailAddress2Param').removeAttr('title');
	}
	return valid;
}

function freeTextError(elem, text) {
	elem.addClass('error').attr('title', text);
}

function validatePword1() {
	var valid = true;
	var text = $('#passwordParam').val();
	var attrVal = '';

	if (!checkInputLen(text, 30)) {
		attrVal += "Password must be entered.\n";
		valid = false;
	} else if (!validatePassword(text)) {
		attrVal += "Password must contain at least one digit, one uppercase letter, and one lowercase letter, one special character "
				+ "(@#%$^ etc.), and be at least 8 characters.\n";
		valid = false;
	}
	if (attrVal.length > 0) {

		$('#passwordParam').attr('title', attrVal);
		$('#passwordParam').addClass('error');
	} else {
		$('#passwordParam').removeClass('error');
		$('#passwordParam').removeAttr('title');
	}
	return valid;
}

function validatePword2() {
	var valid = true;
	if ($('#password2Param').val() !== $('#passwordParam').val()) {
		$('#password2Param').attr('title', 'Reenter Password must be entered.');
		$('#password2Param').addClass('error');
		valid = false;
	} else {
		$('#password2Param').removeClass('error');
		$('#password2Param').removeAttr('title');
	}
	return valid;
}

function validateEmail(email) {
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(email);
}

function validatePassword(pword) {
	var re = /^.*(?=.{8,})(?!.*\s)(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@!#$%&?]).*$/;
	return re.test(pword);
}

function validatePhoneNumber(phnum) {
	// var re = /@"^\d{10}$"/;
	var re = /\d{10}/;
	return re.test(phnum);
}

function checkInputLen(text, maxLength) {
	if (text.length > 0 && text.length <= maxLength)
		return true;
	else
		return false;
}