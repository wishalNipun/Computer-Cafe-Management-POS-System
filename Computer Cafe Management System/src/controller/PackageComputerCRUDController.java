package controller;

import javafx.scene.control.Alert;
import model.Computer;
import model.ComputerSupplierDetail;
import model.Package;
import model.PackageComputerDetail;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PackageComputerCRUDController {
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
    public static ResultSet getPackageComputerDetail() throws SQLException, ClassNotFoundException {
        return   CrudUtil.execute("SELECT * FROM PackageComputerDetail");
    }
    public static ArrayList<String> PackageId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT id FROM PackagePlan");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
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
    public static void DeletePackageComputerDetail(String pId,String cId) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("DELETE FROM PackageComputerDetail WHERE pId=? AND cId =?",pId,cId);
    }
    public static void setPackageComputerDetail(PackageComputerDetail pcd) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("INSERT INTO  PackageComputerDetail VALUES (?,?)",pcd.getPackageId(),pcd.getComputerId());
        new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").showAndWait();
    }
}
