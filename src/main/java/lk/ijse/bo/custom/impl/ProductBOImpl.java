package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ProductBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.DAOTypes;
import lk.ijse.dao.custom.ProductDAO;
import lk.ijse.entity.Product;
import lk.ijse.model.OrderDetailDTO;
import lk.ijse.model.ProductDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBOImpl implements ProductBO {

    ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.PRODUCT);
    @Override
    public  boolean saveProduct(ProductDTO productDTO) throws SQLException {
        return productDAO.save(new Product(productDTO.getProductId(),productDTO.getProductName(),productDTO.getUnitPrice(),productDTO.getQtyOnHand()));
    }
    @Override
    public  boolean updateProduct(ProductDTO productDTO) throws SQLException {
        return productDAO.update(new Product(productDTO.getProductId(),productDTO.getProductName(),productDTO.getUnitPrice(),productDTO.getQtyOnHand()));
    }
    @Override
    public ProductDTO searchProductById(String productId) throws SQLException {
      Product product = productDAO.search(productId);
      return new ProductDTO(product.getProductId(),product.getProductName(),product.getUnitPrice(),product.getQtyOnHand());
    }

    @Override
    public int getProductCount() throws SQLException {
        return 0;
    }

    @Override
    public boolean deleteProduct(String productId) throws SQLException {
        return productDAO.delete(productId);
    }
    @Override
    public List<ProductDTO> getAllProducts() throws SQLException {

        List<Product> products = productDAO.getAll();
        List<ProductDTO> proList = new ArrayList<>();

       for (Product product : products){
           ProductDTO productDTO = new ProductDTO(product.getProductId(),product.getProductName(),product.getUnitPrice(),product.getQtyOnHand());
           proList.add(productDTO);
       }
       return proList;
    }
    @Override
    public List<String> getProductIds() throws SQLException {

        return productDAO.getIds();
    }
    @Override
    public boolean updateProduct(List<OrderDetailDTO> odtList) throws SQLException {

        for (OrderDetailDTO od : odtList){
            boolean isUpdateQty = productDAO.updateQty(od.getProductId(),od.getQty());
            if(!isUpdateQty){
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean updateProductQty(String productId,int qty) throws SQLException {
        /*String sql = "UPDATE Product SET qty_on_hand = qty_on_hand - ? WHERE product_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1,qty);
        pstm.setString(2,productId);

        return pstm.executeUpdate() > 0;*/
        return productDAO.updateQty(productId,qty);
    }
    @Override
    public String getCurrentPrdId() throws SQLException {
        return productDAO.getCurrentId();
    }
    @Override
    public ProductDTO searchProductByName(String productName) throws SQLException {
       Product product = productDAO.searchProductByName(productName);
       return new ProductDTO(product.getProductId(),product.getProductName(),product.getUnitPrice(),product.getQtyOnHand());
    }
}
