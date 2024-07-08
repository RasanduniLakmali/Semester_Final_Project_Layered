package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.BOTypes;
import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.bo.custom.VehicleBO;
import lk.ijse.model.EmployeeDTO;
import lk.ijse.model.VehicleDTO;
import lk.ijse.view.tdm.VehicleTm;
import lk.ijse.dao.custom.impl.VehicleDAOImpl;

import javafx.scene.input.KeyEvent;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;

import java.sql.SQLException;
import java.util.List;

public class VehicleDetailFormController {

    @FXML
    public AnchorPane vehicleDetailPane;

    @FXML
    private ComboBox<String> cmbEmployeeId;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colModel;

    @FXML
    private TableColumn<?, ?> colVehicleId;

    @FXML
    private TableColumn<?, ?> colVehicleNumber;

    @FXML
    private TableView<VehicleTm> tblVehicle;

    @FXML
    private TextField txtVehicleId;

    @FXML
    private TextField txtVehicleModel;

    @FXML
    private TextField txtVehicleNumber;

    @FXML
    private Button btnUpdate;

    private Tooltip tooltip;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOTypes.EMPLOYEE);
    VehicleBO vehicleBO = (VehicleBO) BOFactory.getBoFactory().getBO(BOTypes.VEHICLE);

    public void initialize() {
        getEmployeeIds();
        setCellValueFactory();
        loadAllVehicles();
        getCurrentVehicleId();


        btnUpdate.setOnMouseExited(event -> {
            if (tooltip != null) {
                tooltip.hide();
            }
        });
    }

    private void setCellValueFactory() {
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
    }

    private void loadAllVehicles() {
        ObservableList<VehicleTm> obList = FXCollections.observableArrayList();

        try {
            List<VehicleDTO> vehicleList = vehicleBO.getAllVehicles();
            for (VehicleDTO vehicle : vehicleList) {
                VehicleTm tm = new VehicleTm(
                        vehicle.getVehicleId(),
                        vehicle.getEmployeeId(),
                        vehicle.getVehicleNumber(),
                        vehicle.getModel()
                );
                obList.add(tm);
            }
            tblVehicle.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCurrentVehicleId() {
        try {
            String currentId = vehicleBO.getCurrentVehicleId();
            String nextId = generateNextVehicleId(currentId);
            txtVehicleId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextVehicleId(String currentId) {
        if (currentId != null && currentId.matches("V\\d+")) {
            int idNum = Integer.parseInt(currentId.substring(1));
            return "V" + String.format("%03d", ++idNum);
        }
        return "V001";
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtVehicleId.setText("");
        cmbEmployeeId.setValue("");
        txtVehicleNumber.setText("");
        txtVehicleModel.setText("");
        getCurrentVehicleId();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String vehicleId = txtVehicleId.getText();

        try {
            boolean isDeleted = vehicleBO.deleteVehicle(vehicleId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Vehicle deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String vehicleId = txtVehicleId.getText();
        String employeeId = cmbEmployeeId.getValue();
        String vehicleNumber = txtVehicleNumber.getText();
        String model = txtVehicleModel.getText();

        VehicleDTO vehicle = new VehicleDTO(vehicleId, employeeId, vehicleNumber, model);
        try {
            if ((vehicleId.isEmpty()) || (employeeId.isEmpty()) || (vehicleNumber.isEmpty()) || (model.isEmpty())) {
                new Alert(Alert.AlertType.INFORMATION, "Empty Files!Try again").show();
            } else if (isValid()) {

                boolean isSaved = vehicleBO.saveVehicle(vehicle);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Vehicle saved successfully!").show();
                    loadAllVehicles();
                    clearFields();

                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String vehicleId = txtVehicleId.getText();
        String employeeId = cmbEmployeeId.getValue();
        String vehicleNumber = txtVehicleNumber.getText();
        String model = txtVehicleModel.getText();

        VehicleDTO vehicle = new VehicleDTO(vehicleId, employeeId, vehicleNumber, model);
        try {
            if (isValid()) {
                boolean isUpdated = vehicleBO.updateVehicle(vehicle);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Vehicle updated successfully!").show();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
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

        tooltip.setText("You can update your information using your vehicle number!");

        tooltip.show(btnUpdate, event.getScreenX() + 10, event.getScreenY() + 10);
    }

    @FXML
    void cmbEmployeeOnAction(ActionEvent event) {
        String employeeId = cmbEmployeeId.getValue();

        try {

            EmployeeDTO employee = employeeBO.searchEmployeeById(employeeId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getEmployeeIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> empIdList = employeeBO.getEmployeeIds();
            for (String employee : empIdList) {
                obList.add(employee);
            }
            cmbEmployeeId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String vehicleNumber = txtVehicleNumber.getText();

        VehicleDTO vehicle = vehicleBO.searchVehicleByNumber(vehicleNumber);
        if (vehicle != null) {
            txtVehicleId.setText(vehicle.getVehicleId());
            cmbEmployeeId.setValue(vehicle.getEmployeeId());
            txtVehicleNumber.setText(vehicle.getVehicleNumber());
            txtVehicleModel.setText(vehicle.getModel());
        } else {
            new Alert(Alert.AlertType.ERROR, "Vehicle is not found!").show();
        }
    }

    @FXML
    void txtVehicleIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.VehicleID, txtVehicleId);
    }

    public boolean isValid() {
        if (!Regex.setTextColor(TextFields.VehicleID, txtVehicleId)) return false;
        return true;
    }
}

