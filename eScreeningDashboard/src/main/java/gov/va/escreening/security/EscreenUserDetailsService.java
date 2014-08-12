package gov.va.escreening.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class EscreenUserDetailsService extends JdbcDaoSupport implements UserDetailsService {

    public static final String DEF_USERS_BY_USERNAME_QUERY =
            "select login_id, password, first_name, last_name, user_id, vista_duz, vista_vpid, vista_division, cprs_verified " +
                    "from user " +
                    "where user_status_id = 1 and login_id = ?";

    public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY =
            "select r.name " +
                    "from user u " +
                    "inner join role r on u.role_id = r.role_id " +
                    "where u.login_id = ?";

    public static final String DEF_PROGRAMS_BY_USERNAME_QUERY =
            "select up.program_id " +
                    "from user_program up " +
                    "inner join user u on up.user_id = u.user_id " +
                    "where u.login_id = ?";

    private String usersByUsernameQuery;
    private String authoritiesByUsernameQuery;
    private String programsByUsernameQuery;

    public EscreenUserDetailsService() {
        usersByUsernameQuery = DEF_USERS_BY_USERNAME_QUERY;
        authoritiesByUsernameQuery = DEF_AUTHORITIES_BY_USERNAME_QUERY;
        programsByUsernameQuery = DEF_PROGRAMS_BY_USERNAME_QUERY;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<UserDetails> users = null;

        try {
            users = loadUsersByUsername(username);
        }
        catch (Exception e) {
            logger.error("Error retrieving data from database: " + e);
        }

        if (users.size() == 0) {
            logger.debug("Query returned no results for user '" + username + "'");

            throw new UsernameNotFoundException(String.format("Username %snot found", username));
        }

        UserDetails user = users.get(0); // contains no GrantedAuthority[]

        Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();

        dbAuthsSet.addAll(loadUserAuthorities(user.getUsername()));

        List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);

        if (dbAuths.size() == 0) {
            logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");

            throw new UsernameNotFoundException(String.format("User %s has no GrantedAuthority", username));
        }

        return createUserDetails(username, user, dbAuths);
    }

    protected List<UserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(usersByUsernameQuery,
                new String[] { username }, new RowMapper<UserDetails>() {
                    public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                        String username = rs.getString("login_id");
                        String password = rs.getString("password");
                        String firstName = rs.getString("first_name");
                        String lastName = rs.getString("last_name");
                        Integer userId = rs.getInt("user_id");
                        String vistaDuz = rs.getString("vista_duz");
                        String vistaVpid = rs.getString("vista_vpid");
                        String vistaDivision = rs.getString("vista_division");
                        Boolean cprsVerified = rs.getBoolean("cprs_verified");

                        // Default is to create a user with no roles.
                        EscreenUser escreenUser = new EscreenUser(username, password, true, true, true, true,
                                AuthorityUtils.NO_AUTHORITIES);

                        escreenUser.setFirstName(firstName);
                        escreenUser.setLastName(lastName);
                        escreenUser.setUserId(userId);
                        escreenUser.setVistaDuz(vistaDuz);
                        escreenUser.setVistaVpid(vistaVpid);
                        escreenUser.setVistaDivision(vistaDivision);
                        escreenUser.setCprsVerified(cprsVerified);

                        // Get the clinics associated with this user.
                        escreenUser.setProgramIdList(getProgramList(username));

                        return escreenUser;
                    }
                });
    }

    protected List<GrantedAuthority> loadUserAuthorities(String username) {
        return getJdbcTemplate().query(authoritiesByUsernameQuery,
                new String[] { username }, new RowMapper<GrantedAuthority>() {
                    public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
                        String roleName = rs.getString(1);

                        return new SimpleGrantedAuthority(roleName);
                    }
                });
    }

    protected List<Integer> getProgramList(String username) {
        return getJdbcTemplate().query(programsByUsernameQuery,
                new String[] { username }, new RowMapper<Integer>() {
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Integer programId = rs.getInt(1);

                        return programId;
                    }
                });
    }

    protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery,
            List<GrantedAuthority> combinedAuthorities) {

        // Just adds the roles to the new user object.
        EscreenUser escreenUser = new EscreenUser(username, userFromUserQuery.getPassword(),
                userFromUserQuery.isEnabled(),
                true, true, true, combinedAuthorities);
        escreenUser.setFirstName(((EscreenUser) userFromUserQuery).getFirstName());
        escreenUser.setLastName(((EscreenUser) userFromUserQuery).getLastName());
        escreenUser.setUserId(((EscreenUser) userFromUserQuery).getUserId());
        escreenUser.setProgramIdList(((EscreenUser) userFromUserQuery).getProgramIdList());
        escreenUser.setVistaDuz(((EscreenUser) userFromUserQuery).getVistaDuz());
        escreenUser.setVistaVpid(((EscreenUser) userFromUserQuery).getVistaVpid());
        escreenUser.setVistaDivision(((EscreenUser) userFromUserQuery).getVistaDivision());
        escreenUser.setCprsVerified(((EscreenUser) userFromUserQuery).getCprsVerified());

        return escreenUser;
    }

}
