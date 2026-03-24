package analyzer.util;

import analyzer.model.Student;
import analyzer.service.GradeCalculator;
import analyzer.exception.InvalidStudentDataException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvLoader {

    public List<Student> load(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filePath));
        GradeCalculator gradeCalculator = new GradeCalculator();
        List<Student> studentList = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length != 2)
                throw new InvalidStudentDataException("Invalid line: " + line);
            try {
                int marks = Integer.parseInt(parts[1].trim());
                studentList.add(new Student(parts[0].trim(), marks, gradeCalculator.assignGrade(marks)));
            } catch (NumberFormatException e) {
                throw new InvalidStudentDataException("Invalid marks in line: " + line);
            }
        }
        return studentList;
    }
}
