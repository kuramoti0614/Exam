<%-- 科目登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="content">

		<section>

			<!-- タイトル -->
			<h2 class="h3 mb-4 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
				科目情報登録
			</h2>

			<!-- フォーム -->
			<form action="SubjectCreateExecute.action" method="post">

				<!-- 科目コード -->
				<div class="mb-4">

					<label for="cd" class="form-label">
						科目コード
					</label>

					<input
						type="text"
						id="cd"
						name="cd"
						class="form-control"
						value="${cd}"
						placeholder="科目コードを入力してください"
						required
						maxlength="10">

				</div>

				<!-- エラーメッセージ -->
				<div class="text-warning mb-3">
					${errors.get("cd")}
				</div>

				<!-- 科目名 -->
				<div class="mb-4">

					<label for="name" class="form-label">
						科目名
					</label>

					<input
						type="text"
						id="name"
						name="name"
						class="form-control"
						value="${name}"
						placeholder="科目名を入力してください"
						required
						maxlength="30">

				</div>

				<!-- エラーメッセージ -->
				<div class="text-warning mb-3">
					${errors.get("name")}
				</div>

				<!-- 登録ボタン -->
				<div class="mb-3">

					<button
						type="submit"
						class="btn btn-primary">

						登録

					</button>

				</div>

			</form>

			<!-- 戻る -->
			<a href="SubjectList.action">
				戻る
			</a>

		</section>

	</c:param>

</c:import>