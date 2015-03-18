package gov.va.escreening.controller.dashboard;

import gov.va.escreening.controller.RestController;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.service.DashboardAlertService;
import gov.va.escreening.webservice.Response;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dashboard/alertTypes")
public class DashboardAlertTypeController extends RestController
{
	
	@Autowired
	DashboardAlertService alertTypeSvc;
	
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
	@ResponseBody
	public Response<List<DropDownObject>> getAlertTypes()
	{
		return successResponse(alertTypeSvc.getAll());
		
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> delete(@RequestParam int id)
	{
		alertTypeSvc.deleteAlertType(id);
		return successResponse(true);
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Response<String> update(@RequestParam(required=false) Integer id, 
			@RequestParam String name)
	{
		try
		{
		alertTypeSvc.updateAlertType(id, name);
		return successResponse("OK");
		}catch (Exception ex)
		{
			return failResponse(ex.getMessage());
		}
	}
}
