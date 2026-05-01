package utils;

import employees.Employee;

import java.util.List;

public interface Searchable<T> {
    public T findById(int id);
    public List<T> findByName(String name);
    List<T> findAll();
}
