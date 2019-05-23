<%-- 
    Document   : statistic
    Created on : May 22, 2019, 10:26:07 AM
    Author     : Melnikov
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Статистика</title>
    </head>
    <body>
        <h1>Книги читаемые</h1>
        <form action="showStatistic" method="POST">
            Период от:
            ${dateFrom}
            День:
            <select name="fromDay">
                <c:forEach begin="1" end="31" var="i">
                    <option value="${i}" <c:if test="${i eq fromDay}">selected</c:if>>${i}</option>
                </c:forEach>
            </select>
            Месяц:
            <select name="fromMonth">
                <c:forEach begin="1" end="12" var="i">
                    <option value="${i}" <c:if test="${i eq fromMonth}">selected</c:if>>${i}</option>
                </c:forEach>
            </select>
            Год:
            <select name="fromYear">
                <c:forEach begin="2014" end="2019" var="i">
                    <option value="${i}" <c:if test="${i eq fromYear}">selected</c:if>>${i}</option>
                </c:forEach>
            </select>
            <br> Период до: ${dateTo}
            День:
            <select name="toDay">
                <c:forEach begin="1" end="31" var="i">
                    <option value="${i}" <c:if test="${i eq toDay}">selected</c:if>>${i}</option>
                </c:forEach>
            </select>
            Месяц:
            <select name="toMonth">
                <c:forEach begin="1" end="12" var="i">
                    <option value="${i}" <c:if test="${i eq toMonth}">selected</c:if>>${i}</option>
                </c:forEach>
            </select>
            Год:
            <select name="toYear">
                <c:forEach begin="2014" end="2019" var="i">
                    <option value="${i}" <c:if test="${i eq toYear}">selected</c:if>>${i}</option>
                </c:forEach>
            </select>
            <br>
            <input type="submit" name="timeRange" value="Показать">
        
        <br>
        В период от ${fromDay}.${fromMonth}.${fromYear} до ${toDay}.${toMonth}.${toYear} были взяты следующие книги:
        <c:forEach var="history" items="${listHistories}">
            <p>"${history.book.author}. ${history.book.nameBook}" - книгу брал: ${history.user.name} ${history.user.surname}</p>
        </c:forEach>
            <h2>Книги по популярности</h2>
            <c:forEach var="entry" items="${sortedMapBooksRate}" varStatus="count">
            <p>${count.index+1}. "${entry.key.author}. ${entry.key.nameBook}</p>
        </c:forEach>  
        <input type="submit" name="popBooks" value="Показать">    
       </form>    
            
    </body>
</html>