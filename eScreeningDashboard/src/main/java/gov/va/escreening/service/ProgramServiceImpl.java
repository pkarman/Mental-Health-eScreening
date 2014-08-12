package gov.va.escreening.service;

import gov.va.escreening.domain.ProgramDto;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.Battery;
import gov.va.escreening.entity.Clinic;
import gov.va.escreening.entity.NoteTitle;
import gov.va.escreening.entity.NoteTitleMap;
import gov.va.escreening.entity.Program;
import gov.va.escreening.entity.ProgramBattery;
import gov.va.escreening.form.ProgramEditViewFormBean;
import gov.va.escreening.repository.BatteryRepository;
import gov.va.escreening.repository.ClinicRepository;
import gov.va.escreening.repository.NoteTitleMapRepository;
import gov.va.escreening.repository.NoteTitleRepository;
import gov.va.escreening.repository.ProgramBatteryRepository;
import gov.va.escreening.repository.ProgramRepository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ProgramServiceImpl implements ProgramService {

	private static final Logger logger = LoggerFactory.getLogger(ProgramServiceImpl.class);

	private ClinicRepository clinicRepository;
	private NoteTitleMapRepository noteTitleMapRepository;
	private NoteTitleRepository noteTitleRepository;
	private ProgramRepository programRepository;
	private ProgramBatteryRepository programBatteryRepository;
	private BatteryRepository batteryRepository;

	@Autowired
	public void setClinicRepository(ClinicRepository clinicRepository) {
		this.clinicRepository = clinicRepository;
	}

	@Autowired
	public void setNoteTitleMapRepository(
			NoteTitleMapRepository noteTitleMapRepository) {
		this.noteTitleMapRepository = noteTitleMapRepository;
	}

	@Autowired
	public void setNoteTitleRepository(NoteTitleRepository noteTitleRepository) {
		this.noteTitleRepository = noteTitleRepository;
	}

	@Autowired
	public void setProgramRepository(ProgramRepository programRepository) {
		this.programRepository = programRepository;
	}

	public ProgramServiceImpl() {
	}

	@Transactional(readOnly = true)
	@Override
	public List<ProgramDto> getProgramList() {
		logger.trace("getProgramList()");

		List<ProgramDto> resultList = new ArrayList<ProgramDto>();

		List<Program> programList = programRepository.getProgramList();

		for (Program program : programList) {

			ProgramDto programDto = new ProgramDto();

			programDto.setProgramId(program.getProgramId());
			programDto.setName(program.getName());
			programDto.setIsDisabled(program.getIsDisabled());

			resultList.add(programDto);
		}

		return resultList;
	}

	@Transactional(readOnly = true)
	@Override
	public List<DropDownObject> getDropdownObjects() {
		logger.trace("getDropdownObjects()");

		List<DropDownObject> dropDownObjectList = new ArrayList<DropDownObject>();

		List<Program> programList = programRepository.getActiveProgramList();

		for (Program program : programList) {

			dropDownObjectList.add(new DropDownObject(program.getProgramId().toString(), program.getName()));
		}

		return dropDownObjectList;
	}

	@Transactional(readOnly = true)
	@Override
	public List<DropDownObject> getProgramDropDownObjects(
			List<Integer> programIdList) {
		logger.trace("getProgramDropDownObjects()");

		List<DropDownObject> dropDownList = new ArrayList<DropDownObject>();

		List<Program> programList = programRepository.findByProgramIdList(programIdList);
		for (Program program : programList) {
			DropDownObject dropDown = new DropDownObject(String.valueOf(program.getProgramId()), program.getName());
			dropDownList.add(dropDown);
		}

		return dropDownList;
	}

	@Transactional(readOnly = true)
	@Override
	public ProgramDto find(int programId) {

		Program program = programRepository.findOne(programId);

		ProgramDto programDto = null;

		if (program != null) {
			programDto = new ProgramDto();

			programDto.setProgramId(program.getProgramId());
			programDto.setName(program.getName());
			programDto.setIsDisabled(program.getIsDisabled());
		}

		return programDto;
	}

	@Transactional(readOnly = true)
	@Override
	public ProgramEditViewFormBean getProgramEditViewFormBean(int programId) {

		ProgramEditViewFormBean programEditViewFormBean = new ProgramEditViewFormBean();

		Program program = programRepository.findOne(programId);

		if (program != null) {
			programEditViewFormBean.setProgramId(program.getProgramId());
			programEditViewFormBean.setName(program.getName());
			programEditViewFormBean.setIsDisabled(program.getIsDisabled());

			if (program.getNoteTitleMapList() != null && program.getNoteTitleMapList().size() > 0) {
				for (NoteTitleMap noteTitleMap : program.getNoteTitleMapList()) {
					programEditViewFormBean.getSelectedNoteTitleIdList().add(noteTitleMap.getNoteTitle().getNoteTitleId());
				}
			}

			if (program.getClinicList() != null && program.getClinicList().size() > 0) {
				for (Clinic clinic : program.getClinicList()) {
					programEditViewFormBean.getSelectedClinicIdList().add(clinic.getClinicId());
				}
			}
		}

		// populate batteries associated with this program
		List<ProgramBattery> associatedBatteries = programBatteryRepository.findAllByProgramId(program.getProgramId());
		for (ProgramBattery pb : associatedBatteries) {
			programEditViewFormBean.getSelectedBatteryIdList().add(pb.getBattery().getBatteryId());
		}

		return programEditViewFormBean;
	}

	@Override
	public Integer createProgram(String name, Boolean isDisabled,
			List<Integer> clinicIdList, List<Integer> noteTitleIdList) {

		Program program = new Program();
		program.setName(name);
		program.setIsDisabled(isDisabled);

		if (clinicIdList != null && clinicIdList.size() > 0) {
			program.setClinicList(new ArrayList<Clinic>());

			for (Integer clinicId : clinicIdList) {
				Clinic clinic = clinicRepository.findOne(clinicId);
				clinic.setProgram(program);
				program.getClinicList().add(clinic);
			}
		}

		if (noteTitleIdList != null && noteTitleIdList.size() > 0) {
			program.setNoteTitleMapList(new ArrayList<NoteTitleMap>());

			for (Integer noteTitleId : noteTitleIdList) {

				NoteTitleMap noteTitleMap = new NoteTitleMap();

				// Note Title
				NoteTitle noteTitle = noteTitleRepository.findOne(noteTitleId);
				noteTitleMap.setNoteTitle(noteTitle);

				if (noteTitle.getNoteTitleMapList() == null) {
					noteTitle.setNoteTitleMapList(new ArrayList<NoteTitleMap>());
				}

				noteTitle.getNoteTitleMapList().add(noteTitleMap);

				// Program
				noteTitleMap.setProgram(program);
				program.getNoteTitleMapList().add(noteTitleMap);
			}
		}

		// Save.
		programRepository.create(program);

		// Return the assigned primary key.
		return program.getProgramId();
	}

	@Override
	public void updateProgram(int programId, String name, Boolean isDisabled,
			List<Integer> batteryIdList, List<Integer> clinicIdList,
			List<Integer> noteTitleIdList) {

		Program program = programRepository.findOne(programId);

		// 1. Update the name if we need to.
		if (!StringUtils.equals(program.getName(), name)) {
			program.setName(name);
		}

		program.setIsDisabled(isDisabled);

		// 2. Do some argument checks.
		if (clinicIdList == null) {
			clinicIdList = new ArrayList<Integer>();
		}

		if (noteTitleIdList == null) {
			noteTitleIdList = new ArrayList<Integer>();
		}

		// 3. Update the clinic list.
		List<Integer> existingClinicIdList = new ArrayList<Integer>();

		// First remove everything that is not in the new list.
		if (program.getClinicList() != null) {
			for (int i = 0; i < program.getClinicList().size(); ++i) {
				Integer clinicId = program.getClinicList().get(i).getClinicId();

				if (!clinicIdList.contains(clinicId)) {
					// program.getClinicList().remove(i);
					Clinic clinic = program.getClinicList().remove(i);
					clinic.setProgram(null);
					--i;
				} else {
					existingClinicIdList.add(clinicId);
				}
			}
		}

		// For each new item we need to add, add only if we don't already have
		// it in the list.
		for (Integer clinicId : clinicIdList) {
			if (!existingClinicIdList.contains(clinicId)) {
				Clinic clinic = clinicRepository.findOne(clinicId);
				clinic.setProgram(program);
				program.getClinicList().add(clinic);
			}
		}

		// 4. Update the Note Title List.
		List<Integer> existingNoteTitleIdList = new ArrayList<Integer>();

		// First remove everything that is not in the new list.
		if (program.getNoteTitleMapList() != null) {
			for (int i = 0; i < program.getNoteTitleMapList().size(); ++i) {
				Integer noteTitleId = program.getNoteTitleMapList().get(i).getNoteTitle().getNoteTitleId();

				if (!noteTitleIdList.contains(noteTitleId)) {
					NoteTitleMap noteTitleMap = program.getNoteTitleMapList().remove(i);
					noteTitleMap.setProgram(null);
					noteTitleMap.setNoteTitle(null);

					noteTitleMapRepository.delete(noteTitleMap);
					--i;
				} else {
					existingNoteTitleIdList.add(noteTitleId);
				}
			}
		}

		// For each new item we need to add, add only if we don't already have
		// it in the list.
		for (Integer noteTitleId : noteTitleIdList) {
			if (!existingNoteTitleIdList.contains(noteTitleId)) {
				NoteTitleMap noteTitleMap = new NoteTitleMap();

				NoteTitle noteTitle = noteTitleRepository.findOne(noteTitleId);
				noteTitleMap.setNoteTitle(noteTitle);
				noteTitle.getNoteTitleMapList().add(noteTitleMap);

				noteTitleMap.setProgram(program);
				program.getNoteTitleMapList().add(noteTitleMap);
			}
		}

		// 5. Update
		programRepository.update(program);

		// 6. Update relationships between battery and program
		manageProgramBatteryRelationships(program, batteryIdList);
	}

	private void manageProgramBatteryRelationships(Program program,
			List<Integer> batteryIdList) {

		if (logger.isDebugEnabled()) {
			logger.debug("associating {} batteries with program id {}", batteryIdList.size(), program.getProgramId());
		}

		// delete old relationships
		List<ProgramBattery> oldProramBatteries = programBatteryRepository.findAllByProgramId(program.getProgramId());
		for (ProgramBattery oldPB : oldProramBatteries) {
			programBatteryRepository.delete(oldPB);
		}

		// add this new set of relationships
		for (Integer bid : batteryIdList) {
			Battery battery = batteryRepository.findOne(bid);

			ProgramBattery pb = new ProgramBattery();
			pb.setProgram(program);
			pb.setBattery(battery);
			programBatteryRepository.update(pb);
		}
	}

	@Override
	public void updatePrgoramStatus(int programId, boolean isDisabled) {

		Program program = programRepository.findOne(programId);
		program.setIsDisabled(isDisabled);

		programRepository.update(program);
	}

	@Autowired
	public void setProgramBatteryRepository(
			ProgramBatteryRepository programBatteryRepository) {
		this.programBatteryRepository = programBatteryRepository;
	}

	@Autowired
	public void setBatteryRepository(BatteryRepository batteryRepository) {
		this.batteryRepository = batteryRepository;
	}
}