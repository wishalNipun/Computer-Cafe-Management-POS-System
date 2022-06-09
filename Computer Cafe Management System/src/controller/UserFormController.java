package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.Member;
import model.User;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.UserTM;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserFormController {

    public TableView<UserTM>tblUser;
    public TableColumn colUserId;
    public TableColumn colName;
    public TableColumn colRole;
    public TableColumn colTelNo;
    public TableColumn colEmail;
    public TableColumn colUserName;
    public TableColumn colPassword;
    public TableColumn colDelete;
    public JFXTextField txtUserId;
    public JFXTextField txtRole;
    public JFXTextField txtName;
    public JFXTextField txtTelNo;
    public JFXButton btnSave;
    public JFXTextField txtEmail;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){
        try {
            colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
            colTelNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
            colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
            colDelete.setCellValueFactory(new PropertyValueFactory("btn"));

            tblUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue!= null){
                    setUserData(newValue);
                }
            });
            loadUserDetail();


            Pattern idPattern = Pattern.compile("^U-[0-9]{3}$");
            Pattern namePattern = Pattern.compile("^[A-z ]{3,30}$");
            Pattern rolePattern = Pattern.compile("^Owner|Staff$");
            Pattern telNoPattern = Pattern.compile("^(?:7|0|(?:\\+94))(70|77|78|74|76|72|71)[0-9]{7}$");
            Pattern eMailPattern = Pattern.compile("^[A-z0-9]{3,}@(gmail|ymail|yahoomail).com$");
            Pattern userNamePattern = Pattern.compile("^[A-z0-9@.]{3,10}$");
            Pattern passwordPattern = Pattern.compile("^[A-z0-9@.]{3,10}$");

            map.put(txtUserId,idPattern);
            map.put(txtName,namePattern);
            map.put(txtRole,rolePattern);
            map.put(txtTelNo,telNoPattern);
            map.put(txtEmail,eMailPattern);
            map.put(txtUserName,userNamePattern);
            map.put(txtPassword,passwordPattern);


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void setUserData(UserTM tm) {
        btnSave.setDisable(false);
        btnSave.setText("Update");
        txtUserId.setText(tm.getUserId());
        txtName.setText(tm.getUserName());
        txtRole.setText(tm.getRole());
        txtTelNo.setText(tm.getTelNo());
        txtEmail.setText(tm.getEmail());
        txtUserName.setText(tm.getUserName());
        txtPassword.setText(tm.getPassword());
    }

    private void loadUserDetail() throws ClassNotFoundException, SQLException {

        ResultSet result =UserDataCRUDController.getUserDetail();

        ObservableList<UserTM> obList = FXCollections.observableArrayList();
        while (result.next()){
            Button btn = new Button("Delete");
            String id =  result.getString(1);
            obList.add(
                    new UserTM(
                            id,
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5),
                            result.getString(6),
                            result.getString(7),
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
                        UserDataCRUDController.DeleteUser(id);
                        clearText();
                        loadUserDetail();
                    } catch (SQLException|ClassNotFoundException exception) {
                        exception.printStackTrace();
                    }
                }
            });

        }
        tblUser.setItems(obList);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        userSave();
    }

    private void userSave() {

        User user = new User(
                txtUserId.getText(),txtName.getText(),txtRole.getText(),txtTelNo.getText(),txtEmail.getText(),txtUserName.getText(),txtPassword.getText()
        );
        try{

            if(btnSave.getText().equals("Save")){
                UserDataCRUDController.setUser(user);
                clearText();
            }else {
                UserDataCRUDController.update(user);
            }

            loadUserDetail();
        }catch (ClassNotFoundException|SQLException e){
            e.printStackTrace();

        }


    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        btnSave.setText("Save");
        clearText();
    }
    public void clearText(){
        txtUserId.clear();
        txtName.clear();
        txtRole.clear();
        txtTelNo.clear();
        txtEmail.clear();
        txtUserName.clear();
        txtPassword.clear();
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map,btnSave);;

            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();// if there is a error just focus it
            } else if (response instanceof Boolean) {
                userSave();
            }
        }

    }
}
