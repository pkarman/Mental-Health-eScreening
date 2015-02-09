/**
 * Created by Khalid Rizvi @ 01/24/2015
 */
Editors.controller('ModuleFormulasEditController', ['$state', '$log', '$scope', 'FormulasService', function ($state, $log, $scope, FormulasService) {
    $scope.formula = FormulasService.fetchCurrentFormula();
    $log.debug($scope);
    var clearMsgs = function () {
            $scope.msgs = [];
        },
        addMsg = function (reset, type, msg) {
            if (reset) {
                clearMsgs();
            }
            $scope.msgs.push({type: type, text: msg});
        },
        addSuccessMsg = function (reset, reason) {
            addMsg(reset, 'success', reason);
        },
        addDangerMsg = function (reset, reason) {
            addMsg(reset, 'danger', reason || 'Sorry, we are unable to process your request at this time. If this continues, please contact your system administrator.');
        };
    $scope.closeAlert = function (index) {
        $scope.msgs.splice(index, 1);
    };
    $scope.msgs = [];

    $scope.produceInputFields = function () {
        FormulasService.verifyCurrentFormula().then(function (result) {
            FormulasService.attachWithCurrentFormula(result.verifiedIds);
            $scope.verifiedIds = result.verifiedIds;
            addSuccessMsg(true, 'The formula is verified successfully');
        }, function error(reason) {
            addDangerMsg(true, reason.reason);
        });
    };

    $scope.runTest = function () {
        FormulasService.testCurrentFormula()
            .then(function (result) {
                $scope.result = result.data;
                addSuccessMsg(true, 'Please verify the result and either save or change the values and test again');
            }, function error(reason) {
                addDangerMsg(true, reason.data.data);
            }
        )
    };

    $scope.cancelFormula = function () {
        $state.go('modules.formulasList', {moduleId: FormulasService.fetchCurrentModule().id});
    };

    $scope.saveFormula = function () {
        var isNew = false;
        if ($scope.formula.avId === undefined) {
            isNew = true;
        }
        FormulasService.persistCurrentFormula()
            .then(function (result) {
                FormulasService.updateCurrentFormula(result);
                FormulasService.processSuccessfulSave(isNew);
                $state.go('modules.formulasList', {moduleId: FormulasService.fetchCurrentModule().id});
            }, function error(reason) {
                addDangerMsg(true, reason.data);
            }
        );
    };
}]);