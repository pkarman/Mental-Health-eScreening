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
            var extendedModel = angular.extend({}, Template, model);
            extendedModel.init();
            return extendedModel;
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
