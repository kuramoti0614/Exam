package scoremanager.main;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import DAO.ClassNumDao;          // ★ 正解
import DAO.SubjectDao;
import bean.ClassNum;            // ★ 正解
import bean.Subject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(
            HttpServletRequest req,
            HttpServletResponse res) throws Exception {

        // ===== ログインユーザーの学校コード（仮）=====
        // 実際はセッションから取得
        String schoolCd = "oom";

        // ===== クラス番号一覧取得 =====
        ClassNumDao classNumDao = new ClassNumDao();
        List<ClassNum> classList = classNumDao.filterBySchool(schoolCd);

        // クラス番号だけを Set に格納
        Set<String> classNumSet = new TreeSet<>();
        for (ClassNum c : classList) {
            classNumSet.add(c.getClassNum());
        }

        // ===== 科目一覧取得 =====
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectList = subjectDao.filterBySchool(schoolCd);
        // ===== 入学年度リスト作成 =====
        Set<Integer> entYearSet = new TreeSet<>();
        for (int year = 2019; year <= 2026; year++) {
            entYearSet.add(year);
        }

        // ===== JSP に渡す =====
        req.setAttribute("classNumSet", classNumSet);
        req.setAttribute("subjectList", subjectList);
        req.setAttribute("entYearSet", entYearSet);

        // ===== 画面遷移 =====
        RequestDispatcher rd =
            req.getRequestDispatcher("test_list.jsp");
        rd.forward(req, res);
    }
}