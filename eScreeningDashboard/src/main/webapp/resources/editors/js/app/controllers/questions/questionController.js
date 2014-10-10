/**
 * Created by pouncilt on 8/6/14.
 */
Editors.controller('questionsController', ['$rootScope', '$scope', '$state', 'questionTypeDropDownMenuOptions', function($rootScope, $scope, $state, questionTypeDropDownMenuOptions){
    var getSelectedQuestionDomainObject = function () {
            var firstChildQuestionAnswers = (Object.isDefined($scope.selectedQuestionUIObject))?
                $scope.getFirstChildMeasureAnswers($scope.selectedQuestionUIObject.childQuestions): [],
                selectedQuestionDomainObject = null;

            if(Object.isDefined($scope.selectedQuestionUIObject)){
                $scope.selectedQuestionUIObject.childQuestions.forEach(function (childMeasure, index) {
                    if(index > 0) {
                        childMeasure.answers = firstChildQuestionAnswers;
                    }
                });

                selectedQuestionDomainObject = new EScreeningDashboardApp.models.Question($scope.selectedQuestionUIObject);
            }

            return selectedQuestionDomainObject;
        };

    $scope.questionTypeDropDownMenuOptions = questionTypeDropDownMenuOptions;
    $scope.partentAddToPageQuestion = $scope.addToPageQuestion;
    $scope.parentResetForm = $scope.resetForm;
    $scope.questionTypeSelection = {type: null};

    $scope.$watch('formReset', function(newFormResetFlag, oldFormResetFlag) {
        if (newFormResetFlag === oldFormResetFlag) {
            return;
        } else {
            if(newFormResetFlag) {
                $scope.questionTypeDropDownMenu = null;
            }
        }
    });

    $scope.goToQuestionTypeForm = function() {
        var stateName = $scope.getStateName($scope.questionTypeSelection.type),
            newQuestionUIObject = new EScreeningDashboardApp.models.Question({
                type: $scope.questionTypeSelection.type
            }).toUIObject();

        $scope.setSelectedPageQuestionItem($scope.addQuestion(newQuestionUIObject));
        $state.go(stateName, {selectedQuestionId: -1});
    };/*

    $scope.disableDropDownMenu = function () {
        var disableDropDownMenu = false;

        if(Object.isDefined($scope.selectedQuestionUIObject) && Object.isDefined($scope.selectedQuestionUIObject.type)){
            if(Object.isDefined($scope.questionTypeDropDownMenu)) {
                disableDropDownMenu = true;
            }
        }

        return disableDropDownMenu;
    };*/

    $scope.addToPageQuestion = function (state, resetFormFunction, softReset) {
        var selectedQuestionDomainObject;

        state = (Object.isDefined(state))? state: {
            name: "modules.detail.selectQuestionType",
            params: {selectedQuestionId: -1},
            doTransition: false
        };
        resetFormFunction = (Object.isDefined(resetFormFunction))? resetFormFunction: $scope.resetForm;
        softReset = (Object.isBoolean(softReset))? softReset : false;

        //TODO: Get selected question from SurveyPageItems.
        selectedQuestionDomainObject = getSelectedQuestionDomainObject();

        if(Object.isDefined(selectedQuestionDomainObject)){
            if(Object.isDefined($scope.selectedPageQuestionItem)) {
                //TODO: Need to update the original values saved in the page quesiton items.
                angular.copy(selectedQuestionDomainObject.toUIObject(), $scope.selectedPageQuestionItem.getItem());
            } else {
                $scope.addQuestion(selectedQuestionDomainObject.toUIObject());
            }
        }

        resetFormFunction(softReset, state);
    };

    $scope.resetForm = function (softReset, state){
        softReset = (Object.isBoolean(softReset))? softReset : false;
        state = (Object.isDefined(state))? state: {
            name: "modules.detail.selectQuestionType",
            params: {selectedQuestionId: -1},
            doTransition: false
        };

        if(!softReset) {
            $scope.questionTypeDropDownMenu = null;
        }


        $scope.parentResetForm(softReset, state);
    };

    $scope.goToAddEdit = function(){
        $state.go('modules.detail.selectQuestionType');
    };
}]);