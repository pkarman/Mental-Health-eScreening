package gov.va.escreening.variableresolver;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.AssessmentVariableType;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.repository.VeteranAssessmentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TimeSeriesAssessmentVariableResolverImpl implements
		TimeSeriesAssessmentVariableResolver {
	private static final Logger logger = LoggerFactory.getLogger(TimeSeriesAssessmentVariableResolverImpl.class);
    
	@Autowired
	VeteranAssessmentRepository veteranAssessmentRepo;

	@Autowired
	FormulaAssessmentVariableResolver formulaResolver;

	@Autowired
	private AssessmentVariableDtoFactory assessmentVariableFactory;

	@Override
	public AssessmentVariableDto resolveAssessmentVariable(
			AssessmentVariable assessmentVariable, Integer veteranId) {
		AssessmentVariableDto dto = new AssessmentVariableDto(
				assessmentVariable.getAssessmentVariableId(), "var"
						+ assessmentVariable.getAssessmentVariableId(), null,
				null, "", "", null, null, null, 0, 0, null);
		AssessmentVariable variable = new AssessmentVariable();
		variable.setAssessmentVariableTypeId(new AssessmentVariableType(
				AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA));
		variable.setFormulaTemplate(assessmentVariable.getFormulaTemplate());
		variable.setAssessmentVariableId(assessmentVariable
				.getAssessmentVariableId());
		variable.setAssessmentVarChildrenList(assessmentVariable
				.getAssessmentVarChildrenList());

		List<AssessmentVarChildren> childrenList = assessmentVariable
				.getAssessmentVarChildrenList();
		List<AssessmentVariable> varList = new ArrayList<AssessmentVariable>();

		for (AssessmentVarChildren c : childrenList) {
			if (c.getVariableParent().getAssessmentVariableId()
					.equals(assessmentVariable.getAssessmentVariableId())) {
				varList.add(c.getVariableChild());
			}
		}

		Map<Integer, AssessmentVariable> measureAnswerHash = assessmentVariableFactory
				.createMeasureAnswerTypeHash(varList);

		List<VeteranAssessment> assessmentList = veteranAssessmentRepo
				.findByVeteranId(veteranId);

		Collections.sort(assessmentList, new Comparator<VeteranAssessment>()
		{

			@Override
			public int compare(VeteranAssessment va1, VeteranAssessment va2) {
				
				return va1.getDateCompleted().compareTo(va2.getDateCompleted());
			}
			
		});
		
		SortedMap<Date, String> timeSeries = new TreeMap<Date, String>();
		
		for (VeteranAssessment va : assessmentList) {
			try {
				AssessmentVariableDto result = formulaResolver
						.resolveAssessmentVariable(assessmentVariable,
								va.getVeteranAssessmentId(), measureAnswerHash);
				if (result.getValue() != null) {
					timeSeries.put(va.getDateCompleted(),  result.getValue());
				}
			} catch (Exception ex) {// do nothing

			}
		}
		
		dto.setTimeSeries(timeSeries);
		logger.info("Time Series assessment var " + dto.toString());
		
		return dto;
	}

}
