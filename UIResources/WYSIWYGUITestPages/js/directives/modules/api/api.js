(function() {

    "use strict";

    angular.module('common.api', [])

        .factory('Api', ['$http', function($http) {
            this.base = '';

            /**
             * @api {private} encodeObject(obj); encodeObject()
             * @apiName encodeObject
             * @apiGroup API
             *
             * @apiParam {Object} obj The object to be converted into a http query string.
             *
             * @apiSuccess {String} str http query string.
             */
            this.encodeObject = function(obj) {
                var str = "";
                for(var p in obj) {
                    if (obj[p] !== undefined) {
                        switch(typeof obj[p]) {
                            case 'boolean' :
                                str += p + '=' + parseInt(obj[p], 10) + "&";
                                break;

                            case 'number' :
                            case 'string' :
                                str += p + "=" + obj[p] + "&";
                                break;

                            case 'object' :
                                for (var i in obj[p]) {
                                    if (obj[p][i]) {
                                        str += p + "[" + encodeURIComponent(i) + "]=" + encodeURIComponent(obj[p][i]) + "&";
                                    }
                                }
                                break;
                        }
                    }
                }

                return str;
            }

            /**
             * @api {public} Api.setBase('/api/v2/'); setBase()
             * @apiName setBase
             * @apiGroup API
             *
             * @apiParam {String} newBase The new base url for all $http requests.
             *
             * @apiSuccess {Null} null
             */
            this.setBase = function(newBase) {
                this.base = newBase;
            };

            this.getBase = function() {
                return this.base;
            };

            /**
             * @api {public} Api.get(url,data); get()
             * @apiName get
             * @apiGroup API
             *
             * @apiParam {String} url Get destination.
             * @apiParam {Object} data Data to be sent with request.
             *
             * @apiSuccess {Promise} promise Standard $http promise.
             */
            this.get = function(url, data) {
                return $http.get(this.base + url + "?" + this.encodeObject(data));
            };

            /**
             * @api {public} Api.get(url,data); get()
             * @apiName get
             * @apiGroup API
             *
             * @apiParam {String} url Get destination.
             * @apiParam {Object} data Data to be sent with request.
             *
             * @apiSuccess {Promise} promise Standard $http promise.
             */
            this.post = function(url, data){
                return $http.post(this.base + url, data);
            };

            /**
             * @api {public} Api.get(url,data); get()
             * @apiName get
             * @apiGroup API
             *
             * @apiParam {String} url Get destination.
             * @apiParam {Object} data Data to be sent with request.
             *
             * @apiSuccess {Promise} promise Standard $http promise.
             */
            this.put = function(url, data){
                return $http.put(this.base + url, data);
            };

            /**
             * @api {public} Api.get(url,data); get()
             * @apiName get
             * @apiGroup API
             *
             * @apiParam {String} url Get destination.
             * @apiParam {Object} data Data to be sent with request.
             *
             * @apiSuccess {Promise} promise Standard $http promise.
             */
            this.delete = function(url, data){
                return $http.delete(this.base + url + "?" + this.encodeObject(data));
            };

            return this;
        }]);

})();