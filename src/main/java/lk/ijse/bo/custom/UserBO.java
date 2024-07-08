package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.entity.User;
import lk.ijse.model.UserDTO;

import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {


    public boolean updateUserPw(String pw,String userName) throws SQLException;

    public boolean saveUser(UserDTO userDTO) throws SQLException;

    public boolean update(User dto) throws SQLException;

    public boolean delete(String id) throws SQLException;

    public List<User> getAll() throws SQLException;


    public List<String> getIds() throws SQLException;

    public String getCurrentUserId() throws SQLException;


    public User search(String id) throws SQLException;


    public int getCount() throws SQLException;

    public UserDTO checkData(String userName, String pw) throws SQLException;

    public UserDTO  checkPasswordCredential(String tempUsername) throws SQLException;
}
