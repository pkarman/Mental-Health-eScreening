/**
 * Angular service factory method for Template Types.
 *
 * @author Tonté Pouncil
 */
angular.module('EscreeningDashboardApp.services.templateType', ['restangular'])
    .factory('TemplateTypeService', ['Restangular', 'TemplateType', function (Restangular, TemplateType){
        "use strict";

        var restAngular = Restangular.withConfig(function(Configurer) {
                Configurer.setBaseUrl('/escreeningdashboard/dashboard');
                Configurer.setRequestSuffix('.json');
            }),
            service = restAngular.service("services/templateTypes");

        restAngular.extendModel("services/templateTypes", function(model) {
            return angular.extend(model, TemplateType);
        });
        
        // Expose the public TemplateService API to the rest of the application.
        //return service;
        return {
            getTemplateTypes: function (queryParams) {
                return service.getList(queryParams);
            }
        }
    }]);
        