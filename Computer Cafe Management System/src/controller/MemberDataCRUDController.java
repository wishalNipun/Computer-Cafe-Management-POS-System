package controller;

import javafx.scene.control.Alert;
import model.Member;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDataCRUDController {
    public static ResultSet getMemberDetail() throws SQLException, ClassNotFoundException {
        return   CrudUtil.execute("SELECT * FROM Member");

    }
    public static void setMember(Member m) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("INSERT INTO Member VALUES (?,?,?,?)",m.getMemberId(),m.getMemberName(),m.getTelNo(),m.getAddress());
        new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").showAndWait();
    }

    public static void update(Member m) throws SQLException, ClassNotFoundException {
        boolean isUpdated = CrudUtil.execute("Update Member SET name=? , telNo=? , address=? WHERE id=?",m.getMemberName(),m.getTelNo(),m.getAddress(),m.getMemberId());
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
    }
    public static void DeleteMember(String id) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("Delete From Member Where id =?",id);
    }
}
