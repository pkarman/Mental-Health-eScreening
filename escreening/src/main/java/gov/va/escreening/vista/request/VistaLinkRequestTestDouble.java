package gov.va.escreening.vista.request;

import gov.va.med.vistalink.rpc.RpcResponse;

/**
 * Created by pouncilt on 4/23/14.
 */
public class VistaLinkRequestTestDouble extends VistaLinkBaseRequest {

    public VistaLinkRequestTestDouble() {
        super();
    }

    @Override
    public Object[] parseRpcResponse(RpcResponse response) throws Exception {
        return new Object[0];
    }

    @Override
    public String[] parseRpcResponseLineWithCarrotDelimiter(byte[] bytes) {
        return super.parseRpcResponseLineWithCarrotDelimiter(bytes);
    }
}
