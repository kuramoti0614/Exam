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

        // --- 学生番号 未入力チェック ---
        if (studentNo == null || studentNo.isEmpty()) {
            req.setAttribute("errorMessage", "このフィールドを入力してください");

            RequestDispatcher rd =
                req.getRequestDispatcher(
                    "test_list_student.jsp");
            rd.forward(req, res);
            return;
        }

        // --- Student Bean 作成 ---
        Student student = new Student();
        student.setNo(studentNo);

        // --- DAO 呼び出し ---
        TestListStudentDao dao = new TestListStudentDao();
        List<TestListStudent> list = dao.filter(student);

        // --- 成績情報が存在しない場合 ---
        if (list == null || list.isEmpty()) {
            req.setAttribute("errorMessage", "成績情報が存在しませんでした");
        }

        // --- リクエストスコープへ保存 ---
        req.setAttribute("testlist", list);
        req.setAttribute("student", student);

        // --- JSP へフォワード ---
        RequestDispatcher rd =
            req.getRequestDispatcher(
                "test_list.jsp");
        rd.forward(req, res);
    }
}