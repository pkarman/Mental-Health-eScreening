package gov.va.escreening.dto;

import java.io.Serializable;

public class CprsVerificationRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public String accessCode;
    public String verifyCode;
    public String ssn;
    
	public String getAccessCode() {
		return accessCode;
	}
	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
	
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

}
