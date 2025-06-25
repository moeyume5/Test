package scoremanager.main;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("login");

        if (teacher != null) {
            try {
                // 科目データを取得
                SubjectDao subjectDao = new SubjectDao();
                List<Subject> subjectList = subjectDao.filter(teacher.getSchool_cd());

                // クラスデータを取得
                StudentDao studentDao = new StudentDao();
                List<Student> studentList = studentDao.filter(teacher.getSchool_cd(), false);

                // 重複を取り除いた入学年度リストを作成し、昇順にソート
                Set<Integer> entYearSet = studentList.stream()
                    .map(Student::getEntYear)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
                List<Integer> entYearList = new ArrayList<>(entYearSet);
                entYearList.sort(Integer::compareTo);

                // 重複を取り除いたクラス番号リストを作成
                Set<String> classNumSet = studentList.stream()
                    .map(Student::getClassNum)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
                List<String> classNumList = new ArrayList<>(classNumSet);

                // リクエストパラメータから検索条件を取得
                String entYearStr = request.getParameter("entYear");
                String classNum = request.getParameter("classNum");
                String subjectCd = request.getParameter("subjectCd");
                String noStr = request.getParameter("no");

                // 検索条件をパース
                int entYear = (entYearStr != null && !entYearStr.isEmpty()) ? Integer.parseInt(entYearStr) : 0;
                int no = (noStr != null && !noStr.isEmpty()) ? Integer.parseInt(noStr) : 0;
                Subject subject = subjectList.stream().filter(s -> s.getCd().equals(subjectCd)).findFirst().orElse(null);
                School school = new School();
                school.setCd(teacher.getSchool_cd());

                // デバッグ用ログ出力
                System.out.println("検索条件 - entYear: " + entYear + ", classNum: " + classNum + ", subjectCd: " + subjectCd + ", no: " + no);

                // テストデータを取得
                TestDao testDao = new TestDao();
                List<Test> testList = testDao.filter(entYear, classNum, subject, no, school);

                // ユニークな回数リストを作成
                Set<Integer> noSet = testList.stream()
                    .map(Test::getNo)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
                List<Integer> noList = new ArrayList<>(noSet);

                // デバッグ用ログ出力: testList の内容を表示
                System.out.println("取得したテストデータ:");
                for (Test test : testList) {
                    System.out.println("Student_No: " + test.getStudent().getNo() +
                                       ", Subject_CD: " + test.getSubject().getCd() +
                                       ", School_CD: " + test.getSchool().getCd() +
                                       ", No: " + test.getNo() +
                                       ", Point: " + test.getPoint() +
                                       ", Class_Num: " + test.getClassNum());
                }

                // リクエストにデータを格納
                request.setAttribute("subjectList", subjectList);
                request.setAttribute("entYearList", entYearList);
                request.setAttribute("classNumList", classNumList);
                request.setAttribute("noList", noList); // ユニークな回数リストを追加
                request.setAttribute("testList", testList);

                // フォワードする先のJSPを指定します（例: test_regist.jsp）
                request.getRequestDispatcher("test_regist.jsp").forward(request, response);

            } catch (Exception e) {
                // エラーハンドリング
                e.printStackTrace();
                request.setAttribute("errorMessage", "エラーが発生しました。再度お試しください。");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else {
            // ユーザーがセッションに存在しない場合、エラーページにリダイレクトします
            response.sendRedirect("error.jsp");
        }
    }
}
