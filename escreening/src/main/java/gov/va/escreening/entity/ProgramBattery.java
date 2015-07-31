package gov.va.escreening.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

/**
 * 
 * @author krizvi.ctr@iiinfo.com
 */
@Entity
@Table(name = "program_battery")
@NamedQueries({ @NamedQuery(name = "ProgramBattery.findAll", query = "SELECT pb FROM ProgramBattery pb") })
public class ProgramBattery implements Serializable {
	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "battery_id", referencedColumnName = "battery_id")
	@ManyToOne(optional = false)
	private Battery battery;

	@JoinColumn(name = "program_id", referencedColumnName = "program_id")
	@ManyToOne(optional = false)
	private Program program;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "program_battery_id")
	private Integer programBatteryId;

	@Basic(optional = false)
	@Length(max = 15, message = "Please use programInitials as 'program_XXX'")
	@Column(name = "program_initials", length = 15)
	private String programInitials;

	public ProgramBattery() {
	}

	public ProgramBattery(Integer programBatteryId) {
		this.programBatteryId = programBatteryId;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ProgramBattery)) {
			return false;
		}
		ProgramBattery other = (ProgramBattery) object;
		if ((this.programBatteryId == null && other.programBatteryId != null) || (this.programBatteryId != null && !this.programBatteryId.equals(other.programBatteryId))) {
			return false;
		}
		return true;
	}

	public Battery getBattery() {
		return battery;
	}

	public Program getProgram() {
		return program;
	}

	public Integer getProgramBatteryId() {
		return programBatteryId;
	}

	public String getProgramInitials() {
		return programInitials;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (programBatteryId != null ? programBatteryId.hashCode() : 0);
		return hash;
	}

	public void setBattery(Battery battery) {
		this.battery = battery;
	}

	public void setProgram(Program program) {
		this.program = program;
		setProgramInitials("program_" + this.program.getProgramId());
	}

	public void setProgramBatteryId(Integer programBatteryId) {
		this.programBatteryId = programBatteryId;
	}

	private void setProgramInitials(String programInitials) {
		this.programInitials = programInitials;
	}

	@Override
	public String toString() {
		return "gov.va.escreening.entity.ProgramBattery[ programBatteryId=" + programBatteryId + " ]";
	}

}
