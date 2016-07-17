package filters;

import dao.PersonDao;
import model.Person;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebFilter("/*")
public class SecurityFilter implements Filter {

    private PersonDao personDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        personDao = (PersonDao) filterConfig.getServletContext().getAttribute("personDao");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        String requestURI = request.getRequestURI();
        if (session.getAttribute("person") != null || requestURI.equals("/login.jsp"))
            chain.doFilter(request, response);
        else {
            Optional<Person> person = Optional.ofNullable(request.getParameter("j_username"))
                    .flatMap(login -> Optional.ofNullable(request.getParameter("j_password"))
                                    .flatMap(pwd -> personDao.getByCredentials(login, pwd)));
            if (person.isPresent()) {
                session.setAttribute("person", person.get());
                chain.doFilter(request, response);
            } else {
                request.setAttribute("requestURI", requestURI);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        }
    }
}
