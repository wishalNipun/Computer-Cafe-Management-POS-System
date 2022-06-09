package controller;

import model.Member;
import model.Package;
import model.Payment;
import util.CrudUtil;
import view.tm.PaymentTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchPaymentCRUDController {

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

    public static ResultSet PaymentMemberDetail(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT memId,pId,cost,payDate ,payTime  FROM MemberPackagePaymentDetail WHERE memId =?",id);
        return result;

    }
    public static void DeletePaymentDetail(String mid,String pid, String date,String time) throws SQLException, ClassNotFoundException {
        CrudUtil.execute("DELETE FROM MemberPackagePaymentDetail WHERE memId=? AND pID =? AND payDate =? AND payTime =?",mid,pid,date,time);
    }
}
