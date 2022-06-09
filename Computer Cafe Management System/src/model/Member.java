package model;

public class Member {
    private String MemberId;
    private String MemberName;
    private String telNo;
    private String address;


    public Member(String memberId, String memberName, String telNo, String address) {
        MemberId = memberId;
        MemberName = memberName;
        this.telNo = telNo;
        this.address = address;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String memberId) {
        MemberId = memberId;
    }

    public String getMemberName() {
        return MemberName;
    }

    public void setMemberName(String memberName) {
        MemberName = memberName;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
