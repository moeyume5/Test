<!-- subject_delete_content.jsp -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css">
</head>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>

<section class="me-4">
    <h2 class="h3 mb-3 fw-normal bg-light bg-opacity-10 py-2 px-4">科目情報削除</h2>

    <div class="mt-3">
        <p>「${subject.name}（${subject.cd}）」を削除してもよろしいですか</p>
    </div>

    <div class="mt-3">
        <form action="SubjectDeleteExecute.action" method="post">
            <input type="hidden" name="cd" value="${subject.cd}">
            <input type="hidden" name="school_cd" value="${subject.school_CD}">
            <button type="submit" class="btn btn-danger">削除</button>
        </form>
    </div>

    <div class="mt-3">
        <a href="SubjectList.action" style="text-decoration: underline;">戻る</a>
    </div>

</section>
