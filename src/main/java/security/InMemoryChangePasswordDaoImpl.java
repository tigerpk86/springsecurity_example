package security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

public class InMemoryChangePasswordDaoImpl extends InMemoryUserDetailsManager {


    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext()
                .getAuthentication();

        Object principal = currentUser.getPrincipal();
        UserDetails userDetails = ((UserDetails) principal) ;

        User newUserDetails = new User(userDetails.getUsername(), newPassword, userDetails.isEnabled(), userDetails.isAccountNonExpired()
        , userDetails.isCredentialsNonExpired(), userDetails.isAccountNonLocked(), userDetails.getAuthorities());

        updateUser(newUserDetails);
    }
}
