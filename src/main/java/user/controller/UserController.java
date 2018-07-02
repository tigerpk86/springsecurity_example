package user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import user.bo.UserBO;
import user.model.CommonUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes({"user", "email"})
public class UserController {

    @Autowired
    private UserBO userBO;

    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public String submit(@RequestParam("user_no") int userNo,
                         Model model) {
        model.addAttribute("user", userBO.getUser(userNo));
        return "user/edit";
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String submit(@ModelAttribute("user") CommonUser user,
                         SessionStatus sessionStatus,
                         HttpSession httpSession,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         RedirectAttributes redirectAttributes

    ) {
//        httpSession.invalidate();

//        sessionStatus.setComplete();
        redirectAttributes.addAttribute("name", user.getName());
        System.out.println("----- setComplete -----");
        sessionStatus.setComplete();
        System.out.println("----- redirect -----");
        return "redirect:/user/result";
    }

    @RequestMapping(value = "/user/result", method = RequestMethod.GET)
    public String submit(@RequestParam(value="name") String name,
//                         @ModelAttribute("user") String user,
//                         @ModelAttribute("email") String email,
                         SessionStatus sessionStatus,
                         HttpSession httpSession

    ) {
//        System.out.println(email);
//      httpSession.invalidate();
//      sessionStatus.setComplete();
        return "user/result";
    }

    @ModelAttribute("email")
    public String getEmail(){
        System.out.println("create email");
        return "bongjure";
    }



}

