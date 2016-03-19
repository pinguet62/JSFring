(function() {
	'use strict';

	/**
	 * @ngdoc function
	 * @name jsangleApp.controller:loginController
	 * @description
	 * # loginController
	 * Controller of the jsangleApp
	 * @see http://angularjs-oauth.github.io/oauth-ng/
	 */
	angular.module('jsangleApp')
		.controller('loginController', ['$scope', '$httpParamSerializer', function($scope, $httpParamSerializer) {

			//login()
			var oauthUrl = 'http://jsfring-webservice.herokuapp.com'
			var query = $httpParamSerializer({
				'client_id': 'clientId',
				'response_type': 'token',
				'scope': 'read',
				'redirect_uri': 'http://localhost:8080'
			})
			var authorizeUrl = oauthUrl + '/oauth/authorize' + '?' + query
			window.location.replace(authorizeUrl)

		}]);
})();