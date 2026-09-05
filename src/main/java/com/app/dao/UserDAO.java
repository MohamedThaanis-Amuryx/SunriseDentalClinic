package com.app.dao;

import com.app.model.User;

/**
 * Defines what operations are available for User data.
 * The Servlet/Service layer talks to this interface, not the SQL directly.
 * This is the DAO design pattern.
 */
public interface UserDAO {

    User findByUsername(String username);

    boolean createUser(User user);
}