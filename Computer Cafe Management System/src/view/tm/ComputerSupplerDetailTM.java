package view.tm;

import javafx.scene.control.Button;

public class ComputerSupplerDetailTM {
    private String supplierId;
    private String computerId;
    private String Date;
    private Button btn;

    public ComputerSupplerDetailTM() {
    }

    public ComputerSupplerDetailTM(String supplierId, String computerId, String date, Button btn) {
        this.supplierId = supplierId;
        this.computerId = computerId;
        Date = date;
        this.btn = btn;
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

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "ComputerSupplerDetailTM{" +
                "supplierId='" + supplierId + '\'' +
                ", computerId='" + computerId + '\'' +
                ", Date='" + Date + '\'' +
                ", btn=" + btn +
                '}';
    }
}
