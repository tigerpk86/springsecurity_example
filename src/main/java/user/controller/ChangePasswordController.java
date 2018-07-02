package user.controller;

import com.mysql.cj.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import user.bo.ChangPasswordBO;
import user.bo.IUserService;

@Controller
public class ChangePasswordController {


    public ChangePasswordController() {
    }

    @Autowired
    private IUserService changPasswordBO;


    @RequestMapping(value = "/user/change_password", method = RequestMethod.GET)
    public String changePassword() {

        return "user/change_password";
    }

    @RequestMapping(value = "/user/change_password", method = RequestMethod.POST)
    public String changePassword(
            @RequestParam(value = "old_password") String oldPassword,
            @RequestParam(value = "new_password") String newPassword) {

       changPasswordBO.changPassword(oldPassword, newPassword);
        return "redirect:/";
    }




}
