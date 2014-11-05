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
    var myparent = parent;
    this.guid = EScreeningDashboardApp.getInstance().guid();
    this.section;
    this.name;
    this.type;
    this.summary;
    this.left;
    this.operator;
    this.conditions;
    this.content;
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
        this.contents = (Object.isArray(jsonConfig.contents))? jsonConfig.contents: [];
        this.right = (Object.isDefined(jsonConfig.right))? new EScreeningDashboardApp.models.TemplateRightVariable(jsonConfig.right): null;

        if(Object.isDefined(jsonConfig.children)){
            angular.copy(jsonConfig.children, this.children);
            var self = this;
            this.children.forEach(function(childData){
                angular.extend(childData, new EScreeningDashboardApp.models.TemplateBlock(childData, self));
            });
        }
    }
    
	function createTextContent(text){
		return { type: "text",
			content: text };
	}

	/* TODO: Decouple from TemplateVariableContent */
	function createVarContent(variable){
		return { type: "var",
			content: new EScreeningDashboardApp.models.TemplateVariableContent(variable)};
	}

	function transformTextContent(variableHash){

		if(this.type == "text"){
			var contents = [];
			var fragments = this.content.split(/<img[^>]+id="(\d+)"[^>]+>/);

			fragments.forEach(function(frag){
				var varName = variableHash[frag];

				var content = (varName) ? createVarContent(varName) : createTextContent(frag);
				contents.push(content);
			});
			this.contents = contents;
			delete(this.content);
		}
	}

	function autoGenerateFields(variableHash){

		var block = this;

		if(block.type == "text"){
			block.summary = "";
			block.name = Object.isDefined(block.name) ? block.name :"";
			var setTitle = this.name.trim() == "";

			for(var i=0; ((setTitle && block.name < 10) || block.summary.length < 50)
				&&  i< block.contents.length; i++){
				var blockContent = block.contents[i];
				if(blockContent.type == "text"){
					block.summary += blockContent.content;

					if(setTitle && block.name.length < 10){
						var neededChars = 10 - block.name.length;
						block.name += blockContent.content.replace(/<\/*[^>]/, "").slice(0, neededChars);
					}
				}
				if(blockContent.type == "var"){
					var varName = blockContent.content.getName();
					block.summary += varName;

					if(setTitle && block.name.length < 10){
						block.name += varName;
					}
				}
			}
		}

		if (block.type !== 'text' && block.type !== 'else') {
			if (!block.name) block.name = block.type + "_" + (block.left.content.displayName || block.left.content.name) + "_" + block.operator + "_" + block.right.content;
			block.summary = (block.left.content.displayName || block.left.content.name) + " " + block.operator + " " + block.right.content;
			block.conditions.forEach(function (condition) {
				if (!condition.summary) {
					condition.autoGenerateFields();
					block.summary += " " + condition.summary;
				}
			});
		}

		if(Object.isDefined(this.children)){
			this.children.forEach(function(block){ transformTextContent.call(block, variableHash); });
		}
	}

	/* TODO: Decouple from TemplateVariableContent */
	function sentTextContent(){
		this.content = "";

		if(this.contents){
			this.contents.forEach(function(content){
				if(content.type == "text"){
					//horrible naming.. sorry no time
					this.content += content.content;
				}
				if(content.type == "var"){
					var varObj = new EScreeningDashboardApp.models.TemplateVariableContent(content.content);

					this.content += createContent(varObj) + '&nbsp;';
				}
			}, this);
		}
	}
	
	//TODO: this should be moved into a place where both this and the wysiwyg custom button code can access it (see main.js)
	function createContent(varObj){
	    return '<img ' +
        'class="ta-insert-variable text-info" ' +
        'id="' + varObj.id + '" ' +
        'src="" ' +
        'ta-insert-variable="' + varObj.id + '" ' +
        'alt="(' + varObj.getName() +
        ')" ' +
        'title="(' + varObj.getName() + ')" ' +
        'contenteditable="false" ' +
    '/>';
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
    
	this.autoGenerateFields = autoGenerateFields;
	this.sentTextContent = sentTextContent;
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

