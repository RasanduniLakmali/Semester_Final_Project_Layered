package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Attendance;
import lk.ijse.model.AttendanceDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface AttendanceBO extends SuperBO {


    public boolean saveAttendance(AttendanceDTO attendanceDTO) throws SQLException;

    public boolean updateAttendance(AttendanceDTO attendanceDTO) throws SQLException;


    public boolean deleteAttendance(String attendanceId) throws SQLException;

    public AttendanceDTO searchAttendance(String attendanceId) throws SQLException;


    public int getCount() throws SQLException;


    public List<AttendanceDTO> getAllDetails() throws SQLException;


    public List<String> getAttendanceIds() throws SQLException;


    public String getCurrentAttendanceId() throws SQLException;
}
