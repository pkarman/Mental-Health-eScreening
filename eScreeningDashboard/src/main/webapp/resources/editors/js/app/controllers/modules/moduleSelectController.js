/**
 * Created by pouncilt on 8/4/14.
 */
Editors.controller('moduleSelectController',['$rootScope','$scope','$state', '$filter', 'ngTableParams', function($rootScope, $scope, $state, $filter, ngTableParams){

    $scope.modules = [
        {id:1, title:'Identification', status:'Published', description:'Veteran\'s self identification module.', questions:[]},
        {id:2, title:'Demographics', status:'Editable', description:'Veteran\'s demographic information.', questions:[]},
        {id:3, title:'Service History',  status:'Editable', description:'Veteran\'s Military Service history module.', questions:[]},
        {id:4, title:'Spiritual Beliefs', status:'Published', description:'Veteran\'s spiritual beliefs.', questions:[]},
        {id:5, title:'General Physical Health', status:'Editable', description:'Veteran\'s physical health module.', questions:[]},
        {id:6, title:'General Mental Health', status:'Editable', description:'Veteran\'s mental health module (general).', questions:[]},
        {idx:7, title:'OEF/OIF PTSD', status:'Editable', description:'Post-Traumatic Stress Disorder module.', questions:[]},
        {id:8, title:'OEF/OIF Anxiety Spectrum', status:'Editable', description:'Anxiety Spectrum identification module.', questions:[]},
        {id:9, title:'OEF/OIF Something 1', status:'Published', description:'I\'m your friendly, neighborhood description!', questions:[]},
        {id:10, title:'OEF/OIF Something 2', status:'Editable', description:'I\'m your friendly, neighborhood description!', questions:[]},
        {id:11, title:'OEF/OIF Something 3', status:'Editable', description:'I\'m your friendly, neighborhood description!', questions:[]}
    ]

    var data = $scope.modules;

    $scope.tableParams = new ngTableParams({
        page: 1,            // show first page
        count: 10,          // count per page
        filter: {
            title: ''       // initial filter
        },
        sorting: {
            title: 'asc'     // initial sorting
        }
    }, {
        total: data.length, // length of data
        getData: function($defer, params) {
            // use build-in angular filter
            var filteredData = params.filter() ?
                $filter('filter')(data, params.filter()) :
                data;
            var orderedData = params.sorting() ?
                $filter('orderBy')(filteredData, params.orderBy()) :
                data;

            params.total(orderedData.length); // set total for recalc pagination
            $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
        }
    });

    $scope.buildQuestions = function(){
        var ques = [];
        var qId = 1;
        for (var i = 0; i<10; i++)
        {
            var q = $rootScope.createQuestion();
            q.measureId = qId;
            q.measureText = "What is your name? Q:: " + qId;
            q.displayOrder = qId;
            q.measureType="freeText";
            q.vistaVariable="00A00" + qId;
            q.helpText="I am some Help Text.";
            q.ppi=true;
            q.mha=false;
            q.answers=[];
            q.validations=[];
            ques.push(q);
            qId++;
        }

        return ques;
    }

    /* ---- Button Actions ---- */
    $scope.editModule = function(mod){
        $scope.module = mod;
        mod.questions =  $scope.buildQuestions();
        $state.go('modules.detail.question');
    }

    $scope.addModule = function(){
        $scope.module = $scope.createModule();
        $state.go('modules.detail.question');
    }

    $scope.goToAddEdit = function(){
        $state.go('modules.detail.question');
    }

    $scope.cancel = function(){
        alert('Will take user back to Editors Entry View.');
    }
}]);