<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="content">

<style>

body{
    margin:0;
    background-color:#f5f5f5;
    font-family:sans-serif;
}

/* メイン */
.main{
    width:95%;
    max-width:900px;
    margin:30px auto;
    background-color:white;
    padding:20px;
    box-sizing:border-box;
}

/* タイトル */
.title{
    background-color:#e9e9e9;
    padding:10px;
    font-size:32px;
    font-weight:bold;
    margin-bottom:15px;
    word-break:break-word;
}

/* 完了メッセージ */
.message{
    background-color:#8fd1a8;
    color:#333;
    padding:12px;
    text-align:center;
    margin-bottom:40px;
    word-break:break-word;
}

/* 戻るリンク */
.back-link{
    margin-top:20px;
}

.back-link a{
    color:#4a78ff;
    text-decoration:none;
}

.back-link a:hover{
    text-decoration:underline;
}

</style>

<div class="main">

    <!-- タイトル -->
    <div class="title">
        科目情報削除
    </div>

    <!-- 完了メッセージ -->
    <div class="message">
        削除が完了しました
    </div>

    <!-- 一覧へ戻る -->
    <div class="back-link">

        <a href="SubjectList.action">
            科目一覧
        </a>

    </div>

</div>

    </c:param>

</c:import>