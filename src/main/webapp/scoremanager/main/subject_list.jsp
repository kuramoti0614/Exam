<%-- 科目管理一覧JSP --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                科目管理
            </h2>

            <div class="my-2 text-end px-4">
                <a href="SubjectCreate.action">新規登録</a>
            </div>

            <!-- エラーメッセージ -->
            <c:if test="${not empty errors}">
                <div class="mt-2 text-warning px-4">
                    ${errors.f1}
                </div>
            </c:if>

            <!-- 科目一覧テーブル -->
            <table class="table table-bordered mt-3 px-4">
                <thead>
                    <tr>
                        <th>科目コード</th>
                        <th>科目名</th>
                        <th></th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="subject" items="${subjects}">
                        <tr>
                            <td>${subject.cd}</td>
                            <td>${subject.name}</td>
                            <td>
                                <a href="SubjectUpdate.action?cd=${subject.cd}">変更</a>
                                &nbsp;
                                <a href="SubjectDelete.action?cd=${subject.cd}">削除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </section>
    </c:param>
</c:import>