package scoremanager.main;

import java.util.List;

import DAO.SubjectDao;
import DAO.TestListStudentDao;
import bean.School;
import bean.Student;
import bean.Subject;
import bean.TestListStudent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req,
                        HttpServletResponse res) throws Exception {

        // ✅ パラメータ取得
        String studentNo = req.getParameter("studentNo");

        // ✅ 未入力チェック
        if (studentNo == null || studentNo.isEmpty()) {
            req.setAttribute("errorMessage", "学生番号を入力してください");

            req.getRequestDispatcher("test_list_student.jsp")
               .forward(req, res);
            return;
        }

        // ✅ Student作成
        Student student = new Student();
        student.setNo(studentNo);

        // ✅ DAO
        TestListStudentDao dao = new TestListStudentDao();
        List<TestListStudent> list = dao.filter(student);

        // ✅ データなし
        if (list == null || list.isEmpty()) {
            req.setAttribute("errorMessage", "成績情報が存在しませんでした");
            
            
            
            
            
        }
        
        
        Subject subject = null;

        if (list != null && !list.isEmpty()) {

            // 1件目の科目コード取得
            String subjectCd = list.get(0).getSubjectCd();

            SubjectDao subjectDao = new SubjectDao();

            School school = new School();
            school.setCd("oom");

            subject = subjectDao.get(subjectCd, school);
        }

        // ✅ 属性セット（統一）
        req.setAttribute("student", student);
        req.setAttribute("testList", list);
        req.setAttribute("subject", subject);

        // ✅ 画面遷移
        req.getRequestDispatcher("test_list_student.jsp")
           .forward(req, res);
    }
}
