(function() {
	'use strict';

	/**
	 * Configuration of i18n translations.
	 */
	angular.module('jsangleApp')
		.config(['$translateProvider', function($translateProvider) {
			$translateProvider.translations('en', {
				'button.submit': 'Submit',
				'grid.actions.show': 'Show',
				'grid.actions.update': 'Update',
				'grid.actions.delete': 'Delete',
				'right.code': 'Code',
				'right.title': 'Title',
				'profile.id': 'Id',
				'profile.title': 'Title',
				'profile.rights': 'Rights',
				'user.login': 'Login',
				'user.email': 'Email',
				'user.active': 'Active',
				'user.lastConnection': 'Last connection',
				'user.profiles': 'Profiles',
			});
			$translateProvider.translations('fr', {
				'button.submit': 'Valider',
				'grid.actions.show': 'Afficher',
				'grid.actions.update': 'Modifier',
				'grid.actions.delete': 'Supprimer',
				'right.code': 'Code',
				'right.title': 'Titre',
				'profile.id': 'Id',
				'profile.title': 'Titre',
				'profile.rights': 'Droits',
				'user.login': 'Identifiant',
				'user.email': 'Email',
				'user.active': 'Actif',
				'user.lastConnection': 'Dernière connexion',
				'user.profiles': 'Profils',
			});
			$translateProvider.preferredLanguage('en');
		}]);
})();
