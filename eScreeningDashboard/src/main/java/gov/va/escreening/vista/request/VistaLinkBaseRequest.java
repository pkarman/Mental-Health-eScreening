package gov.va.escreening.vista.request;

import gov.va.med.vistalink.rpc.RpcResponse;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pouncilt on 4/9/14.
 */
public abstract class VistaLinkBaseRequest<T> {

    public VistaLinkBaseRequest() {

    }

    protected abstract T[] parseRpcResponse(RpcResponse response) throws Exception;

    protected String[] parseRpcSimpleResponseWithNoDelimiter(RpcResponse response) throws Exception {
        String[] parsedPpcResponse = new String[0];

        if (response != null && response.getResults() != null && response.getResults().trim().length() > 0) {
            parsedPpcResponse = new String[1];
            parsedPpcResponse[0] = response.getResults().trim();
        }

        if(parsedPpcResponse.length == 0) {
            throw new VistaLinkNoResponseException();
        }

        return parsedPpcResponse;
    }

    protected String[] parseRpcSimpleResponseWithNewLineDelimiter(RpcResponse response) throws Exception {
        String[] parsedPpcResponse = new String[0];

        if (response != null && response.getResults() != null) {
            List<String> boilerPlateTextArray = new ArrayList<String>(Arrays.asList(response.getResults().split("\n")));
            parsedPpcResponse = boilerPlateTextArray.toArray(new String[boilerPlateTextArray.size()]);
        }

        if(parsedPpcResponse.length == 0) {
            throw new VistaLinkNoResponseException();
        }

        return parsedPpcResponse;
    }

    protected String[] parseRpcSimpleResponseWithCarrotDelimiter(RpcResponse response) throws Exception {
        String[] parsedPpcResponse = new String[0];

        if (response != null && response.getResults() != null && response.getResults().trim().length() > 0) {
            parsedPpcResponse = this.parseRpcResponseLineWithCarrotDelimiter(response.getResults().getBytes());
        }

        if(parsedPpcResponse.length == 0) {
            throw new VistaLinkNoResponseException();
        }

        return parsedPpcResponse;
    }

    protected String[] parseRpcResponseLineWithCarrotDelimiter(byte[] bytes){
        byte delimiterByte = 94;
        List<String> results = new ArrayList<String>();
        List<Byte> tempByteList = new ArrayList<Byte>();

        for(int i = 0; i < bytes.length; i ++) {
            if(bytes[i] != delimiterByte) {
                tempByteList.add(new Byte(bytes[i]));
            } if (bytes[i] == delimiterByte) {
                byte[] byteTempArray = new byte[tempByteList.size()];
                Byte[] tempByteArray =  tempByteList.toArray(new Byte[tempByteList.size()]);
                for(int j = 0; j < tempByteArray.length; j ++) {
                    byteTempArray[j] = tempByteArray[j].byteValue();
                }

                results.add((byteTempArray.length == 0)? null : new String(byteTempArray, Charset.forName("UTF-8")));

                tempByteList.clear();
            }
        }

        if(tempByteList.size() > 0) {
            byte[] byteTempArray = new byte[tempByteList.size()];
            Byte[] tempByteArray =  tempByteList.toArray(new Byte[tempByteList.size()]);
            for(int j = 0; j < tempByteArray.length; j ++) {
                byteTempArray[j] = tempByteArray[j].byteValue();
            }

            results.add(new String(byteTempArray, Charset.forName("UTF-8")));

            tempByteList.clear();
        }

        return results.toArray(new String[results.size()]);
    }
}
