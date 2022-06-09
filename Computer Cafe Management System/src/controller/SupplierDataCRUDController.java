package controller;

import javafx.scene.control.Alert;
import model.Supplier;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierDataCRUDController {
    public static ResultSet getSupplierDetail() throws SQLException, ClassNotFoundException {
        return   CrudUtil.execute("SELECT * FROM Supplier");

    }
    public static void setSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("INSERT INTO Supplier VALUES (?,?,?,?)",supplier.getSupplierId(),supplier.getSupplierName(),supplier.getTelNo(),supplier.getAddress());
        new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").showAndWait();
    }

    public static void update(Supplier supplier) throws SQLException, ClassNotFoundException {
        boolean isUpdated = CrudUtil.execute("Update Supplier SET name=? , telNO=?, address=?  WHERE id=?",supplier.getSupplierName(),supplier.getTelNo(),supplier.getAddress(),supplier.getSupplierId());
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
    }
    public static void DeleteSupplier(String id) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("Delete From Supplier Where id =?",id);
    }
}
