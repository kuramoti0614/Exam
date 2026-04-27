package scoremanager.main;
// scoremanager アプリケーションのメイン処理用パッケージ
// 画面アクション（Actionクラス）をまとめる

import java.util.HashMap;
// key-value形式でデータを管理するためのMap実装クラス
import java.util.Map;
// エラーメッセージ格納用インタフェース

import DAO.StudentDao;
// studentテーブルを操作するDAO
import bean.Student;
// 学生情報を保持するJavaBean
import bean.Teacher;
// ログイン中の教員情報を保持するJavaBean
import jakarta.servlet.http.HttpServletRequest;
// リクエスト情報（フォーム入力値など）を取得するためのクラス
import jakarta.servlet.http.HttpServletResponse;
// レスポンス情報（画面遷移など）を制御するクラス
import jakarta.servlet.http.HttpSession;
// セッション（ログイン情報保持）を扱うクラス
import tool.Action;
// 自作フレームワークのActionクラス
// execute()メソッドをオーバーライドして使用する

public class StudentCreateExecuteAction extends Action {
    // 学生登録「実行処理」用のActionクラス
    // フォーム送信（POST）後に呼ばれる

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // フォーム送信時に呼ばれるメイン処理
        // req : ブラウザから送られたリクエスト
        // res : ブラウザへ返すレスポンス

        // ------------------------------------------------------------
        // ■ 1. ローカル変数の準備
        // ------------------------------------------------------------
        HttpSession session = req.getSession();
        // 現在のセッションを取得
        // ログイン情報を参照するために必要

        Teacher teacher = (Teacher)session.getAttribute("user");
        // セッションに保存されているログイン教員情報を取得
        // 型は Object なので Teacher にキャスト

        int ent_year = 0;
        // 入学年度（未選択時の判定用に初期値0）

        String student_no = "";
        // 学生番号

        String student_name = "";
        // 学生氏名

        String class_num = "";
        // クラス番号

        Student student = new Student();
        // 登録用 Student オブジェクト
        // 入力チェック後、問題なければ値をセットしてDB登録に使う

        StudentDao studentDao = new StudentDao();
        // studentテーブル操作用DAO

        Map<String, String> errors = new HashMap<>();
        // エラーメッセージ格納用Map
        // key : エラー識別用番号
        // value : 表示するエラーメッセージ

        // ------------------------------------------------------------
        // ■ 2. リクエストパラメータの取得
        // ------------------------------------------------------------
        ent_year = Integer.parseInt(req.getParameter("ent_year"));
        // 入学年度を取得
        // 使用側（JSP）で「未選択＝0」にしている前提

        student_no = req.getParameter("no");
        // 学生番号を取得

        student_name = req.getParameter("name");
        // 氏名を取得

        class_num = req.getParameter("class_num");
        // クラス番号を取得

        // ------------------------------------------------------------
        // ■ 3. DBからの取得はなし
        // ------------------------------------------------------------
        // 新規登録画面のため
        // 一覧表示などは行わない

        // ------------------------------------------------------------
        // ■ 4. ビジネスロジック（入力チェック & 登録処理）
        // ------------------------------------------------------------

        // ▼ 入学年度が未選択（0）の場合
        if (ent_year == 0) {
            // エラーメッセージをMapに追加
            errors.put("1", "入学年度を選択してください");

            // リクエストにエラー情報をセット
            req.setAttribute("errors", errors);

        } else {

            // ▼ 学生番号の重複チェック
            // 同じ学校内で同一学生番号が存在するか確認
            if (studentDao.get(student_no, teacher.getSchool()) != null) {

                // 既に存在する → エラー
                errors.put("2", "学生番号が重複しています");

                // エラー情報をリクエストへセット
                req.setAttribute("errors", errors);

            } else {
                // ▼ すべて問題なし → 登録処理へ進む

                student.setNo(student_no);
                // 学生番号セット

                student.setName(student_name);
                // 氏名セット

                student.setEntYear(ent_year);
                // 入学年度セット

                student.setClassNum(class_num);
                // クラス番号セット

                student.setAttend(true);
                // 新規登録時は在学扱いにする

                student.setSchool(teacher.getSchool());
                // ログイン教員の学校をセット
                // → 別学校への登録を防ぐ

                // ▼ DBに保存（INSERT）
                studentDao.save(student);
                // save() 内で INSERT or UPDATE を判定
                // 今回は新規なので INSERT が実行される
            }
        }

        // ------------------------------------------------------------
        // ■ 6. 入力値をリクエストにセット
        // ------------------------------------------------------------
        // エラー発生時、入力内容を再表示するため

        req.setAttribute("ent_year", ent_year);
        // 入学年度

        req.setAttribute("no", student_no);
        // 学生番号

        req.setAttribute("name", student_name);
        // 氏名

        req.setAttribute("class_num", class_num);
        // クラス番号

        // ------------------------------------------------------------
        // ■ 7. JSPへフォワード
        // ------------------------------------------------------------
        if (errors.isEmpty()) {
            // ▼ エラーが1つも無い場合

            // 登録完了画面へ遷移
            req.getRequestDispatcher("student_create_done.jsp")
               .forward(req, res);

        } else {
            // ▼ エラーがある場合

            // 入力画面へ戻す
            // ※ JSPではなく Action にフォワードすることで
            //    クラス一覧・年度一覧も再取得できる
            req.getRequestDispatcher("StudentCreate.action")
               .forward(req, res);
        }
    }
}
