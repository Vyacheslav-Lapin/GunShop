package listeners;

import dao.h2.H2GunDao;
import dao.h2.H2PersonDao;

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

    public static final String GUN_DAO = "gunDao";
    public static final String PERSON_DAO = "personDao";
    private static final String INIT_DB_SCRIPT_PATH = "/WEB-INF/classes/h2.sql";

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        Supplier<Connection> connectionSupplier = getConnectionSupplier();

        ServletContext servletContext = sce.getServletContext();
        execInitDbScript(connectionSupplier, servletContext);

        servletContext.setAttribute(GUN_DAO, new H2GunDao(connectionSupplier));
        servletContext.setAttribute(PERSON_DAO, new H2PersonDao(connectionSupplier));
    }

    private void execInitDbScript(Supplier<Connection> connectionSupplier, ServletContext servletContext) {
        try (Connection connection = connectionSupplier.get();
             Statement statement = connection.createStatement()) {

            final String[] sqls = Files.lines(
                    Paths.get(servletContext.getRealPath(INIT_DB_SCRIPT_PATH)))
                    .collect(Collectors.joining()).split(";");
            Arrays.stream(sqls)
                    .forEach((sql) -> {
                        try {
                            statement.addBatch(sql);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
            statement.executeBatch();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Supplier<Connection> getConnectionSupplier() {
        try {
            DataSource lookup = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/TestDB");
            return () -> {
                try {
                    return lookup.getConnection();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            };
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}