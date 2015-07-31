/**
 * Represents the application api.  If the variable is already defined use it,
 * otherwise create an empty object.
 *
 * @type {BytePushers|*|BytePushers|*|{}|{}}
 */
var BytePushers = BytePushers || {};
/**
 * Represents the application static variable. Use existing static variable, if one already exists,
 * otherwise create a new one.
 *
 * @static
 * @type {*|BytePushers.models|*|BytePushers.models|Object|*|Object|*}
 */
BytePushers.models = BytePushers.models || BytePushers.namespace("com.byte-pushers.models");
/**
 * Constructor method for the MessageHandler class.  The properties of this class can be initialized with
 * the jsonUserObject.
 * @class
 * @classdesc   This class is a handler class; which means it has the behaviour to know how to handle the Message.
 * @param {String}  jsonMessagesArrayObject  Represents the JSON representation of a Answer object.
 * @constructor
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
BytePushers.models.MessageHandler = function (jsonMessagesArrayObject) {
    var messages = (Object.isArray(jsonMessagesArrayObject) && jsonMessagesArrayObject.length > 0)? jsonMessagesArrayObject : [];

    this.filterByErrorMessage = function(message, index) {
        if(Object.isDefined(message)) {
            if(message.getType().toLowerCase() === "error") {
                if(!Object.isDefined(message.getValue())) {
                    message.setValue(BytePushers.models.Message.ERROR_MSG);
                }
                return message;
            }
        }
    };

    this.filterBySuccessMessage = function(message, index) {
        var filteredMessage = this.filterBySuccessSaveMessage(message, index);

        if(!Object.isDefined(filteredMessage)) {
            filteredMessage = this.filterBySuccessDeleteMessage(message, index);
        }

        if(Object.isDefined(filteredMessage)) {
            return filteredMessage;
        }
    };

    this.filterBySuccessSaveMessage = function(message, index) {
        if(Object.isDefined(message)) {
            if(message.getType().toLowerCase() === BytePushers.models.Message.SUCCESSFUL_SAVE) {
                if(!Object.isDefined(message.getValue())) {
                    message.setValue(BytePushers.models.Message.SUCCESS_SAVE_MSG);
                }
                return message;
            }
        }
    };

    this.filterBySuccessDeleteMessage = function(message, index) {
        if(Object.isDefined(message)) {
            if(message.getType().toLowerCase() === BytePushers.models.Message.SUCCESSFUL_DELETE) {
                if(!Object.isDefined(message.getValue())) {
                    message.setValue(BytePushers.models.Message.SUCCESS_DELETE_MSG);
                }
                return message;
            }
        }
    };

    this.hasErrorMessages = function() {
        return messages.some( function(message, index) {
            if(Object.isDefined(message)) {
                if (message.getType().toLowerCase() === "error") {
                    return true;
                }
            }
        });
    };

    this.hasSuccessMessages = function() {
        return messages.some( function(message, index) {
            if(message.getType().toLowerCase() === BytePushers.models.Message.SUCCESSFUL_SAVE ||
                message.getType().toLowerCase() === BytePushers.models.Message.SUCCESSFUL_DELETE){
                return true;
            }
        });
    };

    this.clearMessages = function(type) {
        type = (Object.isDefined(type))? type : "all";

        messages.forEach( function(message, index) {
            if(type === "all" 
                || message.getType().toLowerCase() === type){
                removeMessage(index);
            }
        });
    };

    function removeMessage(index){
        if(messages[index].numLives == 0){
            messages.splice(index, 1);
        }
        else{
            messages[index].numLives--;
        }
    }
    
    /**
     * @param someMessage is the message object we are adding
     * @param addDuplicateMessages flag to allow duplicates
     * @param lives is an integer indicating how many times clearMessages() can be called 
     * on the message without it being removed.  This is used for times when state transitions 
     * happen and you want the message to stay around for the next state.
     */
    this.addMessage = function (someMessage, addDuplicateMessages, lives){
        addDuplicateMessages = (Object.isDefined(addDuplicateMessages))? addDuplicateMessages : false;
        someMessage.numLives = Object.isDefined(lives) && parseInt(lives) == lives ? lives : 0;
        
        if(addDuplicateMessages){//we don't care if there are dups
            messages.push(someMessage);
        }
        else{
            var foundDuplicatedMessage = messages.some(function (message) {
                if(message.getValue() === someMessage.getValue()) {
                    return true;
                }
            });

            if(!foundDuplicatedMessage) {
                messages.push(someMessage);
            }
        }
    };

    this.getMessages = function(){
        return messages;
    };
};