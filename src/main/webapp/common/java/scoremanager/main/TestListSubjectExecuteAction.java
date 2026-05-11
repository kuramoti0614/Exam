package scoremanager.main;

import java.util.List;

import DAO.SubjectDao;
import bean.School;
import bean.Subject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TestListSubjectExecuteAction {

    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションから学校情報を取得
        School school = (School) req.getSession().getAttribute("school");

        // DAO 呼び出し
        SubjectDao dao = new SubjectDao();
        List<Subject> list = dao.filter(school);

        // JSP に渡す
        req.setAttribute("subject_list", list);

        // 表示画面へ
        req.getRequestDispatcher("/subject_list.jsp")
           .forward(req, res);
    }
}