package model;

public class Payment {
    private String payId;
    private String memberId;
    private String packageId;
    private Double cost;
    private String payDate;
    private String payTime;

    public Payment() {
    }

    public Payment(String payId, String memberId, String packageId, Double cost, String payDate, String payTime) {
        this.payId = payId;
        this.memberId = memberId;
        this.packageId = packageId;
        this.cost = cost;
        this.payDate = payDate;
        this.payTime = payTime;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
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

    @Override
    public String toString() {
        return "Payment{" +
                "payId='" + payId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", packageId='" + packageId + '\'' +
                ", cost=" + cost +
                ", payDate='" + payDate + '\'' +
                ", payTime='" + payTime + '\'' +
                '}';
    }
}
