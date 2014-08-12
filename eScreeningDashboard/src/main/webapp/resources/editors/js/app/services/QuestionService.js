/**
 * Created by pouncilt on 8/5/14.
 */
/**
 * Created by pouncilt on 8/4/14.
 */
/**
 * Angular service factory method that creates the QuestionSerivce object.
 *
 * @author <a href="mailto:pouncilt.developer@gmail.com">Tont&eacute; Pouncil</a>
 */
angular.module('EscreeningDashboardApp.services.question', ['ngResource'])
    .factory('QuestionService', ['$q', '$resource', function ($q, $resource){
        "use strict";
        /**
         * Convenience method that supports the querying of the QuestionService.
         *
         * @private
         * @method
         * @param {{userId: *, questionId: string}} queryQuestionSearchCriteria Represents the search criteria to query the QuestionService.
         * @returns {promise} A promise.
         */
        var query = function (queryQuestionSearchCriteria) {
            /**
             * Represents the angular Defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {Defer}
             */
            var deferred = $q.defer(),
                service = $resource (
                    "services/:questions/:questionId",
                    {},
                    {
                        query: {
                            method: 'GET',
                            params: {},
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );

            service.query(queryQuestionSearchCriteria, function (jsonResponse) {
                var existingQuestions = handleQuestionQueryResponse(jsonResponse, EScreeningDashboardApp.models.QuestionsTransformer, null);
                deferred.resolve((existingQuestions.length === 0)? existingQuestions[0]: existingQuestions);
            }, function (reason) {
                var errorMessage = "Sorry, we are unable to process your request at this time because we experiencing problems communicating with the server."

                if(Object.isDefined(reason) && Object.isDefined(reason.status) && Object.isNumber(reason.status)) {
                    errorMessage = HttpRejectionProcessor.processRejection(reason);
                }

                deferred.reject(new BytePushers.models.ResponseStatus(
                    {
                        "message": errorMessage,
                        "status": "failed"
                    }
                ));
            });

            return deferred.promise;
        },
        /**
         * Convenience method that supports the querying of the QuestionService.
         *
         * @private
         * @method
         * @param {{userId: *, questionId: string}} queryQuestionSearchCriteria Represents the search criteria to query the QuestionService.
         * @returns {promise} A promise.
         */
        queryBySurveyId = function (setQueryBySurveyIdSearchCriteria) {
            /**
             * Represents the angular Defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {Defer}
             */
            var deferred = $q.defer(),
                service = $resource (
                    "services/surveys/:surveyId/:questions",
                    {},
                    {
                        query: {
                            method: 'GET',
                            params: {/*
                                "surveyId": setQueryBySurveyIdSearchCriteria.surveyId,
                                "questions": setQueryBySurveyIdSearchCriteria.questions
                            */},
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );

            service.query(setQueryBySurveyIdSearchCriteria, function (jsonResponse) {
                var existingQuestions = handleQuestionQueryResponse(jsonResponse, EScreeningDashboardApp.models.QuestionsTransformer, null);
                deferred.resolve((existingQuestions.length === 0)? existingQuestions[0]: existingQuestions);
            }, function (reason) {
                var errorMessage = "Sorry, we are unable to process your request at this time because we experiencing problems communicating with the server."

                if(Object.isDefined(reason) && Object.isDefined(reason.status) && Object.isNumber(reason.status)) {
                    errorMessage = HttpRejectionProcessor.processRejection(reason);
                }

                deferred.reject(new BytePushers.models.ResponseStatus(
                    {
                        "message": errorMessage,
                        "status": "failed"
                    }
                ));
            });

            return deferred.promise;
        },
        /**
         * Convenience method that supports updating a Question  via the QuestionService.
         *
         * @param {{questionId: String, question: EScreeningDashboardApp.models.Question, questions: String }}  saveQuestionRequestParameters Represents the save parameters for the request.
         */
        update = function (updateQuestionRequestParameter) {

            /**
             * Represents the angular Defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {Defer}
             */
            var deferred = $q.defer(),
                /**
                 * Represents the angular Resource object that is used for asynchronous service calls.
                 *
                 * @private
                 * @field
                 * @type {Resource}
                 */
                service = $resource(
                    "services/:questions/:questionId",
                    {},
                    {
                        save: {
                            method: 'PUT',
                            params: {
                                "questionId": updateQuestionRequestParameter.questionId,
                                "questions": updateQuestionRequestParameter.questions
                            },
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );

            service.save(updateQuestionRequestParameter.question.toJSON(), function (jsonResponse) {
                var existingQuestion = handleQuestionSaveResponse(jsonResponse, EScreeningDashboardApp.models.QuestionTransformer);
                deferred.resolve(existingQuestion);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        },
        /**
         * Convenience method that supports creating a Question via the QuestionService.
         *
         * @param {{questionId: String, question: EScreeningDashboardApp.models.Question, questions: String }}  saveQuestionRequestParameters Represents the save parameters for the request.
         */
        create = function (updateQuestionRequestParameter) {

            /**
             * Represents the angular Defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {Defer}
             */
            var deferred = $q.defer(),
                /**
                 * Represents the angular Resource object that is used for asynchronous service calls.
                 *
                 * @private
                 * @field
                 * @type {Resource}
                 */
                service = $resource(
                    "services/:question",
                    {},
                    {
                        save: {
                            method: 'PUT',
                            params: {
                                "question": updateQuestionRequestParameter.question
                            },
                            isArray: false,
                            headers:{
                                'Accept': 'application/json',
                                'Content-Type':'application/json'
                            }
                        }
                    }
                );

            service.save(updateQuestionRequestParameter.payload.toJSON(), function (jsonResponse) {
                var existingQuestion = handleQuestionSaveResponse(jsonResponse, EScreeningDashboardApp.models.QuestionTransformer);
                deferred.resolve(existingQuestion);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        },
        /**
         * Convenience method that supports removing an Question via the QuestionService.
         *
         * @param {{questionId: string}}  removeQuestionRequestParameters Represents the save parameters for the request.
         */
        remove = function (removeQuestionRequestParameters) {
            /**
             * Represents the angular Defer that is used for asynchronous service calls.
             *
             * @private
             * @field
             * @type {Defer}
             */
            var deferred = $q.defer(),
                /**
                 * Represents the angular Resource object that is used for asynchronous service calls.
                 *
                 * @private
                 * @field
                 * @type {Resource}
                 */
                service = $resource(
                    "services/:questions/:questionId",
                    {},
                    {
                        save: {
                            method: 'DELETE',
                            params: {
                                "questionId": removeQuestionRequestParameters.questionId
                            },
                            isArray: false
                        }
                    }
                );

            service.save(removeQuestionRequestParameters, function (jsonResponse) {
                var response = handleQuestionRemoveResponse(jsonResponse);
                deferred.resolve(response);
            }, function (reason) {
                deferred.reject(reason);
            });

            return deferred.promise;
        };

        /**
         * Convenience method that sets the request parameter for the question update service request.
         *
         * @private
         * @method
         * @param {EScreeningDashboardApp.models.Question} question Represents the Question domain object to update.
         * @returns {{questionId: *, questions: String}} A search criteria object.
         */
        var setUpdateQuestionRequestParameter = function (question) {
            var saveQuestionRequestParameter = {
                "questionId": question.getId(),
                "question": question,
                "questions": "questions"
            };

            if (!Object.isDefined(question.getId())) {
                delete saveQuestionRequestParameter.questionId;
                saveQuestionRequestParameter.questions = saveQuestionRequestParameter.questions + ".json";
            } else {
                saveQuestionRequestParameter.questionId = saveQuestionRequestParameter.questionId + ".json";
            }
            return saveQuestionRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the question remove service request.
         *
         * @private
         * @method
         * @param {String} questionId Represent the Question id to save.
         * @returns {{questionId: *, questions: String}} A search criteria object.
         */
        var setRemoveQuestionRequestParameter = function (questionId) {
            var removeQuestionRequestParameter = {
                "questionId": questionId,
                "questions": "questions"
            };

            if (!Object.isDefined(questionId)) {
                throw new BytePushers.exceptions.NullPointerException("questionId parameter can not be null");
            }

            return removeQuestionRequestParameter;
        };

        /**
         * Convenience method that sets the request parameter for the question create service request.
         *
         * @private
         * @method
         * @param {EScreeningDashboardApp.models.Question} question Represents the Question domain object to update.
         * @returns {{payload: EScreeningDashboardApp.models.Question, question: String}} A create parameter request object.
         */
        var setCreateQuestionRequestParameter = function (question) {
            var createQuestionRequestParameter = {
                "payload": question,
                "question": "question"
            };


            return createQuestionRequestParameter;
        };

        /**
         * Convenience method that sets the search criteria for the question search service query request.
         *
         * @private
         * @method
         * @param {String} questionId Represent the user id to search for.
         * @returns {{questionId: *, questions: String}} A search criteria object.
         */
        var setQueryQuestionSearchCriteria = function (questionId) {
            var findQuestionSearchCriteria = {
                "questionId": questionId,
                "questions": "questions"
            };

            if (!Object.isDefined(questionId)) {
                delete findQuestionSearchCriteria.questionId;
                findQuestionSearchCriteria.questions = findQuestionSearchCriteria.questions + ".json";
            } else {
                findQuestionSearchCriteria.questionId = findQuestionSearchCriteria.questionId + ".json";
            }

            return findQuestionSearchCriteria;
        };

        /**
         * Convenience method that sets the search criteria for the question search service query request.
         *
         * @private
         * @method
         * @param {String} surveyId Represent the survey id find question for.
         * @param {String} uriPath Represent the uri path of the WebService.
         * @returns {{questionId: *, questions: String}} A search criteria object.
         */
        var setQueryBySurveyIdSearchCriteria = function (surveyId, uriPath) {
            var findQuestionSearchCriteria = {
                "surveyId": surveyId,
                "questions": (Object.isDefined(uriPath))? uriPath: "questions"
            };

            if (!Object.isDefined(surveyId)) {
                throw new BytePushers.exceptions.InvalidParameterException("surveyId can not be null or undefined.");
            }

            if (!Object.isNumber(new Number(surveyId))) {
                throw new BytePushers.exceptions.InvalidParameterException("surveyId has to be an integer.");
            }

            findQuestionSearchCriteria.questions = findQuestionSearchCriteria.questions + ".json";

            return findQuestionSearchCriteria;
        };

        /**
         * Convenience method that handles the Question Service query response by returning a fully
         * transformed Question payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Question Service query response.
         * @param {EScreeningDashboardApp.models.QuestionsTransformer} jsonResponsePayloadTransformer Represents the Questions JSON Response Payload transformer.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed Question payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleQuestionQueryResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
            /**
             * Represents the response of a service call request.
             *
             * @private
             * @field
             * @type {EScreeningDashboardApp.models.Response}
             */
            var response = BytePushers.models.ResponseTransformer.transformJSONResponse(jsonResponse, jsonResponsePayloadTransformer, userId),
                /**
                 * Represents the transformed payload object of a service call request.
                 *
                 * @private
                 * @field
                 * @type {Object}
                 */
                payload = null;

            if (response !== null) {
                if (response.isSuccessful()) {
                    payload = response.getPayload();
                } else {
                    throw new Error(response.getMessage());
                }
            } else {
                throw new Error("handleQuestionServiceQueryResponse() method: Response is null.");
            }

            return payload;
        };

        /**
         * Convenience method that handles the Question Service save response by returning a fully
         * transformed Question payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Question Service query response.
         * @param {EScreeningDashboardApp.models.QuestionsTransformer} jsonResponsePayloadTransformer Represents the Questions JSON Response Payload transformer.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed Question payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleQuestionSaveResponse = function (jsonResponse, jsonResponsePayloadTransformer, userId) {
            /**
             * Represents the response of a service call request.
             *
             * @private
             * @field
             * @type {EScreeningDashboardApp.models.Response}
             */
            var response = BytePushers.models.ResponseTransformer.transformJSONResponse(jsonResponse, jsonResponsePayloadTransformer, userId),
                /**
                 * Represents the transformed payload object of a service call request.
                 *
                 * @private
                 * @field
                 * @type {Object}
                 */
                payload = null;

            if (response !== null) {
                if (response.isSuccessful()) {
                    payload = response.getPayload();
                } else {
                    throw new Error(response.getMessage());
                }
            } else {
                throw new Error("handleQuestionServiceQueryResponse() method: Response is null.");
            }

            return payload;
        };

        /**
         * Convenience method that handles the Question Service save response by returning a fully
         * transformed Question payload object.
         *
         * @private
         * @method
         * @param {String} jsonResponse Represents the Restful Question Service query response.
         * @param {String} userId Represents the User who made the service query request.
         * @returns {*} A transformed Question payload object.
         * @throws {Error} If the jsonResponse can not be property transformed.
         */
        var handleQuestionRemoveResponse = function (jsonResponse, userId) {
            /**
             * Represents the response of a service call request.
             *
             * @private
             * @field
             * @type {EScreeningDashboardApp.models.Response}
             */

            if (!jsonResponse.status.status == 'succeeded'){
                var response = BytePushers.models.ResponseTransformer.transformJSONResponse(jsonResponse, null, userId),
                    /**
                     * Represents the transformed payload object of a service call request.
                     *
                     * @private
                     * @field
                     * @type {Object}
                     */
                    payload = null;

                if (response !== null) {
                    if (response.isSuccessful()) {
                        payload = response.getPayload();
                    } else {
                        throw new Error(response.getMessage());
                    }
                } else {
                    throw new Error("handleQuestionServiceQueryResponse() method: Response is null.");
                }
            }

            return response;
        };

        // Expose the public QuestionService API to the rest of the application.
        return {
            query: query,
            queryBySurveyId: queryBySurveyId,
            update: update,
            remove: remove,
            create: create,
            setCreateQuestionRequestParameter: setCreateQuestionRequestParameter,
            setQueryQuestionSearchCriteria: setQueryQuestionSearchCriteria,
            setQueryBySurveyIdSearchCriteria: setQueryBySurveyIdSearchCriteria,
            setUpdateQuestionRequestParameter: setUpdateQuestionRequestParameter,
            setRemoveQuestionRequestParameter: setRemoveQuestionRequestParameter
        };
    }]);