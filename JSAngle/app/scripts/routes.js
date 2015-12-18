(function() {
	'use strict';

	/**
	 * Configuration of routes.
	 */
	angular.module('jsangleApp')
		.config(['$routeProvider', function($routeProvider) {
			$routeProvider
				// Admin
				.when('/admin/right', {
					templateUrl: 'views/crud.html',
					controller: 'rightController'
				})
				.when('/admin/profile', {
					templateUrl: 'views/crud.html',
					controller: 'profileController'
				})
				.when('/admin/user', {
					templateUrl: 'views/crud.html',
					controller: 'userController'
				})
				// Sample
				.when('/sample/picklist', {
					templateUrl: 'views/sample/picklist.html',
					controller: 'picklistController'
				})
				// Other
				.otherwise({
					redirectTo: '/'
				});
		}]);
})();
