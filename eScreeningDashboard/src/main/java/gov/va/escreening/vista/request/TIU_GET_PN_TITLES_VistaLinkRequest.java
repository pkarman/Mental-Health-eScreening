package gov.va.escreening.vista.request;

import gov.va.escreening.vista.dto.VistaNoteTitle;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pouncilt on 4/10/14.
 */
public class TIU_GET_PN_TITLES_VistaLinkRequest extends VistaLinkBaseRequest implements VistaLinkRequest {
	private static final Logger logger = LoggerFactory.getLogger(TIU_GET_PN_TITLES_VistaLinkRequest.class);
	private VistaLinkConnection connection = null;
	private RpcRequest request = null;

	public TIU_GET_PN_TITLES_VistaLinkRequest(VistaLinkRequestContext<TIU_GET_PN_TITLES_RequestParameters> requestContext) {
		this.request = requestContext.getRpcRequest();
		this.connection = requestContext.getVistaLinkConnection();
	}

	@Override
	public VistaNoteTitle[] sendRequest() throws VistaLinkRequestException {
		VistaNoteTitle[] vistaNoteTitles = null;

		try {
			vistaNoteTitles = this.getProgressNoteTitles();
		} catch (Exception e) {
			throw new VistaLinkRequestException(e);
		}

		return vistaNoteTitles;
	}

	private VistaNoteTitle[] getProgressNoteTitles() throws Exception {
		String[] vistaProgressNoteTitleArray = null;

		List<VistaNoteTitle> progressNoteTitles = new ArrayList<VistaNoteTitle>();
		request.setRpcName("TIU GET PN TITLES");
		request.clearParams();

		RpcResponse response = connection.executeRPC(request);

		if (logger.isDebugEnabled()) {
			logger.debug("Progress Note Titles: " + response.getResults());
		}

		try {
			String[] existingProgressNoteTitles = parseRpcResponse(response);
			for (int i = 0; i < existingProgressNoteTitles.length; i++) {
				vistaProgressNoteTitleArray = existingProgressNoteTitles[i].split(",");
				progressNoteTitles.add(new VistaNoteTitle(Long.parseLong(vistaProgressNoteTitleArray[0].substring(1).trim()), vistaProgressNoteTitleArray[1]));
			}
		} catch (VistaLinkNoResponseException vlnre) {
			throw new VistaLinkResponseException("Expected a response; but found no response.", null, response.getResults(), vlnre);
		}

		return progressNoteTitles.toArray(new VistaNoteTitle[progressNoteTitles.size()]);
	}

	@Override
	protected String[] parseRpcResponse(RpcResponse response) throws Exception {
		String[] parsedRpcResponse = new String[0];

		if (response != null && response.getResults() != null) {
			List<String> existingProgressNoteTitles = Arrays.asList(response.getResults().split("\n"));
			List<String> vistaNoteTitles = new ArrayList<String>();
			String[] vistaProgressNoteTitleArray = new String[0];

			for (String existingProgressNoteTitle : existingProgressNoteTitles) {
				if (existingProgressNoteTitle.startsWith("~")) {
					continue;
				}
				vistaProgressNoteTitleArray = super.parseRpcResponseLineWithCarrotDelimiter(existingProgressNoteTitle.getBytes());
				vistaNoteTitles.add(vistaProgressNoteTitleArray[0] + "," + vistaProgressNoteTitleArray[1]);
			}

			parsedRpcResponse = vistaNoteTitles.toArray(new String[vistaNoteTitles.size()]);
		}

		if (parsedRpcResponse.length == 0) {
			throw new VistaLinkNoResponseException();
		}

		return parsedRpcResponse;
	}

	@Override
	public void store() throws VistaLinkRequestException {

	}

	@Override
	public void load() throws VistaLinkRequestException {

	}
}
