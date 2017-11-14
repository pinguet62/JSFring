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
		.controller('profileController', ['$scope', 'profileService', '$translate', 'ngDialog', 'rightService', function($scope, profileService, $translate, ngDialog, rightService) {
			initCrudController($scope, profileService);
			
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
			
			// Actions
			$scope.openCreateDialog = function() {
				$scope.selectedValue = { rights: [] };
				$scope.initRightsAssociation(); // Right association
				ngDialog.open({
					template: 'views/profile/create.html',
					scope: $scope
				});
			};
			$scope.openShowDialog = function(entity) {
				$scope.selectedValue = entity;
				$scope.initRightsAssociation(); // Right association
				ngDialog.open({
					template: 'views/profile/show.html',
					scope: $scope
				});
			};
			$scope.openUpdateDialog = function(entity) {
				$scope.selectedValue = entity;
				$scope.initRightsAssociation(); // Right association
				ngDialog.open({
					template: 'views/profile/update.html',
					scope: $scope
				});
			};
			// $scope.openDeleteDialog = function() {
				// ngDialog.open({ template: 'views/profile/delete.html' });
			// };
			
			// TODO synchrone
			let rights = null;
			rightService.list(function(results) {
				rights = results;
			});
			
			// Right association
			$scope.itemKeyConverter = function(x) { return x.code; };
			$scope.itemValueConverter = function(x) { return x.title; };
			$scope.initRightsAssociation = function() {
				$scope.rightsAssociation = {
					source: [],
					target: []
				};
				// TODO Synchrone: let rights = rightService.list();
				for (let i = 0 ; i < rights.length ; i ++)
					if ($scope.selectedValue.rights.indexOf($scope.itemKeyConverter(rights[i])) === -1)
						$scope.rightsAssociation.source.push(rights[i]);
					else
						$scope.rightsAssociation.target.push(rights[i]);
			};
			
			$scope.update = function() {
				// Right association
				$scope.selectedValue.rights = [];
				for (let i = 0 ; i < $scope.rightsAssociation.target.length ; i ++)
					$scope.selectedValue.rights.push($scope.itemKeyConverter($scope.rightsAssociation.target[i]));
				
				profileService.update($scope.selectedValue);
			};
		}]);
})();
