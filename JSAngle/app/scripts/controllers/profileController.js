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
		.controller('profileController', ['$scope', 'profileService', '$translate', 'ngDialog', function($scope, profileService, $translate, ngDialog) {
			initCrudController($scope, profileService);
			
			$scope.openShowDialog = function(entity) {
				ngDialog.open({
					template: 'views/profile/show.html',
					controller: ['$scope', function($scope) {
						$scope.profile = entity;
					}]
				});
			};
			$scope.openUpdateDialog = function(entity) {
				ngDialog.open({
					template: 'views/profile/update.html',
					controller: ['$scope', function($scope) {
						$scope.profile = entity;
					}]
				});
			};
			// $scope.openDeleteDialog = function() {
				// ngDialog.open({ template: 'views/profile/delete.html' });
			// };
			
			// Column titles
			$scope.gridOptions.columnDefs = [
				{ field: 'id', displayName: $translate.instant('profile.id') },
				{ field: 'title', displayName: $translate.instant('profile.title') },
				{ name: 'action', displayName: 'Action',
					cellTemplate:
						'<button id="show" type="button" ng-click="grid.appScope.openShowDialog(row.entity)">'+$translate.instant('grid.actions.show')+'</button>' +
						'<button id="update" type="button" ng-click="grid.appScope.openUpdateDialog(row.entity)">'+$translate.instant('grid.actions.update')+'</button>' /*+ 
						'<button id="delete" type="button" ng-click="grid.appScope.openDeleteDialog(row.entity)">'+$translate.instant('grid.actions.delete')+'</button>'*/ },
			];
		}]);
})();
