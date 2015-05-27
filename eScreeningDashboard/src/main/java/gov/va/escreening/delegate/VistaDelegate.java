package gov.va.escreening.delegate;

import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.vista.VistaLinkClientException;

public interface VistaDelegate {

	/**
	 * Saves the Veteran's assessment to VistA.
	 * @param ctxt
	 * @return
	 */
	void saveVeteranAssessmentToVista(SaveToVistaContext ctxt) throws VistaLinkClientException;
}
