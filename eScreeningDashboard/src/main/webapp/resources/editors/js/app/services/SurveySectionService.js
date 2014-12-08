angular.module('EscreeningDashboardApp.services.surveysection', ['restangular'])
    .factory('SurveySectionService', ['Restangular', '$rootScope', function (Restangular, $rootScope) {
        "use strict";

        var restAngular = Restangular.withConfig(function (config) {
            config.setBaseUrl('services');
            config.setRequestSuffix('.json');
        });

        var surveySections  = restAngular.all('surveySections');

        // service to perform CRUD
        var service = {
            createSS: function(ss) {
                $rootScope.$broadcast('ss:created');
                return surveySections.post(ss);
            },
            readAllSS: function() {
                return surveySections.getList();
            },
            updateSS: function(ss) {
                $rootScope.$broadcast('ss:updated');
                return ss.put();
            },
            deleteSS: function(ss) {
                $rootScope.$broadcast('ss:deleted');
                return ss.remove();
            }
        };
        return service;

    }]);
