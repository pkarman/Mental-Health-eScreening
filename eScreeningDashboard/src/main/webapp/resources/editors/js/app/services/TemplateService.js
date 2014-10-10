/**
 * Angular service factory method for Templates.
 *
 * @author Robin Carnow
 */
angular.module('EscreeningDashboardApp.services.template', ['restangular'])
    .factory('TemplateService', ['Restangular', 'TemplateType', function (Restangular, TemplateType){
        "use strict";

        var job = Restangular.service("services/templateTypes");

        Restangular.extendModel("services", function(model) {
            return angular.extend(model, TemplateType);
        });
        
        // Expose the public TemplateService API to the rest of the application.
        return job;
    }]);
        