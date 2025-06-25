package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class SubjectCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("login");

        ClassNumDao cNumDao = new ClassNumDao();
        List<String> classNumSet = cNumDao.filter(teacher.getSchool_cd());

        req.setAttribute("class_num_set", classNumSet);

        req.getRequestDispatcher("subject_create.jsp").forward(req, res);
    }
}
