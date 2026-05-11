package bean;

public class Test {

    private String studentNo;  // 学生番号
    private String subjectCd;  // 科目コード
    private int score;         // 点数

    // --- getter ---
    public String getStudentNo() {
        return studentNo;
    }

    public String getSubjectCd() {
        return subjectCd;
    }

    public int getScore() {
        return score;
    }

    // --- setter ---
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public void setSubjectCd(String subjectCd) {
        this.subjectCd = subjectCd;
    }

    public void setScore(int score) {
        this.score = score;
    }
}