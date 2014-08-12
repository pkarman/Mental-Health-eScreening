
$(document).ready(function() {

	// Configure the data table.
	$("#userListDataTable").dataTable( {
		"bProcessing": false,
		"bServerSide": false,
		"bJQueryUI": true,
		"sAjaxSource": "services/GetUsersList",
		"aoColumns": [
			{ "mData": "loginId", "mRender": function(data, type, full) { return '<a href="userEditView?uid='+full.userId+'"><span title="Eit ' + data + '" Name="Edit for ' + data + '">' + data + '</span></a>'; }},
			{ "mData": "lastName" },
			{ "mData": "firstName" },
			{ "mData": "userStatusName" },
			{ "mData": "roleName" }]
	} ); 
});
