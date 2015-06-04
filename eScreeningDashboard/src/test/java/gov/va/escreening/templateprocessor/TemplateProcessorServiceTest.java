package gov.va.escreening.templateprocessor;

import gov.va.escreening.dto.template.GraphParamsDto;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

@RunWith(JUnit4.class)
public class TemplateProcessorServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(TemplateProcessorServiceTest.class);

    @Test
    public void historicalGraphGenerationTest(){
        GraphParamsDto graphParams = new GraphParamsDto();
        graphParams.setTitle("Depression Score");
        graphParams.setFooter("*a score of 10 or greater is a positive screen");
        graphParams.setMaxXPoint(27d);
        graphParams.setTicks(ImmutableList.of(0d, 1d, 5d, 10d, 15d, 20d, 27d));
        
        Map<String, Double> intervals = Maps.newLinkedHashMap();
        intervals.put("None", 0d);
        intervals.put("Minimal", 1d);
        intervals.put("Mild", 5d); 
        intervals.put("Moderate", 10d); 
        intervals.put("Moderately Severe", 15d);
        intervals.put("Severe", 20d);
        graphParams.setIntervals(intervals);
        
        Map<String, Double> historicalValues = Maps.newLinkedHashMap();
        historicalValues.put("01/03/2013 12:51:17", 5d);
        historicalValues.put("04/20/2013 12:51:17", 10d);
        historicalValues.put("06/21/2013 12:51:17", 15d);
        historicalValues.put("08/22/2013 12:51:17", 20d);
        historicalValues.put("10/20/2013 12:51:17", 22.6d);
        historicalValues.put("12/20/2013 12:51:17", 20.9d);
        historicalValues.put("02/23/2014 12:51:17", 18.3d);
        historicalValues.put("03/22/2014 12:51:17", 10.5d);
        historicalValues.put("05/22/2014 12:51:17", 15.16d);
        historicalValues.put("07/22/2014 12:51:17", 5d);
        historicalValues.put("09/22/2014 12:51:17", 27d);
        historicalValues.put("11/22/2014 12:51:17", 16d);
        historicalValues.put("01/22/2015 12:51:17", 5d);
        historicalValues.put("03/22/2015 12:51:17", 27d);
        historicalValues.put("05/22/2015 12:51:17", 16.09d);
        
        String graph = new TemplateProcessorServiceImpl().generateHistoricalGraph("PHQ-9", graphParams, historicalValues);
        
        logger.debug(graph);
        System.out.println(graph);
    }
}
