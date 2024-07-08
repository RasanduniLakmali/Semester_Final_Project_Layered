package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.MaterialDetail;
import lk.ijse.model.MaterialDetailDTO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface MaterialDetailBO extends SuperBO {


    public boolean saveDetails(MaterialDetailDTO materialDetailDTO) throws SQLException;

    public boolean updateDetails(MaterialDetailDTO materialDetailDTO) throws SQLException;

    public boolean deleteDetails(String materialOrderId) throws SQLException;

    public MaterialDetailDTO searchDetails(String materialOrderId) throws SQLException;

    public List<MaterialDetailDTO> getAllDetails() throws SQLException;

    public String getCurrentMtdId() throws SQLException;

    public String totalExpenses() throws SQLException;

    public Map<String, Double> getTotalMonthlyExpenses() throws SQLException;

    public String getMonthFromDate(Date date);
    public int getCount() throws SQLException;
    public List<String> getIds() throws SQLException;
}
