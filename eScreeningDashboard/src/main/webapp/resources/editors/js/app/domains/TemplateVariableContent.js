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
 * Constructor method for the TemplateVariableContent class.  The properties of this class can be initialized with
 * the jsonConfig.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behavior and state
 *              information about the user.
 * @param {String}  jsonConfig  Represents the JSON representation of an TemplateVariableContent object.
 * @constructor
 * @author Tonté Pouncil
 */
EScreeningDashboardApp.models.TemplateVariableContent = function (jsonConfig) {
    this.id;
    this.typeId;
    this.type;
    this.name;
    this.displayName;
    this.answerId;
    this.measureId;
    this.measureTypeId;
    this.templateTransformations;

    if(Object.isDefined(jsonConfig)) {
        this.id = (Object.isDefined(jsonConfig.id))? jsonConfig.id: null;
        this.typeId = (Object.isDefined(jsonConfig.typeId))? jsonConfig.typeId: null;
        this.type = (Object.isDefined(jsonConfig.type))? jsonConfig.type: null;
        this.name = (Object.isDefined(jsonConfig.name))? jsonConfig.name: null;
        this.displayName = (Object.isDefined(jsonConfig.displayName))? jsonConfig.displayName: null;
        this.answerId = (Object.isDefined(jsonConfig.answerId))? jsonConfig.answerId: null;
        this.measureId = (Object.isDefined(jsonConfig.measureId))? jsonConfig.measureId: null;
        this.measureTypeId = (Object.isDefined(jsonConfig.measureTypeId))? jsonConfig.measureTypeId: null;
        this.templateTransformations = (Object.isArray(jsonConfig.templateTransformations))? jsonConfig.templateTransformations: [];
    }

    this.getName = function () {
        return (Object.isDefined(this.name))? this.name : (Object.isDefined(this.displayName)? this.displayName: 'var' + this.id);
    };

    this.setType = function () {
        switch (this.typeId) {
            case 1:
                this.type = "Question";
                break;
            case 2:
                this.type = "Answer";
                break;
            case 3:
                this.type = "Custom";
                break;
            case 4:
                this.type = "Formula";
                break;
            default:
                this.type = "Other";
        }
    };

    this.toString = function () {
        return "TemplateVariableContent [id: " + this.id +
            ", typeId: " + this.typeId +
            ", type: " + this.type +
            ", name: " + this.name +
            ", displayName: " + this.displayName +
            ", answerId: " + this.answerId +
            ", measureId: " + this.measureId +
            ", measureTypeId: " + this.measureTypeid +
            ", transformations: " + this.templateTransformations + "]";
    };
};