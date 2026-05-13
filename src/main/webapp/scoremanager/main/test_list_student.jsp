<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">成績一覧（学生別）</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">

        <section class="container mt-4">

            <!-- 画面見出し -->
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                成績一覧（学生）
            </h2>

            <!-- 🔽 検索フォーム -->
            <form method="get" action="StudentScore">
                <div class="row mb-3">
                    <div class="col-auto">
                        <input type="text" name="studentNo"
                               class="form-control"
                               placeholder="学籍番号を入力"
                               value="${param.studentNo}">
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-primary">検索</button>
                    </div>
                </div>
            </form>

            <!-- 学生情報 -->
            <c:if test="${not empty student}">
                <div class="mb-2 fw-bold">
                    氏名：${student.name}（${student.no}）
                </div>
            </c:if>

            <!-- 成績一覧 -->
            <c:if test="${not empty testList}">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>科目名</th>
                            <th>科目コード</th>
                            <th>回数</th>
                            <th>点数</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="t" items="${testList}">
                            <tr>
                                <td>${t.subjectName}</td>
                                <td>${t.subjectCd}</td>
                                <td>${t.num}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${t.point != null}">
                                            ${t.point}
                                        </c:when>
                                        <c:otherwise>-</c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <!-- 🔽 検索後のみメッセージ表示 -->
            <c:if test="${param.studentNo != null}">
                <c:if test="${empty student}">
                    <div class="text-danger fw-bold mt-3">
                        該当する学生が存在しません
                    </div>
                </c:if>

                <c:if test="${not empty student and empty testList}">
                    <div class="text-warning fw-bold mt-3">
                        成績情報が存在しませんでした
                    </div>
                </c:if>
            </c:if>

        </section>

    </c:param>
</c:import>
