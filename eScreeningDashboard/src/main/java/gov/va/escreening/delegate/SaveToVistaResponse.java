package gov.va.escreening.delegate;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import gov.va.escreening.security.EscreenUser;

import java.util.List;
import java.util.Map;

/**
 * Created by munnoo on 5/13/15.
 */
public class SaveToVistaResponse {
    private final int veteranAssessmentId;
    private final EscreenUser escreenUser;
    private Table<PendingOperation, MsgType, String> msgsTbl = HashBasedTable.create();
    private List<PendingOperation> pendingOperations = Lists.newArrayList(PendingOperation.values());

    public SaveToVistaResponse(int veteranAssessmentId, EscreenUser escreenUser) {
        this.veteranAssessmentId = veteranAssessmentId;
        this.escreenUser = escreenUser;
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
        add(po, MsgType.sysErr, errorMsg);
    }

    public void requestDone(PendingOperation pendingOperationName) {
        if (!hasErrors(pendingOperationName)) {
            this.pendingOperations.remove(pendingOperationName);
        }
    }

    public void addFailedMsg(PendingOperation po, String failedMsg) {
        add(po, MsgType.failMsg, failedMsg);
    }

    public boolean isDone(PendingOperation operation) {
        return !this.pendingOperations.contains(operation);
    }

    private boolean hasErrors(PendingOperation po) {
        Map<MsgType, String> msgTypeMap = msgsTbl.row(po);

        //boolean clean = msgTypeMap == null || msgTypeMap.isEmpty();
        boolean errorFree = msgTypeMap.get(MsgType.sysErr) == null || msgTypeMap.get(MsgType.sysErr).isEmpty();
        errorFree &= msgTypeMap.get(MsgType.failMsg) == null || msgTypeMap.get(MsgType.failMsg).isEmpty();
        errorFree &= msgTypeMap.get(MsgType.usrErr) == null || msgTypeMap.get(MsgType.usrErr).isEmpty();

        return !errorFree;
    }

    public List<PendingOperation> getPendingOperations() {
        return pendingOperations;
    }

    public Map<PendingOperation, String> getMsgsFor(MsgType msgType) {
        return msgsTbl.column(msgType);
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
