package gov.va.escreening.templateprocessor;

import gov.va.escreening.dto.template.GraphParamsDto;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

@RunWith(JUnit4.class)
public class VeteranProgressHistoryAsciiGraphTest {
    private static final Logger logger = LoggerFactory.getLogger(VeteranProgressHistoryAsciiGraphTest.class);

    @Test
    public void historicalGraphGeneration15DataPointsTest(){
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
        historicalValues.put("09/22/2014 12:51:17", 20d);
        historicalValues.put("11/22/2014 12:51:17", 16d);
        historicalValues.put("01/22/2015 12:51:17", 5d);
        historicalValues.put("03/22/2015 12:51:17", 20d);
        historicalValues.put("05/22/2015 12:51:17", 16.09d);
        
        String graph = VeteranProgressHistoryAsciiGraph.generateHistoricalGraph("PHQ-9", graphParams, historicalValues);
        
        logger.debug(graph);
        //System.out.println(graph);
    }
    

    @Test
    public void historicalGraphGeneration7DataPointsTest(){
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
        historicalValues.put("03/22/2015 12:51:17", 20d);
        historicalValues.put("05/22/2015 12:51:17", 16.09d);
        
        String graph = VeteranProgressHistoryAsciiGraph.generateHistoricalGraph("PHQ-9", graphParams, historicalValues);
        
        logger.debug(graph);
        //System.out.println(graph);
    }
    
    
    @Test
    public void historicalGraphGeneration1DataPointsTest(){
        GraphParamsDto graphParams = new GraphParamsDto();
        graphParams.setTitle("Very long tile please make me fit");
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
        
        String graph = VeteranProgressHistoryAsciiGraph.generateHistoricalGraph("PHQ-9", graphParams, historicalValues);
        
        logger.debug(graph);
        //System.out.println(graph);
    }
    
    @Test
    public void historicalGraphGenerationIntervalOnAxis(){
        GraphParamsDto graphParams = new GraphParamsDto();
        graphParams.setTitle("Depression Score");
        graphParams.setFooter("*a score of 10 or greater is a positive screen");
        graphParams.setMaxXPoint(27d);
        graphParams.setTicks(ImmutableList.of(0d, 3.5d, 3.75d, 4d, 13d, 17d, 22d, 27d));
        
        Map<String, Double> intervals = Maps.newLinkedHashMap();
        intervals.put("None", 0d);
        intervals.put("Minimal", 3.5d);
        intervals.put("Cool", 3.75d);
        intervals.put("Mild", 4d); 
        intervals.put("Moderate", 13d); 
        intervals.put("Moderately Severe", 17d);
        intervals.put("Severe", 22d);
        graphParams.setIntervals(intervals);
        
        Map<String, Double> historicalValues = Maps.newLinkedHashMap();
        historicalValues.put("01/03/2013 12:51:17", 5d);
        historicalValues.put("04/20/2013 12:51:17", 10d);
        historicalValues.put("06/21/2013 12:51:17", 15d);
        historicalValues.put("08/22/2013 12:51:17", 20d);
        historicalValues.put("10/20/2013 12:51:17", 22.6d);
        historicalValues.put("03/22/2015 12:51:17", 20d);
        historicalValues.put("05/22/2015 12:51:17", 16.09d);
        
        String graph = VeteranProgressHistoryAsciiGraph.generateHistoricalGraph("PHQ-9", graphParams, historicalValues);
        
        logger.debug(graph);
        //System.out.println(graph);
        
        assertTrue(graph.contains(" 0|"));
        assertFalse(graph.contains("1|"));
        assertFalse(graph.contains(" 2|"));
        assertFalse(graph.contains(" 3|"));
        assertTrue(graph.contains(" 4|"));
        assertTrue(graph.contains("13|"));
        assertTrue(graph.contains("17|"));
        assertTrue(graph.contains("22|"));
        assertTrue(graph.contains("27|"));
    }
}
