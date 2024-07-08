package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.entity.Order;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public String getCurrentId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT order_id FROM Orders ORDER BY order_id DESC LIMIT 1");
        rst.next();
        return rst.getString("order_id");
    }

    @Override
    public boolean save(Order order) throws SQLException {
      return SQLUtil.execute("INSERT INTO Orders VALUES(?,?,?,?)",order.getOrderId(),order.getCustomerId(),order.getDate(),order.getPayment());
    }

    @Override
    public boolean update(Order dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public List<Order> getAll() throws SQLException {
        return null;
    }

    @Override
    public Order search(String orderId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Orders WHERE order_id = ?",orderId);
        rst.next();
        return new Order(rst.getString("order_id"),rst.getString("customer_id"),rst.getDate("date"),rst.getDouble("payment"));
    }

    @Override
    public List<String> getIds() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT order_id FROM Orders");

        List<String> oIdList = new ArrayList<>();
        while(resultSet.next()){
            oIdList.add(resultSet.getString(1));
        }
        return oIdList;
    }
    @Override
    public  String totalIncome() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(payment) FROM Orders");

        String value = "0000.00";
        if (resultSet.next()){
            value = resultSet.getString(1);

        }
        return  value;
    }
    @Override
    public  Map<String, Double> getTotalMonthlyIncome() throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Orders");

        Map<String, Double> monthlyTotals = new HashMap<>();
        while (rst.next()){
            String month = getMonthFromDate(rst.getDate(3));
            monthlyTotals.merge(month, rst.getDouble(4), Double::sum);
        }
        return monthlyTotals;
    }
    @Override
    public  String getMonthFromDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
        return dateFormat.format(date);
    }
    @Override
    public  int getCount() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(order_id) AS order_count FROM Orders");

        if (resultSet.next()){
            return resultSet.getInt("order_count");
        }
        return 0;
    }
    @Override
    public Map<String, Double> getOrderCountMonthly() throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT EXTRACT(MONTH FROM date) AS month, COUNT(order_id) AS order_count FROM Orders GROUP BY EXTRACT(MONTH FROM date) ORDER BY month");

        Map<String, Double> monthlyCount = new HashMap<>();
        while (rst.next()) {
            int monthNumber = rst.getInt("month");
            String monthName = getMonthName(monthNumber);
            Double orderCount = rst.getDouble("order_count");
            monthlyCount.put(monthName, orderCount);
        }
        return monthlyCount;
    }
    @Override
    public String getMonthName(int monthNumber) {
        String[] months = new DateFormatSymbols().getMonths();
        return months[monthNumber - 1];
    }
}
