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

// このサーブレットにアクセスするURLを設定
@WebServlet("/scoremanager/main/TestRegistExecuteAction")
public class TestRegistExecuteAction extends HttpServlet {

    // POSTリクエスト時に実行されるメソッド
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response
            ) throws ServletException, IOException {

        // リクエストの文字コードをUTF-8に設定
        request.setCharacterEncoding("UTF-8");

        // DB接続用オブジェクト
        Connection con = null;

        // SQL実行用オブジェクト
        PreparedStatement st = null;

        try {

            // =========================
            // フォームから送られた値を取得
            // =========================

            // 学生番号
            String studentNo = request.getParameter("student_no");

            // 科目コード
            String subjectCd = request.getParameter("subject_cd");

            // クラス番号
            String classNum = request.getParameter("class_num");

            // テスト回数（文字列）
            String countStr = request.getParameter("count");

            // 点数（文字列）
            String pointStr = request.getParameter("point");

            // =========================
            // 数値へ変換
            // =========================

            // テスト回数をint型へ変換
            int count = Integer.parseInt(countStr);

            // 点数をint型へ変換
            int point = Integer.parseInt(pointStr);

            // =========================
            // DB接続
            // =========================

            // Daoクラス生成
            Dao dao = new Dao();

            // DBへ接続
            con = dao.getConnection();

            // =========================
            // SQL文作成
            // =========================

            String sql =
                    "insert into test "
                  + "(student_no, subject_cd, school_cd, no, point, class_num) "
                  + "values (?, ?, ?, ?, ?, ?)";

            // SQLを準備
            st = con.prepareStatement(sql);

            // =========================
            // SQLの ? に値を設定
            // =========================

            // 学生番号
            st.setString(1, studentNo);

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
            // 登録結果判定
            // =========================

            if (result > 0) {

                // 登録成功
                request.setAttribute("message", "成績登録成功");

            } else {

                // 登録失敗
                request.setAttribute("message", "成績登録失敗");
            }

            // =========================
            // 完了画面へ遷移
            // =========================

            request.getRequestDispatcher("test_regist_done.jsp")
                   .forward(request, response);

        } catch (Exception e) {

            // エラー内容をコンソールへ出力
            e.printStackTrace();

            // エラーメッセージ設定
            request.setAttribute("message", "エラー発生");

            // 登録画面へ戻す
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