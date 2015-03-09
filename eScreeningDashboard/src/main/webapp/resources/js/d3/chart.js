$(document).ready(function() {        
	/* JH | To use in HTML 
		var vaid 	= "${veteranAssessmentInfo.veteranAssessmentId}";
		var vid 	= "${veteranAssessmentInfo.veteranId}";
		if(typeof vaid === 'undefined' || vaid == ''){  
			vaid = getParameterByName('vaid');
		};
		if(typeof vid === 'undefined' || vid == ''){
			vid = getParameterByName('vid');
		};			
		graphContainer(vaid, vid);
	*/
});

/* get Query String Parameter By Name */
function getParameterByName(name) {
	name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
		results = regex.exec(location.search);
	return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

// Cache all classes and IDs  
var graphContainer = function(vaid, vid){
        var modal_contents 	= $("#VeteranSummaryModal .modal_contents");
        $(modal_contents).html('<i class="ajax_loading text-center"></i> Loading...');

 	    var modal_url = 'assessmentSummary/assessments/' + vaid + '/veteranSummary' ;
 	    $.ajax({
		  	type : 'get',
		  	contentType: 'application/json',
		   	url : modal_url,
	   		success : function(r){  
	   		    $(modal_contents).html(r);
	            $(".graphicBody").each(function(graphId){
	            	
	                //TODO: mayb we should add a "loading..." icon while we get data from server
	                
	            	var $this = $(this);
	                var graphObj  = $.parseJSON($this.html());
					
	                $this.html(""); //clear the graph area
	        		var stackGraphParams = processIntervals(graphObj);
	        		var timeSeriesParams = processIntervals(graphObj);
	                
					
	                var parentDiv = $this.parents(".moduleTemplate");
	                parentDiv.addClass("graphicBlock");
	                var titleContainer = parentDiv.children(".moduleTemplateTitle");
	                var graphContainer = parentDiv.children(".graphSection");
	                var descriptionContainer = parentDiv.children(".moduleTemplateText");
	            	  
	        		// Update title block to contain the scoring
	        		titleContainer.wrap("<div class='scoreBlock text-center'>");
	        		titleContainer.parent().append("<div><h4>" + stackGraphParams.score + "</h4><h5>" + stackGraphParams.scoresInterval + "</h5></div>");
	        		
	        		//Start adding to the graphic block with the graph's title 
	        		graphContainer.prepend("<div class='graphHeader'>" + graphObj.title + "</div>");
	        		
	        		//Add d3 graph
	                var graphContainerId = "graph_" + graphId;
	                var graphSelector = "#" + graphContainerId;
	                graphContainer.children(".graphicBody").prop("id", graphContainerId)
	                
	                //get time series for the variable
	                // Call timeSeries JSON
					$.ajax({
						type : 'get',
						url : 'assessmentSummary/assessmentvarseries/' + vid + '/' + graphObj.varId + '/' + graphObj.numberOfMonths,
						success : function(points){  
							
							//append correct graph type given the number of historical results for the variable
							if(hasMoreThanOne(points)){
								appendTimeSeries(graphSelector, timeSeriesParams, points);
							}
							else{
								appendStackGraph(graphSelector, stackGraphParams);
							}
			                
							//Add footer if we were given one
							if(graphObj.footer != null && graphObj.footer != ""){
								graphContainer.append("<div class='graphFooter text-center'>" + graphObj.footer +"</div>");
							}									
						},
				  		error: handleError
					});
	            });
	            
			},
          	error: handleError
 	    });
 	    
 	   function handleError(xhr, exception, errorThrown) {
           data = "[" + xhr.responseText + "]";
           data = $.parseJSON(data);
     
           var userMessage       = [];
           var developerMessage  = [];
           for (var i = 0; i < data.length; ++i) {                    
             for (var j = 0; j < data[i].errorMessages.length; j++) {
               errorMessages = data[i].errorMessages[j];
               userMessage.push("<div class='userErrorMessage'>" + [errorMessages.description] + "</div>");
             }
             if(data[i].developerMessage.length > 0){
               result =          "<div class='developerErrorIDMessage'>" + "<strong>ID:</strong> " + [data[i].id] + "</div>";
               result = result + "<div class='developerErrorMessage'>" + "<strong>Developer Message:</strong> " + [data[i].developerMessage] + "</div>";
               result = result + "<div class='logErrorMessage'>" + "<strong>Log Message:</strong> " + [data[i].logMessage] + "</div>";
               developerMessage.push(result);
             }
           }
           var panelTemplate = userMessage;
               panelTemplate = panelTemplate + '<div class="panel-danger-system detailedErrorMessageBlock"><div class="panel-group" id="veteranSummaryAccordion"><div class="panel panel-default"><div class="panel-heading"><h4 class="panel-title"> <a data-toggle="collapse" data-parent="#veteranSummaryAccordion" href="#collapseOne2"> System Error <span class="label label-danger">Click here for more error details</span> </a> </h4></div><div id="collapseOne2" class="panel-collapse collapse"><div class="panel-body"><div class="detailedErrorMessage">';
               panelTemplate = panelTemplate + developerMessage;
               panelTemplate = panelTemplate + '</div></div></div></div></div></div>'
           
           $(modal_contents).show().html(panelTemplate);
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
		}
		return graphParams;
	}
	
	
    //TODO:
	  // 1. for the colors of each bar, what happens when we have more than 6 intervals?  We need the start color and then end color and then
	     // we take the number of intervals and calculate the colors needed to get from the start color to the end color.
	  // 2. the y axis label should not be given 
	  // 3. the score is not showing up in the graph
       
    var colors		= ['#cfd8e0', '#b7c4d0', '#879cb2', '#577593', '#3f6184', '#0f3a65'];
	
	function appendStackGraph(parentSelector, graphParams){

		var ticks = graphParams.ticks;
		
        //Set d3 graph attributes
	    var margins = {
		          top: 46,
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
		      xMax            = d3.max(ticks),
		      xCurrent        = graphParams.score, //4,
		      ticks           = ticks, //[0, 4, 10, 20, 27],
		      // colors          = ['#cfd8e0', '#b7c4d0', '#879cb2', '#577593', '#3f6184', '#0f3a65', '#0d3054', '#0a2845', '#082038', "#000000"],
		      series          = graphParams.legends,
		      dataset         = graphParams.dataset,
		      pointerColor    = '#0f3a65',
		      pointerWidth    = 36,
		      pointerHeight   = 36,
		      stack = d3.layout.stack();
		
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
			    .domain([graphParams.graphStart, xMax])
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

		var xPos = parseFloat(width / (xMax - graphParams.graphStart)) * ( xCurrent - graphParams.graphStart ) ;
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
	            .attr('fill', 'white')
	            .attr('font-size', '20')
	            .attr('font-weight', 'bold')
	            .style("text-anchor", "middle")
	            .attr('x', xPos)
	            .attr('y', -15)
	            .text(xCurrent);
    
    
		  // xAxis postion
		  svg.append('g')
		      .attr('class', 'axis')
		      .attr('transform', 'translate(0,' + height + ')')
		      .call(xAxis);
		
		  
		  // yAxis postion
		  svg.append('g')
		      .attr('class', 'axis')
		      .call(yAxis);
		
		  // legend Rect
		  svg.append('rect')
		      .attr('fill', 'white')
		      .attr('width', legendPanel.width)
		      .attr('height', 5 * dataset.length)
		      .attr('x', 0)
		      .attr('y', 100);
		
		   // legend Text & Box
		  textWidth = 15;
		   series.forEach(function(s, i) {
			   text = svg.append('text')
		          .attr('fill', 'black')
				  .attr('width', 100)
		          .attr('x', textWidth)
		          .attr('y', 100)
		          .attr('font-size', '10')
		          .text(s);
			   box = svg.append('g')
			   	  .append('rect')
		          .attr('fill', colors[i])
		          .attr('width', 10)
		          .attr('height', 10)
		          .attr('x', textWidth - 15)
		          .attr('y', 90);
		      	  textWidth += parseFloat(text.node().getComputedTextLength())  + 30;
              console.log("Parse 8----");
              console.log(text.node().getComputedTextLength());
		  });
		  
		  // Fix graphic bar issue
		  $(parentSelector).find('.bars > g')
		      .each(function() {
		          $(this).prependTo(this.parentNode);
		  });
	}

   
	function appendTimeSeries(parentSelector, graphParams, points){
		
    	$(parentSelector).addClass("timeSeries");

		var ticks = [];
		var maxValue;
		
		var series   = graphParams.legends;
		
		$.each(points, function(date, valueStr){
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
		//var maxValue = d3.max(ticks , function(d) { return +d.value;} );
		
		// Vars
		var w = 450,
			h = 300,
			margin = {
				top: 20,
				bottom: 60,
				left: 30,
				right: 140
			},
			xRangeStart = 25,		// Move the x axis to right
			yStartPoint = 0; 		// Start Point for y axis
			
					
		// var colors          = ['#cfd8e0', '#b7c4d0', '#879cb2', '#577593', '#3f6184', '#0f3a65']; // TODO - May need to swap with this list 
		colors.reverse();
		
		// In progress
		//var graphparams.intervals = {"2015-01-23T02:31:09.000+0000":"14.0"};
			
		// Static Vars
		var legendTitle = "My Score"; 
		
		var xLegendTextPosition = w - 115,
			xLegendRectPosition = w - 130;
	
		var width = w - margin.left - margin.right,
			height = h - margin.top - margin.bottom;
	
		var svg = d3.select(parentSelector).append("svg")
			.attr("id", "chart")
			.attr("width", w)
			.attr("height", h);
	
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
			return +d.value;
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
				.call(params.axis.x)
				.selectAll("text")  
				.style("text-anchor", "end")
				.attr("dx", "-.8em")
				.attr("dy", ".15em")
				.attr("transform", function(d) { return "rotate(-65)"; });
	
			this.append("g")
				.classed("y axis", true)
				.attr("transform", "translate(0,0)")
				.call(params.axis.y);
			
			
			// Enter Plot Started Here
			this.selectAll(".trendline")
				.data(ticks)
				.enter()
				.append("path")
				.classed("trendline", true);
	
			this.selectAll(".point")
				.data(ticks)
				.enter()
				.append("circle")
				.classed("point", true)
				.attr("r", 5);
	
			this.selectAll(".pointText")
				.data(ticks)
				.enter()
				.append("text")
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
				.attr("height", h)
				.attr("width", w)
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
				.attr("x", xLegendTextPosition)
				.attr("y", function (d, i) {
				return i * 35 + 9;
			})
				.attr("dy", 0)
				.text(function (d, i) {
					
					var text = graphParams.legends[i];
					return text;
				})
			.call(wrap, 100);
	
			legend.append("circle")
				.classed("point", true)
				.attr("r", 5)
				.attr("cx", xLegendRectPosition+4 )
				.attr("cy", -12);
			
			 legend.append("text")
				.classed("pointTextValue", true)
				.attr("x", xLegendTextPosition)
				.attr("y", -2)
				.attr("width", 100)
				.text(legendTitle);
	
	
			// Bar Legend Start Here
			// Create X Scale for bar graph
			var xScale = d3.scale.ordinal()
				.domain([20])
				.rangeRoundBands([0, 20]);
	
			//Create Y Scale for bar graph
			var yScale = d3.scale.linear()
				 .domain([yStartPoint, d3.max(ticks, function (d) {
				 return +d.value;
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
					if( maxValue >= +d[0].y){
						return height - yScale(+d[0].y) + 0;
					}else{
						return height - yScale(+maxValue) + 0 ;
					}
				});
	
	
			// Text Wrapper
			function wrap(text, width) {
				text.each(function () {
					var text = d3.select(this),
						words = text.text().split(/\s+/).reverse(),
						word,
						line = [],
						lineNumber = 0,
						lineHeight = 1.1, // ems
						y = text.attr("y"),
						dy = parseFloat(text.attr("dy")),
						tspan = text.text(null).append("tspan").attr("x", 0).attr("y", y).attr("dy", dy + "em");
					while (word = words.pop()) {
						line.push(word);
						tspan.text(line.join(" "));
						if (tspan.node().getComputedTextLength() > width) {
							line.pop();
							tspan.text(line.join(" "));
							line = [word];
							tspan = text.append("tspan").attr("x", 0).attr("y", y).attr("dy", ++lineNumber * lineHeight + dy + "em").text(word);
						}
					}
				});
			}
	
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
		// Reverse bars	
		//$('.bars > rect').each(function () {
		//	$(this).prependTo(this.parentNode);
		//});
	}
}