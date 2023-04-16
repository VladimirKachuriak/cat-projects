package org.example.Model.DAO;

import java.util.List;

public interface Entity<T> {
    List<T> getAll();

    T getById(int id);

    boolean create(T t);

    boolean update(T t);

    boolean delete(int id);
}
