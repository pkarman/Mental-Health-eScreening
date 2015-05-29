<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container" style="padding:0px;">
 <footer>
    	<div class="footer_container">
            <div class="container bg_transparent">
                <div class="row">
                    <div class="col-md-8">
                        U.S. Department of Veterans Affairs | eScreening Program (Ver 1.0)
                    </div>
                    <div class="col-md-4 text-right">
						<c:if test="${pageContext.request.requestURI.endsWith('/assessmentLogin.jsp')}">
							<div class="change_program_block"><a href="tabletConfiguration"><span id="tabletProgramBlock">[NO PROGRAM]</span> (Change)</a></div>
						</c:if>

                    </div>
                </div>
            </div>
    	</div>
    </footer>
</div>