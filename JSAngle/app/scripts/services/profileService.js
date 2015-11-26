(function() {
	'use strict';

	/**
	 * @ngdoc service
	 * @name jsangleApp.profileService
	 * @description
	 * # profileService
	 * Service in the jsangleApp.
	 */
	angular.module('jsangleApp')
		.service('profileService', function() {
			this.list = function() {
				// Mock: webservice
				return [
					{id: 1, title: "Users admin"},
					{id: 2, title: "Profiles admin"},
					{id: 3, title: "Rights admin"}
				];
			};
		});
})();
