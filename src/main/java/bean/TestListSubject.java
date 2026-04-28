package bean;

import java.util.Map;

public class TestListSubject {

    private String studentNo;     // 学生番号
    private String studentName;   // 学生名
    private int entYear;          // 入学年度
    private String classNum;      // クラス番号
    private Map<Integer, Integer> points; // 年度・点数などの管理

    // --- getter ---
    public String getStudentNo() {
        return studentNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getEntYear() {
        return entYear;
    }

    public String getClassNum() {
        return classNum;
    }

    public Map<Integer, Integer> getPoints() {
        return points;
    }

    // --- setter ---
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public void setPoints(Map<Integer, Integer> points) {
        this.points = points;
    }
}