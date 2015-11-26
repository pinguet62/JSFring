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
			var ms = this;
			
			ms.list = function() {
				// Mock: webservice
				return [
					{id: 1, title: "Users admin"},
					{id: 2, title: "Profiles admin"},
					{id: 3, title: "Rights admin"},
					{id: 4, title: "44"},
					{id: 5, title: "55"},
					{id: 6, title: "66"},
					{id: 7, title: "77"},
					{id: 8, title: "88"},
					{id: 9, title: "99"}
				];
			};
			
			ms.find = function(query) {
				var first = query.pageSize * query.currentPage
				var last = query.pageSize * (query.currentPage + 1)
				return {
					'values': ms.list().slice(first, last),
					'totalCount': ms.list().length
				};
			}
		});
})();
