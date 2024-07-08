package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.RawMaterial;
import lk.ijse.model.RawMaterialDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface RawMaterialBO extends SuperBO {


    public boolean saveMaterial(RawMaterialDTO rawMaterialDTO) throws SQLException;

    public boolean updateMaterial(RawMaterialDTO rawMaterialDTO) throws SQLException;

    public  RawMaterialDTO searchMaterial(String materialId) throws SQLException;

    public int getCount() throws SQLException;

    public boolean deleteMaterial(String materialId) throws SQLException;

    public List<RawMaterialDTO> getAllMaterials() throws SQLException;

    public List<String> getMaterialIds() throws SQLException;

    public String getCurrentMtId() throws SQLException;
}
