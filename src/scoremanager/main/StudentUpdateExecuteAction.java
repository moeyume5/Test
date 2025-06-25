package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("login");

        String studentNo = req.getParameter("studentNo");
        String name = req.getParameter("name");
        String classNum = req.getParameter("classNum");
        String isAttendStr = req.getParameter("isAttend");

        Map<String, String> errors = new HashMap<>();

        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "このフィールドを入力してください。");
        }
        if (classNum == null || classNum.trim().isEmpty()) {
            errors.put("classNum", "クラスを選択してください。");
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("ent_year", req.getParameter("entYear"));
            req.setAttribute("no", studentNo);
            req.setAttribute("name", name);
            req.setAttribute("classNum", classNum);
            req.setAttribute("class_num_set", new ClassNumDao().filter(teacher.getSchool_cd()));
            req.setAttribute("isAttend", isAttendStr);
            req.getRequestDispatcher("student_update.jsp").forward(req, res);
            return;
        }

        StudentDao sDao = new StudentDao();
        Student student = sDao.findByNo(studentNo);

        student.setName(name);
        student.setClassNum(classNum);
        student.setAttend("true".equals(isAttendStr));

        sDao.update(student);

        req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
    }
}
