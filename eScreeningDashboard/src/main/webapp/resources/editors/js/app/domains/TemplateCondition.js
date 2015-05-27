var EScreeningDashboardApp = EScreeningDashboardApp || { models: EScreeningDashboardApp.models || EScreeningDashboardApp.namespace("gov.va.escreening.models") };

(function () {
	'use strict';
	
	function TemplateCondition(jsonConfig) {
	    this.guid = EScreeningDashboardApp.getInstance().guid();
	    this.connector;
	    this.left;
	    this.operator;
	    this.right;
	    this.conditions = []
	    
	    if(jsonConfig != null) {
	        this.connector = jsonConfig.connector;
	        this.left = new Operand(leftPrototype, jsonConfig.left);
	        this.operator = jsonConfig.operator;
	        this.right = new Operand(rightPrototype, jsonConfig.right);
	    
	        if(jsonConfig.conditions){
	        	jsonConfig.conditions.forEach(function(conditionConfig){
	        		this.conditions.push(new EScreeningDashboardApp.models.TemplateCondition(conditionConfig));
	        	}, this);
	        }
	    }
	    
	    if(this.left == null){
	    	this.left = new Operand(leftPrototype);
	    }
	    
	    if(this.right == null){
	        this.right = new Operand(rightPrototype);
	    }
	
	    this.autoGenerateFields = function() {
			this.summary = this.connector + " " + (this.left.content.displayName || this.left.content.name) + " " + this.operator + " " + this.right.content;
		}
	};
	
	function Operand(prototype, jsonConfig) {
	    this.type = prototype.type;
	    this.content = angular.copy(prototype.content);

	    if(jsonConfig != null) {
	        this.type = jsonConfig.type;
	        
	        if (this.type === "text") {
	            this.content = jsonConfig.content;
	        } else if (this.type === "var") {
	            this.content = new EScreeningDashboardApp.models.AssessmentVariable(jsonConfig.content);
	        }
	    }
	};
	
	var leftPrototype =  {
	        type: "var",
	        content: {}
	};
	var rightPrototype =  {
	        type: "text",
	        content: ""
	};
	
	EScreeningDashboardApp.models.TemplateCondition = TemplateCondition; 
	EScreeningDashboardApp.models.TemplateCondition.OrConditionMinimumConfig = new TemplateCondition({connector:"or"});
	EScreeningDashboardApp.models.TemplateCondition.AndConditionMinimumConfig = new TemplateCondition({connector:"and"});
	
})();






