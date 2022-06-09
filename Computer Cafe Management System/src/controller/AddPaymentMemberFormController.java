package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
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
import model.Package;

import model.Payment;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;
import view.tm.PaymentTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class AddPaymentMemberFormController {
    public JFXTextField txtTelNo;
    public JFXTextField txtMemberName;
    public TableView<PaymentTM> tblComputerSupplier;
    public TableColumn colMemberId;
    public TableColumn colPackageId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colDelete;
    public JFXComboBox <String>cmbMemberId;
    public JFXTextField txtAddress;
    public JFXTextField txtPackagePrice;
    public JFXTextField txtPackageName;
    public JFXComboBox<String> cmbPackageId;
    public String date;
    public String time;
    public TableColumn colCost;
    public Label lblTotal;
    public TextField txtEnterCashCustomer;
    public Label lblBalance;
    public JFXButton btnSave;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    public void initialize(){
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colPackageId.setCellValueFactory(new PropertyValueFactory<>("packageId"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("payDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("payTime"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));

        date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());


        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            time= currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond();
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        setMemberId();
        setPackageIds();
        cmbMemberId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setMemberDetail(newValue);

            } catch (ClassNotFoundException|SQLException e) {
                e.printStackTrace();
            }
        });
        cmbPackageId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setPackageDetails(newValue);
            }  catch (SQLException|ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        });

        btnSave.setDisable(true);

        Pattern cashPattern = Pattern.compile("^[1-9][0-9]*(.[0-9]{1,2})?$");

        map.put(txtEnterCashCustomer,cashPattern);


    }
    private void setMemberDetail(String SelectedMemberId) throws SQLException, ClassNotFoundException {
        Member member = AddPaymentCRUDController.getMemberDetails(SelectedMemberId);
        if (member != null) {
            txtMemberName.setText(member.getMemberName());
            txtTelNo.setText(member.getTelNo());
            txtAddress.setText(member.getAddress());
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }
    private void setPackageIds() {
        ObservableList<String> pIdObList = null;
        try {
            pIdObList = FXCollections.observableArrayList(
                    AddPaymentCRUDController.PackageId()
            );
            cmbPackageId.setItems(pIdObList);
        } catch (SQLException |ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    private void setMemberId() {
        ObservableList<String> mIdObList = null;
        try {
            mIdObList = FXCollections.observableArrayList(
                    AddPaymentCRUDController.getMemberIds()
            );
            cmbMemberId.setItems(mIdObList);
        } catch (SQLException |ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }
    private void setPackageDetails(String SelectedPackage) throws SQLException, ClassNotFoundException {
        Package pack = AddPaymentCRUDController.getPackage(SelectedPackage);
        if (pack != null){
            txtPackageName.setText(pack.getPackageName());
            txtPackagePrice.setText(String.valueOf(pack.getPackagePrice()));
        }
    }
    public String getPayId() throws SQLException, ClassNotFoundException {
       ResultSet result = AddPaymentCRUDController.payId();
        if (result.next()){

            AtomicInteger tempId = new AtomicInteger(Integer.
                    parseInt(result.getString(1).split("-")[1]));
            tempId.set(tempId.get() + 1);
            if (tempId.get() <=9){
                return "Py-00"+tempId;
            }else if(tempId.get() <=99){
                return "Py-0"+tempId;
            }else{
                return "Py-"+tempId;
            }

        }else{
            return "Py-001";
        }

    }
    ObservableList<PaymentTM> tmList =FXCollections.observableArrayList();
    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        paymentSave();
    }

    private void paymentSave() throws SQLException, ClassNotFoundException {
        Button btn = new Button("Delete");
        String mid =cmbMemberId.getValue();
        String pid =cmbPackageId.getValue();
        PaymentTM tm = new PaymentTM(
                mid,
                pid,
                Double.parseDouble(txtPackagePrice.getText()),
                date,
                time,
                btn

        );
        tmList.add(tm);
        tblComputerSupplier.setItems(tmList);

        String payid =getPayId();
        Payment payment = new Payment(
                payid,
                cmbMemberId.getValue(),
                cmbPackageId.getValue(),
                Double.parseDouble(txtPackagePrice.getText()),
                date,
                time
        );

        try {
            calculateTotal();
            Boolean isSaved= AddPaymentCRUDController.setPayment(payment);
            if (isSaved){
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Data Saved & Do you Want to print bill ?",
                        ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)){
                    billPrint();
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        btn.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are You Sure?",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get().equals(ButtonType.YES)){

                try {
                    tmList.remove(tm);

                    AddPaymentCRUDController.DeletePaymentDetail(mid,pid,date);

                } catch (SQLException|ClassNotFoundException exception) {
                    exception.printStackTrace();
                }
            }
        });

    }

    private void calculateTotal() {
        double amount =0;
        double balance = 0;
        double total = 0;
        for (PaymentTM tm : tmList
        ) {
            total += tm.getCost();
        }
        lblTotal.setText(String.valueOf(total));
        amount=Double.parseDouble(txtEnterCashCustomer.getText());
        balance= amount-total;
        lblBalance.setText(String.valueOf(balance));
    }
    public void billPrint(){
        String MemberId = cmbMemberId.getValue();
        String MemberName = txtMemberName.getText();
        String PackageName = txtPackageName.getText();
        double Cost = Double.parseDouble(txtPackagePrice.getText());
        double Total = Double.parseDouble(lblTotal.getText());
        double Cash = Double.parseDouble(txtEnterCashCustomer.getText());
        double Balance = Double.parseDouble(lblBalance.getText());

        HashMap paramMap = new HashMap();
        paramMap.put("id",MemberId);// id = report param name // customerID = UI typed value
        paramMap.put("memName", MemberName);
        paramMap.put("packName", PackageName);
        paramMap.put("cost", Cost);
        paramMap.put("total", Total);
        paramMap.put("cash", Cash);
        paramMap.put("balance", Balance);

        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/Reports/PaymentInvoice.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, paramMap, new JREmptyDataSource(1));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSave);

    }
}
