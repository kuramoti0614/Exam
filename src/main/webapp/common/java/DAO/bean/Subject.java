package bean;

public class Subject {

    private String cd;    // 科目コード
    private String name;  // 科目名
    private School school; // 学校

    // --- getter ---
    public String getCd() {
        return cd;
    }

    public String getName() {
        return name;
    }

    public School getSchool() {
        return school;
    }

    // --- setter ---
    public void setCd(String cd) {
        this.cd = cd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}