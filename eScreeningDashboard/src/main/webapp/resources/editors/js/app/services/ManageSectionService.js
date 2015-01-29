/**
 * Created by munnoo on 12/29/14.
 */
angular.module('EscreeningDashboardApp.services.managesection', ['restangular'])
    .factory('ManageSectionService', ['$log', 'Restangular', '$q', function ($log, Restangular, $q) {
        "use strict";

        var proxy = Restangular.service('sections');

        // service to perform CRUD
        var service = {
            get: function get(sectionId) {
                return proxy.one(sectionId).get();
            },
            create: function create(ss) {
                return proxy.post(ss);
            },
            getList: function getList() {
                return proxy.getList();
            },
            update: function update(ss) {
                return ss.put();
            },
            delete: function (ss) {
                return ss.remove();
            },
            batchCreate: function batchCreate(newSections) {
                var p = [];
                _.each(newSections, function (ss) {
                    p.push(service.create(ss)
                        .then(function (createdData) {
                            $log.debug('details of section created in database => ' + JSON.stringify(createdData));
                            $log.debug('added success:\'' + ss.name + '\' added successfully');
                        }));
                });
                return p;
            },
            batchUpdate: function batchUpdate(updateSections) {
                var p = [];
                _.each(updateSections, function (ss) {
                    p.push(service.update(ss)
                        .then(function (modifiedData) {
                            $log.debug('details of section updated => ' + JSON.stringify(modifiedData));
                            $log.debug('update success:\'' + ss.name + '\' updated successfully');
                        }));
                });
                return p;
            },
            batchDelete: function batchDelete(deleteSections) {
                var p = [];
                _.each(deleteSections, function (ss) {
                    p.push(service.delete(ss)
                        .then(function () {
                            $log.debug('details of section deleted => ' + JSON.stringify(ss));
                            $log.debug('delete success:\'' + ss.name + '\' deleted successfully');
                        }));
                });
                return p;
            },
            applyCrud: function applyCrud(dbData, toBeDel, succMsgCb, dangerMsgCb, refreshDataCb) {
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
                            $log.debug("[" + newRecs.length + "] Sections added successfully:" + JSON.stringify(newResults));
                        }
                        // update
                        $q.all(service.batchUpdate(updateRecs))
                            .then(function (updateResults) {
                                $log.debug("[" + updateRecs.length + "] Sections updated successfully:" + JSON.stringify(updateResults));
                                // third row is for delete records
                                // PS: this will be called after records are added and created
                                // as otherwise foreign key's will hinder the delete operation
                                var deleteRecs = _.uniq(toBeDel.sections);
                                // reset the toBeDel now
                                toBeDel.sections = [];
                                $q.all(service.batchDelete(deleteRecs))
                                    .then(function (deleteResults) {
                                        $log.debug("[" + deleteRecs.length + "] Sections deleted successfully:" + JSON.stringify(deleteResults));
                                        succMsgCb(true, "Saved all sections successfully");
                                        refreshDataCb();
                                    }, function error(deleteErrs) {
                                        $log.debug("error while write batch delete:" + JSON.stringify(deleteErrs));
                                        dangerMsgCb(true, "Error Found while deleting sections. Delete operation failed");
                                    });
                            }, function error(updateErrs) {
                                $log.debug("error while batch updates:" + JSON.stringify(updateErrs));
                                dangerMsgCb(true, "Error Found while updating sections. Update operation failed");
                            });
                    }, function error(createErrs) {
                        $log.debug("error while write batch:" + JSON.stringify(createErrs));
                        dangerMsgCb(true, "Error Found while creating sections. Create operation failed");
                    });
            }

        };
        return service;

    }]);
