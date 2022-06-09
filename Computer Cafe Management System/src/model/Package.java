package model;

public class Package {
    private String packageId;
    private String packageName;
    private Double packagePrice;

    public Package() {
    }

    public Package(String packageId, String packageName, Double packagePrice) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.packagePrice = packagePrice;
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

    @Override
    public String
    toString() {
        return "Package{" +
                "packageId='" + packageId + '\'' +
                ", packageName='" + packageName + '\'' +
                ", packagePrice=" + packagePrice +
                '}';
    }
}
