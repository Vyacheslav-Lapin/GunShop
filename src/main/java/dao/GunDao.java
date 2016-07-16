package dao;

import model.Gun;

import java.util.Collection;

public interface GunDao {
    Gun getById(long id);
    Collection<Gun> findAll();
}