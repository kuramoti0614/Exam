package scoremanager.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import DAO.Dao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// このサーブレットへアクセスするURL
@WebServlet("/scoremanager/main/TestRegistAction")
public class TestRegistAction extends HttpServlet {

    // POST送信時に実行されるメソッド
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response
            ) throws ServletException, IOException {

        // 文字コードをUTF-8に設定
        request.setCharacterEncoding("UTF-8");

        // =========================
        // フォームから値を取得
        // =========================

        // 入学年度
        String entYear = request.getParameter("ent_year");

        // クラス番号
        String classNum = request.getParameter("class_num");

        // 科目コード
        String subjectCd = request.getParameter("subject_cd");

        // 学生番号
        String no = request.getParameter("no");

        // 点数（文字列）
        String pointStr = request.getParameter("point");

        // テスト回数（文字列）
        String countStr = request.getParameter("count");

        // DB接続用
        Connection con = null;

        // SQL実行用
        PreparedStatement st = null;

        try {

            // =========================
            // 文字列 → int型へ変換
            // =========================

            // 点数
            int point = Integer.parseInt(pointStr);

            // テスト回数
            int count = Integer.parseInt(countStr);

            // =========================
            // DB接続
            // =========================

            // Daoクラス生成
            Dao dao = new Dao();

            // DBへ接続
            con = dao.getConnection();

            // =========================
            // SQL作成
            // =========================

            String sql =
                    "insert into test "
                  + "(student_no, subject_cd, school_cd, no, point, class_num) "
                  + "values (?, ?, ?, ?, ?, ?)";

            // SQL準備
            st = con.prepareStatement(sql);

            // =========================
            // SQLの ? に値を設定
            // =========================

            // 学生番号
            st.setString(1, no);

            // 科目コード
            st.setString(2, subjectCd);

            // 学校コード（固定値）
            st.setString(3, "oom");

            // テスト回数
            st.setInt(4, count);

            // 点数
            st.setInt(5, point);

            // クラス番号
            st.setString(6, classNum);

            // =========================
            // SQL実行
            // =========================

            int result = st.executeUpdate();

            // =========================
            // 実行結果判定
            // =========================

            if (result > 0) {

                // 登録成功
                request.setAttribute("message", "成績登録が完了しました。");

            } else {

                // 登録失敗
                request.setAttribute("message", "成績登録に失敗しました。");
            }

            // =========================
            // 完了画面へ遷移
            // =========================

            request.getRequestDispatcher("test_regist_done.jsp")
                   .forward(request, response);

        } catch (Exception e) {

            // エラー内容をコンソールへ表示
            e.printStackTrace();

            // エラーメッセージ設定
            request.setAttribute("message", "エラーが発生しました。");

            // 登録画面へ戻る
            request.getRequestDispatcher("test_regist.jsp")
                   .forward(request, response);

        } finally {

            // =========================
            // DBリソース解放
            // =========================

            try {

                // PreparedStatementを閉じる
                if (st != null) {
                    st.close();
                }

                // Connectionを閉じる
                if (con != null) {
                    con.close();
                }

            } catch (Exception e) {

                // クローズ時エラー
                e.printStackTrace();
            }
        }
    }
}