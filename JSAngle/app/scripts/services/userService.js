(function() {
	'use strict';

	/**
	 * @ngdoc service
	 * @name jsangleApp.userService
	 * @description
	 * # userService
	 * Service in the jsangleApp.
	 */
	angular.module('jsangleApp')
		.service('userService', function($http) {
			var ms = this;
			
			ms.list = function(callback) {
				$http
					.get("http://localhost:8080/rest/user/")
					.success(function(response) {
						callback && callback(response);
					});
			};
			
			ms.find = function(paginationOptions, callback) {
				$http
					.get("http://localhost:8080/rest/user/find", { params: paginationOptions })
					.success(function(response) {
						callback && callback(response);
					});
			};
		});
})();
