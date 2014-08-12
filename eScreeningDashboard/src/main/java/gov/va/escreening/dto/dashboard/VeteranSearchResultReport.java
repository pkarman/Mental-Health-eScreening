package gov.va.escreening.dto.dashboard;

import java.util.List;

public class VeteranSearchResultReport {
	List<VeteranSearchResult> veteranSearchResults;
	Integer totalNumRowsFound;
	
	public List<VeteranSearchResult> getVeteranSearchResults() {
		return veteranSearchResults;
	}
	public void setVeteranSearchResults(
			List<VeteranSearchResult> veteranSearchResults) {
		this.veteranSearchResults = veteranSearchResults;
	}
	
	public Integer getTotalNumRowsFound() {
		return totalNumRowsFound;
	}
	public void setTotalNumRowsFound(Integer totalNumRowsFound) {
		this.totalNumRowsFound = totalNumRowsFound;
	}
	
	
}
