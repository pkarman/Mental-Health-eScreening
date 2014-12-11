angular.module('EscreeningDashboardApp.services.surveysection', ['restangular'])
    .factory('SurveySectionService', ['Restangular', '$rootScope', function (Restangular, $rootScope) {
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
            readAll: function () {
                return proxy.getList();
            },
            readOne: function (ss) {
                return proxy.get(ss.id);
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
