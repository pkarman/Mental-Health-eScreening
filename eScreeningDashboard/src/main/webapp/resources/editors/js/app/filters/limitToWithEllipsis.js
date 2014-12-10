/**
 * Angular filter which acts like limitTo but will add ellipsis ('...') if the content is greater than the give limit.
 * The ellipsis will take the place of the last 3 characters of the string so the resulting string will not go over limit.
 * If limit is less than 3 then no ellipsis are added.
 * 
 * @author Robin Carnow
 */
angular.module('EscreeningDashboardApp.filters.limitToWithEllipsis', [])
  .filter('limitToWithEllipsis', ['limitToFilter', function(limitTo){
    'use strict';
    
    //found a bug in limitTo which has issues with some white space chars
    function workAroundLimitTo(text, limit){
        return text.substring(0, limit);
    }
    
    return function (text, limit) {
        if(Object.isDefined(text) && Object.isDefined(limit) ){
            var filtered = text;
            if(text.length > limit){
                if(limit >= 3){
                    filtered = workAroundLimitTo(text, limit - 3);
                    filtered += "...";
                }
                else {
                    filtered = workAroundLimitTo(text, limit);
                }
            }            
            return filtered;
        }
        return text;
    };
}]);