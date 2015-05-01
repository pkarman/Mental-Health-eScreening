package gov.va.escreening.template.function;

import freemarker.ext.beans.StringModel;
import freemarker.template.TemplateModelException;


public class TemplateFunction {

	/**
	 * Extracts the wrapped object if it exists and is of the given type.
	 * @param tableObj the FreeMarker-automatically wrapped object  
	 * @param clazz the type of the wrapped object we are extracting
	 * @return the unwrapped object or null
	 * @throws TemplateModelException if the wrapped object is not an instance of the given class
	 */
	protected <T> T unwrapParam(Object tableObj, Class<T> clazz) throws TemplateModelException{
		
		if(!(tableObj instanceof StringModel)){
			throw new TemplateModelException("Must be a table AssessmentVariableDto");
		}
		
		Object unwrapped = ((StringModel)tableObj).getWrappedObject();
		
		if(unwrapped == null)
			return null;
		
		if(! (clazz.isInstance(unwrapped))){
			throw new TemplateModelException("Must be a table AssessmentVariableDto");
		}
		
		return clazz.cast(unwrapped);
	}
}
