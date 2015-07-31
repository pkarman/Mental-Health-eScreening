package gov.va.escreening.vista.request;

import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;

/**
 * 
 * @author khalid rizvi
 * 
 * @param <T>
 */
public class ORWDXM1_BLDQRSP_VistaLinkRequestContext<T extends VistaLinkRequestParameters> extends VistaLinkRequestBaseContext {
	public ORWDXM1_BLDQRSP_VistaLinkRequestContext(RpcRequest request, VistaLinkConnection connection, T requestParameters) {
		super(request, connection, requestParameters);
	}
}
