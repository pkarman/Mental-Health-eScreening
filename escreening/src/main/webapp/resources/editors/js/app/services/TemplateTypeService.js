/**
 * Angular service factory method for Template Types.
 *
 * @author Tonté Pouncil
 */
angular.module('EscreeningDashboardApp.services.templateType', ['restangular'])
    .factory('TemplateTypeService', ['Restangular', 'TemplateType', function (Restangular, TemplateType){
        "use strict";

        var currentTemplateTypes = [];
        var currentTemplateType;
        
        var restAngular = Restangular.withConfig(function(config) {
                config.setBaseUrl('services/');
                config.setRequestSuffix('.json');
            }),
            service = restAngular.service("templateTypes");

        restAngular.extendModel("templateTypes", function(model) {
            return angular.extend(model, TemplateType);
        });
        
        
        var regTypes = function($scope, templateTypes){
            $scope.templateTypes = templateTypes;
            $scope.$watch('templateTypes', function(newVal, oldVal){
                console.log("Updating template type list");
                currentTemplateTypes = newVal;
            }, true);
        };
 
        // Expose the public TemplateTypeService API to the rest of the application.
        //return service;
        return {
            /**
             * Will retrieve the list of template types given the query parameter or return what was last 
             * list queried if queryParam is null.  If queryParam is empty then the server will be queried.
             */
            getTemplateTypes: function (queryParams) {
                if(Object.isDefined(queryParams)){
                    return service.getList(queryParams).then(
                            function(templateTypes){
                                currentTemplateTypes = templateTypes;
                                return templateTypes;
                            });
                }
                return currentTemplateTypes;
            },
            /**
             * Connects the given types to the give scope and sets up a $watch for changes so
             * the state is kept up to date.  If this is not called in a controller, then changes 
             * will not be reflected when getTemplateTypes is called.
             */
            registerTypes : regTypes,
            
            /**
             * To track the currently selected template type.
             */
            setSelectedType : function(templateType){
                currentTemplateType = templateType;
            },
            getSelectedType : function(){
                return currentTemplateType;
            }
        }
    }]);
