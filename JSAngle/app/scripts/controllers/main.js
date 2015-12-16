(function() {
	'use strict';
	
	angular.module('jsangleApp')
		.controller('MainCtrl', ['$scope', 'ngDialog', function($scope, ngDialog) {
			
			$scope.data = {
				source: [
					{key: 1, value: 'a'},
					{key: 2, value: 'b'},
					{key: 3, value: 'c'},
					{key: 4, value: 'd'},
					{key: 5, value: 'e'},
					{key: 6, value: 'f'},
					{key: 7, value: 'g'},
					{key: 8, value: 'h'},
					{key: 9, value: 'i'},
					{key: 10, value: 'j'},
					{key: 11, value: 'k'},
					{key: 12, value: 'l'},
					{key: 13, value: 'm'},
					{key: 14, value: 'n'},
				],
				target: [
					{key: 98, value: 'i'},
					{key: 99, value: 'j'},
				],
			};
			
		}]);
})();
