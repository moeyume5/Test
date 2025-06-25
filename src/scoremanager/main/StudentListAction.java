package scoremanager.main;

import java.time.LocalDate;
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

public class StudentListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("login");

        String entYearStr = req.getParameter("f1"); // 入力された入学年度
        String classNum = req.getParameter("f2"); // 入力されたクラス番号
        String isAttendStr = req.getParameter("f3"); // 入力された在学フラグ
        int entYear = 0; // 入学年度
        boolean isAttend = false; // 在学フラグ

        if (entYearStr != null && !entYearStr.isEmpty() && !entYearStr.equals("0")) {
            entYear = Integer.parseInt(entYearStr);
        }

        if (isAttendStr != null && !isAttendStr.isEmpty()) {
            isAttend = true;
        }

        StudentDao sDao = new StudentDao(); // 学生Dao
        ClassNumDao cNumDao = new ClassNumDao(); // クラス番号Daoを初期化
        Map<String, String> errors = new HashMap<>(); // エラーメッセージ

        // DBからデータ取得
        List<String> list = cNumDao.filter(teacher.getSchool_cd());

        List<Student> students = null;

        if (entYear != 0 && !classNum.equals("0")) {
            // 入学年度とクラス番号を指定
            students = sDao.filter(teacher.getSchool_cd(), entYear, classNum, isAttend);
        } else if (entYear != 0 && classNum.equals("0")) {
            // 入学年度のみ指定
            students = sDao.filter(teacher.getSchool_cd(), entYear, isAttend);
        } else if (entYear == 0 && (classNum == null || classNum.equals("0"))) {
            // 指定なしの場合
            // 全学生情報を取得
            students = sDao.filter(teacher.getSchool_cd(), isAttend);
        } else {
            errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
            req.setAttribute("errors", errors);
            // 全学生情報を取得
            students = sDao.filter(teacher.getSchool_cd(), isAttend);
        }

        LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得
        int year = todaysDate.getYear(); // 現在の年を取得

        // リストを初期化
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }

        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", isAttendStr != null ? "t" : "");
        req.setAttribute("students", students);
        req.setAttribute("class_num_set", list);
        req.setAttribute("ent_year_set", entYearSet);

        req.getRequestDispatcher("student_list.jsp").forward(req, res);
    }
}
