(function (global) {
	System.config({
		paths: {
			'npm:': 'node_modules/'
		},
		map: {
			'app': 'app',
			// Angular
			'@angular/core': 'npm:@angular/core/bundles/core.umd.js',
			'@angular/common': 'npm:@angular/common/bundles/common.umd.js',
			'@angular/compiler': 'npm:@angular/compiler/bundles/compiler.umd.js',
			'@angular/platform-browser': 'npm:@angular/platform-browser/bundles/platform-browser.umd.js',
            '@angular/platform-browser/animations': 'npm:@angular/platform-browser/bundles/platform-browser-animations.umd.js',
			'@angular/platform-browser-dynamic': 'npm:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
			'@angular/http': 'npm:@angular/http/bundles/http.umd.js',
			'@angular/router': 'npm:@angular/router/bundles/router.umd.js',
			'@angular/forms': 'npm:@angular/forms/bundles/forms.umd.js',
            '@angular/animations': 'npm:@angular/animations/bundles/animations.umd.js',
            '@angular/animations/browser':'node_modules/@angular/animations/bundles/animations-browser.umd.js',
			// Material
			'rxjs': 'npm:rxjs',
			'@angular/material': 'npm:@angular/material/bundles/material.umd.js',
			'primeng': 'npm:primeng'
		},
		packages: {
			app: {
				main: 'main.js',
				defaultExtension: 'js'
			},
			rxjs: {
				defaultExtension: 'js'
			},
			primeng: {
				defaultExtension: 'js'
			}
		}
	});
})(this);