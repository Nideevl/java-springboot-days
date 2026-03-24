package analyzer;

import analyzer.repository.InMemoryStudentRepository;
import analyzer.service.ReportGenerator;
import analyzer.util.CsvLoader;
import analyzer.model.Student;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        CsvLoader loader = new CsvLoader();
        List<Student> students = loader.load("D:/JAVA/week1/day7-grade-analyzer/students.csv");

        InMemoryStudentRepository repository = new InMemoryStudentRepository();
        for (Student s : students) repository.save(s);

        ReportGenerator report = new ReportGenerator();
        report.printReport(repository.getAll());
    }
}