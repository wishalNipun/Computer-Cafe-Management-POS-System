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
import model.Computer;
import model.Member;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.ComputerTM;
import view.tm.MemberTM;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class ComputerFormController {
    public TableView<ComputerTM>tblComputer;
    public TableColumn colComputerId;
    public TableColumn colComputerType;
    public TableColumn colComputerPrice;
    public TableColumn colDelete;
    public JFXTextField txtComputerId;
    public JFXTextField txtComputerPrice;
    public JFXTextField txtComputerType;
    public JFXButton btnSave;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    public void initialize(){
        try {
            colComputerId.setCellValueFactory(new PropertyValueFactory<>("computerId"));
            colComputerType.setCellValueFactory(new PropertyValueFactory<>("computerType"));
            colComputerPrice.setCellValueFactory(new PropertyValueFactory<>("computerPrice"));
            colDelete.setCellValueFactory(new PropertyValueFactory("btn"));
            loadComputerDetail();

            tblComputer.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
                if (newValue!=null){
                    setComputerData(newValue);
                }
            });

            btnSave.setDisable(true);


            Pattern idPattern = Pattern.compile("^C-[0-9]{3}$");
            Pattern namePattern = Pattern.compile("^[A-z0-9 ]{3,30}$");
            Pattern pricePattern = Pattern.compile("^[1-9][0-9]*(.[0-9]{1,2})?$");

            map.put(txtComputerId,idPattern);
            map.put(txtComputerType,namePattern);
            map.put(txtComputerPrice,pricePattern);

        } catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
        }
    }

    private void setComputerData(ComputerTM tm) {
        btnSave.setDisable(false);
        btnSave.setText("Update");
        txtComputerId.setText(tm.getComputerId());
        txtComputerType.setText(tm.getComputerType());
        txtComputerPrice.setText(String.valueOf(tm.getComputerPrice()));
    }

    private void loadComputerDetail() throws ClassNotFoundException, SQLException {
        ResultSet result = ComputerDataCRUDController.getComputerDetail();
        ObservableList<ComputerTM> obList = FXCollections.observableArrayList();
        while (result.next()){
            Button btn = new Button("Delete");
            String id = result.getString(1);

            obList.add(
                    new ComputerTM(
                            id,
                            result.getString(2),
                            result.getDouble(3),
                            btn
                    )
            );


            btn.setOnAction((e)->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are You Sure?",
                        ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)){

                    try {
                        ComputerDataCRUDController.DeleteComputer(id);
                        clearText();
                        loadComputerDetail();
                    } catch (SQLException|ClassNotFoundException exception) {
                       exception.printStackTrace();
                    }

                }
            });

        }
        tblComputer.setItems(obList);
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {
        computerSave();

    }

    private void computerSave() {
        Computer c = new Computer(txtComputerId.getText(),txtComputerType.getText(),Double.parseDouble(txtComputerPrice.getText()));

        try {
            if (btnSave.getText().equals("Save")){
                ComputerDataCRUDController.setComputer(c);
                clearText();

            }else {
                ComputerDataCRUDController.update(c);
            }
            loadComputerDetail();


        } catch (SQLException |ClassNotFoundException exception) {
            exception.printStackTrace();
        }

    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        btnSave.setText("Save");
        clearText();
    }

    public void clearText(){
        txtComputerId.clear();
        txtComputerType.clear();
        txtComputerPrice.clear();
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map,btnSave);;

            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();// if there is a error just focus it
            } else if (response instanceof Boolean) {
                computerSave();
            }
        }
    }
}
