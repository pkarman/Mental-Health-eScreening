package gov.va.escreening.service.export;

import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.ExportdataFilterOptions;
import gov.va.escreening.repository.ExportDataFilterOptionsRepository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ExportDataFilterOptionsServiceImpl implements ExportDataFilterOptionsService {

    private static final Logger logger = LoggerFactory.getLogger(ExportDataFilterOptionsServiceImpl.class);

    @Autowired
    private ExportDataFilterOptionsRepository exportDataFilterOptionsRepository;
	
    @Transactional(readOnly = true)
    @Override
    public List<DropDownObject> getExportDataFilterOptions() {
    	logger.trace("getExportDataFilterOptions()");
    	
    	List<ExportdataFilterOptions> options = exportDataFilterOptionsRepository.findAll();

        List<DropDownObject> dropDownList = new ArrayList<DropDownObject>();
        for (ExportdataFilterOptions option : options) {
            DropDownObject dropDown = new DropDownObject(String.valueOf(option.getExportdataFilterOptionsId()), option.getName());
            dropDownList.add(dropDown);
        }

        return dropDownList;
    }
}