package user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import user.model.CommonUser;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("user")
public class User2Controller {

    @RequestMapping(value = "/user/result2", method = RequestMethod.GET)
    public String submit2(@ModelAttribute("user") CommonUser user,
//                         @ModelAttribute("user") String user,
//                         @ModelAttribute("email") String email,
                         SessionStatus sessionStatus,
                         HttpSession httpSession

    ) {
//        System.out.println(email);
//      httpSession.invalidate();
//      sessionStatus.setComplete();
        return "user/result2";
    }
}
