package controllers;

import dao.GunDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static listeners.DbInitListener.GUN_DAO;

@WebServlet("/buy")
public class Buy extends HttpServlet {

    private static final String GUN = "gun";
    private static final String ID = "id";
    private static final String VIEW = "/buy/index.jsp";
    private GunDao gunDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        gunDao = (GunDao) config.getServletContext().getAttribute(GUN_DAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional.ofNullable(request.getParameter(ID))
                        .map(Long::parseLong)
                        .flatMap(gunDao::getById)
                        .ifPresent(gun -> request.setAttribute(GUN, gun));

        request.getRequestDispatcher(VIEW)
                .forward(request, response);
    }
}
