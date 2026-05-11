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

        // --- リクエストパラメータ取得 ---
        String entYearStr = req.getParameter("ent_year");
        String classNum   = req.getParameter("class_num");
        String subjectCd  = req.getParameter("subject_cd");
        String schoolCd   = req.getParameter("school_cd");

        // --- 未入力チェック ---
        if (entYearStr == null || entYearStr.isEmpty()
                || classNum == null || classNum.isEmpty()
                || subjectCd == null || subjectCd.isEmpty()) {

            req.setAttribute(
                "errorMessage",
                "入学年度とクラスと科目を選択してください"
            );

            RequestDispatcher rd =
                req.getRequestDispatcher(
                    "/WEB-INF/jsp/test_list_subject.jsp");
            rd.forward(req, res);
            return;
        }

        int entYear = Integer.parseInt(entYearStr);

        // --- Bean 作成 ---
        Subject subject = new Subject();
        subject.setCd(subjectCd);

        School school = new School();
        school.setCd(schoolCd);

        // --- DAO 呼び出し ---
        TestListSubjectDao dao = new TestListSubjectDao();
        List<TestListSubject> list =
            dao.filter(entYear, classNum, subject, school);

        // --- 該当学生情報なし ---
        if (list == null || list.isEmpty()) {
            req.setAttribute(
                "errorMessage",
                "学生情報が存在しませんでした"
            );
        }

        // --- リクエストスコープへ保存 ---
        req.setAttribute("list", list);
        req.setAttribute("entYear", entYearStr);
        req.setAttribute("classNum", classNum);
        req.setAttribute("subjectCd", subjectCd);

        // --- JSP へフォワード ---
        RequestDispatcher rd =
            req.getRequestDispatcher(
                "/WEB-INF/jsp/test_list_subject.jsp");
        rd.forward(req, res);
    }
}