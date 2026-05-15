package scoremanager.main;

import java.sql.Connection;
import java.sql.PreparedStatement;

import DAO.Dao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        String studentNo = request.getParameter("student_no");
        String subjectCd = request.getParameter("subject_cd");
        String classNum  = request.getParameter("class_num");
        int count        = Integer.parseInt(request.getParameter("count"));
        int point        = Integer.parseInt(request.getParameter("point"));

        try (
            Connection con = new Dao().getConnection();
            PreparedStatement st = con.prepareStatement(
                "INSERT INTO test " +
                "(student_no, subject_cd, school_cd, no, point, class_num) " +
                "VALUES (?, ?, ?, ?, ?, ?)"
            )
        ) {
            st.setString(1, studentNo);
            st.setString(2, subjectCd);
            st.setString(3, "oom");
            st.setInt(4, count);
            st.setInt(5, point);
            st.setString(6, classNum);

            st.executeUpdate();
        }

        request.getRequestDispatcher(
            "test_regist_done.jsp"
        ).forward(request, response);
    }
}