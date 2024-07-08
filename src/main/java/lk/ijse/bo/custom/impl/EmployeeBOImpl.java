package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.DAOTypes;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.entity.Employee;
import lk.ijse.model.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.EMPLOYEE);
    @Override
    public  boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return employeeDAO.save(new Employee(employeeDTO.getEmployeeId(),employeeDTO.getEmployeeName(),employeeDTO.getNIC(),employeeDTO.getAddress(),employeeDTO.getContact(),employeeDTO.getSalary(),employeeDTO.getSection()));
    }

    @Override
    public  boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return employeeDAO.update(new Employee(employeeDTO.getEmployeeId(),employeeDTO.getEmployeeName(),employeeDTO.getNIC(),employeeDTO.getAddress(),employeeDTO.getContact(),employeeDTO.getSalary(),employeeDTO.getSection()));
    }

    @Override
    public  EmployeeDTO searchEmployeeById(String employeeId) throws SQLException {
       Employee employee = employeeDAO.search(employeeId);
       return new EmployeeDTO(employee.getEmployeeId(),employee.getEmployeeName(),employee.getNIC(),employee.getAddress(),employee.getContact(),employee.getSalary(),employee.getSection());
    }
    @Override
    public  boolean deleteEmployee(String employeeId) throws SQLException {
        return employeeDAO.delete(employeeId);
    }
    @Override
    public List<EmployeeDTO> getAllEmployees() throws SQLException {

        List<Employee> employees = employeeDAO.getAll();
        List<EmployeeDTO> empList = new ArrayList<>();

        for (Employee employee : employees){
            EmployeeDTO employeeDTO = new EmployeeDTO(employee.getEmployeeId(),employee.getEmployeeName(),employee.getNIC(),employee.getAddress(),employee.getContact(),employee.getSalary(),employee.getSection());
            empList.add(employeeDTO);
        }
        return empList;
    }
    @Override
    public  List<String> getEmployeeIds() throws SQLException {
        return employeeDAO.getIds();
    }
    @Override
    public  String getCurrentEmpId() throws SQLException {
        return employeeDAO.getCurrentId();
    }
    @Override
    public  EmployeeDTO searchEmployeeByNIC(String NIC) throws SQLException {
        Employee employee = employeeDAO.searchEmployeeByNIC(NIC);
        return new EmployeeDTO(employee.getEmployeeId(),employee.getEmployeeName(),employee.getNIC(),employee.getAddress(),employee.getContact(),employee.getSalary(),employee.getSection());
    }
    @Override
    public  int getEmployeeCount() throws SQLException {
        return employeeDAO.getCount();
    }
}
