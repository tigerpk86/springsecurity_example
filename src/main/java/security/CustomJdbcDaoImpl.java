package security;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import security.model.SaltUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomJdbcDaoImpl extends JdbcDaoImpl {

    @Override
    protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {

        String returnUsername = userFromUserQuery.getUsername();
        if(!isUsernameBasedPrimaryKey()) {
            returnUsername = username;
        }

        return new SaltUser(returnUsername, userFromUserQuery.getPassword(),
                userFromUserQuery.isEnabled(), true, true, true, combinedAuthorities,
                ((SaltUser)userFromUserQuery).getSalt());
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        List<UserDetails> userDetail = getJdbcTemplate().query(
                    getUsersByUsernameQuery(),
                (Object[]) new String[]{username},
                    new RowMapper<UserDetails>() {
                        @Override
                        public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                            String username = rs.getString(1);
                            String password = rs.getString(2);
                            boolean enabled = rs.getBoolean(3);
                            String salt = rs.getString(4);
                            return new SaltUser(username, password, enabled, true, true,
                                    true, AuthorityUtils.NO_AUTHORITIES, salt);
                        }
                    }
                );

        if(userDetail.size() == 0) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        UserDetails user = userDetail.get(0);

        Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();

dbAuthsSet.addAll(loadGroupAuthorities(user.getUsername()));


        List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);

        addCustomAuthorities(user.getUsername(), dbAuths);

        if (dbAuths.size() == 0) {
            this.logger.debug("User '" + username
                    + "' has no authorities and will be treated as 'not found'");

            throw new UsernameNotFoundException(this.messages.getMessage(
                    "JdbcDaoImpl.noAuthority", new Object[] { username },
                    "User {0} has no GrantedAuthority"));
        }

        return new SaltUser(username, user.getPassword(), user.isEnabled(), true, true,
                true, dbAuths, ((SaltUser)user).getSalt());
    }
}
