<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="java.util.UUID" %>
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
        <h2>Контакты:</h2>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h2>Секции:</h2>
        <c:forEach var="typeSection" items="<%=SectionType.values()%>">
            <jsp:useBean id="typeSection" type="com.urise.webapp.model.SectionType"/>
            <c:choose>
                <c:when test="<%=typeSection.equals(SectionType.PERSONAL) || typeSection.equals(SectionType.OBJECTIVE)%>">
                    <dl>
                        <dt><h3>${typeSection.title}</h3></dt>
                        <dd><textarea cols=26 name="${typeSection.name()}">${resume.getSection(typeSection)}</textarea>
                        </dd>
                    </dl>
                </c:when>
                <c:when test="<%=typeSection.equals(SectionType.ACHIEVEMENT) || typeSection.equals(SectionType.QUALIFICATIONS)%>">
                    <dl>
                        <dt><h3>${typeSection.title}</h3></dt>
                        <br>
                        <p>
                            <c:if test="${resume.getSection(typeSection)!=null}">
                            <c:forEach var="text"
                                       items="<%=((ListSection)resume.getSection(typeSection)).getListText()%>">
                        <dd>
                            <textarea cols=27 name="${typeSection.name()}">${text}</textarea>
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
                <c:when test="<%=typeSection.equals(SectionType.EDUCATION) || typeSection.equals(SectionType.EXPERIENCE)%>">
                    <dl>
                        <dt><h3>${typeSection.title}</h3></dt>
                        <br>
                        <c:if test="${resume.getSection(typeSection)!=null}">
                            <c:forEach var="organization"
                                       items="<%=((OrganizationSection)resume.getSection(typeSection)).getOrgText()%>">
                                <dt>Название учереждения:</dt>
                                <dd>
                                    <input type="text" name="${typeSection.name()}" size=30
                                           value="${organization.name}">
                                </dd>
                                <br>
                                <dt>Ссылка:</dt>
                                <dd>
                                    <input type="text" name="link" size=30 value="${organization.link.url}">
                                </dd>
                                <br>
                                <c:forEach var="positions" items="${organization.positions}">
                                    <input type="hidden" name="${typeSection.name()}" value="#exist#">
                                    <div>
                                        <dt>Деятельность:</dt>
                                        <dd>
                                            <input type="text" name="position" size=30 value="${positions.position}">
                                        </dd>
                                        <br>
                                        <c:if test="<%=typeSection.equals(SectionType.EXPERIENCE)%>">
                                            <dt>Описание:</dt>
                                            <dd>
                                                <textarea name="content">${positions.content}</textarea>
                                            </dd>
                                            <br>
                                        </c:if>
                                        <dt>Дата начала:</dt>
                                        <dd>
                                            <input name="startDate" type="date" value="${positions.startDate}">
                                        </dd>
                                        <br>
                                        <dt>Дата окончания:</dt>
                                        <dd>
                                            <input name="finishDate" type="date" value="${positions.finishDate}">
                                        </dd>
                                    </div>
                                </c:forEach>
                                <c:set var="id" value="<%=UUID.randomUUID()%>"/>
                                <button id="${id}" type="button" onclick=addPosition('${typeSection.name()}','${id}')>
                                    Добавить описание
                                </button>
                                <hr>
                            </c:forEach>
                        </c:if>
                        <button id="${typeSection.name()}" type="button"
                                onclick="addOrganization('${typeSection.name()}')">
                            Добавить
                        </button>
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
            '<dd><textarea cols=27 name="<%=SectionType.ACHIEVEMENT%>"></textarea></dd><br>');
    }

    function addQualification() {
        document.getElementById("qua").insertAdjacentHTML('afterbegin',
            '<dd><textarea cols=27 name="<%=SectionType.QUALIFICATIONS%>"></textarea></dd><br>');
    }

    function addOrganization(type) {
        let element = document.createElement('div');
        element.insertAdjacentHTML('beforeend', '<dt>' +
            'Название:</dt><dd><input type="text" name="' + type + '" size=27></dd><br>');
        element.insertAdjacentHTML('beforeend', '<dt>' +
            'Ссылка:</dt><dd><input type="text" name="link" size=30 ></dd><br>');
        const id = Math.random();
        element.insertAdjacentHTML('beforeend', '<button id=' + id + ' type="button" onclick=addPosition(' + "'" + type + "'" + ',"' + id + '")>' +
            'Добавить описание</button>');
        document.getElementById(type).before(element)
    }

    function addPosition(type, id) {
        let element = document.createElement('div');
        element.insertAdjacentHTML('beforeend', '<input type="hidden" name="' + type + '" value="#exist#">' +
            '<dt>Деятельность:</dt><dd> <input type="text" name="position" size=30></dd><br>');
        if (type === 'EXPERIENCE') {
            element.insertAdjacentHTML('beforeend',
                '<dt>Описание:</dt><dd> <textarea name="content"></textarea> </dd> <br>');
        }
        element.insertAdjacentHTML('beforeend',
            '<dt>Дата начала:</dt><dd><input name="startDate" type="date"></dd>' +
            '<br><dt>Дата окончания:</dt><dd> <input name="finishDate" type="date"></dd>');
        document.getElementById(id).before(element)
    }
</script>
</body>
</html>