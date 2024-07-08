package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.ProductDetailDAO;
import lk.ijse.entity.ProductDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailDAOImpl implements ProductDetailDAO {

    @Override
    public boolean save(ProductDetail productDetail) throws SQLException {
        return SQLUtil.execute("INSERT INTO Product_Details VALUES(?,?)",productDetail.getProductId(),productDetail.getMaterialId());
    }
    @Override
    public boolean update(ProductDetail productDetail) throws SQLException {
        return SQLUtil.execute("UPDATE Product_Details SET material_id = ? WHERE product_id = ?",productDetail.getMaterialId(),productDetail.getProductId());
    }
    @Override
    public boolean delete(String productId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Product_Details WHERE product_id = ?",productId);
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }

    @Override
    public String getCurrentId() throws SQLException {
        return null;
    }

    @Override
    public List<ProductDetail> getAll() throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Product_Details");

        List<ProductDetail> detailList = new ArrayList<>();

        while (rst.next()){
            ProductDetail productDetail = new ProductDetail(
                    rst.getString("product_id"),
                    rst.getString("material_id")
            );
            detailList.add(productDetail);
        }
        return  detailList;
    }
    @Override
    public ProductDetail search(String productId) throws SQLException {
       ResultSet rst = SQLUtil.execute("SELECT * FROM Product_Details WHERE product_id = ?",productId);
       rst.next();
       return new ProductDetail(rst.getString("product_id"),rst.getString("material_id"));
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }
}
