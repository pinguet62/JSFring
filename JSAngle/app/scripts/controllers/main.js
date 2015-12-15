(function() {
	'use strict';
	
	angular.module('jsangleApp')
		.controller('MainCtrl', ['$scope', 'ngDialog', function($scope, ngDialog) {
			$scope.openShowDialog = function() {
				ngDialog.open({ template: 'views/showDialog.html' });
			};
		}]);
})();
