package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.model.EmployeeDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EmployeeBO extends SuperBO {

    public  boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException;

    public  boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException;

    public  EmployeeDTO searchEmployeeById(String employeeId) throws SQLException;

    public  boolean deleteEmployee(String employeeId) throws SQLException;

    public List<EmployeeDTO> getAllEmployees() throws SQLException;

    public  List<String> getEmployeeIds() throws SQLException;

    public  String getCurrentEmpId() throws SQLException;

    public  EmployeeDTO searchEmployeeByNIC(String NIC) throws SQLException;

    public  int getEmployeeCount() throws SQLException;


}
