(function(){
    'use strict';
    angular.module('EscreeningDashboardApp.services.surveypage', ['restangular'])
        .service('SurveyPageService', ['Restangular', 'SurveyPage', function (Restangular, SurveyPage) {

            var restAngular = Restangular.withConfig(function(config) {
                config.addResponseInterceptor(function(data, operation) {
                    var newResponse;

                    if (operation === 'getList') {
                        // Pages response does NOT have a payload property like others responses
                        // and the property on the response doesn't match the endpoint
                        // Add the array directly on the response
                        newResponse = data['surveyPages'] || data;
                        // Add the status as a meta property on the array
                        newResponse.status = data.status;
                    }

                    return newResponse || data;
                });
            });

            restAngular.extendModel("pages", function(model) {
                return SurveyPage.create(model);
            });

            return function(surveyId) {
                // Pages is a nested resource and therefore requires this currying function
                return restAngular.one('surveys', surveyId).all('pages');
            }
        }]);
})();

