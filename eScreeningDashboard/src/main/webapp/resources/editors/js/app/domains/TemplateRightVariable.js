/**
 * Created by pouncilt on 10/23/14.
 */
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
 * Constructor method for the TemplateRightVariable class.  The properties of this class can be initialized with
 * the jsonConfig.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behavior and state
 *              information about the user.
 * @param {String}  jsonConfig  Represents the JSON representation of an TemplateRightVariable object.
 * @constructor
 * @author Tonté Pouncil
 */
EScreeningDashboardApp.models.TemplateRightVariable = function (jsonConfig) {
    this.type;
    this.templateContent;

    if(Object.isDefined(jsonConfig)) {
        this.type = (Object.isDefined(jsonConfig.type))? jsonConfig.type: null;
        this.templateContent = (Object.isDefined(jsonConfig.templateContent))? jsonConfig.templateContent: null;
        if(!Object.isDefined(this.templateContent)) {
            if (this.type === "text") {
                this.templateContent = new EScreeningDashboardApp.models.TemplateTextContent.create(jsonConfig.templateContent);
            } else if (this.type === "var") {
                this.templateContent = new EScreeningDashboardApp.models.TemplateVariableContent.create(jsonConfig.templateContent);
            }
        }
    }

    this.toString = function () {
        return "TemplateRightVariable [type: " + this.type + ", content: " + this.templateContent + "]";
    };
};
EScreeningDashboardApp.models.TemplateRightVariable.create = function (factoryConfig){
    var templateLeftVariable = new EScreeningDashboardApp.models.TemplateRightVariable(factoryConfig);

    if(factoryConfig.templateContent){

    }

    return templateLeftVariable;
};