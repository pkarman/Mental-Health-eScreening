package gov.va.escreening.vista;

import java.io.Serializable;

public class VistaRpcParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private String paramType;
    private Object paramValue;

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public Object getParamValue() {
        return paramValue;
    }

    public void setParamValue(Object paramValue) {
        this.paramValue = paramValue;
    }

    public VistaRpcParam() {

    }

    public VistaRpcParam(String paramType, Object paramValue) {
        this.paramType = paramType;
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        return "VistRpcParam [paramType=" + paramType + ", paramValue=" + paramValue + "]";
    }

}
