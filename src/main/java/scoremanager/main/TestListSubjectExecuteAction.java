package scoremanager.main;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import DAO.ClassNumDao;
import DAO.SubjectDao;
import DAO.TestListSubjectDao;
import bean.ClassNum;
import bean.School;
import bean.Student;
import bean.Subject;
import bean.TestListSubject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req,
                        HttpServletResponse res) throws Exception {

        // ✅ school取得（できればsession）
        School school = new School();
        school.setCd("oom");
        String schoolCd = school.getCd();

        /* ========= 初期表示 ========= */

        Set<Integer> entYearSet = new TreeSet<>();
        for (int y = 2019; y <= 2026; y++) {
            entYearSet.add(y);
        }

        ClassNumDao classNumDao = new ClassNumDao();
        List<ClassNum> classList = classNumDao.filterBySchool(schoolCd);

        Set<String> classNumSet = new TreeSet<>();
        for (ClassNum c : classList) {
            classNumSet.add(c.getClassNum());
        }

        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filterBySchool(schoolCd);

        req.setAttribute("entYearSet", entYearSet);
        req.setAttribute("classNumSet", classNumSet);
        req.setAttribute("subjectList", subjectList);

        /* ========= パラメータ ========= */

        String entYearStr = req.getParameter("entYear");
        String classNum   = req.getParameter("classNum");
        String subjectCd  = req.getParameter("subjectCd");

        // ✅ バリデーション
        if (entYearStr == null || entYearStr.isEmpty()
         || classNum   == null || classNum.isEmpty()
         || subjectCd  == null || subjectCd.isEmpty()) {

            req.setAttribute("errorMessage",
                    "入学年度・クラス・科目を選択してください");

            req.getRequestDispatcher("test_list_subject.jsp")
               .forward(req, res);
            return;
        }

        int entYear = Integer.parseInt(entYearStr);

        /* ========= 検索 ========= */

        Subject subject = subjectDao.get(subjectCd, school);

        TestListSubjectDao dao = new TestListSubjectDao();

        List<TestListSubject> testList =
                dao.filter(entYear, classNum, subject, school);
        
     // ▼ 追加
        Student student = null;

        // 1件でもあれば先頭から作る
        if (!testList.isEmpty()) {
            TestListSubject first = testList.get(0);

            student = new Student();
            student.setNo(first.getStudentNo());
            student.setName(first.getStudentName());
        }
        
        
        

        // ✅ 属性セット
        req.setAttribute("testList", testList);
        req.setAttribute("subject", subject);
        req.setAttribute("student", student);

        // ✅ 画面
        req.getRequestDispatcher("test_list_subject.jsp")
           .forward(req, res);
    }
}