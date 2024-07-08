package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.User;
import lk.ijse.model.UserDTO;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {

    public boolean updateUser(String pw,String userName) throws SQLException;

    public User checkCredentials(String userName, String pw) throws SQLException;
    public User checkPassword(String tempUsername) throws SQLException;
}
