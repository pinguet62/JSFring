(function() {
	'use strict';

	/**
	 * @ngdoc function
	 * @name jsangleApp.controller:userController
	 * @description
	 * # userController
	 * Controller of the jsangleApp
	 */
	angular.module('jsangleApp')
		.controller('userController', ['$scope', 'userService', '$translate', function($scope, userService, $translate) {
			initCrudController($scope, userService);
			
			// Column titles
			$scope.gridOptions.columnDefs = [
				{ name: 'login', displayName: $translate.instant('user.login') },
				{ name: 'email', displayName: $translate.instant('user.email') },
				{ name: 'lastConnection', displayName: $translate.instant('user.lastConnection'), cellFilter: 'date: medium' },
			];
		}]);
})();
