package dao.h2;

import dao.GunDao;
import model.Gun;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Supplier;

public class H2GunDao implements GunDao {

    private Supplier<Connection> connectionSupplier;

    public H2GunDao(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    private Connection getConnection() {
        return connectionSupplier.get();
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
