package dao.controllers;

import dao.GunDao;
import model.Gun;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet("/list")
public class GunController extends HttpServlet {

    private GunDao gunDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        gunDao = (GunDao) config.getServletContext().getAttribute("gunDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Gun> guns = gunDao.findAll();
        req.setAttribute("guns", guns);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}