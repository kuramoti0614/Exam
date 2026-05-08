package bean;

public class TestListStudent {

    private String subjectCd;     // 科目コード
    private String subjectName;   // 科目名
    private int num;              // 回数 or No
    private int point;            // 得点（別用途）

    // --- getter ---
    public String getSubjectCd() {
        return subjectCd;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getNum() {
        return num;
    }

    public int getPoint() {
        return point;
    }

    // --- setter ---
    public void setSubjectCd(String subjectCd) {
        this.subjectCd = subjectCd;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}