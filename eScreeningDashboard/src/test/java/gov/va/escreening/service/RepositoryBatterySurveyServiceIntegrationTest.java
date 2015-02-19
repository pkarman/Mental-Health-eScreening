package gov.va.escreening.service;

import gov.va.escreening.dto.SearchDTO;
import gov.va.escreening.dto.SearchType;
import gov.va.escreening.dto.editors.BatteryInfo;
import gov.va.escreening.entity.BatterySurvey;
import gov.va.escreening.transformer.EditorsBatteryViewTransformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by pouncilt on 8/1/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/root-context.xml"
})
public class RepositoryBatterySurveyServiceIntegrationTest {
    @Autowired
    private BatterySurveyService batterySurveyService;

    private static final Integer BATTERY_ID = 3;

    @Transactional
    @Test
    public void searchWhenSearchTypeIsQueryAnnotation() {
        SearchDTO<Integer> searchCriteria = createSearchDTO(BATTERY_ID, SearchType.QUERY_ANNOTATION);
        List<BatterySurvey> actualBatterySurveys = batterySurveyService.search(searchCriteria);
        assertNotNull(actualBatterySurveys);
        assertTrue(actualBatterySurveys.size() > 0);
        BatteryInfo actualBatteryInfo = EditorsBatteryViewTransformer.transformBatterySurveys(actualBatterySurveys);
        assertNotNull(actualBatteryInfo);
        assertTrue(actualBatteryInfo.getSurveys().size() > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void searchWhenSearchTypeIsNull() {
        SearchDTO<Integer> searchCriteria = createSearchDTO(BATTERY_ID, null);

        batterySurveyService.search(searchCriteria);
    }

    private SearchDTO<Integer> createSearchDTO(Integer searchTerm, SearchType searchType) {
        SearchDTO<Integer> searchCriteria = new SearchDTO<Integer>();
        searchCriteria.setSearchTerm(searchTerm);
        searchCriteria.setSearchType(searchType);
        return searchCriteria;
    }
}
