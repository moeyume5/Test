package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("login");

        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setSchool_CD(teacher.getSchool_cd());
        subject.setName(name);

        SubjectDao subjectDao = new SubjectDao();
        boolean result = subjectDao.save(subject);

        if (result) {
            req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
        } else {
            req.setAttribute("error", "科目の登録に失敗しました。");
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
        }
    }
}
