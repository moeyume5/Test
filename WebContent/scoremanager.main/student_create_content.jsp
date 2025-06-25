<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <h2 class="h3 mb-3 fw-norma bg-light bg-opacity-10 py-2 px-4">学生登録</h2>
        <form action="StudentCreateExecute.action" method="post">
            <div class="mb-3">
                <label for="entYear" class="form-label">入学年度</label>
                <select class="form-select" id="entYear" name="entYear">
                    <option value="0">--------</option>
                    <c:forEach var="year" items="${ent_year_set}">
                        <option value="${year}" <c:if test="${param.entYear == year}">selected</c:if>>${year}</option>
                    </c:forEach>
                </select>
                <c:if test="${not empty errors['entYear']}">
                    <div class="text-danger mt-2">${errors['entYear']}</div>
                </c:if>
            </div>
            <div class="mb-3">
                <label for="studentNo" class="form-label">学生番号</label>
                <input type="text" class="form-control" id="studentNo" name="studentNo" value="${param.studentNo}" placeholder="学生番号を入力してください">
                <c:if test="${not empty errors['studentNo']}">
                    <div class="text-danger mt-2">${errors['studentNo']}</div>
                </c:if>
            </div>
            <div class="mb-3">
                <label for="name" class="form-label">氏名</label>
                <input type="text" class="form-control" id="name" name="name" value="${param.name}" placeholder="氏名を入力してください">
                <c:if test="${not empty errors['name']}">
                    <div class="text-danger mt-2">${errors['name']}</div>
                </c:if>
            </div>
            <div class="mb-3">
                <label for="classNum" class="form-label">クラス番号</label>
                <select class="form-select" id="classNum" name="classNum">
                    <c:forEach var="num" items="${class_num_set}">
                        <option value="${num}" <c:if test="${param.classNum == num}">selected</c:if>>${num}</option>
                    </c:forEach>
                </select>
                <c:if test="${not empty errors['classNum']}">
                    <div class="text-danger mt-2">${errors['classNum']}</div>
                </c:if>
            </div>
            <div class="form-check mb-3">
                <input type="checkbox" class="form-check-input" id="isAttend" name="isAttend" value="true" <c:if test="${param.isAttend == 'true'}">checked</c:if>>
                <label class="form-check-label" for="isAttend">在学中</label>
            </div>
            <c:if test="${not empty errors['error']}">
                <div class="text-danger mt-2">${errors['error']}</div>
            </c:if>
            <button type="submit" class="btn btn-secondary">登録して終了</button>
            <br>
            <a href="StudentList.action">戻る</a>
        </form>
    </section>
</body>
</html>
