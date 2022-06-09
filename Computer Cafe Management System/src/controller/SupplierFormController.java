package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Member;
import model.Supplier;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.MemberTM;
import view.tm.SupplierTM;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class SupplierFormController {
    public TableView<SupplierTM>tblSupplier;
    public TableColumn colSupplierId;
    public TableColumn colSupplierName;
    public TableColumn colTelNo;
    public TableColumn colAddress;
    public TableColumn colDelete;
    public JFXTextField txtSupplierId;
    public JFXTextField txtTelNo;
    public JFXTextField txtSupplierName;
    public JFXTextField txtAddress;
    public JFXButton btnSave;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    public void initialize(){
        try {
            colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
            colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
            colTelNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));
            loadSupplierDetail();

            tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue!= null){
                    setSupplierData(newValue);
                }
            });

            btnSave.setDisable(true);


            Pattern idPattern = Pattern.compile("^S-[0-9]{3}$");
            Pattern namePattern = Pattern.compile("^[A-z ]{3,30}$");
            Pattern addressPattern = Pattern.compile("^[A-z0-9 /,]{4,20}$");
            Pattern telNoPattern = Pattern.compile("^(?:7|0|(?:\\+94))(70|77|78|74|76|72)[0-9]{7}$");

            map.put(txtSupplierId,idPattern);
            map.put(txtSupplierName,namePattern);
            map.put(txtTelNo,telNoPattern);
            map.put(txtAddress,addressPattern);
        } catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
        }
    }

    private void setSupplierData(SupplierTM tm) {
        btnSave.setDisable(false);
        btnSave.setText("Update");
        txtSupplierId.setText(tm.getSupplierId());
        txtSupplierName.setText(tm.getSupplierName());
        txtTelNo.setText(tm.getTelNo());
        txtAddress.setText(tm.getAddress());
    }

    private void loadSupplierDetail() throws ClassNotFoundException, SQLException {
        ResultSet result =SupplierDataCRUDController.getSupplierDetail();

        ObservableList<SupplierTM> obList = FXCollections.observableArrayList();
        while (result.next()){
            Button btn = new Button("Delete");
            String id = result.getString(1);
            obList.add(
                    new SupplierTM(
                            id,
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
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
                        SupplierDataCRUDController.DeleteSupplier(id);
                        clearText();
                        loadSupplierDetail();
                    } catch (SQLException|ClassNotFoundException exception) {
                        exception.printStackTrace();
                    }
                }
            });


        }
        tblSupplier.setItems(obList);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
       supplierSave();
    }

    private void supplierSave() {
        Supplier s = new Supplier(
                txtSupplierId.getText(),txtSupplierName.getText(),txtTelNo.getText(),txtAddress.getText()
        );

        try{

            if(btnSave.getText().equals("Save")){
                SupplierDataCRUDController.setSupplier(s);
                clearText();
            }else {
                SupplierDataCRUDController.update(s);
            }

            loadSupplierDetail();

        }catch (ClassNotFoundException|SQLException e){
            e.printStackTrace();

        }
    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        btnSave.setText("Save");
        clearText();
    }
    public void clearText(){
        txtSupplierId.clear();
        txtSupplierName.clear();
        txtTelNo.clear();
        txtAddress.clear();
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map,btnSave);;

            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();// if there is a error just focus it
            } else if (response instanceof Boolean) {
                supplierSave();
            }
        }
    }
}
