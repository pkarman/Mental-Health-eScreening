package gov.va.escreening.service;

import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.DashboardAlert;
import gov.va.escreening.entity.DashboardAlertType;
import gov.va.escreening.repository.DashboardAlertRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardAlertServiceImpl implements DashboardAlertService
{

	@Autowired
	DashboardAlertRepository alertRepo;
	
	@Override
	public List<DropDownObject> getAll() {
		List<DashboardAlert> all = alertRepo.findAll();
		
		List<DropDownObject> result = new ArrayList<>(all.size());
		for(DashboardAlert a : all)
		{
			result.add(new DropDownObject(a.getDashboardAlertId().toString(), 
					a.getName()));
		}
		return result;
	}

	@Override
	public void updateAlertType(Integer id, String name) {
		if(id == null)
		{
			DashboardAlert a = new DashboardAlert();
			a.setName(name);
			a.setMessage(name);
			a.setDashboardAlertType(new DashboardAlertType(1));
			a.setDateCreated(Calendar.getInstance().getTime());
			alertRepo.create(a);
		}
		else
		{
			DashboardAlert a = alertRepo.findOne(id);
			if(a != null)
			{
				if(a.getName() == null || !a.getName().equals(name))
				{
					a.setName(name);
					a.setMessage(name);
					alertRepo.update(a);
				}
			}
			else
			{
				throw new IllegalArgumentException("Dashboard Alert does not exist");
			}
		}
		
	}

	@Override
	public void deleteAlertType(int id) {
		alertRepo.deleteById(id);
	}

}
