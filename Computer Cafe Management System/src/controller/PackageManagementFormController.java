package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Package;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.ComputerTM;
import view.tm.PackageTM;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class PackageManagementFormController {
    public TableView<PackageTM> tblPackage;
    public TableColumn colPackageId;
    public TableColumn colPackageName;
    public TableColumn colPackagePrice;
    public TableColumn colDelete;
    public JFXTextField txtPackageId;
    public JFXTextField txtPackagePrice;
    public JFXTextField txtPackageName;
    public JFXButton btnSave;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){
        try {
            colPackageId.setCellValueFactory(new PropertyValueFactory<>("packageId"));
            colPackageName.setCellValueFactory(new PropertyValueFactory<>("packageName"));
            colPackagePrice.setCellValueFactory(new PropertyValueFactory<>("packagePrice"));
            colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));

                
            loadPackageDetail();
            
            tblPackage.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
                if (newValue!=null){
                    setPackageData(newValue);
                }
            });

            btnSave.setDisable(true);


            Pattern idPattern = Pattern.compile("^P-[0-9]{3}$");
            Pattern namePattern = Pattern.compile("^[A-z ]{3,30}$");
            Pattern pricePattern = Pattern.compile("^[1-9][0-9]*(.[0-9]{1,2})?$");

            map.put(txtPackageId,idPattern);
            map.put(txtPackageName,namePattern);
            map.put(txtPackagePrice,pricePattern);

        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    private void setPackageData(PackageTM p) {
        btnSave.setDisable(false);
        btnSave.setText("Update");
        txtPackageId.setText(p.getPackageId());
        txtPackageName.setText(p.getPackageName());
        txtPackagePrice.setText(String.valueOf(p.getPackagePrice()));
    }


    private void loadPackageDetail() throws ClassNotFoundException, SQLException {
        ResultSet result = PackageDataCRUDController.getPackageDetail();
        ObservableList<PackageTM> obList = FXCollections.observableArrayList();

        while (result.next()){
            Button btn = new Button("Delete");
            String id = result.getString(1);
            obList.add(
                    new PackageTM(
                            id,
                            result.getString(2),
                            result.getDouble(3),
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
                        PackageDataCRUDController.DeletePackage(id);
                        loadPackageDetail();

                    } catch (SQLException| ClassNotFoundException exception) {
                        exception.printStackTrace();
                    }

                }

            });
        }
        tblPackage.setItems(obList);
    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        btnSave.setText("Save");
        clearText();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
      packageSave();
    }

    private void packageSave() {
        Package p = new Package(txtPackageId.getText(),txtPackageName.getText(),Double.parseDouble(txtPackagePrice.getText()));

        try {
            if (btnSave.getText().equals("Save")){
                PackageDataCRUDController.setPackage(p);

            }else {
                PackageDataCRUDController.update(p);
            }
            loadPackageDetail();


        } catch (SQLException |ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void clearText(){
        txtPackageId.clear();
        txtPackageName.clear();
        txtPackagePrice.clear();
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map,btnSave);;

            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();// if there is a error just focus it
            } else if (response instanceof Boolean) {
                packageSave();
            }
        }
    }
}
