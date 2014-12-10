/**
 * Angular service factory method for Templates.
 *
 * @author Robin Carnow
 */
angular.module('EscreeningDashboardApp.services.template', ['restangular'])
    .factory('TemplateService', ['Restangular', 'Template', function (Restangular, Template){
        "use strict";

        var restAngular = Restangular.withConfig(function(config) {
                config.setBaseUrl('services/');
                config.setRequestSuffix('.json');
            }),
            service = restAngular.service("template");
        
        restAngular.extendModel("template", function(model) {
            //this is done because during a put the server does not return the same object that was saved.
            if(typeof(model) == "object"){
                angular.extend(model, Template);
                model.init();
                return model;
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
