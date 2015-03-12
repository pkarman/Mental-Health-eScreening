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
 *
 * @static
 * @type {*|EScreeningDashboardApp.models|*|EScreeningDashboardApp.models|Object|*|Object|*}
 */
EScreeningDashboardApp.models = EScreeningDashboardApp.models || EScreeningDashboardApp.namespace("gov.va.escreening.models");

/**
 * Constructor method for the TemplateBlock class.  The properties of this class can be initialized with
 * the jsonConfig.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behavior and state
 *              information about the user.
 * @param {JSON}  jsonConfig  Represents the JSON representation of an TemplateBlock object.
 * @param {Object} parent optionally the parent block object
 * @constructor
 * @author Tonté Pouncil
 */
EScreeningDashboardApp.models.TemplateBlock = function (jsonConfig, parent) {
    var self = this;
    var myparent = parent;
    var CLEAN_SUMMARY_REG = /(<\/*[^>]+>)|(&#*[a-zA-Z0-9]+;)/g;
    var TEXT_NAME_LENGTH = 30;
    
    this.guid = EScreeningDashboardApp.getInstance().guid();
    this.section;
    this.name;
    this.type;
    this.summary;
    this.left;
    this.operator;
    this.conditions = [];
    this.content = '';
    this.right;
    this.children = [];
    this.contents = [];

    if(jsonConfig){
        //this.guid = (Object.isDefined(jsonConfig.guid))? jsonConfig.guid: this.guid;
        this.section = (Object.isDefined(jsonConfig.section))? jsonConfig.section: null;
        this.name = (Object.isDefined(jsonConfig.name))? jsonConfig.name: null;
        this.type = (Object.isDefined(jsonConfig.type))? jsonConfig.type: null;
        this.summary = (Object.isDefined(jsonConfig.summary))? jsonConfig.summary: null;
        this.left = (Object.isDefined(jsonConfig.left))? new EScreeningDashboardApp.models.TemplateLeftVariable(jsonConfig.left): null;
        this.operator = (Object.isDefined(jsonConfig.operator))? jsonConfig.operator : null;
        this.conditions = (Object.isArray(jsonConfig.conditions))? EScreeningDashboardApp.models.TemplateCondition.createConditionsArray(jsonConfig.conditions): [];
        this.content = (Object.isDefined(jsonConfig.content))? jsonConfig.content: '';
        this.right = (Object.isDefined(jsonConfig.right))? new EScreeningDashboardApp.models.TemplateRightVariable(jsonConfig.right): null;

		if (jsonConfig.table) {
			this.table = jsonConfig.table;
		}

        if(Object.isArray(jsonConfig.contents)){
            jsonConfig.contents.forEach(function(blockData){
                var contentObj = angular.copy(blockData);
                if(contentObj.type == "var"){
                    contentObj.content = new EScreeningDashboardApp.models.AssessmentVariable(contentObj.content);
                }
                self.contents.push(contentObj);   
            });
        }
        
        if(Object.isDefined(jsonConfig.children)){
            angular.copy(jsonConfig.children, this.children);
            this.children.forEach(function(childData){
                angular.extend(childData, new EScreeningDashboardApp.models.TemplateBlock(childData, self));
            });
        }
    }
    
    function reset(){
        this.left = new EScreeningDashboardApp.models.TemplateLeftVariable(EScreeningDashboardApp.models.TemplateBlock.RightLeftMinimumConfig.left);
        this.operator = null;
        this.right = new EScreeningDashboardApp.models.TemplateRightVariable(EScreeningDashboardApp.models.TemplateBlock.RightLeftMinimumConfig.right);
        this.conditions = [];
        this.content = '';
        this.children = [];
        this.contents = [];

		if (this.type === 'table') {
			this.table = { type : 'var' };
		}
    }

    function swapNbspForSpaces(text){
		if(angular.isUndefined(text)){
			return text;
		}
        
        var fragments = text.split(/(<[^>]+>)/);
        var result = "";
        fragments.forEach(function(frag){
            var content = null;
            if(frag.indexOf("<") != 0){
                result += frag.replace(/\s/g, "&nbsp;"); 
            }
            else{
                result += frag;
            }
        });
        return result;
    }
    
    
	function transformTextContent(TemplateBlockService){

		if(this.type == "text"){
		    //this.content = swapNbspForSpaces(this.content);
			this.contents = TemplateBlockService.parseIntoContents(this.content);
			delete(this.content);
		}
	}
	
	function setTextContent(TemplateBlockService){
        this.content = TemplateBlockService.joinContents(this.contents);
    }

	//TODO: If we have domain objects for each block type then we can move this logic into each of them.
	function autoGenerateFields(){
		var block = this;

		if(block.type == "text"){
			block.summary = "";
			block.name = Object.isDefined(block.name) ? block.name :"";
			var setTitle = this.name.trim() == "";

			for(var i=0; ((setTitle && block.name < TEXT_NAME_LENGTH) || block.summary.length < 100)
				&&  i< block.contents.length; i++){
				var blockContent = block.contents[i];
				if(blockContent.type == "text"){
                    var text = blockContent.content.replace(/&nbsp;|<\s*br\s*\/*>/g, " ");
                    text = text.replace(CLEAN_SUMMARY_REG, "");
                    if(block.summary == ""){
                        text = text.replace(/^\s+/, "");
                    }
					block.summary += text;

					if(setTitle && block.name.length < TEXT_NAME_LENGTH){
						var neededChars = TEXT_NAME_LENGTH - block.name.length;
						block.name += text.substring(0, neededChars);
					}
				}
				else if(blockContent.type == "var"){
					var varName = "(" + blockContent.content.getName().replace(/\s+/g, "_") + ")";
					block.summary += varName;

					if(setTitle && block.name.length < TEXT_NAME_LENGTH){
						block.name += varName;
					}
				}
				block.summary += " ";
				if(block.name.length+1 < TEXT_NAME_LENGTH){
    				block.name += " ";
				}
			}
		}
		else if(block.type == 'table'){
			if(angular.isDefined(block.table) 
				&& angular.isDefined(block.table.content)){
				
				block.name = angular.isDefined(block.table.content.name) ?  block.table.content.name : "table_" + block.table.content.id;
				block.summary = block.table.content.displayName;
			} 
		}
		else if (block.type !== 'else') {
		    var rightContentSummary = "";
		    var rightContentName = "";
		    
		    if(angular.isDefined(block.right) && angular.isDefined(block.right.content)){
		        var rightContent = block.right.content;
		        if(angular.isArray(block.measureAnswers)){
		            block.measureAnswers.some(function(answer){
		                if(answer.measureAnswerId === rightContent){
		                    if(angular.isDefined(answer.answerText) && answer.answerText.trim().length > 0){
		                        rightContent = answer.answerText;
		                    }
		                    else if(angular.isDefined(answer.exportName) && answer.exportName.trim().length > 0){
		                        rightContent = answer.exportName;    
		                    }
		                    else if(angular.isDefined(answer.otherExportName) && answer.otherExportName.trim().length > 0){
		                        rightContent = answer.otherExportName;
		                    }
		                    else if(angular.isDefined(answer.calculationValue) && answer.calculationValue.trim().length > 0){
		                        rightContent = answer.calculationValue;
		                    }
		                    return true;
		                }
		                return false;
		            });
		        }
		        rightContentSummary = " " + rightContent;
		        rightContentName = "_" + rightContent;
		    } 
			if (!block.name) block.name = block.type + "_" + (block.left.content.displayName || block.left.content.name) + "_" + block.operator + rightContentName;
			block.summary = (block.left.content.displayName || block.left.content.name) + " " + block.operator + rightContentSummary;
			block.conditions.forEach(function (condition) {
				if (!condition.summary) {
					condition.autoGenerateFields();
					block.summary += " " + condition.summary;
				}
			});
		}
	}
	
	
	//TODO: If we have domain objects for each block type then we can move this logic into each of them.
	/**
	 * Iterates over this block's contents and the contents of its children and 
	 * collects all assessment variables into an array. There can be duplicates.
	 * @return an array of variables (will never be null, but can be empty)
	 */
	function getVariables(){
		var variables = [];
		var block = this;

		if(block.type == "text"){
			if(block.contents){
				 block.contents.forEach(function(content){
					 if(content.type == "var"){
						 variables.push(content.content);
					 }
				 });
			}
		}
		else if(block.type == 'table'){
			if(angular.isDefined(block.table) 
					&& angular.isDefined(block.table.content)){
				variables.push(block.table.content);
			}
		}
		else if (block.type == 'if' || block.type == 'elseif') {
			if(angular.isDefined(block.left) 
					&& angular.isDefined(block.left.content)
					&& angular.isDefined(block.left.content.measureTypeId)){
				variables.push(block.left.content);
			}
			if(angular.isDefined(block.right) 
					&& angular.isDefined(block.right.content)
					&& angular.isObject(block.right.content)
					&& angular.isDefined(block.right.content)
					&& angular.isDefined(block.right.content.measureTypeId)){
				variables.push(block.right.content);
			}
		}
		else if(block.type !== 'else'){
			throw block.type + " is an unsupported type.";
		}
		
		//add children
		if(block.children){
			block.children.forEach(function(child){
				variables.push.apply(variables, child.getVariables());
			})
		}
		
		return variables;
	}

    function toString() {
        return "TemplateBlock [guid: " + guid +
            ",section: " + this.section +
            ", name: " + this.name +
            ", type: " + this.type +
            ", summary: " + this.summary +
            ", leftVariable: " + this.left +
            ", operator: " + this.operator +
            ", rightVariable: " + this.right +
            ", conditions: " + this.conditions + "]";
    }
    
    function lastText(){
        return this.getLast("text");
    }
    
    function lastElseIf(){
        return this.getLast("elseif");
    }
    
    function getElse(){
        return this.getLast("else");
    }
    
    function getLast(typeName){
        var lastFound = {index: -1, block: null};
        if(this.children && angular.isArray(this.children)){
            this.children.forEach(function(child, i){
                 if(child.type == typeName){
                     lastFound.index = i;
                     lastFound.block = child;
                 }
            });
        }
        return lastFound;
    }
    
    /**
     * @return the index of the given block in the list of this blocks children
     */
    function indexOf(block){
        var index = -1;
        if(this.children && angular.isArray(this.children)){
            this.children.every(function(child, i){
                 if(child.equals(block)){
                     index = i;
                 }
                 return index == -1;
            });
        }
        return index;
    }
    
    /**
     * @return the index of this block in it's parent's children array
     */
    function index(){
        if(!this.getParent() || !angular.isArray(this.getParent().children)){
            return -1;
        }
        return this.getParent().indexOf(this);
    }
    
    function equals(otherBlock){
        return otherBlock && this.section === otherBlock.section;
    }
    
    this.reset = reset;
	this.autoGenerateFields = autoGenerateFields;
	this.setTextContent = setTextContent;
	this.toString = toString;
	this.transformTextContent = transformTextContent;
	this.getLast = getLast;
	this.lastText = lastText;
	this.lastElseIf = lastElseIf;
	this.getElse = getElse;
	this.indexOf = indexOf;
	this.index = index;
	this.equals = equals;
	//keeping parent as a private field to avoid cyclic references 
	this.getParent = function(){return myparent};
	this.setParent = function(newParent){ myparent = newParent; }
	this.getVariables = getVariables;
	
};
EScreeningDashboardApp.models.TemplateBlock.createTemplateBlockArray = function(jsonTemplateBlocksConfig) {
    var templateBlocks = [];

    jsonTemplateBlocksConfig.forEach(function(jsonTemplateBlockConfig) {
        templateBlocks.push(new EScreeningDashboardApp.models.TemplateBlock(jsonTemplateBlockConfig));
    });

    return templateBlocks;
}
EScreeningDashboardApp.models.TemplateBlock.RightLeftMinimumConfig = {
    left: {
        type: "var",
        content: {}
    },
    right: {
        type: "text",
        content: ""
    }
};

