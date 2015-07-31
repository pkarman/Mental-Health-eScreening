package gov.va.escreening.service;

import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.UserStatus;
import gov.va.escreening.repository.UserStatusRepository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserStatusServiceImpl implements UserStatusService {

    @Autowired
    private UserStatusRepository userStatusRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserStatusServiceImpl.class);

    @Override
    public List<UserStatus> getUserStatusList() {
        logger.trace("getUserStatusList()");
        return userStatusRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<DropDownObject> getUserStatusDropDownObjects() {
        logger.trace("getUserStatusDropDownObjects()");

        List<DropDownObject> dropDownList = new ArrayList<DropDownObject>();

        List<UserStatus> userStatusList = userStatusRepository.findAll();

        for (UserStatus status : userStatusList) {
            DropDownObject dropDown = new DropDownObject(String.valueOf(status.getUserStatusId()), status.getName());
            dropDownList.add(dropDown);
        }

        return dropDownList;
    }
}
