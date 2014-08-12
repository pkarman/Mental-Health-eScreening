/**
 * 
 */
Editors.factory('AppNameService', ['$window',function($window) {
	alert('howdy');
  return {
      appName: $window.location.href.indexOf('/escreeningdashboard/') > -1 ? '/escreeningdashboard/' : '/escreeningdashboard-test/' 
  };
}]);