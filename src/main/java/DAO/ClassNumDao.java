package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

// ClassNum テーブルに対するデータアクセスを行う DAO クラス
public class ClassNumDao extends Dao {

    // 指定されたクラス番号と学校に対応する ClassNum を 1 件取得するメソッド
    public ClassNum get(String class_num, School school) throws Exception {

        // 取得結果を格納するための変数（見つからなければ null のまま）
        ClassNum classNum = null;
        // DB 接続オブジェクト
        Connection connection = getConnection();
        // SQL を実行するための PreparedStatement
        PreparedStatement statement = null;

        try {
            // class_num と school_cd を条件に 1 件取得する SQL を準備
            statement = connection.prepareStatement(
                "SELECT * FROM class_num WHERE class_num = ? AND school_cd = ?"
            );
            // 1 番目のプレースホルダ（?）にクラス番号をセット
            statement.setString(1, class_num);
            
            // 2 番目のプレースホルダ（?）に学校コードをセット
            statement.setString(2, school.getCd());

            // SQL を実行し、結果セットを取得
            ResultSet rSet = statement.executeQuery();

            // 1 行でも結果があれば、その内容を ClassNum オブジェクトに詰める
            if (rSet.next()) {
                classNum = new ClassNum();
                // 取得した class_num をセット
                classNum.setClass_num(rSet.getString("class_num"));
                // 引数で渡された School オブジェクトをそのままセット
                classNum.setSchool(school);
            }

        } finally {
            // PreparedStatement が生成されていればクローズ
            if (statement != null) statement.close();
            // Connection が生成されていればクローズ
            if (connection != null) connection.close();
        }

        // 見つかった ClassNum（なければ null）を返す
        return classNum;
    }


    // 指定された学校に属するクラス番号一覧を取得するメソッド
    public List<String> filter(School school) throws Exception {

        // クラス番号（String）のリストを格納するための List
        List<String> list = new ArrayList<>();
        // DB 接続オブジェクト
        Connection connection = getConnection();
        // SQL を実行するための PreparedStatement
        PreparedStatement statement = null;

        try {
            // 指定された学校コードに紐づくクラス番号を昇順で取得する SQL
            statement = connection.prepareStatement(
                "SELECT class_num FROM class_num WHERE school_cd = ? ORDER BY class_num"
            );
            // プレースホルダに学校コードをセット
            statement.setString(1, school.getCd());

            // SQL を実行し、結果セットを取得
            ResultSet rSet = statement.executeQuery();

            // 結果セットの全行をループし、クラス番号をリストに追加
            while (rSet.next()) {
                list.add(rSet.getString("class_num"));
            }

        } finally {
            // PreparedStatement が生成されていればクローズ
            if (statement != null) statement.close();
            // Connection が生成されていればクローズ
            if (connection != null) connection.close();
        }

        // 取得したクラス番号のリストを返す（該当なしなら空リスト）
        return list;
    }


    // ClassNum を新規登録するメソッド（INSERT）
    public boolean save(ClassNum classNum) throws Exception {

        // DB 接続オブジェクト
        Connection connection = getConnection();
        // SQL を実行するための PreparedStatement
        PreparedStatement statement = null;
        // 更新件数を格納する変数
        int count = 0;

        try {
            // class_num テーブルに新しいレコードを追加する SQL
            statement = connection.prepareStatement(
                "INSERT INTO class_num (class_num, school_cd) VALUES (?, ?)"
            );
            // 1 番目のプレースホルダにクラス番号をセット
            statement.setString(1, classNum.getClass_num());
            // 2 番目のプレースホルダに学校コードをセット
            statement.setString(2, classNum.getSchool().getCd());

            // INSERT を実行し、影響を受けた行数を取得
            count = statement.executeUpdate();

        } finally {
            // PreparedStatement が生成されていればクローズ
            if (statement != null) statement.close();
            // Connection が生成されていればクローズ
            if (connection != null) connection.close();
        }

        // 1 件以上登録できていれば true を返す
        return count > 0;
    }


    // 既存の ClassNum を別のクラス番号に変更するメソッド
    // class_num テーブルだけでなく、student・test テーブルのクラス番号も合わせて更新する
    public boolean save(ClassNum classNum, String newClassNum) throws Exception {

        // DB 接続オブジェクト
        Connection connection = getConnection();
        // SQL を実行するための PreparedStatement
        PreparedStatement statement = null;
        // すべての UPDATE の合計件数を格納する変数
        int count = 0;

        try {
            // ① class_num テーブルのクラス番号を更新する SQL
            statement = connection.prepareStatement(
                "UPDATE class_num SET class_num = ? WHERE class_num = ? AND school_cd = ?"
            );
            // 新しいクラス番号をセット
            statement.setString(1, newClassNum);
            // 変更前のクラス番号をセット
            statement.setString(2, classNum.getClass_num());
            // 学校コードをセット
            statement.setString(3, classNum.getSchool().getCd());

            // UPDATE を実行し、影響を受けた行数を加算
            count += statement.executeUpdate();
            // いったんクローズ（次の SQL を準備するため）
            statement.close();

            // ② student テーブルのクラス番号を更新する SQL
            statement = connection.prepareStatement(
                "UPDATE student SET class_num = ? WHERE class_num = ? AND school_cd = ?"
            );
            // 新しいクラス番号をセット
            statement.setString(1, newClassNum);
            // 変更前のクラス番号をセット
            statement.setString(2, classNum.getClass_num());
            // 学校コードをセット
            statement.setString(3, classNum.getSchool().getCd());

            // UPDATE を実行し、影響を受けた行数を加算
            count += statement.executeUpdate();
            // 再度クローズ
            statement.close();

            // ③ test テーブルのクラス番号を更新する SQL
            statement = connection.prepareStatement(
                "UPDATE test SET class_num = ? WHERE class_num = ? AND school_cd = ?"
            );
            // 新しいクラス番号をセット
            statement.setString(1, newClassNum);
            // 変更前のクラス番号をセット
            statement.setString(2, classNum.getClass_num());
            // 学校コードをセット
            statement.setString(3, classNum.getSchool().getCd());

            // UPDATE を実行し、影響を受けた行数を加算
            count += statement.executeUpdate();

        } finally {
            // PreparedStatement が生成されていればクローズ
            if (statement != null) statement.close();
            // Connection が生成されていればクローズ
            if (connection != null) connection.close();
        }

        // 合計で 3 件より多く更新されていれば（3 テーブル分がきちんと更新されたとみなして）true を返す
        // ※ 1 テーブルにつき最低 1 件更新されることを期待しているロジック
        return count > 3;
    }
}
