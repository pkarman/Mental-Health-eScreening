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
                Configurer.setRequestSuffix('.json');
            }),
            service = restAngular.service("services/template");

        restAngular.extendModel("services/template", function(model) {
            //this is done because during a put the server does not return the same object that was saved.
            if(typeof(model) == "object"){
                return angular.extend(model, Template);
            }
            return model;
        });
        
        return {
            get: function(templateId) {
                return service.one(templateId).get();
            },
            remove: function (templateId) {
                return service.one(templateId).remove();
            }
        }
    }]);
        