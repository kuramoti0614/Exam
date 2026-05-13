<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">成績一覧（科目別）</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">

        <section class="container mt-4">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                成績一覧（科目）
            </h2>

            <!-- 科目名 -->
            <c:if test="${not empty subject}">
                <div class="mb-2 fw-bold">
                    科目：${subject.name}
                </div>
            </c:if>

            <!-- 成績一覧 -->
            <c:if test="${not empty testList}">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>入学年度</th>
                            <th>クラス</th>
                            <th>学生番号</th>
                            <th>氏名</th>
                            <th>1回</th>
                            <th>2回</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="t" items="${testList}">
                            <tr>
                                <td>${t.entYear}</td>
                                <td>${t.classNum}</td>
                                <td>${t.studentNo}</td>
                                <td>${t.studentName}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${t.points[1] != null}">
                                            ${t.points[1]}
                                        </c:when>
                                        <c:otherwise>-</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${t.points[2] != null}">
                                            ${t.points[2]}
                                        </c:when>
                                        <c:otherwise>-</c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            
<c:if test="${param.subjectCd != null}">
    <c:if test="${empty testList}">
        <div class="text-warning fw-bold mt-3">
            成績情報が存在しませんでした
        </div>
    </c:if>
</c:if>
            

        </section>

    </c:param>
</c:import>