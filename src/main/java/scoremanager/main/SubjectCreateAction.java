package scoremanager.main;
// scoremanagerアプリケーションのメイン処理用パッケージ
// Actionクラス（画面遷移・リクエスト処理）をまとめる

import bean.Teacher;
// ログイン中の教員情報を保持するJavaBean
import jakarta.servlet.http.HttpServletRequest;
// クライアント（ブラウザ）からのリクエスト情報を扱う
import jakarta.servlet.http.HttpServletResponse;
// クライアントへ返すレスポンス情報を扱う
import jakarta.servlet.http.HttpSession;
// セッション（ログイン情報などを保持）を扱う
import tool.Action;
// 自作フレームワークのActionクラス
// 各画面処理の共通親クラス

public class SubjectCreateAction extends Action {
    // 科目登録画面（subject_create.jsp）表示用のActionクラス

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 画面遷移時に必ず呼ばれるメイン処理
        // req : リクエスト情報
        // res : レスポンス情報

        // --------------------------------------------------
        // セッション取得
        // --------------------------------------------------
        HttpSession session = req.getSession();
        // 現在のセッションを取得（ログイン情報を参照するため）

        Teacher teacher = (Teacher) session.getAttribute("user");
        // セッションに保存されているログイン教員情報を取得
        // ログイン時に "user" という名前で保存されている前提

        // ==================================================
        // リクエストパラメータ取得
        // ==================================================
        // この画面は「入力フォーム表示のみ」なので
        // フォームから受け取る値はまだ存在しない
        // → 処理なし

        // ==================================================
        // DBからデータ取得
        // ==================================================
        // 科目登録画面では特に取得処理なし
        // 必要になった場合はDAOを使用して取得する

        // ==================================================
        // ビジネスロジック
        // ==================================================
        // 現時点では特別な処理なし

        // ==================================================
        // レスポンス値の設定
        // ==================================================
        // JSPで使用するデータがあればsetAttributeする

        req.setAttribute("teacher", teacher);
        // ログイン中教員情報をJSPへ渡す

        // ==================================================
        // JSPへフォワード
        // ==================================================
        req.getRequestDispatcher("subject_create.jsp").forward(req, res);
        // subject_create.jsp に画面遷移
        // request にセットしたデータをそのまま渡す
    }
}