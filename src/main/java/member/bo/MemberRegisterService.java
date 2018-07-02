package member.bo;

import member.dao.MemberDAO;

public class MemberRegisterService {

    private MemberDAO memberDAO;

    public MemberRegisterService(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }
}
