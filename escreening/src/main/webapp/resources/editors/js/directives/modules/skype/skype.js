(function() {

    'use strict';

    angular.module('common.skype', [])

    .filter('skype', [function() {
        return function(value) {
            if (!value) {
                return value;
            }

            value = String(value).replace(/\D/g, '');

            if (value[0] === '1') {
                value = value.substr(1);
            }

            return 'skype:+1' + value + '?call';
        };
    }]);

})();