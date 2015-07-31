package gov.va.escreening.vista;

import gov.va.escreening.security.EscreenUser;
import gov.va.med.exception.FoundationsException;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnectionFactory;
import gov.va.med.vistalink.adapter.cci.VistaLinkDuzConnectionSpec;
import gov.va.med.vistalink.adapter.spi.VistaLinkManagedConnectionFactory;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcRequestFactory;

import javax.annotation.Resource;
import javax.resource.ResourceException;

import org.springframework.stereotype.Component;

@Component("rpcConnectionProvider")
public class RpcConnectionProvider implements ConnectionProvider {
	
	@Resource(name = "vistaLinkManagedConnectionFactory")
	private VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory;

	@Override
	public RpcRequest createRpcRequest(String rpcName, String rpcContext,
			VistaLinkConnection vistaLinkConnection,
			boolean useProprietaryMessageFormat) throws FoundationsException {

		RpcRequest request = RpcRequestFactory.getRpcRequest(rpcContext, rpcName);
		request.setTimeOut(vistaLinkConnection.getTimeOut());
		request.setUseProprietaryMessageFormat(useProprietaryMessageFormat);
		return request;
	}

	@Override
	public VistaLinkClientStrategy createVistaLinkClientStrategy(
			EscreenUser escreenUser, String appProxyName, String rpcContext) throws VistaLinkClientException {

		// production code will send the logged in escreen user else for test purpose, the escreen will be null, in
		// which case the test spec will be used
		VistaLinkDuzConnectionSpec duzConnectionSpec = new VistaLinkDuzConnectionSpec(escreenUser.getVistaDivision(), escreenUser.getVistaDuz());

		VistaLinkClientStrategy vistaLinkClientStrategy = new DuzVistaLinkClientStrategy(vistaLinkManagedConnectionFactory, duzConnectionSpec, appProxyName, rpcContext);

		return vistaLinkClientStrategy;
	}
}
