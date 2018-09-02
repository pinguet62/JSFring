/**
 * Default configuration.
 * Can be overwritten by:
 * - locally/debug: {@code "/assets/config.json"} file
 * - production/build: {@code "JSFRING_WEBAPP_ANGULAR_CONFIG"} environment variable
 */
export const environment = {
    production: false,
    configFile: 'assets/config.json',
    api: window.location.origin
        .replace('4200', '8080') // local
        .replace('8083', '8086') // Docker compose
        .replace('jsfring-webapp-angular', 'jsfring-webservice') // Heroku
};
