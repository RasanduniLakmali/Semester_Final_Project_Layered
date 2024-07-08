package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.DAOTypes;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.entity.Supplier;
import lk.ijse.model.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.SUPPLIER);
    @Override
    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException {
       return supplierDAO.save(new Supplier(supplierDTO.getSupplierId(),supplierDTO.getSupplierName(),supplierDTO.getAddress(),supplierDTO.getContact()));
    }
    @Override
    public  boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException {
        return supplierDAO.update(new Supplier(supplierDTO.getSupplierId(),supplierDTO.getSupplierName(),supplierDTO.getAddress(),supplierDTO.getContact()));
    }
    @Override
    public  SupplierDTO searchSupplierById(String supplierId) throws SQLException {
        Supplier supplier = supplierDAO.search(supplierId);
        return new SupplierDTO(supplier.getSupplierId(),supplier.getSupplierName(),supplier.getAddress(),supplier.getContact());
    }
    @Override
    public  boolean deleteSupplier(String supplierId) throws SQLException {
       return supplierDAO.delete(supplierId);
    }
    @Override
    public List<SupplierDTO> getAllSuppliers() throws SQLException {

        List<Supplier> suppliers = supplierDAO.getAll();
        List<SupplierDTO> supList = new ArrayList<>();

        for (Supplier supplier : suppliers){
            SupplierDTO supplierDTO = new SupplierDTO(supplier.getSupplierId(),supplier.getSupplierName(),supplier.getAddress(),supplier.getContact());
            supList.add(supplierDTO);
        }
        return supList;
    }
    @Override
    public  List<String> getSupplierIds() throws SQLException {

        return supplierDAO.getIds();
    }
    @Override
    public  String getCurrentSupId() throws SQLException {

        return supplierDAO.getCurrentId();
    }
    @Override
    public  SupplierDTO searchSupplierByContact(String contact) throws SQLException {
       Supplier supplier = supplierDAO.searchSupplierByContact(contact);
       return new SupplierDTO(supplier.getSupplierId(),supplier.getSupplierName(),supplier.getAddress(),supplier.getContact());
    }
    @Override
    public  int getSupplierCount() throws SQLException {
       return supplierDAO.getCount();
    }
}
