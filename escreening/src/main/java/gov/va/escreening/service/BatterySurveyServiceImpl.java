package gov.va.escreening.service;

import gov.va.escreening.domain.BatterySurveyDto;
import gov.va.escreening.dto.SearchDTO;
import gov.va.escreening.dto.SearchType;
import gov.va.escreening.entity.BatterySurvey;
import gov.va.escreening.repository.BatterySurveyJpaRepository;
import gov.va.escreening.repository.BatterySurveyRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional
@Service
public class BatterySurveyServiceImpl implements BatterySurveyService {
    @Autowired
    private BatterySurveyRepository batterySurveyRepository;

    @Resource
    private BatterySurveyJpaRepository batterySurveyJpaRepository;

    public BatterySurveyServiceImpl() {

    }


    public void setBatterySurveyRepository(BatterySurveyRepository batterySurveyRepository) {
        this.batterySurveyRepository = batterySurveyRepository;
    }

    public void setBatterySurveyJpaRepository(BatterySurveyJpaRepository batterySurveyJpaRepository){
        this.batterySurveyJpaRepository = batterySurveyJpaRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<BatterySurveyDto> findAllByBatteryId(int batteryId) {

        List<BatterySurveyDto> resultList = new ArrayList<BatterySurveyDto>();

        List<BatterySurvey> batterySurveyList = batterySurveyRepository.findAllByBatteryId(batteryId);

        for (BatterySurvey batterySurvey : batterySurveyList) {
            BatterySurveyDto batterySurveyDto = new BatterySurveyDto();

            batterySurveyDto.setBatterySurveyId(batterySurvey.getBatterySurveyId());
            batterySurveyDto.setBatteryId(batterySurvey.getBattery().getBatteryId());
            batterySurveyDto.setBatteryName(batterySurvey.getBattery().getName());
            batterySurveyDto.setSurveyId(batterySurvey.getSurvey().getSurveyId());
            batterySurveyDto.setSurveyName(batterySurvey.getSurvey().getName());
            batterySurveyDto.setDescription(batterySurvey.getSurvey().getDescription());

            resultList.add(batterySurveyDto);
        }

        return resultList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<BatterySurveyDto> getBatterySurveyList() {

        List<BatterySurveyDto> resultList = new ArrayList<BatterySurveyDto>();

        List<BatterySurvey> batterySurveyList = batterySurveyRepository.getBatterySurveyList();

        for (BatterySurvey batterySurvey : batterySurveyList) {
            BatterySurveyDto batterySurveyDto = new BatterySurveyDto();

            batterySurveyDto.setBatterySurveyId(batterySurvey.getBatterySurveyId());
            batterySurveyDto.setBatteryId(batterySurvey.getBattery().getBatteryId());
            batterySurveyDto.setBatteryName(batterySurvey.getBattery().getName());
            batterySurveyDto.setSurveyId(batterySurvey.getSurvey().getSurveyId());
            batterySurveyDto.setSurveyName(batterySurvey.getSurvey().getName());
            batterySurveyDto.setDescription(batterySurvey.getSurvey().getDescription());

            resultList.add(batterySurveyDto);
        }

        return resultList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<BatterySurvey> search(SearchDTO searchCriteria) {
        //LOGGER.debug("Searching persons with search criteria: " + searchCriteria);

        Object searchTerm = searchCriteria.getSearchTerm();
        SearchType searchType = searchCriteria.getSearchType();

        if (searchType == null) {
            throw new IllegalArgumentException();
        }

        return findBatterySurveyBySearchType(searchTerm, searchType);
    }

    private List<BatterySurvey> findBatterySurveyBySearchType(Object searchTerm, SearchType searchType) {
        List<BatterySurvey> batterySurveys = new ArrayList<BatterySurvey>();

        if (searchType == SearchType.METHOD_NAME) {
            //LOGGER.debug("Searching persons by using method name query creation.");
            //batterySurveys = batterySurveyJpaRepository.findByLastName(searchTerm);
        }
        else if (searchType == SearchType.NAMED_QUERY) {
            //LOGGER.debug("Searching persons by using named query");
            //batterySurveys = batterySurveyJpaRepository.findByName(searchTerm);
        }
        else {
            //LOGGER.debug("Searching persons by using query annotation");
            batterySurveys = batterySurveyJpaRepository.find(searchTerm);
        }

        return batterySurveys;
    }

}
