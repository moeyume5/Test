package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("login");

        String entYearStr = req.getParameter("f1"); // 入力された入学年度
        String classNum = req.getParameter("f2"); // 入力されたクラス番号
        String subjectCd = req.getParameter("f3"); // 入力された科目コード
        String studentNo = req.getParameter("f4"); // 入力された学生番号

        int entYear = 0; // 入学年度

        if (entYearStr != null && !entYearStr.isEmpty() && !entYearStr.equals("--------")) {
            entYear = Integer.parseInt(entYearStr);
        }

        TestListSubjectDao testListSubjectDao = new TestListSubjectDao(); // テストリスト科目Dao
        TestListStudentDao testListStudentDao = new TestListStudentDao(); // テストリスト学生Dao
        ClassNumDao classNumDao = new ClassNumDao(); // クラス番号Dao
        SubjectDao subjectDao = new SubjectDao(); // 科目Dao
        Map<String, String> errors = new HashMap<>(); // エラーメッセージ

        // DBからデータ取得
        List<String> classNumList = classNumDao.filter(teacher.getSchool_cd());
        List<Subject> subjectList = subjectDao.filter(teacher.getSchool_cd());

        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();
        List<Integer> yearList = new ArrayList<>(); // 入学年度リスト

        // 入学年度のリストを生成するための仮のデータ (実際のデータはデータベースから取得)
        for (int i = year - 10; i <= year + 1; i++) {
            yearList.add(i);
        }

        List<TestListSubject> testListSubjects = new ArrayList<>();
        List<TestListStudent> testListStudents = new ArrayList<>();

        if (entYear != 0 && classNum != null && !classNum.equals("--------") && subjectCd != null && !subjectCd.equals("--------")) {
            // 入学年度、クラス番号、科目コードを指定
            testListSubjects = testListSubjectDao.filter(entYear, classNum, subjectCd, teacher.getSchool_cd());
            testListStudents = testListStudentDao.filter(subjectCd);
        } else if (entYear != 0 && classNum != null && !classNum.equals("--------")) {
            // 入学年度とクラス番号を指定
            testListSubjects = testListSubjectDao.filter(entYear, classNum, "", teacher.getSchool_cd());
            testListStudents = testListStudentDao.filter("");
        } else if (entYear != 0) {
            // 入学年度のみ指定
            testListSubjects = testListSubjectDao.filter(entYear, "", "", teacher.getSchool_cd());
            testListStudents = testListStudentDao.filter("");
        } else {
            errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
            req.setAttribute("errors", errors);
        }

        req.setAttribute("years", yearList); // 入学年度のリストをリクエストに設定
        req.setAttribute("classes", classNumList); // クラス番号のリストをリクエストに設定
        req.setAttribute("subjects", subjectList); // 科目名のリストをリクエストに設定
        req.setAttribute("students", testListStudents); // 学生情報リストをリクエストに設定

        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", studentNo);
        req.setAttribute("testListSubjects", testListSubjects);

        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}
