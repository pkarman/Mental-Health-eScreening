$(document).ready(function(){
	/*
	$.ajax({
	    type: 'POST',
	    url: 'http://localhost:8080/escreeningdashboard/dashboard/services/usermanagement/verifyclinician',
	    data: '{"accessCode":"1pharmacist","verifyCode":"pharmacist1","ssn":"666324119"}',  
	    //data: '{"accessCode":"1radiologist","verifyCode":"radiologist1","ssn":"123456789"}',
	    //data: '{"accessCode":"bad","verifyCode":"bad","ssn":"666324119"}',
	    //data: '{"accessCode":"bad","verifyCode":"bad","ssn":""}',
	    success: function(data) { alert(JSON.stringify(data)); },
	    contentType: "application/json",
	    dataType: 'json'
	});
	*/
	
	//set the display for the selected tab.
	$("#adminTab").removeClass('menuButtonUnselected');
	$("#adminTab").addClass('menuButtonSelected');
	
	//set the display for the selected sub menu tab.
	$("#userManagementTab").removeClass('subMenuButtonUnselected');
	$("#userManagementTab").addClass('subMenuButtonSelected');
	
	//initialize the user datatable
    $(".jqueryDataTable").dataTable( {
        "bProcessing": false,
        "bServerSide": false,
        "sAjaxSource": "services/GetUsersList",
        "bJQueryUI": true,
        "aoColumns": [
            { "mData": "loginId", 
                "fnRender": function (oObj) {
                    return "<a class='loginIdLink' title='Click login id to edit this user' onClick=\"loadCreateUserForm('edit','"+oObj.aData.userId+"')\">" + oObj.aData.loginId  + "</a>";
                }
            },
            { "mData": "lastName" },
           	{ "mData": "firstName" },
            { "mData": "userStatusName" },
            { "mData": "roleName" }
        ]
    } ); 
    
});
