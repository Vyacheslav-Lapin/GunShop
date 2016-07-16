package dao.h2;

import dao.PersonDao;
import model.Person;

import java.sql.*;
import java.util.Optional;
import java.util.function.Supplier;

public class H2PersonDao implements PersonDao {

    private Supplier<Connection> connectionSupplier;

    public H2PersonDao(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    private Connection getConnection() {
        return connectionSupplier.get();
    }

    @Override
    public Optional<Person> getByCredentials(String login, String password) {
        String sql =
                "SELECT id, first_name, last_name, permission, dob, address, telephone " +
                "FROM Person WHERE email = ? and password = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet rs = statement.executeQuery()) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}