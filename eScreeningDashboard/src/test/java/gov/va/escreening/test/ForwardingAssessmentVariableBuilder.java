package gov.va.escreening.test;

import static com.google.common.base.Preconditions.checkNotNull;
import gov.va.escreening.test.TestAssessmentVariableBuilder.CustomAvBuilder;
import gov.va.escreening.test.TestAssessmentVariableBuilder.FreeTextAvBuilder;
import gov.va.escreening.variableresolver.AssessmentVariableDto;

import java.util.List;

/**
 * Decorator of {@link AssessmentVariableBuilder}
 * 
 * @author Robin Carnow
 *
 */
public class ForwardingAssessmentVariableBuilder implements AssessmentVariableBuilder{
	private AssessmentVariableBuilder delegate;
	
	ForwardingAssessmentVariableBuilder(AssessmentVariableBuilder delegate){
		this.delegate = checkNotNull(delegate);
	}
	
	@Override
	public FreeTextAvBuilder addFreeTextAV(int id, String measureText, String response){
		return delegate.addFreeTextAV(id, measureText, response);
	}
	
	@Override
	public List<AssessmentVariableDto> getDTOs(){
		return delegate.getDTOs();
	}

	@Override
	public CustomAvBuilder addCustomAV(int id) {
		return delegate.addCustomAV(id);
	}
	
}
