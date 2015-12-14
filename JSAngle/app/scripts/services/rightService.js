(function() {
	'use strict';

	/**
	 * @ngdoc service
	 * @name jsangleApp.rightService
	 * @description
	 * # rightService
	 * Service in the jsangleApp.
	 */
	angular.module('jsangleApp')
		.service('rightService', function($http) {
			var ms = this;
			
			ms.list = function(callback) {
				$http
					.get("http://localhost:8080/rest/right/")
					.success(function(response) {
						callback && callback(response);
					});
			};
			
			ms.find = function(paginationOptions, callback) {
				$http
					.get("http://localhost:8080/rest/right/find", { params: paginationOptions })
					.success(function(response) {
						callback && callback(response);
					});
			};
		});
})();
