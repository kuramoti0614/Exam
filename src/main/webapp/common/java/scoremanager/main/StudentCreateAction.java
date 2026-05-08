package scoremanager.main;
// scoremanagerアプリケーションのメイン処理用パッケージ
// Actionクラス（画面遷移・リクエスト処理）をまとめる

import java.time.LocalDate;
// 日付（年・月・日）を扱うためのクラス（現在日時取得用）
import java.util.ArrayList;
// 可変長リスト（順序あり・重複可）を扱うクラス
import java.util.List;
// Listインタフェース（複数データを扱うため）

import DAO.ClassNumDao;
// クラス番号をDBから取得するためのDAO
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

public class StudentCreateAction extends Action {
    // 学生登録画面（student_create.jsp）表示用のActionクラス

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
        // ローカル変数の指定 1
        // ==================================================
        ClassNumDao classNumDao = new ClassNumDao();
        // クラス番号を取得するためのDAOを生成

        LocalDate todaysDate = LocalDate.now();
        // 現在の日付を取得（例：2026-04-24）

        int year = todaysDate.getYear();
        // 現在の「年」だけを取得（例：2026）

        // ==================================================
        // リクエストパラメータ取得 2
        // ==================================================
        // この画面は「入力フォーム表示のみ」なので
        // フォームから受け取る値はまだ存在しない
        // → 処理なし

        // ==================================================
        // DBからデータ取得 3
        // ==================================================
        // ログイン中の教員が所属する学校コードを使って
        // クラス番号の一覧をDBから取得
        List<String> list = classNumDao.filter(teacher.getSchool());
        // 例：["A", "B", "C"]

        // ==================================================
        // ビジネスロジック 4
        // ==================================================
        // 入学年度の選択肢を作成する処理

        List<Integer> entYearSet = new ArrayList<>();
        // 入学年度を格納するリストを初期化

        // 現在の年を基準に「10年前〜10年後」までをリストに追加
        // 例：2016 ～ 2036
        for (int i = year - 10; i < year + 11; i++) {
            entYearSet.add(i);
        }
        // → プルダウンで選択できる入学年度用データ完成

        // ==================================================
        // レスポンス値の設定 6
        // ==================================================
        // JSPで使用できるようにリクエストへデータを保存

        req.setAttribute("class_num_set", list);
        // クラス番号一覧をリクエストスコープに格納
        // JSP側では ${class_num_set} で参照可能

        req.setAttribute("ent_year_set", entYearSet);
        // 入学年度一覧をリクエストスコープに格納

        // ==================================================
        // JSPへフォワード 7
        // ==================================================
        req.getRequestDispatcher("student_create.jsp").forward(req, res);
        // student_create.jsp に画面遷移
        // request にセットしたデータをそのまま渡す
    }
}