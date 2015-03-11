<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-1.10.2.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.dataTables.js" />"></script>
    <script src="<c:url value="/resources/js/d3/d3.min.js" />"></script>
    <script src="<c:url value="/resources/js/d3/chart.js" />"></script>

    <link href="<c:url value="/resources/css/jquery/jquery-ui-1.10.3.custom.min.css" />" rel="stylesheet"
          type="text/css"/>
    <link href="<c:url value="/resources/images/valogo.ico" />" rel="icon" type="image/x-icon"/>
    <link href="<c:url value="/resources/images/valogo.ico" />" rel="SHORTCUT ICON" type="image/x-icon"/>
    <!--  <link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard.css" />" rel="stylesheet" type="text/css" /> -->
    <link href="<c:url value="/resources/css/jquery.dataTables.css" />" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/partialpage/menu-partial.css" />" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/veteranSearch.css" />" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/common-ui-styles/circle.css" />" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/common.css" />" rel="stylesheet" type="text/css"/>

    <!-- Bootstrap -->
    <link href="<c:url value="/resources/js/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/partialpage/standardtopofpage-dashboard_new.css" />" rel="stylesheet"
          type="text/css"/>

    <link href="<c:url value="/resources/css/partialpage/assessmentSummary.css" />" rel="stylesheet" type="text/css"/>
    <title>Assessment Summary</title>
</head>
<body>
<div class="graphWrapper hide" id="graph_1"></div>
</body>
<script>
    //$(document).ready(function() {
    // Example Dataset for Structure JSON and Data JSON
    var dataStructure = {
        'ticks': [0, 1, 5, 10, 15, 20, 27],
        'score': 16,
        'footer': '',
        'varId': 1599,
        'title': 'My Depression Score',
        'intervals': {'None': 0, 'Moderately Severe': 15, 'Mild': 5, 'Severe': 20, 'Moderate': 10, 'Minimal': 1},
        'maxXPoint': 27,
        'numberOfMonths': 12
    };
    var dataDataset = {
        '03/06/2012 08:52:38': 16,
        '03/06/2015 08:59:38': 19,
        '01/23/2015 12:51:17': 27,
        '09/23/2014 12:36:48': 5
    };
    // Call graphGenerator
    graphGenerator(dataStructure, dataDataset);
    var svgObjData = svgObj();
    console.log("===>" + svgObjData);
    // HTML Placeholder container to be added in JSP page
    // <div class="graphWrapper" id="graph_1"></div>
    //});
</script>