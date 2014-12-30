Editors.controller('sectionsController', ['$log', '$scope', '$state', 'ManageSectionService', function ($log, $scope, $state, ManageSectionService) {
    var toBeDel = {sections: []};
    var dbData = [];

    $scope.msgs = [];
    $scope.status = {
        closeOthers: true,
        dd: {
            sections: {
                selected: null
            },
            modules: {
                selected: null
            }
        }
    };
    $scope.ssRows = [];

    var addSuccessMsg = function (reset, reason) {
        addMsg(reset, 'success', reason);
    };

    var clearMsgs = function () {
        $scope.msgs = [];
    };

    var addMsg = function (reset, type, msg) {
        if (reset) {
            clearMsgs();
        }
        $scope.msgs.push({type: type, text: msg});
    };

    var addDangerMsg = function (reset, reason) {
        addMsg(reset, 'danger', reason);
    };

    $scope.closeAlert = function (index) {
        $scope.msgs.splice(index, 1);
    };

    $scope.openAll = function () {
        $scope.status.closeOthers = !$scope.status.closeOthers;
        _.each($scope.ssRows, function (ssRow) {
            ssRow.openSection = !$scope.status.closeOthers;
        })
    }

    $scope.addSection = function () {
        $scope.ssRows.unshift({id: null, name: '', description: '', displayOrder: 0, surveys: []});
        addSuccessMsg(true, 'Please enter the name and description of new survey section');
    };

    var refreshNow = function () {
        ManageSectionService.getList()
            .then(function (fetchedDataFromBackend) {
                dbData = fetchedDataFromBackend;
                $scope.ssRows = updateScopeDataWithDbData(fetchedDataFromBackend);
            }, function error(reason) {
                addDangerMsg(false, reason);
            });
    };

    var updateScopeDataWithDbData = function (backEndData) {
        var recs = [];
        _.each(backEndData, function (singleRecord) {
            var rec = _.pick(singleRecord, ['id', 'name', 'description', 'displayOrder', 'surveys']);
            rec.openSection = false;
            rec.openModule = false;
            recs.push(rec);
        });
        return recs;
    }

    $scope.cancel = function () {
        $state.go("home");
    }

    $scope.saveAll = function () {
        saveAll();
    };

    $scope.delete = function (index) {
        var section = $scope.ssRows[index];

        if (section.surveys !== undefined && section.surveys.length > 0) {
            addDangerMsg(true, '\'' + section.name + '\' has [' + section.surveys.length + '] module(s) and cannot be removed');
        } else if (section.id == null) {
            $scope.ssRows.splice(index, 1);
            addSuccessMsg(true, 'Newly added Survey Section \'' + (section.name || 'No Name provided') + '\' has been deleted');
        } else {
            // transfer the information of ssRow to dbData by finding the correct dbData.row based on the ssRow id
            toBeDel.sections.push(_.where(dbData, {id: section.id})[0]);

            // remove the section from $scope.ssRows but DO NOT save it the db
            //$scope.ssRows.splice(index, 1);

            addSuccessMsg(true, 'Survey Section \'' + section.name + '\' is marked for deletion. Please press \'Save\' to delete the section permanently');
        }
    };

    function adjustDisplayOrders() {
        //reset the display Order as this could be out of order when
        //dragging these survey sections back and forth
        _.each($scope.ssRows, function (ss, index) {
            ss.displayOrder = index + 1;
            $log.debug('displayOrder of \'' + ss.name + '\' set to => ' + ss.displayOrder);
        });
    }

    function updateDbDataWithScopeData() {
        // switch gears and reassign values from $scope.ssRows to 'dbData' so restangular
        // be happy and be able to invoke update if user has changed the order of sections
        var tmp = [];
        _.each($scope.ssRows, function (ss) {
            var d = _.where(dbData, {id: ss.id});
            if (d[0]) {
                _.assign(d[0], _.pick(ss, ['id', 'name', 'description', 'displayOrder', 'surveys']));
            } else {
                tmp.push(ss);
            }
        });

        dbData = dbData.concat(tmp);
    }

    function passBasicSanityChecks() {
        var errSectionName = _.find($scope.ssRows, function (ssRow) {
            return ssRow.name.length <= 0;
        })
        if (errSectionName) {
            addDangerMsg(true, 'Section must have a name');
        }

        return errSectionName == undefined;
    }

    function saveAll(foo) {
        if (passBasicSanityChecks()) {

            adjustDisplayOrders();

            updateDbDataWithScopeData();

            ManageSectionService.applyCrud(dbData, toBeDel, addSuccessMsg, addDangerMsg, refreshNow);
        }
    };

    //refresh the view on entry
    refreshNow();

}]);