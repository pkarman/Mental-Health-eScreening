angular.module('EscreeningDashboardApp.services.surveysection', ['restangular'])

    .config(function (RestangularProvider) {
        RestangularProvider.setBaseUrl('services')
    })

    .factory('SurveySectionService', ['Restangular', function (Restangular) {
        "use strict";

        var proxy = Restangular.all('surveySections');

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
