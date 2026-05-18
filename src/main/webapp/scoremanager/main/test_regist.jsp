<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>

    <c:param name="content">

        <div id="wrap_box">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2">
                成績管理
            </h2>

            <div class="border rounded p-4 bg-white">

                <!-- ================= 検索フォーム ================= -->
                <form action="TestRegist.action" method="post">
                    <div class="d-flex align-items-end gap-4">

                        <!-- 入学年度 -->
                        <div>
                            <label>入学年度</label>
                            <select name="entYear" class="form-select">
                                <option value="">--------</option>
                                <c:forEach var="year" items="${entYearSet}">
                                    <option value="${year}"
                                        <c:if test="${entYear eq year}">selected</c:if>>
                                        ${year}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- クラス -->
                        <div>
                            <label>クラス</label>
                            <select name="classNum" class="form-select">
                                <option value="">--------</option>
                                <c:forEach var="c" items="${classNumSet}">
                                    <option value="${c}"
                                        <c:if test="${classNum eq c}">selected</c:if>>
                                        ${c}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- 科目 -->
                        <div>
                            <label>科目</label>
                            <select name="subject" class="form-select">
                                <option value="">--------</option>
                                <c:forEach var="sub" items="${subjectList}">
                                    <option value="${sub.cd}"
                                        <c:if test="${subject eq sub.cd}">selected</c:if>>
                                        ${sub.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- 回数 -->
                        <div>
                            <label>回数</label>
                            <select name="count" class="form-select">
                                <option value="">--------</option>
                                <c:forEach var="n" items="${countList}">
                                    <option value="${n}"
                                        <c:if test="${count eq n}">selected</c:if>>
                                        ${n}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <input type="hidden" name="schoolCd" value="oom">

                        <div>
                            <button type="submit" class="btn btn-secondary">
                                検索
                            </button>
                        </div>

                    </div>
                </form>

                <!-- ================= 検索結果 ================= -->
                <c:if test="${not empty testList}">

                    <div class="mt-4">
                        <p>科目：${subjectName}（${count}回）</p>
                    </div>

                    <!-- ================= 登録フォーム ================= -->
                    <form action="TestRegist.action" method="post">

                        <table class="table table-bordered">
                            <thead class="table-light">
                                <tr>
                                    <th>入学年度</th>
                                    <th>クラス</th>
                                    <th>学生番号</th>
                                    <th>氏名</th>
                                    <th>点数</th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach var="test" items="${testList}">
                                    <tr>
                                        <td>${test.student.entYear}</td>
                                         <td>${test.student.classNum}</td>
                                         <td>${test.student.no}</td>
                                          <td>${test.student.name}</td>
                                        

                                        
                                                   
                                                   <td>
    <input type="text"
           name="point_${test.student.no}"
           value="${test.point}"
           class="form-control"
           style="width:100px;"
           >

    <!-- ★ エラーメッセージ（個別） -->
    <c:if test="${not empty requestScope['error_' += test.student.no]}">
        <div style="color:#f39c12; font-size:13px; margin-top:4px;">
            ${requestScope['error_' += test.student.no]}
        </div>
    </c:if>
</td>
                                                   
                                        </td>

                                        <td hidden>
                                            <input type="hidden"
                                                   name="studentNoList"
                                                   value="${test.student.no}">
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <!-- ★ 検索条件を保持する hidden -->
                        <input type="hidden" name="subject" value="${subject}">
                        <input type="hidden" name="count" value="${count}">
                        <input type="hidden" name="schoolCd" value="oom">
                        <input type="hidden" name="entYear" value="${entYear}">
                        <input type="hidden" name="classNum" value="${classNum}">

                        <button type="submit" class="btn btn-primary">
                            登録して終了
                        </button>

                    </form>

                </c:if>

            </div>
        </div>

    </c:param>
</c:import>
