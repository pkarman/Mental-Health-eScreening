describe('angular.common: api', function() {
    var scope, httpBackend, api, http;

    beforeEach(module('common.api'));

    beforeEach(inject(function($rootScope, $controller, $httpBackend, Api, $http) {
        scope = $rootScope.$new();
        httpBackend = $httpBackend;
        api = Api;
        http = $http;
    }));

    it('should set the base to an api', function() {
        api.setBase('/api/v2/');
        expect(api.getBase()).toBe('/api/v2/');
    });

    it('should convert and object to a query string', function() {
        var data = {
            serieId: 100,
            stringId: '123',
            userId: 0,
            posts: ['test', 'test1']
        };

        expect(api.encodeObject(data)).toBe("serieId=100&stringId=123&userId=0&posts[0]=test&posts[1]=test1&");
    });

    it('should make a GET request to a url', function() {
        api.setBase('/api/v1/');
        httpBackend.expectGET('/api/v1/users/1?').respond();
        api.get('users/1');
        httpBackend.flush();
    });

    it('should make a PUT request to a url', function() {
        api.setBase('/api/v1/');
        httpBackend.expectPUT('/api/v1/users/1').respond();
        api.put('users/1');
        httpBackend.flush();
    });

    it('should make a POST request to a url', function() {
        api.setBase('/api/v1/');
        httpBackend.expectPOST('/api/v1/users/1').respond();
        api.post('users/1');
        httpBackend.flush();
    });

    it('should make a  request to a url', function() {
        api.setBase('/api/v1/');
        httpBackend.expectDELETE('/api/v1/users/1?').respond();
        api.delete('users/1');
        httpBackend.flush();
    });
});