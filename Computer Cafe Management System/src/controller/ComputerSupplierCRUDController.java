package controller;

import javafx.scene.control.Alert;
import model.Computer;
import model.ComputerSupplierDetail;
import model.Member;
import model.Supplier;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ComputerSupplierCRUDController {
    public static ArrayList<String> SupplierId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT id FROM Supplier");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }
    public static Supplier getSupplier(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Supplier WHERE id =?",id);
        while (result.next()){
            return new Supplier(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
       return null;

    }
    public static ArrayList<String> ComputerId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT id FROM Computer");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }
    public static Computer getComputer(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Computer WHERE id =?",id);
        while (result.next()){
            return new Computer(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3)
            );
        }
        return null;
    }
    public static ResultSet getComputerSupplierDetail() throws SQLException, ClassNotFoundException {
        return   CrudUtil.execute("SELECT * FROM SupplierComputerDetail");
    }
    public static void setComputerSupplier(ComputerSupplierDetail csd) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("INSERT INTO SupplierComputerDetail VALUES (?,?,?)",csd.getSupplierId(),csd.getComputerId(),csd.getDate());
        new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").showAndWait();
    }
    public static void DeleteSupplierComputerDetail(String sId,String cId) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("DELETE FROM SupplierComputerDetail WHERE sId=? AND cId =?",sId,cId);
    }
}
