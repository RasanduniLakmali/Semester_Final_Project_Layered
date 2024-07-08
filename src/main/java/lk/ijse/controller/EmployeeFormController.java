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
import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.model.EmployeeDTO;
import lk.ijse.view.tdm.EmployeeTm;

import javafx.scene.input.KeyEvent;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;



public class EmployeeFormController {

    private AnchorPane centerNode;

    public void setCenterNode(AnchorPane centerNode) {
        this.centerNode = centerNode;
    }
    @FXML
    public AnchorPane employeePane;

    @FXML
    private TextField txtEmployeeAddress;

    @FXML
    private TextField txtEmployeeNIC;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtEmployeeSection;

    @FXML
    private TextField txtEmployeeSalary;

    @FXML
    private TextField txtTelNo;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colSection;

    @FXML
    private TableColumn<?, ?> colTelNo;

    private Tooltip tooltip;

    @FXML
    private Button btnUpdate;

   EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOTypes.EMPLOYEE);

    public void initialize(){
        setCellValueFactory();
        loadAllEmployees();
        getCurrentEmployeeId();

        btnUpdate.setOnMouseExited(event -> {
            if (tooltip != null) {
                tooltip.hide();
            }
        });
    }

    private void getCurrentEmployeeId(){
        try {
            String currentId = employeeBO.getCurrentEmpId();
            String nextId = generateNextCustomerId(currentId);
            txtEmployeeId.setText(nextId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextCustomerId(String currentId) {
        if (currentId != null && currentId.matches("E\\d+")) {
            int idNum = Integer.parseInt(currentId.substring(1));
            return "E" + String.format("%03d", ++idNum);
        }
        return "E001";
    }

    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTelNo.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));
    }

    private void loadAllEmployees(){
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDTO> employeeList = employeeBO.getAllEmployees();
            for (EmployeeDTO employeeDTO : employeeList) {
                EmployeeTm empTm = new EmployeeTm(
                        employeeDTO.getEmployeeId(),
                        employeeDTO.getEmployeeName(),
                        employeeDTO.getNIC(),
                        employeeDTO.getAddress(),
                        employeeDTO.getContact(),
                        employeeDTO.getSalary(),
                        employeeDTO.getSection()

                );
                obList.add(empTm);
            }

                tblEmployee.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
       clearFields();
    }

    private void clearFields() {
        txtEmployeeId.setText("");
        txtEmployeeName.setText("");
        txtEmployeeNIC.setText("");
        txtEmployeeAddress.setText("");
        txtTelNo.setText("");
        txtEmployeeSalary.setText("");
        txtEmployeeSection.setText("");
        getCurrentEmployeeId();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
       String employeeId = txtEmployeeId.getText();

        try {
            boolean isDeleted = employeeBO.deleteEmployee(employeeId);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee deleted successfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws NumberFormatException{
       String employeeId = txtEmployeeId.getText();
       String employeeName = txtEmployeeName.getText();
       String NIC = txtEmployeeNIC.getText();
       String address = txtEmployeeAddress.getText();
       String contact = txtTelNo.getText();
       double salary = Double.parseDouble(txtEmployeeSalary.getText());
       String section = txtEmployeeSection.getText();

        EmployeeDTO employee = new EmployeeDTO(employeeId,employeeName,NIC,address,contact,salary,section);

        try {
            if((employeeId.isEmpty()) || (employeeName.isEmpty()) || (NIC.isEmpty()) || (address.isEmpty()) || (contact.isEmpty()) || (txtEmployeeSalary.getText().isEmpty()) || (section.isEmpty())){
                new Alert(Alert.AlertType.INFORMATION,"Empty Fields!Try again").show();
            }
            else if(isValid() && (isNameValid()) && (isContactValid())){
                boolean isSaved = employeeBO.saveEmployee(employee);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee saved successfully!").show();
                    loadAllEmployees();
                    clearFields();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String employeeId = txtEmployeeId.getText();
        String employeeName = txtEmployeeName.getText();
        String NIC = txtEmployeeNIC.getText();
        String address = txtEmployeeAddress.getText();
        String contact = txtTelNo.getText();
        double salary = Double.parseDouble(txtEmployeeSalary.getText());
        String section = txtEmployeeSection.getText();

        EmployeeDTO employeeDTO = new EmployeeDTO(employeeId,employeeName,NIC,address,contact,salary,section);

        try {
            if (isValid() && (isNameValid()) && (isContactValid())) {
                boolean isUpdated = employeeBO.updateEmployee(employeeDTO);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee updated successfully!").show();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
         String NIC = txtEmployeeNIC.getText();

         EmployeeDTO employeeDTO = employeeBO.searchEmployeeByNIC(NIC);
         if(employeeDTO != null){
             txtEmployeeId.setText(employeeDTO.getEmployeeId());
             txtEmployeeName.setText(employeeDTO.getEmployeeName());
             txtEmployeeNIC.setText(employeeDTO.getNIC());
             txtEmployeeAddress.setText(employeeDTO.getAddress());
             txtTelNo.setText(employeeDTO.getContact());
             txtEmployeeSalary.setText(String.valueOf(employeeDTO.getSalary()));
             txtEmployeeSection.setText(employeeDTO.getSection());

         }
         else{
             new Alert(Alert.AlertType.ERROR,"Employee not found!").show();
         }
    }

    @FXML
    void btnUpdateOnMouseMoved(MouseEvent event) {
        if (tooltip == null) {
            tooltip = new Tooltip();
            tooltip.setAutoHide(true);
        }


        tooltip.setText("You can update your information using your NIC number!");


        tooltip.show(btnUpdate, event.getScreenX() + 10, event.getScreenY() + 10);
    }

    @FXML
    void btnUpdateOnMouseExited(MouseEvent event) {
        if (tooltip != null) {
            tooltip.hide();
        }
    }

    @FXML
    void btnAttendanceOnAction(ActionEvent event) throws IOException {
        AnchorPane attendancePane = FXMLLoader.load(getClass().getResource("/view/attendanceDetails_form.fxml"));
        centerNode.getChildren().clear();
        centerNode.getChildren().add(attendancePane);
    }

    @FXML
    void txtEmployeeIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.EmployeeID,txtEmployeeId);
    }

    @FXML
    void txtSalaryOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.DOUBLE,txtEmployeeSalary);
    }

    @FXML
    void txtContactOnKeyReleased(KeyEvent event) {
       Regex.setTextColor(TextFields.Contact,txtTelNo);
    }

    @FXML
    void txtEmpNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.Name,txtEmployeeName);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(TextFields.EmployeeID,txtEmployeeId)) return false;
        if(!Regex.setTextColor(TextFields.DOUBLE,txtEmployeeSalary))return false;
        return true;
    }

    public boolean isNameValid(){
        if(!Regex.setTextColor(TextFields.Name,txtEmployeeName));
        return true;
    }

    public boolean isContactValid(){
        if(!Regex.setTextColor(TextFields.Contact,txtTelNo));
        return true;
    }
    @FXML
    void txtEmpNameOnAction(ActionEvent event) {
       if(isNameValid()){
           new Alert(Alert.AlertType.INFORMATION,"Should have to consist two names!" +"\n"+
                   "ex:Amali Perera").show();
       }
    }

    @FXML
    void txtContactOnAction(ActionEvent event) {
       if (isContactValid()){
           new Alert(Alert.AlertType.INFORMATION,"Invalid contact number!").show();
       }
    }
}

