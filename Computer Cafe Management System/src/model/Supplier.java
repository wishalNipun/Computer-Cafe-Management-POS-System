package model;

import java.sql.Date;
import java.sql.Time;

public class Supplier {
    private String supplierId;
    private String supplierName;
    private String telNo;
    private String address;

    public Supplier() {
    }

    public Supplier(String supplierId, String supplierName, String telNo, String address) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.telNo = telNo;
        this.address = address;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", telNo='" + telNo + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
