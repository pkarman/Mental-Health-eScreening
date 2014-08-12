package gov.va.escreening.transformer;

import gov.va.escreening.dto.ae.Answer;
import gov.va.escreening.dto.ae.Measure;
import gov.va.escreening.dto.ae.Validation;
import gov.va.escreening.dto.editors.AnswerInfo;
import gov.va.escreening.dto.editors.QuestionInfo;
import gov.va.escreening.dto.editors.ValidationInfo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pouncilt on 8/6/14.
 */
public class EditorsQuestionViewTransformer {
    public static List<QuestionInfo> transformQuestions(List<Measure> measures) {
        List<QuestionInfo> questionInfoList = new ArrayList<QuestionInfo>();

        if(measures != null) {
            for (Measure measure : measures) {
                questionInfoList.add(transformQuestion(measure));
            }
        }

        return questionInfoList;
    }

    private static QuestionInfo transformQuestion(Measure measure) {
        QuestionInfo questionInfo = new QuestionInfo();

        if(measure != null) {
            BeanUtils.copyProperties(measure, questionInfo);
            questionInfo.setAnswers(transformAnswers(measure.getAnswers()));
            questionInfo.setChildQuestions(transformChildQuestions(measure.getChildMeasures()));
            questionInfo.setTableAnswers(transformTableAnswers(measure.getTableAnswers()));
            questionInfo.setValidations(transformValidations(measure.getValidations()));
        }

        return questionInfo;
    }

    private static List<ValidationInfo> transformValidations(List<Validation> validations) {
        List<ValidationInfo> validationInfoLists = new ArrayList<ValidationInfo>();

        if(validations != null) {
            for(Validation validation: validations){
                validationInfoLists.add(transformValidation(validation));
            }
        }

        return validationInfoLists;
    }

    private static ValidationInfo transformValidation(Validation validation) {
        ValidationInfo validationInfo = new ValidationInfo();

        if(validation != null) {
            BeanUtils.copyProperties(validation, validationInfo);
        }

        return validationInfo;
    }

    private static List<List<AnswerInfo>> transformTableAnswers(List<List<Answer>> tableAnswers) {
        List<List<AnswerInfo>> answerInfoList = new ArrayList<List<AnswerInfo>>();

        if(tableAnswers != null) {
            for(List<Answer> answers: tableAnswers){
                answerInfoList.add(transformAnswers(answers));
            }
        }

        return answerInfoList;
    }

    private static List<QuestionInfo> transformChildQuestions(List<Measure> childMeasures) {
        return EditorsQuestionViewTransformer.transformQuestions(childMeasures);
    }

    private static List<AnswerInfo> transformAnswers(List<Answer> answers) {
        List<AnswerInfo> answerInfoList = new ArrayList<AnswerInfo>();

        if(answers != null) {
            for(Answer answer: answers){
                answerInfoList.add(transformAnswer(answer));
            }
        }

        return answerInfoList;
    }

    private static AnswerInfo transformAnswer(Answer answer) {
        AnswerInfo answerInfo = new AnswerInfo();

        if(answer != null) {
            BeanUtils.copyProperties(answer, answerInfo);
        }

        return answerInfo;
    }

    public static Measure transformQuestionInfo(QuestionInfo question) {
        Measure measure = new Measure();

        if(question != null) {
            BeanUtils.copyProperties(question, measure);
            measure.setAnswers(transformAnswerInfoList(question.getAnswers()));
            measure.setChildMeasures(transformChildQuestionInfoList(question.getChildQuestions()));
            measure.setTableAnswers(transformTableAnswerInfoList(question.getTableAnswers()));
            measure.setValidations(transformValidationInfoList(question.getValidations()));
        }

        return measure;
    }

    private static List<List<Answer>> transformTableAnswerInfoList(List<List<AnswerInfo>> tableAnswerInfoList) {
        List<List<Answer>> tableAnswers = new ArrayList<List<Answer>>();

        if(tableAnswerInfoList != null) {
            for(List<AnswerInfo> answerInfoList: tableAnswerInfoList) {
                tableAnswers.add(transformAnswerInfoList(answerInfoList));
            }
        }

        return tableAnswers;
    }

    private static List<Measure> transformChildQuestionInfoList(List<QuestionInfo> childQuestionInfoList) {
        List<Measure> childMeasures = new ArrayList<Measure>();

        if(childQuestionInfoList != null) {
            for(QuestionInfo questionInfo: childQuestionInfoList) {
                childMeasures.add(transformQuestionInfo(questionInfo));
            }
        }

        return childMeasures;
    }

    private static List<Answer> transformAnswerInfoList(List<AnswerInfo> answerInfoList) {
        List<Answer> answers = new ArrayList<Answer>();

        if(answerInfoList != null) {
            for(AnswerInfo answerInfo: answerInfoList){
                answers.add(transformAnswerInfo(answerInfo));
            }
        }

        return answers;
    }

    private static Answer transformAnswerInfo(AnswerInfo answerInfo) {
        Answer answer = new Answer();

        if(answerInfo != null) {
            BeanUtils.copyProperties(answerInfo, answer);
        }

        return answer;
    }

    private static List<Validation> transformValidationInfoList(List<ValidationInfo> validationInfoList) {
        List<Validation> validations= new ArrayList<Validation>();

        if(validationInfoList != null) {
            for(ValidationInfo validationInfo: validationInfoList){
                validations.add(transformValidationInfo(validationInfo));
            }
        }

        return validations;
    }

    private static Validation transformValidationInfo(ValidationInfo validationInfo) {
        Validation validation = new Validation ();

        if(validationInfo != null) {
            BeanUtils.copyProperties(validationInfo, validation);
        }

        return validation;
    }
}
