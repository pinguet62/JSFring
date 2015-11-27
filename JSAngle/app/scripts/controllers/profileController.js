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
		.controller('profileController', ['$scope', 'profileService', function($scope, profileService) {
			// Page state
			$scope.currentPage = 0;
			$scope.pageSize = 3;
			
			$scope.buildFilter = function() {
				return {
					'currentPage': $scope.currentPage,
					'pageSize': $scope.pageSize
				};
			}
			
			$scope.refresh = function() {
				// Eager loading
				//$scope.profiles = profileService.list();
				//$scope.totalCount = $scope.profiles.size();
				// Lazy loading
				var results = profileService.find($scope.buildFilter());
				$scope.profiles = results.values;
				$scope.totalCount = results.totalCount;
			}
			$scope.refresh(); // first load

			// Events
			$scope.pageChanged = $scope.refresh;
		}]);
})();
