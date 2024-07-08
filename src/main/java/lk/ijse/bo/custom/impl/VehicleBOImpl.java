package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.VehicleBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.DAOTypes;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.VehicleDAO;
import lk.ijse.entity.Vehicle;
import lk.ijse.model.VehicleDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleBOImpl implements VehicleBO {

    VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.VEHICLE);

    @Override
    public VehicleDTO searchVehicle(String vehicleId) throws SQLException {
        Vehicle vehicle = vehicleDAO.search(vehicleId);
        return new VehicleDTO(vehicle.getVehicleId(),vehicle.getEmployeeId(),vehicle.getVehicleNumber(),vehicle.getModel());
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    @Override
    public List<String> getVehicleIds() throws SQLException {
       return vehicleDAO.getIds();

    }
    @Override
    public boolean saveVehicle(VehicleDTO vehicleDTO) throws SQLException {
        return vehicleDAO.save(new Vehicle(vehicleDTO.getVehicleId(),vehicleDTO.getEmployeeId(),vehicleDTO.getVehicleNumber(),vehicleDTO.getModel()));

    }
    @Override
    public boolean updateVehicle(VehicleDTO vehicleDTO) throws SQLException {
        return vehicleDAO.update(new Vehicle(vehicleDTO.getVehicleId(),vehicleDTO.getEmployeeId(),vehicleDTO.getVehicleNumber(),vehicleDTO.getModel()));
    }
    @Override
    public boolean deleteVehicle(String vehicleId) throws SQLException {
        return vehicleDAO.delete(vehicleId);
    }
    @Override
    public List<VehicleDTO> getAllVehicles() throws SQLException {
       List<Vehicle> vehicles = vehicleDAO.getAll();
       List<VehicleDTO> vehicleList = new ArrayList<>();

       for (Vehicle vehicle : vehicles){
           VehicleDTO vehicleDTO = new VehicleDTO(vehicle.getVehicleId(),vehicle.getEmployeeId(),vehicle.getVehicleNumber(),vehicle.getModel());
           vehicleList.add(vehicleDTO);
       }
      return vehicleList;
    }
    @Override
    public String getCurrentVehicleId() throws SQLException {
        return vehicleDAO.getCurrentId();
    }
    @Override
    public VehicleDTO searchVehicleByNumber(String vehicleNumber) throws SQLException {
      Vehicle vehicle = vehicleDAO.searchByNumber(vehicleNumber);
      return new VehicleDTO(vehicle.getVehicleId(),vehicle.getEmployeeId(),vehicle.getVehicleNumber(),vehicle.getModel());
    }
}
