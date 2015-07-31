<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags" %>

			<div>
				<form:form modelAttribute="batchCreateFormBean" autocomplete="off" method="post">
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
										<hr class="custom-hr-editVeteran" />
										<div class="row">
											<div class="col-md-6 padding_5"><div class="border_right_gray text-center"><strong>Highlight & Select Screening Modules</strong></div></div>
											<div class="col-md-6 padding_5"><div class="text-center"><strong>Highlight Screening Modules</strong></div></div>
										</div>
										<hr class="custom-hr-editVeteran" />

										<ul class="battery_list" style="padding: 0px">
											<li></li>
											<c:forEach var="item" items="${batteryList}">
												<!--extract the relationship between this battery and program. Means extract all the programs this battery belongs to -->
												<li class="${programsMap[item.stateId]}"><!-- program_1 - Khalid please add value here -->
													<div class="row">
														<div class="col-md-6 padding_5">
															<div class="radio border_right_gray">
																<form:radiobutton path="selectedBatteryId" value="${item.stateId}" label="${item.stateName}" cssClass="battery_${item.stateId} " disabled="${isReadOnly}" data-ref="${dueClinicalReminders}"  required="true" />
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
										<span  class="clear_all"><a href="#" class="btn btn-default btn-xs" role="button">Clear all </a></span>
										<!-- <span  class="reset"><a href="#" class="btn btn-default btn-xs" role="button">Reset </a></span> -->
										<span  class="clear_all_modules pull-right"><a href="#" class="btn btn-default btn-xs" role="button">Clear all Checked Modules</a></span>
										<hr />
									</div>

									<div class="col-md-8">
										<div class="directionsBlock">
											<strong>Directions&nbsp;&nbsp; </strong>
											<input name="name" id="id" type="checkbox" checked="checked" class="directionChecked">
											<label class="custom-label-editVeteran"> Use module for all selected veterans</label>
											&nbsp;&nbsp;&nbsp;
											<input name="name" id="id" type="checkbox" class="directionIndeterminate">
											<label class="custom-label-editVeteran">Remain the same state</label>
										</div>
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
													<td class="tri">
														<div class="checkbox">
															<c:set var="classNameVar" value=" " />
															<c:forEach var="battery" items="${item.batteryList}">
																<c:set var="classNameVar">
																	<c:out value="${classNameVar} battery_${battery.batteryId}"  />
																</c:set>
															</c:forEach>
															<form:checkbox path="selectedSurveyIdList" value="${item.surveyId}" label="${item.name}" cssClass="${classNameVar}" disabled="${isReadOnly}" />
														</div>
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
											<input id="saveButton" name="saveButton" value="Create Assessments" type="submit" class="btn btn-primary ladda-button createAssessmentButton" data-style="expand-right" />
										</c:if>
										<c:if test="${isReadOnly}">
											<input id="saveButton" name="saveButton" value="Create Assessments" type="submit" disabled class="btn btn-primary ladda-button createAssessmentButton" data-style="expand-right" />
										</c:if>
										<input id="cancelButton" name="cancelButton" value="Cancel" type="button" class="btn btn-default btn-default-black"  />
									</div>
								</div>
							</div>
						</div>
					</div>
				</form:form>
				<div class="clear-fix"></div>
			</div>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap/js/bootstrap.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/dashboard/editVeteransAssessmentTail.js" />"></script>
