<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成績参照</title>

<style>

body{
    font-family: sans-serif;
    background-color: white;
}

.main{
    width: 1000px;
    margin: 30px auto;
}

/* 成績参照の部分 */
.title{
    background-color: #eeeeee;
    padding: 15px 20px;
    font-size: 32px;
    font-weight: bold;
    margin-bottom: 20px;
}

/* 白いボックス */
.box{
    border: 1px solid #dcdcdc;
    border-radius: 5px;
    background-color: white;
    padding: 25px;
}

.row{
    display: flex;
    align-items: center;
    gap: 20px;
}

.left-title{
    width: 100px;
    font-weight: bold;
}

.item{
    display: flex;
    flex-direction: column;
}

.item label{
    margin-bottom: 8px;
    font-size: 14px;
}

select,
input[type="text"]{
    width: 180px;
    padding: 8px;
    border: 1px solid #cccccc;
    border-radius: 4px;
}

.btn{
    margin-top: 24px;
    padding: 8px 18px;
    border: none;
    border-radius: 4px;
    background-color: #6c757d;
    color: white;
    cursor: pointer;
}

.line{
    border-top: 1px solid #e0e0e0;
    margin: 25px 0;
}

.message{
    margin-top: 20px;
    color: #46b8da;
    font-size: 14px;
}

</style>

</head>
<body>

<div class="main">

    <!-- タイトル -->
    <div class="title">
        成績参照
    </div>

    <!-- 白枠 -->
    <div class="box">

        <!-- 科目情報 -->
        <form action="" method="post">

            <div class="row">

                <div class="left-title">
                    科目情報
                </div>

                <div class="item">
                    <label>入学年度</label>
                    <select name="entYear">
                        <option value="">--------</option>
                    </select>
                </div>

                <div class="item">
                    <label>クラス</label>
                    <select name="classNum">
                        <option value="">--------</option>
                    </select>
                </div>

                <div class="item">
                    <label>科目</label>
                    <select name="subject">
                        <option value="">--------</option>
                    </select>
                </div>


            </div>

        </form>

        <div class="line"></div>

        <!-- 学生情報 -->
        <form action="TestListStudentExecute.action" method="post">

            <div class="row">

                <div class="left-title">
                    学生情報
                </div>

                <div class="item">
                    <label>学生番号</label>

                    <input type="text"
                           name="studentNo"
                           placeholder="学生番号を入力してください">
                </div>

                <input type="submit"
                       value="検索"
                       class="btn">

            </div>

        </form>

    </div>

    <div class="message">
        科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
    </div>

</div>

</body>
</html>