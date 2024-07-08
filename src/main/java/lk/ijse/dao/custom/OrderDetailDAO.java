package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.OrderDetail;
import lk.ijse.model.OrderDetailDTO;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OrderDetail> {

    public boolean save(List<OrderDetail> odtList) throws SQLException;
}
