<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">成績参照</c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="container mt-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

            <!-- 科目情報 -->
            <form action="" method="post" class="border rounded p-3 mb-4 bg-white">
                <div class="row align-items-center g-3">
                    <div class="col-auto fw-bold">科目情報</div>

                    <div class="col-auto">
                        <label for="entYear" class="form-label mb-1">入学年度</label>
                        <select id="entYear" name="entYear" class="form-select">
                            <option value="">--------</option>
                        </select>
                    </div>

                    <div class="col-auto">
                        <label for="classNum" class="form-label mb-1">クラス</label>
                        <select id="classNum" name="classNum" class="form-select">
                            <option value="">--------</option>
                        </select>
                    </div>

                    <div class="col-auto">
                        <label for="subject" class="form-label mb-1">科目</label>
                        <select id="subject" name="subject" class="form-select">
                            <option value="">--------</option>
                        </select>
                    </div>

                    <div class="col-auto">
                        <button type="submit" class="btn btn-secondary">検索</button>
                    </div>
                </div>
            </form>

<!-- エラーメッセージ表示 -->
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger mt-3">
        ${errorMessage}
    </div>
</c:if>


            <!-- 学生情報 -->
            <form action="TestListStudentExecute.action" method="post" class="border rounded p-3 bg-white">
                <div class="row align-items-center g-3">
                    <div class="col-auto fw-bold">学生情報</div>

                    <div class="col-auto">
                        <label for="studentNo" class="form-label mb-1">学生番号</label>
                        <input type="text" id="studentNo" name="studentNo" class="form-control"
                               placeholder="学生番号を入力してください"required>
                    </div>

                    <div class="col-auto">
                        <button type="submit" class="btn btn-secondary">検索</button>
                    </div>
                </div>
            </form>

            <p class="text-info mt-3">
                科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
            </p>
        </section>
    </c:param>
</c:import>
