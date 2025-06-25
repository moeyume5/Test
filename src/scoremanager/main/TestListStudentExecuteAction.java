package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String studentNo = req.getParameter("f4");
        String schoolCd = ((Teacher) req.getSession().getAttribute("login")).getSchool_cd();

        TestListStudentDao testListStudentDao = new TestListStudentDao();
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        Map<String, String> errors = new HashMap<>();

        // 入学年度、クラス番号、科目名、科目コードのリストを取得
        List<String> classNumList = classNumDao.filter(schoolCd);
        List<Subject> subjectList = subjectDao.filter(schoolCd);

        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();
        List<Integer> yearList = new ArrayList<>();

        // 入学年度のリストを生成
        for (int i = year - 10; i <= year + 1; i++) {
            yearList.add(i);
        }

        List<TestListStudent> testListStudents = new ArrayList<>();

        if (studentNo != null && !studentNo.isEmpty()) {
            // 学生番号が指定された場合、テストリストから科目情報を取得
            testListStudents = testListStudentDao.filter(studentNo);
        } else {
            errors.put("f4", "学生番号を入力してください");
            req.setAttribute("errors", errors);
        }

        req.setAttribute("years", yearList); // 入学年度のリストをリクエストに設定
        req.setAttribute("classes", classNumList); // クラス番号のリストをリクエストに設定
        req.setAttribute("subjects", subjectList); // 科目名のリストをリクエストに設定
        req.setAttribute("students", testListStudents); // 学生情報リストをリクエストに設定
        req.setAttribute("f4", studentNo); // 入力された学生番号をリクエストに設定

        RequestDispatcher dispatcher = req.getRequestDispatcher("test_list_student2.jsp");
        dispatcher.forward(req, res);
    }
}
