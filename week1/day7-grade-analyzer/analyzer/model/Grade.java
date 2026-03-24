package analyzer.model;

public enum Grade {
    A(80),B(60),C(40),FAIL(0);

    private int minMarks;
    private Grade(int minMarks) {
        this.minMarks = minMarks;
    }

    public int getMinMarks() { return minMarks; }
}
