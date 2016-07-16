package dao;

import model.Person;

import java.util.Optional;

public interface PersonDao {
    default Optional<Person> getById(long id) { return Optional.empty(); }
    Optional<Person> getByCredentials(String login, String password);
}
