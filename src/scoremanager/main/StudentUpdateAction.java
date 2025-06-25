package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("login");

        String studentNo = req.getParameter("no");

        StudentDao sDao = new StudentDao();
        Student student = sDao.findByNo(studentNo);

        if (student == null) {
            req.setAttribute("error", "指定された学生情報が存在しません。");
            req.getRequestDispatcher("student_list.jsp").forward(req, res);
            return;
        }

        req.setAttribute("ent_year", student.getEntYear());
        req.setAttribute("no", student.getNo());
        req.setAttribute("name", student.getName());
        req.setAttribute("class_num_set", new ClassNumDao().filter(teacher.getSchool_cd()));
        req.setAttribute("classNum", student.getClassNum());
        req.setAttribute("isAttend", student.isAttend() ? "true" : "false");

        req.getRequestDispatcher("student_update.jsp").forward(req, res);
    }
}
