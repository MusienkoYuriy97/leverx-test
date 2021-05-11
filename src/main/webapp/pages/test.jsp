<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Test</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<button class="btn btn-primary" disabled>Добавить вопрос</button>
<button class="btn btn-primary" disabled>Начать тест</button>

<form action="/test" method="get">
    <c:if test="${requestScope.message != null}">
        <div class="alert alert-primary" role="alert">
                ${requestScope.message}
        </div>
    </c:if>
    <%
        int questionNumber = 1;
    %>
    <c:forEach items="${requestScope.que}" var="q">

        <div class="mb-3">
            <h6><label><%=questionNumber%>-й вопрос. ${q.title}</label></h6>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="a" name="checked" value=<%="1" + questionNumber%>>
                <label class="form-check-label" for="a">
                        ${q.answers.get(0)}
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="b" name="checked" value=<%="2" + questionNumber%>>
                <label class="form-check-label" for="b">
                        ${q.answers.get(1)}
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="c" name="checked" value=<%="3" + questionNumber%>>
                <label class="form-check-label" for="d">
                        ${q.answers.get(2)}
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="d" name="checked" value=<%="4" + questionNumber%>>
                <label class="form-check-label" for="d">
                        ${q.answers.get(3)}
                </label>
            </div>
            <%
                questionNumber++;
            %>
        </div>
    </c:forEach>
    <button type="submit" class="btn btn-primary">Посмотреть результат</button>
</form>
<a href="/"><button class="btn btn-primary">В меню</button></a>
</body>
</html>