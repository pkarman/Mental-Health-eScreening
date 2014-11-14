$(document).ready(function(e) {
  function loadData(){
    $.ajax({
      type : 'get',
      dataType: 'json',
      url : 'welcome_msg',
      success : function(data)
        {
          $("#assessmentWelcome").html(data);
        },
      error: function()
        {
          $("#assessmentWelcome").html("Loading Error ... <a href='#' id='refresh'>Refresh</a>");
        }
    });
  }
  
  loadData();

  $(document).on('click', '#refresh', function(e) { 
      loadData();
      e.preventDefault();
  });
});