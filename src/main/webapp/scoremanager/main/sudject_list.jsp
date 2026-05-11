<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>科目別成績一覧</h2>

<table border="1">
    <thead>
        <tr>
            <!-- ①～⑧ ヘッダー -->
            <th>入学年度</th>
            <th>クラス</th>
            <th>学生番号</th>
            <th>氏名</th>
            <th>1回目</th>
            <th>2回目</th>
        </tr>
    </thead>

    <tbody>
        <!-- ②～⑭ データ行 -->
        <c:forEach var="score" items="${score_list}">
            <tr>
                <!-- ③ 入学年度 -->
                <td>${score.student.entYear}</td>

                <!-- ④ クラス -->
                <td>${score.student.classNum}</td>

                <!-- ⑤ 学生番号 -->
                <td>${score.student.no}</td>

                <!-- ⑥ 氏名 -->
                <td>${score.student.name}</td>

                <!-- ⑦ 1回目の点数 -->
                <td>
                    <c:choose>
                        <c:when test="${score.point1 != null}">
                            ${score.point1}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>

                <!-- ⑧ 2回目の点数 -->
                <td>
                    <c:choose>
                        <c:when test="${score.point2 != null}">
                            ${score.point2}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>