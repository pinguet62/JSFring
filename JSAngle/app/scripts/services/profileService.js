(function() {
	'use strict';

	/**
	 * @ngdoc service
	 * @name jsangleApp.profileService
	 * @description
	 * # profileService
	 * Service in the jsangleApp.
	 */
	angular.module('jsangleApp')
		.service('profileService', function($http) {
			var ms = this;
			
			ms.list = function(callback) {
				$http
					.get("http://localhost:8080/rest/profile/")
					.success(function(response) {
						callback && callback(response);
					});
			};
			
			ms.find = function(paginationOptions, callback) {
				$http
					.get("http://localhost:8080/rest/profile/find", { params: paginationOptions })
					.success(function(response) {
						callback && callback(response);
					});
			};
		});
})();
