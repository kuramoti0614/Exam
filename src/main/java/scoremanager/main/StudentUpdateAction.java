package scoremanager.main;

import java.util.Set;

import DAO.StudentDao;
import bean.Student;
import bean.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ------------------------------------------------------------
        // ■ ログイン中の教員情報をセッションから取得
        // ------------------------------------------------------------
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        // teacher.getSchool() で学校情報が取れる

        // ------------------------------------------------------------
        // ■ 更新対象の学生番号をリクエストパラメータから取得
        //    （student_list.jsp などから ?no=XXXX として渡される）
        // ------------------------------------------------------------
        String no = req.getParameter("no");

        // ------------------------------------------------------------
        // ■ DAO を使って学生情報を 1 件取得
        //    （学校コード + 学生番号で検索）
        // ------------------------------------------------------------
        StudentDao dao = new StudentDao();
        Student student = dao.get(no, teacher.getSchool());

        // ------------------------------------------------------------
        // ■ クラス番号一覧を取得
        //    student テーブルの class_num を DISTINCT で取得した Set
        //    → プルダウン（select）表示に使う
        // ------------------------------------------------------------
        Set<String> classNumSet = dao.getClassNumSet(teacher.getSchool());

        // ------------------------------------------------------------
        // ■ JSP に渡すデータをリクエスト属性にセット
        // ------------------------------------------------------------
        req.setAttribute("student", student);          // 編集対象の学生情報
        req.setAttribute("class_num_set", classNumSet); // クラス番号一覧
        req.setAttribute("class_num", student.getClassNum()); // 初期選択値

        // ------------------------------------------------------------
        // ■ student_update.jsp へフォワード
        // ------------------------------------------------------------
        req.getRequestDispatcher("student_update.jsp").forward(req, res);
    }
}
