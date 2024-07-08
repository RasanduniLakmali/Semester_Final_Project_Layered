package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.MaterialDetail;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

public interface MaterialDetailDAO extends CrudDAO<MaterialDetail> {

    public String total() throws SQLException;
    public Map<String, Double> getTotalMonthly() throws SQLException;
    public String getMonth(Date date);
}
