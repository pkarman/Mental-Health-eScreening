/* V 1.01 */
function graphGenerator(dataStructure, dataDataset){
	var graphWrapper 	= $(".graphWrapper");
	var colors		= ['#cfd8e0', '#b7c4d0', '#879cb2', '#577593', '#3f6184', '#0f3a65'];

	$(graphWrapper).html('<i class="text-center"></i> Loading...');	
	$(graphWrapper).html(dataStructure);

	// graphId = Math.floor((Math.random() * 10) + 1);
	graphId = 1; // Static for reports testing

	var stackGraphParams = processIntervals(dataStructure);
	var timeSeriesParams = processIntervals(dataStructure);
	var parentDiv = graphWrapper.parents(".moduleTemplate");
	graphWrapper.addClass("graphicBlock");
	var graphContainer = parentDiv.children(".graphSection");

	//Add d3 graph
	var graphContainerId 	= "graph_" + graphId;
	var graphSelector 		= "#" + graphContainerId;
	graphContainer.children(".graphicBody").prop("id", graphContainerId)
	
	//get time series for the variable
	// Call timeSeries JSON

	//append correct graph type given the number of historical results for the variable
	if(hasMoreThanOne(dataDataset)){
		appendTimeSeries(graphSelector, timeSeriesParams, dataDataset);
	}else{
		appendStackGraph(graphSelector, stackGraphParams);
	}
		
	/**
	 * Initializes basic graph parameter using given graph parameters.
	 * Uses fields: 
	 *	 intervals - array of map from interval name to the starting value for that interval
	 *	 score - the score for the assessment we are summarizing
	 *	 maxXPoint - the maximum value of any score (end of last interval), this can be undefined if the last interval will be the end of the graph
	 * Sets fields:
	 *	 legends - names for each interval
	 *	 dataset - the array of objects containg x,y which are used by the stack graph to set intervals
	 *	 scoresInterval - name of the interval where the score is found
	 *	 graphStart - the minimum value of any score (start of first interval)
	 */
	
	function processIntervals(graphParams){
		graphParams.legends = [];
		graphParams.dataset = [];
		graphParams.scoresInterval;
		graphParams.graphStart = 0;
		ticks = graphParams.ticks;
		
		var prevInterval, prevName;
		
		$.each(graphParams.intervals, function(name, intervalStart){
			graphParams.legends.push(name);

			//skip the first one
			if(prevInterval != null){
				graphParams.dataset.push([{x:"", y:intervalStart}]);
			}
			else{
				graphParams.graphStart = intervalStart;
			}
			
			if(prevInterval != null && graphParams.score >= prevInterval && graphParams.score < intervalStart){
				graphParams.scoresInterval = prevName;
		    }
			prevInterval = intervalStart;
			prevName = name;
		}); 
		
		//check if scoreInterval is in the last interval
		if(graphParams.scoresInterval == null && graphParams.score >= prevInterval){
			graphParams.scoresInterval = prevName;
		}
		
		//if this is null then that means the final interval will end the graph (there must be a tick for this last point)
		if(graphParams.maxXPoint != null){
			graphParams.dataset.push([{x:"", y:graphParams.maxXPoint}]);
		}else{
			graphParams.dataset.push([{x:"", y:d3.max(ticks)}]);
		}
		return graphParams;
	}

    //TODO:
	  // 1. for the colors of each bar, what happens when we have more than 6 intervals?  We need the start color and then end color and then
	     // we take the number of intervals and calculate the colors needed to get from the start color to the end color.
	  // 2. the y axis label should not be given 
	  // 3. the score is not showing up in the graph
	
	function appendStackGraph(parentSelector, graphParams){
		var ticks = graphParams.ticks;
		var title = graphParams.title;

        //Set d3 graph attributes
	    var margins = {
		          top: 58,
		          left: 15,
		          right: 15,
		          bottom: 0
		      },
		      containerWidth    = 450,
		      containerHeight   = 100,
		      legendPanel       = {
		                            width: containerWidth - margins.left - margins.right
		                           },
		      width       = containerWidth - margins.left - margins.right,
		      height      = containerHeight - margins.top - margins.bottom,
		      value;
		      
		      // Settings
		      //xMax            = d3.max(ticks), // change to maxPoint in case not able to send me the right ticket // graphParams.maxXPoint
		      var graphMaxValue	  = graphParams.maxXPoint,
			  xCurrent        = graphParams.score, //4,
		      ticks           = ticks, //[0, 4, 10, 20, 27],
		      // colors       = ['#cfd8e0', '#b7c4d0', '#879cb2', '#577593', '#3f6184', '#0f3a65', '#0d3054', '#0a2845', '#082038', "#000000"],
		      series          = graphParams.legends,
		      dataset         = graphParams.dataset,
		      pointerColor    = '#0f3a65',
		      pointerWidth    = 40,
		      pointerHeight   = 36,
			  titleY 		  = -45,
		      stack = d3.layout.stack();

		
			  // ticks 		  = ticks.push(graphParams.maxXPoint),
			  if( graphParams.maxXPoint != null ){
				graphMaxValue	  = graphParams.maxXPoint;
				ticks.push(graphMaxValue);
			  }else{
				graphMaxValue	  = d3.max(ticks);
			  }


	    stack(dataset);
		var dataset = dataset.map(
		    function(group) {
			    return group.map(function(d) {
			        // Invert the x and y values, and y0 becomes x0
			        return {
			            x: d.y,
			            y: d.x,
			            x0: d.y0
			        };
			    });
			}),
	
			svg = d3.select(parentSelector)
			    .append('svg')
			        .attr('width', width + margins.left + margins.right)
			        .attr('height', height + margins.top + margins.bottom + 60)
			    .append('g')
			        .attr('class', 'bars')
			        .attr('transform', 'translate(' + margins.left + ',' + margins.top + ')'),
			
			xScale = d3.scale.linear()
			    .domain([graphParams.graphStart, graphMaxValue])
			    .range([0, width]),
			    
			notes = dataset[0].map(function(d) { return d.y; }),
			_ = console.log(notes),
			
			yScale = d3.scale.ordinal()
			    .domain(notes)
			    .rangeRoundBands([0, height], .1),
			    
			xAxis = d3.svg.axis()
			    .scale(xScale).tickValues(ticks)
			    .orient('bottom'),
			    
			yAxis = d3.svg.axis()
			    .scale(yScale)
			    .orient('left'),
			
			// Bars Start
			groups = svg.selectAll('g')
			    .data(dataset)
			    .enter()
			      .append('g')
			        .attr('class', function(d, i) {
			            return "bar_" + [i];
			        })
			        .style('fill', function(d, i) {
			            return colors[i];
			        })
			        
			rects = groups.selectAll('rect')
			    .data(function(d) { return d; })
			    .enter()
			        .append('rect')
			            .attr('x', 0)
			            .attr('y', 0)
			            .attr('height', function(d) { return yScale.rangeBand(); })
			            .attr('width', function(d) { return xScale(d.x); });

		var xPos = parseFloat(width / (graphMaxValue - graphParams.graphStart)) * ( xCurrent - graphParams.graphStart ) ;
		var yPos = 0;
		
		pointer = svg.append('rect')
        .attr('fill', pointerColor)
        .attr('width', pointerWidth)
        .attr('height', pointerHeight)
        .attr('x', xPos - (pointerWidth/2))
        .attr('y', -40)
        .attr('class', 'pointerBlock');
    

	    svg.append('rect')
	        .attr('fill', 'black')
	        .attr('width', 1)
	        .attr('height', 5)
	        .attr('x', xPos)
	        .attr('y', -5)
	        .attr('class', 'pointerLine');
	
	    svg.append('text')
			.style("text-anchor", "middle")
			.attr('x', xPos)
			.attr('y', -15)
			.attr('fill', 'white')
			.attr('font-size', '16')
			.attr('font-weight', 'bold')
			.style("text-anchor", "middle")
			.attr('font-family', 'arial')
			.text(xCurrent);
    
    
		  // xAxis postion
		  svg.append('g')
		      .attr('class', 'axis')
		      .attr('transform', 'translate(0,' + height + ')')
			  .attr('font-family', 'sans-serif')
			  .attr('font-size', '10px')
			  .attr('fill', 'none')
			  .attr('stroke', '#000')	
			  .attr('shape-rendering', 'crispEdges')
		      .call(xAxis);
		
		  
		  // yAxis postion
		  svg.append('g')
		      .attr('class', 'axis')
			  .attr('fill', 'none')
			  .attr('stroke', '#000')
			  .attr('shape-rendering', 'crispEdges')
		      .call(yAxis);
		
		  // legend Rect
		  svg.append('rect')
		      .attr('fill', 'white')
		      .attr('width', legendPanel.width)
		      .attr('height', 5 * dataset.length)
		      .attr('x', 0)
		      .attr('y', 120);
		
		   // legend Text & Box
		  textWidth = 8;
		  gWidth = 15;
		   series.forEach(function(s, i) {
			   g 	= svg.append('g')
			   			.attr('width', 100)
						.attr('x', gWidth)
						 .attr('transform', 'translate(' + gWidth + ',-15)')
						gWidth += 60  + 10;
						
			   text = g.append('text')
		          .attr('fill', 'black')
				  .attr('width', 100)
		          .attr('x', 30)
		          .attr('y', 100)
		          .attr('font-size', '10')
				  .attr('font-family', 'Arial')
				  .attr("dy", 0)
				  .text(s)
				  .call(wrap);

			   box = g.append('rect')
		          .attr('fill', colors[i])
		          .attr('width', 10)
		          .attr('height', 10)
		          .attr('x', -15)
		          .attr('y', 92);
		      	  textWidth += 60  + 10 ;

				svg.append('text')
					.attr('x', parseFloat(width / 2))
					.attr('y', titleY)
					.attr('fill', 'black')
					.attr('font-size', '14')
					.attr('font-weight', 'bold')
					.style("text-anchor", "middle")
					.attr('font-family', 'arial')
					.text(title);
					
				  
              console.log("Parse 8----");
			  console.log(g.node().getBoundingClientRect().width);
              console.log(text.node().getComputedTextLength());
		  });
		  
		  // Fix graphic bar issue
		  $(parentSelector).find('.bars > g')
		      .each(function() {
		          $(this).prependTo(this.parentNode);
		  });
	}

   
	function appendTimeSeries(parentSelector, graphParams, dataDataset){
    	$(parentSelector).addClass("timeSeries");

		var maxValue;
		var ticks 	= [];
		var series  = graphParams.legends;
		var title = graphParams.title;

		$.each(dataDataset, function(date, valueStr){
			//TODO: Add check if can't be parsed
			var value = parseFloat(valueStr);
			if(maxValue == null || maxValue < value){
				maxValue = value;
			}
			
			ticks.push({
				'date': date.split(" ")[0], //parse out the date (removing time)
				'value': value
			});
		});
		
		ticks.reverse(); // reverse the order on the ticks 
		
		// Vars
		var containerWidth = 450,
			containerHeight = 300,
			margin = {
				top: 35,
				bottom: 60,
				left: 30,
				right: 140
			},
			xRangeStart = 25,		// Move the x axis to right
			yStartPoint = 0; 		// Start Point for y axis
			
		colors.reverse();
		
		// Static Vars
		var legendTitle = "My Score"; 
		
		var xLegendTextPosition = containerWidth - 115,
			xLegendRectPosition = containerWidth - 130;
	
		var width = containerWidth - margin.left - margin.right,
			height = containerHeight - margin.top - margin.bottom;
	
		var svg = d3.select(parentSelector).append("svg")
			.attr("id", "chart")
			.attr("width", containerWidth)
			.attr("height", containerHeight);
	
		var chart = svg.append("g")
			.classed("display", true)
			.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
	
		var dateParser = d3.time.format("%m/%d/%Y").parse;
		var dateArr = [];
		
		// Format date
		function formatDate (input) {
		  var datePart = input.match(/\d+/g),
		  month = datePart[0],
		  day = datePart[1],
		  year 	= datePart[2].substring(2);
		  return month+'/'+day+'/'+year;
		}

		for (var i = 0; i < ticks.length; i++) {
			dateArr.push(formatDate(ticks[i].date));
		}
		
		var x = d3.scale.linear().domain([0, ticks.length]).range([xRangeStart, width]);

		var y = d3.scale.linear()
			.domain([yStartPoint, d3.max(ticks, function (d) {
			return graphParams.maxXPoint;
			//return +d.value;
		})])
			.range([height, 0]);
		
		var xAxis = d3.svg.axis()
			.scale(x)
			.orient("bottom")
			.tickFormat(function(d, i) {
				return dateArr[i];
			}).ticks(ticks.length);

		var yAxis = d3.svg.axis()
			.scale(y)
			.orient("left")
			.ticks(5);
	

		var line = d3.svg.line().interpolate("cardinal")
			.x(function (d, i) {
			return x(i);
		})
			.y(function (d) {
			return y(d);
		});		
		
		function plot(params) {
			
			var intervals 	= params.data.intervals,
				ticks 		= params.data.ticks;

			var valueArr = [];
			
			for (var i = 0; i < ticks.length; i++) {
				valueArr.push(ticks[i].value);
			}

			this.append("g")
				.classed("x axis", true)
				.attr("transform", "translate(0," + height + ")")
				.attr("fill", "none")
				.attr("stroke", "#000")
			    .attr('shape-rendering', 'crispEdges')
				.attr('font-family', 'sans-serif')
				.attr('font-size', '11px')
				.call(params.axis.x)
				.selectAll("text")  
				.style("text-anchor", "end")
				.attr("dx", "-.8em")
				.attr("dy", ".15em")
				.attr("transform", function(d) { return "rotate(-65)"; })
				;
	
			this.append("g")
				.classed("y axis", true)
				.attr("transform", "translate(0,0)")
				.attr('font-family', 'sans-serif')
				.attr('font-size', '11px')
				.attr("fill", "none")
				.attr("stroke", "#000")
			    .attr('shape-rendering', 'crispEdges')
				.call(params.axis.y);
			
			
			// Enter Plot Started Here
			this.selectAll(".trendline")
				.data(ticks)
				.enter()
				.append("path")
                .attr("fill", "none")
                .attr("stroke", "#2d5277")
                .attr("stroke-width", "2px")
				.classed("trendline", true);
	
			this.selectAll(".point")
				.data(ticks)
				.enter()
				.append("circle")
				.attr("fill", "#fff")
                .attr("stroke", "#2d5277")
				.classed("point", true)
				.attr("r", 5);
	
			this.selectAll(".pointText")
				.data(ticks)
				.enter()
				.append("text")
				.attr('font-family', 'arial')
                .attr('font-size', '10px')
				.classed("pointTextValue", true)
				.text( function (d) { return +d.value; });
	

			// Update Plot Started Here
			this.selectAll(".trendline")
				.attr("d", function (d) {
				return line(valueArr);
			});
			this.selectAll(".point")
				.attr("cx", function (d, i) { return x(i); })
				.attr("cy", function (d) { return y(+d.value); });

			this.selectAll(".pointTextValue")
				.attr("x", function (d, i) { return x(i); })
				.attr("y", function (d) { return y(+d.value);})
				.attr("dy", "-1em")
				.attr("dx", ".2em")
				.style("text-anchor", "middle");
	
			// Exit Plot Started Here
			this.selectAll(".trendline")
				.data(ticks)
				.exit()
				.remove();
			this.selectAll(".point")
				.data(ticks)
				.exit()
				.remove();
	
			// Add Legend Started Here 
			var legend = this.append("g")
				.attr("class", "legend")
				.attr("height", containerHeight)
				.attr("width", containerWidth)
				.attr('transform', 'translate(-20, 10)');
	
			legend.selectAll('rect')
				.data(graphParams.dataset)
				.enter()
				.append("rect")
				.attr("x", xLegendRectPosition)
				.attr("y", function (d, i) {
				return i * 35;
			})
				.attr("width", 8)
				.attr("height", 30)
				.style('fill', function(d, i) { return colors[i]; });
	
			// Add Legend Started Here
			graphParams.legends.reverse();
			
			legend.append("g")
				.attr("class", "legendBar")
				.attr("transform", "translate(" + xLegendTextPosition + ", 0)")
				.selectAll('text')
				.data(graphParams.dataset)
				.enter()
				.append("text")
				.attr('font-family', 'arial')
                .attr('font-size', '12px')
				.attr("x", xLegendTextPosition)
				.attr("y", function (d, i) {
				return i * 35 + 9;
				})
				.attr("dy", 0)
				.text(function (d, i) {
					var text = graphParams.legends[i];
					return text;
				})
				.call(wrap);
	
			legend.append("circle")
				.classed("point", true)
 				.attr("fill", "none")
                .attr("stroke", "#2d5277")
				.attr("r", 5)
				.attr("cx", xLegendRectPosition+4 )
				.attr("cy", -12);
			
			 legend.append("text")
				.classed("pointTextValue", true)
				.attr("x", xLegendTextPosition)
				.attr("y", -8)
				.attr("width", 100)
				.attr('font-family', 'arial')
                .attr('font-weight', 'bold')
                .attr('font-size', '12px')
				.text(legendTitle);
		
			// Bar Legend Start Here
			// Create X Scale for bar graph
			var xScale = d3.scale.ordinal()
				.domain([20])
				.rangeRoundBands([0, 20]);
	
			//Create Y Scale for bar graph
			var yScale = d3.scale.linear()
				 .domain([yStartPoint, d3.max(ticks, function (d) {
				 // return +d.value;
				 return graphParams.maxXPoint;
			})])
				.range([height, 0]);
	
			// Add Rectangles
			this.append('g')
				.attr("class", "bars")
				.selectAll(".bar")
				.data(graphParams.dataset.reverse())
				.enter()
				.append("rect")
				.attr("class", "bar")
				.style('fill', function(d, i) {
			            return colors[i];
			     })
				.attr("x", 0)
				.attr("y", function (d, i) {
					return yScale(+d[0].y);
			})
				.attr("width", xScale.rangeBand()) //returns rangeRoundBands width
				.attr("height", function (d) {
					if( graphParams.maxXPoint >= +d[0].y){
						return height - yScale(+d[0].y) + 0;
					}else{
						return height - yScale(+maxValue) + 0 ;
					}
				});
				
				


			this.append('text')
				.attr('x', parseFloat(width / 2))
				.attr('y', -25)
				.attr('fill', 'black')
				.attr('font-size', '14')
				.attr('font-weight', 'bold')
				.style("text-anchor", "middle")
				.attr('font-family', 'arial')
				.text(title);
	
		colors.reverse(); // reset colors order
	
		} // End of plot function
	
	
		// Call Plot
		plot.call(chart, {
			data: {
				'intervals': graphParams.intervals,
				'ticks': ticks
			},
			axis: {
				x: xAxis,
				y: yAxis
			}
		});
	}
	 	   


// Text Wrapper
function wrap(text) {
	text.each(function () {
		var text = d3.select(this),
			words = text.text().split(/\s+/).reverse(),
			word,
			line = [],
			lineNumber = -1,
			lineHeight = 1.0, // ems
			y = text.attr("y"),
			dy = parseFloat(text.attr("dy")),
			tspan = text.text(null).append("tspan").attr("x", 0).attr("y", y).attr("dy", dy + "em");
			console.log("tspan >>");
			console.log(tspan);
			console.log("<< tspan");
	
		while (word = words.pop()) {
			line.push(word);
			tspan.text(line.join(" "));
			//if (tspan.node().getComputedTextLength() > width) {
				line.pop();
				tspan.text(line.join(" "));
				line = [word];
				tspan = text.append("tspan").attr("x", 0).attr("y", y).attr("dy", ++lineNumber * lineHeight + dy + "em").text(word);

			
			//}
		}
	});
}
				
function hasMoreThanOne(obj) {
		if (Object.keys) { 
			return Object.keys(obj).length > 1;
		}
		var c = 0;
		for (field in obj) {
			if(c > 1){ return true; }
			c++;
		}
		return false;
		}	
}			
// Root Function to generate SVG obj
var svgObj  = function(){
	// graphSVG created for Khalid
	var graphSVG = $(".graphWrapper").html();
	return graphSVG;
}

$(document).ready(function() {
//	 // Example Dataset for Structure JSON and Data JSON
	 //var dataStructure 	= {'ticks': [ 0, 1, 5, 10, 15, 20, 27 ], 'score': 16, 'footer': '', 'varId': 1599, 'title': 'My Depression Score',  'intervals': {'None': 0, 'Moderately Severe': 15, 'Mild': 5, 'Severe': 20,  'Moderate': 10, 'Minimal': 1},  'maxXPoint': 27, 'numberOfMonths': 12};
	 //var dataDataset 	= {'03/06/2015 08:59:38': 16, '01/23/2015 12:51:17': 27, '09/23/2014 12:36:48': 5};
//	 // Call graphGenerator
	 //graphGenerator(dataStructure, dataDataset);
//	 // HTML Placeholder container to be added in JSP page
//	 // <div class="graphWrapper" id="graph_1"></div>
    console.log("chart.js loaded just fine")
});