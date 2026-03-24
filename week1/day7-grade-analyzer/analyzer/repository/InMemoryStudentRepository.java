package analyzer.repository;

import java.util.ArrayList;
import java.util.List;
import analyzer.model.Student;

public class InMemoryStudentRepository implements StudentRepository<Student> {

    private final List<Student> list = new ArrayList<>();

    @Override
    public void save(Student student) {
        list.add(student);
    }

    @Override
    public List<Student> getAll() {
        return new ArrayList<>(list);
    }

}
