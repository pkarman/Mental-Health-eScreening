
// ------------------------ Assessment Completion by Jeewan --------------------------- //

function createSurveyCompleForm(json) {
	measures = json.page.measures; 
	$("#measuresProgress li label").each(function(index) {
		$("#measuresProgress li label").parent().removeClass("triangleWrapper");
	});
	
	var formContainer = $('<div id="formContainer">');
	var formElements = $('<ul id="centerItemList">');
	
	var txt1 = " You have completed the VA San Diego eScreening Questionnaire!";
	
	var completionInfo = "<div id='completionInfo'>"+txt1+"</div>";
	
	var questionCompleteGraphics = "<div id='questionCompleteGraphics'></div>";
	
	// Buttons
	var buttonWrapper = "<div class='buttonWrapper'>" +
							"<div><input id='answerIncompleteButton' class='answerButton' type='button' value='Answer incomplete questions'></div>" +
							"<div id='surveyFinishedButton' style='margin-left:2px;'><input id='completedButton' class='answerButton' type='button' value='I am finished with the questionnaire'></div>" +
						"</div>";
	
	formElements.append(completionInfo);
	formElements.append(questionCompleteGraphics);
	formElements.append(buttonWrapper);
	
	formContainer.append(formElements);
	
	$('#nextBtn, #saveExitBtn').css('visibility', 'hidden');
	return formContainer;
}


function surveyCompleteFormActions(json) {
	var finalPercentCompletion = 0;
	
	finalPercentCompletion = getFinalPercentCompletion(json);
	if(finalPercentCompletion["accumulatedTotal"]==100) {
		$("#answerIncompleteButton").addClass("hideButton");
		$("#completedButton").css("margin-top", "200px");
	}
	
	else {
		$("#answerIncompleteButton, #surveyFinishedButton").addClass("showButton");
		$('#backBtn').css('visibility', 'visible');
	}
	    
	    $("#answerIncompleteButton")
	    	.off()
	    	.on( "click", function() {		
				callMeasure(measuresURL, 
						buildReqAdvBack('firstSkipped', buildJSONFromForm('firstSkipped')),
						initialPageCallback);
				showSkipNavigationButtons(currPageJSON);
			});

    $("#surveyFinishedButton").on( "click", function() {
    	showLastPageCall();
    });
    
    showCompletionData(json);
}

function showSkipNavigationButtons(currPageJSON) {
	$('#nextIncompleteButton').remove();
	$('#bottomNavigation').append("<input id='nextIncompleteButton' class='anchorButton inputButton searchBtn' type='button' value='Next Incomplete &gt;&gt;'>");
	$("#nextIncompleteButton").on( "click", function() {
		if (surveyValidation.pageIsValid()){
			callMeasure(measuresURL,
				buildReqAdvBack('nextSkipped', buildJSONFromForm('nextSkipped')),
				initialPageCallback);
    	}
    });
}

function showLastPageCall() {
	$("#backBtn").css("visibility", "hidden");
	$.ajax({
		  type: "GET",
		  url: "services/assessments/end",
	      dataType: 'json',
	      contentType: "application/json; charset=utf-8",
		  async:false
		}).done(function(data) {
			showCongratsPage(data);
		});
}

function showCongratsPage(data) {
	$("#left").hide();
	$("#center").addClass("lastPageCenter");
	$('#assessmentContainer').empty();
	var formContainer = $('<div id="formContainer">');
	var formElements = $('<ul id="centerItemList">');
	var textCongrats = " Congratulations! ";
	
	var congratsString = "<div id='congratsLabel'>"+textCongrats+"</div>";
	var graphicImages = "<div id='graphicImages'> " +
						"<div class='imageContainer'><img src='resources/images/final_cesamh.png'></div>" +
						"<div class='imageContainer'><img src='resources/images/final_va.png'></div>" +"</div>";
	var completionText = "<div id='completionText'>"+data.completionText+"</div>";
	
	var summaryNotes = $("<div id='summaryNotes'>");
	$.each(data.summaryNotes, function(k, v){
		summaryNotes.append(v);
		summaryNotes.append("<br /><br />");
	});
	
	var buttonWrapper = "<div class='buttonWrapper'> " +
							"<div id='doneButton'><input class='answerButton doneButton' type='button' value='Done'></div>"+
						"</div>";
	
	formElements.append(congratsString);
	formElements.append(completionText);
	formElements.append(summaryNotes);
	formElements.append(buttonWrapper);
	formElements.append(graphicImages);
	formContainer.append(formElements);
	$('#assessmentContainer').html(formContainer);
	$("html, body").animate({ scrollTop: 0 }, "slow");
	$("#doneButton > input.doneButton").click(function(){ logout("complete");});
}


function showCompletionData(json) {
	$("#nextIncompleteButton").remove();
	var dataArray = getFinalPercentCompletion(json);
	var accumulatedTotal = parseInt(dataArray["accumulatedTotal"]);
	
	$("#questionCompleteGraphics").append("<div class='progressShowWrapper'><span class='accumulatedTotal'>Overall Questions Completed</span><div class='progress-main'>" +
			"<span class='progress-val'>"+accumulatedTotal+"%</span><span class='progress-bar'><span class='progress-in' style='width: "+accumulatedTotal+"%'></span>" +
			"</span></div></div>");
}

function getFinalPercentCompletion(json) {
	var percentData = {};
	var totalQuestionCount = totalResponseCount = 0;
	$.each(json.surveyProgresses, function(i, item) {
	    totalQuestionCount += item.totalQuestionCount;
	    totalResponseCount += item.totalResponseCount;
	});
	
	percentData["accumulatedTotal"] = Math.ceil((totalResponseCount / totalQuestionCount) *100);
	
	return percentData;
}
