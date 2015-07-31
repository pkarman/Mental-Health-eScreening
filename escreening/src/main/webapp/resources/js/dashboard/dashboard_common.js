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
        onIdle: function(dialog){
          var outerPageDiv        = "#outerPageDiv";
          var offscreen           = ".offscreen";
          var container           = ".container";
          var modalBackdropLast   = ".modal-backdrop:last";
          
          $(outerPageDiv).attr('aria-hidden', true);
          $(offscreen).attr('aria-hidden', true);
          $(container).attr('aria-hidden', true);

          $(modalBackdropLast).css("z-index", 1051);
          $(this).css("z-index", 1052);
  
          $(this).modal({
              show: true,
              backdrop: 'static',
              keyboard: false
          })
        },
        onCountdown: function( counter ){
          $(this).find("span.countdown").html( counter ); // update the counter
        },
        onResume: function(){
          var outerPageDiv        = "#outerPageDiv";
          var offscreen           = ".offscreen";
          var container           = ".container";
          var modalBackdrop       = ".modal-backdrop";

          $(modalBackdrop).css("z-index", 1040);
          $(outerPageDiv).attr('aria-hidden', false);
          $(offscreen).attr('aria-hidden', false);
          $(this).slideUp(); // hide the warning bar
          
          // Delay to update body 
          setTimeout(function() {
            if ($("div").hasClass('modal-backdrop fade in')) {
              $("body").addClass("modal-open");
            } else {
              $("body").removeClass("modal-open"); 
            }
          }, 1000);
        }
      })
      
      // Logout when click logout button
      $('#btn-logout-timeout').on('click', function(e) {
        window.location = "handleLogoutRequest";
      });
      }
  });
});