package gov.va.escreening.controller.dashboard;

import gov.va.escreening.controller.RestController;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.service.DashboardAlertService;
import gov.va.escreening.webservice.Response;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dashboard")
public class DashboardAlertTypeController extends RestController {

	@Autowired
	DashboardAlertService alertTypeSvc;

	@RequestMapping(value = "/alertListView", method = RequestMethod.GET)
	public String setup() {
		return "systemTab/alertListView";
	}

	@RequestMapping(value = "/alertEditView", method = RequestMethod.GET)
	public String setupEdit() {
		return "systemTab/alertEditView";
	}

	@RequestMapping(value = "/alertTypes", method = RequestMethod.GET)
	@ResponseBody
	public Response<List<DropDownObject>> getAlertTypes() {
		return successResponse(alertTypeSvc.getAll());

	}

	@RequestMapping(value = "/alertTypes/delete", method = RequestMethod.POST)
	@ResponseBody
	public Response<Boolean> delete(@RequestParam int id) {
		alertTypeSvc.deleteAlertType(id);
		return successResponse(true);
	}

	@RequestMapping(value = "/alertTypes/update", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Response<String> update(@RequestBody DropDownObject req) {
		try {
			Integer id = null;
			if (req.getStateId() != null && !req.getStateId().isEmpty()) {
				try {
					id = Integer.parseInt(req.getStateId());
				} catch (Exception ex) {

				}
			}
			alertTypeSvc.updateAlertType(id, req.getStateName());
			return successResponse("OK");
		} catch (Exception ex) {
			return failResponse(ex.getMessage());
		}
	}

	@RequestMapping(value = "/alertTypes/get", method = RequestMethod.GET)
	@ResponseBody
	public Response<DropDownObject> get(@RequestParam int id) {
		return successResponse(alertTypeSvc.getAlertType(id));
	}
}
