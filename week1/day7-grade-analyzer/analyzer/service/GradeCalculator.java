package analyzer.service;

import analyzer.model.Grade;
import analyzer.model.Student;

import java.util.*;
import java.util.stream.Collectors;

public class GradeCalculator {

    public Grade assignGrade(int marks) {
        if (marks >= 80) return Grade.A;
        else if (marks >= 60) return Grade.B;
        else if (marks >= 40) return Grade.C;
        else return Grade.FAIL;
    }

    public Map<Grade, Optional<Student>> getTopperPerGrade(List<Student> students) {
        return students.stream()
                .collect(Collectors.groupingBy(
                        Student::grade,
                        Collectors.maxBy(Comparator.comparingInt(Student::marks))
                ));
    }

    public double getAverageMarks(List<Student> students) {
        return students.stream().collect(Collectors.averagingInt(Student::marks));
       // return students.stream().mapToDouble(Student::marks).average().orElse(0.0);
    }

    public Map<Grade, List<Student>> groupByGrade(List<Student> students) {
        return students.stream().collect(Collectors.groupingBy(Student::grade));
    }
}
