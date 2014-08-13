/**
 * Created by Bryan on 2/9/14.
 */
AssessmentEditor.controller('ddPageCtrl', function($rootScope, $scope, $compile, $timeout) {
    $scope.oneAtATime = true;
    $scope.list1 = [{'title': 'Text Question'},{'title': 'Select One'},{'title': 'Select Multi'}, {'title':'Table Question'}, {'title':'Select One Matrix'}, {title:"Select Multi Matrix"}, {title:"Instructions"}];
    $scope.list2 = [{'title': 'Quick Tool #1'},{'title': 'Quick Tool #2'},{'title': 'Quick Tool #3'}];
    $scope.list3 = [{'title': 'Survey Tool #1'},{'title': 'Survey Tool #2'},{'title': 'Survey Tool #3'}];
    $scope.list4 = [];

    $scope.currDroppedQuestionText = "Free/Read-Only Text";

    $scope.hideMe = function() {
        return $scope.list4.length > 0;
    }
    $scope.newMeasureIndex = 0;
    $scope.newPageIndex = 0;
    $scope.selectedAssessment = $rootScope.selectedAssessment;
    $scope.selectedSurvey = $rootScope.selectedAssessment.surveys[0];

    $scope.assessmentPage = {
        assessmentid:11,
        surveyid:3,
        surveyname:"Identification",
        pagenum: 1,
        pageTitle: "Please Enter Title",
        instructions: "Please Enter Instructions",
        measures:[]
    };

    $scope.createMeasureTemplate = function(qType){
        return {measureId:$scope.newMeasureIndex,
            measureText:"Question Placeholder Text",
            measureType:qType,
            displayOrder:1,
            answers:[],
            validations:[]
        };
    }

    $scope.pageChange = function(){
        //alert("Current Page:: " + this.n);
        $scope.currentPage = this.n;
    }

    $scope.surveyChanged = function(){
        $scope.numPages = $scope.selectedSurvey.pages.length;
        $scope.assessmentPage = $scope.selectedSurvey.pages[0];
        $scope.currentPage = 0;
        console.log("Current Page:: " + $scope.currentPage + ", Survey Title:: " + $scope.selectedSurvey.title);
    }

    $scope.dropped = function(dragEl, dropEl) {
        // this is your application logic, do whatever makes sense
        var drag = angular.element(dragEl);
        var drop = angular.element(dropEl);

        $scope.currDroppedQuestionText = drag.text();

        var newQ = $compile('<div id="'+ drag.attr('id') +'">{{currDroppedQuestionText}}</div')($scope).appendTo(drop);
        //class="form-group" style="width:100%;border:1 px solid #333333;"
        newQ.addClass('form-group');
        newQ.css('width', '100%').css('border', '1px dashed #ff0000').css('text-align', 'center').css('font-weight', '700').css('font-size', '18px').css('padding', '10px').css('border-radius', '2px');
        newQ.text("New " + $scope.currDroppedQuestionText);

        console.log("The element " + drag.text() + " has been dropped on " + drop.attr("id") + "!");
    };

    // Pagination.
    $scope.totalItems = 10;
    $scope.currentPage = 0;
    $scope.numPages = 1;
    $scope.maxSize = 8;

    $scope.addPage = function(){
        $scope.selectedSurvey.pages.push($scope.createPage());
        console.log('addPage called - ' + $scope.selectedSurvey.pages.length);
    }

    $scope.deletePage = function(){
        console.log("deletePage called.");
    }

    $scope.range = function (start, end) {
        var ret = [];
        if (!end) {
            end = start;
            start = 0;
        }
        for (var i = start; i < end; i++) {
            ret.push(i);
        }
        return ret;
    };

    $scope.prevPage = function () {
        if ($scope.currentPage > 0) {
            $scope.currentPage--;
            $scope.assessmentPage = $scope.selectedSurvey.pages[$scope.currentPage];
            //alert("Current Page:: " + $scope.currentPage);
        }
    };

    $scope.nextPage = function () {
        if ($scope.currentPage < $scope.selectedSurvey.pages.length - 1) {
            $scope.currentPage++;
            $scope.assessmentPage = $scope.selectedSurvey.pages[$scope.currentPage];
            console.log("Current Page:: " + $scope.currentPage);
        }
    };

    $scope.setPage = function () {
        $scope.currentPage = this.n;
        $scope.assessmentPage = $scope.selectedSurvey.pages[$scope.currentPage];
        console.log("Current Page Title:: " + $scope.assessmentPage.pageTitle);
    };
});
