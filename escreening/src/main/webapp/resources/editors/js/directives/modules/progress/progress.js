(function() {

    'use strict';

    angular.module('common.progress', [])

        .factory('ProgressService', [function() {
            var self = {};

            self.processes = {};
            self.pending = 0;
            self.completed = 0;

            self.start = function(name, units) {
                self.processes[name] = {
                    units: units,
                    isDone: false
                };

                self.pending += +units;
            };

            self.done = function(name) {
                self.processes[name].isDone = true;
                self.completed += +self.processes[name].units;
            };

            self.get = function() {
                return +self.pending === 0 ? 0 : Math.ceil(+self.completed / +self.pending * 100);
            };

            self.reset = function() {
                self.processes = {};
                self.completed = 0;
                self.pending = 0;
            };

            return self;
        }]);

})();