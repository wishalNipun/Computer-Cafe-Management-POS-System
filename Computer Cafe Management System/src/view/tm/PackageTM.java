package view.tm;

import javafx.scene.control.Button;

public class PackageTM {
    private String packageId;
    private String packageName;
    private Double packagePrice;
    private Button btn;

    public PackageTM() {
    }

    public PackageTM(String packageId, String packageName, Double packagePrice, Button btn) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.packagePrice = packagePrice;
        this.btn = btn;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Double getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(Double packagePrice) {
        this.packagePrice = packagePrice;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "PackageTM{" +
                "packageId='" + packageId + '\'' +
                ", packageName='" + packageName + '\'' +
                ", packagePrice=" + packagePrice +
                ", btn=" + btn +
                '}';
    }
}
