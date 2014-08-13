/**
 * Created by Bryan on 2/10/14.
 */
var module;

try {
    module = angular.module('dd.services');
} catch (e) {
    module  = angular.module('dd.services', []);
}

module.factory('uuid', function() {
    var svc = {
        new: function() {
            function _p8(s) {
                var p = (Math.random().toString(16)+"000000000").substr(2,8);
                return s ? "-" + p.substr(0,4) + "-" + p.substr(4,4) : p ;
            }
            return _p8() + _p8(true) + _p8(true) + _p8();
        },

        empty: function() {
            return '00000000-0000-0000-0000-000000000000';
        }
    };

    return svc;
});
