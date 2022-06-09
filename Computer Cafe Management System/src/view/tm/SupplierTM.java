package view.tm;

import javafx.scene.control.Button;

public class SupplierTM {
    private String supplierId;
    private String supplierName;
    private String telNo;
    private String address;
    private Button btn;

    public SupplierTM() {
    }

    public SupplierTM(String supplierId, String supplierName, String telNo, String address, Button btn) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.telNo = telNo;
        this.address = address;
        this.btn = btn;
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

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "SupplierTM{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", telNo='" + telNo + '\'' +
                ", address='" + address + '\'' +
                ", btn=" + btn +
                '}';
    }
}
