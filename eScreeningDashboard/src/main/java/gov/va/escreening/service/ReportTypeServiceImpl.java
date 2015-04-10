package gov.va.escreening.service;

import gov.va.escreening.dto.ReportTypeDTO;
import gov.va.escreening.entity.ReportType;
import gov.va.escreening.repository.ReportTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kliu on 2/22/15.
 */
@Transactional
@Service
public class ReportTypeServiceImpl implements ReportTypeService {

    @Autowired
    private ReportTypeRepository reportTypeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReportTypeDTO> getAllReportTypes() {

        List<ReportType> allTypes = reportTypeRepository.findAll();

        List<ReportTypeDTO> allReportTypeDTOs = new ArrayList<>(allTypes.size());

        for (ReportType reportType : allTypes){
            ReportTypeDTO dto = new ReportTypeDTO();
            dto.setReportName(reportType.getName());
            dto.setReportTypeId(reportType.getReportTypeId());
            allReportTypeDTOs.add(dto);
        }
        return allReportTypeDTOs;
    }
}
