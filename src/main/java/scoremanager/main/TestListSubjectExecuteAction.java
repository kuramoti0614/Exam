package scoremanager.main;

import java.util.List;

import DAO.TestListSubjectDao;
import bean.School;
import bean.Subject;
import bean.TestListSubject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(
            HttpServletRequest req,
            HttpServletResponse res)
            throws Exception {

        int entYear =
            Integer.parseInt(req.getParameter("ent_year"));
        String classNum =
            req.getParameter("class_num");
        String subjectCd =
            req.getParameter("subject_cd");
        String schoolCd =
            req.getParameter("school_cd");

        Subject subject = new Subject();
        subject.setCd(subjectCd);

        School school = new School();
        school.setCd(schoolCd);

        TestListSubjectDao dao =
            new TestListSubjectDao();

        List<TestListSubject> list =
            dao.filter(entYear, classNum, subject, school);

        req.setAttribute("list", list);

        RequestDispatcher rd =
            req.getRequestDispatcher(
                "/WEB-INF/jsp/test_list_subject.jsp");

        rd.forward(req, res);
    }
}