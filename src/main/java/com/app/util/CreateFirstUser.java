package com.app.util;

import com.app.dao.UserDAO;
import com.app.dao.impl.UserDAOImpl;
import com.app.model.User;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Run this ONCE as a Java Application to create your first staff login.
 * jbcrypt hashes the password before it ever touches the database,
 * so the users table never stores plain text passwords.
 */
public class CreateFirstUser {

    public static void main(String[] args) {

        String plainPassword = "admin123";              // change this to whatever you want
        String hashed = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        User user = new User();
        user.setUsername("admin");
        user.setPassword(hashed);
        user.setFullName("Front Desk Admin");
        user.setRole("STAFF");

        UserDAO userDAO = new UserDAOImpl();
        boolean created = userDAO.createUser(user);

        if (created) {
            System.out.println("User created. Login with username: admin / password: " + plainPassword);
        } else {
            System.out.println("Failed to create user. Check DBConnection settings.");
        }
    }
}