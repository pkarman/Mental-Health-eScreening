/**
 *  measures-formbuilder.js 
 *	@description "Static" utility to generate Measures forms.
 *	@author Bryan Henderson, Jeewan Aryal, Ramu Karanam, Robin Carnow
 *	@date   1/8/2014
 *  @param surveyValidation an optional (can be null) instance of the surveyValidation object.
 *  @param visibilityUpdateFunction an optional (can be null) function to be called when the visibility of 
 *  questions should be updated.
 */
function FormBuilder(surveyValidation, visibilityUpdateFunction){
	var self = this;
	
	//builders
	var tableBuilder = new TableBuilder(this);
	var selectBuilder = new SelectBuilder(this);
	var matrixBuilder = new MatrixBuilder(this, selectBuilder);
	

	/* Public methods */
	
	/** -- Receives JSON measures array, builds the form jQuery, returns it to the caller. -- **/
	this.createForm = function(questions /* JSON */) /* returns jQuery HTML element */{
		tableBuilder.reset();
		if(surveyValidation != null){
			surveyValidation.reset();
		}
		
		// Create the actual #formContainer div.
		var formContainer = $('<div id="formContainer">');
		
		// Create list to structure the form elements.
		var formElements = $('<ul id="centerItemList">').appendTo(formContainer);
		
		self.fillFormContainer(questions, formElements);
		
		return formContainer;
	};

	/** -- Creates a rendering of the given JSON questions (as html elements) and appends them to the given container. -- **/
	this.fillFormContainer = function(questions /* JSON */, containter /* jQuery Object */, rowIndex /* Integer */){
		
		// Loop through the question array, building the appropriate form elements, appending to the container.
		$.each(questions, function(i, question){
			var input = buildInput(question, rowIndex);
			
			if(input != null){
				containter.append(input);
			}
		});

		/* Matrix Table - In case of TD empty remove the parent TH */
		containter.find( ".matrixQuestionText" ).each(function( index ) {
			var matrixQuestionText = $("#assessmentContainer").find(".matrixQuestionText").html();
			//if (matrixQuestionText == '') {
				$(this).addClass("empty");
				$(this).closest('table').find('th').eq(0).addClass("empty");
			//}
		});

	};
	
	/* Mediator (pattern) functions */
	
	/**
	 * We are using the form builder as a mediator object so we expose some individual builder function here
	 */
	this.tableEntryFor = function(LI){
		return tableBuilder.tableEntryFor(LI);
	};
	
	/**
	 * @return table model for the given question ID
	 */
	this.tableModelFor = function(questionId){
		return tableBuilder.getTableModel(questionId);
	};
	
	/**
	 * If visibilityUpdateFunction was provided during this object's construction then it 
	 * will be called.
	 */
	this.checkVisibility = function(){
		if(visibilityUpdateFunction != null)
			visibilityUpdateFunction();
	};

	/**
	 * Generates an ID to be used for an input corresponding to a question's answer.
	 * @answerId an integer representing the unique ID of an answer.  Only an integer will be accepted.
	 * @param rowIndex is an optional integer representing the index of the table row the input will be in.
	 * @param idPrefix is an optional prefix which allows for related items to an answer to have an ID generated
     * and later the ID can be extracted. This should only contain letters and numbers. 
	 */
	this.createAnswerId = function(answerId, rowIndex, idPrefix){
	    if(answerId != parseInt(answerId)){
	        var msg = "ID of an answer should be an integer.";
	        console.error(msg + " Answer ID given: " + answerId);
	        throw msg;
	    }
	    var index = parseInt(rowIndex) == rowIndex ? rowIndex : "";
	    var prefix = idPrefix ? idPrefix : "";
	    return "inp" + prefix + index + "_" + answerId;
	};
	
	var inputRegex = /inp\w*_/;
    /**
     * Retrieves the embedded answer ID found in the html element's id attribute
     */
    this.getAnswerId = function(elementId){
        return parseInt(elementId.replace(inputRegex, ""));
    };
	
	/**
     * Generates an ID to be used for an HTML element corresponding to a question
     * @param measureId an integer representing the unique ID for a question.  Only an integer will be accepted.
     * @param rowIndex is an optional integer representing the index of the table row the input will be in.
     * @param idPrefix is an optional prefix which allows for related items to a question to have an ID generated
     * and later the ID can be extracted. This can only contain letters and numbers. 
     * Note: the use of this method has not been rolled out to all questions.
     */
    this.createQuestionId = function(measureId, rowIndex, idPrefix){
        if(measureId != parseInt(measureId)){
            var msg = "ID of a question should be an integer.";
            console.error(msg + " Question ID given: " + measureId);
            throw msg;
        }
        var index = parseInt(rowIndex) == rowIndex ? rowIndex : "";
        var prefix = idPrefix ? idPrefix : "";
        return "que" + prefix + index + "_" + measureId;
    };
    
    var questionRegex = /que\w*_/;
    /**
     * Retrieves the embedded question ID found in the html element's id attribute
     */
    this.getQuestionId = function(elementId){
        return parseInt(elementId.replace(questionRegex, ""));
    };
    
    /**
     * Builds the basic entry for all questions
     * question - question object from server. 
     * textOverride - optional, if given then the question text in question object is ignored
     * typeOverride - optional, if given the question type in question object is ignored
     */
    this.buildQuestionLI = function (question, textOverride, typeOverride, labelForId){
        
        var questionLI = $("<li>")
            .attr("class", "surveyQuestion")
            .attr("data", typeOverride == null ? question.measureType: typeOverride)
            .attr("id", "que" + question.measureId);
        
        if(!question.isVisible){
            questionLI.addClass("qInvisible");
        }
        
        //question text
        var questionText = textOverride == null ? question.measureText : textOverride;
        var title = $("<div/>")
            .attr("id", "title" + question.measureId)
            .addClass("surveyQuestionText");
        
        if(labelForId){
            title.append(
                    $("<label/>")
                    .attr("for", labelForId)
                    .html(questionText)
            );
        }
        else{
            title.html(questionText);
        }
        
        questionLI.append(title);
        
        if(question.isRequired==true) {
            questionLI.addClass("isRequired");
            
            //add icon div to title
            $("<div/>")
                .addClass("requiredIcon")
                .html("*")
                .appendTo(title);
        }

        return questionLI;
    };
    
	
	
	
	/* Private methods */
	
	/** -- "Factory" method to create the form elements. -- **/
	function buildInput(question /* JSON */, rowIndex /* optional Integer */) /* returns jQuery HTML element or null if nothing should be appended*/{
		
		var input = null;
		
		switch(question.measureType){
			case "freeText":
				input = buildTextQuestion(question, false, rowIndex);
				break;
				
			case "readOnlyText":
				input = buildTextQuestion(question, true, rowIndex);
				break;
				
			case "selectMulti":
				input = selectBuilder.buildSelectMulti(question, rowIndex);
			 	break;
			 	
			case "selectOne":
				input = selectBuilder.buildSelectSingle(question, rowIndex);
				break;
				
			case "tableQuestion":
				input = tableBuilder.buildTableQuestion(question);
				break;
			
			case "selectOneMatrix":
				input = matrixBuilder.buildOneMatrixQuestion(question);
				break;

			case "selectMultiMatrix":
				input = matrixBuilder.buildMultiMatrixQuestion(question);
				break;
				
			case "instruction":
				input = buildInstruction(question);
				break;
		}
		return  input;
	}

	
	function buildTextQuestion(question, readOnly, rowIndex){
		if(surveyValidation != null){
			surveyValidation.addMeasureValidations(question.measureId, question.validations);
		}
		
		if(readOnly == null) readOnly = false;
		
		var li = $("<li>")
			.attr("class", "surveyQuestion freeTextQuestion")
			.attr("data", readOnly ? "roText" : "freeText")
			.attr("measureId", question.measureId)
			.attr("id", "que" + question.measureId);
		
		var qLabel = $("<label>")
			.attr("class", "freeTextQuestion" + (question.answers.length > 1 ? " freeTextMulti" : ""))
			.html(question.measureText);
		
		if(question.answers.length == 1){
			qLabel.attr("for", self.createAnswerId(question.answers[0].answerId, rowIndex));
		}
		
		var labelDiv = $("<div>")
			.addClass("freeTextLabel")
			.append(qLabel);
		
		var fieldContainer = $("<div>").attr("class", "freeTextFieldContainer");
		
		for(var i = 0; i < question.answers.length; i++){
			var userResponse = question.answers[i].answerResponse;
			if(userResponse == undefined) {
				userResponse = "";
			}
			
			var answerId = self.createAnswerId(question.answers[i].answerId, rowIndex);
			var input = $("<input>")
				.attr("type", "text")
				.attr("id", answerId)
				.prop("readonly", readOnly)
				.val(userResponse)
				.appendTo(fieldContainer);
			
			if(readOnly){
				input.addClass("freeTextReadOnly");
			}
				
			if(question.answers.length > 1){
				input.addClass("freeTextMulti");
			}
			
			if(question.isRequired==true) {
				input.addClass("isRequired");
			}
			
			var answerText = question.answers[i].answerText;
			if(answerText != undefined){
				$("<span>")
					.attr("for", answerId)
					.addClass("freeTextAnswer")
					.html(answerText)
					.appendTo(fieldContainer);
			}
		}
		
		if(question.isRequired==true) {
			li.addClass("isRequired");
			qLabel.append("<span class='requiredIcon'>*</span>");
		}
		
		li.append(labelDiv)
		  .append(fieldContainer);
		
		if(!question.isVisible){
			li.addClass("qInvisible");
		}
		return li;
	}
	
	/**
	 * Creates an instruction entry on the page.
	 * (If we were to pull the question builders into the form builder's definition then would could avoid making this function public)
	 */
	function buildInstruction(question) {
		var li = $("<li/>")
			.addClass("surveyQuestion instructionLi")
			.attr({
				"id" : "que"+question.measureId,
				"data" : "instruction"
			})
			.append(question.measureText);
		
		if(!question.isVisible){
			li.addClass("qInvisible");
		}
		return li;
	}
}	
	
/* Builder Classes */ 

/**  
 * Select (single/multi) question builder class 
 **/
function SelectBuilder(formBuilder){
	var self = this; 
	var groupCounter = 0;
	
	/* PUBLIC METHODS */
	
	this.buildSelectMulti = function(question, rowIndex){
	    var questionText = question.measureText;
	    
	    if(question.answers.length > 1)
	    {
	    	questionText = question.measureText + " (mark all that apply)";
	    }
		var questionLI = formBuilder.buildQuestionLI(question, questionText);
			
		var innerUL = $("<ul/>")
				 .attr("id", "inpIL" + question.measureId)
				 .addClass("selectMulti")
				 .appendTo(questionLI);
		
		$.each(question.answers, function(index, answer){
			var answerElement = self.buildCheckboxLI(answer, question.measureId, rowIndex);
			$(innerUL).append(answerElement);
		});
		
		return questionLI;
	};
	
	this.buildSelectSingle = function(question, rowIndex){
		return question.answers.length < 5 ? 
				buildRadioGroup(question, rowIndex) :
				buildDropdown(question, rowIndex);
	};

	this.buildCheckboxLI = function(answer, measId, rowIndex){

		var li = $("<li/>")
			.addClass("selectMultiCheckbox");
		
		var answerEleId = formBuilder.createAnswerId(answer.answerId, rowIndex);
    
    var checkbox = $('<input/>')
			.attr("type", "checkbox")
			.attr("id", answerEleId)
			.addClass("checkSwitch");
			
		var answerText = answer.answerResponse;
		checkbox.prop("checked", answerText == "true");

		// checkbox div
		$("<div/>")
			.addClass("selectMultiInput")
            .append(checkbox)
			.appendTo(li);
		
		var labelDiv = $("<div/>")
			.addClass("checkSwitchTextLabel")
			.appendTo(li);

		if(answer.answerText != null && answer.answerText.length > 0){
			if(answer.answerType == "other"){ 
				$("<label/>")
                    .attr("for", formBuilder.createAnswerId(answer.answerId, rowIndex, "other"))
                    .html(answer.answerText)
                    .appendTo(labelDiv);
				//we only add this if answerText has something (matrix uses this)
				self.buildOtherDiv(answer, null, rowIndex).appendTo(labelDiv);
			}
			else{
				$("<label/>")
                    .attr("for", checkbox.attr("id"))
                    .html(answer.answerText)
                    .appendTo(labelDiv);
			}
		}
			
		if(answer.answerType == null || answer.answerType == "") {
			li.attr("data", "selectMultiInt");
		}
		else if(answer.answerType == "other") {
			li.attr("data", "selectMultiInt-Other");
		}
		else if (answer.answerType == "none"){
			li.attr("data", "selectMultiInt-None")
			  .addClass("hasNone");
		}
		else{
			displayServerError("Error: '" + answer.answerType + "' is not a valid answer type.");
			return null;
		}

		wrapCheckSwitch(checkbox);
		
		return li;
	};
	
	this.buildOtherDiv = function(answer, hiddenLabelText, rowIndex){
		var textInputId = formBuilder.createAnswerId(answer.answerId, rowIndex, "other");
		
        var otherTextInput = $("<input/>")
            .attr("type", "text")
            .attr("id", textInputId)
            .addClass("selectOther");

		//set saved response
		if(answer.otherAnswerResponse != null){
			otherTextInput.val(answer.otherAnswerResponse);
		}

		var otherDiv = $("<div/>").addClass("selectOther");
		
		//add label if given
		if(hiddenLabelText){
            $("<label/>")
                .attr("for", textInputId)
                .html(hiddenLabelText)
                .addClass("selectOtherHidden")
                .appendTo(otherDiv);
		}
		
		otherDiv.append(otherTextInput);
		return otherDiv;
	};

	/**
	 * This is also used for matrix question types so test them if you make changes.
	 * Also, we are counting on this being a radio input so if that changes please update
	 * the way we notice a user changing an answer (for visibility updates)
	 * 
	 * @param columnHeader is optional for when the radio is a cell in a table. This allows for an additional
	 * hidden label to be added. 
	 * Please note: for 508 either answer.answerText or columnHeader should be given 
	 * @param rowIndex is the index of the table question row which this radio is in
	 */
	this.buildRadioLI = function (answer, groupName, columnHeader, rowIndex){
		
		var inputId = formBuilder.createAnswerId(answer.answerId, rowIndex);
		
		var li = $("<li/>")
			.addClass("selectOneList")
			.addClass("surveyQuestion") //it would be nice if this was removed
			.attr("data", "selectOneInt");  
		
		var inputDiv = $("<div/>")
			.addClass("selectOneInput")
			.appendTo(li);

        $("<input/>")
			.attr("name", groupName)
			.attr("type", "radio")
			.attr("id", inputId)
      .attr("class", "selectOneInput")
			.prop("checked", answer.answerResponse === "true")
			.appendTo(inputDiv);
		
		//added empty element to hold radio graphic
		inputDiv.append("<div/>");
		
		var labelDiv = $("<div/>")
			.addClass("selectOneLabel")
			.appendTo(li);
		
		if(answer.answerText != null && answer.answerText.length > 0){
			$("<label/>")
				.addClass("radioLabel")
				.attr("for", inputId)
				.html(answer.answerText)
				.appendTo(labelDiv);
			
			if(answer.answerType == "other"){
				self.buildOtherDiv(answer, answer.answerText, rowIndex)
				    .appendTo(labelDiv);
			}
		}
		else{// Workaround to add title in case of radio buttons
            $("<label/>")
                .attr("for", inputId)
                .attr("class", "selectOneInputHidden") 
                .text(columnHeader ? columnHeader : "undefined")
                .appendTo(labelDiv);
		}
		
		if(answer.answerType == "other"){
			li.addClass("selectOther");
		}
		if(answer.answerType == "none"){
			li.addClass("selectNone");
		}
		
		return li;
	};	
	
	this.checkSwitchOff = function(checkboxDiv){
		if(checkboxDiv != null){
			checkboxDiv
				.addClass('off')
				.removeClass('on')
				.find('input.checkSwitch')
				.prop('checked', false);
		}
	};

	this.checkSwitchOn = function(checkboxDiv){
		if(checkboxDiv != null){
			checkboxDiv
				.addClass('on')
				.removeClass('off')
				.find('input.checkSwitch')
				.prop('checked', true);
		}
	};
	
	
	/* Private methods */
	
	function buildRadioGroup(question, rowIndex){
		var questionLI = formBuilder.buildQuestionLI(question, question.measureText + " (mark one)");

		var innerUL = $("<ul/>")
			.attr("id", formBuilder.createQuestionId(question.measureId, rowIndex, "ILS"))
			.addClass("selectOne")
			.appendTo(questionLI);
		
		var groupName = "grp_" + question.measureId;
		for(var i = 0; i < question.answers.length; i++) {
			var radio = self.buildRadioLI(question.answers[i], groupName+groupCounter, null, rowIndex);
			radio.appendTo(innerUL)
				 .find("input[type=radio]")
				 .change(formBuilder.checkVisibility);
		}
		groupCounter++;
		return questionLI;
	}

	function buildDropdown(question, rowIndex){
	    var questionId = formBuilder.createQuestionId( question.measureId, rowIndex, "ILS");
	    
		var questionLI = formBuilder.buildQuestionLI(question, null, "selectDropdown", questionId)
						 	.addClass("surveyQuestion dropdownList");
		
		var wrapperDiv = $("<div/>")
			.addClass("dropdownWrapper")
			.appendTo(questionLI);
		
		var row = rowIndex ? parseInt(rowIndex): null;
		
		var select = $("<select/>");
		select
			.attr("id", questionId)
			.addClass("selectOne")
			.addClass("selectDropdown")
			.appendTo(wrapperDiv)
			.on("change", function(){showOtherForDropdown(select, row);})
			.on("change", formBuilder.checkVisibility)
			.append("<option id='' value=''>Select One</option>");
		
		for(var i = 0; i < question.answers.length; i++) {
			var option = $("<option/>")
				.attr("id", formBuilder.createAnswerId(question.answers[i].answerId, rowIndex))
				.attr("is_other", question.answers[i].answerType == "other" ? "true" : "false")
				.val(question.answers[i].answerText.replace(/\'/g, ""))
				.html(question.answers[i].answerText)
				.appendTo(select);
			
			if(question.answers[i].answerResponse == "true"){
				option.prop('selected', true);
				
				// otherAnswerResponse
				if(question.answers[i].answerType == "other"){
					option.attr("answerType", "other");
					//todo: pull this out into its own function, add id to text field, use label with "for=inputid" (using jquery functions), set value of input
					if(question.answers[i].otherAnswerResponse==undefined) {
						question.answers[i].otherAnswerResponse = "";
					}
					appendDropdownOther(select, rowIndex, question.answers[i].otherAnswerResponse);
				}
			}
		}

		return questionLI;
	}

	function showOtherForDropdown(select, rowIndex){
	    select.parent().find("span").remove();
		if(select.find("option:selected").attr("is_other") == "true") {
			appendDropdownOther(select, rowIndex);
		}
	}

    function appendDropdownOther(select, rowIndex, response){
        var selectId = formBuilder.getQuestionId(select.attr("id"));
        var inputId = formBuilder.createQuestionId(selectId, rowIndex, "other");
		
        var ddOther = $("<div/>").addClass("ddOther");
        var span = $("<span/>")
                .addClass("dropdownOther")
                .appendTo(ddOther);
        
        $("<label/>")
            .attr("for", inputId)
            .addClass("selectOtherHidden")
            .html("Please specify")
            .appendTo(span);
        
        $("<input/>")
            .addClass("dropdownOther")
            .attr("type", "text")
            .attr("id", inputId)
            .attr("placeholder", "Please specify")
            .val(response == null ? "" : response)
            .appendTo(span);

        select.parent().append(ddOther);
    }

	/**
	 * Wraps checkSwitch input with on/off divs to initialize switch effect.
	 * Should be called after each checkSwitch checkbox has been added to the page
	 */
	function wrapCheckSwitch(input){
		
		var checkDiv = $("<div/>")
			.addClass("checkSwitch")
			.addClass(input.prop('checked') ? "on" : "off")
			.click(checkboxClick);
		
		checkDiv = input.wrap(checkDiv).parent();
		
		var switchInnerDiv = $("<div/>")
			.addClass("checkSwitchInner")
			.appendTo(checkDiv);
		
		$("<div/>")
			.addClass("checkSwitchOn")
			.html("Yes")
			.appendTo(switchInnerDiv);
		
		$("<div/>")
			.addClass("checkSwitchHandle")
			.appendTo(switchInnerDiv);
		
		$("<div/>")
			.addClass("checkSwitchOff")
			.html("No")
			.appendTo(switchInnerDiv);
	}

	//event handler
	function checkboxClick(){
		var checkboxDiv = $(this);
		
		if(checkboxDiv.hasClass('off')){
			//toggle switch on
			self.checkSwitchOn(checkboxDiv);
			
		} else if(checkboxDiv.hasClass('on')){
			//toggle switch off
			self.checkSwitchOff(checkboxDiv);		
		}
		
		var isNone = checkboxDiv.parents("li[data='selectMultiInt-None']").size() > 0;
		var group =  checkboxDiv.parents("[data='selectMulti']");
		
		if(isNone){
			if(checkboxDiv.hasClass('on')){
				//if this is a None switch that was turned on, then turn off all others			
				var notNoneSwitches = group.find("li[data='selectMultiInt'] .checkSwitch, li[data='selectMultiInt-Other'] .checkSwitch");
				self.checkSwitchOff(notNoneSwitches);
			}
		}
		else{
			//turn any None switches off
			var noneSwitch = group.find("li[data='selectMultiInt-None'] .checkSwitch");
			self.checkSwitchOff(noneSwitch);
		}
		
		//start async ajax call to update question visibility
		formBuilder.checkVisibility();
	}
}

/** Table style question builder **/
function TableBuilder(formBuilder){
	
	var tableQuestionLookup = {};
	var entryId = 0;
	var self = this; 

	/* PUBLIC METHODS */
	
	this.buildTableQuestion = function(question){
		addTableModel(question);
		
		var id = question.measureId;
		var table = formBuilder.buildQuestionLI(question);
		var entryName = "Entry";  //to be changed if given in None answer
		
		// get None answer if given
		var noneAnswer = null;
		if(question.answers != null){
			for(var i = 0; i < question.answers.length; i++){
				var answer = question.answers[i];
				
				if(answer.answerType != null &&  answer.answerType == "none" && question.isRequired != true){ // JH case added in case of required question
					noneAnswer = answer;
					break;
				}
			}
			if(noneAnswer != null && noneAnswer.answerText.replace(/^\s+|\s+$/g, '') != ""){
				entryName = noneAnswer.answerText;
			}
		}
		
		table.data("entryName", entryName);
		
		var questionContainer = $("<ul/>")
			.addClass("tableQuestionEntryContainer")
			.appendTo(table);
		
		var addButton = $("<input/>")
			.attr("id", "tableQuestionAdd_" + id)
			.attr("type", "button")
			.val("Add " + entryName)
			.addClass("searchBtn")
			.addClass("tableQuestionAdd")
			.on("click", function(){
				var noneDiv = questionContainer.find("div.tableQuestionNA");
				var checkVis = noneDiv.size() > 0;
				
				if(questionContainer.children().length == 0) {
					var rowCount = questionContainer.children().size();
					createTableForm(id, questionContainer, rowCount, true, entryName, checkVis);
				}
				else {
					if (surveyValidation != null && surveyValidation.pageIsValid()){ // we don't really need to validate on every Add						
						var skipped = pageSkipped();
						if(skipped.isRequired){
							openRequiredDialog();
						}
						else {
							var entryCount = questionContainer.find(".tableQuestionEntry").size();
							var lastEntryIsClicked = questionContainer.find("div.tableQuestionEntry:last").hasClass("wasClicked");
							if(entryCount == 0 || lastEntryIsClicked) {
								createTableForm(id, questionContainer, entryCount, true, entryName, checkVis);
							}
							else{
								openTableQuestionEntryClicked();
							}
						}
			    	}
				}
				
				noneDiv.slideUp(400, function(){
						$(this).remove();
						formBuilder.checkVisibility();
					});
				
				// reshow the none button
				$(this).nextAll(".tableNone").fadeIn();
				$(this).val("Add " + entryName);
			} );
		
		$("<div/>")
			.addClass("tableQuestionButtons")
			.append(addButton)
			.appendTo(table);
		
		// get row count
		var rowCount = 0;
		if(question.childMeasures.length > 0 
				&& question.childMeasures[0].tableAnswers != null){
			rowCount = question.childMeasures[0].tableAnswers.length;
		}
		
		//call create table form for each row
		for(var i = 0; i < rowCount; i++){
			// we may have to add table data here for each row or maybe the datatable plugin can do it in a nicer way
			
			//for now we will just add a set of child measures to the question entry container
			createTableForm(id, questionContainer, i, false, entryName, true).addClass("wasClicked");
		}
	    
		//configure None option if found in answers array
		if(noneAnswer != null){				
			//add None button
			var noneButton = $("<input/>")
				.attr("id", "tableQuestionNone_" + question.measureId)
				.addClass("searchBtn tableNone")
				.attr("type", "button")
				.val("None")
				.click( function(){ 
					clearTableQuestion(table, noneAnswer.answerId, $(this), true);
					formBuilder.checkVisibility();
				} )
				.appendTo(table.find(".tableQuestionButtons"));
			
			//if the user has said None in the past set it now
			if(noneAnswer.answerResponse == "true"){
				clearTableQuestion(table, noneAnswer.answerId, noneButton);
			}
		}
		
		return table;
	};
	
	this.getTableModel = function(tableMeasureId){
		return tableQuestionLookup[tableMeasureId];
	};
	
	/**
	 * Clears all table models
	 */
	this.reset = function(){
		tableQuestionLookup = {};
	};
	
	/**
	 * Return the entry that the given componet is found in
	 */
	this.tableEntryFor = function(component){
		if(component.hasClass("tableQuestionEntry") )
			return component;
		return component.parents(".tableQuestionEntry");
	};
		
	/* PRIVATE METHODS */
	
	/**
	 * Creates a new set of questions for the table.  If the rowIndex given of the data model has data, it will be prepopulated.
	 * @param tableMeasureId the measure id for the table question
	 * @param container where the rendered questions should be appended to
	 * @param rowIndex the row of the table that the form represents
	 * @param animate if true then an animation will be used to show the new entry. **Should animate only after first entry is showing.
	 * @param entryName is the name of each entry (e.g. Child, Enlistment, etc.)
	 * @param skipVisCheck if true then the visibility check called after the entry has been added will be skipped
	 * @returns the entry that was created and appended to the given container (for further custom updates). 
	 */
	function createTableForm(tableMeasureId, container, rowIndex, animate, entryName, skipVisCheck){
		var measures = self.getTableModel(tableMeasureId)['childMeasures'];
		var onlyOneMeasure = measures.length == 1;
		
		//for each question in the table model set which answers will be used (empty or response-ed)
		for(var i = 0; i < measures.length; i++){
			var measure = measures[i];
			if(rowIndex >= measure.tableAnswers.length){
				//This is a new row, use emptyAnswers
				measure.answers = measure.emptyAnswers;
			}
			else{
				//use model data with saved responses
				measure.answers = measure.tableAnswers[rowIndex];
			}
		}
		
		var entry = $("<div/>")
			.data("entryName", entryName);

		var deleteWrapper = $("<div/>")
			.addClass("deleteRow")
			.appendTo(entry);
		
		if(onlyOneMeasure){
			entry.addClass("tableSingleEntry");
		}
		
		$("<span/>")
			.addClass("entryLabelText")
			.html(entryName  + "  " + (rowIndex + 1))
			.appendTo(deleteWrapper);
		
		$("<input/>")
			.addClass("tableDeleteButton")
			.attr("type", "button")
			.attr("tabindex", "-1")
			.val("Delete")
			.click(function() {	
				//remove row from data model
				for(var i = 0; i < measures.length; i++){
					var measure = measures[i];
					if(rowIndex < measure.tableAnswers.length){
						//use model data with saved responses
						measure.tableAnswers.splice(rowIndex, 1);
					}
				}
				
				removeEntry(self.tableEntryFor($(this)));
			})
			.appendTo(deleteWrapper);
		
		var questionDiv = $("<div/>")
			.addClass("tableQuestionEntry-questionContainer")
			.appendTo(entry);
		
		//render entry providing the responses saved for row rowIndex from model
		formBuilder.fillFormContainer(measures, questionDiv, rowIndex);
		
		//set highlight for entry on any focus in it
		questionDiv.find("*").focus(function(){highlightEntry($(this), true);});
	
		//this is needed to that we can shrink the entry for animations (i.e. entry css does not allow)
		var entryWrapper = $("<div/>")
			.addClass("tableQuestionEntry")
			.attr("id", "tabEntry" + entryId++)
			.append(entry);
		
		if(animate){			
			entryWrapper				
				.hide()
				.appendTo(container)
				.slideDown(600);
			
			highlightEntry(entryWrapper, false);
			
			if(rowIndex > 0){
				$('html, body').animate({
			        scrollTop: entry.offset().top
			    }, 600);
			}
		}
		else{
			container.append(entryWrapper);
		}
		
		if(!skipVisCheck){
			formBuilder.checkVisibility();	
		}
		
		return entryWrapper;
	}
	
	function addTableModel(tableQuestion){
		//create an emptyAnswer field in each measure which contains all answers without any user response
		for(var i = 0; i < tableQuestion.childMeasures.length; i++){
			var childMeasure = tableQuestion.childMeasures[i];
			
			if(childMeasure.tableAnswers == null){
				childMeasure.tableAnswers = [];
			}
			
			//set the empty answers "entry template" array to the answers for this measure
			childMeasure.emptyAnswers = childMeasure.answers;
			childMeasure.answers = [];
		}
		
		tableQuestionLookup[tableQuestion.measureId] = {'childMeasures': tableQuestion.childMeasures, 'answers': tableQuestion.answers};
	}

	function clearTableQuestion(table, noneAnswerId, noneButton, skipVisCheck){		
		var noneInput = $("<input/>")
			.addClass("tableQuestionNA")
			.attr("id", "inp" + noneAnswerId)
			.attr("type", "hidden")
			.val("true");
		
		var noneDiv = $("<div/>")
			.addClass("tableQuestionNA")
			.html("Your answer:   None")
			.hide()
			.append(noneInput);
		
		table.find(".tableQuestionEntryContainer")
			.append(noneDiv)
			.find(".tableQuestionEntry")
				.each(function(){removeEntry($(this), skipVisCheck);});
		
		//hide noneButton
		noneButton.fadeOut(400, function(){noneButton.hide();});
		
		//update the Add button
		noneButton.prevAll(".tableQuestionAdd")
			.val("Change Answer");
		
		noneDiv.fadeIn();
	}
	

	function removeEntry(entry, skipVisCheck){
		entry.animate(
			{height: 0, opacity: 0}, 500, 
			function(){
				var siblings = entry.nextAll();
				var entryName = tableFor(entry).data("entryName");
				
				entry.remove();
				
				//renumber siblings following this deleted entry
				siblings.each(function(){
					var index = $(this).index();
					$(this).find(".entryLabelText").html(entryName + "  " + (index+1));
				});
				
				if(!skipVisCheck){	
					formBuilder.checkVisibility();
				}
			});
	}
	
	function tableFor(component){
		return component.parents("li.surveyQuestion[data='tableQuestion']");
	}
	
	/**
	 * Turns on hightlight for the entry containing the given question/entry
	 * @param component any component in the entry that should be highlighted
	 * @param markClicked if true will mark the entry as touched
	 */
	function highlightEntry(component, markClicked) {
		var container = component.parents(".tableQuestionEntryContainer");
		if(container.size() > 0){
			
			var newHighlightEntry = self.tableEntryFor(component);
			var currentHightEntry = container.find(".highlightTableQuestionEntry");
			
			if(markClicked){
				newHighlightEntry.addClass("wasClicked");
			}
			
			if(newHighlightEntry.attr("id") == currentHightEntry.attr("id")){
				return;
			}
			currentHightEntry.removeClass("highlightTableQuestionEntry");
			newHighlightEntry.addClass("highlightTableQuestionEntry");
		}
	}
	
	function openTableQuestionEntryClicked() {
		$("#dialogTableQuestionEntryClicked").dialog({
			width: 400,
			modal: true,
			draggable: false,
			close:function( event, ui ) {
				$(".ui-dialog").attr('aria-hidden', 'true');
			},				
			buttons: {
	          "Ok": function () {
							$(".ui-dialog").attr('aria-hidden', 'true');
	        	  $(this).dialog('close');
	          }
	        },
	        open: function (e, ui) {
	            $(this).parent().find(".ui-dialog-buttonpane .ui-button")
	                .addClass("customButtonsDialog");
							$(".ui-dialog").attr('aria-hidden', 'false');
	        }
		});
	}
}

/** 
 * Matrix style question builder 
 * Relies on selectBuilder instance.
 **/
function MatrixBuilder(formBuilder, selectBuilder){
	
	/* PUBLIC METHODS */
	
	this.buildOneMatrixQuestion = function(question){
		var questionLI = formBuilder.buildQuestionLI(question);
		var newTable = createMatrixTable(question, questionLI);

		addMatrixRows(newTable, question);
		
		return questionLI;
	};	
	
	this.buildMultiMatrixQuestion = function(question){
		var questionLI = formBuilder.buildQuestionLI(question);
		var newTable = createMatrixTable(question, questionLI);

		addMatrixRows(newTable, question);
		
		return questionLI;
	};	
	
	/* PRIVATE METHODS */
	
	function singleMatrixAnswerFactory(answer, measureId){
		//clear answer text
	    var columnHeader = answer.answerText;
		answer.answerText = "";
		var radio = selectBuilder.buildRadioLI(answer, "grp_"+measureId, columnHeader);
		radio.find("input[type=radio]")
			.change(formBuilder.checkVisibility);
		return radio;
	}

	function multiMatrixAnswerFactory(answer, measureId){
		//clear answer text
		answer.answerText = "";
		return selectBuilder.buildCheckboxLI(answer, measureId);
	}

	/**
	 * Adds all child measures as 
	 */
	function addMatrixRows(table, matrixQuestion){
		
		$.each(matrixQuestion.childMeasures, function(i, question){
			var dataType = question.measureType;
			
			var answerFactory = dataType == "selectOne" ? singleMatrixAnswerFactory : multiMatrixAnswerFactory;
			
			//append row to table
			var row = $("<tr/>")
				.appendTo(table)
				.addClass("matrixQuestion")
				.addClass(table.find("tbody > tr").size() % 2 == 0 ? "matrixOdd" : "matrixEven")
				.attr("data", dataType)
				.attr("id", "que" + question.measureId);
			
			//append question text
			var questionCell = $("<td/>")
				.addClass("matrixQuestionText")
				.addClass("surveyQuestion")
				.html(question.measureText);
			
			row.append(questionCell);
			
			//append cells
			var other = null;
			$.each(question.answers, function(i, answer){
				if(answer.answerType == "other"){
                    other = selectBuilder
                        .buildOtherDiv(answer, question.measureText)
                        .appendTo(questionCell)
                        .change(function(){ matrixOtherTextChange($(this), row); });
					
					//keep other hidden (to be programmatically changed as Other field is changed)
					answerFactory(answer, question.measureId)
						.hide()
						.appendTo(questionCell);
				}
				else if(answer.answerType == "none" && dataType == "selectOne"){
					//This is a special case where we are using the non field to make a question with an other field optional
					answerFactory(answer, question.measureId)
						.hide()
						.appendTo(questionCell);
				}
				else{
					$("<td/>")
						.addClass("matrixCell")
						.append(answerFactory(answer, question.measureId))
						.appendTo(row);
				}
			});
			
			if(other != null){
				//if this is an Other row then hide any None option
				row.find("li[data='selectMultiInt-None']").hide();		
				
				//initialize other state
				matrixOtherTextChange(other, row);
			}
			else if(question.isRequired==true) {
					row.addClass("matrixQuestionText surveyQuestion isRequired");
					questionCell.append("<span class='mqRequired'>*</span>");
			}
		});
	}

	function matrixOtherTextChange(input, row){
		var inputVal = $(input).find("input[type='text']").val(); 
		
		if(row.attr("data") == "selectOne"){ // selectOne type
			row.find("td.matrixQuestionText li.selectNone")
				.find("input[type='radio']")
				.prop("checked", inputVal == "");
		}
		else{ // selectMulti
			var noneCheckbox = row.find("li[data='selectMultiInt-None'] div.checkSwitch");
			//var otherCheckbox = row.find("li[data='selectMultiInt-Other'] div.checkSwitch");
			if(inputVal == ""){ 
				//if no value is in text box turn None on (since this question is optional)
				selectBuilder.checkSwitchOn(noneCheckbox);
			}
			else{
				//if the user has entered text then turn None off and turn Other on
				selectBuilder.checkSwitchOff(noneCheckbox);
			}
		}
	}

	/**
	 * Creates a new table that will be holding the matrix questions.
	 * @returns the table ref of the new table that was created.
	 */
	function createMatrixTable(question, container){			
		var newTable = $("<table/>")
			.addClass("matrixQuestion")
			.appendTo(container);
	
		//Create and append header row
		var headers = $("<tr/>")
			.appendTo($("<thead/>").appendTo(newTable))
			.append($("<th/>"));					  
		
		$.each(question.childMeasures[0].answers, function(i, answer){
			if(answer.answerType != "other"){
				$("<th/>")
					.addClass("matrixQuestion")
					.html(answer.answerText)
					.appendTo(headers);
			}
		});
		
		return newTable.append($("<tbody/>"));
	}

	function getMatrixQuestionName(question){
		var name = "";
		for(var i = 0; i < question.answers.length; i++) {
			if(question.answers[i].answerType != "other" && question.answers[i].answerType != "none"){
				name += question.answers[i].answerText + "_";
			}
		}
		return name;
	}
}