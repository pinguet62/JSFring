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
			
			$scope.openShowDialog = function() {
				ngDialog.open({ template: 'views/user/show.html' });
			};
			$scope.openUpdateDialog = function() {
				ngDialog.open({ template: 'views/user/update.html' });
			};
			// $scope.openDeleteDialog = function() {
				// ngDialog.open({ template: 'views/user/delete.html' });
			// };
			
			// Column titles
			$scope.gridOptions.columnDefs = [
				{ field: 'login', displayName: $translate.instant('user.login') },
				{ field: 'email', displayName: $translate.instant('user.email') },
				{ field: 'lastConnection', displayName: $translate.instant('user.lastConnection') },
				{ name: 'action', displayName: 'Action',
					cellTemplate:
						'<button id="show" type="button" ng-click="grid.appScope.openShowDialog()">'+$translate.instant('grid.actions.show')+'</button>' +
						'<button id="update" type="button" ng-click="grid.appScope.openUpdateDialog()">'+$translate.instant('grid.actions.update')+'</button>' /*+ 
						'<button id="delete" type="button" ng-click="grid.appScope.openDeleteDialog()">'+$translate.instant('grid.actions.delete')+'</button>'*/ },
			];
		}]);
})();
