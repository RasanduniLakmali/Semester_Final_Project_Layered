package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.db.DBConnection;
import lk.ijse.entity.User;
import lk.ijse.model.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean updateUser(String pw,String userName) throws SQLException {
       return SQLUtil.execute("UPDATE Users SET password = ? WHERE username = ?",pw,userName);
    }
    @Override
    public boolean save(User user) throws SQLException {
        return SQLUtil.execute("INSERT INTO Users VALUES(?,?,?,?) ",user.getUser_id(),user.getUsername(),user.getPassword(),user.getEmail());

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
    public String getCurrentId() throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT user_id FROM Users ORDER BY user_id DESC LIMIT 1");
        resultSet.next();
        return resultSet.getString("user_id");
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
    public User checkCredentials(String userName,String pw) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT username,password FROM Users WHERE username = ?",userName);
        rst.next();
        return new User(rst.getString("username"),rst.getString("password"));
    }
    @Override
    public User checkPassword(String tempUsername) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Users WHERE username = ?",tempUsername);
        rst.next();
        return new User(rst.getString("user_id"),rst.getString("username"),rst.getString("password"),rst.getString("email"));
    }

}
