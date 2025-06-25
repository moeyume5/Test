<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
</head>
<body>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>

<section class="me-4">
    <h2 class="h3 mb-3 fw-norma bg-light bg-opacity-10 py-2 px-4">科目登録</h2>
    <form action="SubjectCreateExecute.action" method="post">
        <div class="mb-3">
            <label for="cd" class="form-label">科目コード</label>
            <input type="text" class="form-control" id="cd" name="cd" placeholder="科目コードを入力してください">
        </div>
        <div class="mb-3">
            <label for="name" class="form-label">科目名</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="科目名を入力してください">
        </div>
        <c:if test="${not empty error}">
            <div class="text-danger mt-2">${error}</div>
        </c:if>
        <button type="submit" class="btn btn-primary">登録</button>
		<br>
		<a href="SubjectList.action" style="text-decoration: underline;">戻る</a>
    </form>
</section>
</body>
</html>
