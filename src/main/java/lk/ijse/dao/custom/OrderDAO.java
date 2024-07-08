package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Order;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

public interface OrderDAO extends CrudDAO<Order> {

    public  String totalIncome() throws SQLException;
    public Map<String, Double> getTotalMonthlyIncome() throws SQLException;
    public  String getMonthFromDate(Date date);
    public Map<String, Double> getOrderCountMonthly() throws SQLException;
    public String getMonthName(int monthNumber);
}
