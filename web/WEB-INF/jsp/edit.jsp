<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
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
                <c:when test="<%=typeSection.equals(SectionType.PERSONAL) || typeSection.equals(SectionType.OBJECTIVE)%>">
                    <dl>
                        <dt>${typeSection.title}</dt>
                        <dd><input type="text" name="${typeSection.name()}" size=30 value="${resume.getSection(typeSection)}"> </dd>
                    </dl>
                </c:when>
                <c:when test="<%=typeSection.equals(SectionType.ACHIEVEMENT) || typeSection.equals(SectionType.QUALIFICATIONS)%>">
                    <dl>
                        <dt>${typeSection.title}</dt>
                        <br>
                        <p><c:if test="${resume.getSection(typeSection)!=null}">
                            <c:forEach var="text" items="<%=((ListSection)resume.getSection(typeSection)).getListText()%>">
                        <dd class="sec">
                            <textarea cols=49 name="${typeSection.name()}">${text}</textarea>
                        </dd>
                        <br>
                        </c:forEach>
                        </c:if>
                        <c:if test="<%=typeSection.equals(SectionType.ACHIEVEMENT)%>">
                            <div id="ach">
                            </div>
                            <br>
                            <button type="button" onclick="addAchievement()">Добавить</button>
                        </c:if>
                        <c:if test="<%=typeSection.equals(SectionType.QUALIFICATIONS)%>">
                            <div id="qua">
                            </div>
                            <br>
                            <button type="button" onclick="addQualification()">Добавить</button>
                        </c:if>
                    </dl>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
<script>
    function addAchievement() {
        document.getElementById("ach").insertAdjacentHTML('afterbegin',
            '<dd class="sec"><textarea cols=49 name="<%=SectionType.ACHIEVEMENT%>"></textarea></dd><br>');
    }
    function addQualification() {
        document.getElementById("qua").insertAdjacentHTML('afterbegin',
            '<dd class="sec"><textarea cols=49 name="<%=SectionType.QUALIFICATIONS%>"></textarea></dd><br>');
    }
</script>
</body>
</html>