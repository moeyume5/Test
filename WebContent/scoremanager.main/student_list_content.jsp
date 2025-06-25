<%-- student_list_content.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
 <meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
 <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" >
</head>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" ></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" ></script>

<section class="me-4">
    <h2 class="h3 mb-3 fw-norma bg-light bg-opacity-10 py-2 px-4">学生管理</h2>
    <div class="my-2 text-right px-4">
        <a href="StudentCreate.action" style="text-decoration: underline;">新規登録</a>
    </div>
    <form method="get">
        <div class="row border mx-3 py-2 align-items-center rounded" id="filter">
            <div class="col-4">
                <label class="form-label" for="student-f1-select">入学年度</label><br>
                <select class="form-select" id="student-f1-select" name="f1">
                    <option value="0">--------</option>
                    <c:forEach var="year" items="${ent_year_set}">
                        <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-4">
                <label class="form-label" for="student-f2-select">クラス</label><br>
                <select class="form-select" id="student-f2-select" name="f2">
                    <option value="0">--------</option>
                    <c:forEach var="num" items="${class_num_set}">
                        <option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-2 form-check text-center">
    <input class="form-check-input" type="checkbox" id="student-f3-check" name="f3" value="t"
           <c:if test="${!empty f3}">checked</c:if> />
    <label class="form-check-label ml-1" for="student-f3-check">在学中</label>
</div>

            <div class="col-2 text-center">
                <button class="btn btn-secondary" id="filter-button">絞込み</button>
            </div>
            <div class="mt-2 text-warning">${errors.get("f1")}</div>
        </div>
    </form>
    <c:choose>
        <c:when test="${students.size()>0}">
            <div>検索結果:${students.size()}件</div>
            <table class="table table-hover">
                <tr>
                    <th>入学年度</th>
                    <th>学生番号</th>
                    <th>氏名</th>
                    <th>クラス</th>
                    <th class="text-center">在学中</th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach var="student" items="${students}">
                    <tr>
                        <td>${student.entYear}</td>
                        <td>${student.no}</td>
                        <td>${student.name}</td>
                        <td>${student.classNum}</td>
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${student.isAttend()}">〇</c:when>
                                <c:otherwise>✕</c:otherwise>
                            </c:choose>
                        </td>
                        <td><a href="StudentUpdate.action?no=${student.no}" style="text-decoration: underline;">変更</a></td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <div>学生情報が存在しませんでした</div>
        </c:otherwise>
    </c:choose>
</section>
