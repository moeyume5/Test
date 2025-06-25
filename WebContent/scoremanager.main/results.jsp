<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ include file="score.jsp" %>

<!DOCTYPE html>
<html lang="ja" class="results-page">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <title>成績結果</title>
  <style>
    table {
        border-collapse: collapse;
        width: 100%;
    }
    th, td {
        border-bottom: 1px solid black; /* テーブル行の下の線 */
        padding: 8px;
        text-align: center;
    }
    thead {
        border-bottom: 1px solid black; /* テーブル見出し部分の下線 */
    }
    tr:last-child th,
    tr:last-child td {
        border-bottom: none; /* 最後の行の下線を削除 */
    }
    tr:nth-child(even) {
        background-color: #f2f2f2;
    }
</style>
  
</head>
<body>
    <div class="container">
        <h2>科目 : 数学</h2>
        <table>
            <thead>
                <tr>
                    <th>入学年度</th>
                    <th>クラス</th>
                    <th>学生番号</th>
                    <th>氏名</th>
                    <th>点数</th>
                </tr>
            </thead>
            <tbody>
                <!-- 仮のサンプルデータ -->
                <%
                    String[][] grades = {
                        {"2020", "1", "2023001", "田中太郎", ""},
                        {"2021", "2", "2023002", "山田花子", ""},
                        {"2022", "3", "2023003", "鈴木次郎", ""}
                    };
                    
                    // サンプルデータをテーブルに出力する
                    for (String[] grade : grades) {
                %>
                        <tr>
                            <td><%= grade[0] %></td>
                            <td><%= grade[1] %></td>
                            <td><%= grade[2] %></td>
                            <td><%= grade[3] %></td>
                            <td>
                                <input type="text" name="point_<%= grade[2] %>" placeholder="点数を入力">
                            </td>
                        </tr>
                <% } %>
            </tbody>
        </table>

        <form method="post" action="saveScore.jsp">
            <button type="submit">登録</button>
        </form>
        
        <%-- score.jsp を include --%>
       
        
    </div>
</body>
</html>
