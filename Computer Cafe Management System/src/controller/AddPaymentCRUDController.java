package controller;

import db.DBConnection;
import javafx.scene.control.Alert;
import model.Member;
import model.Package;
import model.Payment;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddPaymentCRUDController {
    public static ArrayList<String> getMemberIds() throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtil.execute("SELECT id FROM Member");
        ArrayList ids = new ArrayList();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }
    public static Member getMemberDetails(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Member WHERE id =?",id);
        while (result.next()){
            return new Member(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)

            );
        }
        return null;
    }
    public static ArrayList<String> PackageId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT id FROM PackagePlan");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }
    public static ResultSet payId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT payId FROM  MemberPackagePaymentDetail ORDER BY payId DESC LIMIT 1");
        return result;
    }
    public static Package getPackage(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM PackagePlan WHERE id =?",id);
        while (result.next()){
            return new Package(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3)
            );
        }
        return null;
    }
    /*public static void setPayment(Payment payment) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("INSERT INTO MemberPackagePaymentDetail VALUES (?,?,?,?,?,?)",payment.getPayId(),payment.getMemberId(),payment.getPackageId(),payment.getCost(),payment.getPayDate(),payment.getPayTime());
        new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").showAndWait();
    }*/
    public static Boolean setPayment(Payment payment) throws SQLException, ClassNotFoundException {
      Boolean isSave=  CrudUtil.execute("INSERT INTO MemberPackagePaymentDetail VALUES (?,?,?,?,?,?)",payment.getPayId(),payment.getMemberId(),payment.getPackageId(),payment.getCost(),payment.getPayDate(),payment.getPayTime());
      return isSave;
        //new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").showAndWait();
    }
    public static void DeletePaymentDetail(String mid,String pid, String date) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("DELETE FROM MemberPackagePaymentDetail WHERE memId=? AND pID =? AND payDate =?",mid,pid,date);
    }
}
