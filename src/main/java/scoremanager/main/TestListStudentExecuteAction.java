package scoremanager.main;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import DAO.ClassNumDao;
import DAO.SubjectDao;
import DAO.TestListStudentDao;
import bean.ClassNum;
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

        // パラメータ取得
        String studentNo = req.getParameter("studentNo");
        String entYearStr = req.getParameter("entYear");
        String classNum   = req.getParameter("classNum");
        String subjectCd  = req.getParameter("subjectCd");

        // ===== School =====
        School school = new School();
        school.setCd("oom");
        String schoolCd = school.getCd();   // ★ schoolCd を定義

        // ===== プルダウン用データを常に取得 =====
        ClassNumDao classNumDao = new ClassNumDao();
        List<ClassNum> classList = classNumDao.filterBySchool(schoolCd);

        Set<String> classNumSet = new TreeSet<>();
        for (ClassNum c : classList) {
            classNumSet.add(c.getClassNum());
        }

        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filterBySchool(schoolCd);

        Set<Integer> entYearSet = new TreeSet<>();
        for (int year = 2019; year <= 2026; year++) {
            entYearSet.add(year);
        }

        // JSP に渡す
        req.setAttribute("classNumSet", classNumSet);
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("entYearSet", entYearSet);

        // session の subject を取得（保持された科目）
        Subject subject = (Subject) req.getSession().getAttribute("subject");
        req.setAttribute("subject", subject);

        // ===== 学生番号未入力チェック =====
        if (studentNo == null || studentNo.isEmpty()) {
            req.setAttribute("errorMessage", "学生番号を入力してください");
            req.setAttribute("testList", null);
            req.setAttribute("student", null);

            req.getRequestDispatcher("test_list_student.jsp")
               .forward(req, res);
            return;
        }

        // ===== Student 作成 =====
        Student student = new Student();
        student.setNo(studentNo);
        student.setSchool(school);

        // 入学年度・クラスをセット
        if (entYearStr != null && !entYearStr.isEmpty()) {
            student.setEntYear(Integer.parseInt(entYearStr));
        }
        student.setClassNum(classNum);

        // ===== DAO =====
        TestListStudentDao dao = new TestListStudentDao();
        List<TestListStudent> list = dao.filter(student, subjectCd);

        // データなし
        if (list == null || list.isEmpty()) {
            req.setAttribute("errorMessage", "成績情報が存在しませんでした");
            req.setAttribute("testList", null);
            req.setAttribute("student", student);

            req.getRequestDispatcher("test_list_student.jsp")
               .forward(req, res);
            return;
        }

        // ===== 正常時 =====
        req.setAttribute("student", student);
        req.setAttribute("testList", list);

        req.getRequestDispatcher("test_list_student.jsp")
           .forward(req, res);
    }
}
