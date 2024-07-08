package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.AttendanceBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.DAOTypes;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.AttendanceDAO;
import lk.ijse.entity.Attendance;
import lk.ijse.model.AttendanceDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceBOImpl implements AttendanceBO {

    AttendanceDAO attendanceDAO = (AttendanceDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.ATTENDANCE);
    @Override
    public boolean saveAttendance(AttendanceDTO attendanceDTO) throws SQLException {
        return attendanceDAO.save(new Attendance(attendanceDTO.getAttendanceId(),attendanceDTO.getEmployeeId(),attendanceDTO.getDate(),attendanceDTO.getInTime(),attendanceDTO.getOffTime()));

    }
    @Override
    public boolean updateAttendance(AttendanceDTO attendanceDTO) throws SQLException {
        return attendanceDAO.update(new Attendance(attendanceDTO.getAttendanceId(),attendanceDTO.getEmployeeId(),attendanceDTO.getDate(),attendanceDTO.getInTime(),attendanceDTO.getOffTime()));
    }

    @Override
    public boolean deleteAttendance(String attendanceId) throws SQLException {
       return attendanceDAO.delete(attendanceId);
    }

    @Override
    public AttendanceDTO searchAttendance(String attendanceId) throws SQLException {
       Attendance attendance = attendanceDAO.search(attendanceId);
       return new AttendanceDTO(attendance.getAttendanceId(),attendance.getEmployeeId(),attendance.getDate(),attendance.getInTime(),attendance.getOffTime());
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    @Override
    public List<AttendanceDTO> getAllDetails() throws SQLException {
       List<Attendance> attendances = attendanceDAO.getAll();
       List<AttendanceDTO> attendanceList = new ArrayList<>();

       for (Attendance attendance : attendances){
           AttendanceDTO attendanceDTO = new AttendanceDTO(attendance.getAttendanceId(),attendance.getEmployeeId(),attendance.getDate(),attendance.getInTime(),attendance.getOffTime());
           attendanceList.add(attendanceDTO);
       }
       return attendanceList;
    }

    @Override
    public List<String> getAttendanceIds() throws SQLException {
        return null;
    }

    @Override
    public String getCurrentAttendanceId() throws SQLException {

       return attendanceDAO.getCurrentId();
    }
}
