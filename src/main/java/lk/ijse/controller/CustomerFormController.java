package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.BOTypes;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.model.CustomerDTO;
import lk.ijse.view.tdm.CustomerTm;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;

import java.sql.SQLException;
import java.util.List;



public class CustomerFormController {


    @FXML
    public AnchorPane customerPane;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtCustomerTel;

    @FXML
    private TextField txtEmailAddress;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    private Tooltip tooltip;

    private boolean typingComplete = false;

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOTypes.CUSTOMER);

    public void initialize(){
      setCellValueFactory();
      loadAllCustomers();
      getCurrentCustomerId();

      btnUpdate.setOnMouseExited(event -> {
            if (tooltip != null) {
                tooltip.hide();
            }
        });


    }
    private void initUI() {
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerTel.clear();
        txtEmailAddress.clear();
        txtCustomerId.setDisable(true);
        txtCustomerName.setDisable(true);
        txtCustomerAddress.setDisable(true);
        txtCustomerTel.setDisable(true);
        txtEmailAddress.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }
    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("contact_number"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadAllCustomers(){
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDTO> customerList = customerBO.getAllCustomers();
            for(CustomerDTO customerDTO : customerList){
                CustomerTm cusTm = new CustomerTm(
                        customerDTO.getCustomer_id(),
                        customerDTO.getCustomer_name(),
                        customerDTO.getAddress(),
                        customerDTO.getContact_number(),
                        customerDTO.getEmail()
                );
                obList.add(cusTm);
            }
            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
      clearFields();
    }

    private void clearFields() {
        txtCustomerId.setText("");
        txtCustomerName.setText("");
        txtCustomerAddress.setText("");
        txtCustomerTel.setText("");
        txtEmailAddress.setText("");
        getCurrentCustomerId();
    }

    private void getCurrentCustomerId(){
        try {
            String currentId = customerBO.getCurrentCusId();
            String nextId = generateNextCustomerId(currentId);
            txtCustomerId.setText(nextId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextCustomerId(String currentId) {
        if (currentId != null && currentId.matches("C\\d+")) {
            int idNum = Integer.parseInt(currentId.substring(1));
            return "C" + String.format("%03d", ++idNum);
        }
        return "C001";
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
       String customer_id = txtCustomerId.getText();

        try {
            boolean isDeleted = customerBO.deleteCustomer(customer_id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer deleted!").show();
                tblCustomer.refresh();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
      String customer_id = txtCustomerId.getText();
      String customer_name = txtCustomerName.getText();
      String address = txtCustomerAddress.getText();
      String contact_number = txtCustomerTel.getText();
      String email = txtEmailAddress.getText();


      try {
          if (isValid() && (isEmailValid()) && (isNameValid())){
                boolean isSaved = customerBO.saveCustomer(new CustomerDTO(customer_id,customer_name,address,contact_number,email));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer saved successfully!").show();
                    loadAllCustomers();
                    clearFields();
                }
          }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        CustomerTm selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
        selectedCustomer.setCustomer_id(customer_id);
        selectedCustomer.setCustomer_name(customer_name);
        selectedCustomer.setAddress(address);
        selectedCustomer.setContact_number(contact_number);
        selectedCustomer.setEmail(email);
        tblCustomer.refresh();
        initUI();
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String customer_id = txtCustomerId.getText();
        String customer_name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String contact_number = txtCustomerTel.getText();
        String email = txtEmailAddress.getText();

        CustomerDTO customer = new CustomerDTO(customer_id,customer_name,address,contact_number,email);
        try {
            if(isValid() && (isNameValid()) && (isEmailValid())) {
                boolean isUpdated = customerBO.updateCustomer(customer);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer updated successfully!").show();
                    tblCustomer.refresh();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String contact = txtCustomerTel.getText();

        CustomerDTO customer = customerBO.searchCustomerByContact(contact);
        if(customer != null){
            txtCustomerId.setText(customer.getCustomer_id());
            txtCustomerName.setText(customer.getCustomer_name());
            txtCustomerAddress.setText(customer.getAddress());
            txtCustomerTel.setText(customer.getContact_number());
            txtEmailAddress.setText(customer.getEmail());
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Customer is not found!").show();
        }
    }


    @FXML
    void btnUpdateOnMouseMoved(MouseEvent event) {
        if (tooltip == null) {
            tooltip = new Tooltip();
            tooltip.setAutoHide(true);
        }

        tooltip.setText("You can update your information using your contact number!");

        tooltip.show(btnUpdate, event.getScreenX() + 10, event.getScreenY() + 10);

    }

    @FXML
    void btnUpdateOnMouseExited(MouseEvent event) {
        if (tooltip != null) {
            tooltip.hide();
        }
    }

    public void txtCustomerIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.CustomerID,txtCustomerId);
    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.EMAIL,txtEmailAddress);
    }

    @FXML
    void txtContactOnKeyReleased(KeyEvent event) {
       Regex.setTextColor(TextFields.Contact,txtCustomerTel);
    }

    public  void txtCustomerNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.Name, txtCustomerName);
    }
    public boolean isNameValid(){
       if(!Regex.setTextColor(TextFields.Name,txtCustomerName));
       return true;
    }

    public boolean isEmailValid(){
        if(!Regex.setTextColor(TextFields.EMAIL,txtEmailAddress));
        return true;
    }
    public boolean isValid(){
        if (!Regex.setTextColor(TextFields.CustomerID,txtCustomerId)) return false;

        if (!Regex.setTextColor(TextFields.Contact,txtCustomerTel)) return false;
        return true;
    }

    @FXML
    void txtCustomerNameOnAction(ActionEvent event) {
        if(isNameValid()){
            new Alert(Alert.AlertType.INFORMATION,"Should have to consist two names!" +"\n"+
                    "ex:Amali Perera").show();
        }
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {
       if (isEmailValid()){
           new Alert(Alert.AlertType.INFORMATION,"Invalid email!Try again").show();
       }
    }
}
