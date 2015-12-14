/**
 * Initialize the CRUD service.
 * @crudService The CRUD service to initialize.
 * @url The target sub-URL.
 */
function initCrudService(crudService, url, $http) {
	crudService.list = function(callback) {
		$http
			.get('http://localhost:8080/webservice/rest' + url + '')
			.success(function(response) {
				callback && callback(response);
			});
	};
	
	crudService.find = function(paginationOptions, callback) {
		$http
			.get('http://localhost:8080/webservice/rest' + url + '/find', {params: paginationOptions})
			.success(function(response) {
				callback && callback(response);
			});
	};
}
