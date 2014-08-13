/**
 * Created by Joseph on 4/10/2014.
 */
var questionModalController = function ($scope, $modalInstance, q) {

    $scope.question = q;


    $scope.ok = function () {
        $modalInstance.close($scope.question);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};
