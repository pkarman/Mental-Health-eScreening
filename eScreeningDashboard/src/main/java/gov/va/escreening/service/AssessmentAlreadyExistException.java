package gov.va.escreening.service;

/**
 * This exception is thrown when trying to create an assessment for a program and there
 * is already a clean assessment for the same program.
 * @author mzhu
 *
 */
public class AssessmentAlreadyExistException extends Exception 
{
	int assessmentId;
	int programId;
	public AssessmentAlreadyExistException(int assessmentId, int programId) {
		super();
		this.assessmentId = assessmentId;
		this.programId = programId;
	}

}
