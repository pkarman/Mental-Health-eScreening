'use strict';

/* Constants */

// Context path variable needs to be declared in each jsp page.
//<script>var contextPath = "${ pageContext.request.contextPath }"</script> 

var escreenConstants = angular.module('escreenConstants', []);

escreenConstants.constant('escreenSettings', {
	contextPath: contextPath
});