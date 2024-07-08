package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.BOTypes;
import lk.ijse.bo.custom.ProductBO;
import lk.ijse.bo.custom.ProductDetailBO;
import lk.ijse.bo.custom.RawMaterialBO;
import lk.ijse.model.ProductDetailDTO;
import lk.ijse.dao.custom.impl.RawMaterialDAOImpl;
import lk.ijse.view.tdm.ProductDetailTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProductDetailFormController {

    @FXML
    private ComboBox<String> cmbMaterialId;

    @FXML
    private ComboBox<String> cmbProductId;

    @FXML
    private TableColumn<?, ?> colMaterialId;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private AnchorPane productDetailPane;

    @FXML
    private TableView<ProductDetailTm> tblProductDetail;

    ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOTypes.PRODUCT);
    ProductDetailBO productDetailBO = (ProductDetailBO) BOFactory.getBoFactory().getBO(BOTypes.PRODUCT_DETAIL);
    RawMaterialBO rawMaterialBO = (RawMaterialBO) BOFactory.getBoFactory().getBO(BOTypes.MATERIAL);

    public void initialize(){
        setCellValueFactory();
        loadAllDetails();
        getProductIds();
        getMaterialIds();
    }

    private void getProductIds(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> prIdList = productBO.getProductIds();
            for(String productId : prIdList){
                obList.add(productId);
            }
            cmbProductId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getMaterialIds(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> mtIdList = rawMaterialBO.getMaterialIds();
            for(String materialId : mtIdList){
                obList.add(materialId);
            }
            cmbMaterialId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
       clearFields();
    }

    private void clearFields() {
        cmbProductId.setValue("");
        cmbMaterialId.setValue("");
    }

    private void setCellValueFactory(){
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colMaterialId.setCellValueFactory(new PropertyValueFactory<>("materialId"));
    }

    private void loadAllDetails(){
        ObservableList<ProductDetailTm> obList = FXCollections.observableArrayList();

        try {
            List<ProductDetailDTO> productDetailList = productDetailBO.getAllDetails();
            for (ProductDetailDTO productDetail : productDetailList){
                ProductDetailTm detailTm = new ProductDetailTm(
                       productDetail.getProductId(),
                        productDetail.getMaterialId()
                );
                obList.add(detailTm);
            }
            tblProductDetail.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
       String productId = cmbProductId.getValue();

        try {
            boolean isDeleted = productDetailBO.deleteDetails(productId);
            if(isDeleted){
                new  Alert(Alert.AlertType.CONFIRMATION,"Details deleted successfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String productId = cmbProductId.getValue();
        String materialId = cmbMaterialId.getValue();

        ProductDetailDTO productDetail = new ProductDetailDTO(productId,materialId);

        try {
            if((productId.isEmpty()) || (materialId.isEmpty()) ){
                new Alert(Alert.AlertType.INFORMATION,"Empty Fields!Try again").show();
            }
            else {
                boolean isSaved = productDetailBO.saveDetails(productDetail);
                if(isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION,"Details saved successfully!").show();
                    loadAllDetails();
                    clearFields();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String productId = cmbProductId.getValue();
        String materialId = cmbMaterialId.getValue();

        ProductDetailDTO productDetail = new ProductDetailDTO(productId,materialId);

        try {
            boolean isUpdated = productDetailBO.updateDetails(productDetail);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Details updated successfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void cmbProductOnAction(ActionEvent event) {
        String productId = cmbProductId.getValue();

        try {
            ProductDetailDTO productDetail = productDetailBO.searchDetailsById(productId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane productDetailPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(productDetailPane);
        Stage stage =(Stage) this.productDetailPane.getScene().getWindow();
        stage.setScene(scene);

        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }


}

