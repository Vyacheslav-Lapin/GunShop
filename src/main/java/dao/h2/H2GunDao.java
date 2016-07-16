package dao.h2;

import dao.GunDao;
import model.Gun;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class H2GunDao implements GunDao {

    private Supplier<Connection> connectionSupplier;

    public H2GunDao(Supplier<Connection> connectionSupplier, String realPath) {
        this.connectionSupplier = connectionSupplier;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            final String[] sqls = Files.lines(Paths.get(realPath + "WEB-INF/classes/h2.sql"))
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
    }

    private Connection getConnection() {
        return connectionSupplier.get();
    }

    @Override
    public Gun getById(long id) {
        return null;
    }

    @Override
    public Collection<Gun> findAll() {
        Collection<Gun> guns = new HashSet<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(
                     "SELECT id, name, caliber FROM Gun")) {
            while (rs.next())
                guns.add(new Gun(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDouble("caliber")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return guns;
    }
}
