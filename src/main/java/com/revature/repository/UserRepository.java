package com.revature.repository;

import com.revature.model.User;

import java.sql.*;
public class UserRepository {


    //Register
    public User addUser(User user) throws SQLException {

        try (Connection connectionObject = ConnectionFactory.createConnection()) {
            String sql = "insert into users (username, password, role_id) values (?, ?, ?)";

            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, 1);

            int numberOfRecordsAdded = pstmt.executeUpdate(); // returns an int

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            return new User(id, user.getUsername(), user.getPassword(), 1);
        }
    }

    public User getUserByUsername(String username) throws SQLException{
        try (Connection connectionObj = ConnectionFactory.createConnection()) {
            String sql = "SELECT * FROM users as u WHERE u.username = ?";
            PreparedStatement pstmt = connectionObj.prepareStatement(sql);

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery(); // ResultSet represents a temporary table that contains all data that we have
            // queried for

            if (rs.next()) { // returns a boolean indicating whether there is a record or not for the "next" row AND iterates to the next row
                int id = rs.getInt("id");
                String un = rs.getString("username");
                String pw = rs.getString("password");
                int role = rs.getInt("role_id");

                return new User(id, un, pw, 1);
            } else {
                return null;
            }

        }
    }

    //login
    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        try (Connection connectionObj = ConnectionFactory.createConnection()) {
            String sql = "SELECT * FROM users as u WHERE u.username = ? AND u.password = ?";
            PreparedStatement pstmt = connectionObj.prepareStatement(sql);

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
               int id = rs.getInt("id");
               String un = rs.getString("username");
               String pw = rs.getString("password");
               int roleId = rs.getInt("role_id");

               return new User(id, un, pw, roleId);
            } else {
                return null;
            }
        }
    }
}
