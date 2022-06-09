package controller;

import javafx.scene.control.Alert;
import model.Computer;
import model.Package;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ComputerDataCRUDController {
    public static ResultSet getComputerDetail() throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("SELECT * FROM Computer");
    }
    public static void DeleteComputer(String id) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("Delete From Computer Where id =?",id);
    }
    public static void setComputer(Computer c) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("INSERT INTO Computer VALUES (?,?,?)",c.getComputerId(),c.getComputerType(),c.getComputerPrice());
        new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").showAndWait();
    }

    public static void update(Computer c) throws SQLException, ClassNotFoundException {
        boolean isUpdated = CrudUtil.execute("Update Computer SET type=? , price=?  WHERE id=?",c.getComputerType(),c.getComputerPrice(),c.getComputerId());
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
    }
}
