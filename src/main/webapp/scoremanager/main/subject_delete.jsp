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

/* 全体 */
.wrapper{
    width:95%;
    margin:20px auto;
    display:flex;
    gap:20px;
    flex-wrap:wrap;
}

/* 右側 */
.content{
    flex:1;
    background-color:white;
    padding:20px;
    min-width:300px;
}

/* タイトル */
.title{
    background-color:#e5e5e5;
    padding:15px;
    font-size:36px;
    font-weight:bold;
    margin-bottom:30px;
}

/* メッセージ */
.message{
    font-size:18px;
    margin-bottom:30px;
    word-break:break-word;
}

/* 削除ボタン */
.delete-btn{
    background-color:#ef4444;
    color:white;
    border:none;
    padding:10px 20px;
    border-radius:5px;
    cursor:pointer;
    margin-bottom:30px;
}

.delete-btn:hover{
    opacity:0.8;
}

/* 戻る */
.back-link a{
    color:#2563eb;
    text-decoration:none;
}

.back-link a:hover{
    text-decoration:underline;
}

</style>

<div class="wrapper">

    <!-- 右コンテンツ -->
    <div class="content">

        <!-- タイトル -->
        <div class="title">
            科目情報削除
        </div>

        <!-- メッセージ -->
        <div class="message">
            「${subject.name}(${subject.cd})」を削除してもよろしいですか
        </div>

        <!-- 削除フォーム -->
        <form action="SubjectDeleteExecute.action"
              method="post">

            <!-- hidden -->
            <input
                type="hidden"
                name="subject_cd"
                value="${subject.cd}">

            <input
                type="hidden"
                name="subject_name"
                value="${subject.name}">

            <!-- 削除 -->
            <input
                type="submit"
                value="削除"
                class="delete-btn">

        </form>

        <!-- 戻る -->
        <div class="back-link">

            <a href="SubjectList.action">
                戻る
            </a>

        </div>

    </div>

</div>

    </c:param>

</c:import>