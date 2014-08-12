package gov.va.escreening.service;

import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.AssessmentStatus;
import gov.va.escreening.repository.AssessmentStatusRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AssessmentStatusServiceImpl implements AssessmentStatusService {

    private AssessmentStatusRepository assessmentStatusRepository;

    @Autowired
    public void setAssessmentStatusRepository(AssessmentStatusRepository assessmentStatusRepository) {
        this.assessmentStatusRepository = assessmentStatusRepository;
    }

    public AssessmentStatusServiceImpl() {
        // Default constructor
    }

    @Transactional(readOnly = true)
    @Override
    public List<DropDownObject> getAssessmentStatusList() {

        List<DropDownObject> dropDownObjectList = new ArrayList<DropDownObject>();

        List<AssessmentStatus> assessmentStatusList = assessmentStatusRepository.findAll();

        for (AssessmentStatus assessmentStatus : assessmentStatusList) {
            dropDownObjectList.add(new DropDownObject(assessmentStatus.getAssessmentStatusId().toString(),
                    assessmentStatus.getName()));
        }

        return dropDownObjectList;
    }
}
