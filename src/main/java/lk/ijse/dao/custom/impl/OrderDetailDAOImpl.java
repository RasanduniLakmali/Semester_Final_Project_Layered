package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public boolean save(List<OrderDetail> odtList) throws SQLException {
        for(OrderDetail od : odtList){
            boolean isSaved = save(od);
            if(!isSaved){

                return false;
            }
        }
        return true;
    }
    @Override
    public boolean save(OrderDetail orderDetail) throws SQLException {
       return SQLUtil.execute("INSERT INTO Order_Details VALUES(?,?,?,?)",orderDetail.getOrderId(),orderDetail.getProductId(),orderDetail.getQty(),orderDetail.getUnitPrice());

    }

    @Override
    public boolean update(OrderDetail dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public List<OrderDetail> getAll() throws SQLException {
        return null;
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
    public OrderDetail search(String id) throws SQLException {
        return null;
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }


}
