/**
 * Created by Joseph on 2/11/14.
 */
var AModalInstanceCtrl = function ($scope, $state, $modalInstance, cAssessment) {

    $scope.selectedAssessment = cAssessment;
    $scope.surveys = cAssessment.surveys;

    $scope.ok = function () {
        $modalInstance.close($scope.selectedAssessment);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};