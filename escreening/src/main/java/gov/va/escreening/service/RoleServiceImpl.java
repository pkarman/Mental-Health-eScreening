package gov.va.escreening.service;

import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.Role;
import gov.va.escreening.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getRoles() {
        return roleRepository.getRoles();
    }

    @Transactional(readOnly = true)
    @Override
    public List<DropDownObject> getRoleDropDownObjects() {
        List<Role> roleList = roleRepository.findAll();

        List<DropDownObject> dropDownList = new ArrayList<DropDownObject>();

        for (Role role : roleList) {
            DropDownObject dropDown = new DropDownObject(String.valueOf(role.getRoleId()), role.getName());
            dropDownList.add(dropDown);
        }

        return dropDownList;

    }
}
