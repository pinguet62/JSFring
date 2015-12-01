(function() {
	'use strict';

	/**
	 * @ngdoc function
	 * @name jsangleApp.controller:MainCtrl
	 * @description
	 * # MainCtrl
	 * Controller of the jsangleApp
	 */
	angular.module('jsangleApp', ['ui.bootstrap', 'ui.grid', 'ui.grid.pagination'])
		.controller('MainCtrl', function() {
			this.awesomeThings = [
				'HTML5 Boilerplate',
				'AngularJS',
				'Karma'
			];
		});
})();
