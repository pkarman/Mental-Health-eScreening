Editors.controller('sectionsController', ['$rootScope', '$scope', '$state', 'SurveySectionService', function ($rootScope, $scope, $state, SurveySectionService) {
    $scope.msgs = [];
    var deleteSection = null;

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


    $scope.addSection = function () {
        $scope.ssRows.unshift({id: null, name: '', description: '', displayOrder: 0, surveys: []});
        addSuccessMsg(true, 'Please enter the name and description of new survey section');
    };

    var refreshNow = function () {
        deleteSection = null;
        SurveySectionService.getList()
            .then(function (ssRows) {
                $scope.ssRows = ssRows;
                addSuccessMsg(true, ssRows.length + ' Survey Sections were loaded successfully');
            }, function error(reason) {
                addDangerMsg(true, reason);
            });
    };

    $scope.cancel = function () {
        $state.go("home");
    }

    $scope.saveAll = function () {
        saveAll();
    };

    $scope.delete = function (index) {
        var section = $scope.ssRows[index];

        if (section.surveys !== undefined && section.surveys.length > 0) {
            addDangerMsg(true, section.name + ' has ' + section.surveys.length + ' module(s) and cannot be removed');
        } else if (section.id == null) { // delete data in memory (not committed yet)
            // if user is deleting a newly created section (not previously returned from db)
            $scope.ssRows.splice(index, 1);
            addSuccessMsg(true, 'Survey Section ' + $scope.ssRows[index].name + ' deleted successfully');
        } else {
            // latch the section to be deleted and call for saveAll
            deleteSection = section;
            saveAll();
        }
    };

    var onLastSectionUpdate = function () {
        if (deleteSection) {
            SurveySectionService.delete(deleteSection)
                .then(function success(section) {
                    console.log('section \'' + deleteSection.name + '\' deleted successfully')
                    refreshNow();
                }, function error(reason) {
                    var errMsg = 'Section name \'' + deleteSection.name + '\' could not be deleted.';
                    console.error(errMsg + 'Reason:' + reason);
                    addDangerMsg(true, errMsg);
                });
        } else {
            refreshNow();
        }
    };


    $scope.$on("lastSection:updated", onLastSectionUpdate);

    var updateSection = function (section, lastSection) {
        SurveySectionService.update(section)
            .then(function (data) {
                console.log("'" + data.name + "' updated successfully");
                if (lastSection) $rootScope.$broadcast('lastSection:updated');
            }, function error(reason) {
                addDangerMsg(true, reason);
            });
    };

    var addSection = function (section, lastSection) {
        SurveySectionService.create(section)
            .then(function (data) {
                console.log("a new survey section '" + data.name + "\ added successfully");
                if (lastSection) $rootScope.$broadcast('lastSection:added');
            }, function error(reason) {
                addDangerMsg(true, reason);
            });
    };

    var saveAll = function (foo) {
        //reset the display Order as this could be out of order when
        //dragging these survey sections back and forth
        _.each($scope.ssRows, function (ss, index) {
            ss.displayOrder = index + 1;
        });

        // split ssRows in two groups, to be added (new) and to be updated
        // (already present in the db and user wishes to make some changes)
        var groupBy = _.groupBy($scope.ssRows, function (ss) {
            return ss.id === null;
        });
        var newSections = groupBy.true;
        var updatableSections = groupBy.false;

        // send all newly entered data for adding
        _.each(newSections, function (ss, index, newSections) {
            addSection(ss, index+1 === newSections.length);
        });

        // send all updated data for editing
        _.each(updatableSections, function (ss, index, updatableSections) {
            updateSection(ss, index+1 === updatableSections.length);
        });
    };

    //refresh the view on entry
    refreshNow();

}]);