package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabasePasswordSecurerBean extends JdbcDaoSupport {

    @Autowired
    private ShaPasswordEncoder shaPasswordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SaltSource saltSource;

    @PostConstruct
    public void init() {
//        secureDatabase();
    }

    public void secureDatabase() {
        getJdbcTemplate().query("select username, password,salt from users"
                , new RowCallbackHandler() {
                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        String username = rs.getString(1);
                        String password = rs.getString(2);
                        String salt = rs.getString(3);
                        UserDetails user = userDetailsService.loadUserByUsername(username);
                        String encodedPassword = shaPasswordEncoder.encodePassword(password, saltSource.getSalt(user));
                        getJdbcTemplate().update("update users set password = ? where username = ?",
                                encodedPassword, username);
                        logger.debug("Updating password for username: " + username + " to: " + encodedPassword);
                    }
                }
        );


    }



}
