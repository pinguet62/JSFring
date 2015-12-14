(function() {
	'use strict';

	/**
	 * @ngdoc function
	 * @name jsangleApp.controller:profileController
	 * @description
	 * # profileController
	 * Controller of the jsangleApp
	 */
	angular.module('jsangleApp')
		.controller('profileController', ['$scope', 'profileService', '$translate', function($scope, profileService, $translate) {
			initCrudController($scope, profileService);
			
			// Column titles
			$scope.gridOptions.columnDefs = [
				{ name: 'id', displayName: $translate.instant('profile.id') },
				{ name: 'title', displayName: $translate.instant('profile.title') },
			];
		}]);
})();
