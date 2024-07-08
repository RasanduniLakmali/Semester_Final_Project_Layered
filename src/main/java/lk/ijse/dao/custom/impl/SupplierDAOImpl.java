package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public boolean save(Supplier supplier) throws SQLException {
        return SQLUtil.execute("INSERT INTO Supplier VALUES(?,?,?,?)",supplier.getSupplierId(),supplier.getSupplierName(),supplier.getAddress(),supplier.getContact());
    }
    @Override
    public  boolean update(Supplier supplier) throws SQLException {
        return SQLUtil.execute("UPDATE Supplier SET supplier_id = ?,supplier_name = ?,address = ? WHERE contact = ?",supplier.getSupplierId(),supplier.getSupplierName(),supplier.getAddress(),supplier.getContact());
    }
    @Override
    public  Supplier search(String supplierId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Supplier WHERE supplier_id = ?",supplierId);
        rst.next();
        return new Supplier(rst.getString("supplier_id"),rst.getString("supplier_name"),rst.getString("address"),rst.getString("contact"));
    }
    @Override
    public  boolean delete(String supplierId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Supplier WHERE supplier_id = ?",supplierId);
    }
    @Override
    public  List<Supplier> getAll() throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Supplier");

        List<Supplier> supList = new ArrayList<>();
        while (rst.next()){
           Supplier supplier = new Supplier(
                   rst.getString("supplier_id"),
                   rst.getString("supplier_name"),
                   rst.getString("address"),
                   rst.getString("contact")
           );
        supList.add(supplier);
        }
        return supList;
    }
    @Override
    public  List<String> getIds() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT supplier_id FROM Supplier");

        List<String> supIdList = new ArrayList<>();
        while (resultSet.next()){
            supIdList.add(resultSet.getString(1));
        }
        return supIdList;
    }
    @Override
    public  String getCurrentId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT supplier_id FROM Supplier ORDER BY supplier_id DESC LIMIT 1");

        if (resultSet.next()){
            String supplierId = resultSet.getString(1);
            return  supplierId;
        }
        return null;
    }
    @Override
    public  Supplier searchSupplierByContact(String contact) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Supplier WHERE contact = ?",contact);
        rst.next();
        return new Supplier(rst.getString("supplier_id"),rst.getString("supplier_name"),rst.getString("address"),rst.getString("contact"));
    }
    @Override
    public  int getCount() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT COUNT(supplier_id) AS supplier_count FROM Supplier");
        rst.next();
        return rst.getInt("supplier_count");
    }
}
