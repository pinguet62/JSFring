(function(global) {
	// Map relative paths to URLs
	// Example:
	// * for:               import { ... } from '@angular/foo';
	// * will be mapped to: import { ... } from 'node_modules/@angular/foo';
	var mapping = {
		'app':                        'app', // 'dist',
		'rxjs':                       'node_modules/rxjs',
		'angular2-in-memory-web-api': 'node_modules/angular2-in-memory-web-api',
		'@angular':                   'node_modules/@angular',
		'@angular2-material':         'node_modules/@angular2-material',
		'primeng':                    'node_modules/primeng'
	};

	// Packages to load by the System loader
	var packages = {
		'app':                         { defaultExtension: 'js', main: 'main.js' },
		'rxjs':                        { defaultExtension: 'js' },
		'angular2-in-memory-web-api':  { defaultExtension: 'js' },
		'primeng':                     { defaultExtension: 'js' }
	};

	// Angular
	[
		'common',
		'compiler',
		'core',
		'http',
		'platform-browser',
		'platform-browser-dynamic',
		'router',
		'router-deprecated',
		'testing',
		'upgrade',
	].forEach(function(name) {
		packages['@angular/'+name] = { main: 'index.js', defaultExtension: 'js' };
	});

	// Material2
	[
		'button',
		'card',
		'checkbox',
		'core',
		'icon',
		'input',
		'list',
		'progress-circle',
		'progress-bar',
		'radio',
		'sidenav',
		'toolbar',
	].forEach(function(name) {
		packages['@angular2-material/'+name] = { main: name+'.js', defaultExtension: 'js', format: 'cjs' };
	});

	// Configuration
	System.config({
		map: mapping,
		packages: packages
	});
})(this);