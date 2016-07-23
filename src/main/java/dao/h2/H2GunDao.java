package dao.h2;

import common.JdbcDao;
import dao.GunDao;
import model.Gun;

import java.sql.Connection;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Supplier;

public class H2GunDao implements GunDao, JdbcDao {

    private Supplier<Connection> connectionSupplier;

    public H2GunDao(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    @Override
    public Connection getConnection() {
        return connectionSupplier.get();
    }

    @Override
    public Collection<Gun> findAll() {
        Collection<Gun> guns = new HashSet<>();
        mapAndReduceRows("SELECT id, name, caliber FROM Gun",
                rs -> new Gun(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDouble("caliber")),
                guns::add
        ).run();
        return guns;
    }
}
