package scoremanager.main;

import java.sql.Connection;
import java.sql.PreparedStatement;

import DAO.Dao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public void execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        // =========================
        // フォームから値を取得
        // =========================
        String classNum  = request.getParameter("class_num");
        String subjectCd = request.getParameter("subject_cd");
        String studentNo = request.getParameter("no");
        String pointStr  = request.getParameter("point");
        String countStr  = request.getParameter("count");

        Connection con = null;
        PreparedStatement st = null;

        try {
            int point = Integer.parseInt(pointStr);
            int count = Integer.parseInt(countStr);

            con = new Dao().getConnection();

            String sql =
                "INSERT INTO test " +
                "(student_no, subject_cd, school_cd, no, point, class_num) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

            st = con.prepareStatement(sql);
            st.setString(1, studentNo);
            st.setString(2, subjectCd);
            st.setString(3, "oom");
            st.setInt(4, count);
            st.setInt(5, point);
            st.setString(6, classNum);

            int result = st.executeUpdate();

            if (result > 0) {
                request.setAttribute("message", "成績登録が完了しました。");
            } else {
                request.setAttribute("message", "成績登録に失敗しました。");
            }

            request.getRequestDispatcher(
                "test_regist_done.jsp"
            ).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "エラーが発生しました。");

            request.getRequestDispatcher(
                "test_regist.jsp"
            ).forward(request, response);

        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }
    }
}