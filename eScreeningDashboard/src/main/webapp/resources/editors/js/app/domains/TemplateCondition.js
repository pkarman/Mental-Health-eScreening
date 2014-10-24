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
 * Constructor method for the TemplateCondition class.  The properties of this class can be initialized with
 * the jsonConfig.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behavior and state
 *              information about the user.
 * @param {String}  jsonConfig  Represents the JSON representation of an TemplateCondition object.
 * @constructor
 * @author Tonté Pouncil
 */
EScreeningDashboardApp.models.TemplateCondition = function (jsonConfig) {
    this.templateConnector;
    this.templateLeftVariable;
    this.templateOperator;
    this.templateRightVariable;

    if(Object.isDefined(jsonConfig)) {
        this.templateConnector = (Object.isDefined(jsonConfig.templateConnector))? jsonConfig.templateConnector: null;
        this.templateLeftVariable = (Object.isDefined(jsonConfig.templateLeftVariable))? jsonConfig.templateLeftVariable: null;
        this.templateOperator = (Object.isDefined(jsonConfig.templateOperator))? jsonConfig.templateOperator: null;
        this.templateRightVariable = (Object.isDefined(jsonConfig.templateRightVariable))? jsonConfig.templateRightVariable: null;
    }

    this.toString = function () {
        return "TemplateCondition [connector: " + this.templateConnector +
            ", left variable: " + this.templateLeftVariable +
            ", operator: " + this.templateOperator +
            ", right operator: " + this.templateOperator + "]";
    };
};
EScreeningDashboardApp.models.TemplateCondition.create = function () {
    return new EScreeningDashboardApp.models.TemplateCondition();
};