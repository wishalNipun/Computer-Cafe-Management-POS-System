package view.tm;

import javafx.scene.control.Button;

public class PaymentTM {
    private String memberId;
    private String packageId;
    private Double cost;
    private String payDate;
    private String payTime;
    private Button btn;

    public PaymentTM() {
    }

    public PaymentTM(String memberId, String packageId, Double cost, String payDate, String payTime, Button btn) {
        this.memberId = memberId;
        this.packageId = packageId;
        this.cost = cost;
        this.payDate = payDate;
        this.payTime = payTime;
        this.btn = btn;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "PaymentTM{" +
                "memberId='" + memberId + '\'' +
                ", packageId='" + packageId + '\'' +
                ", cost=" + cost +
                ", payDate='" + payDate + '\'' +
                ", payTime='" + payTime + '\'' +
                ", btn=" + btn +
                '}';
    }
}
