package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.entity.ProductDetail;
import lk.ijse.model.ProductDetailDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductDetailBO extends SuperBO {

    public boolean saveDetails(ProductDetailDTO productDetailDTO) throws SQLException;

    public boolean updateDetails(ProductDetailDTO productDetailDTO) throws SQLException;

    public boolean deleteDetails(String productId) throws SQLException;

    public List<ProductDetailDTO> getAllDetails() throws SQLException;

    public ProductDetailDTO searchDetailsById(String productId) throws SQLException;

}
