package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ProductDetailBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.DAOTypes;
import lk.ijse.dao.custom.ProductDetailDAO;
import lk.ijse.entity.ProductDetail;
import lk.ijse.model.ProductDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailBOImpl implements ProductDetailBO {

    ProductDetailDAO productDetailDAO = (ProductDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.PRODUCT_DETAIL);

    @Override
    public boolean saveDetails(ProductDetailDTO productDetailDTO) throws SQLException {
      return productDetailDAO.save(new ProductDetail(productDetailDTO.getProductId(),productDetailDTO.getMaterialId()));
    }
    @Override
    public boolean updateDetails(ProductDetailDTO productDetailDTO) throws SQLException {
       return productDetailDAO.update(new ProductDetail(productDetailDTO.getProductId(),productDetailDTO.getMaterialId()));
    }
    @Override
    public boolean deleteDetails(String productId) throws SQLException {
        return productDetailDAO.delete(productId);
    }

    @Override
    public List<ProductDetailDTO> getAllDetails() throws SQLException {
        List<ProductDetail> productDetails = productDetailDAO.getAll();
        List<ProductDetailDTO> detailList = new ArrayList<>();

        for (ProductDetail productDetail : productDetails){
            ProductDetailDTO productDetailDTO = new ProductDetailDTO(productDetail.getProductId(),productDetail.getMaterialId());
            detailList.add(productDetailDTO);
        }
        return detailList;
    }
    @Override
    public ProductDetailDTO searchDetailsById(String productId) throws SQLException {
       ProductDetail productDetail = productDetailDAO.search(productId);
       return new ProductDetailDTO(productDetail.getProductId(),productDetail.getMaterialId());
    }

}
