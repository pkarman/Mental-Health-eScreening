(function() {
    'use strict';

    angular.module('Editors').factory('SurveyService', ['Restangular', 'Survey', function (Restangular, Survey){

        Restangular.extendModel('surveys', function(model) {
            return Survey.create(model);
        });

        return Restangular.service('surveys');

    }]);
})();
