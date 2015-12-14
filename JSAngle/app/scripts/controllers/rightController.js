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
		.controller('rightController', ['$scope', 'rightService', '$translate', function($scope, rightService, $translate) {
			initCrudController($scope, rightService);
			
			// Column titles
			$scope.gridOptions.columnDefs = [
				{ name: 'code', displayName: $translate.instant('right.code') },
				{ name: 'title', displayName: $translate.instant('right.title') },
			];
		}]);
})();
