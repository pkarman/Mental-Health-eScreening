/**
 * Created by pouncilt on 8/4/14.
 */
Editors.controller('moduleController', ['$rootScope', '$scope', '$state', function ($rootScope, $scope, $state) {
    $rootScope.selectedQuestion = {};

    $scope.createModule = function(){
        return {
            index:null,
            moduleId:null,
            title:'Enter Module Title',
            status:'',
            description:'Enter Module Description',
            questions:[]
        };
    };

    $rootScope.createQuestion = function(){
        return {
            measureId:null,
            displayOrder:-1,
            measureText:"",
            measureType:"",
            vistaVariable: "",
            helpText:"",
            ppi:false,
            mha:false,
            answers:[],
            validations:[]
        };
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

    $rootScope.battery = $rootScope.createBattery();
    $scope.module = $scope.createModule();

    $scope.addQuestion = function(){
        $scope.selectedQuestion = $rootScope.createQuestion();
        $scope.goToQuestions();
    };

    $scope.editQuestion = function(q){
        $scope.selectedQuestion = q;
        $scope.goToQuestions();
    };

    $scope.deleteQuestion = function(q){

    };

    $scope.goToQuestions = function(){

        $state.go('modules.detail.editReadOnlyQuestion');
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