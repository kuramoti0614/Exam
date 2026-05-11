package scoremanager.main;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(
            HttpServletRequest req,
            HttpServletResponse res)
            throws Exception {

        RequestDispatcher rd =
            req.getRequestDispatcher(
                "test_list.jsp");

        rd.forward(req, res);
    }
}