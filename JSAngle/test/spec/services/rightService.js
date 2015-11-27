'use strict';

describe('Service: rightService', function () {

	// load the service's module
	beforeEach(module('jsangleApp'));

	// instantiate service
	var rightService;
	beforeEach(inject(function (_rightService_) {
		rightService = _rightService_;
	}));

	it('should do something', function () {
		expect(!!rightService).toBe(true);
	});

});
