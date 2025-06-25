package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("login");

        String entYearStr = req.getParameter("entYear");
        String studentNo = req.getParameter("studentNo");
        String name = req.getParameter("name");
        String classNum = req.getParameter("classNum");
        String isAttendStr = req.getParameter("isAttend");

        Map<String, String> errors = new HashMap<>();

        // 入学年度の検証
        if (entYearStr == null || entYearStr.equals("0")) {
            errors.put("entYear", "入学年度を選択してください");
        }
        // 学生番号の検証
        if (studentNo == null || studentNo.trim().isEmpty()) {
            errors.put("studentNo", "このフィールドを入力してください");
        } else {
            StudentDao sDao = new StudentDao();
            // 学生番号の重複チェック
            if (sDao.findByNo(studentNo) != null) {
                errors.put("studentNo", "学生番号が重複しています");
            }
        }
        // 氏名の検証
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "このフィールドを入力してください");
        }
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("class_num_set", new ClassNumDao().filter(teacher.getSchool_cd()));
            req.setAttribute("ent_year_set", createEntYearSet());
            req.getRequestDispatcher("student_create.jsp").forward(req, res);
            return;
        }

        int entYear = Integer.parseInt(entYearStr);
        boolean isAttend = isAttendStr != null && isAttendStr.equals("true");

        Student student = new Student();
        student.setNo(studentNo);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);
        student.setSchool(teacher.getSchool_cd());

        StudentDao sDao = new StudentDao();
        boolean result = sDao.save(student);

        if (result) {
            req.getRequestDispatcher("student_create_done.jsp").forward(req, res);
        } else {
            errors.put("error", "学生の登録に失敗しました。");
            req.setAttribute("errors", errors);
            req.setAttribute("class_num_set", new ClassNumDao().filter(teacher.getSchool_cd()));
            req.setAttribute("ent_year_set", createEntYearSet());
            req.getRequestDispatcher("student_create.jsp").forward(req, res);
        }
    }

    private List<Integer> createEntYearSet() {
        int currentYear = java.time.LocalDate.now().getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = currentYear - 10; i <= currentYear; i++) {
            entYearSet.add(i);
        }
        return entYearSet;
    }
}
