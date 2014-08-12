package gov.va.escreening.vista;

import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.rpc.RpcRequest;

public interface RpcInvoker<T> {
	public T invokeRpc(VistaLinkConnection vistaLinkConnection,
			RpcRequest request, String rpcName) throws VistaLinkClientException;
}
