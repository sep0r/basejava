<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=30 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="typeSection" items="<%=SectionType.values()%>">
            <jsp:useBean id="typeSection" type="com.urise.webapp.model.SectionType"/>
            <c:choose>
                <c:when test="<%=typeSection.equals(SectionType.PERSONAL) ||
                                 typeSection.equals(SectionType.OBJECTIVE)%>">
                    <dl>
                        <dt>${typeSection.title}</dt>
                        <dd><input type="text" name="${typeSection.name()}" size=30
                                   value="${resume.getSection(typeSection)}">
                        </dd>
                    </dl>
                </c:when>
                <c:when test="<%=typeSection.equals(SectionType.ACHIEVEMENT) ||
                                 typeSection.equals(SectionType.QUALIFICATIONS)%>">
                    <dl>
                        <dt>${typeSection.title}</dt>
                        <dd>
                            <textarea rows="5" cols="27" name="${typeSection.name()}">
                                <%=HtmlUtil.textToList(resume.getSection(typeSection))%>
                            </textarea>
                        </dd>
                    </dl>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>