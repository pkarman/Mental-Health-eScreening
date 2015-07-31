/**
 * measures-validations.js  - The JavaScript functional include for 
 * assessmentHome.jsp.
 * 
 * @author Ramu Karanam, Bryan Henderson, Jeewan Aryal, Robin Carnow.
 * @date 01/09/2014
 */

/**
 * Singleton object that collects the validations for each measure and/or answers during 
 * formBuilding and applies validations during requestBuilding.
 */
var surveyValidation = (function(){
	/* private fields and methods */
	
	/* look up for all validations for the current page */
	var validationLookup = {};
	
	/* map of validation functions which return an error message if the given user response does not validate with the given validation argument 
	 * validationParameter - the validation argument (e.g. the length in the exactLength validator)
	 * userResponse - a non-null string containing the user's response. If the user 
	 * */
	var validationMap = {
		"required" : function(validationParameter, userResponse){
			return userResponse.length == 0 ? "This field is required!" : "";
		},
		"dataType" : function(validationParameter, userResponse){
			var regex;
			switch(validationParameter){ 
				case 'number':
					regex = /^\d+$/;
					break;
				
				case 'email':
					regex = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
					break;
				
				case 'date':
					//regex = /^(\d{2,2})(\/)(\d{2,2})\2(\d{4}|\d{4})$/;
					regex = /^(\d{1,2})\/(\d{1,2})\/(\d{4})$/;
					
					// Validate date
					if(regs = userResponse.match(regex)) {
						// month value between 1 and 12
				        if((regs[1] < 1 || regs[1] > 12) && (regs[1].length == 2)) {
				        	console.log("Invalid value for month: " + regs[1].length);
				        	return  "Must be a valid Month!";
				          return false;
				        }
				        
						// day value between 1 and 31
				        if((regs[2] < 1 || regs[2] > 31)  && (regs[2].length == 2)) {
				          console.log("Invalid value for day: " + regs[2]);
				          	return  "Must be a valid Day!";
				          return false;
				        }

				        // year value between 1902 and 2014
				        
				        //if(regs[3] < 1902 || regs[3] > (new Date()).getFullYear()) {
				        //  console.log("Invalid value for year: " + regs[3] + " - must be between 1902 and " + (new Date()).getFullYear());
				        //  return  "Must be a valid " + validationParameter + "!";
				        //  return false;
				        //}
				        
				      }
					break;
				default:
					return "Error: invalid field type from server: " + validationParameter;		
			}
			
			return userResponse.length > 0 && !regex.test(userResponse) ? "Must be a valid " + validationParameter + "!" : "";
		},
		"exactLength" : function(validationParameter, userResponse){
			return userResponse.length > 0 && userResponse.length != parseInt(validationParameter, 10) ? "Length must be " + validationParameter + " characters!" : "";
		},
		"maxLength"	: function(validationParameter, userResponse){
			return userResponse.length > 0 && userResponse.length > parseInt(validationParameter, 10) ? "Length must not exceed " + validationParameter + " characters!" : "";
			
		},
		"minLength"	: function(validationParameter, userResponse){
			return userResponse.length > 0 && userResponse.length < parseInt(validationParameter, 10) ? "Length must be at least "+ validationParameter +" characters!" : "";
		},
		"minValue"	: function(validationParameter, userResponse){
			if(userResponse.length == 0){
				return "";
			}
			return applyNumericValidation(validationParameter, userResponse,
					function(userNum, validationNum){
						return userNum < validationNum ? "Value must be at least " + validationNum + "!" : "";
					}
			);
		},
		"maxValue"	: function(validationParameter, userResponse){
			if(userResponse.length == 0){
				return "";
			}
			return applyNumericValidation(validationParameter, userResponse,
					function(userNum, validationNum){
						return userNum > validationNum ? "Value must be less than " + (validationNum+1) + "!" : "";
					}
			);
		},
		"dateRange" : function(validationParameter, userResponse){ // There is no backend code to support dateRange validation so far. We need to think about that
			var selectedDate = new Date(userResponse), todayDate = new Date(), validateDate = selectedDate > todayDate.setFullYear(todayDate.getFullYear()-150);			
			return validateDate == false ? "Invalid date range" : ""; 
		}
	};

	/* Used when a validation requires the user response be a number.  
	 * This function will apply the number validation and if it passes, the childNumValidation function will be run with its error value returned.
	 * If the number validation has an error, then the child validation will not be run.
	 * Signature of the childNumValidation function is: 
	 * 		String childNumValidation(userNum, validationNum)
	 * where userNum is the numeric user response and validationNum is the validation parameter in integer form.
	 */
	function applyNumericValidation(validationParameter, userResponse, childNumValidation){
		//this is a child of the number validation so we have to run that first
		var error = validationMap['dataType']('number', userResponse);
		if(error.length > 0 || userResponse.length == 0 || validationParameter.length == 0)
			return error;
		
		var userNum = parseInt(userResponse, 10);
		var validationNum = parseInt(validationParameter, 10);
		
		return childNumValidation(userNum, validationNum);
	}
	
	/*
	 * Applies a list of validations to a jQuery collection of elements
	 */
	function applyValidations(elements, validations, skipEmpty){
		var hasError = false;
		if(validations != null && validations.length > 0){
			
			elements.each(function(){
				if(!isVisible($(this))){
					return;
				}
				
				var inputContainer = $(this).parent();
				var inputElementVal = $(this).val();
				if(inputElementVal == null)
					inputElementVal = "";
				else{//remove white space
					inputElementVal = inputElementVal.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
				}
				
				if(skipEmpty && inputElementVal == ""){
					return;
				}
				
				//clear any previous error
				inputContainer.find("span.validationErrorMessage").remove();
				
				//apply each validation (stopping on first error)
				for(var i = 0; i < validations.length; i++) {
					var validatorName = validations[i].name;
					var validatorParameter = validations[i].value;
					var validation = validationMap[validatorName];
					if(validation != null){
						var validationError = validation(validatorParameter, inputElementVal);
						if(validationError.length > 0){
							
							//console.info("Adding validation error with message: " + validationError);
							//inputContainer.children().css("background-color", "red");
							$('html, body').animate({
		                        scrollTop: inputContainer.children('input[type=text]').offset().top-50
		                    }, 400);
							inputContainer.children('input[type=text]').css("border", "1px solid red");
							hasError = true;
							inputContainer.append('<span class="validationErrorMessage">' + validationError + '</span>');
							//If we ever change free text fields to display in a column instead of next to each other, we might change this to just a break and put each input into its own div
							return false;
						}
						
						else {
							inputContainer.children('input[type=text]').css("border", "1px solid #abadb3");
						}
					}
					else{
						hasError = true;
						// show error
						displayServerError("Invalid validation received from server: " + validatorName);
						break;
					}
				}
			});
		}
		return hasError;
	}
	
	function getMeasureValidations(measureId){
		return validationLookup["measure" + measureId];
	}
	
	function getAnswerValidations(answerId){
		return validationLookup["answer" + answerId];
	}
	
	//returns true if the single select component is checked
	function isSingleSelectChecked(checkLI){
		return checkLI.find("input[type='radio']").prop("checked");
	}
	
	//returns true if the multi select component is checked
	function isMultiSelectChecked(checkLI){
		return checkLI.find("div.checkSwitch").hasClass("on");
	}
	
	function enableWarning(component, enable){
		component.find("span.skipQuestion").remove();
		if(enable){
			component.append("<span class='skipQuestion'><img src='resources/images/warning-triangle.png' alt='Please answer all questions before proceeding.'></span>");
		}
	}
	
	function enableError(component, enable){
		component.find("span.skipQuestion").remove();
		if(enable){
			component.append("<span class='skipQuestion'><img src='resources/images/error.png' alt='You must answer this question.'></span>");
		}
	}
	
	function detectFreeTextField(skipId, LI) {
		skipId.on("keyup", function() {
			LI.find("span.skipQuestion").remove();
			if($(this).val()!="") {
				LI.find("span.skipQuestion").remove();
			}
			else if($(this).val()=="") {
				if(skipId.hasClass("isRequired")) {
					enableError(LI.find("label.freeTextQuestion"), true);
				}
				else {
					enableWarning(LI.find("label.freeTextQuestion"), true);
				}
			}
	    });
	}
	
	function isVisible(element){
		if(element.hasClass("surveyQuestion")){
			return ! element.hasClass("qInvisible");
		}
		return ! $(element).parents("li.surveyQuestion").hasClass("qInvisible");
	}
	
	return { // public interface
		
		/**
		 * On each page load reset should be called
		 */
		reset : function(){
			validationCollection = {};
		},
		
		/**
		 * Add the validations for a measure in form
		 */
		addMeasureValidations : function (measureId, validations){
			validationLookup["measure" + measureId] = validations;
		},

		/**
		 * Add the validations for an answer in form
		 */
		addAnswerValidations : function(answerId, validations){
			validationLookup["answer" + answerId] = validations;
		},
		
		/**
		 * Applies validations to all assessment form fields in the current page.
		 */
		pageIsValid : function(skipEmpty){
			var hasError = false;
			
			//validate free text questions
			$("li.surveyQuestion[data=freeText]").each(function(){
				var measureId = $(this).attr("measureId");
				if(measureId != undefined && measureId != null && isVisible($(this))){
					//apply validations for measure
					var validations = getMeasureValidations(measureId);
					var elements = $(this).find("input[type=text]");
					hasError = applyValidations(elements, validations, skipEmpty) || hasError;
				}
			});
			
			//validate any table questions
			$("li.surveyQuestion[data=tableQuestion] input[type=text]").each(function(){
				var answerId = $(this).attr("answerId"); 
				if(answerId != undefined && answerId != null){
					var validations = getAnswerValidations(answerId);
					hasError = applyValidations($(this), validations, skipEmpty) || hasError;
				}
			});
			return !hasError;
		},
		
		// checking for free text skip question 
		freeTextSkipped: function() {
			var skipped = {"isRequired": false, "isSkipped": false};
			$("li.surveyQuestion[data=freeText]").each(function(){
				var LI = $(this);
				
				if(!isVisible(LI)){
					return;
				}
				
				LI.find("span.skipQuestion").remove();
				$(this).find("input[type=text]").each(function(){
					var skipId = $(this);
					if(skipId.val() =="") {
						var isRequired =skipId.hasClass("isRequired");
						if(isRequired){
							LI.find("label.freeTextQuestion").append("<span class='skipQuestion'><img src='resources/images/error.png' alt='You must answer this question.'></span>");
						}
						else{
							LI.find("label.freeTextQuestion").append("<span class='skipQuestion'><img src='resources/images/warning-triangle.png' alt='Please answer all questions before proceeding.'></span>");
						}
						skipped.isSkipped = true;
						skipped.isRequired = skipped.isRequired || isRequired; 
					}
					else {
						LI.find("span.skipQuestion").remove();
					}
					detectFreeTextField(skipId, LI);			
				});
			});
			
			return skipped;
		},
		
		// checking for multi-select skip question 
		selectMultiSkipped: function() {
			var skipped = {"isRequired": false, "isSkipped": false};
			var pArray = new Array();
			var selectMultiArray = new Array();
			
			$("li.surveyQuestion[data=selectMulti]").each(function(k, v){
				var LI = $(this);
				
				if(!isVisible(LI)){
					return;
				}
				
				selectMultiArray = [];
				LI.find("div.surveyQuestionText span").remove();
					LI.children("ul").each(function(){
						LI.find("div.surveyQuestionText span").remove();
						pArray = new Array();
						$(this).children("li").each(function(j, k) {
							if($(this).children().find("div").hasClass("checkSwitch on")) {
								pArray.push("checkOn");
								if($(this).find("input.selectOther").attr("id") != undefined) {
									if($(this).find("input.selectOther").val()=="") {
										skipped.isRequired = true;
										$(this).find("div.selectOther").parent().append("<span class='validationErrorMessage otherFieldErrorMessage'>* Please enter text</span>");
									}
								}
							}
							else {
								pArray.push("checkOff");
							}
						});
						selectMultiArray.push(pArray);
				    });
					
					for(var ms=0; ms<selectMultiArray.length; ms++) {
						if($.inArray("checkOn", selectMultiArray[ms])==-1){ // not found
							if(LI.hasClass("isRequired")){
								skipped.isRequired = true;
								LI.find("div.surveyQuestionText").append("<span class='skipQuestion'><img src='resources/images/error.png' alt='You must answer this question.'></span>");
								
								$(this).on("click", function() {
									if($(this).find("input").is(':checked')) {
										LI.find("div.surveyQuestionText span").remove();
									}
									else if($(this).find("input").is(':checked') == false) {
										LI.find("div.surveyQuestionText").append("<span class='skipQuestion'><img src='resources/images/error.png' alt='You must answer this question.'></span>");
									}
								});
							}
							else {
								skipped.isSkipped = true;
								LI.find("div.surveyQuestionText").append("<span class='skipQuestion'><img src='resources/images/warning-triangle.png' alt='Please answer all questions before proceeding.'></span>");

								$(this).on("click", function() {
									LI.find("div.surveyQuestionText span").remove();
									if($(this).find("input").is(':checked')) {
										LI.find("div.surveyQuestionText span").remove();
									}
									else if($(this).find("input").is(':checked') == false) {
										LI.find("div.surveyQuestionText").append("<span class='skipQuestion'><img src='resources/images/warning-triangle.png' alt='Please answer all questions before proceeding.'></span>");
									}
								});
							}
						}
					}
			});
			
			return skipped;
		},
		
		// checking for multi one skip question
		selectOneSkipped: function() {
			var skipped = {"isRequired": false, "isSkipped": false};
			var pArray = new Array();
			var selectOneArray = new Array();
			
			$("li.surveyQuestion[data=selectOne]").each(function(k, v){
				var LI = $(this);
				
				if(!isVisible(LI)){
					return;
				}
				
				selectOneArray = [];
				LI.find("div.surveyQuestionText span").remove();
					LI.children("ul").each(function(){
						pArray = new Array();
						$(this).children("li").each(function(j, k) {
							if($(this).children().find("input").is(':checked')) {
								pArray.push("checkOneOn");
								var other = $(this).find("input.selectOther");
								if(other.attr("id") != undefined && other.val()=="") {
									skipped.isRequired = true;
									$(this).find("div.selectOther").parent().append("<span class='validationErrorMessage otherFieldErrorMessage'>* Please enter text</span>");
								
								}
							}
							else {
								pArray.push("checkOneOff");
							}
							
							$(this).children().find("input").on("click", function() {
								LI.find("div.surveyQuestionText span").remove();
							});
							
						});
						
						selectOneArray.push(pArray);
						
				    });
					for(var ms=0; ms<selectOneArray.length; ms++) {
						if($.inArray("checkOneOn", selectOneArray[ms])==-1){ // not found
							if(LI.hasClass("isRequired")){
								skipped.isRequired = true;
								//LI.find("div.surveyQuestionText").append("<span class='skipQuestion'><img src='resources/images/error.png' alt='You must answer this question.'></span>");
								enableError(LI.find("div.surveyQuestionText"), true);
							}
							else {
								skipped.isSkipped = true;
								//LI.find("div.surveyQuestionText").append("<span class='skipQuestion'><img src='resources/images/warning-triangle.png' alt='Please answer all questions before proceeding.'></span>");
								enableWarning(LI.find("div.surveyQuestionText"), true);
							}
						}
					}
			});
			return skipped;
		},
		
		tableQuestionSkipped: function() {
			var skipped = {"isRequired": false, "isSkipped": false};
			$("li.surveyQuestion[data=tableQuestion]").each(function(k, v){
				var LI = $(this);
				
				if(!isVisible(LI)){
					return;
				}
				
				LI.children("div.surveyQuestionText span").remove();
				LI.find(".tableQuestionEntryContainer").each(function(k1, v1){
					if($(this).children().length==0) {
						if(LI.hasClass("isRequired")){
							skipped.isRequired = true;
							LI.find("div.surveyQuestionText").append("<span class='skipQuestion'><img src='resources/images/error.png' alt='You must answer this question.'></span>");
						}
						else {
							skipped.isSkipped = true;
							LI.find("div.surveyQuestionText").append("<span class='skipQuestion'><img src='resources/images/warning-triangle.png' alt='Please answer all questions before proceeding.'></span>");
						}
						
					}
				});
			});
			return skipped;
		},
		
		selectDropdownSkipped: function() {
			var skipped = {"isRequired": false, "isSkipped": false};
			var pArray = new Array();
			var selectDropdownArray = new Array();
			
			$("li.surveyQuestion[data=selectDropdown]").each(function(k, v){
				var LI = $(this);
				
				if(!isVisible(LI)){
					return;
				}
				
				selectDropdownArray = [];
				pArray = [];
				LI.find("div.surveyQuestionText span").remove();
				pArray.push(LI.find("select option:selected").val());
				selectDropdownArray.push(pArray);
				
				for(var dd=0; dd<selectDropdownArray.length; dd++) {
					if($.inArray("", selectDropdownArray[dd])!=-1){
						if(LI.hasClass("isRequired")){
							skipped.isRequired = true;
							LI.find("div.surveyQuestionText").append("<span class='skipQuestion'><img src='resources/images/error.png' alt='You must answer this question.'></span>");
						}
						else {
							skipped.isSkipped = true;
							LI.find("div.surveyQuestionText").append("<span class='skipQuestion'><img src='resources/images/warning-triangle.png' alt='Please answer all questions before proceeding.'></span>");
						}
					}
				}
				
				if(LI.find("input.dropdownOther").val()!=undefined) {
					LI.find("span.validationErrorMessage").remove();
					if(LI.find("input.dropdownOther").val()=="") {
						$(this).append("<span class='validationErrorMessage otherFieldErrorMessage'>* This field can not be empty.</span>");
						skipped.isRequired = true;
					}
				}
				
			});
			return skipped;
		},
		
		matrixQuestionSkipped: function() {
			var skipped = {"isRequired": false, "isSkipped": false};

			$("tr.matrixQuestion").each(function(){
				var row = $(this);
				
				if(!isVisible(row)){
					return;
				}
				
				var isRequired = row.hasClass("isRequired");
				var component = row.find("td.matrixQuestionText");
				var foundChecked = false;
				
				row.find("li.selectOneList").each(function(){
					var singleSelect = $(this);
					foundChecked = foundChecked || isSingleSelectChecked(singleSelect);
				});
				
				row.find("li.selectMultiCheckbox").each(function(){
					var multiSelect = $(this);
					foundChecked = foundChecked || isMultiSelectChecked(multiSelect);
				});
				
				if(isRequired){
					//add error
					skipped.isRequired = skipped.isRequired || !foundChecked;
					enableError(component, !foundChecked);
				}
				else{
					skipped.isSkipped = skipped.isSkipped || !foundChecked;
					enableWarning(component, !foundChecked);
				}

				row.find("li.selectOneList").on("click", function(){
				   if($(this).find("input").is(":checked")) {
					   $(this).parent().parent().find("span.skipQuestion").remove();
				   }
				});
				
				row.find("li.selectMultiCheckbox").on("click", function(){
					if($(this).find("input").is(":checked")) {
						   $(this).parent().parent().find("span.skipQuestion").remove();
					   }
					else if($(this).find("input").is(":checked")==false){
						if(isRequired){
							enableError(component, true);
						}
						if(!isRequired){
							enableWarning(row.find("td.matrixQuestionText"), true);
						}
					}
				});
			
			});
			return skipped;
		}
	};
	
})();


function pageSkipped() {
	
	var skipped = {"isRequired": false, "isSkipped": false};
	mergeSkipped(skipped, surveyValidation.freeTextSkipped());
	mergeSkipped(skipped, surveyValidation.selectMultiSkipped());
	mergeSkipped(skipped, surveyValidation.selectOneSkipped());
	mergeSkipped(skipped, surveyValidation.tableQuestionSkipped());
	mergeSkipped(skipped, surveyValidation.selectDropdownSkipped());
	mergeSkipped(skipped, surveyValidation.matrixQuestionSkipped());
	
	return skipped;
}

function mergeSkipped(oldSkipped, newSkipped){
	oldSkipped.isRequired = oldSkipped.isRequired || newSkipped.isRequired;
	oldSkipped.isSkipped = oldSkipped.isSkipped || newSkipped.isSkipped;
}


// This code keeps the position the dialog box at the center of the browser when resized
$(window).resize(function(){
    $(".ui-dialog-content").dialog("option","position","center");
});


// method to show message when hovering the warning/error icons
function tooltipOnHover() {
	$('.skipQuestion').hover(function(){
	        var textToShow = $(this).find("img").attr('alt');
	        $('<p class="tooltip"></p>')
	        .text(textToShow)
	        .appendTo('body')
	        .fadeIn('fast');
	}, function() {
	        $('.tooltip').remove();
	}).mousemove(function(e) {
	        $('.tooltip')
	        .css({ 
	        	top: e.pageY - 50, 
	        	left: e.pageX + 20 
	        });
	});
}

//TODO: any use of the patter parent().parent().find() should be changed to use jQuery's parents() method
// This method is being called from the "measures-integrated.js" file
// enabling / disabling the "other" input text depending on its check switch's ON/OFF state
// it also disable and clear the input field if there is a "None" check switch is clicked and set to its "ON" state
function validateSingleAndMultiOtherInput() {
	$("li.surveyQuestion[data=selectMulti]").each(function(k, v){
		$(this).find("ul").each(function(kk, vv){
			$(this).find("li[data=selectMultiInt-Other]").each(function(){
				if($(this).find("input.selectOther[type=text]").attr("id")!= undefined) {
					if($(this).find("input.selectOther[type=text]").val()=="") {
						$(this).find("input.selectOther[type=text]").attr("disabled", "disabled");
					}
				}
				
				$(this).find("div.checkSwitch").click(function(){
					if($(this).find("input[type=checkbox]").is(':checked')) {
						$(this).parent().parent().find("input.selectOther[type=text]").attr("disabled", false);
					}
					else {
						$(this).parent().parent().find("input.selectOther[type=text]").val("").attr("disabled", true);
					}
				});
				
				$(this).parent().find("li.hasNone").each(function(){
					$(this).find("div.checkSwitch").click(function(){
						if($(this).find("input[type=checkbox]").is(':checked')) {
							$(this).parent().parent().parent().find("li input.selectOther[type=text]").val("").attr("disabled", true);
						}
					});
				});
				
			});
		});
	});
	
	$("li.surveyQuestion[data=selectOne]").each(function(k, v){
		$(this).find("ul li.selectOther").each(function(k3, v3){
			if($(this).find("input[type=text]").val()=="") {
				$(this).find("input[type=text]").attr("disabled", true);
			}
			$(this).find("input[type=radio]").click(function() {
				if($(this).prop("checked", true)) {
					$(this).parent().parent().find("input[type=text]").attr("disabled", false);
				}
			});
		});
		
		$(this).find("ul li").not(".selectOther").each(function(k3, v3){
			$(this).find("input[type=radio]").click(function() {
				$(this).parent().parent().parent().find("li[data=selectOneInt]").find("input[type=text]").val("").attr("disabled", true);
			});
		});
	});
}








