function tabsLoad(page){
$("div.content ul li a")
         .mouseover(function() {
             $(this).addClass('mouseover');
             alert("mouse over");
         })
         .mouseout(function() {
             $(this).removeClass('mouseover');
             alert("mouse out");
         });

        $.getJSON("allowedTabs", function(data) {
    		  var tabToShow;
    		  $.each(data, function(key, val) {

    		    		if(val==true) {
    		    			
    		    			if(key=="home"){
    		    				str="Home";
    		    				if(page==key){
    		    					tabToShow = $("ul#tabs").append("<li class='active'>" +
        									"<a  href='home' class='' id='homeTab' title='Click to open home screen'>"+str+"</a>"
        								+"</li>");
    		    				}else{
	    		    				tabToShow = $("ul#tabs").append("<li class=''>" +
	    									"<a  href='home' id='homeTab' title='Click to open home screen'>"+str+"</a>"
	    								+"</li>");
    		    				}
    		    			}
    		    			
    		    			else if(key=="assessmentDashboard"){
    		    				str="Dashboard";
    		    				if(page==key){
    		    					tabToShow = $("ul#tabs").append("<li class='active'>" +
	    									"<a href='assessmentDashboard' class='' id='dashboardTab' title='Click to open dashboard screen'>"+str+"</a>"
	    								+"</li>");
    		    				}else{
	    		    				tabToShow = $("ul#tabs").append("<li class=''>" +
	    									"<a href='assessmentDashboard' id='dashboardTab' title='Click to open dashboard screen'>"+str+"</a>"
	    								+"</li>");
    		    				}
    		    			}
    		    			
    		    			else if(key=="userManagement"){
    		    				str="Admin";
    		    				if(page==key){
    		    					tabToShow = $("ul#tabs").append("<li class='active'>" +
	    									"<a href='userListView' class='' id='adminTab' title='Click to open user management screen'>"+str+"</a>"
	    								+"</li>");
    		    				}else{
	    		    				tabToShow = $("ul#tabs").append("<li class=''>" +
	    									"<a href='userListView' id='adminTab' title='Click to open user management screen'>"+str+"</a>"
	    								+"</li>");
    		    				}
    		    			}
    		    			
    		    			else if(key=="formsEditor"){
    		    				str="Editors";
    		    				if(page==key){
    		    					tabToShow = $("ul#tabs").append("<li class='active'>" +
	    									"<a href='formsEditor' class='' id='formsEditorTab' title='Click to open forms editor screen'>"+str+"</a>"
	    								+"</li>");	
    		    				}else{
	    		    				tabToShow = $("ul#tabs").append("<li class=''>" +
	    									"<a href='formsEditor' id='formsEditorTab' title='Click to open forms editor screen'>"+str+"</a>"
	    								+"</li>");
    		    				}
    		    			}
    		    			
    		    			else if(key=="exportData"){
    		    				str="Export Data";
    		    				if(page==key){
    		    					tabToShow = $("ul#tabs").append("<li class='active'>" +
	    									"<a href='exportData' class='' id='exportDataTab' title='Click to open export data screen'>"+str+"</a>"
	    								+"</li>");
    		    				}else{
	    		    				tabToShow = $("ul#tabs").append("<li class=''>" +
	    									"<a href='exportData' id='exportDataTab' title='Click to open export data screen'>"+str+"</a>"
	    								+"</li>");
    		    				}
    		    			}
    		    			
    		    			else if(key=="assessmentReport"){
    		    				str="Assessment Search";
    		    				if(page==key){
    		    					tabToShow = $("ul#tabs").append("<li class='active'>" +
	    									"<a href='assessmentReport' class='' id='assessmentReportTab' title='Click to open assessment report screen'>"+str+"</a>"
	    								+"</li>");
    		    				}else{
	    		    				tabToShow = $("ul#tabs").append("<li class=''>" +
	    									"<a href='assessmentReport' id='assessmentReportTab' title='Click to open assessment report screen'>"+str+"</a>"
	    								+"</li>");
    		    				}
    		    			}
    		    			
    		    			else if(key=="veteranSearch"){
    		    				str="Veteran Search";
    		    				if(page==key){
    		    					tabToShow = $("ul#tabs").append("<li class='active'>" +
	    									"<a href='veteranSearch' class='' id='veteranSearchTab' title='Click to open veteran search screen'>"+str+"</a>"
	    								+"</li>");
    		    				}else{
	    		    				tabToShow = $("ul#tabs").append("<li class=''>" +
	    									"<a href='veteranSearch' id='veteranSearchTab' title='Click to open veteran search screen'>"+str+"</a>"
	    								+"</li>");
    		    				}
    		    			}

    		    			else if(key=="myAccount"){
    		    				str="My Account";
    		    				if(page == key){
    		    					tabToShow = $("ul#tabs").append("<li class='active'>" +
        									"<a href='myAccount' class='' id='myAccountTab' title='Click to open myAccount screen'>"+str+"</a>"
        								+"</li>");
    		    				}else{
    		    					tabToShow = $("ul#tabs").append("<li class=''>" +
        									"<a href='myAccount'  id='myAccountTab' title='Click to open myAccount screen'>"+str+"</a>"
        								+"</li>");
    		    				}
    		    			}
    		    			else if(key=="createBattery"){
    		    				str="Create Battery";
    		    				if(page == key){
    		    					tabToShow = $("ul#tabs").append("<li class='active'>" +
        									"<a href='selectVeteran' class='' id='createBatteryTab' title='Click to open Create Battery screen'>"+str+"</a>"
        								+"</li>");
    		    				}else{
    		    					tabToShow = $("ul#tabs").append("<li class=''>" +
        									"<a href='selectVeteran'  id='createBatteryTab' title='Click to open Create Battery screen'>"+str+"</a>"
        								+"</li>");
    		    				}
    		    			}
    		    			else if(key=="programManagement"){
    		    				str="Programs";
    		    				if(page == key){
    		    					tabToShow = $("ul#tabs").append("<li class='active'>" +
        									"<a href='programListView' class='' id='programManagementTab' title='Click to open Program screen'>"+str+"</a>"
        								+"</li>");
    		    				}else{
    		    					tabToShow = $("ul#tabs").append("<li class=''>" +
        									"<a href='programListView'  id='programManagementTab' title='Click to open Program screen'>"+str+"</a>"
        								+"</li>");
    		    				}
    		    			}
    		    			else if(key=="systemConfiguration"){
    		    				str="System Configuration";
    		    				if(page == key){
    		    					tabToShow = $("ul#tabs").append("<li class='active'>" +
        									"<a href='importData' class='' id='systemConfigurationTab' title='Click to open System Configuration screen'>"+str+"</a>"
        								+"</li>");
    		    				}else{
    		    					tabToShow = $("ul#tabs").append("<li class=''>" +
        									"<a href='importData'  id='systemConfigurationTab' title='Click to open System Configuration screen'>"+str+"</a>"
        								+"</li>");
    		    				}
    		    			}
    		    		}   		    		
    		  });
    	});
}