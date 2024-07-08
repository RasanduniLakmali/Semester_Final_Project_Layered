package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.model.OrderDetailDTO;
import lk.ijse.model.ProductDTO;

import java.sql.SQLException;
import java.util.List;

public interface ProductBO extends SuperBO {


    public  boolean saveProduct(ProductDTO productDTO) throws SQLException;

    public  boolean updateProduct(ProductDTO productDTO) throws SQLException;

    public ProductDTO searchProductById(String productId) throws SQLException;


    public int getProductCount() throws SQLException;


    public boolean deleteProduct(String productId) throws SQLException;

    public List<ProductDTO> getAllProducts() throws SQLException;

    public List<String> getProductIds() throws SQLException;

    public boolean updateProduct(List<OrderDetailDTO> odtList) throws SQLException;

    public boolean updateProductQty(String productId,int qty) throws SQLException;

    public String getCurrentPrdId() throws SQLException;

    public ProductDTO searchProductByName(String productName) throws SQLException;
}
