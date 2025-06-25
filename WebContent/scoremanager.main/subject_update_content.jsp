<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
</head>

<body>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>

    <section class="me-4">
        <h2 class="h3 mb-3 fw-normal bg-light bg-opacity-10 py-2 px-4">科目変更</h2>
        <form action="SubjectUpdateExecute.action" method="post">
            <div class="mb-3">
                <label for="subjectCd" class="form-label">科目ID</label>
                <input type="text" class="form-control" id="subjectCd" name="cd" value="${subject.cd}" readonly>
            </div>
            <div class="mb-3">
                <label for="subjectName" class="form-label">科目名</label>
                <input type="text" class="form-control" id="subjectName" name="subjectName" value="${subject.name}" placeholder="科目名を入力してください">
                <c:if test="${not empty errors['subjectName']}">
                    <div class="text-danger mt-2">${errors['subjectName']}</div>
                </c:if>
            </div>
            <c:if test="${not empty errors['error']}">
                <div class="text-danger mt-2">${errors['error']}</div>
            </c:if>
            <button type="submit" class="btn btn-primary">変更</button>
            <br>
            <a href="SubjectList.action">戻る</a>
        </form>
    </section>
</body>
