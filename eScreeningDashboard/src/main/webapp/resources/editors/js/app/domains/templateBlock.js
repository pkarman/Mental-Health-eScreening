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
 * @param {String}  jsonConfig  Represents the JSON representation of an TemplateBlock object.
 * @constructor
 * @author Tonté Pouncil
 */
EScreeningDashboardApp.models.TemplateBlock = function (jsonConfig) {
    this.section;
    this.name;
    this.type;
    this.summary;
    this.templateLeftVariable;
    this.templateOperator;
    this.templateConditions;
    this.templateRightVariable;

    if(jsonConfig){
        this.section = (Object.isDefined(jsonConfig.section))? jsonConfig.section: null;
        this.name = (Object.isDefined(jsonConfig.name))? jsonConfig.name: null;
        this.type = (Object.isDefined(jsonConfig.name))? jsonConfig.name: null;
        this.summary = (Object.isDefined(jsonConfig.summary))? jsonConfig.summary: null;
        this.templateLeftVariable = (Object.isDefined(jsonConfig.templateLeftVariable))? jsonConfig.templateLeftVariable: null;
        this.templateOperator = (Object.isDefined(jsonConfig.templateOperator))? jsonConfig.templateOperator: null;
        this.templateConditions = (Object.isArray(jsonConfig.templateConditions))? jsonConfig.templateConditions: [];
        this.templateRightVariable = (Object.isDefined(jsonConfig.templateRightVariable))? jsonConfig.templateRightVariable: null;
    }


    this.toString = function () {
        return "TemplateBlock [section: " + this.section +
            ", name: " + this.name +
            ", type: " + this.type +
            ", summary: " + this.summary +
            ", leftVariable: " + this.templateLeftVariable +
            ", operator: " + this.templateOperator +
            ", rightVariable: " + this.templateRightVariable +
            ", conditions: " + this.templateConditions + "]";
    };
};

EScreeningDashboardApp.models.TemplateBlock.create = function () {
    return new EScreeningDashboardApp.models.TemplateBlock();
};