package dao.h2;

import common.JdbcDao;
import dao.PersonDao;
import model.Person;

import java.sql.*;
import java.util.Optional;
import java.util.function.Supplier;

public class H2PersonDao implements PersonDao, JdbcDao {

    private Supplier<Connection> connectionSupplier;

    public H2PersonDao(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    @Override
    public Connection getConnection() {
        return connectionSupplier.get();
    }

    @Override
    public Optional<Person> getByCredentials(String login, String password) {
        //language=H2
        String sql =
                "SELECT id, first_name, last_name, permission, dob, address, telephone " +
                "FROM Person WHERE email = ? and password = ?";
        return mapPreparedStatement(sql, preparedStatement -> {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return Optional.ofNullable(!rs.next() ? null :
                        new Person(rs.getLong("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getBoolean("permission"),
                                rs.getDate("dob").toLocalDate(),
                                login,
                                password,
                                rs.getString("address"),
                                rs.getString("telephone")));
            }
        }).getOrThrowUnchecked();
    }
}