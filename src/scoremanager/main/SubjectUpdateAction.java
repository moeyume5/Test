package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cd = request.getParameter("cd");
        String school_cd = request.getParameter("school_cd");

        SubjectDao dao = new SubjectDao();
        Subject subject = dao.get(cd, school_cd);
        request.setAttribute("subject", subject);
        request.getRequestDispatcher("subject_update.jsp").forward(request, response);
    }
}
