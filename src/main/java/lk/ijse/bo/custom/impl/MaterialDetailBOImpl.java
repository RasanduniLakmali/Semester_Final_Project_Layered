package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.MaterialDetailBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.DAOTypes;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.MaterialDetailDAO;
import lk.ijse.entity.MaterialDetail;
import lk.ijse.model.MaterialDetailDTO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialDetailBOImpl implements MaterialDetailBO {

    MaterialDetailDAO materialDetailDAO = (MaterialDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.MATERIAL_DETAIL);
    @Override
    public boolean saveDetails(MaterialDetailDTO materialDetailDTO) throws SQLException {
        return materialDetailDAO.save(new MaterialDetail(materialDetailDTO.getMaterialOrderId(),materialDetailDTO.getMaterialId(),materialDetailDTO.getSupplierId(),materialDetailDTO.getQty(),materialDetailDTO.getUnitPrice(),materialDetailDTO.getPayment(),materialDetailDTO.getDate()));
    }
    @Override
    public boolean updateDetails(MaterialDetailDTO materialDetailDTO) throws SQLException {
        return materialDetailDAO.update(new MaterialDetail(materialDetailDTO.getMaterialOrderId(),materialDetailDTO.getMaterialId(),materialDetailDTO.getSupplierId(),materialDetailDTO.getQty(),materialDetailDTO.getUnitPrice(),materialDetailDTO.getPayment(),materialDetailDTO.getDate()));
    }
    @Override
    public boolean deleteDetails(String materialOrderId) throws SQLException {
        return materialDetailDAO.delete(materialOrderId);
    }
    @Override
    public MaterialDetailDTO searchDetails(String materialOrderId) throws SQLException {
        MaterialDetail materialDetail = materialDetailDAO.search(materialOrderId);
        return new MaterialDetailDTO(materialDetail.getMaterialOrderId(),materialDetail.getMaterialId(),materialDetail.getSupplierId(),materialDetail.getQty(),materialDetail.getUnitPrice(),materialDetail.getPayment(),materialDetail.getDate());
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    @Override
    public List<MaterialDetailDTO> getAllDetails() throws SQLException {

       List<MaterialDetail> materialDetails = materialDetailDAO.getAll();

       List<MaterialDetailDTO> detailList = new ArrayList<>();

       for (MaterialDetail materialDetail : materialDetails){
           MaterialDetailDTO materialDetailDTO = new MaterialDetailDTO(materialDetail.getMaterialOrderId(),materialDetail.getMaterialId(),materialDetail.getSupplierId(),materialDetail.getQty(),materialDetail.getUnitPrice(),materialDetail.getPayment(),materialDetail.getDate());
           detailList.add(materialDetailDTO);
       }
       return detailList;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }

    @Override
    public String getCurrentMtdId() throws SQLException {
        return materialDetailDAO.getCurrentId();
    }
    @Override
    public String totalExpenses() throws SQLException {
        return materialDetailDAO.total();
    }
    @Override
    public Map<String, Double> getTotalMonthlyExpenses() throws SQLException {
        return materialDetailDAO.getTotalMonthly();
    }
    @Override
    public String getMonthFromDate(Date date) {
       return materialDetailDAO.getMonth(date);
    }
}
