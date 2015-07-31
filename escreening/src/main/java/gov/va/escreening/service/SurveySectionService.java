package gov.va.escreening.service;



import gov.va.escreening.dto.editors.SurveySectionInfo;

import java.util.List;

public interface SurveySectionService {
    
    List<SurveySectionInfo> getSurveySectionList();
    SurveySectionInfo create(SurveySectionInfo surveySectionInfo);
    SurveySectionInfo getSurveySectionItem(int sectionId);
    SurveySectionInfo update(SurveySectionInfo surveySectionInfo);
	void delete(Integer surveySectionId);
}
