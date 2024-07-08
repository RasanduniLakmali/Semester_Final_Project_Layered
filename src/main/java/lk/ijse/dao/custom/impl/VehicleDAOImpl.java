package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.VehicleDAO;
import lk.ijse.db.DBConnection;
import lk.ijse.entity.Vehicle;
import lk.ijse.model.VehicleDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAOImpl implements VehicleDAO {

    @Override
    public Vehicle search(String vehicleId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Vehicle WHERE vehicle_id = ?",vehicleId);
        rst.next();
        return new Vehicle(rst.getString("vehicle_id"),rst.getString("employee_id"),rst.getString("vehicle_number"),rst.getString("model"));
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    @Override
    public List<String> getIds() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT vehicle_id FROM Vehicle");

        List<String> vIdList = new ArrayList<>();
        while (resultSet.next()){
            vIdList.add(resultSet.getString(1));
        }
        return vIdList;
    }
    @Override
    public boolean save(Vehicle vehicle) throws SQLException {
        return SQLUtil.execute("INSERT INTO Vehicle VALUES(?,?,?,?)",vehicle.getVehicleId(),vehicle.getEmployeeId(),vehicle.getVehicleNumber(),vehicle.getModel());

    }
    @Override
    public boolean update(Vehicle vehicle) throws SQLException {
        return SQLUtil.execute("UPDATE Vehicle SET vehicle_id = ?,employee_id = ?,model = ? WHERE vehicle_number = ?",vehicle.getVehicleId(),vehicle.getEmployeeId(),vehicle.getModel(),vehicle.getVehicleNumber());
    }
    @Override
    public boolean delete(String vehicleId) throws SQLException {
       return SQLUtil.execute("DELETE FROM Vehicle WHERE vehicle_id = ?",vehicleId);
    }
    @Override
    public List<Vehicle> getAll() throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Vehicle");

        List<Vehicle> vehicleList = new ArrayList<>();

        while (rst.next()){
            Vehicle vehicle = new Vehicle(
                    rst.getString("vehicle_id"),
                    rst.getString("employee_id"),
                    rst.getString("vehicle_number"),
                    rst.getString("model")
            );
            vehicleList.add(vehicle);
        }
       return vehicleList;
    }
    @Override
    public String getCurrentId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT vehicle_id FROM Vehicle ORDER BY vehicle_id DESC LIMIT 1");

        if (resultSet.next()){
            String vehicleId = resultSet.getString(1);
            return vehicleId;
        }
        return null;
    }
    @Override
    public Vehicle searchByNumber(String vehicleNumber) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Vehicle WHERE vehicle_number = ?",vehicleNumber);
        rst.next();
        return new Vehicle(rst.getString("vehicle_id"),rst.getString("employee_id"),rst.getString("vehicle_number"),rst.getString("model"));
    }
}
