/**
 * Created by Bryan on 2/5/14.
 */
AssessmentEditor.controller('standardCtrl', function($scope, $state, assessments, utils){
    $scope.assessments = assessments;

    $scope.assessmentSelect = function(aId){
        $state.go('assessment-builder/:'+aId);
    }

    $scope.newAssessment = function(){
        $state.go('assessment-builder');
    }
});