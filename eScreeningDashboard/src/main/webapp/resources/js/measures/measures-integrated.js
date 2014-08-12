/**
	measures-integration.js - Navigation template file for transiting an eScreening Measures assessment.
	Governs button actions and page-queueing.
	
	This module relies on measures-formbuilder.js, and surveyValidation from measures-validations.js
	
	@author Bryan Henderson, Jeewan Aryal, Ramu Karanam, Robin Carnow
	@date   1/4/2014
*/

/** -- Global variables -- **/

// Status of current returned service call.
var currPageJSON = {};

// URL for Measures
var measuresURL 	= "services/assessments/active";
var errorCallAPI 	= "<div class='erorrMessage'>Error, Please contact support</div>";
var errorDelay 		= " <i class='ico_loading'></i>Generating Questions, Please Wait...";
var errorAPI 		= false;

//TODO: All timer stuff should be moved to a class
var timerValue = 0;
var logoutTimerValue = 0;
var timer;
var autoLogoutTimer;
var autoLogoutCounter = 10;

var autoLogoutTimeset = 420; // Setting 7 minutes time to log out the user automatically (420 = 7 * 60)

var formBuilder = new FormBuilder(surveyValidation, checkQuestionVis);

/** -- JQuery loading. -- **/
$(document).ready(function(e) {
	$(".buttonSignout").hide();
	$("#center").css({
		"width" : $("#headerDiv").width()-$("#left").width()-40
	});
	
	$( window ).resize(function() {
		$("#center").css({
			"width" : $("#headerDiv").width()-$("#left").width()-40
		});
	});
	
	// Hide the 'Back' button.
	$('#backBtn').css('visibility', 'hidden');
	// Set up the initial service call.
	callMeasure(measuresURL, JSON.stringify({}), initialPageCallback);

	// Button actions
	$('#saveExitBtn').click(function(){
		callMeasure(measuresURL, 
			buildReqAdvBack('previous', buildJSONFromForm('next')),
			logout);
	});
	
    $('#nextBtn').click(function(){
    	
    	if (surveyValidation.pageIsValid()){
			var request = buildReqAdvBack('next', buildJSONFromForm('next'));
			
			var skipped = pageSkipped();
			if(skipped.isRequired){
				openRequiredDialog();
				tooltipOnHover();
			}
			else if(skipped.isSkipped){
					openSkipDialog(measuresURL, request, initialPageCallback);
					tooltipOnHover();
			}
			else {
				callMeasure(measuresURL, request, initialPageCallback);
			}
    	}
	});
    
    $("#measuresProgress li label").parent().parent().click(function() {
    	if (surveyValidation.pageIsValid()){
    		var sectionId = $(this).find("label").attr("for").match(/[0-9]+/g)[0];
			callMeasure(measuresURL,
				buildReqAdvBack('nextSkipped', buildJSONFromForm('nextSkipped'), sectionId),
				initialPageCallback);
    	}
    });
    
    
    
    
    /* Matrix Table - In case of TD empty remove the parent TH */
    $("#assessmentContainer").bind("DOMSubtreeModified", function() {
		$( ".matrixQuestionText" ).each(function( index ) {
			  var matrixQuestionText = $("#assessmentContainer").find(".matrixQuestionText").html();
				 if (matrixQuestionText == '') {
					$(this).addClass("empty");
					$(this).closest('table').find('th').eq(0).addClass("empty");
				}	  
		});
	});
    
});

function logout(reason){
	var reasonQuery = "";
	if(reason != null && typeof reason == "string" && reason != ""){
		reasonQuery += "?reason=" + reason;
	}
	document.location.href = "handleLogoutRequest.html" + reasonQuery;
}

function showError() {
   $('.top_header_message').slideDown(300).show(0);
   if(errorAPI != true){
	   $(".top_header").html(errorDelay);
   }
}


function hideError() {
	$('.top_header_message').slideUp(300).hide(0);
}

//TODO: this should be done using sequence numbers instead of one boolean
var openRequests = 0;
function checkQuestionVis(){

	// Show notification message in the top of the page - delay for 5 second to show 
	setTimeout(function(){
		if(openRequests > 0){
			showError();
		}
	}, 6000);
	
	var request = buildReqAdvBack('next', buildJSONFromForm('next'));

	openRequests++;
	asyncPost("services/assessments/visibility", request, 
		function(response){
			openRequests--;
			if(response != null && typeof response == 'object'){
				$("li.surveyQuestion").each(function(){
					var question = $(this);
					var qAttr = question.attr('id');
					
					if(qAttr != null){
						var measId = qAttr.substring(3);
						var visibile = response[measId];
						
						if(visibile == null){
							hideError();
							return;							
						}						
					
						if(!visibile){
							question.slideUp(400,
								function(){question.addClass("qInvisible");});
								hideError();
							return;
						}
						//make sure question is visible
						if(question.hasClass("qInvisible")){
							question.hide()
								.removeClass("qInvisible")
								.slideDown(400);
								hideError();
						}
					}
				});
			}
			hideError();
	});
	
}

function openRequiredDialog() {
	$("#dialogRequired").dialog({
		width: 400,
		modal: true,
		draggable: false,
		buttons: {
          "Ok": function () {
        	  $(this).dialog('close');
          }
        },
        open: function (e, ui) {
            $(this).parent().find(".ui-dialog-buttonpane .ui-button")
                .addClass("customButtonsDialog");
        }
	});
}

function openSkipDialog(url, requestJSON, initialPageCallback) {
	$("#dialogSkip").dialog({
		width: 500,
		modal: true,
		draggable: false,
		buttons: {
          "Yes, proceed to next page": function () {
        	  callMeasure(url, requestJSON, initialPageCallback);
        	  $(this).dialog('close');
            
          },
          "No, stay on current page": function () {
        	  $(this).dialog('close');
          }
        },
        open: function (e, ui) {
            $(this).parent().find(".ui-dialog-buttonpane .ui-button")
                .addClass("customButtonsDialog");
        }
	});
}

/**
 * 
 * @param navigation where to go
 * @param userData the form data to save 
 * @param targetSectionId optional survey section ID if the user would like to navigate to 
 * the page containing the first skipped question of the given target survey section
 * @returns
 */
function buildReqAdvBack(navigation, userData, targetSectionId){
	var reqData = {
			"pageId":currPageJSON.assessment.pageId, 
			"navigation":navigation, 
			"userAnswers":userData
		};
	
	if(targetSectionId != null){
		reqData.targetSection = targetSectionId;
	}
		
	return JSON.stringify(reqData);
}
	
// Ajax Call.  Generic.
function callMeasure(url, requestJSON, callbackSuccess)
{
	// Use post() shorthand method.
	$.ajax({
	  type: "POST",
	  url: url,
      dataType: 'json',
      contentType: "application/json; charset=utf-8",
	  async:false,
	  data: requestJSON,
	  success: callbackSuccess,
	  error: handleServerError,
	});
}


function handleServerErrorMessage(data, errorThrown) {
	showError();
	errorAPI = true;
	$(".top_header").html(errorCallAPI).addClass("errorMessage");
}

function handleServerError(data, errorThrown) {
	var message = "";
	if(data.responseJSON != null){
		//session timed out
		if (data.responseJSON.status == 401) {
			logout();
			return;
		}

		$.each(data.responseJSON.errorMessages, function(i, error){
			if(error.description != null){
				message += error.description + "<br/>";
			}
			else{
				message += error + "<br/>";
			}
		});
	}
	else if(data.responseText != null){
		message = data.responseText;
	}

	if(message == ""){
		message = "Unable to connect.<br/>Please see support staff for assistance.";
	}
	displayServerError(message);
}

function callMeasureAutoSave(url, requestJSON, callbackSuccess, logoutOnError)
{
	// Use post() shorthand method.
	$.ajax({
	  type: "POST",
	  url: url,
      dataType: 'json',
      contentType: "application/json; charset=utf-8",
	  async:true,
	  data: requestJSON,
	  success: callbackSuccess,
	  error: function(data, errorThrown) {
		  if(logoutOnError){
			  logout("timeout");
		  }
	  }
	
	});
}

function asyncPost(url, requestJSON, callbackSuccess){
	$.ajax({
	  type: "POST",
	  url: url,
      dataType: 'json',
      contentType: "application/json; charset=utf-8",
	  async:true,
	  timeout: 15000,
	  data: requestJSON,
	  success: callbackSuccess,
	  error: handleServerErrorMessage,
	});
}


function initialPageCallback(response){
	consumeResponse(response);
	$("#savedTime").html(getSavedTimeStamp(response));
	setTimer(response);
}

function setTimer() {
	clearInterval(timer);
	timerValue = 0;
	logoutTimerValue = 0;
	timer=setInterval(function(){
		callbackTimer();
		}, 1000);
}

function callbackTimer() {
	
	// the timerValue should be set to 5*60 = 300 for 5 minutes time frame, 
	// For testing purpose, its value can be set about 10 
	// make sure to put this value < logoutTimerValue for better testing
	if(timerValue <= 300) {  
		timerValue++;
		logoutTimerValue++;
	}
	else {
		autoSaveData();
		timerValue = 0;
	}
	
	
	// If logoutTimerValue becomes larger than autoLogoutTimeset then the session has to be expired
	if(logoutTimerValue>=autoLogoutTimeset) {
		logoutTimerValue = 0;
		showLogoutCounter(logoutTimerValue);
	}
}

function autoSaveData(callback, logoutOnError) {
	callMeasureAutoSave(measuresURL, buildReqAdvBack('next', 
		buildJSONFromForm('next')), 
		function(response){
			$("#savedTime").html(getSavedTimeStamp(response));
			if(callback != null){
				callback(response);
			}
		}, logoutOnError);
	
}

function getSavedTimeStamp(response) {
	var dateModifiedJson = response.dateModified;
	if(dateModifiedJson == 0) {
		$("#savedTime").html("");
		return;
	}
	
	else if(dateModifiedJson > 0) {
		var d = new Date(dateModifiedJson * 1000),
	    minutes = d.getMinutes().toString().length == 1 ? '0'+d.getMinutes() : d.getMinutes(),
	    hours = d.getHours().toString().length == 1 ? '0'+d.getHours() : d.getHours(),
	    months = ['January','February','March','April','May','June','July','August','September','October','November','December'];
	    return "Last save was: " + hours+':'+minutes + " on " +months[d.getMonth()] +' '+d.getDate()+ ','+ ' '+d.getFullYear();
	}
}

function showLogoutCounter(logoutTimerValue) {
	autoLogoutCounter = 20;
	clearInterval(autoLogoutTimer);
	$("#counter").html("20");
	
	// add message with dynamic minute calculation 
	$("#sessionExpireMessageLabel")
		.html("You have been inactive for more than " 
				+ (autoLogoutTimeset/60) 
				+ " minutes.<br/>For security purposes you will be logged out in");
	
	//show dialog
	$("#dialogLogoutTimer").dialog({
		width: 500,
		modal: true,
		draggable: false,
		closeOnEscape: false,
		buttons: {
          "Logout": function () {
        	  clearInterval(timer);
        	  $(this).dialog('close');
        	  autoSaveData(logout, true);
          },
          "Continue to the survey": function () {
        	  clearInterval(autoLogoutTimer);
        	  setTimer();
        	  $(this).dialog('close');
          }
        },
        open: function(event, ui) { 
            $(this).parent().children().children('.ui-dialog-titlebar-close').hide();
        	autoLogoutTimer = setInterval(function () {
        		$("#counter").html(autoLogoutCounter--);
        		if(autoLogoutCounter<0){
        			// call the save method
        			
        			console.log("HERE: "+autoLogoutCounter);
        			autoSaveData(function(){logout("timeout");}, true);
        			autoLogoutCounter = 20;
        		}
        	}, 1000);
        	$(this).parent().find(".ui-dialog-buttonpane .ui-button")
            .addClass("customButtonsDialog");
        	
        }
	});
	clearInterval(timer);
}

// Handle response data.
function consumeResponse(json){
	if (json.status == 200){
		currPageJSON = json;		
		buildFormFromJSON(currPageJSON);
	}else{
		alert("Service call failed.");
	}
}


/**
 * Translates survey progress into section progress
 * @param surveyProgresses the SurveyProgress for each survey
 * @return associative array mapping from ("progress_" + sectionId) to progress object containing count keys: resp and total
 */
function calculateSectionProgress(surveyProgresses){
	var sectionProgresses = {};
	$.each(surveyProgresses, function(i, surveyProgress){
		var progress = sectionProgresses["progress_" + surveyProgress.sectionId];
		if(progress == null){
			sectionProgresses["progress_" + surveyProgress.sectionId] = {'resp':surveyProgress.totalResponseCount,
													 			 'total':surveyProgress.totalQuestionCount};			
		}
		else{
			progress.resp += surveyProgress.totalResponseCount;
			progress.total += surveyProgress.totalQuestionCount;
		}
	});
	
	return sectionProgresses;
}
	

//Form Builder Methods.
function buildFormFromJSON(json){
	// Show/Hide 'Back' button.
	if(json.assessment.isFirstPage){
		$('#backBtn').css('visibility', 'hidden')
	}
	else{
		$('#backBtn').css('visibility', 'visible');
	}

	if(json.assessment.isComplete){
		$('#nextBtn').css('visibility', 'hidden');
		$('#saveExitBtn').css('visibility', 'hidden');
	}
	else{
		$('#nextBtn').css('visibility', 'visible');
		$('#saveExitBtn').css('visibility', 'visible');
	}
	
	$("html, body").animate({ scrollTop: 0 }, "fast");
	// Build the Progress Bars.
	// The matched page title will be wrapped with the triangle
	
	//move progress arrow
	$("#measuresProgress li label").each(function(index) {
		$(".progressEntry").removeClass("triangleWrapper");
	});

	$("#progress_"+ currPageJSON.assessment.currentSurveySection).parents(".progressEntry").addClass("triangleWrapper");
	
	$.each(calculateSectionProgress(currPageJSON.surveyProgresses), function(sectionId, progress){
		var per = getPercentValue(progress.resp, progress.total);
		
		$("#" + sectionId)
			.progressbar({ value: per })
			.children('.ui-progressbar-value')
			.html(per + '%')
			.css({
				'display': 'block',
				'background-color': '-moz-linear-gradient(top, #b8d779 0%, #b8d779 50%, #c5ea83 51%, #c5ea83 100%)',
				'background-color': '-webkit-gradient(linear, left top, left bottom, color-stop(0%,#b8d779), color-stop(50%,#b8d779), color-stop(51%,#c5ea83), color-stop(100%,#7dbc5ea83e8))',
				'background-color': '-webkit-linear-gradient(top, #b8d779 0%,#b8d779 50%,#c5ea83 51%,#c5ea83 100%)',
				'background-color': '-o-linear-gradient(top, #b8d779 0%,#b8d779 50%,#c5ea83 51%,#c5ea83 100%)',
				'background-color': '-ms-linear-gradient(top, #b8d779 0%,#b8d779 50%,#c5ea83 51%,#c5ea83 100%)',
				'background-color': 'linear-gradient(to bottom, #b8d779 0%,#b8d779 50%,#c5ea83 51%,#c5ea83 100%)',
				'background' : '-webkit-linear-gradient(#B8D779, #B8D779)',
			    'color': '#000000',
			    'font-weight': 'bold'
			});
	});

	// Remove previous form.
	$('#assessmentContainer').empty();
	
	if(json.assessment.isComplete){
		$('#viewTitle').text("Completion Screen"); // This is a dummy text for now
		document.title = "Completion Screen";		 // This is also a dummy text for now
		var strForm = createSurveyCompleForm(json);
		$('#assessmentContainer').append(strForm);
		surveyCompleteFormActions(json);
		
		$('#backBtn').off()
			.on("click", reshowBackButtonAction);
	}
	else{
		// Set the Form Title.
		$('#viewTitle').text(json.page.pageTitle);
		
		// Setting the title of the page
		document.title = json.page.pageTitle;
		
		// Use measures-formbuilder.js to create new form and attached it to page
		formBuilder.createForm(json.page.measures)
			.appendTo($('#assessmentContainer'));
		
		//fix back button event
		$('#backBtn')
			.off()
			.on("click", normalBackButtonAction);
		
		validateSingleAndMultiOtherInput();
		
	}
}

function normalBackButtonAction(){
	if(surveyValidation.pageIsValid(true)){
		callMeasure(measuresURL, 
			buildReqAdvBack('previous', buildJSONFromForm('previous')),
			initialPageCallback);
	}
}

function reshowBackButtonAction(){
	currPageJSON.assessment.isComplete = false;
	buildFormFromJSON(currPageJSON);
}

function getPercentValue(totalResponseCount, totalQuestionCount) {
	if(totalQuestionCount != 0){
		return Math.ceil((totalResponseCount / totalQuestionCount) * 100);
	}
	
	return 100;
}

// Form-to-JSON builder.

 // Get the information that user has entered	
function buildJSONFromForm(backForth) {
	
	// Checking for "Required" field.
	var responseObject = new Object();
	responseObject.userAnswers = [];
	responseObject.pageId = currPageJSON.assessment.pageId;
	responseObject.currentPage = currPageJSON.assessment.currentPage;
	responseObject.navigation = backForth;
	
	// Build userAnswers from measures-requestbuilder.
	var fullReq = buildRequest(responseObject, $('#centerItemList')); 
	return fullReq.userAnswers;		
}

// This function will return the Yes/No values of each boolean question.
function getPageInfoDemographics() {
	console.log("-------------------------------------------------------------------");
	console.log("-------------------------------------------------------------------");
		console.log("measureId:" + nextJSONData.page.measures[0].measureId);
		for(var chk=0; chk<nextJSONData.page.measures[0].answers.length; chk++) {
			console.log("answerId: "+nextJSONData.page.measures[0].answers[chk].answerId+ " - " + "answerResponse: " + $("#inp"+nextJSONData.page.measures[0].answers[chk].answerId+"").is(":checked"));
		}
	console.log("-------------------------------------------------------------------");
	console.log("-------------------------------------------------------------------");
}

//The following codes are for displaying the server-side validation in a custom dialog box
function displayServerError(message) {
	$(function() {
	    $( "#dialogError" ).dialog({ 
	    		width: 350,
	    		height: 150,
	    		draggable: false,
	    		modal: true,
	    		title: "Server Error!",
	            open: function (e, ui) {
	                $(this).parent().find(".ui-dialog-buttonpane .ui-button")
	                    .addClass("customButtonsDialog");
	            }
	    	});
	  });
	$(".dialogErrorMessageText").html(message);
}


/* BELOW IS FOR NAVIGATION WORK AROUND PLEASE REMOVE */

function navto(requestObject){

    var request=JSON.stringify(requestObject);
     $.ajax({
        type: "POST",
        url: "services/assessments/active",
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        async:false,
        data: request,
        success: initialPageCallback
        });
}

function page3(){
	navto(
			{
				"pageId":3,
				"currentPage":2,
				"navigation":"next",
				"userAnswers":[
			        {
			            "measureId": 10,
			            "answers": [
			               {
			                  "answerId": 14,
			                  "answerResponse": "false"
			               },
			                              {
			                  "answerId": 15,
			                  "answerResponse": "false"
			               },
			                              {
			                  "answerId": 16,
			                  "answerResponse": "false"
			               },
			                              {
			                  "answerId": 17,
			                  "answerResponse": "false"
			               },
			                              {
			                  "answerId": 18,
			                  "answerResponse": "false"
			               },
			                              {
			                  "answerId": 19,
			                  "answerResponse": "true"
			               }
			            ]
			         },
			                  {
			            "measureId": 11,
			            "answers":             [
			                              {
			                  "answerId": 20,
			                  "answerResponse": "false"
			               },
			                              {
			                  "answerId": 21,
			                  "answerResponse": "false"
			               },
			                              {
			                  "answerId": 22,
			                  "answerResponse": "true"
			               },
			                              {
			                  "answerId": 23,
			                  "answerResponse": "false"
			               }
			            ]
			         },
			                  {
			            "measureId": 12,
			            "answers": [
			               {
			                  "answerId": 24,
			                  "answerResponse": "false"
			               },
			                              {
			                  "answerId": 25,
			                  "answerResponse": "false"
			               },
			                              {
			                  "answerId": 26,
			                  "answerResponse": "true"
			               },
			                              {
			                  "answerId": 27,
			                  "answerResponse": "false"
			               }
			            ]
			         },
			         {
			            "measureId": 13,
			            "answers": [
			               {
			                  "answerId": 28,
			                  "answerResponse": "true"
			               },
			                              {
			                  "answerId": 29,
			                  "answerResponse": "false"
			               }
			            ]
			         }
				]
			});
}

function page4(){
    navto(

    {
    "pageId":4,
    "currentPage":2,
    "navigation":"next",
    "userAnswers":[
       {
            "measureId": 14,
            "answers": [
               {
                  "answerId": 30,
                  "answerResponse": "true"
               },
                              {
                  "answerId": 31,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 32,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 33,
                  "answerResponse": "false"
               }
            ]
         },
         {
            "measureId": 15,
            "answers": [
               {
                  "answerId": 34,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 35,
                  "answerResponse": "true"
               },
                              {
                  "answerId": 36,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 37,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 38,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 39,
                  "answerResponse": "true",
                  "otherAnswerResponse": "Patents"
               }
            ]
         },
         {
            "measureId": 16,
            "answers": [
                              {
                  "answerId": 40,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 41,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 42,
                  "answerResponse": "true"
               }
            ]
         }
    ]
});
}



function page5(){
    navto(

        {
    "pageId":5,
    "currentPage":4,
    "navigation":"next",
    "userAnswers":[
         {
            "measureId": 18,
            "answers": [{"answerId": 45,
              "answerResponse": "01/01/1980"
              }]
         },
         {
            "measureId": 19,
            "answers": [
                              {
                  "answerId": 46,
                  "answerResponse": "5"
               },
                              {
                  "answerId": 47,
                  "answerResponse": "11"
               }
            ]
         },
         {
            "measureId": 20,
            "answers": [{"answerId": 48,
             "answerResponse": "200"
            }]
         },
                  {
            "measureId": 17,
            "answers": [
                              {
                  "answerId": 43,
                  "answerResponse": "true"
               },
                              {
                  "answerId": 44,
                  "answerResponse": "false"
               }
            ]
         },
                  {
            "measureId": 21,
            "answers": [
                              {
                  "answerId": 49,
                  "answerResponse": "true"
               },
                              {
                  "answerId": 50,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 51,
                  "answerResponse": "false"
               }
            ]
         },
                  {
            "measureId": 22,
            "answers": [
                              {
                  "answerId": 52,
                  "answerResponse": "true"
               },
                              {
                  "answerId": 53,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 54,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 55,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 56,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 57,
                  "answerResponse": "false",
                  "answerType": "other",
                  "otherAnswerResponse":""
               }
            ]
         }

    ]
});
}

function page6(){
    navto(
    {
    "pageId":6,
    "currentPage":5,
    "navigation":"next",
    "userAnswers":[
         {
            "measureId": 25,
            "answers": [{"answerId": 71,
              "answerResponse": "40"
              }]
         },
                  {
            "measureId": 26,
            "answers": [{"answerId": 72,
              "answerResponse": "clerk"
              }]
         },
                  {
            "measureId": 23,
            "answers": [
                              {
                  "answerId": 58,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 59,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 60,
                  "answerResponse": "true"
               },
                              {
                  "answerId": 61,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 62,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 63,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 64,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 65,
                  "answerResponse": "false"
               }
            ]
         },
                  {
            "measureId": 24,
            "answers": [
                              {
                  "answerId": 66,
                  "answerResponse": "true"
               },
                              {
                  "answerId": 67,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 68,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 69,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 70,
                  "answerResponse": "false"
               }
            ]
         },
                  {
            "measureId": 27,
            "answers": [
                              {
                  "answerId": 73,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 74,
                  "answerResponse": "true"
               },
                              {
                  "answerId": 75,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 76,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 77,
                  "answerResponse": "false"
               },
                              {
                  "answerId": 78,
                  "answerResponse": "true",
                  "answerType": "other",
                  "otherAnswerResponse":"Lottery"
               }
            ]
         }


    ]
});
}

function page7(){
    navto(
    		{
    			"pageId":7,
    			"currentPage":6,
    			"navigation":"next",
    			"userAnswers":[
    		                  {
    		            "measureId": 28,
    		            "answers": [
    		                              {
    		                  "answerId": 79,
    		                  "answerResponse": "true"
    		               },
    		                              {
    		                  "answerId": 80,
    		                  "answerResponse": "false"
    		               },
    		                              {
    		                  "answerId": 81,
    		                  "answerResponse": "false"
    		               },
    		                              {
    		                  "answerId": 82,
    		                  "answerResponse": "false"
    		               },
    		                              {
    		                  "answerId": 83,
    		                  "answerResponse": "false"
    		               },
    		                              {
    		                  "answerId": 84,
    		                  "answerResponse": "false"
    		               },
    		                              {
    		                  "answerId": 85,
    		                  "answerResponse": "false"
    		               },
    		                              {
    		                  "answerId": 86,
    		                  "answerResponse": "false"
    		               }
    		            ]
    		         },
    		         {
    		            "measureId": 29,
    		            "tableAnswers": [
    		               {
    		                 "rowId": 1,
    		                 "fields": [
    		                   { "answerId": 87, "answerResponse": 10 }
    		                  ]
    		               },
    		               {
    		                 "rowId": 2,
    		                 "fields": [
    		                   { "answerId": 87, "answerResponse": 13 }
    		            ]
    		          }
    		        ]
    		         }
    			]
    		});
}

