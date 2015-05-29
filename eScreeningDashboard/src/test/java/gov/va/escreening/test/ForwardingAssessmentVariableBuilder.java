package gov.va.escreening.test;

import static com.google.common.base.Preconditions.checkNotNull;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.test.TestAssessmentVariableBuilder.CustomAvBuilder;
import gov.va.escreening.test.TestAssessmentVariableBuilder.FormulaAvBuilder;
import gov.va.escreening.test.TestAssessmentVariableBuilder.FreeTextAvBuilder;
import gov.va.escreening.test.TestAssessmentVariableBuilder.MatrixAvBuilder;
import gov.va.escreening.test.TestAssessmentVariableBuilder.SelectAvBuilder;
import gov.va.escreening.test.TestAssessmentVariableBuilder.TableQuestionAvBuilder;
import gov.va.escreening.variableresolver.AssessmentVariableDto;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Decorator of {@link AssessmentVariableBuilder}
 * 
 * @author Robin Carnow
 *
 */
public class ForwardingAssessmentVariableBuilder implements AssessmentVariableBuilder{
	private AssessmentVariableBuilder delegate;
	
	protected ForwardingAssessmentVariableBuilder(AssessmentVariableBuilder delegate){
		this.delegate = checkNotNull(delegate);
	}

	protected AssessmentVariableBuilder getRootBuilder(){
		return delegate;
	}
	
	@Override
	public FreeTextAvBuilder addFreeTextAv(Integer id, String measureText, String response){
		return delegate.addFreeTextAv(id, measureText, response);
	}
	
	@Override
	public CustomAvBuilder addCustomAv(int id) {
		return delegate.addCustomAv(id);
	}
	
	@Override
	public SelectAvBuilder addSelectOneAv(@Nullable Integer avId, @Nullable String questionText) {
		return delegate.addSelectOneAv(avId, questionText);
	}

	@Override
	public SelectAvBuilder addSelectMultiAv(@Nullable Integer avId, @Nullable String questionText) {
		return delegate.addSelectMultiAv(avId, questionText);
	}

	@Override
	public MatrixAvBuilder addSelectOneMatrixAv(Integer avId,
			String questionText) {
		return delegate.addSelectOneMatrixAv(avId, questionText);
	}

	@Override
	public MatrixAvBuilder addSelectMultiMatrixAv(Integer avId,
			String questionText) {
		return delegate.addSelectMultiMatrixAv(avId, questionText);
	}

	@Override
	public TableQuestionAvBuilder addTableQuestionAv(Integer avId,
			String questionText, boolean hasNone, Boolean noneResponse) {
		return delegate.addTableQuestionAv(avId, questionText, hasNone, noneResponse);
	}

    @Override
    public FormulaAvBuilder addFormulaAv(int avId, String expression) {
        return delegate.addFormulaAv(avId, expression);
    }
    
    @Override
    public Collection<AssessmentVariableDto> getDTOs(){
        return delegate.getDTOs();
    }
    
    @Override
    public Collection<AssessmentVariable> getVariables() {
        return delegate.getVariables();
    }

    @Override
    public Map<Integer, AssessmentVariableDto> buildDtoMap() {
        return delegate.buildDtoMap();
    }
}
