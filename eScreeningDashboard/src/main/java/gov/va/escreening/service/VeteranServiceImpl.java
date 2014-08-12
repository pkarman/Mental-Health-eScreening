package gov.va.escreening.service;

import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.domain.VeteranDtoHelper;
import gov.va.escreening.dto.SearchAttributes;
import gov.va.escreening.dto.dashboard.SearchResult;
import gov.va.escreening.dto.dashboard.VeteranSearchResult;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.form.CreateVeteranFormBean;
import gov.va.escreening.repository.VeteranRepository;
import gov.va.escreening.util.VeteranUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class VeteranServiceImpl implements VeteranService {

    private static final Logger logger = LoggerFactory.getLogger(VeteranServiceImpl.class);

    private VeteranRepository veteranRepository;

    @Autowired
    public void setVeteranRepository(VeteranRepository veteranRepository) {
        this.veteranRepository = veteranRepository;
    }

    @Override
    public VeteranDto add(VeteranDto veteranDto) {
        logger.debug("add Veteran called.");
        Veteran veteran = convertVeteranDtoToVeteran(veteranDto);
        veteran.setGuid(UUID.randomUUID().toString());
        veteranRepository.create(veteran);
        veteranDto = convertVeteranToVeteranDto(veteran);
        return veteranDto;
    }

    @Override
    public VeteranDto add(String lastName, String ssnLastFour) {
        logger.debug("add Veteran by ssn and last name called.");
        Veteran veteran = new Veteran();
        veteran.setLastName(lastName);
        veteran.setSsnLastFour(ssnLastFour);
        veteran.setGuid(UUID.randomUUID().toString());
        veteranRepository.create(veteran);
        VeteranDto veteranDto = convertVeteranToVeteranDto(veteran);
        return veteranDto;
    }

    @Override
    public List<VeteranDto> findVeterans(VeteranDto veteranDto) {
        logger.debug("findVeterans called.");

        Veteran searchVeteran = convertVeteranDtoToVeteran(veteranDto);
        List<Veteran> veterans = veteranRepository.getVeterans(searchVeteran);

        List<VeteranDto> veteranDtos = new ArrayList<VeteranDto>();
        for (Veteran foundVeteran : veterans) {
            VeteranDto foundVeteranDto = convertVeteranToVeteranDto(foundVeteran);
            foundVeteranDto.setRecordSource("db");
            veteranDtos.add(foundVeteranDto);
        }
        return veteranDtos;
    }

    @Override
    public SearchResult<VeteranSearchResult> searchVeterans(Integer veteranId, String lastName, String ssnLastFour,
            List<Integer> programIdList, SearchAttributes searchAttributes) {

        logger.debug("searchVeterans called.");

        SearchResult<VeteranSearchResult> searchResult = veteranRepository.searchVeterans(veteranId, lastName,
                ssnLastFour, programIdList, searchAttributes);

        return searchResult;
    }

    @Override
    public void updateVeteran(VeteranDto veteranDto) {
        logger.debug("updateVeteran called");

        Veteran veteran = convertVeteranDtoToVeteran(veteranDto);
        veteranRepository.update(veteran);
        veteranRepository.commit(); // must explicitly call commit
    }

    @Override
    public void updateDemographicsData(VeteranDto veteranDto) {
        logger.debug("updateDemographicsData called");

        // First get the data from the database.
        Veteran veteran = veteranRepository.findOne(veteranDto.getVeteranId());

        // Update the fields we must.
        veteran.setFirstName(veteranDto.getFirstName());
        veteran.setMiddleName(veteranDto.getMiddleName());
        veteran.setSuffix(veteranDto.getSuffix());
        veteran.setEmail(veteranDto.getEmail());
        veteran.setPhone(veteranDto.getPhone());
        veteran.setBestTimeToCall(veteranDto.getBestTimeToCall());
        veteran.setBestTimeToCallOther(veteranDto.getBestTimeToCallOther());

        // Save it.
        veteranRepository.update(veteran);
        veteranRepository.commit(); // must explicitly call commit
    }

    @Override
    public VeteranDto getByVeteranId(Integer veteranId) {
        logger.debug("getByVeteranId called");
        Veteran veteran = veteranRepository.findOne(veteranId);
        VeteranDto veteranDto = convertVeteranToVeteranDto(veteran);
        return veteranDto;
    }

    private VeteranDto convertVeteranToVeteranDto(Veteran veteran) {
        VeteranDto veteranDto = new VeteranDto();

        veteranDto.setBestTimeToCall(veteran.getBestTimeToCall());
        veteranDto.setBestTimeToCallOther(veteran.getBestTimeToCallOther());
        veteranDto.setBirthDate(veteran.getBirthDate());
        if (veteran.getBirthDate() != null) {
            DateTime dateTime = new DateTime(veteran.getBirthDate());
            veteranDto.setBirthDateString(dateTime.toString("MM/dd/yyyy"));
        }
        veteranDto.setDateCreated(veteran.getDateCreated());
        veteranDto.setEmail(veteran.getEmail());
        veteranDto.setFirstName(veteran.getFirstName());
        veteranDto.setLastName(veteran.getLastName());
        veteranDto.setMiddleName(veteran.getMiddleName());
        veteranDto.setSsnLastFour(veteran.getSsnLastFour());
        veteranDto.setSuffix(veteran.getSuffix());
        veteranDto.setVeteranId(veteran.getVeteranId());
        veteranDto.setPhone(veteran.getPhone());
        veteranDto.setGender(veteran.getGender());
        veteranDto.setGuid(veteran.getGuid());
        veteranDto.setVeteranIen(veteran.getVeteranIen());
        veteranDto.setDateRefreshedFromVista(veteran.getDateRefreshedFromVista());
        veteranDto.setCellPhone(veteran.getCellPhone());
        veteranDto.setWorkPhone(veteran.getOfficePhone());
        veteranDto.setFullName(VeteranUtil.getFullName(veteran.getFirstName(), veteran.getMiddleName(),
                veteran.getLastName(), veteran.getSuffix(), null));
        return veteranDto;
    }

    private Veteran convertVeteranDtoToVeteran(VeteranDto veteranDto) {
        Veteran veteran = new Veteran();

        veteran.setBestTimeToCall(veteranDto.getBestTimeToCall());
        veteran.setBestTimeToCallOther(veteranDto.getBestTimeToCallOther());
        veteran.setBirthDate(veteranDto.getBirthDate());
        veteran.setDateCreated(veteranDto.getDateCreated());
        veteran.setEmail(veteranDto.getEmail());
        veteran.setFirstName(veteranDto.getFirstName());
        veteran.setLastName(veteranDto.getLastName());
        veteran.setMiddleName(veteranDto.getMiddleName());
        veteran.setPhone(VeteranDtoHelper.getPhoneByPriority(veteranDto));
        veteran.setSsnLastFour(veteranDto.getSsnLastFour());
        veteran.setSuffix(veteranDto.getSuffix());
        veteran.setVeteranId(veteranDto.getVeteranId());
        veteran.setGender(veteranDto.getGender());
        veteran.setGuid(veteranDto.getGuid());
        veteran.setVeteranIen(veteranDto.getVeteranIen());
        veteran.setDateRefreshedFromVista(veteranDto.getDateRefreshedFromVista());
        veteran.setCellPhone(veteranDto.getCellPhone());
        veteran.setOfficePhone(veteranDto.getWorkPhone());

        return veteran;
    }

    @Override
    public Integer add(CreateVeteranFormBean createVeteranFormBean) {
        Veteran veteran = new Veteran();

        veteran.setFirstName(createVeteranFormBean.getFirstName());
        veteran.setMiddleName(createVeteranFormBean.getMiddleName());
        veteran.setLastName(createVeteranFormBean.getLastName());
        veteran.setSsnLastFour(createVeteranFormBean.getSsnLastFour());
        veteran.setBirthDate(createVeteranFormBean.getBirthDate());
        veteran.setPhone(createVeteranFormBean.getPhone());
        veteran.setOfficePhone(createVeteranFormBean.getOfficePhone());
        veteran.setCellPhone(createVeteranFormBean.getCellPhone());
        veteran.setEmail(createVeteranFormBean.getEmail());
        veteran.setGuid(java.util.UUID.randomUUID().toString());

        // Save to DB and return primary key.
        veteranRepository.create(veteran);

        return veteran.getVeteranId();
    }

    @Transactional(readOnly = true)
    @Override
    public List<VeteranDto> searchVeterans(String lastName, String ssnLastFour) {

        List<Veteran> veterans = veteranRepository.searchVeterans(lastName, ssnLastFour);

        List<VeteranDto> resultList = new ArrayList<VeteranDto>();

        for (Veteran foundVeteran : veterans) {
            VeteranDto foundVeteranDto = convertVeteranToVeteranDto(foundVeteran);
            foundVeteranDto.setRecordSource("db");
            resultList.add(foundVeteranDto);
        }

        return resultList;
    }

    @Override
    public void updateMappedVistaFields(VeteranDto veteranDto) {
        logger.debug("updateMappedVistaFields called");

        // First get the data from the database.
        Veteran veteran = veteranRepository.findOne(veteranDto.getVeteranId());

        // Update the fields we must.
        veteran.setSsnLastFour(veteranDto.getSsnLastFour());
        veteran.setFirstName(veteranDto.getFirstName());
        veteran.setMiddleName(veteranDto.getMiddleName());
        veteran.setLastName(veteranDto.getLastName());
        veteran.setSuffix(veteranDto.getSuffix());
        veteran.setEmail(veteranDto.getEmail());
        veteran.setPhone(StringUtils.remove(veteranDto.getPhone(), "-"));
        veteran.setCellPhone(StringUtils.remove(veteranDto.getCellPhone(), "-"));
        veteran.setOfficePhone(StringUtils.remove(veteranDto.getWorkPhone(), "-"));
        veteran.setBirthDate(veteranDto.getBirthDate());
        veteran.setGender(veteranDto.getGender());
        veteran.setDateRefreshedFromVista(new Date());

        // Save it.
        veteranRepository.update(veteran);
        veteranRepository.commit(); // must explicitly call commit
    }

    @Override
    public void updateVeteranIen(int veteranId, String veteranIen) {

        // First get the data from the database.
        Veteran veteran = veteranRepository.findOne(veteranId);
        veteran.setVeteranIen(veteranIen);
        veteranRepository.update(veteran);
    }

    @Override
    public Integer importVistaVeteranRecord(VeteranDto veteranDto) {
        logger.debug("importVistaVeteranRecord called");

        // First get the data from the database.
        Veteran veteran = new Veteran();

        // Insert the fields we must.
        veteran.setVeteranIen(veteranDto.getVeteranIen());
        veteran.setGuid(java.util.UUID.randomUUID().toString());
        veteran.setSsnLastFour(veteranDto.getSsnLastFour());
        veteran.setFirstName(veteranDto.getFirstName());
        veteran.setMiddleName(veteranDto.getMiddleName());
        veteran.setLastName(veteranDto.getLastName());
        veteran.setSuffix(veteranDto.getSuffix());
        veteran.setEmail(veteranDto.getEmail());
        veteran.setPhone(StringUtils.remove(veteranDto.getPhone(), "-"));
        veteran.setCellPhone(StringUtils.remove(veteranDto.getCellPhone(), "-"));
        veteran.setOfficePhone(StringUtils.remove(veteranDto.getWorkPhone(), "-"));
        veteran.setBirthDate(veteranDto.getBirthDate());
        veteran.setGender(veteranDto.getGender());
        veteran.setDateRefreshedFromVista(new Date());

        // Save it.
        veteranRepository.create(veteran);
        veteranRepository.commit(); // must explicitly call commit

        return veteran.getVeteranId();
    }
}