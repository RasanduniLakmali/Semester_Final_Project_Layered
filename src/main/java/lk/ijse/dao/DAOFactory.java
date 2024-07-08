package lk.ijse.dao;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {

    private static  DAOFactory daoFactory;

    public DAOFactory(){

    }
    public static DAOFactory getDaoFactory(){
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }
    public CrudDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case PRODUCT:
                return new ProductDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();
            case PRODUCT_DETAIL:
               return new ProductDetailDAOImpl();
            case MATERIAL:
                return new RawMaterialDAOImpl();
            case MATERIAL_DETAIL:
                return new MaterialDetailDAOImpl();
            case VEHICLE:
                return new VehicleDAOImpl();
            case DELIVERY:
                return new DeliveryDAOImpl();
            case ATTENDANCE:
                return new AttendanceDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }
}
