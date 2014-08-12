package gov.va.escreening.service;

import gov.va.escreening.entity.SystemProperty;
import gov.va.escreening.repository.SystemPropertyRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SystemPropertyServiceImpl implements SystemPropertyService {

	@Autowired
	private SystemPropertyRepository systemPropertyRepository;
	
    private static final Logger logger = LoggerFactory.getLogger(SystemPropertyServiceImpl.class);
	
	@Override
	public SystemProperty findBySystemPropertyId(Integer systemPropertyId) {

		SystemProperty systemProperty = systemPropertyRepository.findOne(systemPropertyId);
		
		return systemProperty;
	}
}