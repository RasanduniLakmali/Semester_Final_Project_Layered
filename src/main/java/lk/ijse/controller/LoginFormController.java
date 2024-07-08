package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.BOTypes;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.db.DBConnection;
import lk.ijse.model.UserDTO;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    private AnchorPane centerNode;

    public void setCenterNode(AnchorPane centerNode) {
        this.centerNode = centerNode;
    }
    public Text txtForgotPassword;

    @FXML
    private PasswordField txtPassword;

    public TextField txtUsername;

    public AnchorPane rootNode;

    public static String tempUsername;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOTypes.USER);

    public void btnLoginOnAction(ActionEvent event) throws IOException {
      String userName = txtUsername.getText();
      String pw = txtPassword.getText();

        try {
            checkCredentials(userName,pw);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }


    public void checkCredentials(String userName, String pw) throws SQLException, IOException {
        if(userName.isEmpty() && pw.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION,"Empty fields! Try again").show();
            return;
        }

        if(userName.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION,"Username is empty! Try again").show();
            return;
        }

        if(pw.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION,"Password is empty! Try again").show();
            return;
        }

        UserDTO userDTO = userBO.checkData(userName, pw);

        if (userDTO == null) {
            new Alert(Alert.AlertType.INFORMATION,"Sorry! Username can't be found").show();
            return;
        }

        if (!userDTO.getPassword().equals(pw)) {
            new Alert(Alert.AlertType.ERROR,"Sorry! Password is incorrect").show();
            return;
        }

        navigateToTheDashboard();
    }


    public  void navigateToTheDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage =(Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);

        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void hyperlinkSignUpOnAction(ActionEvent event) throws IOException {
         AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/register_form.fxml"));
         Scene scene = new Scene(rootNode);
         Stage stage = (Stage) this.rootNode.getScene().getWindow();
         stage.setScene(scene);

         stage.setTitle("Register Form");
         stage.centerOnScreen();
    }

    public void txtForgotPasswordOnAction(MouseEvent event) throws IOException {
        tempUsername = txtUsername.getText();
        String pw = txtPassword.getText();

        try {
            checkPasswordCredentials(tempUsername);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    public void checkPasswordCredentials(String tempUsername) throws SQLException, IOException {
        UserDTO userDTO = userBO.checkPasswordCredential(tempUsername);

        if(tempUsername.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Empty Fields!Enter username").show();
        }

        else if (userDTO.getUsername().equals(tempUsername)) {
                    AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/forgotPassword_form.fxml"));
                    Scene scene = new Scene(rootNode);
                    Stage stage = (Stage) txtForgotPassword.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Reset Password");
                    stage.centerOnScreen();


        }
        else{
            new Alert(Alert.AlertType.ERROR, "Sorry!Invalid username").show();
        }
    }
    @FXML
    void txtPasswordOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.Password,txtPassword);
    }

    @FXML
    void txtUsernameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.UserName,txtUsername);
    }

    public boolean isUsernameValid(){
        if(!Regex.setTextColor(TextFields.UserName,txtUsername));
        return true;
    }

    public boolean isPasswordValid(){
        if(!Regex.setTextColor(TextFields.Password,txtPassword));
        return true;
    }
    @FXML
    void txtUsernameOnAction(ActionEvent event) {
      if(isUsernameValid()){
          new Alert(Alert.AlertType.INFORMATION,"Invalid username!").show();
      }
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
       if (isPasswordValid()){
           new Alert(Alert.AlertType.INFORMATION,"Invalid Password!").show();
       }
    }
}
