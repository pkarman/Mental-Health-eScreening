package gov.va.escreening.service;

import gov.va.escreening.dto.DropDownObject;

import java.util.List;

public interface DashboardAlertService 
{
	public List<DropDownObject> getAll();
	
	public void updateAlertType(Integer id, String name);
	
	public void deleteAlertType(int id);

}
