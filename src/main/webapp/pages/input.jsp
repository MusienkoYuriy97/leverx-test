<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Input Question</title>
</head>
<body>
<jsp:include page="_header.jsp"/>
<form action="/input" method="post">
    <div class="mb-3 row" >
        <label for="title" class="form-label">Введите текст вопроса</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" id="title" aria-describedby="emailHelp" name = "title">
        </div>
    </div>
    <div class="mb-3 row">
        <label for="a" class="form-label">Введите текст 1 варианта ответа</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" id="a" name = "a">
        </div>
    </div>
    <div class="mb-3 row">
        <label for="b" class="form-label">Введите текст 2 варианта ответа</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" id="b" name = "b">
        </div>
    </div>
    <div class="mb-3 row">
        <label for="c" class="form-label">Введите текст 3 варианта ответа</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" id="c" name = "c">
        </div>
    </div>
    <div class="mb-3 row">
        <label for="d" class="form-label">Введите текст 4 варианта ответа</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" id="d" name = "d">
        </div>
    </div>
    <div class="mb-3 row">
        <label for="e" class="form-label">Введите номера правильных ответов через запятую. Нумерация начинается с 1</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" id="e" name = "answerNumbers">
        </div>
    </div>
    <button type="submit" class="btn btn-primary">Сохранить вопрос</button>
</form>
<a href="/"><button class="btn btn-primary">В меню</button></a>
<c:if test="${requestScope.message != null}">
    <div class="alert alert-primary" role="alert">
            ${requestScope.message}
    </div>
</c:if>
</body>
</html>
