/**
 * Angular service factory method for Templates.
 *
 * @author Robin Carnow
 */
angular.module('EscreeningDashboardApp.services.template', ['restangular'])
    .factory('TemplateService', ['Restangular', 'Template', function (Restangular, Template){
        "use strict";

        var restAngular = Restangular.withConfig(function(Configurer) {
                Configurer.setBaseUrl('/escreeningdashboard/dashboard');
                //Configurer.setRequestSuffix('.json');
            }),
            service = restAngular.service("services/template");

        restAngular.extendModel("services/template", function(model) {
            return angular.extend(model, Template);
        });
        
        // Expose the restangular service
        return service;
    }]);
        