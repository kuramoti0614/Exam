package scoremanager.main;

import DAO.SubjectDao;
import bean.Subject;
import bean.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {
    // 科目情報「更新処理」を行うActionクラス

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ==================================================
        // セッションからログイン教員情報を取得
        // ==================================================
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ==================================================
        // リクエストパラメータの取得
        // ==================================================
        // 科目コード（更新のキー：cd）
        String cd = req.getParameter("cd");
        // 更新後の科目名（name）
        String name = req.getParameter("name");

        // ==================================================
        // Subjectオブジェクトを生成し、更新内容をセット
        // ==================================================
        Subject subject = new Subject();

        // 科目コードをセット（WHERE句に使用）
        subject.setCd(cd);
        // 科目名をセット
        subject.setName(name);
        // ログイン教員の学校情報をセット
        subject.setSchool(teacher.getSchool());

        // ==================================================
        // DAOを使って更新処理を実行
        // ==================================================
        SubjectDao dao = new SubjectDao();

        // UPDATE文を実行
        // 戻り値が必要な設計（成否判定など）の場合は適宜調整してください
        dao.save(subject); 
        // ※DAOのメソッド名がsaveかupdateかは、お使いのDAOの定義に合わせてください

        // ==================================================
        // 更新完了画面へフォワード
        // ==================================================
        // 更新結果を表示するためにリクエストにセット
        req.setAttribute("subject", subject);

        // 科目更新完了画面へ遷移
        req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
    }
}