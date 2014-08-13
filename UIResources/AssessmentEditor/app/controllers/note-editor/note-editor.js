/**
 * Created by Bryan on 2/9/14.
 */
AssessmentEditor.controller('NoteEditorCtrl', ['$scope', function($scope){
    $scope.noteHTMLVariable = '<h2>I am a note for the Note Editor.</h2><p>Write yourself some interesting text right here in this box.</p>';
    $scope.noteHTMLContent = $scope.noteHTMLVariable;
}]);