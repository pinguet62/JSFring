(function() {
	'use strict';

	/**
	 * @ngdoc service
	 * @name jsangleApp.userService
	 * @description
	 * # userService
	 * Service in the jsangleApp.
	 */
	angular.module('jsangleApp')
		.service('userService', function($http) {
			var ms = this;
			
			ms.list = function(callback) {
				$http
					.get("http://localhost:8080/webservice/rest/user/")
					.success(function(response) {
						callback && callback(response);
					});
			};
			
			ms.listMocked = function() {
				return [
					{id: 1, email: "email 1", lastConnection: '2011-01-21T11:21:31.401Z'},
					{id: 2, email: "email 2", lastConnection: '2012-02-22T1:22:32.40Z2'},
					{id: 3, email: "email 3", lastConnection: '2013-03-23T1:23:33.40Z3'},
					{id: 4, email: "email 4", lastConnection: '2014-04-24T1:24:34.40Z4'},
					{id: 5, email: "email 5", lastConnection: '2015-05-25T1:25:35.40Z5'},
					{id: 6, email: "email 6", lastConnection: '2016-06-26T1:26:36.40Z6'},
					{id: 7, email: "email 7", lastConnection: '2017-07-27T1:27:37.407Z'},
					{id: 8, email: "email 8", lastConnection: '2018-08-28T1:28:38.408Z'},
					{id: 9, email: "email 9", lastConnection: '2019-09-29T1:29:39.409Z'}
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
