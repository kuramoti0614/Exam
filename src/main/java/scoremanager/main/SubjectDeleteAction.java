package scoremanager.main;
 
import DAO.SubjectDao;
import bean.Subject;
import bean.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
 
public class SubjectDeleteAction extends Action{
 
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		SubjectDao dao = new SubjectDao();
		String cd = req.getParameter("cd");
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		Subject subject = dao.get(cd, teacher.getSchool());
		req.setAttribute("subject", subject);
		req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
		// TODO 自動生成されたメソッド・スタブ
	}


 
}