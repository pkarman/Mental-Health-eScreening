<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<style type="text/css">
/* for print preview styles  */
@page {
	size: auto; /* auto is the initial value */
	/* this affects the margin in the printer settings */
	margin: 17mm 0mm 16.12mm 0mm;
}

.container_main{
	width: 96%;
	padding: 0 2%;
	margin: 0 auto;
}

.templateHeader{ }

.templateFooter{ }

.templateSectionTitle{ font-weight:bold; font-size: 20px; margin: 10px 0; }

.templateSection{ }

.moduleTemplateTitle{ font-weight: bold; }
.moduleTemplateText{ margin: 10px 0 20px; }

.matrixTableHeader{ width:200px; }

.matrixTableData{ width:240px; }

@media print {
	.non-printable {
		display: none;
	}
	marquee {
		-moz-binding: none;
	}
	body {
		overflow: visible !important;
	}
}


</style>



	<div class="container_main">
		<div class="row">
			<div class="col-md-12">
				<div align="right" class="non-printable">
					<button class="btn btn-primary" onClick="window.print();"><span class=" glyphicon glyphicon-print"></span> Print Review Note</button>
				</div>
				
				<br />
				
				<div>${userMessage}</div>
				<div>${errorMessage}</div>
				<div>${assessmentClinicalNotes}</div>
			</div>

		</div>



	</div>
