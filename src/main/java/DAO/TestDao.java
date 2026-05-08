package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.Test;

public class TestDao {

    // H2 DB接続情報
    private static final String URL =
            "jdbc:h2:~/test";

    private static final String USER = "sa";
    private static final String PASSWORD = "";

    /**
     * 成績登録
     */
    public boolean insert(Test test) {

        boolean result = false;

        String sql =
            "INSERT INTO test(student_no, subject_cd, score) VALUES(?, ?, ?)";

        try {
            // H2ドライバ読み込み
            Class.forName("org.h2.Driver");

            // DB接続
            Connection conn =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            // SQL準備
            PreparedStatement ps =
                    conn.prepareStatement(sql);

            // 値セット
            ps.setString(1, test.getStudentNo());
            ps.setString(2, test.getSubjectCd());
            ps.setInt(3, test.getScore());

            // 実行
            int count = ps.executeUpdate();

            if (count > 0) {
                result = true;
            }

            // クローズ
            ps.close();
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}