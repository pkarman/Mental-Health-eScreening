/**
 * Created by pouncilt on 8/4/14.
 */
Editors.controller('moduleController', ['$rootScope', '$scope', '$state', function ($rootScope, $scope, $state) {
    var selectedPage;

    var createModule = function(jsonModuleObject){
            return new EScreeningDashboardApp.models.Survey(jsonModuleObject);
        },
        createQuestion = function(jsonQuestionObject){
            return new EScreeningDashboardApp.models.Question(jsonQuestionObject);
        };

    $scope.surveyUIObjects = [];
    $scope.pageQuestionItems = [];
    $scope.selectedSurveyUIObject = createModule().toUIObject();
    $scope.selectedPageQuestionItem = {};
    $scope.createModule = createModule;
    $scope.createQuestion = createQuestion;
    $scope.formReset = false;

    $scope.$watch('pageQuestionItems', function(newValue, oldValue) {
        var pageIndex = 0;
        if (newValue === oldValue) {
            return;
        } else {
            $scope.pageQuestionItems.forEach(function(pageQuestionItem) {
                if(pageQuestionItem.isPage()) {
                    pageQuestionItem.getItem().pageNumber = ++pageIndex;
                }
            });
        }
    }, true);

    $scope.organizePages = function () {
        var organizedPages = [];

        $scope.pageQuestionItems.forEach(function(item) {
            if(item.isPage()){
                organizedPages.push(item.getItem());
            } else if(organizedPages.length > 0){
                organizedPages[organizedPages.length-1].questions.push(item.getItem());
            }
        });

        if(organizedPages.length === 1 &&
            Object.isArray(organizedPages[0].questions) &&
            organizedPages[0].questions.length === 0) {
            organizedPages = [];
        }

        return organizedPages;
    };

    $scope.setSurveyUIObjects = function(newSurveyUIObjects) {
        $scope.surveyUIObjects = newSurveyUIObjects;
    };

    $scope.getSurveyUIObjects = function() {
        return $scope.surveyUIObjects;
    };

    $scope.setSelectedPage = function (someSelectedPage){
        selectedPage = someSelectedPage;
    };

    $scope.getSelectedPage = function(){
        return selectedPage;
    };

    $scope.setFormReset = function(formReset) {
        $scope.formReset = formReset;
    };

    $scope.setSelectedSurveyUIObject = function(someSelectedQuestionUIObject) {
        $scope.selectedSurveyUIObject = someSelectedQuestionUIObject
    };

    $scope.setSelectedPageQuestionItem = function(someSelectedPageQuestionItem) {
        $scope.selectedPageQuestionItem = someSelectedPageQuestionItem;
    };

    $scope.getSelectedPageQuestionItem = function() {
        return $scope.selectedPageQuestionItem;
    };

    $scope.setPageQuestionItems = function(newPageQuestionItems) {
        $scope.pageQuestionItems = newPageQuestionItems;
    };

    $scope.addPageBreak = function(surveyPageUIObject){
        var surveyPageUIObject = new EScreeningDashboardApp.models.SurveyPage(surveyPageUIObject).toUIObject(),
            pageQuestionItem = new EScreeningDashboardApp.models.SurveyPageUIObjectItemWrapper((($scope.pageQuestionItems.length === 0)? {surveyPageUIObject: surveyPageUIObject, enabled: false} : {surveyPageUIObject: surveyPageUIObject}));

        $scope.pageQuestionItems.push(pageQuestionItem);
        return pageQuestionItem;
    };

    $scope.addQuestion = function(someQuestionUIObject) {
        var questionUIObject = (Object.isDefined(someQuestionUIObject))? someQuestionUIObject: new EScreeningDashboardApp.models.Question(null).toUIObject,
            pageQuestionItem = new EScreeningDashboardApp.models.QuestionUIObjectItemWrapper({questionIUObject: questionUIObject}),
            selectedPage = $scope.getSelectedPage(),
            trackPageQuestionItem = false,
            nextPageAfterSelectedPage;

        if($scope.pageQuestionItems.length === 0) {
           $scope.addPageBreak();
        }

        if((Object.isDefined(selectedPage))){
            nextPageAfterSelectedPage = $scope.pageQuestionItems.find(function(existingPageQuestionItem, index, pageQuestionItems) {

                if(existingPageQuestionItem === selectedPage){
                    trackPageQuestionItem = true;
                } else if(trackPageQuestionItem) {
                    if(existingPageQuestionItem.isPage()) {
                        $scope.pageQuestionItems.splice(index, 0, pageQuestionItem);
                        return true;
                    }
                }

                return false;
            });

            if(!Object.isDefined(nextPageAfterSelectedPage)) {
                $scope.pageQuestionItems.push(pageQuestionItem);
            }
        } else {
            $scope.pageQuestionItems.push(pageQuestionItem);
        }

        return pageQuestionItem;
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
        var matchIndex = -1,
            doStateTransition = (pageQuestionItem === $scope.getSelectedPageQuestionItem())? true: false;

        if(Object.isDefined(pageQuestionItem)) {
            matchIndex = $scope.pageQuestionItems.indexOf(pageQuestionItem);

            if(matchIndex > -1) {
                $scope.pageQuestionItems.splice(matchIndex, 1);
            }
        }

        if(doStateTransition) {
            $scope.setSelectedPageQuestionItem(null);
            $scope.setSelectedSurveyUIObject(null);
            $state.go('modules.detail.empty', {selectedQuestionId: -1})
        }
    };


    $scope.resetForm = function(softReset, state) {
        softReset = (Object.isBoolean(softReset))? softReset: false;

        if(!softReset) {
            $scope.setFormReset(true);
        }

        $scope.selectedPageQuestionItem = null;

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

    $scope.goToQuestions = function(selectedPage) {
        var softReset = false,
            state = {
                name: "modules.detail.selectQuestionType",
                params: {selectedQuestionId: -1},
                doTransition: true
            };
        $scope.setSelectedPage(selectedPage);
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
    
    $scope.editTemplates = function(){        
        $state.go('modules.templates', {selectedSurveyId: $scope.selectedSurveyUIObject.id});
    };
}]);