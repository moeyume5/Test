<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList" %>

<%
    // サンプルデータをリストに格納
    List<String> years = new ArrayList<>();
    years.add("2020");
    years.add("2021");
    years.add("2022");

    List<Integer> classes = new ArrayList<>();
    classes.add(1);
    classes.add(2);
    classes.add(3);

    class Subject {
        String cd;
        String name;
        Subject(String cd, String name) {
            this.cd = cd;
            this.name = name;
        }
    }

    List<Subject> subjects = new ArrayList<>();
    subjects.add(new Subject("math", "Math"));
    subjects.add(new Subject("science", "Science"));
    subjects.add(new Subject("history", "History"));

    List<Integer> terms = new ArrayList<>();
    terms.add(1);
    terms.add(2);
    terms.add(3);
%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style2.css">
    <title>成績管理システム</title>
    <script>
        // 検索機能のプレースホルダー関数
        function searchScores() {
            // 選択されたフィルターに基づいて成績を取得して表示するロジック
            alert("検索ボタンが押されました。検索機能を実装してください。");
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>成績管理</h2>
        <form id="searchForm">
            <table>
                <tbody>
                    <tr>
                        <td>
                            <label for="year">入学年度</label>
                            <select id="year" name="f1">
                                <option value="--------">--------</option>
                                <% for(String year : years) { %>
                                    <option value="<%= year %>"><%= year %></option>
                                <% } %>
                            </select>
                        </td>
                        <td>
                            <label for="class">クラス</label>
                            <select id="class" name="f2">
                                <option value="--------">--------</option>
                                <% for(Integer num : classes) { %>
                                    <option value="<%= num %>">Class <%= num %></option>
                                <% } %>
                            </select>
                        </td>
                        <td>
                            <label for="subject">科目</label>
                            <select id="subject" name="f3" style="width: 200px;">
                                <option value="--------">--------</option>
                                <% for(Subject subject : subjects) { %>
                                    <option value="<%= subject.cd %>"><%= subject.name %></option>
                                <% } %>
                            </select>
                        </td>
                        <td>
                            <label for="term">回数</label>
                            <select id="term" name="f4">
                                <option value="--------">--------</option>
                                <% for(Integer num : terms) { %>
                                    <option value="<%= num %>"><%= num %></option>
                                <% } %>
                            </select>
                        </td>
                        <td>
                            <button type="button" onclick="searchScores()">検索</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
    </div>
</body>
</html>
