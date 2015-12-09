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
		.service('profileService', function($http) {
			var ms = this;
			
			ms.list = function(callback) {
				$http
					.get("http://localhost:8080/webservice/rest/profile/")
					.success(function(response) {
						callback && callback(response);
					});
			};
			
			ms.listMocked = function() {
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
			
			ms.findMocked = function(paginationOptions) {
				var totalResults = ms.list();
				// Filter
				var filteredResults = totalResults;
				// Sort
				var sortedResults = filteredResults;
				if (paginationOptions.sort != null) {
					// Order
					var order = paginationOptions.sort.order === 'asc' ? 1 : -1;
					// Field
					var comparator;
					if (paginationOptions.sort.name == 'id')
						comparator = function(a, b) { return order * (a.id < b.id ? -1 : +1); };
					else if (paginationOptions.sort.name == 'title')
						comparator = function(a, b) { return order * a.title.localeCompare(b.title); };
					// Apply
					sortedResults = sortedResults.sort(comparator);
				}
				// Pagination
				var first = paginationOptions.pageSize * (paginationOptions.pageNumber - 1);
				var last = paginationOptions.pageSize * paginationOptions.pageNumber;
				var results = sortedResults.slice(first, last);
				// Result
				return {
					'values': results,
					'totalCount': filteredResults.length
				};
			}
		});
})();
