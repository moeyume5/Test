<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="base.jsp">
    <c:param name="contentPage" value="error_content.jsp" />
    <c:param name="showSidebar" value="${showSidebar != null ? showSidebar : 'true'}" />
</c:import>
