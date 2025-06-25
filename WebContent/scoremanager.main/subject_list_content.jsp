<!-- subject_list_content.jsp -->
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
    <h2 class="h3 mb-3 fw-normal bg-light bg-opacity-10 py-2 px-4">科目管理</h2>
    <div style="text-align: right; padding-right: 100px;">
        <a href="SubjectCreate.action" style="text-decoration: underline;">新規登録</a>
	</div>

    <c:if test="${not empty errorMessage}">
        <!-- エラーメッセージの表示 -->
        <p style="color: red;">${errorMessage}</p>
    </c:if>

    <!-- フィルターフォーム -->
    <form method="get">
        <!-- フィルターの内容 -->
        <!-- 例: 入学年度、クラスの選択肢、在学中チェックボックス -->
    </form>

    <!-- 科目リストの表示 -->
    <c:choose>
    <c:when test="${not empty subjects}">
        <table class="table table-hover">
            <tr>
                <th>科目コード</th>
                <th>科目名</th>
                <th> <th>
            </tr>
            <c:forEach var="subject" items="${subjects}">
                <tr>
                    <td>${subject.cd}</td>
                    <td>${subject.name}</td>
                    <td>
                        <a href="SubjectUpdate.action?cd=${subject.cd}&school_cd=${subject.school_CD}" style="text-decoration: underline;">変更</a>
                    </td>
                    <td>
                        <a href="SubjectDelete.action?cd=${subject.cd}&school_cd=${subject.school_CD}" style="text-decoration: underline;">削除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <div>科目情報が存在しませんでした</div>
    </c:otherwise>
</c:choose>


</section>
