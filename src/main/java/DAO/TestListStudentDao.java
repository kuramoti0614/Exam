package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

    private static final String BASE_SQL =
        "SELECT " +
        "    st.name AS student_name, " +
        "    s.cd AS subject_cd, " +
        "    s.name AS subject_name, " +
        "    t.no AS num, " +
        "    t.point " +
        "FROM student st " +
        "JOIN subject s ON s.school_cd = st.school_cd " +
        "LEFT JOIN test t " +
        "       ON t.student_no = st.no " +
        "      AND t.subject_cd = s.cd " +
        "      AND t.school_cd = st.school_cd " +
        "WHERE st.no = ? " +
        "  AND st.school_cd = ? " +
        "ORDER BY s.cd, t.no";

    public List<TestListStudent> filter(Student student,String subjectCd) {

        List<TestListStudent> list = new ArrayList<>();

        try (
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(BASE_SQL)
        ) {
            ps.setString(1, student.getNo());
            ps.setString(2, student.getSchool().getCd());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TestListStudent t = new TestListStudent();

                    t.setSubjectCd(rs.getString("subject_cd"));
                    t.setSubjectName(rs.getString("subject_name"));

                    Integer num = rs.getObject("num", Integer.class);
                    t.setNum(num != null ? num : 0);

                    Integer point = rs.getObject("point", Integer.class);
                    t.setPoint(point);

                    // ★ 学生名は Student にセット
                    student.setName(rs.getString("student_name"));

                    list.add(t);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
