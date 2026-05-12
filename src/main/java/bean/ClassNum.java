package bean;

public class ClassNum {

    private String classNum;
    private School school;

    public String getClassNum() {     // ★ 修正
        return classNum;
    }

    public void setClassNum(String classNum) {   // ★ 修正
        this.classNum = classNum;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
