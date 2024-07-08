package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.OrderDetail;
import lk.ijse.entity.Product;
import lk.ijse.model.OrderDetailDTO;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO extends CrudDAO<Product> {

    public boolean update(List<OrderDetail> odtList) throws SQLException;
    public boolean updateQty(String productId,int qty) throws SQLException;
    public Product searchProductByName(String productName) throws SQLException;
}
