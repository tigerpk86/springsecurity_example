package user.bo;

import org.springframework.stereotype.Service;
import user.model.CommonUser;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserBO {

    Map<Integer, CommonUser> users = new HashMap<>();

    @PostConstruct
    public void init() {
        users.put(1, new CommonUser(1, "hello"));
        users.put(2, new CommonUser(2, "hi"));
    }

    public CommonUser getUser(int userNo) {
        return users.get(userNo);
    }
}
