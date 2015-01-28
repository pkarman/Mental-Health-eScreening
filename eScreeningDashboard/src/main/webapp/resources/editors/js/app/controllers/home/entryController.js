/**
 * 
 */
Editors.controller('entryController', ['$rootScope', '$scope', '$state', 'MessageHandler', function($rootScope, $scope, $state, MessageHandler){
    $scope.entryApp = '/escreeningdashboard/';

    $rootScope.messageHandler = MessageHandler;

    
    $rootScope.addMessage = function(message) {
        if(Object.isDefined(message)) {
            $rootScope.messageHandler.addMessage(message);
        }
    };
    
    /**
     * Adds a message which will stay around for the next state.
     * Needed if you want to:
     *  - pass a message to another state because the action both makes a change and also transitions state
     *  - you want to set a message when a controller is being initialized
     */
    $rootScope.addInterstateMessage = function(message) {
        if(Object.isDefined(message)) {
            $rootScope.messageHandler.addMessage(message, null, 1);
        }
    };

    $rootScope.createSuccessDeleteMessage = function(message) {
        var msg = BytePushers.models.Message.SUCCESS_DELETE_MSG;
        if(Object.isDefined(message)){
            if(Object.isDefined(message.getValue)){
                msg = message.getValue();
            }
            else{ msg = message; }
        }
        
        return new BytePushers.models.Message({type: BytePushers.models.Message.SUCCESSFUL_DELETE, value: msg});
    };

    $rootScope.createSuccessSaveMessage = function (message) {
        var msg = BytePushers.models.Message.SUCCESS_SAVE_MSG;
        if(Object.isDefined(message)){
            if(Object.isDefined(message.getValue)){
                msg = message.getValue();
            }
            else{ msg = message; }
        }
        
        return new BytePushers.models.Message({type: BytePushers.models.Message.SUCCESSFUL_SAVE, value: msg});
    };

    $rootScope.createErrorMessage = function (message) {
        var msg = BytePushers.models.Message.ERROR_MSG;
        if(Object.isDefined(message)){
            if(Object.isDefined(message.getValue)){
                msg = message.getValue();
            }
            else{ msg = message; }
        }
        
        return new BytePushers.models.Message({type: BytePushers.models.Message.ERROR, value: msg})
    };

    $rootScope.batteries = [];

    $rootScope.selectedBattery = {};

    $rootScope.modules = [
        {id: 1, title: 'Identification', status: 'Published', description: 'Veteran\'s self identification module.', questions: []},
        {id: 2, title: 'Demographics', status: 'Editable', description: 'Veteran\'s demographic information.', questions: []},
        {id: 3, title: 'Service History', status: 'Editable', description: 'Veteran\'s Military Service history module.', questions: []},
        {id: 4, title: 'Spiritual Beliefs', status: 'Published', description: 'Veteran\'s spiritual beliefs.', questions: []},
        {id: 5, title: 'General Physical Health', status: 'Editable', description: 'Veteran\'s physical health module.', questions: []},
        {id: 6, title: 'General Mental Health', status: 'Editable', description: 'Veteran\'s mental health module (general).', questions: []},
        {idx: 7, title: 'OEF/OIF PTSD', status: 'Editable', description: 'Post-Traumatic Stress Disorder module.', questions: []},
        {id: 8, title: 'OEF/OIF Anxiety Spectrum', status: 'Editable', description: 'Anxiety Spectrum identification module.', questions: []},
        {id: 9, title: 'OEF/OIF Something 1', status: 'Published', description: 'I\'m your friendly, neighborhood description!', questions: []},
        {id: 10, title: 'OEF/OIF Something 2', status: 'Editable', description: 'I\'m your friendly, neighborhood description!', questions: []},
        {id: 11, title: 'OEF/OIF Something 3', status: 'Editable', description: 'I\'m your friendly, neighborhood description!', questions: []}
    ];


    $scope.goToModuleNew = function () {
        $state.go('modules.detail', {selectedSurveyId: -1});
    };

    $scope.goToModuleEdit = function () {
        //alert('This navigation is not implemented in this demonstrator.');
        $state.go('modules.list');
    };

    $scope.goToBatteryNew = function () {
        console.log('ENTRY:: New Battery View Selected.');
        $state.go('batteries.detail');
    };

    $scope.goToBatteryEdit = function () {
        console.log('ENTRY:: Edit Battery Selection View Selected.');
        $state.go('batteries.list');
    };

    $scope.goToSections = function () {
        console.log('ENTRY:: Manage Sections View Selected.');
        $state.go('sections');
    };

    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
        $rootScope.messageHandler.clearMessages();
    });
    
    //some error logging to reduce the amount of hair I pull out of my head :)
    $rootScope.$on('$stateChangeError', 
        function(event, toState, toParams, fromState, fromParams, error){
            console.log("Error transitioning from " + JSON.stringify(fromState) 
                    + "\n to state: " + JSON.stringify(toState)
                    + "\n with error: " + JSON.stringify(error));
        });
    
    
}]);