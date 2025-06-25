package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School; // Schoolクラスのインポート
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("login");

        if (teacher != null) {
            try {
                // リクエストからテストデータを取得
                String[] studentNos = request.getParameterValues("studentNo");
                String[] subjectCds = request.getParameterValues("subjectCd");
                String[] schoolCds = request.getParameterValues("schoolCd");
                String[] classNums = request.getParameterValues("classNum"); // クラス情報を取得
                String[] scores = request.getParameterValues("score");

                // パラメータのチェック
                if (studentNos == null || subjectCds == null || scores == null || schoolCds == null || classNums == null) {
                    String missingParams = "不足しているパラメータ: ";
                    if (studentNos == null) missingParams += "studentNos ";
                    if (subjectCds == null) missingParams += "subjectCds ";
                    if (schoolCds == null) missingParams += "schoolCds ";
                    if (classNums == null) missingParams += "classNums ";
                    if (scores == null) missingParams += "scores ";
                    throw new Exception(missingParams);
                }

                // パラメータの数が一致しているかチェック
                if (studentNos.length != subjectCds.length || subjectCds.length != schoolCds.length || schoolCds.length != scores.length || scores.length != classNums.length) {
                    throw new Exception("パラメータの数が一致しません。");
                }

                TestDao testDao = new TestDao();
                List<Test> testList = new ArrayList<>();
                boolean isValid = true;
                StringBuilder errorMessages = new StringBuilder();

                for (int i = 0; i < studentNos.length; i++) {
                    Test test = new Test();

                    // Studentオブジェクトを作成し、テストに設定
                    Student student = new Student();
                    student.setNo(studentNos[i]);
                    test.setStudent(student);

                    // Subjectオブジェクトを作成し、テストに設定
                    Subject subject = new Subject();
                    subject.setCd(subjectCds[i]);
                    test.setSubject(subject);

                    // Schoolオブジェクトを作成し、テストに設定
                    School school = new School();
                    school.setCd(schoolCds[i]);
                    test.setSchool(school);

                    // クラス情報を設定
                    test.setClassNum(classNums[i]); // クラス情報を設定

                    // 点数を設定
                    try {
                        int score = Integer.parseInt(scores[i]);
                        if (score < 0 || score > 100) {
                            isValid = false;
                            errorMessages.append("点数の範囲は0〜100である必要があります: ").append(scores[i]).append("<br>");
                        } else {
                            test.setPoint(score);
                        }
                    } catch (NumberFormatException e) {
                        isValid = false;
                        errorMessages.append("点数の形式が正しくありません: ").append(scores[i]).append("<br>");
                    }

                    testList.add(test);
                }

                if (isValid) {
                    boolean success = testDao.save(testList);

                    if (success) {
                        request.setAttribute("message", "登録が完了しました");
                        request.getRequestDispatcher("test_regist_done.jsp").forward(request, response);
                    } else {
                        request.setAttribute("errorMessage", "成績の保存に失敗しました。");
                        request.getRequestDispatcher("test_regist.jsp").forward(request, response);
                    }
                } else {
                    // エラーメッセージをリクエスト属性に設定し、再度フォームに戻す
                    request.setAttribute("errorMessage", errorMessages.toString());
                    request.setAttribute("studentNos", studentNos); // 再設定することでフォームにデータを保持する
                    request.setAttribute("subjectCds", subjectCds);
                    request.setAttribute("schoolCds", schoolCds);
                    request.setAttribute("classNums", classNums);
                    request.setAttribute("scores", scores);
                    request.getRequestDispatcher("test_regist.jsp").forward(request, response);
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "エラーが発生しました。再度お試しください。" + e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else {
            // ユーザーがセッションに存在しない場合、エラーページにリダイレクト
            response.sendRedirect("error.jsp");
        }
    }
}
