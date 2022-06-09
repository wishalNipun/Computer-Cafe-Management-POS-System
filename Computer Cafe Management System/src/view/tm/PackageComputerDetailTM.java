package view.tm;

import javafx.scene.control.Button;

public class PackageComputerDetailTM {
    private String packageId;
    private String computerId;
    private Button btn;

    public PackageComputerDetailTM() {
    }

    public PackageComputerDetailTM(String packageId, String computerId, Button btn) {
        this.packageId = packageId;
        this.computerId = computerId;
        this.btn = btn;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getComputerId() {
        return computerId;
    }

    public void setComputerId(String computerId) {
        this.computerId = computerId;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "PackageComputerDetailTM{" +
                "packageId='" + packageId + '\'' +
                ", computerId='" + computerId + '\'' +
                ", btn=" + btn +
                '}';
    }
}

