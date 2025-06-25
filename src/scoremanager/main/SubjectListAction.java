//SubjectListAction.java
package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションから教師オブジェクトを取得
        Teacher teacher = Util.getUser(request);

        // 科目DAOを通じてデータベースから科目リストを取得
        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjects = new ArrayList<>();

        try {
            subjects = subjectDao.filter(teacher.getSchool_cd());
        } catch (Exception e) {
            e.printStackTrace();
            // エラーメッセージをリクエストに設定
            request.setAttribute("errorMessage", "科目リストの取得に失敗しました。");
        }

        // リクエストに科目リストを設定
        request.setAttribute("subjects", subjects);

        // subject_list.jsp にフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("subject_list.jsp");
        dispatcher.forward(request, response);
    }
}
