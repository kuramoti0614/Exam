package scoremanager.main;

import java.util.List;

import DAO.SubjectDao; // 科目DAO
import bean.Subject;    // 科目Bean
import bean.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションからユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");

        // 1. ローカル変数の初期化
        SubjectDao subjectDao = new SubjectDao(); 
        
        // 2. DBからデータ取得
        // ログイン講師の所属する学校の科目一覧を取得
        List<Subject> subjects = subjectDao.filter(teacher.getSchool());

        // 3. レスポンス値をセット
        // JSPで表示するために科目リストをリクエスト属性に格納
        req.setAttribute("subjects", subjects);

        // 4. JSPへフォワード
        // フォワード先を科目一覧用のJSP（subject_list.jspなど）に変更
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}