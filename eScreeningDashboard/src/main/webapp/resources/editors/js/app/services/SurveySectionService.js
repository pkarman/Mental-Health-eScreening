angular.module('EscreeningDashboardApp.services.surveysection', ['restangular'])
    .factory('SurveySectionService', ['Restangular', '$q', function (Restangular, $q) {

        "use strict";

        var restAngular = Restangular.withConfig(function (config) {
            config.setBaseUrl('services');
            config.setRequestSuffix('.json');
        });

        var proxy = restAngular.all('surveySections');

        // service to perform CRUD
        var service = {
            create: function (ss) {
                return proxy.post(ss);
            },
            getList: function () {
                return proxy.getList();
            },
            update: function (ss) {
                return ss.put();
            },
            delete: function (ss) {
                return ss.remove();
            },
            batchCreate: function (newSections) {
                var p = [];
                _.each(newSections, function (ss) {
                    p.push(service.create(ss)
                        .then(function (createdData) {
                            console.debug('details of section created in database => '+JSON.stringify(createdData));
                            console.log('added success:\'' + ss.name + '\' added successfully');
                        }));
                });
                return p;
            },
            batchUpdate: function (updateSections) {
                var p = [];
                _.each(updateSections, function (ss) {
                    p.push(service.update(ss)
                        .then(function (modifiedData) {
                            console.debug('details of section updated => '+JSON.stringify(modifiedData));
                            console.log('update success:\'' + ss.name + '\' updated successfully');
                        }));
                });
                return p;
            },
            batchDelete: function (deleteSections) {
                var p = [];
                _.each(deleteSections, function (ss) {
                    p.push(service.delete(ss)
                        .then(function () {
                            console.debug('details of section deleted => '+JSON.stringify(ss));
                            console.log('delete success:\'' + ss.name + '\' deleted successfully');
                        }));
                });
                return p;
            },
            applyCrud: function (dbData, toBeDel, succMsgCb, dangerMsgCb, refreshDataCb) {
                // split ssRows in two groups, to be added (new) and to be updated
                // (already present in the db and user wishes to make some changes)
                var groupBy = _.groupBy(dbData, function (ss) {
                    return ss.id === null;
                });

                // first list is for create records
                var newRecs = groupBy.true;

                // second list is for delete records
                var updateRecs = groupBy.false;

                // create
                $q.all(service.batchCreate(newRecs))
                    .then(function (newResults) {
                        if (newRecs) {
                            console.log("[" + newRecs.length + "] Sections added successfully:" + JSON.stringify(newResults));
                        }
                        // update
                        $q.all(service.batchUpdate(updateRecs))
                            .then(function (updateResults) {
                                console.log("[" + updateRecs.length + "] Sections updated successfully:" + JSON.stringify(updateResults));
                                // third row is for delete records
                                // PS: this will be called after records are added and created
                                // as otherwise foreign key's will hinder the delete operation
                                var deleteRecs = _.uniq(toBeDel.sections);
                                // reset the toBeDel now
                                toBeDel.sections=[];
                                $q.all(service.batchDelete(deleteRecs))
                                    .then(function (deleteResults) {
                                        console.log("[" + deleteRecs.length + "] Sections deleted successfully:" + JSON.stringify(deleteResults));
                                        succMsgCb(true, "Saved all sections successfully");
                                        refreshDataCb();
                                    }, function error(deleteErrs) {
                                        console.log("error while write batch delete:" + JSON.stringify(deleteErrs));
                                        dangerMsgCb(true, "Error Found while deleting sections. Delete operation failed");
                                    });
                            }, function error(updateErrs) {
                                console.log("error while batch updates:" + JSON.stringify(updateErrs));
                                dangerMsgCb(true, "Error Found while updating sections. Update operation failed");
                            });
                    }, function error(createErrs) {
                        console.log("error while write batch:" + JSON.stringify(createErrs));
                        dangerMsgCb(true, "Error Found while creating sections. Create operation failed");
                    });
            }

        };
        return service;

    }]);
