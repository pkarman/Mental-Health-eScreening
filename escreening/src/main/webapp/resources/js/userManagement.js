$(document).ready(function(){
	/* Tabs */ 
	tabsLoad("userManagement");

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
    });    
});