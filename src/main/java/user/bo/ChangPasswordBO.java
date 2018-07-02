package user.bo;

import com.mysql.cj.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ChangPasswordBO implements IUserService {

    public ChangPasswordBO() {
    }

    @Autowired
    private UserDetailsService userDetailsService;


//    public void changePassword(String oldPassword, String newPassword) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Object principal = authentication.getPrincipal();
//
//        String username = null;
//        if(principal instanceof UserDetails) {
//            username = ((UserDetails) principal).getUsername();
//        }
//
//        if(StringUtils.isNullOrEmpty(username)) {
//            throw new UsernameNotFoundException("UserNotFound");
//        }
//
//        SecurityContextHolder.clearContext();
//    }

    @Override
    public void changPassword(String username, String password) {

    }
}
