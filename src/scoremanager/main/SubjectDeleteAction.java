package scoremanager.main;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String cd = req.getParameter("cd");
        String school_cd = req.getParameter("school_cd");

        // SubjectDaoを使用して、削除する科目の詳細を取得
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = null;
        try {
            subject = subjectDao.get(cd, school_cd);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("科目情報の取得に失敗しました。", e);
        }

        // 取得した科目情報をリクエスト属性に設定
        req.setAttribute("subject", subject);

        // subject_delete.jspにフォワード
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}
