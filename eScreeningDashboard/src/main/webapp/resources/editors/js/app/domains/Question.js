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
 *visible
 * @static
 * @type {*|EScreeningDashboardApp.models|*|EScreeningDashboardApp.models|Object|*|Object|*}
 */
EScreeningDashboardApp.models = EScreeningDashboardApp.models || EScreeningDashboardApp.namespace("gov.va.escreening.models");
/**
 * Constructor method for the Answer class.  The properties of question class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   question class is a domain model class; which means it has both behaviour and state
 *              information about the user.
 * @param {String}  jsonQuestionObject  Represents the JSON representation of a Answer object.
 * @constructor
 * @author Aaron Roberson
 */
EScreeningDashboardApp.models.Question = (function Question() {

    function create(config) {
        var question = {
            id: undefined,
            text: undefined,
            type: undefined,
            displayOrder: undefined,
            required: undefined,
            ppi: false,
            mha: false,
            visible: true,
            variableName: undefined,
            answers: [],
            validations: [],
            childQuestions: [],
            tableAnswers: []
        };

        question.escapeTags = function (string) {
            string = string.replace(/</g, 'tag');
            string = string.replace(/>/g, 'endtag;');
            string = prettifyStr(text);
            alert('string:: ' + string);
            return string;
        };

        var unescapeTags = function (string) {
            string = string.replace(/tag/g, '<');
            string = string.replace(/endtag/g, '>');
            return string;
        };

        var prettifyStr = function (text) {
            var e = {
                lsquo: '\u2018',
                rsquo: '\u2019',
                ldquo: '\u201c',
                rdquo: '\u201d'
            };
            var subs = [
                {pattern: "(^|[\\s\"])'", replace: '$1' + e.lsquo},
                {pattern: '(^|[\\s-])"', replace: '$1' + e.ldquo},
                {pattern: "'($|[\\s\"])?", replace: e.rsquo + '$1'},
                {pattern: '"($|[\\s.,;:?!])', replace: e.rdquo + '$1'}
            ];
            for (var i = 0; i < subs.length; i++) {
                var sub = subs[i];
                var pattern = new RegExp(sub.pattern, 'g');
                text = text.replace(pattern, sub.replace);
            }
            return text;
        };


        question.filterValidations = function (targetPropertyName, targetPropertyValue) {
            var isPropertyName = "is" + targetPropertyName.charAt(0).toUpperCase() + targetPropertyName.substring(1),
                hasPropertyName = "has" + targetPropertyName.charAt(0).toUpperCase() + targetPropertyName.substring(1),
                getPropertyName = "get" + targetPropertyName.charAt(0).toUpperCase() + targetPropertyName.substring(1),
                propertyNameValue = false,
                filteredValidations = [];

            validations.some(function (validation) {
                if (validation.hasOwnProperty(targetPropertyName) || validation.hasOwnProperty(isPropertyName) ||
                    validation.hasOwnProperty(hasPropertyName) || validation.hasOwnProperty(getPropertyName)) {
                    if (typeof validation[isPropertyName] === 'function') {
                        propertyNameValue = validation[isPropertyName]();
                    } else if (typeof validation[hasPropertyName] === 'function') {
                        propertyNameValue = validation[hasPropertyName]();
                    } else if (typeof validation[getPropertyName] === 'function') {
                        propertyNameValue = validation[getPropertyName]();
                    } else {
                        propertyNameValue = validation[targetPropertyName];
                    }

                    if (propertyNameValue === targetPropertyValue) {
                        filteredValidations.push(validation);
                        return true;
                    }

                    return false;
                }
            });

            return filteredValidations;
        };

        question.findValidation = function (targetPropertyName, targetPropertyValue) {
            var filteredValidations = question.filterValidations(targetPropertyName, targetPropertyValue),
                filteredValidation = null,
                filteredValidationConstructorParameters;

            if (Object.isArray(filteredValidations) && filteredValidations.length > 0) {
                filteredValidation = filteredValidations[0];
            } else {
                filteredValidationConstructorParameters = {};
                filteredValidationConstructorParameters[targetPropertyName] = targetPropertyValue;
                filteredValidationConstructorParameters["selected"] = false;

                filteredValidation = new EScreeningDashboardApp.models.Validation(filteredValidationConstructorParameters);
            }

            return filteredValidation;
        };

        question.filterValidations = function filterValidations(targetPropertyName, targetPropertyValue, validations) {
            var isPropertyName =  "is"  + targetPropertyName.charAt(0).toUpperCase() + targetPropertyName.substring(1),
                hasPropertyName = "has" + targetPropertyName.charAt(0).toUpperCase() + targetPropertyName.substring(1),
                getPropertyName = "get" + targetPropertyName.charAt(0).toUpperCase() + targetPropertyName.substring(1),
                propertyNameValue = false,
                filteredValidations = [];

            validations.some(function (validation) {
                if(validation.hasOwnProperty(targetPropertyName) || validation.hasOwnProperty(isPropertyName) ||
                    validation.hasOwnProperty(hasPropertyName) || validation.hasOwnProperty(getPropertyName)) {
                    if(typeof validation[isPropertyName] === 'function'){
                        propertyNameValue = validation[isPropertyName]();
                    } else if(typeof validation[hasPropertyName] === 'function') {
                        propertyNameValue = validation[hasPropertyName]();
                    } else if(typeof validation[getPropertyName] === 'function') {
                        propertyNameValue = validation[getPropertyName]();
                    } else {
                        propertyNameValue = validation[targetPropertyName];
                    }

                    if(propertyNameValue === targetPropertyValue) {
                        filteredValidations.push(validation);
                        return true;
                    }

                    return false;
                }
            });

            return filteredValidations;
        };

        if(config) {
            // Loop through the properties on the config
            for (var prop in config) {
                if (config.hasOwnProperty(prop) && config[prop] === null) {
                    // Update nulls to undefined so they don't override class properties
                    config[prop] = undefined;
                }
            }
        }

        return _.extend(question, config);
    }

    return {
        create: create
    }

})();
