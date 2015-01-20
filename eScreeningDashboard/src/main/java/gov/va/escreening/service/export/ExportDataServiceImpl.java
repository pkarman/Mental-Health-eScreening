package gov.va.escreening.service.export;

import com.google.common.base.Preconditions;
import com.google.common.collect.*;
import gov.va.escreening.domain.ExportTypeEnum;
import gov.va.escreening.dto.dashboard.AssessmentDataExport;
import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.dto.dashboard.DataExportFilterOptions;
import gov.va.escreening.entity.*;
import gov.va.escreening.form.ExportDataFormBean;
import gov.va.escreening.repository.*;
import gov.va.escreening.service.VeteranAssessmentService;
import gov.va.escreening.util.DataExportAndDictionaryUtil;
import gov.va.escreening.util.SurveyResponsesHelper;
import gov.va.escreening.variableresolver.AssessmentVariableDto;
import gov.va.escreening.variableresolver.VariableResolverService;
import org.bouncycastle.util.Strings;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.Map.Entry;

@Service("exportDataService")
public class ExportDataServiceImpl implements ExportDataService, MessageSourceAware {
    private static final Logger logger = LoggerFactory.getLogger(ExportDataServiceImpl.class);

    @Resource(type = AssessmentVariableRepository.class)
    private AssessmentVariableRepository avr;

    @Resource(type = DataDictionaryService.class)
    private DataDictionaryService dds;

    @Resource(type = ExportLogRepository.class)
    private ExportLogRepository exportLogRepository;

    @Resource(type = ExportTypeRepository.class)
    private ExportTypeRepository exportTypeRepository;

    @Resource(type = ExportLogAuditRepository.class)
    private ExportLogAuditRepository exportLogAuditRepository;

    private MessageSource msgSrc;

    @Resource(type = ProgramRepository.class)
    private ProgramRepository programRepository;

    @Resource(type = SurveyResponsesHelper.class)
    private SurveyResponsesHelper srh;

    @Resource(type = UserRepository.class)
    private UserRepository userRepository;

    @Resource(type = VeteranAssessmentService.class)
    private VeteranAssessmentService veteranAssessmentService;

    @Resource(type = VeteranRepository.class)
    private VeteranRepository veteranRepository;

    @Resource(type = VariableResolverService.class)
    private VariableResolverService vrs;

    @Resource(type = DataExportAndDictionaryUtil.class)
    private DataExportAndDictionaryUtil dedUtil;

    /**
     * method to find a response of 1 in multi-select type responses. If user has responded to any question, then all
     * other unanswered question must be marked 999. All this method does is that it changes those 999 to 0
     *
     * @param multiSelectMap
     */
    private void change999To0IfNeeded(
            Multimap<String, DataExportCell> multiSelectMap) {
        for (String key : multiSelectMap.keySet()) {
            Collection<DataExportCell> cells = multiSelectMap.get(key);
            for (DataExportCell cell : cells) {
                if ("1".equals(cell.getCellValue())) {
                    setMissingCellsToZero(cells);
                    break;
                }
            }
        }
    }

    @Override
    public List<DataExportCell> buildExportDataForOneAssessment(Map<String, Table<String, String, String>> dd,
                                                                VeteranAssessment va, int identifiedExportType) {

        Map<String, Map<String, String>> preFetchedData = prepareData(dd, Arrays.asList(va), identifiedExportType);
        List<DataExportCell> row = buildExportDataPerAssessment(dd, va, identifiedExportType, preFetchedData);
        return row;

    }

    private List<DataExportCell> buildExportDataPerAssessment(Map<String, Table<String, String, String>> dd,
                                                              VeteranAssessment assessment, Integer identifiedExportType,
                                                              Map<String, Map<String, String>> preFetchedData) {

        Map<String, String> formulaeMap = preFetchedData.get(formulaKey(assessment));

        // has user asked to show the ppi info or not
        boolean show = ExportTypeEnum.DEIDENTIFIED.getExportTypeId() != identifiedExportType;

        // gather mandatory columns data
        List<DataExportCell> exportDataRowCells = buildMandatoryColumns(assessment, show);

        for (String surveyName : preFetchedData.get(surveyNamesKey()).keySet()) {
            Map<String, String> usrRespMap = preFetchedData.get(usrRespKey(assessment, surveyName));
            Map<String, String> surveyDictionary = preFetchedData.get(dictHdrKey(surveyName));
            Map<String, String> answerTypeOther = preFetchedData.get(answerTypeOtherKey(surveyName));

            // traverse through each exportName, and try to find the veteran's response for the exportName. In case the
            // user has not responded, leave 999
            Multimap<String, DataExportCell> multiSelectMap = HashMultimap.create();
            for (Entry<String, String> surveyEntry : surveyDictionary.entrySet()) {
                String surveyExportName = surveyEntry.getValue();
                if (!surveyExportName.isEmpty()) {
                    DataExportCell aCell = createExportCell(usrRespMap, formulaeMap, answerTypeOther, surveyExportName, show);
                    if (logger.isDebugEnabled()) {
                        logger.debug(String.format("adding data for data dictionary column %s->%s=%s", surveyName, surveyExportName, aCell));
                    }
                    exportDataRowCells.add(aCell);
                    saveMultiSelectResponses(multiSelectMap, surveyEntry, aCell);
                }
            }
            change999To0IfNeeded(multiSelectMap);
        }
        return exportDataRowCells;
    }

    private String surveyNamesKey() {
        return "DATA_DICTIONARY_SURVEY";
    }

    private String dictHdrKey(String surveyName) {
        return String.format("SURVEY_DICTIONARY_KEY_%s", surveyName);
    }

    private Map<String, Map<String, String>> buildFormulaeMapForMatchingAssessments(
            List<VeteranAssessment> matchingAssessments) {

        Map<String, Map<String, String>> formulaeMap = Maps.newHashMap();

        Iterable<AssessmentVariable> avFormulaeEntities = avr.findAllFormulae();

        for (VeteranAssessment va : matchingAssessments) {
            Iterable<AssessmentVariableDto> avFormulaeList = vrs.resolveVariablesFor(va.getVeteranAssessmentId(), avFormulaeEntities);
            formulaeMap.put(formulaKey(va), createFormulaeMap(avFormulaeList));
        }
        return formulaeMap;
    }

    public List<DataExportCell> buildMandatoryColumns(
            VeteranAssessment assessment, boolean show) {

        List<DataExportCell> mandatoryData = new ArrayList<DataExportCell>();

        mandatoryData.addAll(collectPpi(assessment, show));

        mandatoryData.add(new DataExportCell("assessment_id", srh.getOrMiss(srh.getStrFromInt(assessment.getVeteranAssessmentId()))));
        mandatoryData.add(new DataExportCell("created_by", srh.getOrMiss(assessment.getCreatedByUser() != null ? assessment.getCreatedByUser().getUserFullName() : null)));
        mandatoryData.add(new DataExportCell("battery_name", srh.getOrMiss(assessment.getBattery() != null ? assessment.getBattery().getName() : null)));
        mandatoryData.add(new DataExportCell("program_name", srh.getOrMiss(assessment.getProgram() != null ? assessment.getProgram().getName() : null)));
        mandatoryData.add(new DataExportCell("vista_clinic", srh.getOrMiss(assessment.getClinic() != null ? assessment.getClinic().getName() : null)));
        mandatoryData.add(new DataExportCell("note_title", srh.getOrMiss(assessment.getNoteTitle() != null ? assessment.getNoteTitle().getName() : null)));
        mandatoryData.add(new DataExportCell("clinician_name", srh.getOrMiss(assessment.getClinician() != null ? assessment.getClinician().getUserFullName() : null)));
        mandatoryData.add(new DataExportCell("date_created", srh.getOrMiss(srh.getDtAsStr(assessment.getDateCreated()))));
        mandatoryData.add(new DataExportCell("time_created", srh.getOrMiss(srh.getTmAsStr(assessment.getDateCreated()))));
        mandatoryData.add(new DataExportCell("date_completed", srh.getOrMiss(srh.getDtAsStr(assessment.getDateCompleted()))));
        mandatoryData.add(new DataExportCell("time_completed", srh.getOrMiss(srh.getTmAsStr(assessment.getDateCompleted()))));
        mandatoryData.add(new DataExportCell("duration", srh.getOrMiss(srh.getStrFromInt(assessment.getDuration()))));

        mandatoryData.add(new DataExportCell("vista_DOB", show ? srh.getOrMiss(srh.getDtAsStr(assessment.getVeteran().getBirthDate())) : srh.miss()));
        return mandatoryData;
    }

    private List<DataExportCell> collectPpi(VeteranAssessment assessment,
                                            boolean show) {
        Veteran v = assessment.getVeteran();

        List<DataExportCell> mandatoryIdendifiedData = new ArrayList<DataExportCell>();

        // if veteran has taken the 'Identification' survey then skip this as veteran survey response
        // will take precedence over the clinician entered data

        mandatoryIdendifiedData.addAll(Arrays.asList(new DataExportCell("vista_lastname", show ? srh.getOrMiss(v.getLastName()) : srh.miss()),//
                new DataExportCell("vista_firstname", show ? srh.getOrMiss(v.getVistaFirstName()) : srh.miss()),//
                new DataExportCell("vista_midname", show ? srh.getOrMiss(v.getVistaMiddleName()) : srh.miss()),//
                new DataExportCell("vista_SSN", show ? srh.getOrMiss(v.getSsnLastFour()) : srh.miss()), new DataExportCell("vista_ien", v.getVeteranIen())));
        return mandatoryIdendifiedData;
    }

    private List<List<DataExportCell>> createDataExportReport(Map<String, Table<String, String, String>> dd,
                                                              List<VeteranAssessment> matchingAssessments,
                                                              Integer identifiedExportType,
                                                              Map<String, Map<String, String>> preFetchedData) {

        List<List<DataExportCell>> dataExportReport = new ArrayList<List<DataExportCell>>();

        // build an export row for each assessment
        for (VeteranAssessment assessment : matchingAssessments) {
            List<DataExportCell> xportedDataForOneAssessment = buildExportDataPerAssessment(dd, assessment, identifiedExportType, preFetchedData);
            dataExportReport.add(xportedDataForOneAssessment);
        }

        return dataExportReport;
    }

    public DataExportCell createExportCell(Map<String, String> usrRespMap,
                                           Map<String, String> formulaeMap, Map<String, String> answerTypeOther, String exportName, boolean show) {

        // try to find the user response
        String exportVal = usrRespMap == null ? null : usrRespMap.get(exportName);
        // if value of export name was a formula (sum, avg, or some other formula derived value)
        exportVal = srh.getOrMiss(exportVal == null ? formulaeMap.get(exportName) : exportVal);

        // find out if this is an other datatype
        boolean other = "true".equals(answerTypeOther.get(exportName));
        return new DataExportCell(exportName, exportVal, other);
    }

    private ExportLog createExportLogFromOptions(DataExportFilterOptions options) {
        Preconditions.checkNotNull(options, "DataExportFilterOptions value cannot be null");

        ExportLog exportLog = new ExportLog();

        if (options.getAssessmentEnd() != null) {
            exportLog.setAssessmentEndFilter(options.getAssessmentEnd());
        }

        if (options.getAssessmentStart() != null) {
            exportLog.setAssessmentStartFilter(options.getAssessmentStart());
        }

        if (options.getClinicianUserId() != null) {
            User clinician = userRepository.findOne(options.getClinicianUserId());
            exportLog.setClinician(clinician);
        }

        if (options.getComment() != null && !options.getComment().isEmpty()) {
            exportLog.setComment(options.getComment());
        }

        if (options.getCreatedByUserId() != null) {
            User createdByUser = userRepository.findOne(options.getCreatedByUserId());
            exportLog.setCreatedByUser(createdByUser);
        }

        if (options.getExportedByUserId() != null) {
            User exportedByUser = userRepository.findOne(options.getExportedByUserId());
            exportLog.setExportedByUser(exportedByUser);
        }

        if (options.getExportTypeId() != null) {
            ExportType exportType = exportTypeRepository.findOne(options.getExportTypeId());
            exportLog.setExportType(exportType);
        }

        if (options.getFilePath() != null && !options.getFilePath().isEmpty()) {
            exportLog.setFilePath(options.getFilePath());
        }

        if (options.getProgramId() != null) {
            Program program = programRepository.findOne(options.getProgramId());
            exportLog.setProgram(program);
        }

        if (options.getVeteranId() != null) {
            Veteran veteran = veteranRepository.findOne(options.getVeteranId());
            exportLog.setVeteran(veteran);
        }

        return exportLog;
    }

    private DataExportFilterOptions createFilterOptions(
            ExportDataFormBean exportDataFormBean) {

        DataExportFilterOptions filterOptions = new DataExportFilterOptions();

        if (exportDataFormBean.getToAssessmentDate() != null)
            filterOptions.setAssessmentEnd(exportDataFormBean.getToAssessmentDate());

        if (exportDataFormBean.getFromAssessmentDate() != null)
            filterOptions.setAssessmentStart(exportDataFormBean.getFromAssessmentDate());

        if (exportDataFormBean.getClinicianId() != null)
            filterOptions.setClinicianUserId(exportDataFormBean.getClinicianId());

        if (exportDataFormBean.getCommentText() != null)
            filterOptions.setComment(exportDataFormBean.getCommentText());

        if (exportDataFormBean.getCreatedByUserId() != null)
            filterOptions.setCreatedByUserId(exportDataFormBean.getCreatedByUserId());

        if (exportDataFormBean.getExportedByUserId() != null)
            filterOptions.setExportedByUserId(exportDataFormBean.getExportedByUserId());

        if (exportDataFormBean.getExportLogId() != null)
            filterOptions.setExportLogId(exportDataFormBean.getExportLogId());

        if (exportDataFormBean.getExportTypeId() != null)
            filterOptions.setExportTypeId(exportDataFormBean.getExportTypeId());

        if (exportDataFormBean.getProgramId() != null)
            filterOptions.setProgramId(exportDataFormBean.getProgramId());

        if (exportDataFormBean.getVeteranId() != null)
            filterOptions.setVeteranId(exportDataFormBean.getVeteranId());

        String filePath = String.format("va_export_data_as_of_ts_%s.csv", System.nanoTime());
        filterOptions.setFilePath(filePath);

        return filterOptions;
    }

    private Map<String, String> createFormulaeMap(
            Iterable<AssessmentVariableDto> avFormulaeList) {
        Map<String, String> formulaeMap = Maps.newHashMap();
        for (AssessmentVariableDto dto : avFormulaeList) {
            formulaeMap.put(dto.getDisplayName(), dto.getDisplayText());
        }
        return formulaeMap;
    }


    @Override
    @Transactional
    public AssessmentDataExport downloadExportData(Integer userId,
                                                   int exportLogId, String comment) {

        // create Assessment Data Export from export log
        ExportLog exportLog = exportLogRepository.findOne(exportLogId);
        AssessmentDataExport ade = AssessmentDataExport.createFromExportLog(exportLog);
        ade.setExportLogId(exportLog.getExportLogId());

        // create a new entry in the export log audit saving the comments plus also the clinician who is downloading this export log
        ExportLogAudit exportLogAudit=new ExportLogAudit();
        exportLogAudit.setExportLog(exportLog);
        exportLogAudit.setExportedByUser(userRepository.findByUserId(userId));
        exportLogAudit.setComment(comment);
        exportLogAudit.setDateUpdated(new Date());
        exportLogAuditRepository.create(exportLogAudit);

        return ade;
    }

    @Override
    @Transactional
    public AssessmentDataExport getAssessmentDataExport(Map<String, Table<String, String, String>> dd,
                                                        ExportDataFormBean exportDataFormBean) {

        if (exportDataFormBean.getHasParameter()) {
            AssessmentDataExport assessmentDataExport = generateAssessmentDataExport(dd, exportDataFormBean);
            saveDataExportAsZip(dd, assessmentDataExport);

            return assessmentDataExport;
        }

        return null;
    }

    @Override
    public void takeAssessmentSnapShot() {
        Map<String, Table<String, String, String>> dd=dds.createDataDictionary();
        Date lastSnapshotDate=exportLogRepository.findLastSnapshotDate();
        ExportDataFormBean exportDataFormBean=new ExportDataFormBean();
        exportDataFormBean.setFromAssessmentDate(LocalDate.fromDateFields(lastSnapshotDate).plusDays(1).toDate());
        getAssessmentDataExport(dd, exportDataFormBean);
    }

    private AssessmentDataExport generateAssessmentDataExport(Map<String, Table<String, String, String>> dd, ExportDataFormBean exportDataFormBean) {
        // ***Step#1*** Use the passed in filter criteria to pull in the matching assessments
        List<VeteranAssessment> matchingAssessments = veteranAssessmentService.searchVeteranAssessmentForExport(exportDataFormBean);

        // ***Step#2*** get data dictionary (optimized with table questions) + usr responses grouped by assessment and every
        // module
        Map<String, Map<String, String>> prefetchedData = prepareData(dd, matchingAssessments, exportDataFormBean.getExportTypeId());

        // ***Step#3*** prepare exportData from matching assessments and against the data dictionary
        List<List<DataExportCell>> dataExportRows = createDataExportReport(dd, matchingAssessments, exportDataFormBean.getExportTypeId(), prefetchedData);

        AssessmentDataExport assessmentDataExport = new AssessmentDataExport();
        assessmentDataExport.setHeaderAndRows(dataExportRows);
        DataExportFilterOptions filterOptions = createFilterOptions(exportDataFormBean);
        assessmentDataExport.setFilterOptions(filterOptions);
        return assessmentDataExport;
    }

    /**
     * method to create a zip file and pack data-dict and data-export
     *
     * @param dd         represent the data dictionary created at this time
     * @param dataExport csv data consisting of data export
     */
    private void saveDataExportAsZip(Map<String, Table<String, String, String>> dd, AssessmentDataExport dataExport) {
        ByteArrayOutputStream zippedBaos = new ByteArrayOutputStream();
        String zipFileName = dedUtil.createZipFor(dd, dataExport, zippedBaos);

        //extract the byte[] so we can save them
        dataExport.getFilterOptions().setFilePath(zipFileName);
        byte[] zippedBytes = zippedBaos.toByteArray();
        dataExport.setExportZip(zippedBytes);
        saveExportLog(dataExport);
    }

    private ExportLog saveExportLog(AssessmentDataExport dataExport) {

        // Add an entry to the exportLog table
        ExportLog exportLog = createExportLogFromOptions(dataExport.getFilterOptions());
        exportLog.setExportZip(dataExport.getExportZip());
        exportLogRepository.create(exportLog);
        return exportLog;
    }

    /**
     * This method
     * <p/>
     * <ol>
     * <p/>
     * <li>
     * traverses every Assessment and for every Assessment, it traverses every Survey, collects the user Response, and
     * then adjust the Survey Dictionary for Survey. This merges export names (export names can vary for each assessment
     * depending on the table questions). This is necessary to merge and collect distinct columns of each assessment's
     * export names to allows aligning veteran rows. It saves set of merged survey columns with a key of
     * "SURVEY_DICTIONARY_KEY_<surveyName></li>
     * <p/>
     * <li>
     * since this method captures the optimized survey dictionary, while doing so, it has to collect user responses for
     * the survey, which is time consuming--so this method also saves those user responses under the key of
     * USR_RESPONSE_KEY_<veteranAssessmentId>_<surveyName></li>
     * <p/>
     * <li>and just before returning, it also captures Variable formulae for every assessments under the key of
     * FORMULAE_KEY_<veteranAssessmentId></li>
     * <p/>
     * </ol>
     * <p/>
     * PS: all the data this method captures for future processing is a transaction data, except the survey dictionary,
     * which is mostly static data
     *
     * @param matchingAssessments
     * @param identifiedExportType
     * @return
     */
    private Map<String, Map<String, String>> prepareData(Map<String, Table<String, String, String>> dd,
                                                         List<VeteranAssessment> matchingAssessments,
                                                         Integer identifiedExportType) {

        String exportNameKey = msgSrc.getMessage("data.dict.column.var.name", null, null);
        String answerTypeKey = msgSrc.getMessage("data.dict.column.answer.type.other", null, null);
        boolean show = ExportTypeEnum.DEIDENTIFIED.getExportTypeId() != identifiedExportType;

        Map<String, Map<String, String>> miscDataMap = Maps.newHashMap();

        // we want to see surveys sorted (columns are children of surveys) in the report
        Map<String, String> surveyNamesMap = Maps.newTreeMap();

        for (VeteranAssessment assessment : matchingAssessments) {
            List<SurveyMeasureResponse> smrList = assessment.getSurveyMeasureResponseList();

            for (String surveyName : dd.keySet()) {
                Map<String, String> usrRespMap = srh.prepareSurveyResponsesMap(surveyName, smrList, show);
                if (usrRespMap == null) {
                    continue;
                }

                miscDataMap.put(usrRespKey(assessment, surveyName), usrRespMap);

                // get the table data (one sheet) for this survey
                Table<String, String, String> surveyDD = dd.get(surveyName);

                // extract all rows/columnValues for column key=data.dict.column.var.name
                Map<String, String> ddColumnData = surveyDD.column(exportNameKey);
                // extract all answerType is Other or not
                miscDataMap.put(answerTypeOtherKey(surveyName), createExportNameOtherMap(surveyDD.column(answerTypeKey)));

                buildDictionaryHeaderFor(surveyName, syncTableQ(usrRespMap, ddColumnData), miscDataMap);

                surveyNamesMap.put(surveyName, miscDataMap.get(dictHdrKey(surveyName)).toString());
            }
        }

        // collect all the formulae for these matching Assessments
        miscDataMap.putAll(buildFormulaeMapForMatchingAssessments(matchingAssessments));
        miscDataMap.put(surveyNamesKey(), surveyNamesMap);
        return miscDataMap;
    }

    private Map<String, String> createExportNameOtherMap(Map<String, String> ddColumnOtherData) {
        Map<String, String> m = Maps.newHashMap();
        for (String value : ddColumnOtherData.values()) {
            String v[] = value.split("[$]");
            if (!v[0].isEmpty()) {
                m.put(v[0], v[1]);
            }
        }
        return m;
    }

    private String answerTypeOtherKey(String surveyName) {
        return String.format("%s__%s", surveyName, "ANSWER_TYPE_OTHER");
    }

    private void buildDictionaryHeaderFor(String surveyName,
                                          Map<String, String> syncTableQ,
                                          Map<String, Map<String, String>> miscDataMap) {

        String surveyDictionaryKey = dictHdrKey(surveyName);
        Map<String, String> currentSurveyDictionary = miscDataMap.get(surveyDictionaryKey);

        if (currentSurveyDictionary == null) {
            miscDataMap.put(surveyDictionaryKey, syncTableQ);
        } else {
            currentSurveyDictionary.putAll(syncTableQ);
        }
    }

    private String formulaKey(VeteranAssessment va) {
        return String.format("FORMULAE_KEY_%s", va.getVeteranAssessmentId());
    }

    private String usrRespKey(VeteranAssessment assessment, String surveyName) {
        return String.format("USR_RESPONSE_KEY_%s_%s", assessment.getVeteranAssessmentId(), surveyName);

    }

    private void saveMultiSelectResponses(
            Multimap<String, DataExportCell> multiSelectMap,
            Entry<String, String> entry, DataExportCell oneCell) {

        String key = entry.getKey();
        key = key.substring(0, key.lastIndexOf("_"));
        multiSelectMap.put(key, oneCell);
    }

    @Override
    public void setMessageSource(MessageSource msgSrc) {
        this.msgSrc = msgSrc;
    }

    private void setMissingCellsToZero(Collection<DataExportCell> cells) {
        for (DataExportCell cell : cells) {
            if (!cell.isOther() && srh.miss().equals(cell.getCellValue())) {
                cell.setCellValue("0");
            }
        }
    }

    /**
     * method to traverse user responses and look for table questions
     * <p/>
     * for every table question, this method will add a row in data dictionary
     *
     * @param usrRespMap
     * @return
     */
    private Map<String, String> syncTableQ(Map<String, String> usrRespMap,
                                           Map<String, String> dataDictionarySurveyColumns) {

        // map of data dictionary row id and column name (no values)
        Map<String, String> ddColumnData = Maps.newTreeMap();
        ddColumnData.putAll(dataDictionarySurveyColumns);

        // keep data for modified column data and will merge with ddColumnData after finishing the traversing
        Map<String, String> modColumnData = Maps.newTreeMap();
        // keep track of rows to delete from ddColumn--the rows will be deleted as they are modified and preserved in
        // modColumnData
        List<String> toBeDelKeys = Lists.newArrayList();

        Map<String, String> tableQuestionResponseCntr = Maps.newHashMap();

        Set<String> usrRespExportNames = usrRespMap.keySet();

        for (String usrRespExportName : usrRespExportNames) {
            if (usrRespExportName.contains("~")) {
                String[] exportNamesParts = Strings.split(usrRespExportName, '~');

                String usrExportName = exportNamesParts[0];
                String usrTQIndex = exportNamesParts[1];

                // now look for this export name in the columnData []
                for (Entry<String, String> entry : ddColumnData.entrySet()) {

                    if (entry.getValue().equals(usrExportName)) {
                        String expotNameKeyPrefix = dds.getExportNameKeyPrefix();
                        modColumnData.put(entry.getKey().replaceAll(expotNameKeyPrefix, expotNameKeyPrefix + usrTQIndex + "_"), usrRespExportName);
                        toBeDelKeys.add(entry.getKey());
                    }
                }

                // also update userRespMap with TableQuestion response counter. Data Dictionary has a table Question
                // response counter for this export name
                // try to create the same export name here. we may end up having some redundant names here but thats ok.
                // as far as we have the one that matches with data dictionary table question response counter we are
                // fine
                handleTableQuestionResponse(usrExportName, tableQuestionResponseCntr, Integer.parseInt(usrTQIndex));
            }
        }

        // merge table question response counter with usrRespMap
        usrRespMap.putAll(tableQuestionResponseCntr);

        // truncate the ddColumnData (remove all keys which are to be replaced with Table Questions Tabular rows)
        for (String key : toBeDelKeys) {
            ddColumnData.remove(key);
        }

        // update the data dictionary row-id with the set of new row id and column names with the new column names
        ddColumnData.putAll(modColumnData);

        return ddColumnData;
    }

    private void handleTableQuestionResponse(String usrExportName,
                                             Map<String, String> tableQuestionResponseCntr, int usrTQIndex) {

        String keyName = dds.createTableResponseVarName(usrExportName);
        String existingIndexAsStr = tableQuestionResponseCntr.get(keyName);

        int nextIndex = usrTQIndex + 1; // incoming index is zero based and we want to use this as a counter

        if (existingIndexAsStr != null) {
            int existingIndex = Integer.parseInt(existingIndexAsStr);
            nextIndex = Math.max(existingIndex, nextIndex);
        }
        tableQuestionResponseCntr.put(keyName, String.valueOf(nextIndex));
    }

}