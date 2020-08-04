<%@ page import="com.urise.webapp.model.AbstractSection" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contact}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <p>
        <c:forEach var="sectionEntry" items="${resume.textSection}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
    <p>
    <h2><a name="type.name">${type.title}</a></h2>
    </p>
    <c:choose>
        <c:when test="${type=='PERSONAL'}">
            <p>
                <%=((TextSection) section).getText()%>
            </p>
        </c:when>
        <c:when test="${type == 'OBJECTIVE'}">
            <tr>
                <%=((TextSection) section).getText()%>
            </tr>
        </c:when>
        <c:when test="${type=='ACHIEVEMENT'}">
            <ul>
                <c:forEach var="listEntry" items="<%=((ListSection) section).getListText()%>">
                    <li>${listEntry}</li>
                </c:forEach>
            </ul>
        </c:when>
        <c:when test="${type=='QUALIFICATIONS'}">
            <ul>
                <c:forEach var="listEntry" items="<%=((ListSection) section).getListText()%>">
                    <li>${listEntry}</li>
                </c:forEach>
            </ul>
        </c:when>
    </c:choose>
    </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>