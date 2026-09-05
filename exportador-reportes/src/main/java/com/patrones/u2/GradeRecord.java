package com.patrones.u2;

/**
 * Registro de una calificacion individual dentro de un acta academica.
 */
public class GradeRecord {
    private final String studentId;
    private final String studentName;
    private final String courseCode;
    private final double grade;

    public GradeRecord(String studentId, String studentName, String courseCode, double grade) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.courseCode = courseCode;
        this.grade = grade;
    }

    public String getStudentId()   { return studentId; }
    public String getStudentName() { return studentName; }
    public String getCourseCode()  { return courseCode; }
    public double getGrade()       { return grade; }
}