package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("login");

        String cd = req.getParameter("cd");
        String name = req.getParameter("subjectName");

        // Error checking for empty fields
        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("error", "科目名を入力してください。");
            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
            return;
        }

        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setSchool_CD(teacher.getSchool_cd());
        subject.setName(name);

        SubjectDao subjectDao = new SubjectDao();
        boolean result = subjectDao.update(subject);

        if (result) {
            req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
        } else {
            req.setAttribute("error", "科目の変更に失敗しました。");
            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
        }
    }
}
