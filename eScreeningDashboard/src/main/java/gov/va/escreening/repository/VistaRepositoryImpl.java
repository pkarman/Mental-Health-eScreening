package gov.va.escreening.repository;

import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.exception.VistaLinkException;
import gov.va.escreening.exception.VistaLockedAccountException;
import gov.va.escreening.exception.VistaVerificationException;
import gov.va.escreening.util.VeteranUtil;
import gov.va.escreening.vista.VistaBrokerVistaLinkConnectionSpec;
import gov.va.escreening.vista.VistaRpcParam;
import gov.va.escreening.vista.dto.DialogComponent;
import gov.va.escreening.vista.dto.DialogPrompt;
import gov.va.escreening.vista.dto.VistaBrokerUserInfo;
import gov.va.escreening.vista.dto.VistaClinicAppointment;
import gov.va.escreening.vista.dto.VistaClinicalReminderAndCategory;
import gov.va.escreening.vista.dto.VistaLocation;
import gov.va.escreening.vista.dto.VistaNoteTitle;
import gov.va.escreening.vista.dto.VistaVeteranAppointment;
import gov.va.escreening.vista.dto.VistaVeteranClinicalReminder;
import gov.va.escreening.vista.extractor.DialogComponentListExtractor;
import gov.va.escreening.vista.extractor.DialogPromptListExtractor;
import gov.va.escreening.vista.extractor.OrqptClinicPatientsListExtractor;
import gov.va.escreening.vista.extractor.OrqqpxRemindersListExtractor;
import gov.va.escreening.vista.extractor.OrwptApptlstListExtractor;
import gov.va.escreening.vista.extractor.OrwptLast5ListExtractor;
import gov.va.escreening.vista.extractor.OrwptPtinqExtractor;
import gov.va.escreening.vista.extractor.OrwptSelectExtractor;
import gov.va.escreening.vista.extractor.Orwu1NewlocListExtractor;
import gov.va.escreening.vista.extractor.PxrmReminderAndCategoryListExtractor;
import gov.va.escreening.vista.extractor.StringExtractor;
import gov.va.escreening.vista.extractor.TiuGetPnTitlesListExtractor;
import gov.va.escreening.vista.extractor.VistaRecordExtractor;
import gov.va.escreening.vista.extractor.XusKaajeeGetUserInfoExtractor;
import gov.va.med.exception.FoundationsException;
import gov.va.med.vistalink.adapter.cci.VistaLinkAppProxyConnectionSpec;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnection;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnectionFactory;
import gov.va.med.vistalink.adapter.cci.VistaLinkConnectionSpec;
import gov.va.med.vistalink.adapter.cci.VistaLinkDuzConnectionSpec;
import gov.va.med.vistalink.adapter.cci.VistaLinkVpidConnectionSpec;
import gov.va.med.vistalink.adapter.record.VistaLinkFaultException;
import gov.va.med.vistalink.adapter.spi.VistaLinkManagedConnectionFactory;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcRequestFactory;
import gov.va.med.vistalink.rpc.RpcResponse;
import gov.va.med.vistalink.security.m.SecurityAccessVerifyCodePairInvalidException;
import gov.va.med.vistalink.security.m.SecurityDivisionDeterminationFaultException;
import gov.va.med.vistalink.security.m.SecurityIdentityDeterminationFaultException;
import gov.va.med.vistalink.security.m.SecurityTooManyInvalidLoginAttemptsFaultException;
import gov.va.med.vistalink.security.m.SecurityUserAuthorizationException;
import gov.va.med.vistalink.security.m.SecurityUserVerifyCodeException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.resource.ResourceException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class VistaRepositoryImpl implements VistaRepository {

	private static final Logger logger = LoggerFactory.getLogger(VistaRepositoryImpl.class);

	@Resource(name = "allowedClinicalReminders")
	List<String> allowedClinicalReminders;

	@Autowired
	private VistaLinkManagedConnectionFactory vistaLinkManagedConnectionFactory;
	private boolean useProprietaryMessageFormat = true;
	private int timeOut = 10000;
	
	@Value(value="${sample.patient.ien}")
	private String samplePatientIen;

	@Override
	public String callRpc(String division, String vpid, String duz,
			String appProxyName, String rpcContext, String rpcName,
			List<VistaRpcParam> vistaRpcParamList) {

		String results = null;

		// 1. Create the correct connection spec.
		VistaLinkConnectionSpec connSpec = null;

		// create connection spec
		if (vpid != null) {
			connSpec = new VistaLinkVpidConnectionSpec(division, vpid);
		} else if (duz != null) {
			connSpec = new VistaLinkDuzConnectionSpec(division, duz);
		} else if (appProxyName != null) {
			connSpec = new VistaLinkAppProxyConnectionSpec(division, appProxyName);
		} else {
			// throw exception.
			return "Could not create connectionSpec";
		}

		VistaLinkConnection vistaLinkConnection = null;

		try {
			// 2. Managed mode:
			// Use JNDI name to get a managed connection factory.
			// VistaLinkConnectionFactory vistaLinkConnectionFactory =
			// (VistaLinkConnectionFactory) ic.lookup(jndiName);

			// 2. Non-managed mode:
			// In the future, 'vistaLinkConnectionFactory' will be returned by a
			// another method and not directly
			// instantiated. This class is for a specific PRIMARY STATION which
			// is determined by the user's DIVISION.
			VistaLinkConnectionFactory vistaLinkConnectionFactory = new VistaLinkConnectionFactory(vistaLinkManagedConnectionFactory, null);

			// 3. Get Connection
			vistaLinkConnection = (VistaLinkConnection) vistaLinkConnectionFactory.getConnection(connSpec);
			vistaLinkConnection.setTimeOut(timeOut);

			RpcRequest vReq = RpcRequestFactory.getRpcRequest();
			vReq.setTimeOut(vistaLinkConnection.getTimeOut() * 2);
			vReq.setUseProprietaryMessageFormat(useProprietaryMessageFormat);
			vReq.setRpcContext(rpcContext);

			vReq.setRpcName(rpcName);

			vReq.clearParams();

			if (vistaRpcParamList != null && vistaRpcParamList.size() > 0) {
				for (int i = 0; i < vistaRpcParamList.size(); ++i) {
					vReq.getParams().setParam(i + 1, vistaRpcParamList.get(i).getParamType(), vistaRpcParamList.get(i).getParamValue());
				}
			}

			RpcResponse vResp = vistaLinkConnection.executeRPC(vReq);

			results = vResp.getResults();
			logger.debug("Results: " + results);
		} catch (VistaLinkFaultException e) {
			logger.error("Exception thrown testRpc: ", e);
			return e.toString();
		} catch (ResourceException e) {
			logger.error("Exception thrown testRpc: ", e);
			return e.toString();
		} catch (FoundationsException e) {
			logger.error("Exception thrown testRpc: ", e);
			return e.toString();
		} finally {
			if (vistaLinkConnection != null) {
				try {
					vistaLinkConnection.close();
				} catch (ResourceException e) {
					logger.error("Exception thrown testRpc: ", e);
				}
			}
		}

		return results;
	}

	@Override
	public Boolean getPing(String division, String vpid, String duz,
			String appProxyName) {

		String result = query(division, vpid, duz, appProxyName, "XOBV VISTALINK TESTER", "XOBV TEST PING", new ArrayList<VistaRpcParam>(), new StringExtractor());

		if (StringUtils.isNotBlank(result)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<VeteranDto> searchVeteran(String division, String vpid,
			String duz, String appProxyName, String lastNameSsn) {

		VistaRpcParam vistaRpcParam = new VistaRpcParam("string", lastNameSsn);

		List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
		vistaRpcParamList.add(vistaRpcParam);

		List<VeteranDto> resultList = query(division, vpid, duz, appProxyName, "OR CPRS GUI CHART", "ORWPT LAST5", vistaRpcParamList, new OrwptLast5ListExtractor());

		if (resultList == null) {
			resultList = new ArrayList<VeteranDto>();
		}

		return resultList;
	}

	@Override
	public VeteranDto getVeteran(String division, String vpid, String duz,
			String appProxyName, String veteranIen) {

		VistaRpcParam vistaRpcParam = new VistaRpcParam("string", veteranIen);

		List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
		vistaRpcParamList.add(vistaRpcParam);

		VeteranDto veteranDto = query(division, vpid, duz, appProxyName, "OR CPRS GUI CHART", "ORWPT SELECT", vistaRpcParamList, new OrwptSelectExtractor());

		if (veteranDto != null) {
			veteranDto.setVeteranIen(veteranIen);
			veteranDto.setFullName(VeteranUtil.getFullName(veteranDto.getFirstName(), veteranDto.getMiddleName(), veteranDto.getLastName(), veteranDto.getSuffix(), null));
		}

		return veteranDto;
	}

	@Override
	public VeteranDto getVeteranDetail(String division, String vpid,
			String duz, String appProxyName, String veteranIen) {

		VistaRpcParam vistaRpcParam = new VistaRpcParam("string", veteranIen);

		List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
		vistaRpcParamList.add(vistaRpcParam);

		VeteranDto veteranDto = query(division, vpid, duz, appProxyName, "OR CPRS GUI CHART", "ORWPT PTINQ", vistaRpcParamList, new OrwptPtinqExtractor());

		return veteranDto;
	}

	@Override
	public List<VistaVeteranAppointment> getVeteranAppointments(
			String division, String vpid, String duz, String appProxyName,
			String veteranIen) {

		VistaRpcParam vistaRpcParam = new VistaRpcParam("string", veteranIen);

		List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
		vistaRpcParamList.add(vistaRpcParam);

		List<VistaVeteranAppointment> resultList = query(division, vpid, duz, appProxyName, "OR CPRS GUI CHART", "ORWPT APPTLST", vistaRpcParamList, new OrwptApptlstListExtractor());

		return resultList;
	}

	@Override
	public List<VistaVeteranClinicalReminder> getVeteranClinicalReminders(
			String division, String vpid, String duz, String appProxyName,
			String veteranIen) {

		List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
		vistaRpcParamList.add(new VistaRpcParam("string", veteranIen));

		List<VistaVeteranClinicalReminder> resultList = query(division, vpid, duz, appProxyName, "OR CPRS GUI CHART", "ORQQPX REMINDERS LIST", vistaRpcParamList, new OrqqpxRemindersListExtractor());

		filterClinicalReminders(resultList);

		return resultList;
	}

	private void filterClinicalReminders(
			List<VistaVeteranClinicalReminder> resultList) {

		for (Iterator<VistaVeteranClinicalReminder> iter = resultList.iterator(); iter.hasNext();) {
			VistaVeteranClinicalReminder vvcr = iter.next();
			if (!allowedClinicalReminders.contains(vvcr.getName().replaceAll("&", "_"))) {
				iter.remove();
			}
		}
	}

	/**
	 * 
	 * @param division
	 * @param vpid
	 * @param duz
	 * @param appProxyName
	 * @param rpcContext
	 * @param rpcName
	 * @param vistaRpcParamList
	 * @param vistaRecordExtractor
	 * @return
	 */
	public <T> T query(String division, String vpid, String duz,
			String appProxyName, String rpcContext, String rpcName,
			List<VistaRpcParam> vistaRpcParamList,
			VistaRecordExtractor<T> vistaRecordExtractor) {

		String results = null;

		// 1. Create the correct connection spec.
		VistaLinkConnectionSpec connSpec = null;

		// create connection spec
		if (vpid != null) {
			connSpec = new VistaLinkVpidConnectionSpec(division, vpid);
		} else if (duz != null) {
			connSpec = new VistaLinkDuzConnectionSpec(division, duz);
		} else if (appProxyName != null) {
			connSpec = new VistaLinkAppProxyConnectionSpec(division, appProxyName);
		} else {
			throw new IllegalArgumentException("No connection data provided to create a VistA Link Connection Spec.");
		}

		VistaLinkConnection vistaLinkConnection = null;

		try {

			// 2. Managed mode:
			// Use JNDI name to get a managed connection factory.
			// VistaLinkConnectionFactory vistaLinkConnectionFactory =
			// (VistaLinkConnectionFactory) ic.lookup(jndiName);

			// 2. Non-managed mode:
			// In the future, 'vistaLinkConnectionFactory' will be returned by a
			// another method and not directly
			// instantiated. This class is for a specific PRIMARY STATION which
			// is determined by the user's DIVISION.
			VistaLinkConnectionFactory vistaLinkConnectionFactory = new VistaLinkConnectionFactory(vistaLinkManagedConnectionFactory, null);

			// 3. Get Connection
			vistaLinkConnection = (VistaLinkConnection) vistaLinkConnectionFactory.getConnection(connSpec);
			vistaLinkConnection.setTimeOut(timeOut);

			RpcRequest vReq = RpcRequestFactory.getRpcRequest();
			vReq.setTimeOut(vistaLinkConnection.getTimeOut() * 2);
			vReq.setUseProprietaryMessageFormat(useProprietaryMessageFormat);
			vReq.setRpcContext(rpcContext);

			vReq.setRpcName(rpcName);

			vReq.clearParams();

			if (vistaRpcParamList != null && vistaRpcParamList.size() > 0) {
				for (int i = 0; i < vistaRpcParamList.size(); ++i) {
					vReq.getParams().setParam(i + 1, vistaRpcParamList.get(i).getParamType(), vistaRpcParamList.get(i).getParamValue());
				}
			}

			RpcResponse vResp = vistaLinkConnection.executeRPC(vReq);

			results = vResp.getResults();
			logger.debug("Vista Response: for " + rpcName + " \n" + results);
			return vistaRecordExtractor.extractData(results);
		} catch (VistaLinkFaultException e) {
			logger.error("Exception thrown query: ", e);
			throw new VistaLinkException(e.toString());
		} catch (ResourceException e) {
			logger.error("Exception thrown query: ", e);
			throw new VistaLinkException(e.toString());
		} catch (FoundationsException e) {
			logger.error("Exception thrown query: ", e);
			throw new VistaLinkException(e.toString());
		} finally {
			if (vistaLinkConnection != null) {
				try {
					vistaLinkConnection.close();
				} catch (ResourceException e) {
					logger.error("Exception thrown query: ", e);
				}
			}
		}
	}

	@Override
	public List<VistaNoteTitle> getNoteTitles(String division, String vpid,
			String duz, String appProxyName) {

		List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();

		List<VistaNoteTitle> resultList = query(division, vpid, duz, appProxyName, "OR CPRS GUI CHART", "TIU GET PN TITLES", vistaRpcParamList, new TiuGetPnTitlesListExtractor());

		return resultList;
	}

	@Override
	public List<VistaClinicAppointment> getAppointmentsForClinic(
			String division, String vpid, String duz, String appProxyName,
			String clinicIen, Date fromAppointmentDate, Date toAppointmentDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		String fromAppointmentDateString = sdf.format(fromAppointmentDate);
		String toAppointmentDateString = sdf.format(toAppointmentDate);

		List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
		vistaRpcParamList.add(new VistaRpcParam("string", clinicIen));
		vistaRpcParamList.add(new VistaRpcParam("string", fromAppointmentDateString));
		vistaRpcParamList.add(new VistaRpcParam("string", toAppointmentDateString));

		List<VistaClinicAppointment> resultList = query(division, vpid, duz, appProxyName, "OR CPRS GUI CHART", "ORQPT CLINIC PATIENTS", vistaRpcParamList, new OrqptClinicPatientsListExtractor());

		return resultList;
	}

	@Override
	public List<VistaClinicalReminderAndCategory> getClinicalReminderAndCategories(
			String division, String vpid, String duz, String appProxyName) {

		List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();

		List<VistaClinicalReminderAndCategory> resultList = query(division, vpid, duz, appProxyName, "OR CPRS GUI CHART", "PXRM REMINDERS AND CATEGORIES", vistaRpcParamList, new PxrmReminderAndCategoryListExtractor());

		return resultList;
	}

	@Override
	public List<DialogPrompt> getDialogPrompt(String division, String vpid,
			String duz, String appProxyName, String dialogElementIen,
			Boolean isHistorical, String findingType) {

		String historicalOrCurrent = (isHistorical) ? "1" : "0";

		List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
		vistaRpcParamList.add(new VistaRpcParam("string", dialogElementIen));
		vistaRpcParamList.add(new VistaRpcParam("string", historicalOrCurrent));
		vistaRpcParamList.add(new VistaRpcParam("string", findingType));

		List<DialogPrompt> dialogPromptList = query(division, vpid, duz, appProxyName, "OR CPRS GUI CHART", "ORQQPXRM DIALOG PROMPTS", vistaRpcParamList, new DialogPromptListExtractor());

		return dialogPromptList;
	}

	@Override
	public List<VistaLocation> getLocationList(String division, String vpid,
			String duz, String appProxyName, String locationNameStartRow,
			Boolean ascendingOrder) {

		if (locationNameStartRow == null) {
			locationNameStartRow = "";
		}

		List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
		vistaRpcParamList.add(new VistaRpcParam("string", locationNameStartRow));
		vistaRpcParamList.add(new VistaRpcParam("string", ascendingOrder ? "1" : "0"));

		List<VistaLocation> resultList = query(division, vpid, duz, appProxyName, "OR CPRS GUI CHART", "ORWU1 NEWLOC", vistaRpcParamList, new Orwu1NewlocListExtractor());

		return resultList;
	}

	@Override
	public String getServiceCategory(String division, String vpid, String duz,
			String appProxyName, String initialServiceConnectionCategory,
			String locationIen, Boolean isInPatient) {

		if (initialServiceConnectionCategory == null) {
			initialServiceConnectionCategory = "";
		}

		if (locationIen == null) {
			locationIen = "";
		}

		List<VistaRpcParam> vistaRpcParamList = new ArrayList<VistaRpcParam>();
		vistaRpcParamList.add(new VistaRpcParam("string", initialServiceConnectionCategory));
		vistaRpcParamList.add(new VistaRpcParam("string", locationIen));
		vistaRpcParamList.add(new VistaRpcParam("string", isInPatient ? "1" : "0"));

		String result = query(division, vpid, duz, appProxyName, "OR CPRS GUI CHART", "ORWPCE GETSVC", vistaRpcParamList, new StringExtractor());

		return result;
	}

	@Override
	public List<DialogComponent> getClinicalReminderDialogs(String division,
			String vpid, String duz, String appProxyName,
			String clinicalReminderIEN) {
		List<VistaRpcParam> params = new ArrayList<VistaRpcParam>();
		params.add(new VistaRpcParam("string", clinicalReminderIEN));
		params.add(new VistaRpcParam("string", samplePatientIen));
		return query(division, vpid, duz, appProxyName, "OR CPRS GUI CHART", "ORQQPXRM REMINDER DIALOG", params, new DialogComponentListExtractor());
	}

	public VistaBrokerUserInfo doVistaSignon(String division,
			String applicationName, String accessCode, String verifyCode,
			String clientIp) {

		String results = null;

		String rpcContext = "XUS KAAJEE WEB LOGON";
		String rpcName = "XUS KAAJEE GET USER INFO";

		// 1. Create the correct connection spec.
		String av = accessCode + ";" + verifyCode;
		VistaLinkConnectionSpec connSpec = new VistaBrokerVistaLinkConnectionSpec(division, av, "", clientIp);

		VistaLinkConnection vistaLinkConnection = null;

		try {
			// 2. Managed mode:
			// Use JNDI name to get a managed connection factory.
			// VistaLinkConnectionFactory vistaLinkConnectionFactory =
			// (VistaLinkConnectionFactory) ic.lookup(jndiName);

			// 2. Non-managed mode:
			// In the future, 'vistaLinkConnectionFactory' will be returned by a
			// another method and not directly
			// instantiated. This class is for a specific PRIMARY STATION which
			// is determined by the user's DIVISION.
			VistaLinkConnectionFactory vistaLinkConnectionFactory = new VistaLinkConnectionFactory(vistaLinkManagedConnectionFactory, null);

			// 3. Get Connection
			vistaLinkConnection = (VistaLinkConnection) vistaLinkConnectionFactory.getConnection(connSpec);
			vistaLinkConnection.setTimeOut(timeOut);

			RpcRequest vReq = RpcRequestFactory.getRpcRequest();
			vReq.setTimeOut(vistaLinkConnection.getTimeOut() * 2);
			vReq.setUseProprietaryMessageFormat(useProprietaryMessageFormat);
			vReq.setRpcContext(rpcContext);

			vReq.setRpcName(rpcName);

			vReq.clearParams();
			vReq.getParams().setParam(1, "string", clientIp);
			vReq.getParams().setParam(2, "string", applicationName);

			RpcResponse vResp = vistaLinkConnection.executeRPC(vReq);

			results = vResp.getResults();

			logger.debug("Results: " + results);

			// Parse the string and return the UserInfo object.
			XusKaajeeGetUserInfoExtractor xusKaajeeGetUserInfoExtractor = new XusKaajeeGetUserInfoExtractor();
			return xusKaajeeGetUserInfoExtractor.extractData(results);
		} catch (SecurityUserAuthorizationException e) {
			throw new VistaVerificationException(e.toString());
		} catch (SecurityTooManyInvalidLoginAttemptsFaultException e) {
			throw new VistaLockedAccountException(e.toString());
		} catch (SecurityUserVerifyCodeException e) {
			throw new VistaVerificationException(e.toString());
		} catch (SecurityAccessVerifyCodePairInvalidException e) {
			throw new VistaVerificationException(e.toString());
		} catch (SecurityIdentityDeterminationFaultException e) {
			throw new VistaVerificationException(e.toString());
		} catch (SecurityDivisionDeterminationFaultException e) {
			throw new VistaVerificationException(e.toString());
		} catch (Exception e) {
			throw new VistaLinkException(e.toString());
		} finally {
			if (vistaLinkConnection != null) {
				try {
					vistaLinkConnection.close();
				} catch (ResourceException e) {
					logger.error("Exception thrown trying to close connection in doVistaSignon: ", e);
				}
			}
		}
		
		
	}

	@Override
    public Map<String, String> getHealthFactorIENMap(String division,
	            String vpid, String duz, String appProxyName, String componentIen) 
	 {
	    logger.info("Getting health factors for component Ien: " + componentIen);  
        Map<String, String> result = new HashMap<String, String>();
        String[] lines = getDialogPromptsAsString(division, vpid, duz, appProxyName, componentIen);
        if(lines == null) return result;
        
        for(String l : lines)
        {
            if(l.contains("^HF^"))
            {
                //3^63^^HF^^453^^EF-UNKNOWN IF FRAGMENTS IN BODY^^
                String[] tmp = StringUtils.splitPreserveAllTokens(l, '^');
                
                String name = tmp[7];
                String ien = tmp[5];
                
                if(!result.containsKey(name))
                {
                    result.put(name, ien);
                }
            }
        }
	    return result;    
	 }

    @Override
    public String[] getDialogPromptsAsString(String division, String vpid, String duz, String appProxyName,
            String componentIen) {
        List<VistaRpcParam> params = new ArrayList<VistaRpcParam>();
        params.add(new VistaRpcParam("string", componentIen));
        params.add(new VistaRpcParam("string", "0"));
        params.add(new VistaRpcParam("string", "0"));
        String s = null;
        try
        {
           s = query(division, vpid, duz, appProxyName, "OR CPRS GUI CHART", "ORQQPXRM DIALOG PROMPTS", params, new StringExtractor());
        }
        catch(Exception ex)
        {
            logger.warn("Error calling Viata RPC " + ex.getMessage());
        }
        if(s==null)
        {
            return null;
        }
        
        String[] lines = s.split("\n");
        return lines;
    }
    
    @Override
    public String getMHATestDetail(String division, String vpid, String duz, String appProxyName,
        String MHATestName) {
    List<VistaRpcParam> params = new ArrayList<VistaRpcParam>();
    params.add(new VistaRpcParam("string", MHATestName));
  
     return query(division, vpid, duz, appProxyName, "OR CPRS GUI CHART", "ORQQPXRM MENTAL HEALTH", 
             params, new StringExtractor());
  
    }
}
