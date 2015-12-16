(function() {
	'use strict';

	/**
	 * Main module of the application.
	 */
	angular.module('jsangleApp', [
		'ngRoute',
		'pascalprecht.translate',
		'ui.bootstrap',
		'ngDialog',
		'ui.grid', 'ui.grid.pagination', 'ui.grid.exporter'
	]);
})();
