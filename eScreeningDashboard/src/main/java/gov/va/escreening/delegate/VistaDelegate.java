package gov.va.escreening.delegate;

import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.vista.VistaLinkClientException;

public interface VistaDelegate {

    /**
     * Saves the Veteran's assessment to VistA.
     * @param veteranAssessmentId
     * @param escreenUser
     * @return 
     */
	void saveVeteranAssessmentToVista(int veteranAssessmentId,
			EscreenUser escreenUser) throws VistaLinkClientException;
}
