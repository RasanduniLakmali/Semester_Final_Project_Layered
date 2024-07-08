package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.RawMaterialBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.DAOTypes;
import lk.ijse.dao.custom.RawMaterialDAO;
import lk.ijse.entity.RawMaterial;
import lk.ijse.model.RawMaterialDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawMaterialBOImpl implements RawMaterialBO {

    RawMaterialDAO rawMaterialDAO = (RawMaterialDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.MATERIAL);

    @Override
    public boolean saveMaterial(RawMaterialDTO rawMaterialDTO) throws SQLException {
        return rawMaterialDAO.save(new RawMaterial(rawMaterialDTO.getMaterialId(),rawMaterialDTO.getMaterialName(),rawMaterialDTO.getUnitPrice()));

    }
    @Override
    public boolean updateMaterial(RawMaterialDTO rawMaterialDTO) throws SQLException {
        return rawMaterialDAO.update(new RawMaterial(rawMaterialDTO.getMaterialId(),rawMaterialDTO.getMaterialName(),rawMaterialDTO.getUnitPrice()));
    }
    @Override
    public  RawMaterialDTO searchMaterial(String materialId) throws SQLException {
        RawMaterial rawMaterial = rawMaterialDAO.search(materialId);
        return new RawMaterialDTO(rawMaterial.getMaterialId(),rawMaterial.getMaterialName(),rawMaterial.getUnitPrice());
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    @Override
    public boolean deleteMaterial(String materialId) throws SQLException {
        return rawMaterialDAO.delete(materialId);
    }
    @Override
    public List<RawMaterialDTO> getAllMaterials() throws SQLException {
        List<RawMaterial> rawMaterials = rawMaterialDAO.getAll();
        List<RawMaterialDTO> mtList = new ArrayList<>();

        for (RawMaterial rawMaterial : rawMaterials){
            RawMaterialDTO rawMaterialDTO = new RawMaterialDTO(rawMaterial.getMaterialId(),rawMaterial.getMaterialName(),rawMaterial.getUnitPrice());
            mtList.add(rawMaterialDTO);
        }
        return mtList;
    }
    @Override
    public List<String> getMaterialIds() throws SQLException {

      return rawMaterialDAO.getIds();
    }
    @Override
    public String getCurrentMtId() throws SQLException {

        return rawMaterialDAO.getCurrentId();
    }
}
