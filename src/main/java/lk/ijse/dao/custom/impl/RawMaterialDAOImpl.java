package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.RawMaterialDAO;
import lk.ijse.entity.RawMaterial;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawMaterialDAOImpl implements RawMaterialDAO {

    @Override
    public boolean save(RawMaterial rawMaterial) throws SQLException {
       return SQLUtil.execute("INSERT INTO Raw_Material VALUES (?,?,?)",rawMaterial.getMaterialId(),rawMaterial.getMaterialName(),rawMaterial.getUnitPrice());

    }
    @Override
    public boolean update(RawMaterial rawMaterial) throws SQLException {
       return SQLUtil.execute("UPDATE Raw_Material SET material_name = ?, unit_price = ? WHERE material_id = ?",rawMaterial.getMaterialName(),rawMaterial.getUnitPrice(),rawMaterial.getMaterialId());
    }
    @Override
    public  RawMaterial search(String materialId) throws SQLException {
       ResultSet rst = SQLUtil.execute("SELECT * FROM Raw_Material WHERE material_id = ?",materialId);
       rst.next();
       return new RawMaterial(rst.getString("material_id"),rst.getString("material_name"),rst.getDouble("unit_price"));
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    @Override
    public boolean delete(String materialId) throws SQLException {
       return SQLUtil.execute("DELETE FROM Raw_Material WHERE material_id = ?",materialId);
    }
    @Override
    public List<RawMaterial> getAll() throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Raw_Material");

        List<RawMaterial> mtList = new ArrayList<>();

        while (rst.next()){
            RawMaterial rawMaterial = new RawMaterial(
                    rst.getString("material_id"),
                    rst.getString("material_name"),
                    rst.getDouble("unit_price")
            );
            mtList.add(rawMaterial);
        }
        return mtList;
    }
    @Override
    public List<String> getIds() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT material_id FROM Raw_Material");

        List<String> mtIDList = new ArrayList<>();
        while (resultSet.next()){
            mtIDList.add(resultSet.getString(1));
        }
        return mtIDList;
    }
    @Override
    public String getCurrentId() throws SQLException {

        ResultSet rst = SQLUtil.execute("SELECT material_id FROM Raw_Material ORDER BY material_id DESC LIMIT 1");

        if (rst.next()){
            String materialId = rst.getString(1);
            return materialId;
        }
        return null;
    }
}
