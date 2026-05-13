<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">成績参照</c:param>

    <c:param name="content">
        <section class="container mt-4">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                成績参照
            </h2>

            <!-- ✅ 科目検索 -->
            <form action="TestListSubjectExecute.action" method="post"
                  class="border rounded p-3 mb-4 bg-white">

                <div class="row align-items-center g-3">

                    <div class="col-auto fw-bold">科目情報</div>

                    <!-- 入学年度 -->
                    <div class="col-auto">
                        <select name="entYear" class="form-select">
                            <option value="">--------</option>
                            <c:forEach var="y" items="${entYearSet}">
                                <option value="${y}"
                                    <c:if test="${param.entYear == y}">selected</c:if>>
                                    ${y}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- クラス -->
                    <div class="col-auto">
                        <select name="classNum" class="form-select">
                            <option value="">--------</option>
                            <c:forEach var="c" items="${classNumSet}">
                                <option value="${c}"
                                    <c:if test="${param.classNum == c}">selected</c:if>>
                                    ${c}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- 科目 -->
                    <div class="col-auto">
                        <select name="subjectCd" class="form-select">
                            <option value="">--------</option>
                            <c:forEach var="s" items="${subjectList}">
                                <option value="${s.cd}"
                                    <c:if test="${param.subjectCd == s.cd}">selected</c:if>>
                                    ${s.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-auto">
                        <button class="btn btn-secondary">検索</button>
                    </div>

                </div>
            </form>

            <!-- ✅ 学生検索 -->
            <form action="TestListStudentExecute.action" method="post"
                  class="border rounded p-3 bg-white">

                <div class="row align-items-center g-3">

                    <div class="col-auto fw-bold">学生情報</div>

                    <div class="col-auto">
                        <input type="text" name="studentNo"
                               class="form-control"
                               value="${param.studentNo}"
                               placeholder="学生番号">
                    </div>

                    <div class="col-auto">
                        <button class="btn btn-secondary">検索</button>
                    </div>

                </div>
            </form>

            <!-- ✅ エラー -->
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger mt-3">
                    ${errorMessage}
                </div>
            </c:if>

            <!-- ✅ 科目結果 -->
            <c:if test="${not empty testList}">
                <table class="table table-bordered mt-3">
                    <tr>
                        <th>入学年度</th>
                        <th>クラス</th>
                        <th>学生番号</th>
                        <th>氏名</th>
                        <th>1回</th>
                        <th>2回</th>
                    </tr>

                    <c:forEach var="t" items="${testList}">
                        <tr>
                            <td>${t.entYear}</td>
                            <td>${t.classNum}</td>
                            <td>${t.studentNo}</td>
                            <td>${t.studentName}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${t.point1 != null}">
                                        ${t.point1}
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${t.point2 != null}">
                                        ${t.point2}
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>

        </section>
    </c:param>
</c:import>