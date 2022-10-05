package com.revature.service;

import com.revature.exception.InvalidLoginException;
import com.revature.exception.InvalidRegisterException;
import com.revature.exception.UsernameAlreadyExistsException;
import com.revature.model.User;
import com.revature.repository.UserRepository;

import java.sql.SQLException;

public class AuthService {

    private UserRepository userRepo = new UserRepository();

    public User login(String username, String password) throws SQLException, InvalidLoginException {
        User user = userRepo.getUserByUsernameAndPassword(username, password);


        if (user == null) {
            throw new InvalidLoginException("Invalid login and/or password");
        }
        return user;
    }


    public User register(User user) throws UsernameAlreadyExistsException, SQLException {
        if (userRepo.getUserByUsername(user.getUsername()) != null) {
            throw new UsernameAlreadyExistsException("User with username " + user.getUsername() + " already exists!");
        }

        User addedUser = userRepo.addUser(user);

        return addedUser;
    }

}
