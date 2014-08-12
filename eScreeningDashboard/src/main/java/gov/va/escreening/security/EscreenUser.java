package gov.va.escreening.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class EscreenUser extends User {

	private static final long serialVersionUID = -338832908211694464L;

	private Integer userId;
	private String firstName;
	private String lastName;
	private String fullName;
	private String vistaDuz;
	private String vistaVpid;
	private String vistaDivision;
	private Boolean cprsVerified;

	private List<Integer> ProgramIdList = new ArrayList<Integer>();

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		if (fullName == null) {
			fullName = lastName + ", " + firstName;
		}

		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getVistaDuz() {
		return vistaDuz;
	}

	public void setVistaDuz(String vistaDuz) {
		this.vistaDuz = vistaDuz;
	}

	public String getVistaVpid() {
		return vistaVpid;
	}

	public void setVistaVpid(String vistaVpid) {
		this.vistaVpid = vistaVpid;
	}

	public String getVistaDivision() {
		return vistaDivision;
	}

	public void setVistaDivision(String vistaDivision) {
		this.vistaDivision = vistaDivision;
	}

	public Boolean getCprsVerified() {
		return cprsVerified;
	}

	public void setCprsVerified(Boolean cprsVerified) {
		this.cprsVerified = cprsVerified;
	}

	public List<Integer> getProgramIdList() {
		return ProgramIdList;
	}

	public void setProgramIdList(List<Integer> programIdList) {
		ProgramIdList = programIdList;
	}

	public EscreenUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {

		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public EscreenUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {

		super(username, password, authorities);
	}

	public String getAuthority() {
		Collection<GrantedAuthority> grantedAuths = getAuthorities();
		GrantedAuthority ga = grantedAuths.iterator().next();
		return ga.getAuthority();
	}

}
