package scoremanager.main;
// scoremanagerアプリケーションのメイン処理用パッケージ

import DAO.SubjectDao;
// 科目テーブル操作用DAO
import bean.Subject;
// 科目情報を保持するJavaBean
import bean.Teacher;
// ログイン中教員情報Bean
import jakarta.servlet.http.HttpServletRequest;
// リクエスト情報
import jakarta.servlet.http.HttpServletResponse;
// レスポンス情報
import jakarta.servlet.http.HttpSession;
// セッション情報
import tool.Action;
// 自作Actionクラス

public class SubjectCreateExecuteAction extends Action {
    // 科目登録実行用Actionクラス

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // --------------------------------------------------
        // セッション取得
        // --------------------------------------------------
        HttpSession session = req.getSession();

        Teacher teacher = (Teacher) session.getAttribute("user");
        // ログイン中教員情報取得

        // ==================================================
        // リクエストパラメータ取得
        // ==================================================
        String cd = req.getParameter("cd");
        // 科目コード

        String name = req.getParameter("name");
        // 科目名

        // ==================================================
        // 入力値チェック
        // ==================================================
        if (cd == null || cd.isEmpty() ||
            name == null || name.isEmpty()) {

            req.setAttribute("error", "科目コードと科目名を入力してください");

            req.getRequestDispatcher("subject_create.jsp")
               .forward(req, res);

            return;
        }

        // ==================================================
        // SubjectBeanへ値セット
        // ==================================================
        Subject subject = new Subject();

        subject.setCd(cd);
        // 科目コードセット

        subject.setName(name);
        // 科目名セット

        subject.setSchool(teacher.getSchool());
        // ログイン教員の学校情報セット

        // ==================================================
        // DB登録処理
        // ==================================================
        SubjectDao dao = new SubjectDao();

        dao.save(subject);
        // 科目情報をDBへ登録

        // ==================================================
        // レスポンス設定
        // ==================================================
        req.setAttribute("message", "科目登録が完了しました");

        req.setAttribute("subject", subject);

        // ==================================================
        // 完了画面へフォワード
        // ==================================================
        req.getRequestDispatcher("subject_create_done.jsp")
           .forward(req, res);
    }
}