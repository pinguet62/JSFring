/**
 * Initialize the CRUD service.
 * @crudService The CRUD service to initialize.
 * @url The target sub-URL.
 */
function initCrudService(crudService, url, $http) {
	var baseUrl = 'http://jsfring-webservice.herokuapp.com/rest'
	
	crudService.list = function(callback) {
		$http
			.get(baseUrl + url + '/')
			.success(function(response) {
				callback && callback(response);
			});
	};
	
	crudService.find = function(paginationOptions, callback) {
		$http
			.get(baseUrl + url + '/find', {params: paginationOptions})
			.success(function(response) {
				callback && callback(response);
			});
	};
	
	crudService.create = function(object) {
		$http
			.put(baseUrl + url + '/', object)
			.success(function(data, status, headers, config) {
			});
	};
	
	crudService.update = function(object) {
		$http
			.post(baseUrl + url + '/', object)
			.success(function(data, status, headers, config) {
			});
	};
}
