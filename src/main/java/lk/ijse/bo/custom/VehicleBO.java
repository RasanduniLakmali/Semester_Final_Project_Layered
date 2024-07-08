package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Vehicle;
import lk.ijse.model.VehicleDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface VehicleBO extends SuperBO {

    public VehicleDTO searchVehicle(String vehicleId) throws SQLException;
    public int getCount() throws SQLException;
    public List<String> getVehicleIds() throws SQLException;

    public boolean saveVehicle(VehicleDTO vehicleDTO) throws SQLException;

    public boolean updateVehicle(VehicleDTO vehicleDTO) throws SQLException;

    public boolean deleteVehicle(String vehicleId) throws SQLException;

    public List<VehicleDTO> getAllVehicles() throws SQLException;

    public String getCurrentVehicleId() throws SQLException;

    public VehicleDTO searchVehicleByNumber(String vehicleNumber) throws SQLException;
}
