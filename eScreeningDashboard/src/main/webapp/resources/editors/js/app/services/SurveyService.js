(function() {
    'use strict';

    angular.module('Editors').factory('SurveyService', ['Restangular', function (Restangular){

        return Restangular.service('surveys');

    }]);
})();
