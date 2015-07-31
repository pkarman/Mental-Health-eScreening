<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html><head><title>VistA Link Test Page</title></head><body>

<h1>VistA Link Test Page</h1>

<form:form id="vistaForm" class="" method="post" modelAttribute="vistaTestFormBean">

<table>
	<tr>
		<td><form:label path="lastFourSsn">Last Four SSN: </form:label></td>
		<td><form:input path="lastFourSsn"/></td>
	</tr>
	<tr>
		<td><form:label path="firstName">First Name: </form:label></td>
		<td><form:input path="firstName"/></td>
	</tr>
	<tr>
		<td><form:label path="middleName">Middle Name: </form:label></td>
		<td><form:input path="middleName"/></td>
	</tr>
	<tr>
		<td><form:label path="lastName">Last Name: </form:label></td>
		<td><form:input path="lastName"/></td>
	</tr>
	<tr>
		<td><form:label path="searchString">Search String: </form:label></td>
		<td><form:input path="searchString"/></td>
	</tr>
	<tr>
		<td><form:label path="ien">IEN: </form:label></td>
		<td><form:input path="ien"/></td>
	</tr>
	<tr>
		<td><form:label path="clinicalReminderIen">Clinical Reminder IEN: </form:label></td>
		<td><form:input path="clinicalReminderIen"/></td>
	</tr>
	<tr>
		<td><form:label path="dialogElementIen">Clinical Reminder Dialog Element IEN: </form:label></td>
		<td><form:input path="dialogElementIen"/></td>
	</tr>
</table>


<table>
	<tr>
		<td>
            <ul style="list-style-type: none; padding-left:0;">
                <form:radiobuttons path="selectedRpcId" items="${rpcList}" element="li" />
            </ul>
        </td>
    </tr>
    <tr>
        <td>
            <input type="submit" name="testRpcButton" value="TestRpc"/>
        </td>
	</tr>
</table>

</form:form>


<div style="font-family: courier">

<pre>
<c:out escapeXml="true" value="${userMessage}"/>
</pre>

</div>

</body></html>
