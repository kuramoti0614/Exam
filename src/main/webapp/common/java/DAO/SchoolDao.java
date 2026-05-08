package DAO;
// DAO（Data Access Object）パッケージ
// データベースアクセス専用クラスをまとめるための場所

import java.sql.Connection;
// データベースとの接続を表すクラス
import java.sql.PreparedStatement;
// SQLを安全に実行するためのクラス
// プレースホルダ（?）に値を設定することでSQLインジェクションを防ぐ
import java.sql.ResultSet;
// SELECT文の実行結果を1行ずつ扱うためのクラス

import bean.School;
// schoolテーブル1件分のデータを保持するJavaBean

// School テーブルにアクセスする DAO クラス
public class SchoolDao extends Dao {
    // Daoクラスを継承
    // → getConnection() などの共通DB接続機能を利用できる

    // 学校コード（cd）をキーに School オブジェクトを 1 件取得するメソッド
    public School get(String cd) throws Exception {
        // cd : schoolテーブルの主キー（学校コード）
        // 戻り値 : Schoolオブジェクト（存在しなければ null）

        // schoolテーブルから cd と name のみを取得するSQL文
        // ※ 必要なカラムだけ取得することで無駄なデータ転送を防ぐ
        String sql = "SELECT cd, name FROM school WHERE cd = ?";

        // try-with-resources 構文を使用
        // tryを抜けると、自動的に close() が呼ばれる
        try (
            Connection con = getConnection();                 
            // DBに接続
            // Daoクラスで定義されたメソッドを使用

            PreparedStatement ps = con.prepareStatement(sql)  
            // SQL文をPreparedStatementとして準備
            // コンパイル済みSQLとして扱われるため安全・高速
        ) {

            // SQL中の ?（プレースホルダ）に引数の cd をセット
            ps.setString(1, cd);
            // 1 は「? の番号」（PreparedStatementは1始まり）

            // SQLを実行し、検索結果をResultSetとして受け取る
            try (ResultSet rs = ps.executeQuery()) {
                // ResultSetも try-with-resources で自動closeされる

                // 結果が1行以上存在するか確認
                if (rs.next()) {
                    // true → 該当する学校コードが存在する

                    School school = new School();
                    // Schoolオブジェクトを生成

                    // 結果セットから cd カラムの値を取得してセット
                    school.setCd(rs.getString("cd"));
                    // → schoolテーブルの主キー

                    // 結果セットから name カラムの値を取得してセット
                    school.setName(rs.getString("name"));
                    // → 学校名

                    // データが詰まったSchoolオブジェクトを呼び出し元へ返す
                    return school;
                }
                // rs.next() が false の場合
                // → 該当する学校コードは存在しない
            }
        }

        // tryブロック内で return されなかった場合
        // → データが見つからなかったことを表すため null を返す
        return null;
    }
}