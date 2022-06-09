package view.tm;

import javafx.scene.control.Button;

public class MemberTM {
    private String MemberId;
    private String MemberName;
    private String telNo;
    private String address;
    private Button btn;

    public MemberTM() {
    }

    public MemberTM(String memberId, String memberName, String telNo, String address, Button btn) {
        MemberId = memberId;
        MemberName = memberName;
        this.telNo = telNo;
        this.address = address;
        this.btn = btn;
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

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "MemberTM{" +
                "MemberId='" + MemberId + '\'' +
                ", MemberName='" + MemberName + '\'' +
                ", telNo='" + telNo + '\'' +
                ", address='" + address + '\'' +
                ", btn=" + btn +
                '}';
    }
}
