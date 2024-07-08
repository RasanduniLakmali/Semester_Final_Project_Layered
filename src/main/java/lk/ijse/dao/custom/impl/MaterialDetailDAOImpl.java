package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.MaterialDetailDAO;
import lk.ijse.db.DBConnection;
import lk.ijse.entity.MaterialDetail;
import lk.ijse.model.MaterialDetailDTO;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class MaterialDetailDAOImpl implements MaterialDetailDAO {

    @Override
    public boolean save(MaterialDetail materialDetail) throws SQLException {
       return SQLUtil.execute("INSERT INTO Material_Details VALUES(?,?,?,?,?,?,?)",materialDetail.getMaterialOrderId(),materialDetail.getMaterialId(),materialDetail.getSupplierId(),materialDetail.getQty(),materialDetail.getUnitPrice(),materialDetail.getPayment(),materialDetail.getDate());
    }
    @Override
    public boolean update(MaterialDetail materialDetail) throws SQLException {
       return SQLUtil.execute("UPDATE Material_Details SET material_id = ?,supplier_id = ?,qty = ?,unit_price = ?,payment = ?,date = ? WHERE material_order_id = ?",materialDetail.getMaterialId(),materialDetail.getSupplierId(),materialDetail.getQty(),materialDetail.getUnitPrice(),materialDetail.getPayment(),materialDetail.getDate(),materialDetail.getMaterialOrderId());
    }
    @Override
    public boolean delete(String materialOrderId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Material_Details WHERE material_order_id = ?",materialOrderId);
    }
    @Override
    public MaterialDetail search(String materialOrderId) throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Material_Details WHERE material_order_id = ?",materialOrderId);
        rst.next();
        return new MaterialDetail(rst.getString("material_order_id"),rst.getString("material_id"),rst.getString("supplier_id"),rst.getInt("qty"),rst.getDouble("unit_price"),rst.getDouble("payment"),rst.getDate("date"));

    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    @Override
    public List<MaterialDetail> getAll() throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Material_Details");

        List<MaterialDetail> detailList = new ArrayList<>();
        while (rst.next()) {
            MaterialDetail materialDetail = new MaterialDetail(
                    rst.getString("material_order_id"),
                    rst.getString("material_id"),
                    rst.getString("supplier_id"),
                    rst.getInt("qty"),
                    rst.getDouble("unit_price"),
                    rst.getDouble("payment"),
                    rst.getDate("date")
            );
            detailList.add(materialDetail);
        }
           return detailList;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }

    @Override
    public String getCurrentId() throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT material_order_id FROM Material_Details ORDER BY material_order_id DESC LIMIT 1");

        if (rst.next()){
            String materialOrderId = rst.getString(1);
            return materialOrderId;
        }
        return null;
    }
    @Override
    public String total() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT SUM(payment) FROM Material_Details");

        String value = "0000.00";
        if(resultSet.next()){
            value = resultSet.getString(1);
        }
        return value;
    }
    @Override
    public Map<String, Double> getTotalMonthly() throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Material_Details");

        Map<String, Double> monthlyTotals = new HashMap<>();

        while (rst.next()){
            String month = getMonth(rst.getDate(7));
            monthlyTotals.merge(month, rst.getDouble(6), Double::sum);
        }
        return monthlyTotals;
    }
    @Override
    public String getMonth(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
        return dateFormat.format(date);
    }

}
