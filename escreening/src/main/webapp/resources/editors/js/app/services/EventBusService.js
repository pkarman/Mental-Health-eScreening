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
        var _MODAL_CLOSING_ = '_MODAL_CLOSING_';
    
        $rootScope.$on("$locationChangeStart",function(event, next, current){
            $rootScope.$broadcast(_LOCATION_CHANGED_, {'next': next, 'current': current});
        });
        
        //subscribe to location change notification
        function onLocationChange($scope, handler) {
            return $scope.$on(_LOCATION_CHANGED_, function(event, args) {
               handler(args.next, args.current);
            });
        };
        
        //subscribe to modal closing
        function onModalClose($scope, modalId, handler, unsubscribe) {
            var unsub = $scope.$on(_MODAL_CLOSING_, function(event, args) {
               if(args.id && modalId && args.id == modalId){
            	   handler();
            	   if(unsubscribe && unsub){
            		   unsub();
            	   }
               }
            });
            return unsub;
        };
        
        //publish that modal is closing
        function modalClosing(modalId) {
        	$rootScope.$broadcast(_MODAL_CLOSING_, {id: modalId});
        };
        
        return {
            /**
             * @param $scope 
             * @param handler - function will be passed: next and current objects
             * @return unsubscribe function - call it to unsubscribe to modal close event
             */
            onLocationChange: onLocationChange,
            
            /**
             * @param $scope
             * @param modalId - the unique ID of the modal to listen for
             * @param handler - function to be called before modal is closed
             * @param unsubscribe - if true then the handler will be unsubscribed after it is done executing
             * @return unsubscribe function - call it to unsubscribe to modal close event
             */
            onModalClose: onModalClose,
            
            /**
             * @param modalId - the unique ID of the modal that is about to close
             */
            modalClosing: modalClosing
        };
    }])