(function() {
    'use strict';

    angular.module('Editors').factory('ClinicalReminderService', ['Restangular', 'ClinicalReminder', function (Restangular, ClinicalReminder){

        Restangular.extendModel('clinicalReminders', function(model) {
            return ClinicalReminder.extend(model);
        });

        return Restangular.service('clinicalReminders');

    }]);
})();
