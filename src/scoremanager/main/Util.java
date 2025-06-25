//Util.java
package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Teacher;

public class Util {

	// セッションからTeacherオブジェクトを取得するメソッド
	public static Teacher getUser(HttpServletRequest request) {
	    HttpSession session = request.getSession(false); // セッションが存在しない場合にnullを返すように修正
	    if (session != null) {
	        return (Teacher) session.getAttribute("login");
	    } else {
	        return null;
	    }
	}


    // クラス番号をリクエストから取得してセッションに保存するメソッド
    public static void setClassNumSet(HttpServletRequest request) {
        String classNum = request.getParameter("classNum");
        if (classNum != null) {
            HttpSession session = request.getSession();
            session.setAttribute("classNum", classNum);
        }
    }

    // 学年をリクエストから取得してセッションに保存するメソッド
    public static void setEntryYearSet(HttpServletRequest request) {
        String entYear = request.getParameter("entYear");
        if (entYear != null) {
            HttpSession session = request.getSession();
            session.setAttribute("entYear", entYear);
        }
    }

    // 科目リストをリクエストから取得してセッションに保存するメソッド
    public static void setSubjects(HttpServletRequest request) {
        String[] subjects = request.getParameterValues("subjects");
        if (subjects != null) {
            HttpSession session = request.getSession();
            session.setAttribute("subjects", subjects);
        }
    }

    // 生徒数をリクエストから取得してセッションに保存するメソッド
    public static void setNumSet(HttpServletRequest request) {
        String numSet = request.getParameter("numSet");
        if (numSet != null) {
            HttpSession session = request.getSession();
            session.setAttribute("numSet", numSet);
        }
    }
}
