package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.DeliveryBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.DAOTypes;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.DeliveryDAO;
import lk.ijse.entity.Delivery;
import lk.ijse.model.DeliveryDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryBOImpl implements DeliveryBO {

    DeliveryDAO deliveryDAO = (DeliveryDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.DELIVERY);

    @Override
    public boolean saveDelivery(DeliveryDTO deliveryDTO) throws SQLException {
       return deliveryDAO.save(new Delivery(deliveryDTO.getDeliveryId(),deliveryDTO.getOrderId(),deliveryDTO.getVehicleId(),deliveryDTO.getDate(),deliveryDTO.getStatus()));

    }
    @Override
    public boolean updateDelivery(DeliveryDTO deliveryDTO) throws SQLException {
       return deliveryDAO.update(new Delivery(deliveryDTO.getDeliveryId(),deliveryDTO.getOrderId(),deliveryDTO.getVehicleId(),deliveryDTO.getDate(),deliveryDTO.getStatus()));
    }
    @Override
    public DeliveryDTO searchDelivery(String deliveryId) throws SQLException {
       Delivery delivery = deliveryDAO.search(deliveryId);
       return new DeliveryDTO(delivery.getDeliveryId(),delivery.getOrderId(),delivery.getVehicleId(),delivery.getDate(),delivery.getStatus());
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    @Override
    public boolean deleteDelivery(String deliveryId) throws SQLException {
        return deliveryDAO.delete(deliveryId);
    }
    @Override
    public List<DeliveryDTO> getAllDeliveries() throws SQLException {
         List<Delivery> deliveries = deliveryDAO.getAll();
         List<DeliveryDTO> deliveryList = new ArrayList<>();

         for (Delivery delivery : deliveries){
             DeliveryDTO deliveryDTO = new DeliveryDTO(delivery.getDeliveryId(),delivery.getOrderId(),delivery.getVehicleId(),delivery.getDate(),delivery.getStatus());
             deliveryList.add(deliveryDTO);
         }
        return deliveryList;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }

    @Override
    public String getCurrentDeliveryId() throws SQLException {
        return deliveryDAO.getCurrentId();
    }
}
