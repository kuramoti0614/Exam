package DAO;
// DAO（Data Access Object）パッケージ
// データベースアクセス処理を専門に担当するクラスをまとめる

import java.sql.Connection;
// データベースとの接続を表すクラス
import java.sql.PreparedStatement;
// SQLを安全に実行するためのクラス
// プレースホルダ（?）を使い、SQLインジェクションを防ぐ
import java.sql.ResultSet;
// SELECT文の実行結果を保持し、1行ずつデータを取得するためのクラス
import java.sql.SQLException;
// SQL処理に関する例外
import java.util.ArrayList;
// Listインタフェースの代表的な実装クラス（順序あり・重複可）
import java.util.LinkedHashSet;
// Setの一種（重複不可・追加順を保持）
import java.util.List;
// 複数データを順序付きで保持するためのインタフェース
import java.util.Set;
// 重複しないデータ集合を扱うためのインタフェース

import bean.School;
// 学校情報を保持するJavaBean
import bean.Student;
// 学生情報を保持するJavaBean

public class StudentDao extends Dao {
    // studentテーブル専用DAOクラス
    // Daoクラスを継承し、getConnection() を利用可能にする

    // ------------------------------------------------------------
    // ■ 学校コードで絞り込む基本SQL
    // ------------------------------------------------------------
    private String baseSql = "select * from student where school_cd = ?";
    // すべての検索で必ず「学校単位」でデータを取得するための共通SQL
    // 条件を共通化することで、SQLの重複記述を防ぐ

    // ============================================================
    // ■ 学生番号 + 学校コードで 1 件取得
    // ============================================================
    public Student get(String no, School school) throws Exception {
        // no     : 学生番号（studentテーブルの主キー）
        // school : 学校情報（school_cd を取得するため）

        Student student = null;
        // 検索結果を格納するStudentオブジェクト
        // 見つからなかった場合は null のまま返す

        Connection connection = getConnection();
        // データベース接続を取得

        PreparedStatement statement = null;
        // SQL実行用PreparedStatement（finallyでcloseするため外で宣言）

        try {
            // 学校コード＋学生番号で1件を特定するSQLを準備
            statement = connection.prepareStatement(
                "select * from student where school_cd = ? and no = ?"
            );

            // SQL中の ? に値を設定
            statement.setString(1, school.getCd());
            // 1番目の ? → 学校コード

            statement.setString(2, no);
            // 2番目の ? → 学生番号

            // SQLを実行し、結果を取得
            ResultSet resultSet = statement.executeQuery();

            // 結果が1件存在するか確認
            if (resultSet.next()) {
                // true → 該当学生が存在する

                student = new Student();
                // Studentオブジェクト生成

                student.setNo(resultSet.getString("no"));
                // 学生番号をセット

                student.setName(resultSet.getString("name"));
                // 氏名をセット

                student.setEntYear(resultSet.getInt("ent_year"));
                // 入学年度をセット

                student.setClassNum(resultSet.getString("class_num"));
                // クラス番号をセット

                student.setAttend(resultSet.getBoolean("is_attend"));
                // 在学フラグをセット

                student.setSchool(school);
                // 引数で渡されたSchoolオブジェクトをそのまま保持
            }

        } finally {
            // try内で例外が起きても必ず実行される

            if (statement != null) statement.close();
            // SQL実行オブジェクトを解放

            if (connection != null) connection.close();
            // DB接続を解放（接続枯渇防止）
        }

        // 検索結果（存在しなければ null）を返す
        return student;
    }

    // ============================================================
    // ■ ResultSet → List<Student> 変換処理（共通）
    // ============================================================
    private List<Student> postFilter(ResultSet resultSet, School school) throws Exception {
        // SELECT結果（複数行）を Student の List に変換する共通メソッド

        List<Student> list = new ArrayList<>();
        // 学生一覧格納用リスト

        try {
            while (resultSet.next()) {
                // ResultSetを1行ずつ処理

                Student s = new Student();
                // Studentオブジェクト生成

                s.setNo(resultSet.getString("no"));
                s.setName(resultSet.getString("name"));
                s.setEntYear(resultSet.getInt("ent_year"));
                s.setClassNum(resultSet.getString("class_num"));
                s.setAttend(resultSet.getBoolean("is_attend"));
                s.setSchool(school);
                // 1レコード分のデータをセット

                list.add(s);
                // リストに追加
            }
        } catch (SQLException | NullPointerException e) {
            // ResultSetがnullの場合やSQLエラー発生時の保険
            e.printStackTrace();
        }

        // 完成した学生リストを返す
        return list;
    }

    // ============================================================
    // ■ 入学年度 + クラス + 在学フラグで検索
    // ============================================================
    public List<Student> filter(
            School school,
            int entYear,
            String classNum,
            boolean isAttend) throws Exception {
        // 複数条件を組み合わせた学生検索

        List<Student> list = new ArrayList<>();
        // 戻り値用の学生リスト

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String condition = " and ent_year = ? and class_num = ? ";
        // 入学年度＋クラス条件

        String order = " order by no asc";
        // 学生番号順に並び替え

        String conditionIsAttend = "";
        // 在学条件（必要な場合のみ追加）

        if (isAttend) {
            conditionIsAttend = " and is_attend = true ";
            // 在学者のみ表示したい場合
        }

        try {
            // baseSqlに条件を追加してSQLを完成させる
            statement = connection.prepareStatement(
                baseSql + condition + conditionIsAttend + order
            );

            // プレースホルダに値をセット
            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);

            // SQL実行
            resultSet = statement.executeQuery();

            // ResultSetをList<Student>に変換
            list = postFilter(resultSet, school);

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // ============================================================
    // ■ 入学年度 + 在学フラグで検索
    // ============================================================
    public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {

        List<Student> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String condition = " and ent_year = ? ";
        // 入学年度条件のみ

        String order = " order by no asc";
        // 並び替え条件

        String conditionIsAttend = "";
        if (isAttend) {
            conditionIsAttend = " and is_attend = true ";
        }
        // 在学条件

        try {
            statement = connection.prepareStatement(
                baseSql + condition + conditionIsAttend + order
            );

            statement.setString(1, school.getCd());
            statement.setInt(2, entYear);

            resultSet = statement.executeQuery();

            list = postFilter(resultSet, school);
            // 共通処理でリスト化

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // ============================================================
    // ■ 在学フラグのみで学生一覧取得
    // ============================================================
    public List<Student> filter(School school, boolean isAttend) throws Exception {

        List<Student> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String order = " order by no asc";
        // 学生番号順

        String conditionIsAttend = "";
        if (isAttend) {
            conditionIsAttend = " and is_attend = true ";
        }

        try {
            String sql = baseSql + conditionIsAttend + order;
            // SQLを完成させる

            statement = connection.prepareStatement(sql);

            statement.setString(1, school.getCd());
            // 学校コード設定

            resultSet = statement.executeQuery();

            list = postFilter(resultSet, school);
            // List<Student> に変換

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    // ============================================================
    // ■ save（INSERT or UPDATE を自動判定）
    // ============================================================
    public boolean save(Student student) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;
        // SQLの更新件数

        try {
            // 同じ学生番号＋学校コードのデータが存在するか確認
            Student old = get(student.getNo(), student.getSchool());

            if (old == null) {
                // ---------- 新規登録（INSERT） ----------
                statement = connection.prepareStatement(
                    "insert into student(no, name, ent_year, class_num, is_attend, school_cd) "
                  + "values(?, ?, ?, ?, ?, ?)"
                );

                statement.setString(1, student.getNo());
                statement.setString(2, student.getName());
                statement.setInt(3, student.getEntYear());
                statement.setString(4, student.getClassNum());
                statement.setBoolean(5, student.isAttend());
                statement.setString(6, student.getSchool().getCd());

            } else {
                // ---------- 既存データ更新（UPDATE） ----------
                statement = connection.prepareStatement(
                    "update student set name=?, ent_year=?, class_num=?, is_attend=? "
                  + "where no=? and school_cd=?"
                );

                statement.setString(1, student.getName());
                statement.setInt(2, student.getEntYear());
                statement.setString(3, student.getClassNum());
                statement.setBoolean(4, student.isAttend());
                statement.setString(5, student.getNo());
                statement.setString(6, student.getSchool().getCd());
            }

            // INSERT または UPDATE を実行
            count = statement.executeUpdate();

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        // 1件以上更新されていれば成功
        return count > 0;
    }

    // ============================================================
    // ■ 更新専用メソッド
    // ============================================================
    public boolean update(Student student) throws Exception {

        String sql =
            "UPDATE student SET name=?, ent_year=?, class_num=?, is_attend=? "
          + "WHERE no=? AND school_cd=?";

        // try-with-resourcesを使用し、安全にリソース解放
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setInt(2, student.getEntYear());
            ps.setString(3, student.getClassNum());
            ps.setBoolean(4, student.isAttend());
            ps.setString(5, student.getNo());
            ps.setString(6, student.getSchool().getCd());

            // 1件更新されたかどうかを返す
            return ps.executeUpdate() == 1;
        }
    }

    // ============================================================
    // ■ クラス番号一覧取得（重複なし）
    // ============================================================
    public Set<String> getClassNumSet(School school) throws Exception {

        Set<String> set = new LinkedHashSet<>();
        // LinkedHashSet：重複なし＋表示順保持

        String sql =
            "SELECT DISTINCT class_num FROM student "
          + "WHERE school_cd = ? ORDER BY class_num";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, school.getCd());
            // 学校コード設定

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                set.add(rs.getString("class_num"));
                // クラス番号をSetに追加
            }
        }

        return set;
    }
}