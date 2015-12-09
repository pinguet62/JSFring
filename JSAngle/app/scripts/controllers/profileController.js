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
			
			var lazy = false;
			
			var paginationOptions = {
				pageNumber: 1,
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
					var results = profileService.find(paginationOptions);
					$scope.gridOptions.data = results.values;
					$scope.gridOptions.totalItems = results.totalCount;
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
				useExternalPagination: true,
				onRegisterApi: function(gridApi) {
					// Pagination
					gridApi.pagination.on.paginationChanged($scope, function(newPage, pageSize) {
						paginationOptions.pageNumber = newPage;
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
