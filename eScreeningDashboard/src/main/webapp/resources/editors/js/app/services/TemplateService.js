/**
 * Angular service factory method for Templates.
 *
 * @author Robin Carnow
 */
angular.module('EscreeningDashboardApp.services.template', ['ngResource'])
    .factory('TemplateService', ['$q', '$resource', function ($q, $resource){
        "use strict";
        
        var getTypesForModule = function(moduleId){
            var deferred = $q.defer()
            var service = $resource (
                    "services/templateType/:moduleID",
                    {},
                    {
                        query: {
                            method: 'GET',
                            params: {moduleID: moduleId},
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );
            
            service.query(null, function (jsonResponse) {
        	//TODO: Add response handling
                //var templateTypes = handleSurveyQueryResponse(jsonResponse, EScreeningDashboardApp.models.SurveysTransformer, null);
                //deferred.resolve((templateTypes.length === 0)? templateTypes[0]: templateTypes);
            }, function (reason) {
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
            getTypesForModule: getTypesForModule,
            update: update,
            remove: remove
        };
    }]);
        