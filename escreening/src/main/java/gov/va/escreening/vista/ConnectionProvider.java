package gov.va.escreening.vista;

import javax.resource.ResourceException;

import gov.va.escreening.security.EscreenUser;
import gov.va.med.exception.FoundationsException;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.adapter.cci.VistaLinkDuzConnectionSpec;
import gov.va.med.vistalink.rpc.RpcRequest;

public interface ConnectionProvider {

	VistaLinkClientStrategy createVistaLinkClientStrategy(
			EscreenUser escreenUser, String appProxyName, String rpcContext) throws VistaLinkClientException;

	RpcRequest createRpcRequest(String rpcName, String rpcContext,
			VistaLinkConnection vistaLinkConnection,
			boolean useProprietaryMessageFormat) throws FoundationsException;
}
