package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.DAOTypes;
import lk.ijse.entity.Customer;
import lk.ijse.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.CUSTOMER);
    @Override
    public  boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
       return customerDAO.save(new Customer(customerDTO.getCustomer_id(),customerDTO.getCustomer_name(),customerDTO.getAddress(),customerDTO.getContact_number(),customerDTO.getEmail()));
    }

    @Override
    public  boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
       return customerDAO.update(new Customer(customerDTO.getCustomer_id(),customerDTO.getCustomer_name(),customerDTO.getAddress(),customerDTO.getContact_number(),customerDTO.getEmail()));
    }

    @Override
    public  boolean deleteCustomer(String customer_id) throws SQLException {
        return customerDAO.delete(customer_id);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException {

        List<Customer> customers = customerDAO.getAll();
        List<CustomerDTO> cuList = new ArrayList<>();

        for (Customer customer : customers){
            CustomerDTO customerDTO = new CustomerDTO(customer.getCustomer_id(),customer.getCustomer_name(),customer.getAddress(),customer.getContact_number(),customer.getEmail());
            cuList.add(customerDTO);
        }
       return cuList;
    }
    @Override
    public  CustomerDTO searchCustomerByContact(String contact) throws SQLException {
        Customer customer = customerDAO.searchCustomerByContact(contact);
        return new CustomerDTO(customer.getCustomer_id(),customer.getCustomer_name(),customer.getAddress(),customer.getContact_number(),customer.getEmail());
    }
    @Override
    public  List<String> getCustomerIds() throws SQLException {
        /*String sql = "SELECT customer_id FROM Customer";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;*/
        return customerDAO.getIds();

    }
    @Override
    public  String getCurrentCusId() throws SQLException {
        /*ResultSet rst = SQLUtil.execute("SELECT customer_id FROM Customer ORDER BY customer_id DESC LIMIT 1");
        rst.next();
        return rst.getString("customer_id");*/
        return customerDAO.getCurrentId();
    }
    @Override
    public  CustomerDTO searchCustomerById(String customerId) throws SQLException {
       Customer customer = customerDAO.search(customerId);
       return new CustomerDTO(customer.getCustomer_id(),customer.getCustomer_name(),customer.getAddress(),customer.getContact_number(),customer.getEmail());
    }
    @Override
    public  CustomerDTO searchCustomerByName(String customerName) throws SQLException {
        Customer customer = customerDAO.searchCustomerByName(customerName);
        return new CustomerDTO(customer.getCustomer_id(),customer.getCustomer_name(),customer.getCustomer_name(),customer.getContact_number(),customer.getEmail());
    }
    @Override
    public  int getCustomerCount() throws SQLException {
        return customerDAO.getCount();
    }
}
