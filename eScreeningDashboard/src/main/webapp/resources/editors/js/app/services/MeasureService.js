/* This should later be changed to the QuestionService */
angular.module('EscreeningDashboardApp.services.MeasureService', ['restangular'])
	.factory('MeasureService', ['Restangular', function (Restangular) {
		"use strict";

		var restAngular = Restangular.withConfig(function (config) {
				config.setBaseUrl('services/');
				config.setRequestSuffix('.json');
			});

		return restAngular.service("questions");

	}]);
