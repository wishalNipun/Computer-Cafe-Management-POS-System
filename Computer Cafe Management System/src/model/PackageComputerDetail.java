package model;

public class PackageComputerDetail {
    private String packageId;
    private String computerId;

    public PackageComputerDetail() {
    }

    public PackageComputerDetail(String packageId, String computerId) {
        this.packageId = packageId;
        this.computerId = computerId;
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

    @Override
    public String toString() {
        return "PackageComputerDetail{" +
                "packageId='" + packageId + '\'' +
                ", computerId='" + computerId + '\'' +
                '}';
    }
}
