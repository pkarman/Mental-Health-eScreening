package gov.va.escreening.service;

import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.entity.VeteranAssessmentSurveyScore;
import gov.va.escreening.repository.VeteranAssessmentSurveyScoreRepository;
import gov.va.escreening.variableresolver.AssessmentVariableDto;
import gov.va.escreening.variableresolver.VariableResolverService;
import gov.va.escreening.variableresolver.VariableResolverServiceImpl;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by munnoo on 3/6/15.
 */
@Service("veteranAssessmentSurveyScoreService")
public class VeteranAssessmentSurveyScoreServiceImpl implements VeteranAssessmentSurveyScoreService {
    @Resource(type = VariableResolverService.class)
    VariableResolverService vrSrv;

    @Resource(type = AssessmentVariableService.class)
    AssessmentVariableService avSrv;

    @Resource(type = VeteranAssessmentSurveyScoreRepository.class)
    VeteranAssessmentSurveyScoreRepository vassRepos;

    // map between surveyName and formulas that belong to that survey
    // this is defined in WEB-INF/spring/business-config.xml:107
    @Resource(name = "reportableFormulasMap")
    Map<String, String> reportableFormulasMap;

    @Override
    @Transactional
    public void recordAllReportableScores(VeteranAssessment veteranAssessment) {
        for (Survey s : veteranAssessment.getSurveys()) {
            // find reportable Assessment Variables for each Survey in this Veteran Assessment. Most of these Assessment Variables will be Formulas,
            // and also most of the Formulas would be Aggregate Formulas
            final Collection<AssessmentVariable> reportableAvs = getReportableAvsForSurvey(s);

            // in case a survey does not have any Assessment Variable as reportable
            if (reportableAvs != null) {
                // use assessment variables and veteran Assessment Id
                final Iterable<AssessmentVariableDto> reportableAvDtos = vrSrv.resolveVariablesFor(veteranAssessment.getVeteranAssessmentId(), reportableAvs);
                for (AssessmentVariableDto avDto : reportableAvDtos) {
                    VeteranAssessmentSurveyScore vass = tryCreateVASS(s, avDto, veteranAssessment);
                    if (vass != null) {
                        vassRepos.update(vass);
                    }
                }
            }
        }
    }

    private Collection<AssessmentVariable> getReportableAvsForSurvey(Survey s) {
        List<String> avDisplayNames = getDisplayNamesForSurvey(s);
        if (avDisplayNames == null) {
            return null;
        }
        final Collection<AssessmentVariable> byDisplayNames = avSrv.findByDisplayNames(avDisplayNames);
        return byDisplayNames;
    }

    private List<String> getDisplayNamesForSurvey(Survey s) {
        String avDisplayNames = reportableFormulasMap.get(s.getName());
        if (avDisplayNames == null) {
            return null;
        }
        final List<String> avDisplayNamesAsList = Lists.newArrayList(Splitter.on(',').omitEmptyStrings().trimResults().split(avDisplayNames));
        return avDisplayNamesAsList;
    }

    private VeteranAssessmentSurveyScore tryCreateVASS(Survey s, AssessmentVariableDto avDto, VeteranAssessment veteranAssessment) {
        String scoreAsStr = avDto.getDisplayText();
        if (scoreAsStr != null && !scoreAsStr.trim().isEmpty()) {
            VeteranAssessmentSurveyScore vass = new VeteranAssessmentSurveyScore();
            vass.setClinic(veteranAssessment.getClinic());
            vass.setDateCompleted(LocalDate.now().toDate());

            //get the string as an integer, round it to get best mathematical number
            int roundedScore = (int) Math.round(Double.parseDouble(scoreAsStr));
            vass.setScore(roundedScore);

            vass.setSurvey(s);
            vass.setVeteran(veteranAssessment.getVeteran());
            vass.setVeteranAssessment(veteranAssessment);
            return vass;
        }
        return null;
    }
}
