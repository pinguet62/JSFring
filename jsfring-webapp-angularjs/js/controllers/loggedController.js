(function() {
	'use strict';

	/**
	 * @ngdoc function
	 * @name jsangleApp.controller:loggedController
	 * @description
	 * # loggedController
	 * Controller of the jsangleApp
	 */
	angular.module('jsangleApp')
		.controller('loggedController', ['$scope', '$routeParams', '$http', '$location', function($scope, $routeParams, $http, $location) {

			let token = $routeParams.access_token
			$http.defaults.headers.common.Authorization = 'Bearer ' + token // Save for webservice calls
			$location.path('/') // Reset URL

		}]);
})();