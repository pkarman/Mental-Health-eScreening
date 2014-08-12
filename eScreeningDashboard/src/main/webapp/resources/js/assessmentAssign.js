
$(document).ready(function(){
	/*
		var surveyIdArray = new Array();
		var versionNumber = new Array();
		var parentDescription = new Array();
		var childDescription = new Array();
		var categoryIdArray = new Array();
	*/
	
	$.getJSON('services/survey/getAllSurveys', function(data) {
		//var data=$.parseJSON(dataRaw);
    	//console.log(data);
    	var tmp = {};
    	var survArray = {};

        $.each(data, function(i, item) {
            var desc = item.surveySection.name;
            var surv = item.surveyId;
            
            if (!tmp[desc]) {
                tmp[desc] = [];
            }
            if (!survArray[surv]) {
            	survArray[surv] = [];
            }
            tmp[desc].push(item);
            survArray[surv].push(item);
            //console.log("HERE: "+surv);
        });
        
        
        var html = [];
        $.each(tmp, function(key, item) {
            html.push('<input class="parentCheckbox" type="checkbox" value='+""+' />' + key +"<div class='showAll'>Hide</div>"+ '<ul style="list-style: none; background-color: #d3d3d3; margin: 20px 0px 0px 0px;">');
            $.each(item, function(i, val) {
                html.push('<li style="text-align: justify;" class="childText"><input type="checkbox" value='+ val.surveyId+' />' + val.name +  '</li>');
            });
            html.push('</ul> <br /><br />');
        });

        $('#assessmentBox').append(html.join(''));
	 });
	
	
	
	$("#assessmentTakeNext").click(function(){ 
		/*
		var checkedValue='';
		   $(':checkbox:checked').each(function(){
			   checkedValue+=$(this).val()+",";;
		   });
		 */
		
		var surveyIdChecked = new Array();

		$(".childText input:checked").each(function() {
			surveyIdChecked.push($(this).val());
		});
		   
	   //alert(surveyIdChecked);
		   
	   $("#selectedSurveyIdsParam").css("background-color", "red");
	   $("#selectedSurveyIdsParam").val(surveyIdChecked);
	   //alert($("#selectedSurveyIdsParam").val());
	   //$("#assessmentAssignForm").submit();
	   $("#formContainer form").submit();
	   
	   /*
	   setTimeout(function(){
		   var redirectURL = document.location.pathname.split('/').slice(0,2).join('/');
			redirectURL = redirectURL+"/veteran/home";
			//alert(redirectURL);
			window.location.href = redirectURL;
	   }, 2000);
	   */
		
	});
	
	
	
	$("#deselectAll").click(function(){
		$('input:checkbox').removeAttr('checked');
	});
	
});



$(document).ready(function(){
	//setTimeout(function(){
		$('.showAll').live("click", function(){
			
			var $showHidePanel = $(this);
			//alert($('.childText').next());
			$(this).next('ul').slideToggle( "slow", function(){ 
				//alert($(this).text());
				if ($(this).is(':hidden')) {
					$showHidePanel.html("Show");
				} else {
					$showHidePanel.html("Hide");
				}
			});
			//$(this).html("Ok");
		});
		//},1000);
		
		$('.parentCheckbox').live("click", function(){
			if($(this).is(':checked')){
				$(this).next("div").next("ul").children().find(':checkbox').each(function(){ 
					$(this).attr('checked','checked');
					//alert($(this).get(0).tagName);
				});
			}
			else if($(this).not(':checked')){
				$(this).next("div").next("ul").children().find(':checkbox').each(function(){ 
					$(this).removeAttr('checked','checked');
					//alert($(this).get(0).tagName);
				});
			}
		});
	
	setTimeout(function(){
		$('.childText').live("click", function(){
			//alert($(this).text());

		});
		},1000);
});





