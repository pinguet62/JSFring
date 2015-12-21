(function() {
	'use strict';

	/**
	 * @ngdoc function
	 * @name jsangleApp.controller:rightController
	 * @description
	 * # rightController
	 * Controller of the jsangleApp
	 */
	angular.module('jsangleApp')
		.controller('rightController', ['$scope', 'rightService', '$translate', 'ngDialog', function($scope, rightService, $translate, ngDialog) {
			initCrudController($scope, rightService);
			
			$scope.openShowDialog = function(entity) {
				$scope.selectedValue = entity;
				ngDialog.open({
					template: 'views/right/show.html',
					scope: $scope
				});
			};
			$scope.openUpdateDialog = function(entity) {
				$scope.selectedValue = entity;
				ngDialog.open({
					template: 'views/right/update.html',
					scope: $scope
				});
			};
			// $scope.openDeleteDialog = function() {
				// ngDialog.open({ template: 'views/right/delete.html' });
			// };
			
			// Column titles
			$scope.gridOptions.columnDefs = [
				{ field: 'code', displayName: $translate.instant('right.code') },
				{ field: 'title', displayName: $translate.instant('right.title') },
				{ name: 'action', displayName: 'Action',
					cellTemplate:
						'<button id="show" type="button" ng-click="grid.appScope.openShowDialog(row.entity)">'+$translate.instant('grid.actions.show')+'</button>' +
						'<button id="update" type="button" ng-click="grid.appScope.openUpdateDialog(row.entity)">'+$translate.instant('grid.actions.update')+'</button>' /*+ 
						'<button id="delete" type="button" ng-click="grid.appScope.openDeleteDialog(row.entity)">'+$translate.instant('grid.actions.delete')+'</button>'*/ },
			];
		}]);
})();
