(function() {
	'use strict';

	/**
	 * @ngdoc service
	 * @name jsangleApp.rightService
	 * @description
	 * # rightService
	 * Service in the jsangleApp.
	 */
	angular.module('jsangleApp')
		.service('rightService', function($http) {
			var ms = this;
			
			ms.list = function(callback) {
				$http
					.get("http://localhost:8080/webservice/rest/right/")
					.success(function(response) {
						callback && callback(response);
					});
			};
			
			ms.listMocked = function() {
				return [
					{code: "code1", title: "title 9-1"},
					{code: "code2", title: "title 8-2"},
					{code: "code3", title: "title 7-3"},
					{code: "code4", title: "title 6-4"},
					{code: "code5", title: "title 5-5"},
					{code: "code6", title: "title 4-6"},
					{code: "code7", title: "title 3-7"},
					{code: "code8", title: "title 2-8"},
					{code: "code9", title: "title 1-9"}
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
					if (paginationOptions.sort.name == 'code')
						comparator = function(a, b) { return order * a.code.localeCompare(b.code); };
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
