Editors.controller('sectionsController', ['$timeout', '$scope', '$state', 'SurveySectionService', function ($timeout, $scope, $state, SurveySectionService) {
    $scope.msgs = [];
    $scope.surveys = {selected: null};

    $scope.addSuccessMsg = function (reset, reason) {
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
    }

    $scope.addDangerMsg = function (reset, reason) {
        addMsg(reset, 'danger', reason);
    };

    $scope.closeAlert = function (index) {
        $scope.msgs.splice(index, 1);
    };


    $scope.addSection = function () {
        $scope.ssRows.unshift({id: null, name: 'New Survey Section', description: '', surveys: []});
        $scope.addSuccessMsg(true, 'Please enter the name and description of new survey section');
    };

    $scope.refresh = function () {
        SurveySectionService.readAll()
            .then(function (ssRows) {
                $scope.ssRows = ssRows;
                $scope.addSuccessMsg(true, ssRows.length + ' Survey Sections were loaded successfully');
            }, function error(reason) {
                $scope.addDangerMsg(true, reason);
            });
    };


    $scope.delete = function (index) {
        // reusable inner function
        function applyDeleteAction(index) {
            $scope.ssRows.splice(index, 1);
            $scope.addSuccessMsg(true, 'Survey Section ' + $scope.ssRows[index].name + ' deleted successfully');
        }

        var section = $scope.ssRows[index];

        if (section.surveys !== undefined && section.surveys.length > 0) {
            $scope.addDangerMsg(true, section.name + ' has ' + section.surveys.length + ' surveys and cannot be removed');
        } else if (section.id == null) { // delete data in memory (not committed yet)
            // if user is deleting a newly created section (not previously returned from db)
            applyDeleteAction(index);
            return;
        } else {         // user is trying to delete data from database
            SurveySectionService.delete(section)
                .then(function () {
                    applyDeleteAction(index);
                }, function error(reason) {
                    $scope.addDangerMsg(true, reason);
                });
        }
    }

    function syncSS(index, data) {
        $scope.ssRows[index] = data;
    }

    $scope.update = function (section) {
        SurveySectionService.update(section)
            .then(function (data) {
            }, function error(reason) {
                $scope.addDangerMsg(true, reason);
            });
    }

    $scope.add = function (section) {
        SurveySectionService.create(section)
            .then(function (data) {
            }, function error(reason) {
                $scope.addDangerMsg(true, reason);
            });
    }

    $scope.saveAll = function () {
        // split ssRows in two groups, to be added (new) and to be updated
        // (already present in the db and user wishes to make some changes)
        var groupBy = _.groupBy($scope.ssRows, function (ss) {
            return ss.id === null;
        });
        var createRows = groupBy.true;
        var updateRows = groupBy.false;

        // send all newly entered data for adding
        _.forEach(createRows, function (ss) {
            $scope.add(ss)
        });

        // send all updated data for editing
        _.forEach(updateRows, function (ss) {
            $scope.update(ss)
        });

        //refresh the view
        $scope.refresh();
    }

    //refresh the view on entry
    $scope.refresh();

}]);