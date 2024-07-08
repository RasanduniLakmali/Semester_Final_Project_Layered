package lk.ijse.bo;

import lk.ijse.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;

    public BOFactory(){

    }
    public static BOFactory getBoFactory(){
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case PRODUCT:
                return new ProductBOImpl();
            case PLACE_ORDER:
                return new PlaceOrderBOImpl();
            case PRODUCT_DETAIL:
                return new ProductDetailBOImpl();
            case MATERIAL:
                return new RawMaterialBOImpl();
            case MATERIAL_DETAIL:
                return new MaterialDetailBOImpl();
            case VEHICLE:
                return new VehicleBOImpl();
            case DELIVERY:
                return new DeliveryBOImpl();
            case ATTENDANCE:
                return new AttendanceBOImpl();
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }
}
