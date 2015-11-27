(function() {
	'use strict';

	/**
	 * @ngdoc function
	 * @name jsangleApp.controller:MainCtrl
	 * @description
	 * # MainCtrl
	 * Controller of the jsangleApp
	 */
	angular.module('jsangleApp', ['ui.bootstrap'])
		.controller('MainCtrl', function() {
			this.awesomeThings = [
				'HTML5 Boilerplate',
				'AngularJS',
				'Karma'
			];
		});
})();
