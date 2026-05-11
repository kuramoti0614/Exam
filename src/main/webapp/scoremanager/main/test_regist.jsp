<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="content">
		<div id="wrap_box">            <!-- 見出し -->
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2">
                成績管理
            </h2>

            <!-- 白枠 -->
            <div class="border rounded p-4 bg-white">

                <form action="" method="post">

                    <div class="d-flex align-items-end gap-4">

                        <!-- 入学年度 -->
                        <div>

                            <label class="form-label">
                                入学年度
                            </label>

                            <select name="entYear"
                                    class="form-select">

                                <option value="">
                                    --------
                                </option>

                                <c:forEach var="year"
                                           items="${entYearSet}">

                                    <option value="${year}">
                                        ${year}
                                    </option>

                                </c:forEach>

                            </select>

                        </div>

                        <!-- クラス -->
                        <div>

                            <label class="form-label">
                                クラス
                            </label>

                            <select name="classNum"
                                    class="form-select">

                                <option value="">
                                    --------
                                </option>

                                <c:forEach var="c"
                                           items="${classNumSet}">

                                    <option value="${c}">
                                        ${c}
                                    </option>

                                </c:forEach>

                            </select>

                        </div>

                        <!-- 科目 -->
                        <div>

                            <label class="form-label">
                                科目
                            </label>

                            <select name="subject"
                                    class="form-select">

                                <option value="">
                                    --------
                                </option>

                                <c:forEach var="sub"
                                           items="${subjectList}">

                                    <option value="${sub.cd}">
                                        ${sub.name}
                                    </option>

                                </c:forEach>

                            </select>

                        </div>

                        <!-- 回数 -->
                        <div>

                            <label class="form-label">
                                回数
                            </label>

                            <select name="count"
                                    class="form-select">

                                <option value="">
                                    --------
                                </option>

                                <option value="1">
                                    1
                                </option>

                                <option value="2">
                                    2
                                </option>

                            </select>

                        </div>

                        <!-- 検索ボタン -->
                        <div>

                            <button type="submit"
                                    class="btn btn-secondary">

                                検索

                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </c:param>
</c:import>