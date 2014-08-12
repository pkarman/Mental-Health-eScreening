/**
 *  measures-requestbuilder.js - 
 *	@description "Static" utility to generate Measures JSON requests. Requires measures-formbuilder.js and measures-integration.js
 *	@author Bryan Henderson, Jeewan Aryal, Ramu Karanam, Robin Carnow
 *	@date   1/9/2014
 */

function buildRequest(reqLevelOne /*JSON*/, formHTML /*HTMLElement with children*/)/*returns JSON*/{
	var req = reqLevelOne;
	req.userAnswers = evaluateAndBuildTypes(formHTML);
	return req;
}

function evaluateAndBuildTypes(formHTML /*HTMLElement with children*/)/* returns JSON */{
	// Iterate the form, figure out what type it is, farm out building the answers to the specific types, and return the content for userAnswers.

	var questions = $(formHTML).find("li[id^='que'], tr[id^='que']");
	
	var answers = [];
	$(questions).each(function(index){
		// Section off by type.
		var currLI = $(this);
		
			var questionType = currLI.attr('data');
			var measId = currLI.attr('id').substring(3);
			
			var measureObj = {"measureId":measId};
		
			switch (questionType){
				case "freeText":
				case "roText":
					var inputs = currLI.find('input[type=text]');
					measureObj.answers = grabAnswersFromText(inputs);
					break;
					
				case "selectMulti":
				case "selectMulti-Other":
				case "selectMulti-None":
					measureObj.answers = grabAnswersFromMulti(currLI, measId);
					break;
					
				case "selectOne":
					measureObj.answers = grabAnswersFromRadio(currLI);
					break;
					
				case "selectDropdown":
					var intSUL = $(currLI).find('select[id=inpILS'+measId+']');
					measureObj.answers = grabAnswersFromDropdown($(intSUL));
					break;
					
				case "tableQuestion":
					measureObj = buildFromTabularObject(currLI);
					break;
			}
			
			if(measureObj != null){
				
				//get row index
				var tableEntry = formBuilder.tableEntryFor(currLI);
				if(tableEntry.size() != 0){
					var row = tableEntry.index();
					//add row index to all answers
					$.each(measureObj.answers, function(i, answer){
						answer.rowId = row;
					});
				}
				
				answers.push(measureObj);
			}
	});
	return answers;
}

function isVisible(element){
	if(element.hasClass("surveyQuestion")){
		return ! element.hasClass("qInvisible");
	}
	return ! $(element).parents("li.surveyQuestion").hasClass("qInvisible");
}

// Keyed to specific types
function grabAnswersFromText(inputs /*Text input*/)/*returns JSON*/{
	var answers = [];
	$(inputs).each(function(){
		var answerId = parseInt($(this).attr('id').substring(3));
		var answerResponse = $(this).val();
		if(answerResponse != null){
			answerResponse = answerResponse.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
		}
		answers.push(buildSimpleObject(answerId, answerResponse));
	});
	
	return answers;
}

//TODO: this is very similar to grabAnswersFromRadio so we might be able to consolidate the logic
function grabAnswersFromMulti(liContainer /* selectMulti question li with any number of checkSwitch <li>s*/)/*returns JSON*/{
	var answers = [];
	liContainer
		.find("li[data^='selectMultiInt']")
		.each(function(index){
			var ansInput = $(this).find("input[type='checkbox']");
			var ansResponse = ansInput.parent().hasClass("on") ?  "true" : "false";
			
			var idStr = ansInput.attr('id').substring(3);
			var ansId = parseInt(idStr);
			
			var otherAnswer = $(this).find("input.selectOther[type='text']");
			if(otherAnswer.size() == 0 && $(this).attr("data") == "selectMultiInt-Other"){
				//extra check for matrix structure 
				otherAnswer = $(this).parent(".matrixQuestionText").find("input.selectOther[type='text']");
			}
			
			if(otherAnswer.size() > 0){
				answers.push(buildObjectFromOther(ansId, ansResponse, otherAnswer.val()));
			}
			else{			
				answers.push(buildSimpleObject(ansId, ansResponse));
			}
		});
	return answers;
}

function grabAnswersFromRadio(liContainer /* HTML (the element that contains LIs for each radio)*/, questId /* The measure id, to make life simple */)/*returns JSON*/{
	/*  A grouping of these beauties */
	var answers = [];
	var LIs = $(liContainer).find('li.selectOneList');
	LIs.each(function(index){
		var currRadio =  $(this).find("input[type=radio]");
		var ansId = currRadio.attr("id").substring(3);
		var ansResponse = currRadio.is(':checked')? "true" : "false";
		var otherAnswer = $(this).find("input.selectOther[type='text']");
		if(otherAnswer.size() == 0 && $(this).hasClass("selectOther")){
			//extra check for matrix structure 
			otherAnswer = $(this).parent(".matrixQuestionText").find("input.selectOther[type='text']");
		}
		if(otherAnswer.size() > 0){
			answers.push(buildObjectFromOther(ansId, ansResponse, otherAnswer.val()));
		}
		else{			
			answers.push(buildSimpleObject(ansId, ansResponse));
		}
	});
	
	return answers;
}

function grabAnswersFromDropdown(input) {
	// input -> select
	var answers = [];
	
	input.find("option:not(:first)").filter(":not(:selected)").each(function(ind) {
		var answerId = $(this).attr("id").substring(3);
		answers.push(buildSimpleObject(answerId, "false"));
	});
	
	var selected = input.find("option:selected");
	if(selected.val() != ""){
		var selectedAnswerId = selected.attr("id").substring(3);
		
		if(selected.attr("is_other") == "true") {
			answers.push(buildObjectFromOther(selectedAnswerId, "true", input.parent().find("input[type='text']").val()));
		}
		else{
			answers.push(buildSimpleObject(selectedAnswerId, "true"));
		}
	}
	
	return answers;
}

function buildSimpleObject(answerId, responseText /*HTMLElement*/)/*Returns JSON*/{
	return {"answerId":answerId, "answerResponse":responseText};
}

function buildObjectFromOther(answerId /*Number*/, responseText /*String*/, otherAnswerText /*String*/) /*returns JSON*/{
	return {"answerId":answerId, "answerResponse":responseText, "answerType":"other", "otherAnswerResponse":otherAnswerText};
}

function buildFromTabularObject(lineitem /* <li> HTMLElement*/)/*returns JSON*/{
	var answersArr = [];
	// Get the total number of answers for this table.
	var questId = parseInt($(lineitem).attr("id").substring(3));
	
	//var totalCols = formBuilder.tableModelFor(questId).columns;
	// Grab the div with the class='tableQuestionEntryContainer'.
	var tableAnswerCont =  $(lineitem).find('.tableQuestionEntryContainer');
	
	
	// Now, grab all of the input[type=text] fields from the enclosing tableAnswerCont.
	//var entryDIVs = $(tableAnswerCont).find('div.tableQuestionEntry > div');
	
	// Iterate over the returned <div>s and drag out the value for each.
	/*var rowId = 1;
	$(entryDIVs).each(function(index){
		// Grab the inputs for the current <div>
		
		// Scope the inner each(), and iterate only the inputs in that context.
		//Initialize fields array.
		var fieldsArr = [];
		$(this).find('input[type=text]').each(function(index){
			var answerId = parseInt($(this).attr('answerid'));
			var answerResponse = $(this).val();
			var answerObj = buildSimpleObject(answerId, answerResponse);
			fieldsArr.push(answerObj);
		});
		// Build the answer.
		var rowObj = {"rowId":rowId, "fields":fieldsArr};
		answersArr.push(rowObj);
		rowId++;
	});*/
	
	//add None option
	var answers = [];
	var noneInput = tableAnswerCont.find("input.tableQuestionNA").first();
	if(noneInput.size() == 0){
		var answerId = null;
		var question = formBuilder.tableModelFor(questId);
		if(question.answers != null){
			for(var i = 0; i < question.answers.length; i++){
				var answer = question.answers[i];
				if(answer.answerType != null &&  answer.answerType == "none"){
					answerId = answer.answerId;
				}
			}
		}
		if(answerId != null){
			answers.push(buildSimpleObject(answerId, "false"));
		}
	}
	else{
		var answerId = parseInt(noneInput.attr('id').substring(3));
		var answerResponse = noneInput.val();
		answers.push(buildSimpleObject(answerId, answerResponse));
	}
	
	var questObj = {"measureId":questId, "tableAnswers":answersArr, "answers":answers};
	return questObj;
}


