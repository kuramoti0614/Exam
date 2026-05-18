<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">

        <section>

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                科目情報変更
            </h2>

            <form action="SubjectUpdateExecute.action" method="post">

                <!-- 科目コード -->
                <div class="mb-3">
                    <label for="cd">科目コード</label><br>

                    <input type="text"
                           id="cd"
                           name="cd"
                           value="${subject.cd}"
                           readonly />
                </div>

                <!-- エラーメッセージ（重要：1つに統一） -->
                <c:if test="${not empty errorMessage}">
                    <div class="text-warning mb-3">
                        ${errorMessage}
                    </div>
                </c:if>

                <!-- 科目名 -->
                <div class="mb-3">
                    <label for="name">科目名</label><br>

                    <input type="text"
                           id="name"
                           name="name"
                           value="${subject.name}"
                           required />
                </div>

                <!-- 更新ボタン -->
                <div>
                    <input class="btn btn-primary"
                           type="submit"
                           value="変更" />
                </div>

            </form>

            <a href="SubjectList.action">戻る</a>

        </section>

    </c:param>

</c:import>