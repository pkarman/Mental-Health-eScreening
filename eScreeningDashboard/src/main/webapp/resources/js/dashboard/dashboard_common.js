$(document).ready(function() {

  var serverTimeout = 1200;
  $.ajax({
    type : 'get',
    url : 'sessionTimeout',
    success : function(r)
      { 
        serverTimeout = r;
        $.idleTimeout('#idletimeout', '#idletimeout a', {
        idleAfter: serverTimeout - 120, // Server Timeout minus 2 mins
        pollingInterval: 2,
        serverResponseEquals: 'OK',
        keepAliveURL: 'requestHrtBeat',
        onTimeout: function(){
          window.location = "handleLogoutRequest";
        },
        onIdle: function(){
          $(this).modal({
              show: true,
              backdrop: 'static',
              keyboard: false
            });
        },
        onCountdown: function( counter ){
          $(this).find("span.countdown").html( counter ); // update the counter
        },
        onResume: function(){
          $(this).slideUp(); // hide the warning bar
        }
      })
      
      // Logout when click logout button
      $('#btn-logout-timeout').on('click', function(e) {
        window.location = "handleLogoutRequest";
      });
      }
  });
        
        
	
});