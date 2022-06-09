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
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.MemberTM;
import view.tm.UserTM;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class MemberManagementFormController {

    public TableView<MemberTM> tblMember;
    public TableColumn colMemberId;
    public TableColumn colMemberName;
    public TableColumn colTelNo;
    public TableColumn colAddress;
    public TableColumn colDelete;
    public JFXTextField txtMemberId;
    public JFXTextField txtTelNo;
    public JFXTextField txtMemberName;
    public JFXTextField txtAddress;
    public JFXButton btnSave;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    public void initialize(){
        try {
            colMemberId.setCellValueFactory(new PropertyValueFactory<>("MemberId"));
            colMemberName.setCellValueFactory(new PropertyValueFactory<>("MemberName"));
            colTelNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colDelete.setCellValueFactory(new PropertyValueFactory("btn"));
            loadMemberDetail();

            tblMember.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> {
                if (newValue!=null){
                    setMemberData(newValue)
                    ;}
            });


            btnSave.setDisable(true);


            Pattern idPattern = Pattern.compile("^M-[0-9]{3}$");
            Pattern namePattern = Pattern.compile("^[A-z ]{3,30}$");
            Pattern addressPattern = Pattern.compile("^[A-z0-9 /,]{4,20}$");
            Pattern telNoPattern = Pattern.compile("^(?:7|0|(?:\\+94))(70|77|78|74|76|72|71)[0-9]{7}$");

            map.put(txtMemberId,idPattern);
            map.put(txtMemberName,namePattern);
            map.put(txtTelNo,telNoPattern);
            map.put(txtAddress,addressPattern);

        } catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
        }

    }

    private void setMemberData(MemberTM tm) {
        btnSave.setDisable(false);
        btnSave.setText("Update");
        txtMemberId.setText(tm.getMemberId());
        txtMemberName.setText(tm.getMemberName());
        txtTelNo.setText(tm.getTelNo());
        txtAddress.setText(tm.getAddress());

   }

    private void loadMemberDetail() throws ClassNotFoundException, SQLException {

        ResultSet result = MemberDataCRUDController.getMemberDetail();

        ObservableList<MemberTM> obList = FXCollections.observableArrayList();
        while (result.next()){
            Button btn = new Button("Delete");
             String id  = result.getString(1);
            obList.add(
                    new MemberTM(
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
                       MemberDataCRUDController.DeleteMember(id);
                       clearText();
                       loadMemberDetail();
                   } catch (SQLException|ClassNotFoundException exception) {
                       exception.printStackTrace();
                   }
               }
            });

        }
        tblMember.setItems(obList);
    }


    public void btnSaveOnAction(ActionEvent actionEvent){
       MemberSave();


    }

    private void MemberSave() {
        Member m =new Member(
                txtMemberId.getText(),txtMemberName.getText(),txtTelNo.getText(),txtAddress.getText()
        );

        try{

            if(btnSave.getText().equals("Save")){
                MemberDataCRUDController.setMember(m);
                clearText();
            }else {
                MemberDataCRUDController.update(m);
            }

            loadMemberDetail();

        }catch (ClassNotFoundException|SQLException e){
            e.printStackTrace();

        }

    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        btnSave.setText("Save");
        clearText();
    }

    public void clearText(){
        txtMemberId.clear();
        txtMemberName.clear();
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
               MemberSave();
            }
        }

    }



}
