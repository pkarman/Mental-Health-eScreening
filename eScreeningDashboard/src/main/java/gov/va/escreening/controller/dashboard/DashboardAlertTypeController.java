package gov.va.escreening.controller.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gov.va.escreening.controller.RestController;
import gov.va.escreening.entity.DashboardAlertType;
import gov.va.escreening.repository.DashboardAlertRepository;
import gov.va.escreening.webservice.Response;
import gov.va.escreening.webservice.ResponseStatus;

@Controller
@RequestMapping(value = "/dashboard/alertTypes")
public class DashboardAlertTypeController extends RestController
{
	@Autowired
	DashboardAlertRepository alertTypeRepo;
	
	@RequestMapping(value = "/alertListView", method = RequestMethod.GET)
	public String setup()
	{
		return "systemTab/alertListView";
	}
	
	@RequestMapping(value="/alertEditView", method = RequestMethod.GET)
	public String setupEdit()
	{
		return "systemTab/alertEditView";
	}

	@RequestMapping(value="/", method = RequestMethod.GET)
	public Response<List<DashboardAlertType>> getAlertTypes()
	{
		//return successResponse(alertTypeRepo.findAll());
		return null;
	}
}
