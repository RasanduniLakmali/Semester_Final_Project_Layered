package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.BOTypes;
import lk.ijse.bo.custom.DeliveryBO;
import lk.ijse.bo.custom.VehicleBO;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.impl.DeliveryDAOImpl;
import lk.ijse.entity.Order;
import lk.ijse.model.DeliveryDTO;

import lk.ijse.model.VehicleDTO;
import lk.ijse.view.tdm.DeliveryTm;
import lk.ijse.dao.*;

import javafx.scene.input.KeyEvent;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class DeliveryFormController {

    private AnchorPane centerNode;

    public void setCenterNode(AnchorPane centerNode) {
        this.centerNode = centerNode;
    }

    @FXML
    private ComboBox<String> cmbOrderId;

    @FXML
    private ComboBox<String> cmbVehicleId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDeliveryId;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colVehicleId;

    @FXML
    private AnchorPane deliveryDetailPane;

    @FXML
    private TableView<DeliveryTm> tblDeliveryDetail;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDeiveryId;

    @FXML
    private TextField txtStatus;

    @FXML
    private Button btnUpdate;

    private Tooltip tooltip;

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.ORDER);
    VehicleBO vehicleBO = (VehicleBO) BOFactory.getBoFactory().getBO(BOTypes.VEHICLE);
    DeliveryBO deliveryBO = (DeliveryBO) BOFactory.getBoFactory().getBO(BOTypes.DELIVERY);

    public void initialize(){
        getOrderIds();
        getVehicleIds();
        setCellValueFactory();
        loadDeliveryDetails();
        getCurrentDeliveryId();

        btnUpdate.setOnMouseExited(event -> {
            if (tooltip != null) {
                tooltip.hide();
            }
        });
    }

    private void setCellValueFactory(){
        colDeliveryId.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadDeliveryDetails(){
        ObservableList<DeliveryTm> obList = FXCollections.observableArrayList();

        try {
            List<DeliveryDTO> deliveryDetailList = deliveryBO.getAllDeliveries();
            for (DeliveryDTO delivery : deliveryDetailList){
                DeliveryTm deliveryTm = new DeliveryTm(
                        delivery.getDeliveryId(),
                        delivery.getOrderId(),
                        delivery.getVehicleId(),
                        delivery.getDate(),
                        delivery.getStatus()
                );
                obList.add(deliveryTm);
            }
            tblDeliveryDetail.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCurrentDeliveryId(){
        try {
            String currentId = deliveryBO.getCurrentDeliveryId();
            String nextId = generateNextDeliveryId(currentId);
            txtDeiveryId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextDeliveryId(String currentId) {
        if (currentId != null && currentId.matches("D\\d+")) {
            int idNum = Integer.parseInt(currentId.substring(1));
            return "D" + String.format("%03d", ++idNum);
        }
        return "D001";
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
       clearFields();
    }

    private void clearFields() {
        txtDeiveryId.setText("");
        cmbOrderId.setValue("");
        cmbVehicleId.setValue("");
        txtDate.setText("");
        txtStatus.setText("");
        getCurrentDeliveryId();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String deliveryId = txtDeiveryId.getText();

        try {
            boolean isDeleted = deliveryBO.deleteDelivery(deliveryId);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Details deleted successfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
      String deliveryId = txtDeiveryId.getText();
      String orderId = cmbOrderId.getValue();
      String vehicleId = cmbVehicleId.getValue();
      Date date = Date.valueOf(txtDate.getText());
      String status = txtStatus.getText();

        DeliveryDTO deliveryDetail = new DeliveryDTO(deliveryId,orderId,vehicleId,date,status);
        try {
            if((deliveryId.isEmpty()) || (orderId.isEmpty()) || (vehicleId.isEmpty()) || (txtDate.getText().isEmpty()) || (status.isEmpty())){
                new Alert(Alert.AlertType.INFORMATION,"Empty Fields!Try again").show();
            }
            else if(isValid()){
                boolean isSaved = deliveryBO.saveDelivery(deliveryDetail);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved successfully!").show();
                    loadDeliveryDetails();
                    clearFields();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String deliveryId = txtDeiveryId.getText();
        String orderId = cmbOrderId.getValue();
        String vehicleId = cmbVehicleId.getValue();
        Date date = Date.valueOf(txtDate.getText());
        String status = txtStatus.getText();

        DeliveryDTO deliveryDetail = new DeliveryDTO(deliveryId,orderId,vehicleId,date,status);

        try {
            boolean isUpdated = deliveryBO.updateDelivery(deliveryDetail);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated successfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnMouseExited(MouseEvent event) {
        if (tooltip != null) {
            tooltip.hide();
        }
    }

    @FXML
    void btnUpdateOnMouseMoved(MouseEvent event) {
        if (tooltip == null) {
            tooltip = new Tooltip();
            tooltip.setAutoHide(true);
        }

        tooltip.setText("Update delivery details using Delivery Id!");

        tooltip.show(btnUpdate, event.getScreenX() + 10, event.getScreenY() + 10);
    }
    @FXML
    void cmbOrderOnAction(ActionEvent event) {
      String orderId = cmbOrderId.getValue();

        try {
            Order order =orderDAO.search(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void cmbVehicleOnAction(ActionEvent event) {
       String vehicleId = cmbVehicleId.getValue();

        try {
            VehicleDTO vehicle =vehicleBO.searchVehicle(vehicleId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getOrderIds(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> oIdList = orderDAO.getIds();
            for (String orderId : oIdList){
                obList.add(orderId);
            }
            cmbOrderId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getVehicleIds(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> vIdList = vehicleBO.getVehicleIds();
            for (String vehicleId : vIdList){
                obList.add(vehicleId);
            }
            cmbVehicleId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String deliveryId = txtDeiveryId.getText();

        DeliveryDTO deliveryDetail = deliveryBO.searchDelivery(deliveryId);
        if(deliveryDetail != null){
            txtDeiveryId.setText(deliveryDetail.getDeliveryId());
            cmbOrderId.setValue(deliveryDetail.getOrderId());
            cmbVehicleId.setValue(deliveryDetail.getVehicleId());
            txtDate.setText(String.valueOf(deliveryDetail.getDate()));
            txtStatus.setText(deliveryDetail.getStatus());
        }
        else{
            new Alert(Alert.AlertType.ERROR,"Material Order not found!").show();
        }
    }
    @FXML
    void btnVehicleDetailsOnAction(ActionEvent event) throws IOException {
        AnchorPane vehicleDetailPane = FXMLLoader.load(getClass().getResource("/view/vehicleDetail_form.fxml"));
        centerNode.getChildren().clear();
        centerNode.getChildren().add(vehicleDetailPane);
    }

    @FXML
    void txtDeliveryIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.DeliveryID,txtDeiveryId);
    }

    public boolean isValid(){
        if (!Regex.setTextColor(TextFields.DeliveryID,txtDeiveryId)) return false;
        return true;
    }
}

