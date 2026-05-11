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
        "SELECT subject_name, subject_cd, num, point " +
        "FROM test " +
        "WHERE student_no = ?";

    protected List<TestListStudent> postFilter(ResultSet rs)
            throws SQLException {

        List<TestListStudent> list = new ArrayList<>();

        while (rs.next()) {
            TestListStudent t = new TestListStudent();

            t.setSubjectName(rs.getString("subject_name"));
            t.setSubjectCd(rs.getString("subject_cd"));
            t.setNum(rs.getInt("num"));

            // --- point が NULL か判定 ---
            Integer point = rs.getObject("point", Integer.class);
            t.setPoint(point);   // null のままセットできる

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

            try (ResultSet rs = ps.executeQuery()) {
                list = postFilter(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
        return list;
    }
}
