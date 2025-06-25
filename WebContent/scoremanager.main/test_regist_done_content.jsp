<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>得点管理システム</title>
<style>
        .content {
            padding: 20px;
        }
        .message {
            margin-bottom: 20px;
            font-size: 1.2em;
            color: black;
            background-color: #d4edda; /* light green background */
            border: 1px solid #c3e6cb; /* darker green border */
            padding: 10px;
            border-radius: 5px;
        }
        .btn-container {
            display: flex;
            gap: 10px; /* ボタン間のスペース */
        }
</style>
</head>
<body>
<div class="container content">
<h2 class="h2">成績管理</h2><br>

        <div class="message">
<%
                String message = (String) request.getAttribute("message");
                if (message == null) {
                    message = "登録が完了しました";
                }
                out.print(message);
            %>
</div><br>

        <div class=text">
<!-- 戻るボタン -->
<a href="javascript:history.back()">戻る</a>
<!-- 成績参照ボタン -->
<a href="TestList.action">成績参照</a>
</div>
</div>
</body>
</html>