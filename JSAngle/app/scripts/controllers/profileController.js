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
		.controller('profileController', function($scope) {
			// $scope.profiles = profileService.list();
			$scope.profiles = [
					{id: 1, title: "Users admin"},
					{id: 2, title: "Profiles admin"},
					{id: 3, title: "Rights admin"}
				];
		});
})();
