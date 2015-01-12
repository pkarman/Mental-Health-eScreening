/**
 * Created by munnoo on 12/29/14.
 */
angular.module('EscreeningDashboardApp.services.manageformulas', ['restangular'])
    .factory('ManageFormulasService', ['$log', 'Restangular', '$q', function ($log, Restangular, $q) {

        "use strict";

        var restAngular = Restangular.withConfig(function (config) {
            config.setDefaultHttpFields({cache: true});
            config.setBaseUrl('services');
            config.setRequestSuffix('.json');
        });

        var proxy = restAngular.all('formulas');

        // service to perform CRUD
        var service = {
            create: function (formula) {
                return proxy.post(formula);
            },
            getList: function (id) {
                return proxy.getList({moduleId:id});
            },
            update: function (formula) {
                return formula.put();
            },
            delete: function (formula) {
                return formula.remove();
            }
        };
        return service;
    }]);
