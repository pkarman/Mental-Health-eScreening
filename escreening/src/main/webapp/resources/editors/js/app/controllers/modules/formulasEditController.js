/**
 * Created by Khalid Rizvi @ 01/24/2015
 */

(function () {
    'use strict';

    angular.module('Editors').filter('tokensfilter', function () {
        return function (tokens, srchThis) {
            var out = [];

            if (angular.isArray(tokens)) {
                tokens.forEach(function (item) {
                    var itemMatches = false,
                        keys = Object.keys(srchThis);
                    for (var i = 0; i < keys.length; i++) {
                        var prop = keys[i],
                            text = srchThis[prop].toLowerCase();
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
})();

(function () {
    'use strict';

    angular.module('Editors').controller('ModuleFormulasEditController', ['$state', '$log', '$scope', 'FormulasService', 'MessageFactory', function ($state, $log, $scope, FormulasService, MessageFactory) {
        $scope.module = function () {
            return FormulasService.fetchCurrentModule().name;
        };
        $scope.alerts = MessageFactory.get();
        $scope.formulaTemplate = {};
        $scope.formula = FormulasService.fetchCurrentFormula();
        $scope.formulaTemplate.selectedTokens = $scope.formula.selectedTokens;
        $scope.verifiedIds = [];
        $scope.result = {};

        // now see if the current formula is not new, if it is not new than we have to load the fresh copy from database
        if (!FormulasService.isNewFormula()) {
            FormulasService.loadCurrentFormula().then(function () {
                $scope.formula = FormulasService.fetchCurrentFormula();
                $scope.formulaTemplate.selectedTokens = $scope.formula.selectedTokens;
            })
        }

        $scope.produceInputFields = function () {
            $scope.toggleDisable();
            $scope.result = {};
            var t = tokens();
            if (validate(t)) {
                FormulasService.verifySelectedTokens(t).then(function (result) {
                    FormulasService.attachWithCurrentFormula(result.verifiedIds);
                    $scope.verifiedIds = result.verifiedIds;
                    MessageFactory.set('success', 'The formula is verified successfully', false, true);
                }, function error(reason) {
                    MessageFactory.set('danger', reason.data.reason, false, true);
                });
            }
        };

        $scope.formulaReady = function () {
            return $scope.formulaTemplate.selectedTokens.length > 0;
        };

        $scope.tested = function () {
            return $scope.result.data != undefined;
        };

        $scope.inputsAvailable = function () {
            function haveValues(verifiedIds) {
                var incompleteInput = _.find(verifiedIds, function (verifiedId) {
                    return _.isEmpty(verifiedId.value);
                });

                return _.isEmpty(incompleteInput);
            }

            return $scope.verifiedIds.length > 0 && haveValues($scope.verifiedIds);
        };

        $scope.runTest = function () {
            var t = tokens();
            if (validate(t)) {
                FormulasService.testSelectedTokens(t)
                    .then(function (result) {
                        $scope.result.data = result.data;
                        MessageFactory.set('success', 'Please verify the result and either save or change the values and test again', false, true);
                    }, function error(reason) {
                        MessageFactory.set("danger", reason.data.data, false, true);
                    }
                );
            }
        };

        $scope.cancelFormula = function () {
            $state.go('modules.formulasList', {moduleId: FormulasService.fetchCurrentModule().id});
        };

        $scope.saveFormula = function () {
            // reset all errrors
            MessageFactory.clear();

            var isNew = false;
            if ($scope.formula.id === undefined) {
                isNew = true;
            }

            //verify data -- name is required
            var n = FormulasService.fetchCurrentFormula().name;
            if (n === undefined || n.length === 0) {
                MessageFactory.error('Name of the formula is required.', true);
            }

            var t = tokens();
            validate(t);

            if (!MessageFactory.isEmpty()) {
                return;
            }

            FormulasService.persistSelectedTokens(t)
                .then(function (result) {
                    FormulasService.updateCurrentFormula(result);
                    FormulasService.processSuccessfulSave(isNew);
                    $state.go('modules.formulasList', {moduleId: FormulasService.fetchCurrentModule().id});
                }, function error(reason) {
                    MessageFactory.set("danger", reason.data, false, true);
                }
            );
        };

        var validate = function (t) {
            if (t.length === 0) {
                MessageFactory.error('Please build an equation by selecting questions, answers, constants, operators, or you can also enter constants or any operator not found in the list.', true);
                return false;
            }
            return true;
        };

        // called when user presses ENTER key
        $scope.tagFormula = function (userEnteredToken) {
            // try to find this userEnteredToken in the list of variables already present in the reference variables
            var existingToken = _.find($scope.variables, function (variable) {
                return variable.name === userEnteredToken;
            });
            if (existingToken != undefined) {
                existingToken.guid = FormulasService.guid(existingToken.id);
                return existingToken;
            } else {
                return {
                    name: userEnteredToken,
                    id: 't|' + userEnteredToken.trim(),
                    guid: FormulasService.guid(userEnteredToken)
                };
            }
        };


        $scope.disable = false;
        $scope.toggleDisable = function () {
            $scope.disable = !$scope.disable;
        };

        var tokens = function () {
            return _.map($scope.formulaTemplate.selectedTokens, 'id');
        };

        // select-ui will call this as soon as user starts typing data by hand (not by clicking on drop down) in the edit area
        var refVars = [];
        $scope.refreshVariables = function () {
            if ($scope.variables === undefined) {
                if (!FormulasService.hasValidModule()) return;
                // load variables from rest endpoint
                FormulasService.loadVarsByModuleId().then(function (vars) {
                    $scope.variables = vars;
                    refVars = _.clone(vars, true);
                }, function error(reason) {
                    MessageFactory.set("danger", reason, false, true);
                });
            } else {
                $scope.variables = _.clone(refVars, true);
            }
        };

        // this function is called as soon as data is added or subtracted from selectedTokens array.
        // This will happens when user clicks on drop down and select a token from drop down or add
        // a token by hand and press enter or use backspace to delete a token
        $scope.$watch('formulaTemplate.selectedTokens', function (newValue, oldValue) {
            if (oldValue != newValue && newValue.length > 0) {
                // reset the result && verified fields so user is unable to save or run test
                $scope.result = {};
                $scope.verifiedIds = [];

                // all new data goes to the end of the list
                var formulaToken = newValue[newValue.length - 1];
                var existingToken = _.find(refVars, function (refVar) {
                    return refVar.id === formulaToken.id;
                });
                if (existingToken != undefined) {
                    existingToken.guid = FormulasService.guid(existingToken.id);
                }
            }
        });

    }]);
})();