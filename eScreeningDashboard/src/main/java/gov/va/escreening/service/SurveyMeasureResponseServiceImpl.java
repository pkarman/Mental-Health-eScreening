package gov.va.escreening.service;

import com.google.common.collect.Maps;
import gov.va.escreening.domain.MeasureTypeEnum;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.SurveyPage;
import gov.va.escreening.repository.SurveyMeasureResponseRepository;
import gov.va.escreening.repository.VeteranAssessmentMeasureVisibilityRepository;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ListMultimap;

@Transactional
@Service
public class SurveyMeasureResponseServiceImpl implements SurveyMeasureResponseService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SurveyMeasureResponseRepository surveyMeasureResponseRepository;

    @Autowired
    private VeteranAssessmentMeasureVisibilityRepository visibilityRepo;

    @Transactional(readOnly = true)
    @Override
    public Hashtable<Integer, List<SurveyMeasureResponse>> findForVeteranAssessmentId(int veteranAssessmentId, Map<Integer, Integer> measureQuestionMap) {

        List<SurveyMeasureResponse> resultList = surveyMeasureResponseRepository
                .findForVeteranAssessmentId(veteranAssessmentId);

        Hashtable<Integer, List<SurveyMeasureResponse>> surveyMeasureResponseMap = new Hashtable<Integer, List<SurveyMeasureResponse>>();

        for (SurveyMeasureResponse surveyMeasureResponse : resultList) {

            if (surveyMeasureResponseMap.containsKey(surveyMeasureResponse.getMeasureAnswer().getMeasureAnswerId())) {
                List<SurveyMeasureResponse> responses = surveyMeasureResponseMap.remove(surveyMeasureResponse
                        .getMeasureAnswer().getMeasureAnswerId());
                responses.add(surveyMeasureResponse);
                surveyMeasureResponseMap.put(surveyMeasureResponse.getMeasureAnswer().getMeasureAnswerId(), responses);
            } else {
                List<SurveyMeasureResponse> responses = new ArrayList<SurveyMeasureResponse>();
                responses.add(surveyMeasureResponse);
                surveyMeasureResponseMap.put(surveyMeasureResponse.getMeasureAnswer().getMeasureAnswerId(), responses);
            }
        }
        fillTableQuestionMap(surveyMeasureResponseMap, measureQuestionMap);

        return surveyMeasureResponseMap;
    }

    private void fillTableQuestionMap(Hashtable<Integer, List<SurveyMeasureResponse>> surveyMeasureResponseMap, Map<Integer, Integer> m) {
        Map<Measure, Integer> fm = Maps.newHashMap();
        for (List<SurveyMeasureResponse> smrLst : surveyMeasureResponseMap.values()) {
            for (SurveyMeasureResponse smr : smrLst) {
                if (smr.getTabularRow() != null) {
                    Measure measure = smr.getMeasure();
                    Integer measureQuestionCnt = fm.get(measure) == null ? 1 : fm.get(measure) + 1;
                    fm.put(measure, measureQuestionCnt);
                }
            }
        }

        // now transfer the data from temp map of averages (as float) to m
        for (Measure key : fm.keySet()) {
            int maLstSz = key.getMeasureAnswerList().size();
            m.put(key.getMeasureId(), fm.get(key) / (maLstSz == 0 ? 1 : maLstSz));
        }
    }

    @Override
    @Transactional
    public String generateQuestionsAndAnswers(Survey survey, Integer veteranAssessmentId) {

        StringBuilder sb = new StringBuilder();
        sb.append(survey.getName() + "\n");

        ListMultimap<Integer, SurveyMeasureResponse> resp = surveyMeasureResponseRepository
                .getForVeteranAssessmentAndSurvey(
                        veteranAssessmentId, survey.getSurveyId());

        for (SurveyPage page : survey.getSurveyPageList()) {
            Map<Integer, Boolean> visibilityMap = visibilityRepo.getVisibilityMapForSurveyPage(veteranAssessmentId, page.getSurveyPageId());

            int index = 1;
            for (gov.va.escreening.entity.Measure m : page.getMeasures()) {

                if (m == null) continue;

                if (visibilityMap.containsKey(m.getMeasureId()) && !visibilityMap.get(m.getMeasureId())) {
                    continue;
                }

                String indent = "";
                if (visibilityMap.containsKey(m.getMeasureId())) {
                    indent = "  ";
                }
                appendMeasure(indent, sb, resp, m, "");
                if (m.getChildren() != null) {
                    //int childIndex = 1;
                    for (Measure measure : m.getChildren()) {
                        if (visibilityMap.containsKey(m.getMeasureId()) && !visibilityMap.get(m.getMeasureId())) {
                            continue;
                        }
                        appendMeasure("  ", sb, resp, measure, "  ");
                    }
                }
            }
        }

        return wrapLines(sb.toString()) + "\n";
    }

    private void appendMeasure(String indexStr, StringBuilder sb, ListMultimap<Integer, SurveyMeasureResponse> resp,
                               gov.va.escreening.entity.Measure m, String indentDelta) {
        String indent = "  " + indentDelta;
        String ques = m.getVistaText() == null ? m.getMeasureText() : m.getVistaText();
        ques = ques.trim().isEmpty() ? indent : indent + indexStr + ques;

        String answer = null;
        if (m.getMeasureType().getMeasureTypeId() == MeasureTypeEnum.FREETEXT.getMeasureTypeId()) {
            MeasureAnswer ma = m.getMeasureAnswerList().get(0);
            if (resp.containsKey(ma.getMeasureAnswerId())) {
                SurveyMeasureResponse mar = resp.get(ma.getMeasureAnswerId()).get(0);
                answer = mar.getTextValue();
                if (answer == null) {
                    Long l = mar.getNumberValue();
                    if (l != null)
                        answer = l.toString();
                }

            }
        } else if (m.getMeasureType().getMeasureTypeId() == MeasureTypeEnum.SELECTONE.getMeasureTypeId() ||
                m.getMeasureType().getMeasureTypeId() == MeasureTypeEnum.SELECTONEMATRIX.getMeasureTypeId()) {
            for (MeasureAnswer ma : m.getMeasureAnswerList()) {
                if (resp.containsKey(ma.getMeasureAnswerId())) {
                    for (SurveyMeasureResponse smr : resp.get(ma.getMeasureAnswerId())) {
                        if (smr.getBooleanValue() != null && smr.getBooleanValue()) {
                            answer = ma.getVistaText() == null ? ma.getAnswerText() : ma.getVistaText();
                            break;
                        }
                    }
                }
            }
        } else if (m.getMeasureType().getMeasureTypeId() == MeasureTypeEnum.SELECTMULTI.getMeasureTypeId() ||
                m.getMeasureType().getMeasureTypeId() == MeasureTypeEnum.SELECTMULTIMATRIX.getMeasureTypeId()) {
            StringBuilder answerStr = new StringBuilder();
            for (MeasureAnswer ma : m.getMeasureAnswerList()) {
                if (resp.containsKey(ma.getMeasureAnswerId())) {
                    SurveyMeasureResponse smr = resp.get(ma.getMeasureAnswerId()).get(0);
                    if (smr.getBooleanValue() != null && smr.getBooleanValue()) {
                        answerStr.append("\n    " + indent).append(ma.getVistaText() == null ? ma.getAnswerText() : ma.getVistaText());
                    }
                }
            }
            answer = answerStr.toString();
        }

        sb.append(ques);
        if (answer == null) {
            answer = "No Answer";
        }
        sb.append(" ").append(answer);
        sb.append("\n\n");
    }

    /**
     * Wraps given text to 80 columns including a 4 space indent on everything.
     *
     * @param text
     * @return
     */
    private String wrapLines(String text) {
        String newLine = "\n    ";
        Pattern prefixSpace = Pattern.compile("^(\\s+).*");
        Pattern newLineReplace = Pattern.compile("\n");

        StringBuilder wrappedText = new StringBuilder();
        String[] lines = text.split("\n");
        for (String line : lines) {
            logger.debug("wrapping line:\n{}", line);
            Matcher m = prefixSpace.matcher(line);
            String indent = m.find() ? m.group(1) : "";

            String wrappedLine = WordUtils.wrap(line, 70, "\n", true);

            //wrap method removes space if it wraps but doen't if no wrap was needed.
            if (!indent.isEmpty())
                wrappedLine = wrappedLine.replaceFirst("^\\s+", "");

            String margin = newLine + indent;
            logger.debug("margin size: {}", margin.length());

            logger.debug("wrapped by itself:\n{}", wrappedLine);

            //add margin to wrapped lines
            wrappedLine = newLineReplace.matcher(wrappedLine).replaceAll(margin);

            logger.debug("wrapped with replaced for each line margin: \n{}", wrappedLine);

            wrappedText.append(margin).append(wrappedLine);
        }

        String wrapped = wrappedText.toString();
        //logger.debug("wrapped text:\n{}", wrapped);
        return wrapped;
    }
}