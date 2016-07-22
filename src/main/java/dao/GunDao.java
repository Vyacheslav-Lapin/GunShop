package dao;

import model.Gun;

import java.util.Collection;
import java.util.Optional;

public interface GunDao {

    default Optional<Gun> getById(long id) {
        return findAll().stream()
                .filter(gun -> gun.getId() == id)
                .findAny();
    }

    Collection<Gun> findAll();
}