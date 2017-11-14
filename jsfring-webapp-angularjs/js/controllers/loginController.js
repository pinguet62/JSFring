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
			let oauthUrl = window.location.origin.replace('jsfring-webapp-angularjs', 'jsfring-webservice') // TODO config variable
			let query = $httpParamSerializer({
				'client_id': 'clientId',
				'response_type': 'token',
				'scope': 'read',
				'redirect_uri': window.location.protocol + "//" + window.location.host + window.location.pathname
			})
			let authorizeUrl = oauthUrl + '/oauth/authorize' + '?' + query
			window.location.replace(authorizeUrl)

		}]);
})();