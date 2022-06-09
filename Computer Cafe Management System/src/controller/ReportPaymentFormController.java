package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Payment;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.PaymentTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

public class ReportPaymentFormController {
    public DatePicker dtpkDate;
    public TableView tblPaymentReport;
    public TableColumn colPaymentId;
    public TableColumn colMemberId;
    public TableColumn colPackageId;
    public TableColumn colCost;
    public TableColumn colDate;
    public TableColumn colTime;
    public Label lblCost;
    public JFXButton btnSearch;


    public void initialize(){
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("payId"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colPackageId.setCellValueFactory(new PropertyValueFactory<>("packageId"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("payDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("payTime"));



    }

    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        obList.clear();
        setPaymentDetail(String.valueOf(dtpkDate.getValue()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Do you Want to print Report ?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().equals(ButtonType.YES)){
            printReport();
        }
    }

    private void printReport() {
        //ObservableList<Payment> tableRecords = tblPaymentReport.getItems(); // bean collection
        double total = Double.parseDouble(lblCost.getText());

        HashMap paramMap = new HashMap();
        paramMap.put("total",total);
        try {
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/Reports/PaymentDetail.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, paramMap, new JRBeanCollectionDataSource(obList));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    ObservableList<Payment> obList= FXCollections.observableArrayList();
    private void setPaymentDetail(String SelectedDate) throws SQLException, ClassNotFoundException {

        ResultSet result =ReportPaymentCRUDController.PaymentDetail(SelectedDate);

        while (result.next()){
            obList.add(
                    new Payment(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getDouble(4),
                            result.getString(5),
                            result.getString(6)
                    )
            );

        }
        tblPaymentReport.setItems(obList);
        calculateTotal();
    }
    private void calculateTotal() {
        double total = 0;
        for (Payment tm : obList
        ) {
            total += tm.getCost();
        }
        lblCost.setText(String.valueOf(total));

    }
}
