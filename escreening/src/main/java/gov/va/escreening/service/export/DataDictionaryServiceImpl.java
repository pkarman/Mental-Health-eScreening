package gov.va.escreening.service.export;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.*;
import gov.va.escreening.entity.*;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.ValidationRepository;
import gov.va.escreening.service.AssessmentVariableService;
import gov.va.escreening.util.DataDictExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.bouncycastle.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service("dataDictionaryService")

public class DataDictionaryServiceImpl implements DataDictionaryService, MessageSourceAware, BeanFactoryAware {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Lock mainThreadLock = new ReentrantLock();
    private final Lock workerThreadLock = new ReentrantLock();
    @Resource(name = "dataDictionaryHelper")
    DataDictionaryHelper ddh;
    @Resource(name = "theDataDictionary")
    DataDictionary dd;
    @Resource(type = ValidationRepository.class)
    ValidationRepository vr;
    @Resource(type = AssessmentVariableService.class)
    AssessmentVariableService avs;
    @Resource(name = "dataDictAsExcelUtil")
    DataDictExcelUtil ddeutil;
    Callable<DataDictionary> ddCallable;
    @Resource(type = SurveyRepository.class)
    SurveyRepository sr;
    private MessageSource msgSrc;
    private ExecutorService taskExecuter;
    private BeanFactory beanFactory;

    @PostConstruct
    private void initDataDictionaryCallable() {
        taskExecuter = Executors.newSingleThreadExecutor();
        ddCallable = new Callable<DataDictionary>() {
            @Override
            public DataDictionary call() {
                tryUpdateExcelWorkbook();
                return dd;
            }
        };
    }

    @PreDestroy
    private void preDestroy() {
        logger.warn("Shutting down DataDictionary Executer" + taskExecuter);
        taskExecuter.shutdown();
    }

    private Multimap<Integer, String> buildMeasureValidationMap() {

        List<Validation> validations = vr.findAll();
        /**
         * Validations for a free text measure (only) are defined in measure_validation table which relate a measure to
         * a validation. A measure can have more than one validation which is applied.
         *
         * The way validations work is there is a type id (taken from the validation table), combined with some value in
         * the measure_validation table. Each type only has one valid value in measure_validation table.
         *
         * For example minValue will have an entry in measure_validation.number_value to indicate the minimum allowable
         * value.
         *
         * the property this{@link #measureValidationsMap} will keep a map of measure id with a list of validation in
         * the form 'Min Value=1970, Max Value=2020, Exact Number=4' would mean that the measure answer is a date and it
         * should be between 1970 and 2020 and must contain century too
         * */
        Multimap<Integer, String> measureValidationsMap = LinkedHashMultimap.create();

        for (Validation v : validations) {
            for (MeasureValidation mv : v.getMeasureValidationList()) {
                measureValidationsMap.put(mv.getMeasure().getMeasureId(), buildMeasureValidation(mv));
            }
        }
        return measureValidationsMap;
    }

    private String buildMeasureValidation(MeasureValidation mv) {
        Validation v = mv.getValidation();
        String mvStr = null;
        switch (v.getDataType()) {
            case "string":
                mvStr = String.format("%s=%s", v.getDescription(), mv.getTextValue());
                break;
            case "number":
                mvStr = String.format("%s=%s", v.getDescription(), mv.getNumberValue());
                break;
            default:
                mvStr = "";
                break;
        }
        return mvStr;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean tryPrepareDataDictionary(boolean force) {
        boolean mainThreadAvailable = false;
        boolean retVal = false;
        try {
            mainThreadAvailable = mainThreadLock.tryLock();
        } finally {
            if (mainThreadAvailable) {
                retVal = proceedMainTask(force);
                mainThreadLock.unlock();
            }
            return retVal;
        }
    }

    private boolean proceedMainTask(boolean force) {
        logger.debug("1-tryPrepareDataDictionary {}", dd);
        if (force || !dd.isReady()) {
            updateDataDictionary();
            logger.debug("2-tryPrepareDataDictionary {}", dd);

            logger.debug("3-tryPrepareDataDictionary {}", dd);
            if (!force) {
                asyncExecLongRunningTask();
            } else {
                updateExcelWorkbook();
            }
            logger.debug("4-tryPrepareDataDictionary {}", dd);
        } else {
            logger.debug("5-tryPrepareDataDictionary {dd already ready} {}", dd);
            Preconditions.checkState(dd.isReady(), "Data Dictionary must be ready to be used...");
        }
        return dd.isReady();
    }

    private void asyncExecLongRunningTask() {
        logger.debug("1-asyncExecLongRunningTask {}", dd);
        try {
            Future<DataDictionary> future = taskExecuter.submit(ddCallable);
            // Waits if necessary for at most 120 seconds for the
            // DataDictionary to complete, and then retrieves its result, if available.
            future.get(120, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Throwables.propagate(e);
        } catch (ExecutionException e) {
            Throwables.propagate(e);
        } catch (TimeoutException e) {
            Throwables.propagate(e);
        }
        logger.debug("2-asyncExecLongRunningTask {}", dd);
    }

    private void tryUpdateExcelWorkbook() {
        boolean workerThreadAvailable = false;
        try {
            workerThreadAvailable = workerThreadLock.tryLock();
        } finally {
            if (workerThreadAvailable) {
                proceedWorkerTask();
                workerThreadLock.unlock();
            }
        }
    }

    private void proceedWorkerTask() {
        logger.debug("1-tryUpdateExcelWorkbook {}", dd);
        updateExcelWorkbook();
        logger.debug("2-tryUpdateExcelWorkbook {}", dd);
    }

    private void updateExcelWorkbook() {
        logger.debug("1-updateExcelWorkbook {}", dd);
        dd.setWorkbook(new HSSFWorkbook());
        logger.debug("2-updateExcelWorkbook {}", dd);
        ddeutil.buildDdAsExcel(dd.getWorkbook());
        logger.debug("3-updateExcelWorkbook {}", dd);
        dd.markReady();
        logger.debug("4-updateExcelWorkbook {}", dd);
    }

    private void updateDataDictionary() {
        try {
            // partition all survey with its list of measures
            Multimap<Survey, Measure> surveyMeasuresMap = avs.buildSurveyMeasuresMap();
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("%s--%s", "total surveys found", surveyMeasuresMap.size()));
            }
            if (logger.isTraceEnabled()) {
                logger.trace(String.format("details of every survey with its measures are--%s", surveyMeasuresMap));
            }

            // read all AssessmenetVariables having formulae
            Collection<AssessmentVariable> formulas = avs.findAllFormulas();
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("%s--%s", "total formulas found", formulas.size()));
            }
            if (logger.isTraceEnabled()) {
                logger.trace(String.format("details of formulas are--%s", formulas));
            }

            // read all measures of free text and its validations
            Multimap<Integer, String> ftMvMap = buildMeasureValidationMap();
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("%s--%s", "total free text measures validation found", ftMvMap.size()));
            }
            if (logger.isTraceEnabled()) {
                logger.trace(String.format("details of free text measures validation are--%s", ftMvMap));
            }

            // bookkeeping set to verify that each and every assessment var of formula type has been utilized in creation of
            // the data dictionary
            Set<String> formulaeAvTouched = Sets.newLinkedHashSet();

            for (Survey s : surveyMeasuresMap.keySet()) {
                DataDictionarySheet sheet = buildSheetFor(s, surveyMeasuresMap.get(s), ftMvMap, formulas, formulaeAvTouched);
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("%s--%s||%s-%s", "sheet name", s.getName(), "sheet size (total number of elements) (rows*columns)", sheet.size()));
                }
                if (logger.isTraceEnabled()) {
                    logger.debug(String.format("details of sheet are--%s", sheet));
                }

                // bind the survey (or module with its sheet)
                dd.put(s.getName(), sheet);

                //logger.debug("sheet data for Survey={} =>> {}", s.getName(), sheet);
            }

            if (logger.isTraceEnabled()) {
                Set<String> avUsedInDataDictionary = Sets.newHashSet(formulaeAvTouched);

                String refAssessmentVars = getRefAssessmentVars(formulas);
                Set<String> avReference = Sets.newHashSet(Strings.split(refAssessmentVars, ','));

                Set<String> unusedAv = Sets.difference(avUsedInDataDictionary, avReference);
                logger.trace(String.format("AvSizeUsedInDD:%s==AvReferenceDD:%s==AvUnusedInDD:%s", avUsedInDataDictionary.size(), avReference.size(), unusedAv));
            }
        } catch (Exception e) {
            logger.error(Throwables.getRootCause(e).getMessage());
            Throwables.propagate(e);
        }
    }

    private String getRefAssessmentVars(Collection<AssessmentVariable> avList) {
        Iterable<String> displayNames = Iterables.transform(avList, new Function<AssessmentVariable, String>() {
            public String apply(AssessmentVariable input) {
                // extract display names from Assessment Variables
                return (input == null) ? null : input.getDisplayName();
            }
        });
        String joinedDisplayNames = Joiner.on(",").skipNulls().join(displayNames);
        return joinedDisplayNames;
    }

    private DataDictionarySheet buildSheetFor(Survey s,
                                              Collection<Measure> surveyMeasures, Multimap mvMap,
                                              Collection<AssessmentVariable> avList, Set<String> avUsed) {

        DataDictionarySheet t = ddh.buildDataDictionaryFor(s, surveyMeasures, mvMap, avList, avUsed);

        return t;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.msgSrc = messageSource;
    }

    @Override
    public String getExportNameKeyPrefix() {
        return ddh.EXPORT_KEY_PREFIX;
    }

    @Override
    public String createTableResponseVarName(String exportName) {
        return ddh.createTableResponseVarName(exportName);
    }

    @Override
    public List<String> findAllFormulas(String moduleName) {
        final DataDictionarySheet ddSheet = dd.findSheet(moduleName);
        final Map<String, Map<String, String>> rowMap = ddSheet.rowMap();
        List<String> formulaNames = Lists.newArrayList();

        for (String rowKey : rowMap.keySet()) {
            if (rowKey.startsWith(ddh.FORMULA_KEY_PREFIX)) {
                final Map<String, String> row = rowMap.get(rowKey);
                formulaNames.add(row.get(ddh.msg("var.name")));
            }
        }
        return formulaNames;
    }

    @Override
    public boolean invalidateDataDictionary() {
        boolean mainThreadAvailable = false;
        try {
            mainThreadAvailable = mainThreadLock.tryLock();
        } finally {
            if (mainThreadAvailable) {
                dd.markNotReady();
                mainThreadLock.unlock();
            }
            logger.warn(String.format("Data Dictionary cache %s invalidated as Data Export %s operation", mainThreadAvailable ? "has been" : "could not be", mainThreadAvailable ? "is not in" : "is in"));
            return mainThreadAvailable;
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
