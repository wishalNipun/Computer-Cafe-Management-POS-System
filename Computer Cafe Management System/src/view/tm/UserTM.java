package view.tm;

import javafx.scene.control.Button;

public class UserTM {
    private String userId;
    private String name;
    private String role;
    private String telNo;
    private String email;
    private String userName;
    private String password;
    private Button btn;

    public UserTM() {
    }

    public UserTM(String userId, String name, String role, String telNo, String email, String userName, String password, Button btn) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.telNo = telNo;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.btn = btn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "UserTM{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", telNo='" + telNo + '\'' +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", btn=" + btn +
                '}';
    }
}
