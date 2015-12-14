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
		.controller('profileController', ['$scope', 'profileService', function($scope, profileService) {
			
			var lazy = true;
			
			var paginationOptions = {
				page: 0,
				pageSize: 2,
				sort: null,
				filter: null
			};
			
			/**
			 * Executed after: pagination, sorting or filtering
			 * Call the corresponding service.
			 */
			var getPage = function() {
				if (lazy) {
					profileService.find(paginationOptions, function(results) {
						$scope.gridOptions.data = results.results;
						$scope.gridOptions.totalItems = results.total;
					});
				} else {
					profileService.list(function(results) {
							$scope.gridOptions.data = results;
							$scope.gridOptions.totalItems = results.length;
						});
				}
			}
			
			$scope.gridOptions = {
				// Pagination
				paginationPageSizes: [2, 5, 10],
				paginationPageSize: paginationOptions.pageSize,
				// Lazy-loading
				useExternalPagination: $scope.lazy,
				onRegisterApi: function(gridApi) {
					// Pagination
					gridApi.pagination.on.paginationChanged($scope, function(newPage, pageSize) {
						paginationOptions.page = newPage - 1;
						paginationOptions.pageSize = pageSize;
						getPage();
					});
					// Sorting
					gridApi.core.on.sortChanged($scope, function(grid, sortColumns) {
						if (sortColumns.length === 0)
							paginationOptions.sort = null;
						else
							paginationOptions.sort = {
								name: sortColumns[0].name,
								order: sortColumns[0].sort.direction
							};
						getPage();
					});
					// TODO Filtering
				}
			};
			
			getPage(); // initial load
		}]);
})();
