package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

/**
 * 科目テーブル(subject)を操作するDAOクラス
 */
public class SubjectDao extends Dao {

    /**
     * コンストラクタ
     * 親クラスDaoの初期化を行う
     */
    public SubjectDao() throws Exception {
        super();
    }

    /**
     * 科目コード(cd)と学校情報を使って
     * 科目を1件取得するメソッド
     *
     * @param cd      科目コード
     * @param school  学校オブジェクト
     * @return Subjectオブジェクト（存在しない場合はnull）
     */
    public Subject get(String cd, School school) throws Exception {

        // 戻り値用のSubject
        Subject subject = null;

        // SQL文
        String sql = "SELECT * FROM subject WHERE cd = ? AND school_cd = ?";

        // DB接続
        try (Connection conn = getConnection();

             // PreparedStatement生成
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // プレースホルダに値をセット
            ps.setString(1, cd);
            ps.setString(2, school.getCd());

            // SQL実行
            ResultSet rs = ps.executeQuery();

            // データが存在する場合
            if (rs.next()) {

                // Subjectオブジェクト生成
                subject = new Subject();

                // DB値をセット
                subject.setCd(rs.getString("cd"));
                subject.setName(rs.getString("name"));

                // Schoolオブジェクト設定
                subject.setSchool(school);
            }
        }

        // 結果返却
        return subject;
    }

    /**
     * 指定学校に属する科目一覧を取得する
     *
     * @param school 学校オブジェクト
     * @return 科目リスト
     */
    public List<Subject> filter(School school) throws Exception {

        // 科目一覧格納用リスト
        List<Subject> list = new ArrayList<>();

        // SQL文
        String sql = "SELECT * FROM subject WHERE school_cd = ? ORDER BY cd";

        // DB接続
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 学校コードをセット
            ps.setString(1, school.getCd());

            // SQL実行
            ResultSet rs = ps.executeQuery();

            // 1件ずつ取り出す
            while (rs.next()) {

                // Subject生成
                Subject subject = new Subject();

                // DB値セット
                subject.setCd(rs.getString("cd"));
                subject.setName(rs.getString("name"));

                // 学校情報セット
                subject.setSchool(school);

                // リストへ追加
                list.add(subject);
            }
        }

        // 科目一覧返却
        return list;
    }

    /**
     * 科目を新規登録する
     *
     * @param subject 登録する科目
     * @return 登録成功時true
     */
    public boolean save(Subject subject) throws Exception {

        // INSERT文
        String sql =
            "INSERT INTO subject (cd, name, school_cd) VALUES (?, ?, ?)";

        // DB接続
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 値セット
            ps.setString(1, subject.getCd());
            ps.setString(2, subject.getName());
            ps.setString(3, subject.getSchool().getCd());

            // 実行結果が1件なら成功
            return ps.executeUpdate() == 1;
        }
    }

    /**
     * 科目を削除する
     *
     * @param subject 削除対象科目
     * @return 削除成功時true
     */
    public boolean delete(Subject subject) throws Exception {

        // DELETE文
        String sql = "DELETE FROM subject WHERE cd = ? AND school_cd = ?";

        // DB接続
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 条件値セット
            ps.setString(1, subject.getCd());
            ps.setString(2, subject.getSchool().getCd());

            // 実行結果が1件なら成功
            return ps.executeUpdate() == 1;
        }
    }

    public List<Subject> filterBySchool(String schoolCd) throws Exception {

        List<Subject> list = new ArrayList<>();

        String sql =
            "SELECT cd, name FROM subject WHERE school_cd = ? ORDER BY cd";

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, schoolCd);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setCd(rs.getString("cd"));
                subject.setName(rs.getString("name"));

                School school = new School();
                school.setCd(schoolCd);
                subject.setSchool(school);

                list.add(subject);
            }
        }
        return list;
    }
}