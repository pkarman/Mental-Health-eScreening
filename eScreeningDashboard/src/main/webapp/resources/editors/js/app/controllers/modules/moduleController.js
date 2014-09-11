/**
 * Created by pouncilt on 8/4/14.
 */
Editors.controller('moduleController', ['$rootScope', '$scope', '$state', function ($rootScope, $scope, $state) {
    var createModule = function(){
            return new EScreeningDashboardApp.models.Survey({
                title:'Enter Module Title',
                description:'Enter Module Description'
            });
        },
        createQuestion = function(){
            return new EScreeningDashboardApp.models.Question();
        };
    //$rootScope.selectedQuestion = {};
    $scope.pageQuestionItems = [];
    $scope.selectedSurveyUIObject = createModule().toUIObject();
    $scope.createModule = createModule;
    $scope.createQuestion = createQuestion;
    $scope.formReset = false;

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

    $scope.deletePageQuestionItem = function (pageQuestionItem) {
        var matchIndex = -1;
        if(Object.isDefined(pageQuestionItem)) {
            matchIndex = $scope.pageQuestionItems.indexOf(pageQuestionItem);

            if(matchIndex > -1) {
                $scope.pageQuestionItems.splice(matchIndex, 1);
            }
        }
    };

    $scope.resetForm = function(stateName, stateParams) {
        $scope.selectedQuestionUIObject = createQuestion().toUIObject();
        $scope.formReset = true;

        if(Object.isDefined(stateName)) {
            if(Object.isDefined(stateParams)) {
                $state.go(stateName, stateParams);
            } else {
                $state.go(stateName);
            }
        }
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

    $scope.goToQuestions = function() {
        //$state.go('modules.detail.questions.editReadOnly');
        $scope.selectedQuestionUIObject = createQuestion().toUIObject();
        $state.go('modules.detail.questions.blank');
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