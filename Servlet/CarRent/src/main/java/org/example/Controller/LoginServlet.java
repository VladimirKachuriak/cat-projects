package org.example.Controller;

import org.apache.log4j.Logger;
import org.example.Model.DAO.UserDAOImpl;
import org.example.Service.UserService;
import org.example.Service.UserServiceImpl;
import org.example.Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(LoginServlet.class.getSimpleName());
    private  UserService userService = new UserServiceImpl(new UserDAOImpl());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("return login page");
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login").trim();
        String password = req.getParameter("password").trim();
        User user = userService.getByLogin(login);
        Map<String, String> viewAttributes = new HashMap<>();
        viewAttributes.put("login", login);
        viewAttributes.put("password", password);
        if (user == null) {
            viewAttributes.put("message","label.warning.incorrectLogin");
            passErrorToView(req, resp, viewAttributes);
            return;
        }
        if(!user.getPassword().equals(password)) {
            viewAttributes.put("message","label.warning.incorrectPassword");
            passErrorToView(req, resp, viewAttributes);
            return;
        }
        if(user.getStatus().equals(User.Status.BANNED)){
            viewAttributes.put("message","label.warning.youBanned");
            passErrorToView(req, resp, viewAttributes);
            return;
        }
        HttpSession session= req.getSession();
        session.setAttribute("login", login);
        session.setAttribute("role", user.getRole());
        log.debug("user login in system");
        resp.sendRedirect("catalog");
    }

    private void passErrorToView(HttpServletRequest request, HttpServletResponse response, Map<String,String> viewAttributes) throws ServletException, IOException {
        for(Map.Entry<String, String> entry : viewAttributes.entrySet())
            request.setAttribute(entry.getKey(), entry.getValue());
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
