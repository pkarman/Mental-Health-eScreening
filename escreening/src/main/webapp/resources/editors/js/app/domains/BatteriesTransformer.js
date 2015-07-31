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
 * @type {*|EScreeningDashboardApp.models.BatteriesTransformer|*|EScreeningDashboardApp.models.BatteriesTransformer|Object|*|Object|*}
 */
EScreeningDashboardApp.models.BatteriesTransformer =  EScreeningDashboardApp.namespace("gov.va.escreening.models.BatteriesTransformer");
/**
 * Static method for the BatteriesTransformer class that is responsible for transforming the JSON Representation of the
 * Batteries domain model object.
 *
 * @static
 * @method
 * @param {String}  jsonResponse  Represents the JSON response of a service call request.
 * @returns {EScreeningDashboardApp.models.Batteries[]} A list of existing Battery domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.BatteriesTransformer.transformJSONPayload = function (jsonResponse) {
    'use strict';
    /**
     * Represent the existing Batteries.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Batteries[]}
     */
    var existingBatteries = [];

    if (!Object.isDefined(jsonResponse)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse can not be null or undefined.");
    }

    if (!Object.isDefined(jsonResponse.batteries) && !Object.isDefined(jsonResponse.battery)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.Batteries can not be null or undefined.");
    }

    if (!Array.isArray(jsonResponse.batteries) && !Object.isDefined(jsonResponse.battery)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonResponse.Batteries must be an Array or Object.");
    }

    if (!jsonResponse.batteries)
    	existingBatteries = EScreeningDashboardApp.models.BatteriesTransformer.transformJSONBattery(jsonResponse.battery);
    else
    	existingBatteries = EScreeningDashboardApp.models.BatteriesTransformer.transformJSONBatteries(jsonResponse.batteries);

    return existingBatteries;
};
/**
 * Static method for the BatteriesTransformer class that is responsible for transforming the JSON Representation of the
 * Battery domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonBatteries  Represents the JSON representation of Batteries returned from the service call request.
 * @returns {EScreeningDashboardApp.models.Batteries[]} A list of existing Battery domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.BatteriesTransformer.transformJSONBattery = function (jsonBattery) {
    'use strict';
    /**
     * Represent the existing Battery.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Battery}
     */
    var existingBatteries = [];

    if (!Object.isDefined(jsonBattery)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonBatteries can not be null or undefined.");
    }

    existingBatteries.push(new EScreeningDashboardApp.models.Battery(jsonBattery));

    return existingBatteries;
};

/**
 * Static method for the BatteriesTransformer class that is responsible for transforming the JSON Representation of the
 * Battery domain model object.
 *
 * @static
 * @method
 * @param {String[]}  jsonBatteries  Represents the JSON representation of Batteries returned from the service call request.
 * @returns {EScreeningDashboardApp.models.Batteries[]} A list of existing Battery domain models that have been returned from a service call request.
 *
 * @author Bryan Henderson
 */
EScreeningDashboardApp.models.BatteriesTransformer.transformJSONBatteries = function (jsonBatteries) {
    'use strict';
    /**
     * Represent the existing Batteries.
     *
     * @private
     * @field
     * @type {EScreeningDashboardApp.models.Batteries[]}
     */
    var existingBatteries = [];

    if (!Object.isDefined(jsonBatteries)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonBatteries can not be null or undefined.");
    }

    if (!Array.isArray(jsonBatteries)) {
        throw new BytePushers.exceptions.InvalidParameterException("jsonBatteries must be an Array.");
    }

    jsonBatteries.forEach(function(jsonBattery){
        existingBatteries.push(new EScreeningDashboardApp.models.Battery(jsonBattery));
    });

    return existingBatteries;
};