package gov.va.escreening.repository;

import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.entity.VeteranAssessmentSurveyScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kliu on 3/3/15.
 */
@Repository
public class VeteranAssessmentSurveyScoreRepositoryImpl extends AbstractHibernateRepository<VeteranAssessmentSurveyScore>
        implements VeteranAssessmentSurveyScoreRepository {

    @Autowired
    private VeteranRepository veteranRepository;

    public VeteranAssessmentSurveyScoreRepositoryImpl() {
        super();

        setClazz(VeteranAssessmentSurveyScore.class);
    }

    public List<VeteranAssessmentSurveyScore> getDataForIndividual(String lastName, String ssnLastFour, List<Survey> surveys, String fromDate, String toDate) {

        List<VeteranAssessmentSurveyScore> scores = new ArrayList<>();

        List<Veteran> veterans = veteranRepository.searchVeterans(lastName, ssnLastFour);

        if (veterans != null && veterans.size() > 0) {


            String query = "select vassr from VeteranAssessmentSurveyScore vassr  " +

                    " where vassr.dateCompleted >= :fromDate " +
                    " and vassr.dateCompeted <= :toDate " +
                    " and vassr.survey.id in ( :surveyIds ) " +
                    " and vassr.veteran.id in ( :veteranIds )" +
                    " order by vassr.survey.id, vassr.dateCompleted desc ";

        }
        return scores;
    }

    public List<VeteranAssessmentSurveyScore> getDataForClicnic(List<Integer> clinicIds, List<Integer> surveyIds,
                                                                String fromDate, String toDate) {

        List<VeteranAssessmentSurveyScore> scores = new ArrayList<>();

        String query = "select vassr from VeteranAssessmentSurveyScore vassr  " +

                " where vassr.dateCompleted >= :fromDate " +
                " and vassr.dateCompeted <= :toDate " +
                " and vassr.survey.id in ( :surveyIds ) " +
                " order by vassr.veteran.id, vassr.survey.id, vassr.dateCompleted asc ";

        return scores;


    }

}
