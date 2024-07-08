package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.DAOTypes;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.User;
import lk.ijse.model.UserDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.USER);
    @Override
    public boolean updateUserPw(String pw,String userName) throws SQLException {
       return userDAO.updateUser(pw, userName);
    }

    @Override
    public boolean saveUser(UserDTO userDTO) throws SQLException {
       return userDAO.save(new User(userDTO.getUser_id(),userDTO.getUsername(),userDTO.getPassword(),userDTO.getEmail()));

    }

    @Override
    public boolean update(User dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public List<User> getAll() throws SQLException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }
    @Override
    public String getCurrentUserId() throws SQLException {

       return userDAO.getCurrentId();
    }

    @Override
    public User search(String id) throws SQLException {
        return null;
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }
    @Override
    public UserDTO checkData(String userName,String pw) throws SQLException{
        User user = userDAO.checkCredentials(userName,pw);
        return new UserDTO(user.getUser_id(),user.getUsername(),user.getPassword(),user.getEmail());
    }
    @Override
    public UserDTO  checkPasswordCredential(String tempUsername) throws SQLException{
        User user = userDAO.checkPassword(tempUsername);
        return new UserDTO(user.getUser_id(),user.getUsername(),user.getPassword(),user.getEmail());
    }
}
