package com.app.controller;

import com.app.dao.UserDAO;
import com.app.dao.impl.UserDAOImpl;
import com.app.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAOImpl();

    // Show the login page when someone visits /login directly (GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    // Handle the actual login form submission (POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = com.app.util.ValidationUtil.sanitize(request.getParameter("username"));
        String password = request.getParameter("password");   // not sanitized, bcrypt handles raw comparison safely

        if (com.app.util.ValidationUtil.isBlank(username) || com.app.util.ValidationUtil.isBlank(password)) {
            request.setAttribute("error", "Username and password are both required.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        User user = userDAO.findByUsername(username);

        // BCrypt.checkpw compares the plain password typed in against the stored hash.
        // We never decrypt the hash, we just check if it matches.
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {

            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", user);
            session.setMaxInactiveInterval(30 * 60); // 30 minutes

            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            request.setAttribute("error", "Invalid username or password");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
