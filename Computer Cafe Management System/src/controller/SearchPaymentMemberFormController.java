package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Computer;
import model.Member;
import model.Payment;
import util.CrudUtil;
import view.tm.PaymentTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class SearchPaymentMemberFormController {
    public JFXTextField txtTelNo;
    public JFXTextField txtMemberName;
    public TableColumn colMemberId;
    public TableColumn colPackageId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colDelete;
    public JFXComboBox <String>cmbMemberId;
    public JFXTextField txtAddress;
    public TableView<Payment> tblPaymentMember;
    public TableColumn colCost;


    public void initialize(){
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colPackageId.setCellValueFactory(new PropertyValueFactory<>("packageId"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("payTime"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("payDate"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));
        setMemberId();
        cmbMemberId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setMemberDetail(newValue);
                setPaymentMemberDetail(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


    }

    private void setPaymentMemberDetail(String SelectedId) throws SQLException, ClassNotFoundException {

        ResultSet result =SearchPaymentCRUDController.PaymentMemberDetail(SelectedId);
        ObservableList obList=FXCollections.observableArrayList();
        while (result.next()){
            Button btn = new Button("Delete");
            String mid=result.getString(1);
            String pid=result.getString(2);
            String date = result.getString(4);
            String time = result.getString(5);
            obList.add(
                    new PaymentTM(
                            mid,
                            pid,
                            result.getDouble(3),
                            date,
                            time,
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
                      SearchPaymentCRUDController.DeletePaymentDetail(mid,pid,date,time);
                      setPaymentMemberDetail(SelectedId);
                    } catch (SQLException|ClassNotFoundException exception) {
                        exception.printStackTrace();
                    }
                }
            });

        }
        tblPaymentMember.setItems(obList);

    }

    private void setMemberDetail(String SelectedMemberId) throws SQLException, ClassNotFoundException {
        Member member = SearchPaymentCRUDController.getMemberDetails(SelectedMemberId);
        if (member != null) {
            txtMemberName.setText(member.getMemberName());
            txtTelNo.setText(member.getTelNo());
            txtAddress.setText(member.getAddress());
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }

    private void setMemberId() {
        ObservableList<String> mIdObList = null;
        try {
            mIdObList = FXCollections.observableArrayList(
                    SearchPaymentCRUDController.getMemberIds()
            );
            cmbMemberId.setItems(mIdObList);
        } catch (SQLException |ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }


}
