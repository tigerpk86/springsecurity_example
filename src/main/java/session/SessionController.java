package session;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.ExpiringSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SessionController {

    @Autowired
    private HashMapSessionRepository hashMapSessionRepository;

    @RequestMapping("/get_session")
    public String searchSession(@RequestParam(value = "session_id") String sessionId){
        ExpiringSession expiringSession = hashMapSessionRepository.getSession(sessionId);

        for(String key : expiringSession.getAttributeNames()) {
            Object o = expiringSession.getAttribute(key);
            System.out.println("key :" + key + ", value : " + o);
        }
        return "/hello";
    }

}
