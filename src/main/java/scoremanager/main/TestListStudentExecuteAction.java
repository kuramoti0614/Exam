package scoremanager.main;

import java.util.List;

import DAO.TestListStudentDao;
import bean.Student;
import bean.TestListStudent;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(
            HttpServletRequest req,
            HttpServletResponse res)
            throws Exception {

        // --- リクエストパラメータ取得 ---
        String studentNo = req.getParameter("student_no");

        // --- Student Bean 作成 ---
        Student student = new Student();
        student.setNo(studentNo);

        // --- DAO 呼び出し ---
        TestListStudentDao dao = new TestListStudentDao();
        List<TestListStudent> list = dao.filter(student);

        // --- リクエストスコープへ保存 ---
        req.setAttribute("list", list);
        req.setAttribute("studentNo", studentNo);

        // --- JSP へフォワード ---
        RequestDispatcher rd =
            req.getRequestDispatcher(
                "/WEB-INF/jsp/test_list_student.jsp");
        rd.forward(req, res);
    }
}