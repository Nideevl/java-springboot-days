package analyzer.repository;

import java.util.List;

public interface StudentRepository <T> {
    void save(T item);
    List<T> getAll();
}
