(function() {
	'use strict';

	/**
	 * @ngdoc function
	 * @name jsangleApp.controller:userController
	 * @description
	 * # userController
	 * Controller of the jsangleApp
	 */
	angular.module('jsangleApp')
		.controller('userController', ['$scope', 'userService', '$translate', 'ngDialog', function($scope, userService, $translate, ngDialog) {
			initCrudController($scope, userService);
			
			// Column titles
			$scope.gridOptions.columnDefs = [
				{ field: 'login', displayName: $translate.instant('user.login') },
				{ field: 'email', displayName: $translate.instant('user.email') },
				{ field: 'lastConnection', displayName: $translate.instant('user.lastConnection') },
				{ name: 'action', displayName: 'Action',
					cellTemplate:
						'<button id="show" type="button" ng-click="grid.appScope.openShowDialog(row.entity)">'+$translate.instant('grid.actions.show')+'</button>' +
						'<button id="update" type="button" ng-click="grid.appScope.openUpdateDialog(row.entity)">'+$translate.instant('grid.actions.update')+'</button>' /*+ 
						'<button id="delete" type="button" ng-click="grid.appScope.openDeleteDialog(row.entity)">'+$translate.instant('grid.actions.delete')+'</button>'*/ },
			];
			
			$scope.openShowDialog = function(entity) {
				ngDialog.open({
					template: 'views/user/show.html',
					controller: ['$scope', function($scope) {
						$scope.user = entity;
					}]
				});
			};
			$scope.openUpdateDialog = function(entity) {
				ngDialog.open({
					template: 'views/user/update.html',
					controller: ['$scope', function($scope) {
						$scope.user = entity;
					}]
				});
			};
			// $scope.openDeleteDialog = function() {
				// ngDialog.open({ template: 'views/user/delete.html' });
			// };
		}]);
})();
