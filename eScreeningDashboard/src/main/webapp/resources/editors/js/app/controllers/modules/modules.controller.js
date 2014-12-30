
Editors.controller('ModulesController', ['$rootScope', '$scope', '$state', function ($rootScope, $scope, $state) {

    var selectedPage;

    $scope.formReset = false;

    $scope.organizePages = function () {
        var organizedPages = [];

        // Loop through the pageQuestionItems domain objects
        $scope.pageQuestionItems.forEach(function(item) {
            // Check if the item is a page using the isPage accessor
            if(item.isPage()){
                // Add the page to the organizedPages array
                organizedPages.push(item.getItem());
                // Clear out any existing questions (needed when saving/updating surveys with existing pages
                organizedPages[organizedPages.length-1].questions = [];
            } else if(organizedPages.length > 0){
                // Item is a question, add it to last page in the organizedPages array
                organizedPages[organizedPages.length-1].questions.push(item.getItem());
            }
        });

        // Return empty array if no questions exist on the first page (seems brute force)
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
        $scope.survey = someSelectedQuestionUIObject
    };

    $scope.setSelectedPageQuestionItem = function(someSelectedPageQuestionItem) {
        $scope.selectedPageQuestionItem = someSelectedPageQuestionItem;
    };

    $scope.getSelectedPageQuestionItem = function() {
        return $scope.selectedPageQuestionItem;
    };

    $scope.setPageQuestionItems = function(newPageQuestionItems) {
        $scope.surveyPages = newPageQuestionItems;
    };

    $scope.addPageBreak = function(surveyPageUIObject){
        var surveyPageUIObject = new EScreeningDashboardApp.models.SurveyPage(surveyPageUIObject).toUIObject(),
            pageQuestionItem = new EScreeningDashboardApp.models.SurveyPageUIObjectItemWrapper((($scope.surveyPages.length === 0)? {surveyPageUIObject: surveyPageUIObject, enabled: false} : {surveyPageUIObject: surveyPageUIObject}));

        $scope.surveyPages.push(pageQuestionItem);
        return pageQuestionItem;
    };

    $scope.addQuestion = function(someQuestionUIObject) {
        var questionUIObject = (Object.isDefined(someQuestionUIObject))? someQuestionUIObject: new EScreeningDashboardApp.models.Question(null).toUIObject,
            pageQuestionItem = new EScreeningDashboardApp.models.QuestionUIObjectItemWrapper({questionIUObject: questionUIObject}),
            selectedPage = $scope.getSelectedPage(),
            trackPageQuestionItem = false,
            nextPageAfterSelectedPage;

        if($scope.surveyPages && $scope.surveyPages.length === 0) {
           $scope.addPageBreak();
        }

        if((Object.isDefined(selectedPage))){
            nextPageAfterSelectedPage = $scope.surveyPages.find(function(existingPageQuestionItem, index, surveyPages) {

                if(existingPageQuestionItem === selectedPage){
                    trackPageQuestionItem = true;
                } else if(trackPageQuestionItem) {
                    if(existingPageQuestionItem.isPage()) {
                        $scope.surveyPages.splice(index, 0, pageQuestionItem);
                        return true;
                    }
                }

                return false;
            });

            if(!Object.isDefined(nextPageAfterSelectedPage)) {
                $scope.surveyPages.push(pageQuestionItem);
            }
        } else {
            $scope.surveyPages.push(pageQuestionItem);
        }

        return pageQuestionItem;
    };

    $scope.updatePageQuestionItem = function(somePageQuestionItem) {
        var foundExistingPageQuestionItem = $scope.surveyPages.find(function (pageQuestionItem) {
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
            matchIndex = $scope.surveyPages.indexOf(pageQuestionItem);

            if(matchIndex > -1) {
                $scope.surveyPages.splice(matchIndex, 1);
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
                name: "modules.detail.list",
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

    $rootScope.createTableQuestion = function(){
        var q = $scope.createQuestion();
        q.childAnswers = [];
        return q;
    };

    $scope.editQuestion = function(){
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
    
    $scope.editTemplates = function(surveyId, surveyName){
        $state.go('modules.templates', 
                {selectedSurveyId: surveyId,
                 selectedSurveyName: encodeURIComponent(surveyName)});
    };
}]);
