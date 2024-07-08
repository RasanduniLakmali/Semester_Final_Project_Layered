package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public  boolean save(Employee employee) throws SQLException {
       return SQLUtil.execute("INSERT INTO Employee VALUES(?,?,?,?,?,?,?)",employee.getEmployeeId(),employee.getEmployeeName(),employee.getNIC(),employee.getAddress(),employee.getContact(),employee.getSalary(),employee.getSection());
    }
    @Override
    public  boolean update(Employee employee) throws SQLException {
       return SQLUtil.execute("UPDATE Employee SET employee_id = ?,employee_name = ?,address = ?,contact = ?,salary = ?,section = ? WHERE NIC = ?",employee.getEmployeeId(),employee.getEmployeeName(),employee.getAddress(),employee.getContact(),employee.getSalary(),employee.getSection(),employee.getNIC());
    }
    @Override
    public  Employee search(String employeeId) throws SQLException {
       ResultSet rst = SQLUtil.execute("SELECT * FROM Employee WHERE employee_id = ?",employeeId);
       rst.next();
       return new Employee(rst.getString("employee_id"),rst.getString("employee_name"),rst.getString("NIC"),rst.getString("address"),rst.getString("contact"),rst.getDouble("salary"),rst.getString("section"));
    }
    @Override
    public  boolean delete(String employeeId) throws SQLException {
       return SQLUtil.execute("DELETE FROM Employee WHERE employee_id = ?",employeeId);
    }
    @Override
    public  List<Employee> getAll() throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Employee");

        List<Employee> empList = new ArrayList<>();
        while (rst.next()){
            Employee employee = new Employee(
               rst.getString("employee_id"),
               rst.getString("employee_name"),
               rst.getString("NIC"),
               rst.getString("address"),
               rst.getString("contact"),
               rst.getDouble("salary"),
               rst.getString("section")
            );
            empList.add(employee);
        }
        return empList;
    }
    @Override
    public  List<String> getIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT employee_id FROM Employee");

        List<String> empIdList = new ArrayList<>();
        while (rst.next()){
            empIdList.add(rst.getString(1));
        }
        return empIdList;
    }
    @Override
    public  String getCurrentId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT employee_id FROM Employee ORDER BY employee_id DESC LIMIT 1");
        rst.next();
        return rst.getString("employee_id");
    }
    @Override
    public  Employee searchEmployeeByNIC(String NIC) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Employee WHERE NIC = ?",NIC);
        rst.next();
        return new Employee(rst.getString("employee_id"),rst.getString("employee_name"),rst.getString("NIC"),rst.getString("address"),rst.getString("contact"),rst.getDouble("salary"),rst.getString("section"));
    }
    @Override
    public  int getCount() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT COUNT(employee_id) AS employee_count FROM Employee");
        rst.next();
        return rst.getInt("employee_count");
    }
}

