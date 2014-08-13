/**
 * Created by Bryan on 2/11/14.
 */
angular.module('AssessmentEditor')

    // A RESTful factory for retrieving contacts from 'assessmentlist.json'
    .factory('assessments', ['$http', function ($http, utils) {
        var path = 'app/json/assessmentlist.json';
        var assessments = $http.get(path).then(function (resp) {
            return resp.data.assessments;
        });

        var factory = {};
        factory.all = function () {
            return assessments;
        };
        factory.get = function (id) {
            return assessments.then(function(){
                return utils.findById(contacts, id);
            })
        };
        return factory;
    }])

    .factory('utils', function () {

        return {

            // Util for finding an object by its 'id' property among an array
            findById: function findById(a, id) {
                for (var i = 0; i < a.length; i++) {
                    if (a[i].id == id) return a[i];
                }
                return null;
            },

            // Util for returning a randomKey from a collection that also isn't the current key
            newRandomKey: function newRandomKey(coll, key, currentKey){
                var randKey;
                do {
                    randKey = coll[Math.floor(coll.length * Math.random())][key];
                } while (randKey == currentKey);
                return randKey;
            }

        };

    });
