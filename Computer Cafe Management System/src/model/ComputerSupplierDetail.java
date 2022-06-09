package model;

public class ComputerSupplierDetail {
    private String supplierId;
    private String computerId;
    private String Date;

    public ComputerSupplierDetail() {
    }

    public ComputerSupplierDetail(String supplierId, String computerId, String date) {
        this.supplierId = supplierId;
        this.computerId = computerId;
        Date = date;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getComputerId() {
        return computerId;
    }

    public void setComputerId(String computerId) {
        this.computerId = computerId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public String toString() {
        return "ComputerSupplierDetail{" +
                "supplierId='" + supplierId + '\'' +
                ", computerId='" + computerId + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }
}
