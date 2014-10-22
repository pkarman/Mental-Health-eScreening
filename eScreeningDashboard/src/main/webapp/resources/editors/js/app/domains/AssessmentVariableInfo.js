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
 * Constructor method for the AssessmentVariable class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a domain model class; which means it has both behavior and state
 *              information about the user.
 * @param {String}  jsonSurveySectionObject  Represents the JSON representation of an Assessment Variable object.
 * @constructor
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.AssessmentVariableInfo = function (jsonAssessmentVariableObject) {
    var id = (Object.isDefined(jsonAssessmentVariableObject) && Object.isDefined(jsonAssessmentVariableObject.id))? jsonAssessmentVariableObject.id : null,
        answerId = (Object.isDefined(jsonAssessmentVariableObject) && Object.isDefined(jsonAssessmentVariableObject.answerId)) ? jsonAssessmentVariableObject.answerId : null,
        measureId = (Object.isDefined(jsonAssessmentVariableObject) && Object.isDefined(jsonAssessmentVariableObject.measureId))? jsonAssessmentVariableObject.measureId : null,
        measureTypeId = (Object.isDefined(jsonAssessmentVariableObject) && Object.isDefined(jsonAssessmentVariableObject.measureTypeId))? jsonAssessmentVariableObject.measureTypeId : null,
        typeId = (Object.isDefined(jsonAssessmentVariableObject) && Object.isDefined(jsonAssessmentVariableObject.typeId))? jsonAssessmentVariableObject.typeId : null,
        name = (Object.isDefined(jsonAssessmentVariableObject) && Object.isDefined(jsonAssessmentVariableObject.name))? jsonAssessmentVariableObject.name : null,
        displayName = (Object.isDefined(jsonAssessmentVariableObject) && Object.isDefined(jsonAssessmentVariableObject.displayName))? jsonAssessmentVariableObject.displayName : null;
    this.type = null;

    this.getName = function () {
        return (Object.isDefined(this.name))? this.name : (Object.isDefined(this.displayName)? this.displayName: 'var' + this.id);
    };

    this.getType = function () {
        switch (this.typeId) {
            case 1:
                this.type = "Measure";
                break;
            case 2:
                this.type = "Measure Answer"
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
        return "AssessmentVariable [id: " + this.id +
            ", answerId: " + this.answerId +
            ", measureId: " + this.measureId +
            ", measureTypeId: " + this.measureTypeId +
            ", typeId: " + this.typeId +
            ", name: " + this.name +
            ", displayName: " + this.displayName + "]";
    };
};