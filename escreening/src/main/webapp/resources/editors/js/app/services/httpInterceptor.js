/**
 * 
 */
(function(){
  var httpInterceptor = function ($provide, $httpProvider, $rootScope) {
    $provide.factory('httpInterceptor', function ($q) {
      return {
        response: function (response) {
          return response || $q.when(response);
        },
        responseError: function (rejection) {
          if(rejection.status === 401) {
            // you are not authorized.
        	 
          }
          /* We can ignore most non-200 status messages, but some,
             the egregious ones, we'll collect for display.*/
          switch (rejection.status){
          		case 500:
          			// Server-side processing error.
          			$rootScope.errors.push("The server encountered an error processing the request.");
          			break;
          		case 503:
          			// Gateway Timeout.
          			$rootScope.errors.push("The request timed out contacting the server. Please check network connection.");
          			break;
          		case 403:
          		case 404:
          			// This should be caught by the server, and the user shunted to the global errors page, so...
          			// just in case...
          			$rootScope.errors.push("The requested resource is unavailable, you don't have permission to access it.  Please contact your administrator.");
          			break;
          		case 301:
          			$rootScope.errors.push("The requested resource has been permanently moved.  Please contact your administrator.");
          			break;
          		case 400:
          			// Messed up request.  Our (developers) bad.
          			$rootScope.errors.push("The server refused the request. Please contact your administrator.")
          			break;
          		
          }
          return $q.reject(rejection);
        }
      };
    });
    $httpProvider.interceptors.push('httpInterceptor');
  };
  angular.module('Editors').config(httpInterceptor);
}());