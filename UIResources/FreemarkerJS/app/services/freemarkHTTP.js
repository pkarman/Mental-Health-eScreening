/**
 * Created by Bryan Henderson on 5/13/2014.
 */
FreemarkApp.factory('freemarkHTTP', function($http) {
    return {
        getFTL: function(url) {
            //since $http.get returns a promise,
            //and promise.then() also returns a promise
            //that resolves to whatever value is returned in it's
            //callback argument, we can return that.
            return $http.get(url).then(function(result) {
                return result.data;
            });
        }
    }
});
