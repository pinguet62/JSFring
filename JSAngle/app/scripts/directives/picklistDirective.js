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
		.directive('picklist', function() {
			return {
				restrict: 'E',
				replace: true,
				templateUrl: 'views/picklist.html',
				scope: {
					'value': '=',
					'itemKey': '=',
					'itemValue': '='
				},
				controller: ['$scope', function($scope) {
					if (angular.isUndefined($scope.itemKey))
						$scope.itemKey = function(x) { return x; };
					if (angular.isUndefined($scope.itemValue))
						$scope.itemValue = function(x) { return x; };
					
					$scope.selectedSources = [];
					$scope.selectedTargets = [];
					
					$scope.selectedSrcToTgt = function() {
						for (var i = 0; i < $scope.selectedSources.length ; i ++) {
							var selected = $scope.selectedSources[i];
							var f = find($scope.value.source, function(value, index) { return $scope.itemKey(value) === $scope.itemKey(selected); });
							$scope.value.source.splice(f, 1);
							$scope.value.target.push(selected);
						}
					};
					$scope.allSrcToTgt = function() {
						$scope.value.target = $scope.value.target.concat($scope.value.source);
						$scope.value.source = [];
					};
					$scope.selectedTgtToSrc = function() {
						for (var i = 0; i < $scope.selectedTargets.length ; i ++) {
							var selected = $scope.selectedTargets[i];
							$scope.value.source.push(selected);
							var f = find($scope.value.target, function(value, index) { return $scope.itemKey(value) === $scope.itemKey(selected); });
							$scope.value.target.splice(f, 1);
						}
					};
					$scope.allTgtToSrc = function() {
						$scope.value.source = $scope.value.source.concat($scope.value.target);
						$scope.value.target = [];
					};
				}],
			};
		});
})();
