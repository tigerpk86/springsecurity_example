package user.model;

public class CommonUser {

    Integer userNo;
    String name;

    public CommonUser(Integer userNo, String name) {
        this.userNo = userNo;
        this.name = name;
    }

    public Integer getUserNo() {
        return userNo;
    }

    public void setUserNo(Integer userNo) {
        this.userNo = userNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "userNo=" + userNo +
                ", name='" + name + '\'' +
                '}';
    }
}
