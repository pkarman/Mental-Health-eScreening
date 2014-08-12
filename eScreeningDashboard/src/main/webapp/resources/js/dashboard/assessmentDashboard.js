var app = angular.module('assessmentDashboardApp', []);
app.directive('reportTable', function() {
	return function(scope, element, attrs) {
        var options = {};
        options = {
	    	"bProcessing": true,
	    	"bServerSide": true,
	    	"aaSorting": [[0,"desc"]],
	    	"bFilter": false,
	    	"bJQueryUI": true,
	    	"aLengthMenu": [[10,25,50,100,250,500,750,1000], [10,25,50,100,250,500,750,1000]],
	    	"sPaginationType": "full_numbers",
	    	"sServerMethod": "POST",
	    	"sAjaxSource": "assessmentDashboard/services/assessments/search",
	    	"fnServerData": scope.$eval(attrs.fnDataCallback)
        };

        var aoColumns = {};
        aoColumns = [
			{ "mData": "assessmentDate","sClass":"numeric"},
			{ "mData": "veteranName", "mRender": function(data, type, full) { return '<a href="assessmentSummary?vaid='+full.veteranAssessmentId+'"><span title="View Assessment Summary for ' + data + '" Name="View Assessment Summary for ' + data + '">' + data + '</span></a>'; }},
			{ "mData": "ssnLastFour","sClass":"numeric"},
			{ "mData": "programName"},
			{ "mData": "clinicianName"},
			{ "mData": "duration","sClass":"numeric"},
			{ "mData": "percentComplete","sClass":"numeric", "mRender": function(data, type, full) { return full.percentComplete + "%"; }},
			{ "mData": "assessmentStatusName", "mRender": function(data, type, full) { return "<div class='ico_status_"+full.assessmentStatusName+"'>" + full.assessmentStatusName + "</div>" ; }},
			{ "mData": "alerts[].alertName" , "bSortable" : false , "mRender": function(data, type, row) { return "<div class='alerts_list'> " + data.toString().replace(/ *, */g, '<br > ') + "</div>"; }}];

        options["aoColumns"] = aoColumns;

        // apply the plugin
        var dataTable = element.dataTable(options);

        // watch for any changes to our data, rebuild the DataTable
        scope.$watch(attrs.aaData, function(value) {
            var val = value || null;
            if (val) {
                dataTable.fnClearTable();
                dataTable.fnAddData(scope.$eval(attrs.aaData));
            }
        });     
    };
});

app.factory('programListService', function($http) {
	return {
		getProgramList : function() {
			return $http({
				method : "POST",
				url : "assessmentReport/services/user/programs",
				responseType : "json"
			}).then(function(result) {
				return result.data;
			});
		}
	};
});

app.controller("assessmentDashboardController", function($scope, $element,
		$http, $window, programListService) {
		
		$scope.assessmentDashboardFormBean = {};
		$scope.assessmentDashboardFormBean.programId = "";
	
		programListService.getProgramList().then(function(data) {
			$scope.programList = data;
		});
	
		$scope.searchDatabase = function() {
			var oTable = $('#assessmentDashboardTable').dataTable();
			oTable.dataTable().fnDraw(true);
		};

		//#################################
		//##########  Charts  #############
		//#################################

		// Global Vars
		$scope.pieChartJSON 		= 'services/alerts/r/dashboardAlerts/99';
		$scope.barChartJSON 		= 'services/alerts/r/slowMovingAssessments/99';
		$scope.barChartHoriJSON		= 'services/alerts/r/nearingCompletionAssessments/99';
		$scope.timerDelay			= 60000; // 1 min
		$scope.showAllId			= 99; // Default ID to list all programs
	
   
	
		// Update Graphs Timer Function
		$scope.updateGraphsTimer = function() {
			if(($scope.programId === undefined) || ($scope.programId == "")  || ($scope.programId == null)){
				$scope.programId = $scope.showAllId;
			}    	

			// Set timer
			$scope.timer = setTimeout($scope.updateGraphsTimer, $scope.timerDelay);
			$scope.updateGraphs();
		};
		
		$scope.stopTimer = function() {
			// ClearTimeOUt
			clearTimeout($scope.timer);
		};
		
		$scope.change = function() {
			if($scope.autorefresh == true){
				$scope.updateGraphsTimer();
				console.log("TimeerEnabled");
			}else{
				console.log("Timeerdisabled");
				$scope.stopTimer();
			}
		};
		
		
		// Update Graph
		$scope.updateGraphs = function() {
			$("#pie_chart svg, #bar_chart svg, #bar_horizontal svg").remove();
			$scope.programId 	= $scope.assessmentDashboardFormBean.programId;
			
			if(($scope.programId === undefined) || ($scope.programId == "")  || ($scope.programId == null)){
				$scope.programId = $scope.showAllId;
			}else{
				$scope.programId 			= $scope.assessmentDashboardFormBean.programId;
			}
			
	
			$scope.pieChartJSON 		= 'services/alerts/r/dashboardAlerts/'+ $scope.programId;
			$scope.barChartJSON 		= 'services/alerts/r/slowMovingAssessments/'+$scope.programId;
			$scope.barChartHoriJSON		= 'services/alerts/r/nearingCompletionAssessments/'+$scope.programId;
						
			$scope.pieChart($scope.pieChartJSON);
			$scope.barChart($scope.barChartJSON);
			$scope.barChartHori($scope.barChartHoriJSON);
		};
		
		$scope.getDataForSearch = function(sSource, aoData, fnCallback, oSettings) {
	
			console.log("Calling getDataForSearch()");
			console.log("assessmentDashboardFormBean.programId: " + $scope.assessmentDashboardFormBean.programId);
	
			aoData.push({
				"name" : "programId",
				"value" : $scope.assessmentDashboardFormBean.programId
			});
	
			oSettings.jqXHR = $.ajax({
				"dataType" : 'json',
				"type" : "POST",
				"url" : sSource,
				"data" : aoData,
				"success" : fnCallback
			});
		};
		
		
	
		// Pie Chart
		$scope.pieChart = function(pieChartJSON) {
			$.ajax({
				type : 'get',
				 url : pieChartJSON, // Call JSON 
					success : function(r)
				   {
					   var pie = new d3pie("pie_chart", {
							"header": {
								"title": {
									"fontSize": 24,
									"font": "open sans"
								},
								"subtitle": {
									"color": "#999999",
									"fontSize": 11,
									"font": "open sans"
								},
								"titleSubtitlePadding": 2
							},
							"footer": {
								"color": "#999999",
								"fontSize": 10,
								"font": "open sans",
								"location": "bottom-left"
							},
							"size": {
								"canvasHeight": 360,
								"canvasWidth": 540,
								"pieInnerRadius": "1%",
								"pieOuterRadius": "50%"
							},
							"data": {
								"content": r
							},
							"labels": {
								"outer": {
									"format": "label-value2",
									"pieDistance": 8
								},
								"inner": {
									
									"hideWhenLessThanPercentage": 3
								},
								"mainLabel": {
									"fontSize": 11
								},
								"percentage": {
									"color": "#ffffff",
									"decimalPlaces": 0
								},
								"value": {
									"color": "#BF6B00",
									"fontSize": 10
								},
								"lines": {
									"enabled": true
								}
							},
							"effects": {
								"pullOutSegmentOnClick": {
									"effect": "linear",
									"speed": 400,
									"size": 5
								}
							},
							"misc": {
								"gradient": {
									"enabled": true,
									"percentage": 100
								}
							}
						});
					   $(".error-json").hide();
				   },
				   error: function(r){
					  $(".error-json").show();
				   }
		  });
		};
		
		// Bar Chart
		$scope.barChart = function(barChartJSON) {
			// Bar Chart Vertical - Slow Moving Assessments
			$.ajax({
				  type : 'get',
				  url : barChartJSON,
				   success : function(r)
					   {	
							var dataBarChart	= r; // Set Data
							
							var margin 	= {top: 20, right: 20, bottom: 50, left: 40},
							width 		= 500 - margin.left - margin.right,
							
							height 		= 350 - margin.top - margin.bottom;
							
							var x 		= d3.scale.ordinal()
											.rangeRoundBands([0, width], .5, .2);
						
							var y 		= d3.scale.linear()
											.domain([0,100])
											.range([height, 0]);
						
							var xAxis 	= d3.svg.axis()
											.scale(x)
											.orient("bottom");
						
							var yAxis 	= d3.svg.axis()
											.scale(y)
											.orient("left");
							
							
							var tip = d3.tip()
										.attr('class', 'd3-tip')
										.offset([-10, 0])
										.html(function(d) {
											return "<strong>SSN:</strong> <span>" + d.ssn + "</span><br/><strong>Time by mins:</strong> <span>" + d.time + "</span>";
										});
						
							var svg 	= d3.select("#bar_chart").append("svg")
											.attr("width", width + margin.left + margin.right)
											.attr("height", height + margin.top + margin.bottom)
											.append("g")
											.attr("transform", "translate(" + margin.left + "," + margin.top + ")")
											.call(tip);
							
							// If there is no data return
							if ( r.length == 0 ) {
										svg.append("text")
										.attr("dx", (width/2) - 20)
										.attr("dy", height/2)
										.text("No Data");
							};
							
							dataBarChart.forEach(function(d) {
								d.time 			= +d.time;
																	
								x.domain(dataBarChart.map(function(d) { return d.vLastNamePlusSsn;}));
								y.domain([0, d3.max(dataBarChart, function(d) { return d.time; })]);
							
								svg.append("g")
								  .attr("class", "x axis")
								  .attr("transform", "translate(0," + height + ")")
								  .call(xAxis);
							
								// Time Label
								svg.append("g")
								  .attr("class", "y axis")
								  .call(yAxis)
								.append("text")
								  .attr("transform", "rotate(-90)")
								  .attr("y", 6)
								  .attr("dy", ".71em")
								  .style("text-anchor", "end")
								  .text("Time by mins");
							
								// Bars
								svg.selectAll(".bar")
								  .data(dataBarChart)
								.enter().append("rect")
								  .attr("class", "bar")
								  .style("fill", function(d) { return d.colors; })
								  .attr("x", function(d) { return x(d.vLastNamePlusSsn); })
								  .attr("width", x.rangeBand())
								  .attr("y", function(d) { return y(d.time); })
								  .attr("height", function(d) { return height - y(d.time); })
	
								  .on('mouseover', tip.show)
								  .on('mouseout', tip.hide)
								  .style('cursor','pointer')
								  .on("click", function(d, i)  { window.location = "assessmentSummary?vaid=" + d.vaid});
							});
							$(".error-json").hide();
					   },
					   error: function(r){
						  $(".error-json").show();
					   }
			});
		};
		
		
		// Horizontal  Bar Chart 
		$scope.barChartHori = function(barChartHoriJSON) {
			 $.ajax({
				 type : 'get',
				  url : barChartHoriJSON, // in here you should put your query 
				  
			   success : function(r)
				   { 	
						
						var dataset = r, 
							  chart,
							  width 		= 900,
							  bar_height 	= 20,
							  height 		= bar_height * dataset.length,
							  x,
							  y,
							  yRangeBand,
							  left_width 	= 100,
							  gap 			= 10,
							  extra_width 	= 20,
							  percent 		= d3.format('.0%');

						  x 	= d3.scale.linear()
									.domain([0, 1 ])
									.range([0, width]);
						 
						  yRangeBand = bar_height + 2 * gap;
						  						  
						  y 	= function(i) { return yRangeBand * i; };
						  
						  var ssnList = dataset.map(function(d, i) { return d.ssn; }); 
						  
						  var tip = d3.tip()
									  .attr('class', 'd3-tip')
									  .offset([-10, 0])
									  .html(function(d, i) {
										return "<strong>SSN:</strong> <span>" + ssnList[i] + "</span>";
						  });
						 
						  chart = d3.select($("#bar_horizontal")[0])
									.append('svg')
									.attr('class', 'chart')
									.attr('width', left_width + width + 40 + extra_width)
									.attr('height', (bar_height + gap * 2) * (dataset.map(function(d) { return d.vLastNamePlusSsn; }).length + 1))
									.append("g")
									.attr("transform", "translate(10, 20)")
									.call(tip);
						  
						  
						// If there is no data return
							if ( dataset.length == 0 ) {
										console.log("NO DATA");
										chart.attr('height', 30)
										.append("g")
										.attr('height', 200)
										.append("text")
										.style('fill', "#000")
										.attr("dx", 500)
										.attr("dy", 20)
										.text("No Data");
							};
							
							
						 
						  chart.selectAll("line")
								.data(x.ticks(d3.max(dataset.map(function(d) { return d.percentages; })  * 10 )))
								.enter().append("line")
								.attr("x1", function(d) { return x(d) + left_width; })
								.attr("x2", function(d) { return x(d) + left_width; })
								.attr("y1", 0)
								.attr("y2", (bar_height + gap * 2) * dataset.map(function(d) { return d.vLastNamePlusSsn; }).length);
						 
						  chart.selectAll(".rule")
								.data(x.ticks(d3.max(dataset.map(function(d) { return d.percentages * 10; }))))
								.enter().append("text")
								.attr("class", "rule")
								.attr("x", function(d) { return x(d) + left_width; })
								.attr("y", 0)
								.attr("dy", -6)
								.attr("text-anchor", "middle")
								.attr("font-size", 10)
								.text(percent);
						  
						  colorsList = dataset.map(function(d, i) { return d.colors; }); 
						  vaidsList = dataset.map(function(d, i) { return d.vaid; }); 
						  
						  chart.selectAll("rect")
								.data(dataset.map(function(d) { return d.percentages; }))
								.enter().append("rect")
								.attr("x", left_width)
								.attr("y", function(d, i) { return y(i) + gap; })
								.attr("width", x)
								.attr("height", bar_height)
								.on('mouseover', tip.show)
								.on('mouseout', tip.hide)
								.style('fill', function(d, i) { return colorsList[i]; })
								.style('cursor','pointer')
								.on("click", function(d, i)  { window.location = "assessmentSummary?vaid=" + vaidsList[i];});
	
						  chart.selectAll("text.score")
								.data(dataset.map(function(d) { return d.percentages; }))
								.enter()
								.append("text")
								.attr("x", function(d) { return x(d) + left_width; })
								.attr("y", function(d, i){ return y(i) + yRangeBand/2; } )
								.attr("dx", -5)
								.attr("dy", ".36em")
								.attr("text-anchor", "end")
								.attr('class', 'score')
								.text(percent);
						  chart.selectAll("text.name")
								.data(dataset)
								.enter()
								.append("text")
								.attr("x", left_width / 2)
								.attr("y", function(d, i){ return y(i) + yRangeBand/2; } )
								.attr("dy", ".36em")
								.attr("text-anchor", "middle")
								.attr('class', 'name')
								.text(function(d, i) { return d.vLastNamePlusSsn; });
						$(".error-json").hide();
				   },
				   error: function(r){
					  $(".error-json").show();
				   }
			});
		};
		
		// Call all charts on load
		$scope.pieChart($scope.pieChartJSON);
		$scope.barChart($scope.barChartJSON);
		$scope.barChartHori($scope.barChartHoriJSON);
});



// JQuery 
$(document).ready(function() {	
	tabsLoad("assessmentDashboard");
    $('#dashboardTab a').click(function (e) {
    	e.preventDefault();
    	$(this).tab('show');
    });
});