Editors.controller('sectionsController', ['$timeout', '$scope', '$state', 'SurveySectionService', function ($timeout, $scope, $state, SurveySectionService) {
    $scope.msgs = [];

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
        $scope.addSuccessMsg(true, 'Please click on header \'New Survey Section\' and enter the name and description of new survey section');
    };

    SurveySectionService.readAll()
        .then(function (ssRows) {
            $scope.ssRows = ssRows;
            $scope.addSuccessMsg(true, ssRows.length + ' Survey Sections were loaded successfully');
        }, function error(reason) {
            $scope.addDangerMsg(true, reason);
        });


    $scope.delete = function (index) {
        // reusable inner function
        function applyDeleteAction(index) {
            $scope.ssRows.splice(index, 1);
            $scope.addSuccessMsg(true, 'Survey Section ' + $scope.ssRows[index].name + ' deleted successfully');
        }

        var section = $scope.ssRows[index];

        // if user is deleting a newly created section (not previously returned from db)
        if (section.id == null) {
            applyDeleteAction(index);
            return;
        }

        // user is trying to delete data from database
        if (section.surveys !== undefined && section.surveys.length > 0) {
            $scope.addDangerMsg(true, section.name + ' has ' + section.surveys.length + ' surveys and cannot be removed');
        } else {
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

    $scope.update = function (index) {
        var section = $scope.ssRows[index];
        SurveySectionService.update(section)
            .then(function (data) {
                syncSS(index, data);
                $scope.addSuccessMsg(true, 'Survey Section ' + data.name + ' modified successfully');
            }, function error(reason) {
                $scope.addDangerMsg(true, reason);
            });
    }

    $scope.add = function (index) {
        var section = $scope.ssRows[index];
        SurveySectionService.create(section)
            .then(function (data) {
                syncSS(index, data);
                $scope.addSuccessMsg(true, 'Survey Section ' + data.name + ' added successfully');
            }, function error(reason) {
                $scope.addDangerMsg(true, reason);
            });
    }
}]);