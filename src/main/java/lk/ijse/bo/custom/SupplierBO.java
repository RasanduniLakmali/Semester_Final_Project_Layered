package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.SQLUtil;
import lk.ijse.entity.Supplier;
import lk.ijse.model.SupplierDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SupplierBO extends SuperBO {

    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException;

    public  boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException;

    public  SupplierDTO searchSupplierById(String supplierId) throws SQLException;

    public  boolean deleteSupplier(String supplierId) throws SQLException;

    public List<SupplierDTO> getAllSuppliers() throws SQLException;

    public  List<String> getSupplierIds() throws SQLException;

    public  String getCurrentSupId() throws SQLException;

    public  SupplierDTO searchSupplierByContact(String contact) throws SQLException;

    public  int getSupplierCount() throws SQLException;
}
