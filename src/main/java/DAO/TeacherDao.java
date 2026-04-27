package DAO;
// DAO（Data Access Object）用パッケージ
// データベースアクセス専用クラスをまとめる

import java.sql.Connection;
// DBとの「接続」を表すクラス
import java.sql.PreparedStatement;
// SQL文を安全に実行するためのクラス
// ?（プレースホルダ）を使い、SQLインジェクションを防止する
import java.sql.ResultSet;
// SELECT文の実行結果を1行ずつ扱うためのクラス
import java.sql.SQLException;
// SQL処理中に発生する例外クラス

import bean.Teacher;
// 教員情報を保持するJavaBean

public class TeacherDao extends Dao {
    // teacherテーブル専用のDAOクラス
    // Daoクラスを継承することで getConnection() を利用可能

    /**
     * getメソッド
     * 教員IDを指定して教員インスタンスを1件取得する
     *
     * @param id 教員ID
     * @return Teacherインスタンス（存在しない場合は null）
     */
    public Teacher get(String id) throws Exception {

        // 教員データ格納用の Teacher オブジェクト
        // 一旦生成し、見つからなかった場合は null に差し替える
        Teacher teacher = new Teacher();

        // DBへの接続を取得
        // Daoクラスで定義されている共通メソッド
        Connection connection = getConnection();

        // SQLを実行するための PreparedStatement
        // finally句で close() するため外で宣言
        PreparedStatement statement = null;

        try {
            // 教員IDを条件に teacher テーブルから1件取得するSQLを準備
            statement = connection.prepareStatement(
                "SELECT * FROM teacher WHERE id = ?"
            );

            // SQL中の ?（プレースホルダ）に教員IDをセット
            statement.setString(1, id);
            // ↑ 1 は「? の何番目か」を表す（最初は 1）

            // SELECT文を実行し、結果を ResultSet として受け取る
            ResultSet resultSet = statement.executeQuery();

            // 学校情報を取得するために SchoolDao を生成
            // teacherテーブルには school_cd しか無いため
            SchoolDao schoolDao = new SchoolDao();

            // 結果が1件以上存在するか確認
            if (resultSet.next()) {
                // next() が true → データが存在する

                // 教員IDをセット
                teacher.setId(resultSet.getString("id"));

                // 教員名をセット
                teacher.setName(resultSet.getString("name"));

                // パスワードをセット
                teacher.setPassword(resultSet.getString("password"));

                // school_cd を使って SchoolDao から School を取得
                // 文字列ではなく School オブジェクトとして保持する設計
                teacher.setSchool(
                    schoolDao.get(resultSet.getString("school_cd"))
                );

            } else {
                // resultSet.next() が false → データが存在しない
                // 見つからなかったことを呼び出し元に伝えるため null にする
                teacher = null;
            }

        } catch (Exception e) {
            // このメソッド内では例外処理せず、
            // 上位（ServiceやAction）に処理を委ねる
            throw e;

        } finally {
            // try内で例外が起きても必ず実行される

            // PreparedStatementの解放
            // closeしないとDBリソースが解放されず、接続枯渇の原因になる
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    // close中に発生した例外も上位へ投げる
                    throw sqle;
                }
            }

            // DB接続を解放
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        // 教員データ（存在しなければ null）を返却
        return teacher;
    }

    /**
     * loginメソッド
     * 教員IDとパスワードで認証する
     *
     * @param id 教員ID
     * @param password パスワード
     * @return 認証成功 → Teacherインスタンス、失敗 → null
     */
    public Teacher login(String id, String password) throws Exception {

        // 教員IDを使って教員情報を取得
        // DBアクセスは get() に集約することでコード重複を防ぐ
        Teacher teacher = get(id);

        // 以下のどちらかに該当すれば認証失敗
        // ① teacher が null（＝IDが存在しない）
        // ② パスワードが一致しない
        if (teacher == null || !teacher.getPassword().equals(password)) {
            return null;
            // 認証失敗を表すため null を返す
        }

        // IDとパスワードが一致 → 認証成功
        // 取得した Teacher インスタンスをそのまま返す
        return teacher;
    }
}
