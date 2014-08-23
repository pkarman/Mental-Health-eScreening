/* jshint strict: true */
/**
 * <p>
 * EScreeningDashboardApp variable is a singleton object that support the
 * EScreening Dashboard Application. It exposes certain methods in its API while hiding
 * others.
 * </p>
 * 
 * @field
 * 
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
var EScreeningDashboardApp = (function() {
	"use strict";
	/*
	 * Singleton object that returns the public method that will be exposed to
	 * the public.
	 */
	function Singleton() {
		
		/**
		 * Represents the restful client URL prefix. When set to development
		 * mode the restful client will prefix the request with the 'data'
		 * folder in order to service the request with mock data.
		 * 
		 * @field
		 * @type {string}
		 */
		this.restfulClientUrlPrefix = EScreeningDashboardApp.RestfulClientUrlPrefixes.PROD;

		/**
		 * Convenience method that sets the restful client URL prefix.
		 * 
		 * @public
		 * @method
		 * @param {String}
		 *            restfulClientUrlPrefix Represents prefix to all restful
		 *            request.
		 */
		this.setRestfulClientUrlPrefix = function(restfulClientUrlPrefix) {
			this.restfulClientUrlPrefix = restfulClientUrlPrefix;
		};

		/**
		 * Convenience method that gets the restful client URL prefix.
		 * 
		 * @returns {String} The prefix to all restful request.
		 */
		this.getRestfulClientUrlPrefix = function() {
			return this.restfulClientUrlPrefix;
		};

		this.parseQueryString = function(queryString) {
			var params = {}, queries, temp, i, l;

			// Split into key/value pairs
			queries = queryString.split("&");

			// Convert the array of strings into an object
			for (i = 0, l = queries.length; i < l; i++) {
				temp = queries[i].split('=');
				params[temp[0]] = temp[1];
			}

			return params;
		};

        this.checkWhetherSurveySectionSurveyArrayHasIncreased = function(savedSurveySections, modifiedSurveySections) {
            var surveySectionSurveyArrayHasIncreased = savedSurveySections.some(function (savedSurveySection) {
                if(!Object.isDefined(savedSurveySection.surveys)) savedSurveySection.surveys = [];
                return modifiedSurveySections.some(function (modifiedSurveySection) {
                    if (modifiedSurveySection.name === savedSurveySection.getName()) {
                        if (modifiedSurveySection.surveys.length > savedSurveySection.surveys.length) {
                            modifiedSurveySection.surveyArrayHasIncreased = true;
                            return true;
                        }
                        return false;
                    }
                });
            });
        };

        this.sort = function(surveySections, propertyName, sortDirection){
            var spliceIndex, targetElement,
                isPropertyName = "is" + propertyName.charAt(0).toUpperCase() + propertyName.substring(1),
                hasPropertyName = "has" + propertyName.charAt(0).toUpperCase() + propertyName.substring(1),
                propertyNameBooleanValue = false,
                ascendingSortDirection = (Object.isDefined(sortDirection) && sortDirection === "+")? true: (Object.isDefined(sortDirection) && sortDirection === "-")? false: false;

            surveySections.forEach(function (surveySection, index) {
                if(surveySection.hasOwnProperty(propertyName)) {
                    if(typeof surveySection[isPropertyName] === 'function'){
                        propertyNameBooleanValue = surveySection[isPropertyName]();
                    } else if(typeof surveySection[hasPropertyName] === 'function') {
                        propertyNameBooleanValue = surveySection[hasPropertyName]();
                    } else {
                        propertyNameBooleanValue = surveySection[propertyName];
                    }

                    if(propertyNameBooleanValue) {
                        spliceIndex = index;
                        targetElement = surveySection;

                        if(ascendingSortDirection){
                            surveySections.splice(spliceIndex, 1);
                            surveySections.splice(0, 0, targetElement);
                        } else {
                            surveySections.splice(spliceIndex, 1);
                            surveySections.push(targetElement);
                        }
                    }
                }
            });
        };
        
        /**
         * Utility Method (static), to remove HTML < and > from a string.
         */
        this.escapeTags = function(string){
        	string = string.replace(/<\/?[^>]+(>|$)/g, "");
        	string = string.replace(/"/g,"\"");
        	
        	return string;
        };
        
        /**
         * Utility Method (static), to place HTML < and > into a string, replacing &lt; and &gt;.
         */
        this.unescapeTags = function(string){
        	string = string.replace(/&lt;/g, '<');
        	string = string.replace(/&gt;/g, '>');
        	return string;
        };
        
        /**
         * Utility Method (static), to remove duplicates from an array of Objects.
         */
        this.cleanArray = function(ary, key) {
            var seen = {};
            return ary.filter(function(elem) {
                var k = key(elem);
                return (seen[k] === 1) ? 0 : seen[k] = 1;
            });
        };
	}

	/**
	 * Private variable for Singleton pattern.
	 * 
	 * @private
	 * @field
	 */
	var instance, // our this holder
	private_static_variables_and_methods = { // an emulation of static
		// variables and methods
		name : "EScreeningDashboardApp",

		/**
		 * Singleton method to get the only instance of the
		 * EScreeningDashboardApp class.
		 * 
		 * @returns {EScreeningDashboardApp} A singleton instance of
		 *          EScreeningDashboardApp object.
		 */
		getInstance : function() {
			if (instance === undefined) {
				instance = new Singleton();
			}
			return instance;
		},
		/**
		 * Convenience method for creating name spaces in the application code.
		 * 
		 * @private
		 * @method
		 * @param {String}
		 *            ns_string Represent a namespace that is dot delimited.
		 * @returns {Object} An Object that represents all the objects in the
		 *          name space.
		 */
		namespace : function(ns_string) {
			var parts = ns_string.split('.'), parent = EScreeningDashboardApp;
			// strip redundant leading global
			if (parts[0] === "EScreeningDashboardApp") {
				parts = parts.slice(1);
			}
			parts.forEach(function(part) {
				// create a property if it doesn't exist
				if (typeof parent[part] === "undefined") {
					parent[part] = {};
				}
				parent = parent[part];
			});
			/*
			 * for (i = 0; i < parts.length; i = i + 1) { // create a property
			 * if it doesn't exist if (typeof parent[parts[i]] === "undefined") {
			 * parent[parts[i]] = {}; } parent = parent[parts[i]]; }
			 */
			return parent;
		}
	};

	return private_static_variables_and_methods; // Returns private static
	// variables and methods.
}());

/**
 * Static variable that holds the objects defined by the namespace.
 * 
 * @type {Object|*}
 */
EScreeningDashboardApp.views = EScreeningDashboardApp.views || EScreeningDashboardApp.namespace("gov.va.escreening.views");
/**
 * Static variable that holds the objects defined by the namespace.
 * 
 * @type {Object|*}
 */
EScreeningDashboardApp.models = EScreeningDashboardApp.models || EScreeningDashboardApp.namespace("gov.va.escreening.models");
/**
 * Static Object that represents the available restful client URL prefixes.
 * 
 * @type {{DEV: string, TEST: string, PROD: string}}
 */
EScreeningDashboardApp.RestfulClientUrlPrefixes = {
	DEV : "resources/data/",
	TEST : "",
	PROD : ""
};
// Sets the restful client URL prefix to development. This is strictly for
// mocking the service calls while in development.
// TODO: Need to remove this line before we go to test and production.
EScreeningDashboardApp.getInstance().setRestfulClientUrlPrefix(EScreeningDashboardApp.RestfulClientUrlPrefixes.DEV);