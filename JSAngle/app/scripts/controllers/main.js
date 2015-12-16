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
				]
			};
			
			$scope.selectedSources = [];
			$scope.selectedTargets = [];
			
			$scope.selectedSrcToTgt = function() {
				for (var i = 0; i < $scope.selectedSources.length ; i ++) {
					var selected = $scope.selectedSources[i];
					var f = find($scope.data.source, function(value, index) { return value.key === selected.key; });
					$scope.data.source.splice(f, 1);
					$scope.data.target.push(selected);
				}
			};
			$scope.selectedTgtToSrc = function() {
				for (var i = 0; i < $scope.selectedTargets.length ; i ++) {
					var selected = $scope.selectedTargets[i];
					$scope.data.source.push(selected);
					var f = find($scope.data.target, function(value, index) { return value.key === selected.key; });
					$scope.data.target.splice(f, 1);
				}
			};
			
		}]);
})();
