package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.db.DBConnection;
import lk.ijse.model.CustomerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerBO extends SuperBO {


    public  boolean saveCustomer(CustomerDTO customerDTO) throws SQLException;

    public  boolean updateCustomer(CustomerDTO customerDTO) throws SQLException;

    public  boolean deleteCustomer(String customer_id) throws SQLException;

    public List<CustomerDTO> getAllCustomers() throws SQLException;

    public  CustomerDTO searchCustomerByContact(String contact) throws SQLException;

    public  List<String> getCustomerIds() throws SQLException;

    public  String getCurrentCusId() throws SQLException;

    public  CustomerDTO searchCustomerById(String customerId) throws SQLException;

    public  CustomerDTO searchCustomerByName(String customerName) throws SQLException;

    public  int getCustomerCount() throws SQLException;
}
