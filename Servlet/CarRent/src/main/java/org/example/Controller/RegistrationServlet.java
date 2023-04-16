package org.example.Controller;

import org.apache.log4j.Logger;
import org.example.Model.DAO.UserDAOImpl;
import org.example.Service.UserService;
import org.example.Service.UserServiceImpl;
import org.example.Model.User;
import org.example.Service.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(RegistrationServlet.class.getSimpleName());
    private   UserService userService = new UserServiceImpl(new UserDAOImpl());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phoneNumber = req.getParameter("phone");
        User user = new User();
        user.setLogin(login);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setRole(User.Role.ADMIN);
        user.setStatus(User.Status.ACTIVE);
        if(!Validator.validatePhone(phoneNumber)){
            req.getSession().setAttribute("message","label.warning.incorrectPhone");
            req.setAttribute("user",user);
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
            return;
        }
        if(!Validator.validateEmail(email)){
            req.getSession().setAttribute("message","label.warning.incorrectEmail");
            req.setAttribute("user",user);
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
            return;
        }
        if(userService.getByLogin(login)==null){
            req.getSession().setAttribute("message","label.warning.userAlreadyExist");
            req.setAttribute("user",user);
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
            return;
        }
        log.info("register new user");
        boolean flag = userService.create(user);
        if(flag)resp.sendRedirect("login");
    }
}
