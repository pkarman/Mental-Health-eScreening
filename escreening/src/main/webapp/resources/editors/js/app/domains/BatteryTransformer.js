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
 * Represents the application static variable. Use existing static variable, if one already exists,
 * otherwise create a new one.
 *
 * @static
 * @type {*|EScreeningDashboardApp.models.BatteryTransformer|*|EScreeningDashboardApp.models.BatteryTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.BatteryTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.BatteryTransformer");
/**
 * Static method for the BatteryTransformer class that is responsible for transforming the JSON Representation of the
 * Battery domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.Battery} An existing Battery domain model that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.BatteryTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing Battery.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Battery}
     */
    var existingBattery = null;

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.battery)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.battery can not be null or undefined.");
    }

    existingBattery = EScreeningDashboardApp.models.BatteryTransformer.transformJSONBattery(jsonResponse.battery);

    return existingBattery;
};
/**
 * Static method for the BatteryTransformer class that is responsible for transforming the JSON Representation of the
 * Battery domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonBattery  Represents the JSON representation of battery returned from the service call request.
 * @returns {EScreeningDashboardApp.models.Battery} An existing Battery domain model that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.BatteryTransformer.transformJSONBattery = function (jsonBattery) {
    'use strict';
    /**
     * Represent the existing battery.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Battery}
     */
    var existingBattery;

    if (!Object.isDefined(jsonBattery)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonBattery can not be null or undefined.");
    }

    existingBattery = new EScreeningDashboardApp.models.Battery(jsonBattery);

    return existingBattery;
};