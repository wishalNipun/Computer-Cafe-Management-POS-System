package model;

public class Computer {
    private String computerId;
    private String computerType;
    private Double computerPrice;

    public Computer() {
    }

    public Computer(String computerId, String computerType, Double computerPrice) {
        this.computerId = computerId;
        this.computerType = computerType;
        this.computerPrice = computerPrice;
    }

    public String getComputerId() {
        return computerId;
    }

    public void setComputerId(String computerId) {
        this.computerId = computerId;
    }

    public String getComputerType() {
        return computerType;
    }

    public void setComputerType(String computerType) {
        this.computerType = computerType;
    }

    public Double getComputerPrice() {
        return computerPrice;
    }

    public void setComputerPrice(Double computerPrice) {
        this.computerPrice = computerPrice;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "computerId='" + computerId + '\'' +
                ", computerType='" + computerType + '\'' +
                ", computerPrice=" + computerPrice +
                '}';
    }
}
