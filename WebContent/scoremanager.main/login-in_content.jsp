<!-- login_in_content.jsp -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <meta charset="UTF-8">
    <title>ログインページ</title>
    <link rel="stylesheet" href="style.css">
</head>
<div class="login-box">
    <h2>ログイン</h2>

    <c:if test="${not empty error}">
        <p style="color:black;">${error}</p>
    </c:if>

    <form action="LoginExecute.action" method="post">
        <div class="form-group">
            <label for="id">ID</label>
            <input type="text" id="id" name="id" size="20" placeholder="半角でご入力ください" maxlength="20" pattern="[\x21-\x7E]*" title="半角でご入力ください" required>
        </div>
        <div class="form-group">
            <label for="password">パスワード</label>
            <input type="password" id="password" name="password" size="20" placeholder="20文字以内の半角英数字でご入力ください" maxlength="20" pattern="[\x21-\x7E]*" title="20文字以内の半角英数字でご入力ください" required>
        </div>
        <div class="form-checkbox">
            <input type="checkbox" id="chk_d_ps" onclick="togglePasswordVisibility()">
            <label for="chk_d_ps">パスワードを表示</label>
        </div>
        <div class="form-actions">
            <input type="submit" value="ログイン" name="login">
        </div>
    </form>
</div>

<script>
function togglePasswordVisibility() {
    var passwordField = document.querySelector('input[name="password"]');
    var checkbox = document.getElementById('chk_d_ps');
    if (checkbox.checked) {
        passwordField.type = "text";
    } else {
        passwordField.type = "password";
    }
}
</script>
