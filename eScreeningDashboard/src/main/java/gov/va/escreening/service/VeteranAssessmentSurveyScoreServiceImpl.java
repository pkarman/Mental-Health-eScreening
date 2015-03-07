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
        final Collection<AssessmentVariable> allFormulas = avSrv.findAllFormulas();

        for (Survey s : veteranAssessment.getSurveys()) {
            final Collection<AssessmentVariable> reportableFormulas = extractReportableFormulasOnly(s, allFormulas);
            if (reportableFormulas != null) {
                final Iterable<AssessmentVariableDto> reportableAvDtos = vrSrv.resolveVariablesFor(veteranAssessment.getVeteranAssessmentId(), reportableFormulas);
                for (AssessmentVariableDto avDto : reportableAvDtos) {
                    vassRepos.update(createVASS(s, avDto, veteranAssessment));
                }
            }
        }

    }

    private VeteranAssessmentSurveyScore createVASS(Survey s, AssessmentVariableDto avDto, VeteranAssessment veteranAssessment) {
        VeteranAssessmentSurveyScore vass = new VeteranAssessmentSurveyScore();
        vass.setClinic(veteranAssessment.getClinic());
        vass.setDateCompleted(LocalDate.now().toDate());

        //get the string as an integer, round it to get best mathematical number
        int roundedScore = (int) Math.round(Double.parseDouble(avDto.getDisplayText()));
        vass.setScore(roundedScore);

        vass.setSurvey(s);
        vass.setVeteran(veteranAssessment.getVeteran());
        vass.setVeteranAssessment(veteranAssessment);
        return vass;
    }

    private Collection<AssessmentVariable> extractReportableFormulasOnly(Survey s, Collection<AssessmentVariable> allFormulas) {
        String reportableFormalasNames = reportableFormulasMap.get(s.getName());
        if (reportableFormalasNames == null) {
            return null;
        }
        final List<String> reportableFormulasAsList = Lists.newArrayList(Splitter.on(',').omitEmptyStrings().trimResults().split(reportableFormalasNames));
        Predicate<AssessmentVariable> predicate = new Predicate<AssessmentVariable>() {
            @Override
            public boolean apply(AssessmentVariable input) {
                return reportableFormulasAsList.contains(input.getDisplayName());
            }
        };
        Collection<AssessmentVariable> result = Collections2.filter(allFormulas, predicate);
        return result;
    }
}
