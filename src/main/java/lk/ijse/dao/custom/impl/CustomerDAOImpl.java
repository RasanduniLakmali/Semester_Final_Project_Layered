package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public  boolean save(Customer customer) throws SQLException {

        return SQLUtil.execute("INSERT INTO Customer VALUES (?,?,?,?,?)",customer.getCustomer_id(),customer.getCustomer_name(),customer.getAddress(),customer.getContact_number(),customer.getEmail());
    }
    @Override
    public  boolean update(Customer customer) throws SQLException {
       return SQLUtil.execute("UPDATE Customer SET customer_id = ?,customer_name = ?,address = ?,email = ? WHERE contact = ?",customer.getCustomer_id(),customer.getCustomer_name(),customer.getAddress(),customer.getEmail(),customer.getContact_number());
    }
    @Override
    public  boolean delete(String customer_id) throws SQLException {
        return SQLUtil.execute("DELETE FROM Customer WHERE customer_id = ?",customer_id);

    }
    @Override
    public  List<Customer> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer");
        List<Customer> cuList = new ArrayList<>();

        while (rst.next()){
            Customer customer = new Customer(
                    rst.getString("customer_id"),
                    rst.getString("customer_name"),
                    rst.getString("address"),
                    rst.getString("contact"),
                    rst.getString("email")
            );
            cuList.add(customer);
        }
        return cuList;
    }
    @Override
    public  Customer searchCustomerByContact(String contact) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer WHERE contact = ?",contact);
        rst.next();
        return new Customer(rst.getString("customer_id"),rst.getString("customer_name"),rst.getString("address"),rst.getString("contact"),rst.getString("email"));
    }


    @Override
    public  List<String> getIds() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT customer_id FROM Customer");

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;

    }
    @Override
    public  String getCurrentId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT customer_id FROM Customer ORDER BY customer_id DESC LIMIT 1");
        rst.next();
        return rst.getString("customer_id");
    }
    @Override
    public  Customer search(String customerId) throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer WHERE customer_id = ?",customerId);
        rst.next();
        return new Customer(rst.getString("customer_id"),rst.getString("customer_name"),rst.getString("address"),rst.getString("contact"),rst.getString("email"));
    }
    @Override
    public  Customer searchCustomerByName(String customerName) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer WHERE customer_name = ?",customerName);
        rst.next();
        return new Customer(rst.getString("customer_id"),rst.getString("customer_name"),rst.getString("address"),rst.getString("contact"),rst.getString("email"));
    }
    @Override
    public  int getCount() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT COUNT(customer_id) AS customer_count FROM Customer");
        rst.next();
        return rst.getInt("customer_count");
    }
}
