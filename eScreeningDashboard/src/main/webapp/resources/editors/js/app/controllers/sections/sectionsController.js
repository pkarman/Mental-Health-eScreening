Editors.controller('sectionsController', ['$timeout', '$scope', '$state', 'SurveySectionService', function ($timeout, $scope, $state, SurveySectionService) {
    $scope.alerts = [];
    var reloadSSTimeout = undefined;

    $scope.addSuccessMsg = function (reset, reason) {
        addMsg(reset, 'success', reason);
    };

    var clearMsgs = function () {
        $scope.alerts = [];
    };

    var addMsg = function (reset, type, msg) {
        if (reset) {
            clearMsgs();
        }
        $scope.alerts.push({type: type, msg: msg});
    }

    $scope.addDangerMsg = function (reset, reason) {
        addMsg(reset, 'danger', reason);
    };

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };


    $scope.addSection = function () {
        $scope.ssRows.unshift({id: null, name: 'New Survey Section', description: ''});
        $scope.addSuccessMsg(true, 'New Survey Section is added locally');
    };


    SurveySectionService.readAllSS()
        .then(function (ssRows) {
            $scope.ssRows = ssRows;
            $scope.addSuccessMsg(true, ssRows.length + ' Survey Sections were loaded successfully');
        }, function error(reason) {
            $scope.addDangerMsg(true, reason);
        });


    $scope.delete = function (index) {
        var section = $scope.ssRows[index];
        if (section.surveys !== undefined && section.surveys.length > 0) {
            $scope.addDangerMsg(true, section.name + ' has ' + section.surveys.length + ' surveys and cannot be removed');
        } else {
            SurveySectionService.deleteSS(section)
                .then(function () {
                    $scope.addSuccessMsg(true, 'Survey Section ' + section.name + ' deleted successfully');
                }, function error(reason) {
                    $scope.addDangerMsg(true, reason);
                });
        }
    }

    $scope.update = function (section) {
        SurveySectionService.updateSS(section)
            .then(function (data) {
                $scope.addSuccessMsg(true, 'Survey Section ' + section.name + ' modified successfully');
            }, function error(reason) {
                $scope.addDangerMsg(true, reason);
            });
    }

    $scope.add = function (section) {
        SurveySectionService.createSS(section)
            .then(function (data) {
                $scope.addSuccessMsg(true, 'Survey Section ' + section.name + ' added successfully');
            }, function error(reason) {
                $scope.addDangerMsg(true, reason);
            });
    }

    // on successful Create, Update, and Delete, SurveySectionService sends messages
    var reloadSS = function () {
        if (reloadSSTimeout) $timeout.cancel(reloadSSTimeout);

        reloadSSTimeout = $timeout(function () {

            $scope.$apply(function () {
                SurveySectionService.readAllSS()
                    .then(function (ssRows) {
                        $scope.ssRows = ssRows;
                        //clearMsgs();
                    }, function error(reason) {
                        $scope.addDangerMsg(true, reason);
                    });
            });
        }, 500);
    }

    $scope.$on('ss:created', reloadSS);
    $scope.$on('ss:deleted', reloadSS);
    $scope.$on('ss:updated', reloadSS);

}]);