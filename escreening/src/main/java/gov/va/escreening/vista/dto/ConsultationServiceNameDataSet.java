package gov.va.escreening.vista.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pouncilt on 6/13/14.
 */
public class ConsultationServiceNameDataSet {
    private List<ConsultationServiceNameInfo> consultationServiceNameInfoList = new ArrayList<ConsultationServiceNameInfo>();

    public ConsultationServiceNameDataSet(List<ConsultationServiceNameInfo> consultationServiceNameInfoList) {
        this.consultationServiceNameInfoList = consultationServiceNameInfoList;
    }

    public List<ConsultationServiceNameInfo> getConsultationServiceNameInfoList() {
        return consultationServiceNameInfoList;
    }

    public ConsultationServiceNameInfo findConsultationServiceNameInfoByServiceName(String serviceName) {
        ConsultationServiceNameInfo existingConsultationServiceNameInfo = null;

        for(ConsultationServiceNameInfo consultationServiceNameInfo: consultationServiceNameInfoList){
            if(consultationServiceNameInfo.getName().equals(serviceName.toUpperCase())){
                existingConsultationServiceNameInfo = consultationServiceNameInfo;
                break;
            }
        }
        return existingConsultationServiceNameInfo;
    }
}
