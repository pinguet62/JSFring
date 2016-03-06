(function() {
	'use strict';
	
	/**
	 * @param array The array with values.
	 * @param predicate The predicate to test objects.
	 * @return The index of object. -1 if not found.
	 */
	function find(array, predicate) {
		for (var i = 0 ; i < array.length ; i ++)
			if (predicate(array[i], i))
				return i;
		return -1;
	}
	
	angular.module('jsangleApp')
		.controller('picklistController', ['$scope', 'ngDialog', function($scope, ngDialog) {
			// Primitive type
			$scope.primitives = {
				source: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14],
				target: [98, 99]
			};
			// Complex object
			$scope.complexObjects = {
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
				]
			};
			$scope.itemKeyConverter = function(x) { return x.key; };
			$scope.itemValueConverter = function(x) { return x.value; };
		}]);
})();
