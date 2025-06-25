package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // パラメータから削除する科目コードと学校コードを取得
        String cd = request.getParameter("cd");
        String schoolCd = request.getParameter("school_cd");

        // SubjectDaoのインスタンスを生成
        SubjectDao subjectDao = new SubjectDao();

        // 削除する科目のインスタンスを作成
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setSchool_CD(schoolCd);

        // 削除処理を実行
        boolean deleted = subjectDao.delete2(subject);

        if (deleted) {
            // 削除成功時の処理
            response.sendRedirect("subject_delete_done.jsp");
        }
        // エラー時の処理はFrontControllerで行われているため、ここでは何も行いません
    }
}
