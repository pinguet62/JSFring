(function() {
	'use strict';
	
	angular.module('jsangleApp')
		.directive('picklist', function() {
			return {
				restrict: 'E',
				replace: true,
				templateUrl: 'views/picklist.html',
				compile: function compile(elements, attrs, transclude) {
					return {
						pre: function(scope, element, attrs, controller) {},
						post: function(scope, element, attrs, controller) {},
					};
				},
				link: function(scope, elements, attrs) {
				},
			};
		});
})();
