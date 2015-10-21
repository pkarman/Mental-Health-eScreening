package gov.va.escreening.delegate;

import com.google.common.base.Objects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import gov.va.escreening.domain.AssessmentStatusEnum;
import gov.va.escreening.dto.CallResult;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.AssessmentEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by munnoo on 5/13/15.
 */
public class SaveToVistaContext {
    private final Logger logger = LoggerFactory.getLogger(SaveToVistaContext.class);

    private final int veteranAssessmentId;
    private final EscreenUser escreenUser;
    private final AssessmentEngineService assessmentEngineService;
    private Table<PendingOperation, MsgType, String> msgsTbl = HashBasedTable.create();
    private List<PendingOperation> pendingOperations = Lists.newArrayList(PendingOperation.values());
    private List<CallResult> callResults = Lists.newArrayList();

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("veteranAssessmentId", veteranAssessmentId)
                .add("pendingOperations", pendingOperations)
                .toString();
    }

    public SaveToVistaContext(int veteranAssessmentId, EscreenUser escreenUser, AssessmentEngineService assessmentEngineService) {
        this.veteranAssessmentId = veteranAssessmentId;
        this.escreenUser = escreenUser;
        this.assessmentEngineService = assessmentEngineService;
    }

    public int getVeteranAssessmentId() {
        return veteranAssessmentId;
    }

    public List<CallResult> getCallResults() {
        if (hasAnyError()) {
            buildCallResultsWithErr();
        }
        return callResults;
    }

    private boolean hasAnyError() {
        for (PendingOperation po : PendingOperation.values()) {
            if (hasErrors(po)) {
                return true;
            }
        }
        return false;
    }

    public void addCallResult(CallResult callResult) {
        this.callResults.add(callResult);
    }


    public boolean isSuccessful() {
        return pendingOperations.isEmpty() &&
                getMsgsFor(MsgType.usrErr).isEmpty() &&
                getMsgsFor(MsgType.warnMsg).isEmpty() &&
                getMsgsFor(MsgType.sysErr).isEmpty() &&
                getMsgsFor(MsgType.failMsg).isEmpty();
    }

    public void addUserError(PendingOperation po, String msgDetails) {
        add(po, MsgType.usrErr, msgDetails);
    }


    private void add(PendingOperation po, MsgType msgType, String msgDetails) {
        msgsTbl.put(po, msgType, msgDetails);
    }

    public void addWarnMsg(PendingOperation po, String warmMsg) {
        add(po, MsgType.warnMsg, warmMsg);
    }

    public void addSuccess(PendingOperation po, String successMsg) {
        add(po, MsgType.succMsg, successMsg);
    }

    public void addSysErr(PendingOperation po, String errorMsg) {
        if(errorMsg == null)
        {
            errorMsg = "NULL";
        }
        add(po, MsgType.sysErr, errorMsg);
    }

    public void requestDone(PendingOperation pendingOperationName) {
        logger.trace("done requested by {}, current pending operations {}", pendingOperationName, this.pendingOperations);
        if (!hasErrors(pendingOperationName)) {
            this.pendingOperations.remove(pendingOperationName);
        }
    }

    public void addFailedMsg(PendingOperation po, String failedMsg) {
        add(po, MsgType.failMsg, failedMsg);
    }

    public boolean opFailed(PendingOperation operation) {
        return this.pendingOperations.contains(operation);
    }

    private boolean hasErrors(PendingOperation po) {
        Map<MsgType, String> msgTypeMap = msgsTbl.row(po);

        boolean errorFree = msgTypeMap.get(MsgType.sysErr) == null || msgTypeMap.get(MsgType.sysErr).isEmpty();
        errorFree &= msgTypeMap.get(MsgType.failMsg) == null || msgTypeMap.get(MsgType.failMsg).isEmpty();
        errorFree &= msgTypeMap.get(MsgType.usrErr) == null || msgTypeMap.get(MsgType.usrErr).isEmpty();

        boolean hasErrors=!errorFree;
        logger.trace("hasErrors: MsgTypes for PendingOperation {} are {}",po, msgTypeMap);
        return hasErrors;
    }

    public List<PendingOperation> getPendingOperations() {
        return pendingOperations;
    }

    public Map<PendingOperation, String> getMsgsFor(MsgType msgType) {
        return msgsTbl.column(msgType);
    }

    private void buildCallResultsWithErr() {
        buildCallResults(SaveToVistaContext.MsgType.succMsg);
        buildCallResults(SaveToVistaContext.MsgType.usrErr);
        buildCallResults(SaveToVistaContext.MsgType.sysErr);
        buildCallResults(SaveToVistaContext.MsgType.failMsg);
        buildCallResults(SaveToVistaContext.MsgType.warnMsg);

        buildCallResultsForPendingOperations(); 
        Map<PendingOperation, String> failedOps = msgsTbl.column(MsgType.failMsg);
        
        //requirements dictate that we will not be setting the assessment into an Error state if the only error was an MHA save error 
        if(failedOps.size() != 1 || !failedOps.containsKey(PendingOperation.sendMhaToVista)){
            assessmentEngineService.transitionAssessmentStatusTo(this.veteranAssessmentId, AssessmentStatusEnum.ERROR);
        }
    }

    private void buildCallResultsForPendingOperations() {
        for (SaveToVistaContext.PendingOperation po : pendingOperations) {
            callResults.add(new CallResult(true, String.format("Operation '%s' could not be performed because of previous errors", po.getDescription()), null));
        }
    }

    private void buildCallResults(SaveToVistaContext.MsgType msgType) {
        Map<SaveToVistaContext.PendingOperation, String> msgsMap = getMsgsFor(msgType);
        for (SaveToVistaContext.PendingOperation po : msgsMap.keySet()) {
            callResults.add(createCallResult(msgsMap.get(po), po, msgType));
        }
    }

    private CallResult createCallResult(String msg, SaveToVistaContext.PendingOperation po, SaveToVistaContext.MsgType msgType) {
        String usrMsg = msgType.isErr() ? String.format("%s operation failed", po.getDescription()) : msg;
        String sysMsg = msgType.isErr() ? msg : null;
        return new CallResult(msgType.isErr(), usrMsg, sysMsg);
    }

    public boolean hasError() {
        for (CallResult cr : this.callResults) {
            if (cr.getHasError()) {
                return true;
            }
        }
        return false;
    }

    public EscreenUser getEscUserId() {
        return escreenUser;
    }

    public enum PendingOperation {
        veteran("Veteran Information"),
        sendMhaToVista("Send MHA Record to Vista"),
        saveMhaToDb("Save MHA Record in Database"),
        mha("Mental Health Assessment"),
        cprs("Computerized Patient Record System"),
        hf("Mental Health Factors"),
        tbi("Traumatic Brain Injury"),
        pain_scale("Basic Pain Scale");

        final private String description;

        PendingOperation(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum MsgType {
        usrErr(true),
        sysErr(true),
        warnMsg(true),
        failMsg(true),
        succMsg(false);

        private final boolean err;

        MsgType(boolean err) {
            this.err = err;
        }

        public boolean isErr() {
            return err;
        }
    }

    /**
     * Created by munnoo on 5/13/15.
     */
    public enum MsgKey {
        usr_err_vet__not_found,
        usr_err_vet__failed_mapping,
        usr_err_vet__verification,
        usr_warn_mha__answers_blank,
        usr_pass_mha__success,
        usr_err_mha__failed,
        sys_err_mha__mhp_exception,
        usr_pass_mha__mhp_success,
        usr_pass_mha__mhar_success,
        sys_err_mha__mhar_exception,
        usr_pass_mha__mhtr_success,
        usr_err_mha__mhtr_failed,
        sys_err_mha__exception,
        usr_pass_cprs__gen_success,
        sys_err_cprs__gen_exception,
        usr_pass_cprs__saved_success,
        sys_err_cprs__saved_exception,
        usr_pass_hf__saved_success,
        sys_err_hf__saved_exception,
        usr_pass_tbi__saved_success,
        sys_err_tbi__saved_exception,
        sys_err_ps__saved_exception,
        usr_err_ps__saved_failed,
        usr_pass_ps__saved_success
    }
}
