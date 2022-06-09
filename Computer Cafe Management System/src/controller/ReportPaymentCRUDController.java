package controller;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportPaymentCRUDController {
    public static ResultSet PaymentDetail(String date) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT *  FROM MemberPackagePaymentDetail WHERE payDate=?",date);
        return result;

    }

}
