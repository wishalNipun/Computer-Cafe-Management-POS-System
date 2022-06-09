package controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Computer;
import model.ComputerSupplierDetail;
import model.Member;
import model.Supplier;
import util.ValidationUtil;
import view.tm.ComputerSupplerDetailTM;
import view.tm.MemberTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class ComputerSupplierDetailFormController {

    public JFXComboBox<String> cmbSupplierId;
    public JFXTextField txtAddress;
    public JFXTextField txtPrice;
    public JFXTextField txtComputerType;
    public JFXComboBox <String>cmbComputerId;
    public JFXTextField txtSupplierName;
    public TableView <ComputerSupplerDetailTM>tblComputerSupplier;
    public JFXTextField txtTelNo;
    public JFXTextField txtDate;
    public TableColumn colSupplierId;
    public TableColumn colComputerId;
    public TableColumn colDate;
    public TableColumn colDelete;
    public JFXButton btnSave;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){
        colComputerId.setCellValueFactory(new PropertyValueFactory<>("computerId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));
        setSupplierIds();
        setComputerIds();
        try {
            loadComputerSupplierDetail();
        } catch (SQLException |ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbSupplierId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setSupplierDetails(newValue);

            } catch (SQLException|ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        });

        cmbComputerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            try {
                setComputerDetails(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        });
        btnSave.setDisable(true);

        Pattern idPattern = Pattern.compile("^[1-9]{1}[0-9]{3}-[0-9]{2}-[0-9]{2}$");
        map.put(txtDate,idPattern);

    }

    private void setComputerDetails(String SelectedComputer) throws SQLException, ClassNotFoundException {
        Computer computer = ComputerSupplierCRUDController.getComputer(SelectedComputer);

        if (computer != null) {
            txtComputerType.setText(computer.getComputerType());
            txtPrice.setText(String.valueOf(computer.getComputerPrice()));

        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }

    private void loadComputerSupplierDetail() throws SQLException, ClassNotFoundException {
        ResultSet result = ComputerSupplierCRUDController.getComputerSupplierDetail();
        ObservableList <ComputerSupplerDetailTM>obList = FXCollections.observableArrayList();
        while (result.next()){
            Button btn = new Button("Delete");
            String sId  = result.getString(1);
            String cId  = result.getString(2);
            obList.add(
                    new ComputerSupplerDetailTM(
                            sId,
                            cId,
                            result.getString(3),
                            btn
                    )
            );
            btn.setOnAction(e->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are You Sure?",
                        ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)){

                    try {
                        ComputerSupplierCRUDController.DeleteSupplierComputerDetail(sId,cId);
                        loadComputerSupplierDetail();
                    } catch (SQLException|ClassNotFoundException exception) {
                        exception.printStackTrace();
                    }
                }
            });

        }
        tblComputerSupplier.setItems(obList);
    }

    private void setComputerIds() {
        ObservableList<String> cIdObList = null;
        try {
            cIdObList = FXCollections.observableArrayList(
                    ComputerSupplierCRUDController.ComputerId()
            );
            cmbComputerId.setItems(cIdObList);
        } catch (SQLException|ClassNotFoundException exception) {
            exception.printStackTrace();
        }

    }

    private void setSupplierDetails(String SelectedSupplierId) throws SQLException, ClassNotFoundException {
        Supplier supplier = ComputerSupplierCRUDController.getSupplier(SelectedSupplierId);

        if (supplier != null) {
            txtSupplierName.setText(supplier.getSupplierName());
            txtTelNo.setText(supplier.getTelNo());
            txtAddress.setText(supplier.getAddress());

        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }


    private void setSupplierIds() {
        try {

            ObservableList<String> cIdObList = FXCollections.observableArrayList(
                    ComputerSupplierCRUDController.SupplierId()
            );
            cmbSupplierId.setItems(cIdObList);
        } catch (SQLException|ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {
       computerSupplierDetailSave();

    }

    private void computerSupplierDetailSave() {
        ComputerSupplierDetail cmd = new ComputerSupplierDetail(
                cmbSupplierId.getValue(),cmbComputerId.getValue(),txtDate.getText()
        );
        try {
            ComputerSupplierCRUDController.setComputerSupplier(cmd);
            clearText();
            loadComputerSupplierDetail();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clearText(){
        txtDate.clear();

    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map,btnSave);;

            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();// if there is a error just focus it
            } else if (response instanceof Boolean) {
                computerSupplierDetailSave();
            }
        }
    }
}
