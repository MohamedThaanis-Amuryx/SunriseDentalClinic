package com.app.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Runs before every request to a protected page.
 * If there's no logged-in user in the session, it redirects to /login
 * instead of letting the page load. This is what enforces
 * "only authorized staff can use the system" from the brief.
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    // Pages that do NOT require login (otherwise no one could even reach the login page)
    private static final String[] PUBLIC_PAGES = {
            "/login",
            "/login.jsp",
            "/resources/",   // css, js, images
            "/index.jsp"
    };

    @Override
    public void init(FilterConfig filterConfig) {
        // nothing needed here
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        if (isPublicPage(path)) {
            chain.doFilter(request, response);   // let it through, no login needed
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("loggedInUser") != null);

        if (loggedIn) {
            chain.doFilter(request, response);   // continue to the page they asked for
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }

    private boolean isPublicPage(String path) {
        for (String publicPath : PUBLIC_PAGES) {
            if (path.startsWith(publicPath)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
        // nothing needed here
    }
}