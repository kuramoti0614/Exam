package scoremanager.main;

import DAO.SubjectDao;
import bean.Subject;
import bean.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession httpSession = req.getSession();

        Teacher teacher = (Teacher) httpSession.getAttribute("user");

        String cd = req.getParameter("cd");

        // Subjectオブジェクト作成
        Subject subject = new Subject();

        subject.setCd(cd);
        subject.setSchool(teacher.getSchool());

        SubjectDao dao = new SubjectDao();

        // Subjectを渡す
        dao.delete(subject);

        req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
    }
}