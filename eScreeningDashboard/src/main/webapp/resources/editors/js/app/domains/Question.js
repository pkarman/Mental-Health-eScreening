 /**
 * Represents the application api.  If the variable is already defined use it,
 * otherwise create an empty object.
 *
 * @type {EScreeningDashboardApp|*|EScreeningDashboardApp|*|{}|{}}
 */
var EScreeningDashboardApp = EScreeningDashboardApp || {};
/**
 * Represents the application static variable. Use existing static variable, if one already exists,
 * otherwise create a new one.
 *visible
 * @static
 * @type {*|EScreeningDashboardApp.models|*|EScreeningDashboardApp.models|Object|*|Object|*}
 */
EScreeningDashboardApp.models = EScreeningDashboardApp.models || EScreeningDashboardApp.namespace("gov.va.escreening.models");
/**
 * Constructor method for the Answer class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behaviour and state
 *              information about the user.
 * @param {String}  jsonQuestionObject  Represents the JSON representation of a Answer object.
 * @constructor
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
EScreeningDashboardApp.models.Question = function (jsonQuestionObject) {
    var that = this,
        id = (Object.isDefined(jsonQuestionObject) && Object.isDefined(jsonQuestionObject.id))? jsonQuestionObject.id : -1,
        text = (Object.isDefined(jsonQuestionObject) && Object.isDefined(jsonQuestionObject.text))? jsonQuestionObject.text : null,
        type = (Object.isDefined(jsonQuestionObject) && Object.isDefined(jsonQuestionObject.type))? jsonQuestionObject.type : null,
        displayOrder = (Object.isDefined(jsonQuestionObject) && Object.isDefined(jsonQuestionObject.displayOrder))? jsonQuestionObject.displayOrder : null,
        required = (Object.isDefined(jsonQuestionObject) && Object.isDefined(jsonQuestionObject.required) && Object.isBoolean(jsonQuestionObject.required)) ? jsonQuestionObject.required : false,
        ppi = (Object.isDefined(jsonQuestionObject) && Object.isDefined(jsonQuestionObject.ppi) && Object.isBoolean(jsonQuestionObject.ppi)) ? jsonQuestionObject.ppi : false,
        visible = (Object.isDefined(jsonQuestionObject) && Object.isDefined(jsonQuestionObject.visible) && Object.isBoolean(jsonQuestionObject.visible))? visible : false,
        variableName = (Object.isDefined(jsonQuestionObject) && Object.isDefined(jsonQuestionObject.variableName))? jsonQuestionObject.variableName : null,
        answers = (Object.isDefined(jsonQuestionObject) && Object.isArray(jsonQuestionObject.answers))? EScreeningDashboardApp.models.AnswersTransformer.transformJSONPayload({"answers": jsonQuestionObject.answers}): [],
        validations = (Object.isDefined(jsonQuestionObject) && Object.isDefined(jsonQuestionObject.validations) && Object.isArray(jsonQuestionObject.validations))? EScreeningDashboardApp.models.ValidationsTransformer.transformJSONPayload({"validations": jsonQuestionObject.validations}): [],
        childQuestions = (Object.isDefined(jsonQuestionObject) && Object.isDefined(jsonQuestionObject.childQuestions) && Object.isArray(jsonQuestionObject.childQuestions)) ? EScreeningDashboardApp.models.QuestionsTransformer.transformJSONPayload({"questions": jsonQuestionObject.childQuestions}) : [],
        tableAnswers = (Object.isDefined(jsonQuestionObject) && Object.isDefined(jsonQuestionObject.tableAnswers) && Object.isArray(jsonQuestionObject.tableAnswers)) ? EScreeningDashboardApp.models.TableAnswersTransformer.transformJSONPayload({"tableAnswers": jsonQuestionObject.tableAnswers}) : [];

        this.escapeTags = function(string){
        	string = string.replace(/</g, 'tag');
        	string = string.replace(/>/g, 'endtag;');
        	string = prettifyStr(text);
        	alert('string:: ' + string);
        	return string;
        };
        
        var unescapeTags = function(string){
        	string = string.replace(/tag/g, '<');
        	string = string.replace(/endtag/g, '>');
        	return string;
        };
        
        var prettifyStr = function(text) {
      	  var e = {
      			    lsquo:  '\u2018',
      			    rsquo:  '\u2019',
      			    ldquo:  '\u201c',
      			    rdquo:  '\u201d',
      			  };
      			  var subs = [
      			    {pattern: "(^|[\\s\"])'",      replace: '$1' + e.lsquo},
      			    {pattern: '(^|[\\s-])"',       replace: '$1' + e.ldquo},
      			    {pattern: "'($|[\\s\"])?",     replace: e.rsquo + '$1'},
      			    {pattern: '"($|[\\s.,;:?!])',  replace: e.rdquo + '$1'}
      			  ];
      			  for (var i = 0; i < subs.length; i++) {
      			    var sub = subs[i];
      			    var pattern = new RegExp(sub.pattern, 'g');
      			    text = text.replace(pattern, sub.replace);
      			  };
      			  return text;
      			};

    var generateJsonStringForAnswers = function (){
        var answersJson = "[";


        answers.forEach(function (answer) {
            answersJson += answer.toJSON() + ",";
        });

        if (answers.length > 0) answersJson = answersJson.slice(0, answersJson.length - 1);

        answersJson += "]";

        return answersJson;
    };

    var generateAnswerUIObjects = function(){
    	var answerUIObjects = [];

        escapeTags(answers).forEach(function(answer){
            answerUIObjects.push(answer.toUIObject());
        });

    	return answerUIObjects;
    };

    var generateJsonStringForValidations = function () {
        var validationJsonArray = "[";

        validations.forEach(function (validation) {
            validationJsonArray += validation.toJSON() + ",";
        });

        if (validations.length > 0) validationJsonArray = validationJsonArray.slice(0, validationJsonArray.length-1);
        validationJsonArray += "]";

        return validationJsonArray;
    };
    
    var generateValidationUIObjects = function(){
    	var validationUIObjects = [];
    	
    	escapeTags(validations).forEach(function(validation){
            validationUIObjects.push(validation.toUIObject());
        });

    	return validationUIObjects;
    };

    var generateJsonStringForChildQuestions = function () {
        var childQuestionJsonArray = "[";

        childQuestions.forEach(function (childQuestion) {
            childQuestionJsonArray += childQuestion.toJSON() + ",";
        });

        if (childQuestions.length > 0) childQuestionJsonArray = childQuestionJsonArray.slice(0, childQuestionJsonArray.length-1);
        childQuestionJsonArray += "]";

        return childQuestionJsonArray;
    };
    
    var generateChildQuestionUIObjects = function(){
    	var childQuestionsUIObjects = [];


        childQuestions.forEach(function(childQuestion){
            childQuestionsUIObjects.push(childQuestion.toUIObject());
        });

    	return childQuestionsUIObjects;
    };

    var generateJsonStringForTableAnswers = function (){
        var tableAnswerJsonArray = "[";

        tableAnswers.forEach(function (answers) {
            if(Object.isDefined(answers) && Object.isArray(answers)) {
                if(answers.length > 0) {
                    var answerJsonArray = "[";
                    answerJsonArray += generateJsonStringForAnswers(answers) + ",";
                    if (answerJsonArray.length > 0) answerJsonArray = answerJsonArray.slice(0, answerJsonArray.length - 1);
                    answerJsonArray += "]";
                }
            }

            tableAnswerJsonArray += answerJsonArray;
        });

        if (tableAnswers.length > 0) {
            tableAnswerJsonArray = tableAnswerJsonArray.slice(0, tableAnswerJsonArray.length-1);
        } else {
            tableAnswerJsonArray += "[]";
        }
        tableAnswerJsonArray += "]";

        return tableAnswerJsonArray;
    };

    var generateTableAnswerUIObjects = function(){
    	var tableAnswerUIObjects = [];

        escapeTags(tableAnswers).forEach(function (answers) {
            var answerUIObjects = [];

            answers.forEach(function (answer) {
                answerUIObjects.push(answer.toUIObject());
            });

            tableAnswerUIObjects.push(answerUIObjects);
        });

    	return tableAnswerUIObjects;
    };
    


    this.getSelf = function(){
    	return this;
    };

    this.getId = function(){
        return id;
    };

    this.getText = function() {
        return text;
    };

    this.getType = function() {
        return type;
    };

    this.getDisplayOrder = function() {
        return displayOrder;
    };

    this.getRequired = function() {
        return required;
    };

    this.isPPI = function (){
        return ppi;
    };

    this.getVisible = function() {
        return visible;
    };

    this.getVariableName = function () {
        return variableName;
    };
    
    this.getAnswers = function(){
    	return answers;
    };

    this.getValidations = function(){
        return validations;
    };
    
    this.getChildMeasures = function(){
    	return childQuestions;
    };
    
    this.getTableAnswers = function(){
    	return tableAnswers;
    };

    this.toString = function() {
        return "Question{id: " + id + ", name: " + name + ", text: " + text + ", type: " + type + ", ppi: " + ppi +
            ", displayOrder: " + displayOrder + ", required: " + required + ", visible: " + visible + ", variableName: " + variableName +
            ", answers: " + answers.toString() + ", validations: " + validations.toString() +
            ", childQuestions:" + childQuestions.toString() + ", tableAnswers: " + tableAnswers.toString() + "]}";
    };

    this.toJSON = function (serializeCollections) {
        serializeCollections = (Object.isDefined(serializeCollections) && Object.isBoolean(serializeCollections))? serializeCollections : true;
        var jsonId = (id != null && id > 0)? id : null,
            jsonText = (Object.isDefined(text))? "\"" + this.escapeTags(text) + "\"": null,
            jsonType = (Object.isDefined(type))? "\"" + type + "\"" : null,
            jsonDisplayOrder = (Object.isDefined(displayOrder))? displayOrder: null,
            jsonVisible = (Object.isDefined(visible))? visible : false,
            jsonIsRequired = (Object.isDefined(required))? required : false,
            jsonIsPpi = (Object.isDefined(ppi))? ppi : false,
            jsonVariableName = (Object.isDefined(variableName))? "\"" + variableName + "\"" : null,
            jsonAnswers =  (serializeCollections)? "\"answers\": " + generateJsonStringForAnswers() : "",
            jsonValidations = (serializeCollections)? ",\"validations\": " + generateJsonStringForValidations(): "",
            jsonChildMeasures = (serializeCollections) ? ",\"childQuestions\": " + generateJsonStringForChildQuestions() : "",
            jsonTableAnswers = (serializeCollections) ? ",\"tableAnswers\": " + generateJsonStringForTableAnswers() : "",
            json = "{" +
                "\"id\": " + jsonId + "," +
                "\"text\": " + jsonText + "," +
                "\"type\": " + jsonType + "," +
                "\"displayOrder\": " + jsonDisplayOrder + "," +
                "\"visible\": " + jsonVisible + "," +
                "\"required\": " + jsonIsRequired + "," +
                "\"ppi\": " + jsonIsPpi + "," +
                "\"variableName\": " + jsonVariableName + "," +
                jsonAnswers + 
                jsonValidations + 
                jsonChildMeasures + 
                jsonTableAnswers +
            "}";

        return json;
    };
    
    this.escapeTags = function(string){
    	string = string.replace(/<\/?[^>]+(>|$)/g, "");
    	string = string.replace(/"/g,"\"");
    	
    	return string;
    };
    
    this.unescapeTags = function(string){
    	string = string.replace(/opentag/g, '<');
    	string = string.replace(/closetag/g, '>');
    	return string;
    };
    
    
    this.toUIObject = function(){
    	var questionUIObject = JSON.parse(this.escapeTags(this.toJSON()));
    	return questionUIObject;
    };
};
EScreeningDashboardApp.models.Question.toUIObjects = function(questions) {
     var surveyUIObjects = [];

     if(Object.isDefined(questions) && Object.isArray(questions)) {
         questions.forEach(function(question) {
             surveyUIObjects.push(question.toUIObject());
         });
     }

     return surveyUIObjects;
 };