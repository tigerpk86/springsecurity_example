package user.bo;

import org.springframework.security.access.prepost.PreAuthorize;

public interface IUserService {

//    @PreAuthorize("hasRole('ROLE_USER')")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void changPassword(String username, String password);
}
