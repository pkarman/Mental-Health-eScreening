<div class="container" style="padding:0px;">
 <footer>
    	<div class="footer_container">
            <div class="container bg_transparent">
                <div class="row">
                    <div class="col-md-12">
                        U.S. Department of Veterans Affairs | eScreening Program (Ver 1.0)
                    </div>
                </div>
            </div>
    	</div>
    </footer>
</div>


<div class="modal fade" id="idletimeout" title="Your session is about to expire!" tabindex="-1" role="dialog" aria-labelledby="idletimeout" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">Session Time out</h4>
      </div>
      <div class="modal-body">
        <p class="sessionTimeOutMessage">You will be logged off in <span class="countdown"><!-- countdown place holder --></span>&nbsp;seconds due to inactivity.</p>
	
      </div>
      <div class="modal-footer">
      	<div class="text-center">
          <a id="idletimeout-resume" href="#"  data-dismiss="modal" class="btn btn-primary">Continue using this page</a>
          <a href="handleLogoutRequest" class="btn btn-default" id="btn-logout-timeout">Logout</a>
        </div>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script src="resources/js/bootstrap/js/bootstrap.js" type="text/javascript"></script>
<script src="resources/js/lib/jquery.idletimer.js" type="text/javascript"></script>
<script src="resources/js/lib/jquery.idletimeout.js" type="text/javascript"></script>
<script src="resources/js/dashboard/dashboard_common.js" type="text/javascript"></script>