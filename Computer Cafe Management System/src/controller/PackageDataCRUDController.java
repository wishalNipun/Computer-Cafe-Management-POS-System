package controller;

import javafx.scene.control.Alert;
import model.Package;
import util.CrudUtil;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PackageDataCRUDController {
    public static ResultSet getPackageDetail() throws SQLException, ClassNotFoundException {
        return   CrudUtil.execute("SELECT * FROM PackagePlan");

    }
    public static void setPackage(Package packData) throws SQLException, ClassNotFoundException {
       CrudUtil.execute("INSERT INTO PackagePlan VALUES (?,?,?)",packData.getPackageId(),packData.getPackageName(),packData.getPackagePrice());
        new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").showAndWait();
    }

    public static void update(Package packData) throws SQLException, ClassNotFoundException {
        boolean isUpdated = CrudUtil.execute("Update PackagePlan SET name=? , price=?  WHERE id=?",packData.getPackageName(),packData.getPackagePrice(),packData.getPackageId());
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
    }
    public static void DeletePackage(String id) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("Delete From PackagePlan Where id =?",id);
    }

}
