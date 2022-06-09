package controller;

import db.DBConnection;
import javafx.scene.control.Alert;
import model.Member;
import model.User;
import util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataCRUDController {
    public static ResultSet getUserDetail() throws SQLException, ClassNotFoundException {
        return   CrudUtil.execute("SELECT * FROM `User`");

    }
    public static void setUser(User u) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("INSERT INTO `User` VALUES (?,?,?,?,?,?,?)",u.getUserId(),u.getName(),u.getRole(),u.getTelNo(),u.getEmail(),u.getUserName(),u.getPassword());
        new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").showAndWait();
    }

    public static void update(User u) throws SQLException, ClassNotFoundException {
        boolean isUpdated = CrudUtil.execute("Update `User` SET name=? , role=? ,telNo=? , email=? ,userName=? ,password=?  WHERE id=?",u.getName(),u.getRole(),u.getTelNo(),u.getEmail(),u.getUserName(),u.getPassword(),u.getUserId());
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
    }
    public static void DeleteUser(String id) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("Delete From `User` Where id =?",id);
    }
    public static User getUser(String userName,String password) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM User WHERE UserName=? and Password=?",userName,password);
        if (rst.next()) {
            return new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );

        } else {
            return null;
        }
    }
}
