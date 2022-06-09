package view.tm;

import javafx.scene.control.Button;

public class ComputerTM {
    private String computerId;
    private String computerType;
    private Double computerPrice;
    private Button btn;

    public ComputerTM() {
    }

    public ComputerTM(String computerId, String computerType, Double computerPrice, Button btn) {
        this.computerId = computerId;
        this.computerType = computerType;
        this.computerPrice = computerPrice;
        this.btn = btn;
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

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "ComputerTM{" +
                "computerId='" + computerId + '\'' +
                ", computerType='" + computerType + '\'' +
                ", computerPrice=" + computerPrice +
                ", btn=" + btn +
                '}';
    }
}
