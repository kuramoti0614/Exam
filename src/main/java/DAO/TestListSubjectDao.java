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

    private static final String BASE_SQL =
        "SELECT s.ENT_YEAR, s.NO AS STUDENT_NO, s.NAME AS STUDENT_NAME, " +
        "       s.CLASS_NUM, t.NO AS TEST_NO, t.POINT " +
        " FROM STUDENT s " +
        " LEFT JOIN TEST t " +
        "        ON s.NO = t.STUDENT_NO " +
        "       AND s.SCHOOL_CD = t.SCHOOL_CD " +
        "       AND t.SUBJECT_CD = ? " +
        " WHERE s.ENT_YEAR = ? " +
        "   AND s.CLASS_NUM = ? " +
        "   AND s.SCHOOL_CD = ? " +
        " ORDER BY s.NO, t.NO";

    protected List<TestListSubject> postFilter(ResultSet rs)
            throws SQLException {

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

            Integer testNo = rs.getObject("TEST_NO", Integer.class);
            Integer point  = rs.getObject("POINT", Integer.class);

            // テスト回数が存在する場合のみ追加
            if (testNo != null) {
                tls.putPoint(testNo, point);  // point は null 許容
            }
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
            PreparedStatement ps = con.prepareStatement(BASE_SQL)
        ) {
            ps.setString(1, subject.getCd());
            ps.setInt(2, entYear);
            ps.setString(3, classNum);
            ps.setString(4, school.getCd());

            try (ResultSet rs = ps.executeQuery()) {
                list = postFilter(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
