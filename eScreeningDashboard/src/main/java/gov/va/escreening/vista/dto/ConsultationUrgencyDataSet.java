package gov.va.escreening.vista.dto;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

/**
 * Created by pouncilt on 6/11/14.
 */
public class ConsultationUrgencyDataSet {
    List<ConsultationUrgencyInfo> consultationUrgencyInfoList = new ArrayList<ConsultationUrgencyInfo>();

    public ConsultationUrgencyDataSet() {

    }

    public ConsultationUrgencyDataSet(List<ConsultationUrgencyInfo> consultationUrgencyInfoList) {
        this.consultationUrgencyInfoList = consultationUrgencyInfoList;
    }

    public void addConsultationUrgencyInfo(ConsultationUrgencyInfo consultationUrgencyInfo) {
        consultationUrgencyInfoList.add(consultationUrgencyInfo);
    }

    public Collection<ConsultationUrgencyInfo> getInpatientConsultationUrgencies() {
        return CollectionUtils.select(this.consultationUrgencyInfoList, new Predicate<ConsultationUrgencyInfo>() {
            @Override
            public boolean evaluate(ConsultationUrgencyInfo consultationUrgencyInfo) {
                return consultationUrgencyInfo.isInpatientInfo() && consultationUrgencyInfo.hasUrgencyInfo();
            }
        });
    }

    public Collection<ConsultationUrgencyInfo> getOutpatientConsultationUrgencies() {
        return CollectionUtils.select(this.consultationUrgencyInfoList, new Predicate<ConsultationUrgencyInfo>() {
            @Override
            public boolean evaluate(ConsultationUrgencyInfo consultationUrgencyInfo) {
                return consultationUrgencyInfo.isOutpatientInfo() && consultationUrgencyInfo.hasUrgencyInfo();
            }
        });
    }

    public Collection<ConsultationUrgencyInfo> getInpatientConsultationLocations() {
        return CollectionUtils.select(this.consultationUrgencyInfoList, new Predicate<ConsultationUrgencyInfo>() {
            @Override
            public boolean evaluate(ConsultationUrgencyInfo consultationUrgencyInfo) {
                return consultationUrgencyInfo.isInpatientInfo() && consultationUrgencyInfo.hasLocationInfo();
            }
        });
    }

    public Collection<ConsultationUrgencyInfo> getOutpatientConsultationLocations() {
        return CollectionUtils.select(this.consultationUrgencyInfoList, new Predicate<ConsultationUrgencyInfo>() {
            @Override
            public boolean evaluate(ConsultationUrgencyInfo consultationUrgencyInfo) {
                return consultationUrgencyInfo.isOutpatientInfo() && consultationUrgencyInfo.hasLocationInfo();
            }
        });
    }

    public int getConsultationUrgencyInfoList() {
        return consultationUrgencyInfoList.size();
    }

    public String findOutpatientConsultationUrgencyByName(String searchCriteria) {
        String searchResult = null;
        Collection<ConsultationUrgencyInfo> outpatientConsultationUrgencies = this.getOutpatientConsultationUrgencies();
        for(ConsultationUrgencyInfo outpatientConsultationUrgency : outpatientConsultationUrgencies) {
            if(outpatientConsultationUrgency.getName().trim().equalsIgnoreCase(searchCriteria)) {
                searchResult = outpatientConsultationUrgency.getName();
                break;
            }
        }
        return searchResult;
    }

    public String findInpatientConsultationUrgencyByName(String searchCriteria) {
        String searchResult = null;
        Collection<ConsultationUrgencyInfo> inpatientConsultationUrgencies = this.getInpatientConsultationUrgencies();
        for(ConsultationUrgencyInfo inpatientConsultationUrgency : inpatientConsultationUrgencies) {
            if(inpatientConsultationUrgency.getName().trim().equalsIgnoreCase(searchCriteria)) {
                searchResult = inpatientConsultationUrgency.getName();
                break;
            }
        }
        return searchResult;
    }

    public String findOutpatientConsultationLocationByName(String searchCriteria) {
        String searchResult = null;
        Collection<ConsultationUrgencyInfo> outpatientConsultationLocations = this.getOutpatientConsultationLocations();
        for(ConsultationUrgencyInfo outpatientConsultationLocation : outpatientConsultationLocations) {
            if(outpatientConsultationLocation.getName().trim().equals(searchCriteria.toUpperCase())) {
                searchResult = outpatientConsultationLocation.getName();
                break;
            }
        }
        return searchResult;
    }

    public String findInpatientConsultationLocationByName(String searchCriteria) {
        String searchResult = null;
        Collection<ConsultationUrgencyInfo> inpatientConsultationLocations = this.getInpatientConsultationLocations();
        for(ConsultationUrgencyInfo inpatientConsultationLocation : inpatientConsultationLocations) {
            if(inpatientConsultationLocation.getName().trim().equals(searchCriteria.toUpperCase())) {
                searchResult = inpatientConsultationLocation.getName();
                break;
            }
        }
        return searchResult;
    }
}
