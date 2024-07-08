package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.db.DBConnection;
import lk.ijse.entity.Customer;
import lk.ijse.model.CustomerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer> {
    public Customer searchCustomerByContact(String contact) throws SQLException;
    public Customer searchCustomerByName(String customerName) throws SQLException;


}
