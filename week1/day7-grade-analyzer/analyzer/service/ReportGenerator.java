package analyzer.service;

import analyzer.model.Grade;
import analyzer.model.Student;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReportGenerator {
    public void printReport(List<Student> students) {

        GradeCalculator studentReportCalculator = new GradeCalculator();
        Map<Grade, Optional<Student>> toppers = studentReportCalculator.getTopperPerGrade(students);
        Map<Grade, List<Student>> gradeGroups = studentReportCalculator.groupByGrade(students);
        System.out.println("======================================================================================");
        System.out.println("Class Report");
        System.out.println("======================================================================================");

        for (Grade grade : Grade.values()) {
            if (gradeGroups.get(grade) == null) continue;

            System.out.println("Grade " + grade.name() + " students :");
            for (Student student : gradeGroups.get(grade)) {
                System.out.println(" -" + student.name());
            }
            System.out.println();
            toppers.get(grade)
                    .ifPresent(t -> System.out.println("- Topper of this grade is " + t.name() + " with score of " + t.marks()));
            System.out.println("--------------------------------------------------------------------------------------");
        }
        System.out.println("- Average of the class is " + studentReportCalculator.getAverageMarks(students));
        System.out.println("======================================================================================");
    }
}
