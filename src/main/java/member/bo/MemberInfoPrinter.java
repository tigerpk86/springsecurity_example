package member.bo;

import member.dao.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberInfoPrinter {
    private MemberDAO memberDAO;
    private MemberPrinter memberPrinter;

    public MemberDAO getMemberDAO() {
        return memberDAO;
    }

    @Autowired
    public void setMemberDAO(MemberDAO memberDAO) {
        System.out.println("public void setMemberDAO(MemberDAO memberDAO)");
        this.memberDAO = memberDAO;
    }

    public MemberPrinter getMemberPrinter() {
        return memberPrinter;
    }

    @Autowired
    public void setMemberPrinter(MemberPrinter memberPrinter) {
        System.out.println("public void setMemberPrinter(MemberPrinter memberPrinter)");
        this.memberPrinter = memberPrinter;
    }
}
