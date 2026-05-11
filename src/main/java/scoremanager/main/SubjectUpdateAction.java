package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import DAO.SubjectDao;
import bean.Subject;
import bean.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ------------------------------------------------------------
        // ■ ログイン中の教員情報をセッションから取得
        // ------------------------------------------------------------
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ------------------------------------------------------------
        // ■ 更新対象の科目コードをリクエストパラメータから取得
        //    （subject_list.jsp などから ?cd=XXXX として渡される想定）
        // ------------------------------------------------------------
        String cd = req.getParameter("cd");

        // ------------------------------------------------------------
        // ■ DAO を使って科目情報を 1 件取得
        //    （学校コード + 科目コードで検索）
        // ------------------------------------------------------------
        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(cd, teacher.getSchool());

        // ------------------------------------------------------------
        // ■ JSP に渡すデータをリクエスト属性にセット
        // ------------------------------------------------------------
        if (subject != null) {
            // 科目が見つかった場合
            req.setAttribute("subject", subject);
        } else {
            // 万が一科目が取得できなかった場合のエラー処理
            Map<String, String> errors = new HashMap<>();
            errors.put("error", "指定された科目が見つかりません。");
            req.setAttribute("errors", errors);
        }

        // ------------------------------------------------------------
        // ■ subject_update.jsp へフォワード
        // ------------------------------------------------------------
        req.getRequestDispatcher("subject_update.jsp").forward(req, res);
    }
}