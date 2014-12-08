/**
 * Angular service factory for to create an Event Bus for pub/sub communications.
 * Ref:
 * http://codingsmackdown.tv/blog/2013/04/29/hailing-all-frequencies-communicating-in-angularjs-with-the-pubsub-design-pattern/
 *
 * We can add notifications by adding a sub and pub method to this service, or alternatively we can make this a more 
 * generic communication pathway where the subscribers and the publishers provide the "topic" ID.
 *
 * @author Robin Carnow
 */
angular.module('EscreeningDashboardApp.services.eventBus', [])
    .factory('eventBus', ['$rootScope', function ($rootScope) {
        "use strict";
        
        // private notification messages
        var _LOCATION_CHANGED_ = '_LOCATION_CHANGED_';
    
        $rootScope.$on("$locationChangeStart",function(event, next, current){
            $rootScope.$broadcast(_LOCATION_CHANGED_, {'next': next, 'current': current});
        });
        
        //subscribe to location change notification
        function onLocationChange($scope, handler) {
            $scope.$on(_LOCATION_CHANGED_, function(event, args) {
               handler(args.next, args.current);
            });
        };
        
        return {
            /**
             * @param $scope 
             * @param handler - function will be passed: next and current objects
             */
            onLocationChange: onLocationChange
        };
    }])