package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Order;
import lk.ijse.model.CustomerDTO;

import lk.ijse.model.OrderDTO;
import lk.ijse.model.OrderDetailDTO;
import lk.ijse.model.ProductDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface PlaceOrderBO extends SuperBO {

    public boolean placeOrder(OrderDTO orderDTO, List<OrderDetailDTO> orderDetails) throws SQLException;

    public String getCurrentOrderId() throws SQLException;

    public List<ProductDTO> getAllProducts() throws SQLException;

    public List<CustomerDTO> getAllCustomers() throws SQLException;
    public  CustomerDTO searchCustomer(String customerName) throws SQLException;
    public ProductDTO searchProduct(String productId) throws SQLException;
    public  CustomerDTO searchCustomerById(String customerId) throws SQLException;
    public  int getCount() throws SQLException;
    public  String income() throws SQLException;
    public Map<String, Double> monthlyIncome() throws SQLException;
    public ProductDTO findItem(String code)throws SQLException, ClassNotFoundException;
}
