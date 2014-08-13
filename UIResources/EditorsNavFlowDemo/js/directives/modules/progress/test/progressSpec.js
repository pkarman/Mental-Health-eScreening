describe('Common Progress |', function() {
    var progressService, httpBackend, http, scope;

    beforeEach(module('common.progress'));

    beforeEach(inject(function($rootScope, $httpBackend, $http, ProgressService) {
        scope = $rootScope.$new();
        progressService = ProgressService;
        httpBackend = $httpBackend;
        http = $http;
    }));

    it('should complete basic arthimetic', function() {
        progressService.start('loadUser', 10);
        progressService.start('loadWhatever', 30);
        progressService.start('loadTest', 80);

        progressService.done('loadUser');
        progressService.done('loadWhatever');
        progressService.done('loadTest');

        expect(progressService.get()).toBe(100);
    });

    it('should complete a process after $http is done', function() {
        progressService.start('loadUser', 25);
        expect(progressService.processes['loadUser'].units).toBe(25);

        httpBackend.expectGET('user').respond();
        http.get('user').success(function() {
            progressService.done('loadUser');
        });
        httpBackend.flush();
        expect(progressService.processes['loadUser'].isDone).toBe(true);
    });

    it('should return the current progress', function() {
        progressService.start('load1', 25);
        progressService.start('load2', 25);
        progressService.start('load3', 25);
        progressService.start('load4', 25);
        expect(progressService.get()).toBe(0);

        progressService.done('load1');
        expect(progressService.get()).toBe(25);

        progressService.done('load2');
        expect(progressService.get()).toBe(50);

        progressService.done('load3');
        expect(progressService.get()).toBe(75);

        progressService.done('load4');
        expect(progressService.get()).toBe(100);
    });

    it('shoudl clear pending, completed, and processes', function() {
        progressService.reset();
        expect(progressService.get()).toBe(0);
        expect(progressService.pending).toBe(0);
        expect(progressService.completed).toBe(0);
    });
});