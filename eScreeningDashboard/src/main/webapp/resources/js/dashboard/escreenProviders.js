'use strict';

/* Providers */

var escreenProviders = angular.module('escreenProviders', ['escreenConstants']);

escreenProviders.provider('clinicList', function(escreenSettings) {

	this.$get = function($http) {
		return {
			getResultList : function() {
				return $http({
					method : "GET",
					url : escreenSettings.contextPath + "/assessAdmin/services/getclinicsForDropdown",
					responseType : "json"
				}).then(function(result) {
					return result.data;
				});
			}
		};
	};
});

escreenProviders.provider('clinicianList', function(escreenSettings) {

	this.$get = function($http) {
		
		return {
			getResultList : function(clinicId) {
				var endPoint = escreenSettings.contextPath + "/assessAdmin/services/clinics/0/clinicians";
				console.log("clinicId: " + clinicId);
				var newEndPoint = endPoint.replace("clinics/0", "clinics/" + clinicId);
				console.log(newEndPoint);
				
				return $http({
					method : "GET",
					url : newEndPoint,
					responseType : "json"
				}).then(function(result) {
					return result.data;
				});
			}
		};
	};
});

escreenProviders.provider('veteranList', function(escreenSettings) {

	this.$get = function($http) {

		return {
			getResultList : function(lastName, ssnLastFour) {
				var requestPayload = JSON.stringify({ "lastName": lastName, "ssnLastFour" : ssnLastFour });

				return $http({
					method : "POST",
					url : escreenSettings.contextPath + "/dashboard/veteranSearch/services/veterans/search3",
					data : requestPayload, 
					headers: {'Content-Type': 'application/json'}, 
					responseType : "json" 
				}).then(function(result) {
					return result.data;
				});
			}
		};
	};
});

