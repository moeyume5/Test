package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.LoginDAO2;
import tool.Action;

public class LoginExecuteAction extends Action {
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();

        String id = request.getParameter("id");
        String password = request.getParameter("password");

        // 入力チェック
        if (id == null || password == null || id.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "IDまたはパスワードが入力されていません。");
            request.setAttribute("showSidebar", "true");  // サイドバーを表示
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        // DAOのインスタンス生成
        LoginDAO2 dao = new LoginDAO2();
        try {
            Teacher login = dao.search(id, password);

            if (login != null) {
                // ログイン成功時の処理
                session.setAttribute("login", login);
                request.getRequestDispatcher("menu.jsp").forward(request, response);
            } else {
                // ログイン失敗時の処理
                request.setAttribute("error", "・  ログインに失敗しました。IDまたはパスワードが正しくありません。");
                request.setAttribute("contentPage", "login-in_content.jsp");
                request.setAttribute("showSidebar", "false");
                request.getRequestDispatcher("login-in.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // データベース接続エラーなどの処理
            request.setAttribute("error", "データベース接続エラーが発生しました。");
            request.setAttribute("showSidebar", "false");  // サイドバーを非表示
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
