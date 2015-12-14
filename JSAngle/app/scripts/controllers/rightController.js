(function() {
	'use strict';

	/**
	 * @ngdoc function
	 * @name jsangleApp.controller:rightController
	 * @description
	 * # rightController
	 * Controller of the jsangleApp
	 */
	angular.module('jsangleApp')
		.controller('rightController', ['$scope', 'rightService', function($scope, rightService) {
			initCrudController($scope, rightService);
		}]);
})();
