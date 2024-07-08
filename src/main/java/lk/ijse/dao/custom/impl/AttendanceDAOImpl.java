package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.AttendanceDAO;
import lk.ijse.db.DBConnection;
import lk.ijse.entity.Attendance;
import lk.ijse.model.AttendanceDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAOImpl implements AttendanceDAO {

    @Override
    public boolean save(Attendance attendance) throws SQLException {
       return SQLUtil.execute("INSERT INTO Attendance VALUES (?,?,?,?,?)",attendance.getAttendanceId(),attendance.getEmployeeId(),attendance.getDate(),attendance.getInTime(),attendance.getOffTime());

    }
    @Override
    public boolean update(Attendance attendance) throws SQLException {
       return SQLUtil.execute("UPDATE Attendance SET employee_id = ?,date = ?,in_time = ?,off_time = ? WHERE attendance_id = ?",attendance.getEmployeeId(),attendance.getDate(),attendance.getInTime(),attendance.getOffTime(),attendance.getAttendanceId());
    }
    @Override
    public boolean delete(String attendanceId) throws SQLException {
       return SQLUtil.execute("DELETE FROM Attendance WHERE attendance_id = ?",attendanceId);
    }
    @Override
    public Attendance search(String attendanceId) throws SQLException {
       ResultSet rst = SQLUtil.execute("SELECT * FROM Attendance WHERE attendance_id = ?",attendanceId);
       rst.next();
       return new Attendance(rst.getString("attendance_id"),rst.getString("employee_id"),rst.getDate("date"),rst.getString("in_time"),rst.getString("off_time"));
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    @Override
    public List<Attendance> getAll() throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Attendance");

        List<Attendance> atdList = new ArrayList<>();

        while (rst.next()){
            Attendance attendance = new Attendance(
                    rst.getString("attendance_id"),
                    rst.getString("employee_id"),
                    rst.getDate("date"),
                    rst.getString("in_time"),
                    rst.getString("off_time")
            );
            atdList.add(attendance);
        }
       return atdList;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }

    @Override
    public String getCurrentId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT attendance_id FROM Attendance ORDER BY attendance_id DESC LIMIT 1");

        if (resultSet.next()){
            String attendanceId = resultSet.getString(1);
            return attendanceId;
        }
        return null;
    }
}
