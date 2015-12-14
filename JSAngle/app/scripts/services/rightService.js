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
			initCrudService(this, '/right', $http);
		});
})();
