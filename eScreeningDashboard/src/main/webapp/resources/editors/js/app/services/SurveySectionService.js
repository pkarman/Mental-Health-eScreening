angular.module('EscreeningDashboardApp.services.surveysection', ['restangular'])
    .factory('SurveySectionService', ['Restangular', function (Restangular) {
        "use strict";

        var restAngular = Restangular.withConfig(function (config) {
            config.setBaseUrl('services');
            config.setRequestSuffix('.json');
        });

        var proxy = restAngular.all('surveySections');

        // service to perform CRUD
        var service = {
            create: function (ss) {
                return proxy.post(ss);
            },
            getList: function () {
                return proxy.getList();
        },
            update: function (ss) {
                return ss.put();
            },
            delete: function (ss) {
                return ss.remove();
            }
        };
        return service;

    }]);
