package gov.va.escreening.vista.request;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by pouncilt on 4/9/14.
 */
public abstract class VistaLinkRequestParameters {

	public VistaLinkRequestParameters() {

	}

	abstract protected void checkRequiredParameters();

	protected void checkRequestParameterInteger(String requestParameterName,
			Integer requestParameterValue, boolean required) {
		if (required && (requestParameterValue == null)) {
			throw new NullPointerException("Request parameter name(" + requestParameterName + ") must be set.");
		}
	}

	protected void checkRequestParameterLong(String requestParameterName,
			Long requestParameterValue, boolean required) {
		if (required && (requestParameterValue == null)) {
			throw new NullPointerException("Request parameter name(" + requestParameterName + ") must be set.");
		}
	}

	protected void checkRequestParameterLong(String requestParameterName,
			Long requestParameterValue, boolean required, long requiredValue) {
		checkRequestParameterLong(requestParameterName, requestParameterValue, required);
		if (requestParameterValue != requiredValue) {
			throw new IllegalStateException(String.format("%s must have a value of %s", requestParameterName, requiredValue));
		}
	}

	protected void checkRequestParameterString(String requestParameterName,
			String requestParameterValue, boolean required) {
		if (required && (requestParameterValue == null || requestParameterValue.trim().length() == 0)) {
			throw new NullPointerException("Request parameter name(" + requestParameterName + ") must be set.");
		}
	}

	protected void checkRequestParameterString(String requestParameterName,
			String requestParameterValue, boolean required,
			List<String> onlyAllowedValues) {
		checkRequestParameterString(requestParameterName, requestParameterValue, required);

		for (String onlyAllowedValue : onlyAllowedValues) {
			if (requestParameterValue.equals(onlyAllowedValue)) {
				return;
			}
			throw new IllegalStateException(String.format("%s must have a value of one of %s", requestParameterName, onlyAllowedValues));
		}
	}

	protected void checkRequestParameterBoolean(String requestParameterName,
			Boolean requestParameterValue, boolean required) {

		if (required && requestParameterValue == null) {
			throw new NullPointerException("Request parameter name(" + requestParameterName + ") must be set.");
		}
	}

	protected void checkRequestParameterBoolean(String requestParameterName,
			Boolean requestParameterValue, boolean required,
			boolean onlyAllowedValue) {

		checkRequestParameterBoolean(requestParameterName, requestParameterValue, required);
		if (!requestParameterValue.equals(onlyAllowedValue)) {
			throw new IllegalStateException(String.format("%s must have a value of %s", requestParameterName, onlyAllowedValue));
		}
	}

	protected void checkRequestParameterDate(String requestParameterName,
			Date requestParameterValue, boolean required) {
		if (required && requestParameterValue == null) {
			throw new NullPointerException("request parameter name(" + requestParameterName + ") must be set.");
		}
	}

	protected void checkRequestParameterArray(String requestParameterName,
			Object[] requestParameterValue, boolean required) {
		if (required && (requestParameterValue == null || requestParameterValue.length < 0)) {
			throw new NullPointerException("request parameter Name(" + requestParameterName + ") must be set.");
		}
	}

	public void checkRequestParameterMap(String requestParameterName,
			Map<String, Object> requestParameterValue, boolean required) {
		if (required && (requestParameterValue == null || requestParameterValue.size() < 0)) {
			throw new NullPointerException("request parameter Name(" + requestParameterName + ") must be set.");
		}
	}
}
