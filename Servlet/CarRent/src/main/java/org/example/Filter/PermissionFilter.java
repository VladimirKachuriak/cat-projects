package org.example.Filter;


import org.apache.log4j.Logger;
import org.example.Model.DAO.UserDAOImpl;
import org.example.Service.UserService;
import org.example.Service.UserServiceImpl;
import org.example.Model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PermissionFilter implements Filter {
    private static final Logger log = Logger.getLogger(EncodingFilter.class.getSimpleName());
    //URL which allowed for admin
    private static final List<String> admin = Arrays.asList("/login","/logout","/car","/logout","/catalog","/adminOrders","/users","/new_admin.jsp","/new_car.jsp","/error.jsp","/error404.jsp");
    //URL which allowed for user
    private static final List<String> user = Arrays.asList("/login","/logout","/catalog","/logout","/registration","/userOrders","/error.jsp","/error404.jsp");
    //URL which allowed for guest
    private static final List<String> guest = Arrays.asList("/login","/registration","/catalog","/error.jsp","/error404.jsp");
    private UserService userService = new UserServiceImpl(new UserDAOImpl());
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("init permission filter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();

        final HttpSession session = request.getSession();
        //if there is no role in the session, then the guest role is set
        if(session.getAttribute("role")==null){
            User.Role role = (User.Role) session.getAttribute("login");
            if(role!=null){
                session.setAttribute("role", role.toString());
            }else{
                session.setAttribute("role", "GUEST");
            }
        }
        log.debug("user role: "+session.getAttribute("role"));
        //check urls for admin if role admin
        if(session.getAttribute("role").toString().equals("ADMIN")){
            if(admin.contains(path)){
              filterChain.doFilter(servletRequest,servletResponse);
            }else{
                request.getRequestDispatcher("error404.jsp").forward(servletRequest,servletResponse);
            }
            return;
        }
        //check urls for user if role user
        if(session.getAttribute("role").toString().equals("USER")){
            if(userService.getByLogin((String)request.getSession().getAttribute("login")).getStatus().equals(User.Status.BANNED)){
                request.getSession().removeAttribute("login");
                request.getSession().removeAttribute("role");
                response.sendRedirect("login");
                return;
            }
            if(user.contains(path)){
                filterChain.doFilter(servletRequest,servletResponse);
            }else{
                request.getRequestDispatcher("error404.jsp").forward(servletRequest,servletResponse);
            }
            return;
        }
          //check urls for guest if role guest
        if(session.getAttribute("role").toString().equals("GUEST")){
            if(guest.contains(path)){
                filterChain.doFilter(servletRequest,servletResponse);
            }else{
                request.getRequestDispatcher("error404.jsp").forward(servletRequest,servletResponse);
            }
            return;
        }
        request.getRequestDispatcher("error404.jsp").forward(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        log.debug("destroy permission filter");
    }
}
