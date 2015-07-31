package gov.va.escreening.controller;

import gov.va.escreening.domain.ClinicDto;
import gov.va.escreening.domain.UserListDto;
import gov.va.escreening.dto.DataTableObject;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.service.ClinicService;
import gov.va.escreening.service.RoleService;
import gov.va.escreening.service.UserService;
import gov.va.escreening.service.UserStatusService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dashboard")
public class UserManagementRestController {

    private static final Logger logger = LoggerFactory.getLogger(UserManagementRestController.class);

    @Autowired
    private ClinicService clinicService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserStatusService userStatusService;

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/services/GetClinicsList", method = RequestMethod.GET)
    @ResponseBody
    public DataTableObject getClinicDtoList() {
        logger.debug("getClinicDtoList");

        List<ClinicDto> clinicDtoList = clinicService.getClinicDtoList();
        logger.debug("getClinicDtoList.size(): " + clinicDtoList.size());

        DataTableObject<ClinicDto> dataTableObject = new DataTableObject<ClinicDto>();
        dataTableObject.setAaData(clinicDtoList);

        return dataTableObject;
    }

    @RequestMapping(value = "/services/GetRoleList", method = RequestMethod.GET)
    @ResponseBody
    public List<DropDownObject> getRoleDropDownObjectList() {
        logger.debug("getRoleDropDownObjectList");

        return roleService.getRoleDropDownObjects();
    }

    @RequestMapping(value = "/services/GetUserStatusList", method = RequestMethod.GET)
    @ResponseBody
    public List<DropDownObject> getUserStatusDropDownObjectList() {
        logger.debug("getUserStatusDropDownObjectList");

        return userStatusService.getUserStatusDropDownObjects();
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/services/GetUsersList", method = RequestMethod.GET)
    @ResponseBody
    public DataTableObject getUserListDtoList() {
        logger.debug("getUserListDtoList");

        List<UserListDto> userListDtoList = userService.getUserListDtoList();

        logger.debug("getUserListDtoList.size(): " + userListDtoList.size());

        DataTableObject<UserListDto> dataTableObject = new DataTableObject<UserListDto>();
        dataTableObject.setAaData(userListDtoList);

        return dataTableObject;
    }

}
