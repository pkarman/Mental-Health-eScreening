<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags" %>

			<div>
				<form:form modelAttribute="editVeteranAssessmentFormBean" autocomplete="off" method="post">
                    <form:hidden path="veteranId"/>
                    <form:hidden path="veteranAssessmentId"/>
                    <form:hidden path="selectedProgramId"/>
                    <form:hidden path="selectedClinicId"/>
                    <form:hidden path="selectedNoteTitleId"/>
                    <form:hidden path="selectedClinicianId"/>

                    <div class="row">
						<div class="col-md-12">
							<h4>Please Select Screening Modules for the Veteran</h4>
							<div class="border_box">
								<div class="row">
									<div class="col-md-4">
										<h4>Battery</h4>
										<div class="radio_block">
											<c:forEach var="item" items="${batteryList}">

											</c:forEach>
											<form:errors path="selectedBatteryId" cssClass="help-inline"/>
										</div>
										<hr />
										<div class="row">
											<div class="col-md-6 padding_5"><div class="border_right_gray text-center"><strong>Highlight & Select Screening Modules</strong></div></div>
											<div class="col-md-6 padding_5"><div class="text-center"><strong>Highlight Screening Modules</strong></div></div>
										</div>
										<hr />
										<ul class="battery_list" style="padding: 0px">
											<li>
											</li>
											<c:forEach var="item" items="${batteryList}">
												<!--extract the relationship between this battery and program. Means extract all the programs this battery belongs to -->
												<li class="${programsMap[item.stateId]}"><!-- program_1 - Khalid please add value here -->
													<div class="row">
														<div class="col-md-6 padding_5">
															<div class="radio border_right_gray">
																<form:radiobutton path="selectedBatteryId" value="${item.stateId}" label="${item.stateName}" cssClass="battery_${item.stateId}" disabled="${isReadOnly}" />
															</div>
														</div>
														<div class="col-md-6 padding_5">
															<span class="battery_${item.stateId} battery_highlight_${item.stateId}"><a href="#" class="btn btn-warning btn-block highlight_module_item" role="button"><span class="glyphicon glyphicon-bookmark"></span> Highlight <c:out value="${item.stateName}" /></a></span>
														</div>
													</div>
												</li>
											</c:forEach>
										</ul>
										<hr />
										<span  class="clear_all"><a href="#" class="btn btn-default btn-xs" role="button">Clear all </a></span> <span  class="reset"><a href="#" class="btn btn-default btn-xs" role="button">Reset </a></span>
										<span  class="clear_all_modules pull-right"><a href="#" class="btn btn-default btn-xs" role="button">Clear all Checked Modules</a></span>
										<hr />
									</div>
									<div class="col-md-8">
										<table class="table table-striped table-hover module_list" summary="Screening Modules Table">
											<thead>
											<tr>
												<th scope="col" class="col-md-6">Screening Module</th>
												<th scope="col" class="col-md-3">Description</th>
												<th scope="col" class="col-md-3">Notes</th>
											</tr>
											</thead>
											<tbody>
											<c:forEach var="item" items="${surveyList}">
												<tr>
													<td>
														<c:set var="classNameVar" value=" " />
														<c:forEach var="battery" items="${item.batteryList}">
															<c:set var="classNameVar">
																<c:out value="${classNameVar} battery_${battery.batteryId}"  />
															</c:set>
														</c:forEach>
														<form:checkbox path="selectedSurveyIdList" value="${item.surveyId}" label="${item.name}" cssClass="${classNameVar}" disabled="${isReadOnly}" />
													</td>
													<td><c:out value="${item.description}" /></td>
													<td><c:out value="${item.note}" /></td>
												</tr>
											</c:forEach>
											</tbody>
										</table>
									</div>
								</div>

								<div class="row">
									<div class="col-md-8 col-md-offset-4 text-right ">
										<c:if test="${not isReadOnly}">
											<input id="saveButton" name="saveButton" value="Save" type="submit" class="btn btn-primary" />
										</c:if>
										<c:if test="${isReadOnly}">
											<input id="saveButton" name="saveButton" value="Save" type="submit" disabled class="btn btn-primary" />
										</c:if>
										<input id="cancelButton" name="cancelButton" value="Cancel" type="submit" class="btn btn-default" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</form:form>
				<div class="clear-fix"></div>
			</div>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/dashboard/editVeteranAssessmentTail.js" />"></script>
