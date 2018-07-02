package login;

import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.StringWriter;
import java.nio.file.AccessDeniedException;

@Controller
public class LoginLogoutController {

    @RequestMapping(method = RequestMethod.GET, value = "/login/loginPage")
    public String home(){
        return "/login/loginPage";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login/logout")
    public void logout(){
    }

    @RequestMapping(method = RequestMethod.GET, value = "/accessDenied")
    public String accessDenied(Model model, HttpServletRequest request){

//        AccessDeniedException ex
//                = (AccessDeniedException) request.getAttribute(WebAttributes.ACCESS_DENIED_403);
//        StringWriter sw = new StringWriter();
//        model.addAttribute("errorDetails", ex.getMessage());

        return "/login/accessDenied";
    }


}
