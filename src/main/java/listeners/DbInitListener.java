package listeners;

import dao.h2.H2GunDao;
import dao.h2.H2PersonDao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@WebListener
public class DbInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        Supplier<Connection> connectionSupplier = () -> {
            try {
                Context initContext = new InitialContext();
                Context envContext = (Context) initContext.lookup("java:/comp/env");
                DataSource ds = (DataSource) envContext.lookup("jdbc/TestDB");
                return ds.getConnection();
            } catch (NamingException | SQLException e) {
                throw new RuntimeException(e);
            }
        };

        ServletContext servletContext = sce.getServletContext();
        try (Connection connection = connectionSupplier.get();
             Statement statement = connection.createStatement()) {

            final String[] sqls = Files.lines(
                    Paths.get(servletContext.getRealPath("/WEB-INF/classes/h2.sql")))
                    .collect(Collectors.joining()).split(";");
            Arrays.stream(sqls)
                    .forEach((sql) -> {
                        try {
                            statement.addBatch(sql);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
            statement.executeBatch();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        servletContext.setAttribute("gunDao", new H2GunDao(connectionSupplier));
        servletContext.setAttribute("personDao", new H2PersonDao(connectionSupplier));
    }
}