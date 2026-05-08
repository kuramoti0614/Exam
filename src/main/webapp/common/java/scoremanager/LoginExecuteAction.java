package scoremanager;
// ログイン処理を含む画面制御用パッケージ
// scoremanager アプリケーションの入り口に近い部分

import java.util.ArrayList;
// エラーメッセージを複数管理するためのList実装クラス
import java.util.List;
// Listインタフェース（順序付き・重複可）

import DAO.TeacherDao;
// 教員情報を操作するDAOクラス
import bean.Teacher;
// 教員情報を保持するJavaBean
import jakarta.servlet.http.HttpServletRequest;
// フォーム入力など、リクエスト情報を扱うクラス
import jakarta.servlet.http.HttpServletResponse;
// 画面遷移（forward / redirect）などレスポンス制御用
import jakarta.servlet.http.HttpSession;
// セッション管理用（ログイン情報保持）
import tool.Action;
// 自作フレームワークのActionクラス
// execute()をオーバーライドして使用

public class LoginExecuteAction extends Action {
    // ログインフォーム送信後に呼び出されるActionクラス

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // ログイン処理のメインメソッド
        // req : 入力されたID・パスワードなど
        // res : 画面遷移制御

        // --------------------------------------------------
        // ローカル変数の宣言 1
        // --------------------------------------------------
        String url = "";
        // 遷移先URLを格納する変数
        // ※ 成功時・失敗時で中身が変わる

        String id = "";
        // フォームから受け取る教員ID

        String password = "";
        // フォームから受け取るパスワード

        TeacherDao teacherDao = new TeacherDao();
        // 教員テーブル操作用DAO

        Teacher teacher = null;
        // 認証結果を格納するTeacherオブジェクト
        // 認証失敗時は null のまま

        // --------------------------------------------------
        // リクエストパラメータの取得 2
        // --------------------------------------------------
        id = req.getParameter("id");
        // ログインフォームで入力された教員IDを取得

        password = req.getParameter("password");
        // ログインフォームで入力されたパスワードを取得

        // --------------------------------------------------
        // DBからデータ取得 3
        // --------------------------------------------------
        teacher = teacherDao.login(id, password);
        // TeacherDaoのloginメソッドを使って認証処理を実行
        // 成功 → Teacherインスタンス
        // 失敗 → null

        // --------------------------------------------------
        // ビジネスロジック 4 ～ フォワード 7
        // --------------------------------------------------
        // 認証結果によって処理を分岐
        if (teacher != null) {
            // ------------------------------
            // ▼ 認証成功の場合
            // ------------------------------

            // セッションを取得（存在しなければ新規作成）
            HttpSession session = req.getSession(true);

            // 認証済みフラグを true に設定
            // ※ フィルターや他画面でログイン判定に使用される想定
            teacher.setAuthenticated(true);

            // セッションにログイン済み教員情報を保存
            session.setAttribute("user", teacher);
            // 他のActionから session.getAttribute("user") で参照可能

            // メニュー画面へリダイレクト
            url = "main/Menu.action";
            // redirect を使うことでURLも変更される

            res.sendRedirect(url);
            // クライアントに再リクエストを指示（PRGパターン）

        } else {
            // ------------------------------
            // ▼ 認証失敗の場合
            // ------------------------------

            // エラーメッセージを格納するListを作成
            List<String> errors = new ArrayList<>();

            // 表示用エラーメッセージを追加
            errors.add("IDまたはパスワードが確認できませんでした");

            // JSPで表示できるようにリクエストへセット
            req.setAttribute("errors", errors);

            // 入力された教員IDを再表示用に保持
            req.setAttribute("id", id);

            // ログイン画面に戻す（forward）
            url = "login.jsp";
            req.getRequestDispatcher(url).forward(req, res);
            // forwardなので request に入れたエラー情報が保持される
        }
    }
}
