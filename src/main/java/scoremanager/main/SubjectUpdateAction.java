package scoremanager.main;

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

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String cd = req.getParameter("cd");

        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(cd, teacher.getSchool());

        // ============================
        // 存在チェック（重要）
        // ============================
        if (subject == null) {

            // ダミー作成（画面崩壊防止）
            Subject dummy = new Subject();
            dummy.setCd(cd);
            dummy.setName("");

            req.setAttribute("subject", dummy);

            req.setAttribute("errorMessage",
                    "科目が存在していません");

        } else {

            req.setAttribute("subject", subject);
        }

        req.getRequestDispatcher("subject_update.jsp")
           .forward(req, res);
    }
}
