$(document).ready(function() {
	// keepAliveURL: 'keepalive.php'
	$.idleTimeout('#idletimeout', '#idletimeout a', {
		idleAfter: 300, // Set the timeout for 5 min
		pollingInterval: 2,
		serverResponseEquals: 'OK',
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
	})
})