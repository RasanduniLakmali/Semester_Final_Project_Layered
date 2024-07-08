package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.ProductDAO;
import lk.ijse.entity.OrderDetail;
import lk.ijse.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public  boolean save(Product product) throws SQLException {
        return SQLUtil.execute("INSERT INTO Product VALUES(?,?,?,?)",product.getProductId(),product.getProductName(),product.getUnitPrice(),product.getQtyOnHand());
    }
    @Override
    public  boolean update(Product product) throws SQLException {
       return SQLUtil.execute("UPDATE Product SET product_id = ?,unit_price = ?,qty_on_hand = ? WHERE product_name = ?",product.getProductId(),product.getUnitPrice(),product.getQtyOnHand(),product.getProductName());
    }
    @Override
    public Product search(String productId) throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Product WHERE product_id = ?",productId);
        rst.next();
        return new Product(rst.getString("product_id"),rst.getString("product_name"),rst.getDouble("unit_price"),rst.getInt("qty_on_hand"));
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    @Override
    public boolean delete(String productId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Product WHERE product_id = ?",productId);
    }
    @Override
    public List<Product> getAll() throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Product");
        List<Product> proList = new ArrayList<>();

        while (rst.next()){
           Product product = new Product(
                   rst.getString("product_id"),
                   rst.getString("product_name"),
                   rst.getDouble("unit_price"),
                   rst.getInt("qty_on_hand")
           );
           proList.add(product);
        }
        return proList;
    }
    @Override
    public List<String> getIds() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT product_id FROM Product");

        List<String> idList = new ArrayList<>();
        while (resultSet.next()){
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
    @Override
    public boolean update(List<OrderDetail> odtList) throws SQLException {
        for (OrderDetail od : odtList){
            boolean isUpdateQty = updateQty(od.getProductId(),od.getQty());
                 if(!isUpdateQty){
                     return false;
                 }
        }
        return true;
    }
    @Override
    public boolean updateQty(String productId,int qty) throws SQLException {
        /*String sql = "UPDATE Product SET qty_on_hand = qty_on_hand - ? WHERE product_id = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1,qty);
        pstm.setString(2,productId);

        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("UPDATE Product SET qty_on_hand = qty_on_hand - ? WHERE product_id = ?",qty,productId);
    }
    @Override
    public String getCurrentId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT product_id FROM Product ORDER BY product_id DESC LIMIT 1");
        rst.next();
        return rst.getString("product_id");
    }
    @Override
    public Product searchProductByName(String productName) throws SQLException {
       ResultSet rst = SQLUtil.execute("SELECT * FROM Product WHERE product_name = ?",productName);
       rst.next();
       return new Product(rst.getString("product_id"),rst.getString("product_name"),rst.getDouble("unit_price"),rst.getInt("qty_on_hand"));
    }
}
