package scoremanager.main;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import DAO.ClassNumDao;
import DAO.SubjectDao;
import DAO.TestListSubjectDao;
import bean.ClassNum;
import bean.School;
import bean.Subject;
import bean.TestListSubject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(
            HttpServletRequest req,
            HttpServletResponse res) throws Exception {

        String schoolCd = "oom";

        // ================= 初期表示用データ（必須） =================
        // 入学年度
        Set<Integer> entYearSet = new TreeSet<>();
        for (int y = 2019; y <= 2026; y++) {
            entYearSet.add(y);
        }

        // クラス
        ClassNumDao classNumDao = new ClassNumDao();
        List<ClassNum> classList = classNumDao.filterBySchool(schoolCd);
        Set<String> classNumSet = new TreeSet<>();
        for (ClassNum c : classList) {
            classNumSet.add(c.getClassNum());
        }

        // 科目
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filterBySchool(schoolCd);

        req.setAttribute("entYearSet", entYearSet);
        req.setAttribute("classNumSet", classNumSet);
        req.setAttribute("subjectList", subjectList);

        // ================= 検索条件取得 =================
        String entYearStr = req.getParameter("ent_year");
        String classNum   = req.getParameter("class_num");
        String subjectCd  = req.getParameter("subject_cd");

        // 未入力チェック
        if (entYearStr == null || entYearStr.isEmpty()
                || classNum == null || classNum.isEmpty()
                || subjectCd == null || subjectCd.isEmpty()) {

            req.setAttribute(
                "errorMessage",
                "入学年度・クラス・科目を選択してください"
            );

            req.getRequestDispatcher("test_list_subject.jsp")
               .forward(req, res);
            return;
        }

        int entYear = Integer.parseInt(entYearStr);

        // ================= 成績検索 =================
        Subject subject = new Subject();
        subject.setCd(subjectCd);

        School school = new School();
        school.setCd(schoolCd);

        TestListSubjectDao dao = new TestListSubjectDao();
        List<TestListSubject> testList =
            dao.filter(entYear, classNum, subject, school);

        req.setAttribute("testList", testList);
        req.setAttribute("subject", subject);

        // ================= 同じ画面に戻す =================
        req.getRequestDispatcher("test_list.jsp")
           .forward(req, res);
    }
}