package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Vehicle;

import java.sql.SQLException;

public interface VehicleDAO extends CrudDAO<Vehicle> {

    public Vehicle searchByNumber(String vehicleNumber) throws SQLException;
}
