<%-- 学生情報変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section>
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
			<form action="StudentUpdateExecute.action" method="get">

    <div>
        <label for="ent_year">科目コード</label><br>
        <input type="text" id="ent_year" name="ent_year"
               value="${student.entYear}" readonly />
    </div>

    <div>
        <label for="no">科目名</label><br>
        <input type="text" id="no" name="no"
               value="${student.no}" readonly />
    </div>

 
    <div>
        <input class="btn btn-primary" type="submit" value="変更" />
    </div>

</form>
			
			<a href="StudentList.action">戻る</a>
		</section>
	</c:param>
</c:import>