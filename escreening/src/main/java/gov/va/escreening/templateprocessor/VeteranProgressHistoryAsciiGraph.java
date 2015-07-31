package gov.va.escreening.templateprocessor;

import gov.va.escreening.dto.template.GraphParamsDto;

import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;

public class VeteranProgressHistoryAsciiGraph {

    private static final int HISTORICAL_GRAPH_WIDTH = 80;
    private static final int HISTORICAL_GRAPH_COL_WIDTH = 4;
    private static final String HISTORICAL_GRAPH_EMPTY_COL = StringUtils.repeat(StringUtils.SPACE, HISTORICAL_GRAPH_COL_WIDTH);
    private static final String HISTORICAL_GRAPH_BAR_COL = StringUtils.center("#", HISTORICAL_GRAPH_COL_WIDTH);
    private static final DecimalFormat HISTORICAL_GRAPH_FORMAT_2DIGIT_NUM = new DecimalFormat("#.#");
    private static final DecimalFormat HISTORICAL_GRAPH_FORMAT_3DIGIT_NUM = new DecimalFormat("#");
    private static final String HISTORICAL_GRAPH_HORZ_LINE = "+===========+";
    private static final String HISTORICAL_GRAPH_EMPTY_X_HEADER = "|           ";
    private static final String HISTORICAL_GRAPH_DATE_X_HEADER = "|  DATE     ";
    private static final int HISTORICAL_GRAPH_X_HEADER_WIDTH = 11;
    
    static String generateHistoricalGraph(String name, GraphParamsDto graphParams, Map<String, Double> historicalValuesMap){
        StringBuilder graph = new StringBuilder();
        int graphTitleWidth = Strings.nullToEmpty(graphParams.getTitle()).length();
        int historyCount = historicalValuesMap.size();
        int graphWidth = Math.max(HISTORICAL_GRAPH_X_HEADER_WIDTH + (HISTORICAL_GRAPH_COL_WIDTH * historyCount)-1, graphTitleWidth+1) + 1;
        
        List<String> historyDates = new ArrayList<>(historyCount);
        double[] historyValues = new double[historyCount];
        double maxHistory = Double.MIN_VALUE;
        int i = 0;
        for(Entry<String, Double> historyEntry : historicalValuesMap.entrySet()){
            double value = historyEntry.getValue();
            
            if(i == 0){
                maxHistory = value;
            }
            
            historyDates.add(historyEntry.getKey());
            historyValues[i++] = value;
            if(value > maxHistory){
                maxHistory = value;
            }
        }
        
        //iterate over intervals and recored needed values
        double minIntervalValue = Double.MAX_VALUE;
        double maxIntervalValue = graphParams.getMaxXPoint() != null ? graphParams.getMaxXPoint() : Double.MIN_VALUE;
        Deque<Double> axisValues = new ArrayDeque<>(graphParams.getIntervals().size());
        int maxLabelWidth = 0;
        for(Entry<String,Double> intervalEntity : graphParams.getIntervals().entrySet()){
            String intervalName = intervalEntity.getKey();
            Double intervalValue = intervalEntity.getValue();
            axisValues.add(intervalValue);
            if(intervalName.length() > maxLabelWidth){
                maxLabelWidth = intervalName.length();
            }
            if(intervalValue < minIntervalValue){
                minIntervalValue = intervalValue;
            }
            if(intervalValue > maxIntervalValue){
                maxIntervalValue = intervalValue;
            }
        }
        
        if(graphParams.getMaxXPoint() != null && (axisValues.isEmpty() || !graphParams.getMaxXPoint().equals(axisValues.getLast())))
        	axisValues.addLast(graphParams.getMaxXPoint());
        	
        //add title
        graph.append(StringUtils.center(name, graphWidth))
            .append("\n\n");
        
        //add bar graph
        int largestYRow = (int)Math.ceil(maxIntervalValue)+1;
        int halfway = (largestYRow - (int)minIntervalValue)/2;
        for(int currentValue = largestYRow; currentValue >= minIntervalValue; currentValue--){
            appendGraphMargin(graph, axisValues, currentValue, halfway, largestYRow);
            
            for(i = 0; i < historyValues.length; i++){
                appendHistoryValue(graph, currentValue, historyValues[i]);
            }
            graph.append("\n");
        }
        
        appendGraphHorizontalLine(graph, historyCount, graphWidth);
        
        //add X axis
        int differenceFromGraphWidth = graphWidth - (HISTORICAL_GRAPH_X_HEADER_WIDTH + (HISTORICAL_GRAPH_COL_WIDTH * historyCount));
        for(i = 0; i < 10; i++){
            
            if(i == 5){
                graph.append(HISTORICAL_GRAPH_DATE_X_HEADER);
            }
            else{
                graph.append(HISTORICAL_GRAPH_EMPTY_X_HEADER);
            }
            
            for(int historyIndex = 0; historyIndex < historyCount; historyIndex++){
                graph.append("| ")
                    .append(historyDates.get(historyIndex).charAt(i))
                    .append(StringUtils.SPACE);     
            }
            graph.append("|");
            
            if(differenceFromGraphWidth > 0){
                graph.append(StringUtils.repeat(" ", differenceFromGraphWidth -1))
                    .append("|");
            }
            graph.append("\n");
        }
        
        appendGraphHorizontalLine(graph, historyCount, graphWidth);
        
        graph.append("|")
            .append(StringUtils.center(
                Strings.nullToEmpty(graphParams.getTitle()), graphWidth))
            .append("|\n");
        
        appendGraphHorizontalLine(graph, historyCount, graphWidth);
        
        if(!Strings.nullToEmpty(graphParams.getFooter()).trim().isEmpty()){
            graph.append(StringUtils.center(
                    Strings.nullToEmpty(graphParams.getFooter()), graphWidth));
        }
        
        appendLegend(graph, graphParams.getIntervals(), graphParams.getMaxXPoint(), maxLabelWidth);
        
        return graph.toString();
    }
    
    static private void appendLegend(StringBuilder graph, Map<String, Double> intervals, Double maxValue, int maxLabelWidth){
        
        graph.append("\n\n");    
        Entry<String, Double> prev = null;
        Iterator<Entry<String, Double>> intervalIter = intervals.entrySet().iterator();
        while(intervalIter.hasNext()){
            Entry<String, Double> interval = intervalIter.next();
            String currentValue = HISTORICAL_GRAPH_FORMAT_2DIGIT_NUM.format(interval.getValue());
            
            if(prev != null){
                graph.append(currentValue)
                    .append("\n");
            }
            
            graph.append(StringUtils.rightPad(interval.getKey(), maxLabelWidth+2))
                 .append(currentValue);
                
                
            if(!intervalIter.hasNext()){
                if(maxValue == null){
                    graph.append(" = X");
                }
                else{
                    graph.append(" >= X <= ")
                        .append(HISTORICAL_GRAPH_FORMAT_2DIGIT_NUM.format(maxValue));
                }
            }
            else{
                graph.append(" >= X < ");
            }
                
            prev = interval;
        }
    }
    
    static private void appendGraphHorizontalLine(StringBuilder graph, int historyCount, int graphWidth){
        graph.append(HISTORICAL_GRAPH_HORZ_LINE)
        .append(StringUtils.repeat("=", graphWidth-HISTORICAL_GRAPH_HORZ_LINE.length()+1))
        .append("+\n");
    }
    
    static private void appendHistoryValue(StringBuilder graph, int currentValue, double historyValue){
        //the next larger integer from the truncated version of the history value
        int nextHistoryValue = ((int)historyValue) + 1;
        
        if(currentValue > nextHistoryValue){
            graph.append(HISTORICAL_GRAPH_EMPTY_COL);
        }else if(currentValue == nextHistoryValue){
            //print the number
            String barValue = historyValue >= 100d ? 
                    HISTORICAL_GRAPH_FORMAT_3DIGIT_NUM.format(historyValue) 
                    : HISTORICAL_GRAPH_FORMAT_2DIGIT_NUM.format(historyValue);
                
            graph.append(StringUtils.center(barValue, HISTORICAL_GRAPH_COL_WIDTH));
        }
        else{
            graph.append(HISTORICAL_GRAPH_BAR_COL);
        }
    }
    
    static private void appendGraphMargin(StringBuilder graph, Deque<Double> axisValues,
    		int currentValue, int halfway, double largestYRow){
        
        if(currentValue == halfway){
            graph.append(" SCORE   ");
        }
        else{
            graph.append("         ");
        }
        //When the axis value is between to integers we are to output a label for the row with the larger integer
        if(!axisValues.isEmpty() && Math.ceil(axisValues.getLast().doubleValue()) >= currentValue){
            if(currentValue < 100 && currentValue > 9){
                graph.append(" ");
            }
            else if(currentValue < 10){
                graph.append("  ");
            }
            graph.append(currentValue);
            
            //remove any values that would map to this row (case is that we have mulitple fractional values (e.g. 2.35, 2.5, 2.75) and we only want to put put once
            while(!axisValues.isEmpty() && Math.ceil(axisValues.getLast().doubleValue()) >= currentValue){
            	axisValues.removeLast();
            }
        }
        else{
            graph.append("   ");
        }
        
        if(currentValue != largestYRow){
            graph.append('|');
        }
    }
    
}
