(function(){
    'use strict';

    angular.module('Editors').factory('SurveyPageService', ['Restangular', 'SurveyPage', function (Restangular, SurveyPage) {

        Restangular.extendModel('pages', function(model) {
            return SurveyPage.extend(model);
        });

        return Restangular.service('pages');
    }]);
})();

