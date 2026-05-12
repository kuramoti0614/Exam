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

    private static final String BASE_SQL =
        "SELECT s.name AS subject_name, " +
        "       s.cd   AS subject_cd, " +
        "       t.no   AS num, " +
        "       t.point " +
        "FROM subject s " +
        "LEFT JOIN test t " +
        "       ON s.cd = t.subject_cd " +
        "      AND t.student_no = ? " +
        "      AND t.school_cd  = ? " +
        "ORDER BY s.cd, t.no";

    protected List<TestListStudent> postFilter(ResultSet rs)
            throws SQLException {

        List<TestListStudent> list = new ArrayList<>();

        while (rs.next()) {
            TestListStudent t = new TestListStudent();

            t.setSubjectName(rs.getString("subject_name"));
            t.setSubjectCd(rs.getString("subject_cd"));
            t.setNum(rs.getInt("num"));

            Integer point = rs.getObject("point", Integer.class);
            t.setPoint(point); // NULL 許容

            list.add(t);
        }
        return list;
    }

    public List<TestListStudent> filter(Student student) {

        List<TestListStudent> list = new ArrayList<>();

        try (
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(BASE_SQL)
        ) {
            ps.setString(1, student.getNo());
            ps.setString(2, student.getSchool().getCd());

            try (ResultSet rs = ps.executeQuery()) {
                list = postFilter(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
