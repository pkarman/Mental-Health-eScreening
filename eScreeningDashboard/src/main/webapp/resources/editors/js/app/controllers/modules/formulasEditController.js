/**
 * Created by Khalid Rizvi @ 01/24/2015
 */

Editors.filter('tokensfilter', function () {
    return function (tokens, srchThis) {
        var out = [];

        if (angular.isArray(tokens)) {
            tokens.forEach(function (item) {
                var itemMatches = false;

                var keys = Object.keys(srchThis);
                for (var i = 0; i < keys.length; i++) {
                    var prop = keys[i];
                    var text = srchThis[prop].toLowerCase();
                    if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                        itemMatches = true;
                        break;
                    }
                }

                if (itemMatches) {
                    out.push(item);
                }
            });
        } else {
            // Let the output be the input untouched
            out = tokens;
        }
        return out;
    };
});


Editors.controller('ModuleFormulasEditController', ['$state', '$log', '$scope', 'FormulasService', function ($state, $log, $scope, FormulasService) {
    $scope.module = function () {
        return FormulasService.fetchCurrentModule().name;
    };

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

    $scope.formulaTemplate = {};

    $scope.formula = FormulasService.fetchCurrentFormula();
    $scope.formulaTemplate.selectedTokens = $scope.formula.selectedTokens;
    // now see if the current formula is not new, if it is not new than we have to load the fresh copy from database
    if (!FormulasService.isNewFormula()) {
        FormulasService.loadCurrentFormula().then(function () {
            $scope.formula = FormulasService.fetchCurrentFormula();
            $scope.formulaTemplate.selectedTokens = $scope.formula.selectedTokens;
        })
    }

    $scope.produceInputFields = function () {
        clearMsgs();
        $scope.toggleDisable();
        var t = tokens();
        if (validate(t)) {
            FormulasService.verifySelectedTokens(t).then(function (result) {
                FormulasService.attachWithCurrentFormula(result.verifiedIds);
                $scope.verifiedIds = result.verifiedIds;
                addSuccessMsg(true, 'The formula is verified successfully');
            }, function error(reason) {
                addDangerMsg(true, reason.data.reason);
            });
        }
    };

    $scope.runTest = function () {
        clearMsgs();
        var t = tokens();
        if (validate(t)) {
            FormulasService.testSelectedTokens(t)
                .then(function (result) {
                    $scope.result = result.data;
                    addSuccessMsg(true, 'Please verify the result and either save or change the values and test again');
                }, function error(reason) {
                    addDangerMsg(true, reason.data.data);
                }
            );
        }
    };

    $scope.cancelFormula = function () {
        $state.go('modules.formulasList', {moduleId: FormulasService.fetchCurrentModule().id});
    };

    $scope.saveFormula = function () {
        var isNew = false;
        if ($scope.formula.id === undefined) {
            isNew = true;
        }

        //verify data -- name is required
        var n = FormulasService.fetchCurrentFormula().name;
        clearMsgs();
        if (n === undefined || n.length === 0) {
            addDangerMsg(false, 'Name of the formula is required.');
        }

        var t = tokens();
        validate(t);

        if ($scope.msgs.length > 0) {
            return;
        }

        FormulasService.persistSelectedTokens(t)
            .then(function (result) {
                FormulasService.updateCurrentFormula(result);
                FormulasService.processSuccessfulSave(isNew);
                $state.go('modules.formulasList', {moduleId: FormulasService.fetchCurrentModule().id});
            }, function error(reason) {
                addDangerMsg(true, reason.data);
            }
        );
    };

    validate = function (t) {
        if (t.length === 0) {
            addDangerMsg(false, 'Please build an equation by selecting questions, answers, constants, operators, or you can also enter constants or any operator not found in the list.');
            return false;
        }
        return true;
    };

    $scope.tagFormula = function (userEnteredToken) {
        // try to find this userEnteredToken in the list of variables already present in the reference variables
        var existingToken = _.find($scope.variables, function (variable) {
            return variable.name === userEnteredToken;
        });
        if (existingToken != undefined) {
            existingToken.guid = FormulasService.guid(existingToken.id);
            return existingToken;
        } else {
            var item = {
                name: userEnteredToken,
                id: 't|' + userEnteredToken.trim(),
                guid: FormulasService.guid(userEnteredToken)
            };
            return item;
        }
    };


    $scope.disable = false;
    $scope.toggleDisable = function () {
        $scope.disable = !$scope.disable;
    }

    var tokens = function () {
        return _.map($scope.formulaTemplate.selectedTokens, 'id');
    }
    var refVars = [];
    $scope.refreshVariables = function () {
        if ($scope.variables === undefined) {
            if (!FormulasService.hasValidModule()) return;
            // load variables from rest endpoint
            FormulasService.loadVarsByModuleId().then(function (vars) {
                $scope.variables = vars;
                refVars = _.clone(vars, true);
            }, function error(reason) {
                addDangerMsg(true, reason);
            });
        } else {
            $scope.variables = _.clone(refVars, true);
        }
    }

    $scope.$watch('formulaTemplate.selectedTokens', function (newValue, oldValue) {
        if (oldValue != newValue && newValue.length > 0) {
            var formulaToken = newValue[newValue.length - 1];
            if (parseInt(formulaToken.id, 10) != undefined) {
                var existingToken = _.find(refVars, function (refVar) {
                    return refVar.id === formulaToken.id;
                });
                if (existingToken != undefined) {
                    existingToken.guid = FormulasService.guid(existingToken.id);
                }
            }
        }
    });

}]);