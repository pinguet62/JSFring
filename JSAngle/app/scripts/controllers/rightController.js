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
			
			$scope.openShowDialog = function() {
				ngDialog.open({ template: 'views/showDialog.html' });
			};
			$scope.openUpdateDialog = function() {
				ngDialog.open({ template: 'views/updateDialog.html' });
			};
			$scope.openDeleteDialog = function() {
				ngDialog.open({ template: 'views/deleteDialog.html' });
			};
			
			// Column titles
			$scope.gridOptions.columnDefs = [
				{ field: 'code', displayName: $translate.instant('right.code') },
				{ field: 'title', displayName: $translate.instant('right.title') },
				{ name: 'action', displayName: 'Action',
					cellTemplate:
						'<button id="show" type="button" ng-click="grid.appScope.openShowDialog()">Show</button>' +
						'<button id="update" type="button" ng-click="grid.appScope.openUpdateDialog()">Update</button>' + 
						'<button id="delete" type="button" ng-click="grid.appScope.openDeleteDialog()">Delete</button>' },
			];
		}]);
})();
