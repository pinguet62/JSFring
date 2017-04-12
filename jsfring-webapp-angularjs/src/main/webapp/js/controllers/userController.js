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
		.controller('userController', ['$scope', 'userService', '$translate', 'ngDialog', 'profileService', function($scope, userService, $translate, ngDialog, profileService) {
			initCrudController($scope, userService);
			
			// Column titles
			$scope.gridOptions.columnDefs = [
				{ field: 'email', displayName: $translate.instant('user.email') },
				{ field: 'active', displayName: $translate.instant('user.active') },
				{ field: 'lastConnection', displayName: $translate.instant('user.lastConnection') },
				{ name: 'action', displayName: 'Action',
					cellTemplate:
						'<button id="show" type="button" ng-click="grid.appScope.openShowDialog(row.entity)">'+$translate.instant('grid.actions.show')+'</button>' +
						'<button id="update" type="button" ng-click="grid.appScope.openUpdateDialog(row.entity)">'+$translate.instant('grid.actions.update')+'</button>' /*+ 
						'<button id="delete" type="button" ng-click="grid.appScope.openDeleteDialog(row.entity)">'+$translate.instant('grid.actions.delete')+'</button>'*/ },
			];
			
			// Actions
			$scope.openCreateDialog = function() {
				$scope.selectedValue = { profiles: [] };
				$scope.initProfilesAssociation(); // Profile association
				ngDialog.open({
					template: 'views/user/create.html',
					scope: $scope
				});
			};
			$scope.openShowDialog = function(entity) {
				$scope.selectedValue = entity;
				$scope.initProfilesAssociation(); // Profile association
				ngDialog.open({
					template: 'views/user/show.html',
					scope: $scope
				});
			};
			$scope.openUpdateDialog = function(entity) {
				$scope.selectedValue = entity;
				$scope.initProfilesAssociation(); // Profile association
				ngDialog.open({
					template: 'views/user/update.html',
					scope: $scope
				});
			};
			// $scope.openDeleteDialog = function() {
				// ngDialog.open({ template: 'views/user/delete.html' });
			// };
			
			// TODO synchrone
			var profiles = null;
			profileService.list(function(results) {
				profiles = results;
			});
			
			// Profile association
			$scope.itemKeyConverter = function(x) { return x.id; };
			$scope.itemValueConverter = function(x) { return x.title; };
			$scope.initProfilesAssociation = function() {
				$scope.profilesAssociation = {
					source: [],
					target: []
				};
				// TODO Synchrone: var profiles = rightService.list();
				for (var i = 0 ; i < profiles.length ; i ++)
					if ($scope.selectedValue.profiles.indexOf($scope.itemKeyConverter(profiles[i])) === -1)
						$scope.profilesAssociation.source.push(profiles[i]);
					else
						$scope.profilesAssociation.target.push(profiles[i]);
			};
			
			$scope.update = function() {
				// Profile association
				$scope.selectedValue.profiles = [];
				for (var i = 0 ; i < profilesAssociation.target.length ; i ++)
					$scope.selectedValue.rights.push($scope.itemKeyConverter($scope.profilesAssociation.target[i]));
				
				userService.update($scope.selectedValue);
			};
		}]);
})();
