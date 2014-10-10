/**
 * Angular service factory method for Templates.
 *
 * @author Robin Carnow
 */
angular.module('EscreeningDashboardApp.services.template', ['restangular'])
    .factory('TemplateService', ['$q', 'Restangular', 'TemplateType', function ($q, Restangular, TemplateType){
        "use strict";

        /*var moduleTemplateTypesService = function(surveyId){
            return $resource (
                "services/templateType/survey/:surveyId",
                {},
                {
                    query: {
                        method: 'GET',
                        params: {"surveyId": surveyId},
                        isArray: false,
                        headers:{
                            'Accept': 'application/json',
                            'Content-Type':'application/json'
                        }
                    }
                }
            );
        };
        
        var getTypes = function(queryObj){

            var service = null;
            var transformer = null;
            if(Object.isDefined(queryObj.surveyId)){
                service = moduleTemplateTypesService(queryObj.surveyId);
                transformer = null; //TODO: create this: EScreeningDashboardApp.models.TemplateTypesTransformer;
            }
            //TODO: add a test for batteryId and if found use the new batteryTemplateTypesService() method
            
            var deferred = $q.defer();
            
            if(Object.isDefined(service)){
                service.query(null, 
                    function (jsonResponse) {

                        var response = BytePushers.models.ResponseTransformer.transformJSONResponse(jsonResponse, EScreeningDashboardApp.models.TemplateType);

                        if (response !== null) {
                            if (response.isSuccessful()) {
                                var templateTypes = response.getPayload();
                                deferred.resolve((templateTypes.length === 1)? templateTypes[0]: templateTypes);
                            } else {
                                fail(deferred, response.getMessage());
                            }
                        } else {
                            fail(deferred, "Response is null.");
                        }
                    },
                    function (reason) {
                        var errorMessage = "Sorry, we are unable to process your request at this time because we experiencing problems communicating with the server."

                        if(Object.isDefined(reason) && Object.isDefined(reason.status) && Object.isNumber(reason.status)) {
                            errorMessage = HttpRejectionProcessor.processRejection(reason);
                        }

                        fail(deferred, errorMessage);
                    }
                );
            }
            else{
                fail(deferred, "No valid context object found.");
            }

            return deferred.promise;
        };*/

        function fail(deferred, message){
            deferred.reject(new BytePushers.models.ResponseStatus(
                {
                    "message": message,
                    "status": "failed"
                }
            ));
        }

        Restangular.extendModel('templateTypes', function(model) {
            return angular.extend(model, TemplateType);
        });

        /**
         * Querys the template with a given survey Id from the system.
         * @param surveyId
         * @returns {*}
         */
        var query = function(queryObj) {
            var deferred = $q.defer();
            if(!Object.isDefined(queryObj) || !Object.isNumber(parseInt(queryObj.surveyId))){
                fail(deferred, "Invalid survey id.");
            }

            Restangular.all('services/templateTypes').getList({surveyId: queryObj.surveyId}).then(function (templateTypes) {
                deferred.resolve(templateTypes);
            }, function (reason) {
                fail(deferred, reason);
            });

            return deferred.promise;
        };
        
        /**
         * Removes the template with the give ID from the system
         */
        var remove = function(templateId){
            
        };
        
        /**
         * Send template update to the server.
         * 
         * @param Template DTO:  ???
         */
        var update = function(template){
            
        };
        
        
        
        // Expose the public TemplateService API to the rest of the application.
        return {
            query: query,
            update: update,
            remove: remove
        };
    }]);
        