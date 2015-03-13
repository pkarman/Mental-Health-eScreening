package gov.va.escreening.reports;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gov.va.escreening.controller.dashboard.ReportsController;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.expressionevaluator.ExpressionEvaluatorService;
import gov.va.escreening.formula.AvMapTypeEnum;
import gov.va.escreening.formula.FormulaHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class ReportsTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource(type = ReportsController.class)
    ReportsController rc;

  //  @Test
    public void createTstChartableData() {
       // Map<String, Object> chartableData=rc.createTstChartableData(null);
      //  Gson gson = new GsonBuilder().create();
      //  String jsonDataSet = gson.toJson(chartableData.get("dataSet"));
      //  String jsonDataFormat = gson.toJson(chartableData.get("dataFormat"));
    }
}