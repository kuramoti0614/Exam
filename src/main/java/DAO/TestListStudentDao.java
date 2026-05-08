package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

    private String baseSql =
        "SELECT subject_name, subject_cd, num, point " +
        "FROM test " +
        "WHERE student_no = ?";

    protected List<TestListStudent> postFilter(ResultSet rs) throws SQLException {
        List<TestListStudent> list = new ArrayList<>();

        while (rs.next()) {
            TestListStudent t = new TestListStudent();
            t.setSubjectName(rs.getString("subject_name"));
            t.setSubjectCd(rs.getString("subject_cd"));
            t.setNum(rs.getInt("num"));
            t.setPoint(rs.getInt("point"));
            list.add(t);
        }
        return list;
    }

    public List<TestListStudent> filter(Student student) {
        List<TestListStudent> list = new ArrayList<>();

        try (
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(baseSql)
        ) {
            ps.setString(1, student.getNo());
            ResultSet rs = ps.executeQuery();
            list = postFilter(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
