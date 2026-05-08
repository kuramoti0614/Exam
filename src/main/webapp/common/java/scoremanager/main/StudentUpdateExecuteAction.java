package scoremanager.main;
// 学生管理機能（scoremanager）のメイン処理用パッケージ
// Actionクラス（画面処理）を配置する場所

import DAO.StudentDao;
// studentテーブルを操作するDAOクラス
import bean.Student;
// 学生情報を保持するJavaBean
import bean.Teacher;
// ログイン中教員情報を保持するJavaBean
import jakarta.servlet.http.HttpServletRequest;
// フォーム入力などリクエスト情報を扱うクラス
import jakarta.servlet.http.HttpServletResponse;
// 画面遷移（forward / redirect）用レスポンスクラス
import jakarta.servlet.http.HttpSession;
// セッション管理用（ログイン情報保持）
import tool.Action;
// 自作フレームワークのActionクラス
// execute() をオーバーライドして使用

public class StudentUpdateExecuteAction extends Action {
    // 学生情報「更新処理」を行うActionクラス
    // 更新フォーム送信後に呼び出される

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 更新処理のメインメソッド
        // req : 入力フォームから送られたデータ
        // res : 画面遷移制御

        // ==================================================
        // セッションからログイン教員情報を取得
        // ==================================================
        HttpSession session = req.getSession();
        // 現在のセッションを取得
        // ログイン済み教員情報を参照するため

        Teacher teacher = (Teacher) session.getAttribute("user");
        // セッションに保存されている教員情報を取得
        // update時は「どの学校の学生か」を判定するため必須

        // ==================================================
        // リクエストパラメータの取得
        // ==================================================
        String no = req.getParameter("no");
        // 学生番号（どの学生を更新するかを特定するキー）

        String name = req.getParameter("name");
        // 更新後の氏名

        int entYear = Integer.parseInt(req.getParameter("ent_year"));
        // 入学年度
        // JSP側で数値が送られてくる前提

        String classNum = req.getParameter("class_num");
        // クラス番号

        boolean attend = req.getParameter("attend") != null;
        // 在学フラグ
        // チェックボックスはチェックされている時のみ値が送られる
        // → nullでなければ true、nullなら false

        // ==================================================
        // Studentオブジェクトを生成し、更新内容をセット
        // ==================================================
        Student student = new Student();
        // 更新用のStudentインスタンスを生成

        student.setNo(no);
        // 学生番号セット（WHERE句に使われる）

        student.setName(name);
        // 氏名セット

        student.setEntYear(entYear);
        // 入学年度セット

        student.setClassNum(classNum);
        // クラス番号セット

        student.setAttend(attend);
        // 在学フラグセット

        student.setSchool(teacher.getSchool());
        // ログイン教員の学校情報をセット
        // → 他校の学生を更新できないようにするセキュリティ対策

        // ==================================================
        // DAOを使って更新処理を実行
        // ==================================================
        StudentDao dao = new StudentDao();
        // StudentDao生成（DB操作担当）

        dao.update(student);
        // UPDATE文を実行
        // 条件：no + school_cd
        // 内容：name, ent_year, class_num, is_attend

        // ==================================================
        // JSPで利用できるようにリクエストへセット
        // ==================================================
        req.setAttribute("student", student);
        // 更新後の学生情報をJSP側で表示するため

        // ==================================================
        // 更新完了画面へフォワード
        // ==================================================
        req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
        // 更新完了画面へ遷移
    }
}
