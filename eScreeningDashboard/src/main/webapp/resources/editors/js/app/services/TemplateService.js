/**
 * Angular service factory method for Templates.
 *
 * @author Robin Carnow
 */
angular.module('EscreeningDashboardApp.services.template', ['ngResource'])
    .factory('TemplateService', ['$q', '$resource', function ($q, $resource){
        "use strict";
        
        var moduleTemplateTypesService = function(surveyId){
            return $resource (
                    "services/templateType/:surveyId",
                    {},
                    {
                        query: {
                            method: 'GET',
                            params: {"surveyId": surveyId},
                            isArray: true,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );
        }
        
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
                    	//TODO: Add response handling
                            //var templateTypes = handleSurveyQueryResponse(jsonResponse, transformer);
                            //deferred.resolve((templateTypes.length === 0)? templateTypes[0]: templateTypes);
                        }, 
                        function (reason) {
                            var errorMessage = "Sorry, we are unable to process your request at this time because we experiencing problems communicating with the server."
            
                            if(Object.isDefined(reason) && Object.isDefined(reason.status) && Object.isNumber(reason.status)) {
                                errorMessage = HttpRejectionProcessor.processRejection(reason);
                            }
            
                            deferred.reject(new BytePushers.models.ResponseStatus(
                                {
                                    "message": errorMessage,
                                    "status": "failed"
                                }
                            ));
                        });
            }
            else{
                deferred.reject(new BytePushers.models.ResponseStatus(
                        {
                            "message": "No valid context object found.",
                            "status": "failed"
                        }
                    ));
            }

            return deferred.promise;
        };
        
        
        
        /**
         * Send template update to the server.
         * 
         * @param Template DTO:  templateId, name, description, templateFile, templateTypeId, isGraphical
         */
        var update = function(template){
            
        };
        
        /**
         * Removes the template with the give ID from the system
         */
        var remove = function(templateId){
            
        };
        
        // Expose the public TemplateService API to the rest of the application.
        return {
            getTypes: getTypes,
            update: update,
            remove: remove
        };
    }]);
        