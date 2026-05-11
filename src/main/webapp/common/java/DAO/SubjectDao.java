package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

    public SubjectDao() throws Exception {
        super();
    }

    /**
     * 科目コード＋学校で1件取得
     */
    public Subject get(String cd, School school) throws Exception {
        Subject subject = null;

        String sql = "SELECT * FROM subject WHERE cd = ? AND school_cd = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cd);
            ps.setString(2, school.getCd());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                subject = new Subject();
                subject.setCd(rs.getString("cd"));
                subject.setName(rs.getString("name"));
                subject.setSchool(school);
            }
        }
        return subject;
    }

    /**
     * 学校に属する科目一覧取得
     */
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();

        String sql = "SELECT * FROM subject WHERE school_cd = ? ORDER BY cd";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, school.getCd());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setCd(rs.getString("cd"));
                subject.setName(rs.getString("name"));
                subject.setSchool(school);

                list.add(subject);
            }
        }
        return list;
    }

    /**
     * 科目登録
     */
    public boolean save(Subject subject) throws Exception {
        String sql =
            "INSERT INTO subject (cd, name, school_cd) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, subject.getCd());
            ps.setString(2, subject.getName());
            ps.setString(3, subject.getSchool().getCd());

            return ps.executeUpdate() == 1;
        }
    }

    /**
     * 科目削除
     */
    public boolean delete(Subject subject) throws Exception {
        String sql = "DELETE FROM subject WHERE cd = ? AND school_cd = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, subject.getCd());
            ps.setString(2, subject.getSchool().getCd());

            return ps.executeUpdate() == 1;
        }
    }
}
