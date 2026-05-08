package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    private String baseSql =
        "SELECT s.ENT_YEAR, s.NO AS STUDENT_NO, s.NAME AS STUDENT_NAME, " +
        "       s.CLASS_NUM, t.NO AS TEST_NO, t.POINT " +
        " FROM STUDENT s " +
        " JOIN TEST t ON s.NO = t.STUDENT_NO " +
        "           AND s.SCHOOL_CD = t.SCHOOL_CD " +
        " WHERE s.ENT_YEAR = ? " +
        "   AND s.CLASS_NUM = ? " +
        "   AND t.SUBJECT_CD = ? " +
        "   AND s.SCHOOL_CD = ? " +
        " ORDER BY s.NO, t.NO";

    protected List<TestListSubject> postFilter(ResultSet rs) throws SQLException {

        Map<String, TestListSubject> map = new LinkedHashMap<>();

        while (rs.next()) {
            String studentNo = rs.getString("STUDENT_NO");

            TestListSubject tls = map.get(studentNo);
            if (tls == null) {
                tls = new TestListSubject();
                tls.setEntYear(rs.getInt("ENT_YEAR"));
                tls.setStudentNo(studentNo);
                tls.setStudentName(rs.getString("STUDENT_NAME"));
                tls.setClassNum(rs.getString("CLASS_NUM"));
                map.put(studentNo, tls);
            }

            tls.putPoint(
                rs.getInt("TEST_NO"),
                rs.getInt("POINT")
            );
        }

        return new ArrayList<>(map.values());
    }

    public List<TestListSubject> filter(
            int entYear,
            String classNum,
            Subject subject,
            School school) {

        List<TestListSubject> list = new ArrayList<>();

        try (
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(baseSql)
        ) {
            ps.setInt(1, entYear);
            ps.setString(2, classNum);
            ps.setString(3, subject.getCd());
            ps.setString(4, school.getCd());

            ResultSet rs = ps.executeQuery();
            list = postFilter(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
