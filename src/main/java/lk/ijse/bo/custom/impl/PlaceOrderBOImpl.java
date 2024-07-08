package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PlaceOrderBO;
import lk.ijse.dao.*;

import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.dao.custom.ProductDAO;
import lk.ijse.db.DBConnection;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Order;
import lk.ijse.entity.OrderDetail;
import lk.ijse.entity.Product;
import lk.ijse.model.CustomerDTO;

import lk.ijse.model.OrderDTO;
import lk.ijse.model.OrderDetailDTO;
import lk.ijse.model.ProductDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.PRODUCT);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.CUSTOMER);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.ORDER);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.ORDER_DETAIL);
   /* @Override
    public  boolean placeOrder(Order order, List<OrderDetailDTO> orderDetailList) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = orderDAO.save(order);
            if(isOrderSaved) {
                boolean isQtyUpdated = productDAO.update(orderDetailList);
                if (isQtyUpdated){
                    boolean isOrderDetailSaved = orderDetailDAO.save((OrderDetail) orderDetailList);
                    if(isOrderDetailSaved){
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        }
        catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }*/
   @Override
   public boolean placeOrder(OrderDTO orderDTO, List<OrderDetailDTO> orderDetails) throws SQLException {

       Connection connection = null;
       try {
           connection = DBConnection.getInstance().getConnection();

           connection.setAutoCommit(false);

           boolean isSaved = orderDAO.save(new Order(orderDTO.getOrderId(),orderDTO.getCustomerId(),orderDTO.getDate(),orderDTO.getPayment()));

           if (!isSaved) {
               connection.rollback();
               connection.setAutoCommit(true);
               return false;
           }
           for(OrderDetailDTO detail : orderDetails) {
               OrderDetail orderDetail = new OrderDetail(detail.getOrderId(),detail.getProductId(),detail.getQty(),detail.getUnitPrice());
               boolean isDetailSaved = orderDetailDAO.save(orderDetail);

               if (!isDetailSaved) {
                   connection.rollback();
                   connection.setAutoCommit(true);
                   return false;
               }

               ProductDTO productDTO = findItem(detail.getProductId());
               productDTO.setQtyOnHand(productDTO.getQtyOnHand() - detail.getQty());

               boolean isUpdated = productDAO.update(new Product(productDTO.getProductId(),productDTO.getProductName(),productDTO.getUnitPrice(),productDTO.getQtyOnHand()));

               if (!isUpdated) {
                   connection.rollback();
                   connection.setAutoCommit(true);
                   return false;
               }

           }
           connection.commit();
           connection.setAutoCommit(true);
           return true;

       } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
       }
   }
    @Override
    public String getCurrentOrderId() throws SQLException {
        return orderDAO.getCurrentId();
    }
    @Override
    public List<ProductDTO> getAllProducts() throws SQLException {
        List<Product> products = productDAO.getAll();
        List<ProductDTO> productDTOS = new ArrayList<>();

        for (Product product : products){
            ProductDTO productDTO = new ProductDTO(product.getProductId(),product.getProductName(),product.getUnitPrice(),product.getQtyOnHand());
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }
    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException {

        List<Customer> customers = customerDAO.getAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();

        for (Customer customer : customers){
            CustomerDTO customerDTO = new CustomerDTO(customer.getCustomer_id(),customer.getCustomer_name(),customer.getAddress(),customer.getContact_number(),customer.getEmail());
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }
    @Override
    public  CustomerDTO searchCustomer(String customerName) throws SQLException {
        Customer customer = customerDAO.searchCustomerByName(customerName);
        return new CustomerDTO(customer.getCustomer_id(),customer.getCustomer_name(),customer.getAddress(),customer.getContact_number(),customer.getEmail());
    }

    @Override
    public ProductDTO searchProduct(String productId) throws SQLException {
        Product product = productDAO.search(productId);
        return new ProductDTO(product.getProductId(),product.getProductName(),product.getUnitPrice(),product.getQtyOnHand());

    }

    @Override
    public  CustomerDTO searchCustomerById(String customerId) throws SQLException {
       Customer customer = customerDAO.search(customerId);
       return new CustomerDTO(customer.getCustomer_id(),customer.getCustomer_name(),customer.getAddress(),customer.getContact_number(),customer.getEmail());

    }
    @Override
    public  int getCount() throws SQLException {
        return orderDAO.getCount();
    }
    @Override
    public  String income() throws SQLException {
        return orderDAO.totalIncome();
    }
    @Override
    public Map<String, Double> monthlyIncome() throws SQLException {
        return orderDAO.getTotalMonthlyIncome();
    }

    @Override
    public ProductDTO findItem(String code)throws SQLException, ClassNotFoundException {
        try {
            Product product = productDAO.search(code);
            return new ProductDTO(product.getProductId(),product.getProductName(),product.getUnitPrice(),product.getQtyOnHand());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        }

    }
}
