angular.module('EscreeningDashboardApp.services.surveysection', ['restangular'])
    .factory('SurveySectionService', ['Restangular', '$rootScope', function (Restangular, $rootScope) {
        "use strict";

        var restAngular = Restangular.withConfig(function (config) {
            config.setBaseUrl('services');
            config.setRequestSuffix('.json');
        });

        var ssProxy = restAngular.all('surveySections');

        // service to perform CRUD
        var service = {
            createSS: function (ss) {
                return ssProxy.post(ss);
            },
            readAllSS: function () {
                return ssProxy.getList();
            },
            readOneSS: function (ss) {
                return ssProxy.get(ss.id);
            },
            updateSS: function (ss) {
                return ss.put();
            },
            deleteSS: function (ss) {
                return ss.remove();
            },
        };
        return service;

    }]);
