package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Delivery;
import lk.ijse.model.DeliveryDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DeliveryBO extends SuperBO {


    public boolean saveDelivery(DeliveryDTO deliveryDTO) throws SQLException;

    public boolean updateDelivery(DeliveryDTO deliveryDTO) throws SQLException;

    public DeliveryDTO searchDelivery(String deliveryId) throws SQLException;

    public int getCount() throws SQLException;

    public boolean deleteDelivery(String deliveryId) throws SQLException;

    public List<DeliveryDTO> getAllDeliveries() throws SQLException;

    public List<String> getIds() throws SQLException;

    public String getCurrentDeliveryId() throws SQLException;
}
