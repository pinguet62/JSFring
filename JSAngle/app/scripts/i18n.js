(function() {
	'use strict';

	/**
	 * Configuration of i18n translations.
	 */
	angular.module('jsangleApp')
		.config(['$translateProvider', function($translateProvider) {
			$translateProvider.translations('en', {
				'button.submit': 'Submit',
				'right.code': 'Code',
				'right.title': 'Title',
				'profile.id': 'Id',
				'profile.title': 'Title',
				'user.login': 'Login',
				'user.email': 'Email',
				'user.lastConnection': 'Last connection',
			});
			$translateProvider.translations('fr', {
				'button.submit': 'Valider',
				'right.code': 'Code',
				'right.title': 'Titre',
				'profile.id': 'Id',
				'profile.title': 'Titre',
				'user.login': 'Identifiant',
				'user.email': 'Email',
				'user.lastConnection': 'Dernière connexion',
			});
			$translateProvider.preferredLanguage('en');
		}]);
})();
