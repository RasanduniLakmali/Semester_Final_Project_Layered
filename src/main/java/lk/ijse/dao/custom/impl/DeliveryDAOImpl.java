package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.DeliveryDAO;
import lk.ijse.db.DBConnection;
import lk.ijse.entity.Delivery;
import lk.ijse.model.DeliveryDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAOImpl implements DeliveryDAO {

    @Override
    public boolean save(Delivery delivery) throws SQLException {
        return SQLUtil.execute("INSERT INTO Delivery VALUES(?,?,?,?,?)",delivery.getDeliveryId(),delivery.getOrderId(),delivery.getVehicleId(),delivery.getDate(),delivery.getStatus());

    }
    @Override
    public boolean update(Delivery delivery) throws SQLException {
       return SQLUtil.execute("UPDATE Delivery SET order_id = ?,vehicle_id = ?,date = ?,status = ? WHERE delivery_id = ?",delivery.getOrderId(),delivery.getVehicleId(),delivery.getDate(),delivery.getStatus(),delivery.getDeliveryId());
    }
    @Override
    public Delivery search(String deliveryId) throws SQLException {
       ResultSet rst = SQLUtil.execute("SELECT * FROM Delivery WHERE delivery_id = ?",deliveryId);
       rst.next();
       return new Delivery(rst.getString("delivery_id"),rst.getString("order_id"),rst.getString("vehicle_id"),rst.getDate("date"),rst.getString("status"));
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    @Override
    public boolean delete(String deliveryId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Delivery WHERE delivery_id = ?",deliveryId);
    }
    @Override
    public List<Delivery> getAll() throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Delivery");

        List<Delivery> deliveryList = new ArrayList<>();

        while (rst.next()){
            Delivery delivery = new Delivery(
                    rst.getString("delivery_id"),
                    rst.getString("order_id"),
                    rst.getString("vehicle_id"),
                    rst.getDate("date"),
                    rst.getString("status")
            );
            deliveryList.add(delivery);
        }
        return deliveryList;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }

    @Override
    public String getCurrentId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT delivery_id FROM Delivery ORDER BY delivery_id DESC LIMIT 1");

        if (resultSet.next()){
            String deliveryId = resultSet.getString(1);
            return deliveryId;
        }
        return null;
    }
}
