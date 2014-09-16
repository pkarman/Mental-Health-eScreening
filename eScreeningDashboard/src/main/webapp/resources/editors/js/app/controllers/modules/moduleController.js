/**
 * Created by pouncilt on 8/4/14.
 */
Editors.controller('moduleController', ['$rootScope', '$scope', '$state', function ($rootScope, $scope, $state) {
    var createModule = function(jsonModuleObject){
            if(Object.isDefined(jsonModuleObject)) {
                return new EScreeningDashboardApp.models.Survey(jsonModuleObject);
            } else {
                return new EScreeningDashboardApp.models.Survey({
                    title:'Enter Module Title',
                    description:'Enter Module Description'
                });
            }

        },
        createQuestion = function(jsonQuestionObject){
            return new EScreeningDashboardApp.models.Question(jsonQuestionObject);
        };

    $scope.pageQuestionItems = [];
    $scope.selectedSurveyUIObject = createModule().toUIObject();
    $scope.selectedPageQuestionItem = {};
    $scope.createModule = createModule;
    $scope.createQuestion = createQuestion;
    $scope.formReset = false;
    $scope.showUpdateButtons = false;
    $scope.questionSaveButtonLabelText = "Add To Page"; //Update Question

    $scope.$watch('pageQuestionItems', function(newValue, oldValue) {
        if (newValue === oldValue) {
            return;
        } else {
            $scope.pageQuestionItems.forEach(function(pageQuestionItem, index) {
                if(pageQuestionItem.isPage()) {
                    pageQuestionItem.getItem().setPageNumber(index+1);
                }
            });
        }
    }, true);

    $scope.changeQuestionSaveButtonLabel = function(buttonLabelText) {
        $scope.questionSaveButtonLabelText = buttonLabelText;
    };

    $scope.setFormReset = function(formReset) {
        $scope.formReset = formReset;
    };

    $scope.setSelectedSurveyUIObject = function(someSelectedQuestionUIObject) {
        $scope.selectedSurveyUIObject = someSelectedQuestionUIObject
    };

    $scope.setSelectedQuestionUIObject = function (someSelectedQuestionUIObject) {
        $scope.selectedQuestionUIObject = someSelectedQuestionUIObject;
    };

    $scope.setSelectedPageQuestionItem = function(someSelectedPageQuestionItem) {
        $scope.selectedPageQuestionItem = someSelectedPageQuestionItem;
    };

    $scope.setPageQuestionItems = function(newPageQuestionItems) {

    };

    $scope.addPageBreak = function(){
        var surveyPage = new EScreeningDashboardApp.models.SurveyPage(null),
            pageQuestionItem = new EScreeningDashboardApp.models.PageQuestionItem(surveyPage);

        $scope.pageQuestionItems.push(pageQuestionItem);
    };

    $scope.addQuestion = function(someQuestion) {
        var question = (Object.isDefined(someQuestion))? someQuestion: new EScreeningDashboardApp.models.Question(null),
            pageQuestionItem = new EScreeningDashboardApp.models.PageQuestionItem(question);

        if($scope.pageQuestionItems.length === 0) {
           $scope.addPageBreak();
        }

        $scope.pageQuestionItems.push(pageQuestionItem);

    };

    $scope.updatePageQuestionItem = function(somePageQuestionItem) {
        var foundExistingPageQuestionItem = $scope.pageQuestionItems.find(function (pageQuestionItem) {
            if(pageQuestionItem.getId() === somePageQuestionItem.getId()){
                return pageQuestionItem;
            }
        });

        if(Object.isDefined(foundExistingPageQuestionItem)) {
            angular.copy(somePageQuestionItem, foundExistingPageQuestionItem);
        }
    };

    $scope.deletePageQuestionItem = function (pageQuestionItem) {
        var matchIndex = -1;
        if(Object.isDefined(pageQuestionItem)) {
            matchIndex = $scope.pageQuestionItems.indexOf(pageQuestionItem);

            if(matchIndex > -1) {
                $scope.pageQuestionItems.splice(matchIndex, 1);
            }
        }
    };


    $scope.resetForm = function(softReset, state) {
        softReset = (Object.isBoolean(softReset))? softReset: false;

        $scope.setSelectedQuestionUIObject(createQuestion().toUIObject());

        if(!softReset) {
            $scope.setFormReset(true);
        }

        $scope.selectedPageQuestionItem = null;
        $scope.changeQuestionSaveButtonLabel("Add To Page");

        if(Object.isDefined(state) && Object.isBoolean(state.doTransition)) {
            if(state.doTransition) {
                if (Object.isString(state.name)) {
                    if (Object.isDefined(state.params)) {
                        $state.go(state.name, state.params);
                    } else {
                        $state.go(state.name);
                    }
                }
            }
        }
    };

    $scope.goToQuestions = function() {
        var softReset = false,
            state = {
                name: "modules.detail.questions.blank",
                params: {selectedQuestionId: -1},
                doTransition: true
            };
        $scope.resetForm(false, state);
    };











    $rootScope.createBattery = function(){
        return {
            batteryId:null,
            title:"Enter Battery Title",
            description:"Enter Battery Description",
            sections:[]
        };
    };

    $rootScope.createSection = function(){
        return {
            sectionId:null,
            title:'Enter Section Title',
            modules:[]
        }
    };

    $rootScope.createFormula = function(){
        alert('Not presently implemented');
    };

    $rootScope.createTemplate = function(){
        alert('Not presently implemented');
    };

    $rootScope.createRule = function(){
        alert('Not presently implemented');
    };

    $rootScope.createTableQuestion = function(){
        var q = $scope.createQuestion();
        q.childAnswers = [];
        return q;
    };

    //$rootScope.battery = $rootScope.createBattery();


    /*$scope.addQuestion = function(){
        $scope.selectedQuestion = $rootScope.createQuestion();
        $scope.goToQuestions();
    };*/

    $scope.editQuestion = function(q){
        //$scope.selectedQuestion = q;
        $scope.goToQuestions();
    };

    $scope.deleteQuestion = function(q){
        console.info("deleteQuestion() method.");
    };

    /* ---- Button Actions ---- */

    $scope.goToSelection = function(){
        $state.go('modules.list');
    };

    $scope.goToSections = function(){
        $state.go('modules.detail.mapsection');
    };

    $scope.goToMapConsult = function(){
        $state.go('modules.detail.questions.mapconsult');
    };

    $scope.goToFormulaExpression = function(){
        $state.go('modules.detail.expressioneditor');
    };

    $scope.goToCreateVar = function(){
        $state.go('modules.detail.createvariable.questionvariable');
    };
}]);