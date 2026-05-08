<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成績登録</title>

<style>

body{
    margin:0;
    background-color:#f5f5f5;
    font-family:sans-serif;
}

.main{
    width:900px;
    margin:30px auto;
    background-color:white;
    padding:20px;
}

.title{
    background-color:#e9e9e9;
    padding:10px;
    font-size:30px;
    font-weight:bold;
    margin-bottom:20px;
}

.search-box{
    border:1px solid #dcdcdc;
    border-radius:5px;
    padding:20px;
}

.form-row{
    display:flex;
    align-items:flex-end;
    gap:20px;
}

.item{
    display:flex;
    flex-direction:column;
}

label{
    margin-bottom:5px;
    font-size:14px;
}

select{
    width:180px;
    height:35px;
    border:1px solid #ccc;
    border-radius:5px;
    padding:5px;
}

button{
    width:70px;
    height:35px;
    border:none;
    border-radius:5px;
    background-color:#6c757d;
    color:white;
    cursor:pointer;
}

</style>
</head>

<body>

<div class="main">

    <div class="title">
        成績管理
    </div>

    <div class="search-box">

        <form action="" method="post">

            <div class="form-row">

                <!-- 入学年度 -->
                <div class="item">

                    <label>入学年度</label>

                    <select name="entYear">

                        <option value="">--------</option>

                        <c:forEach var="year"
                                   items="${entYearSet}">

                            <option value="${year}">
                                ${year}
                            </option>

                        </c:forEach>

                    </select>

                </div>

                <!-- クラス -->
                <div class="item">

                    <label>クラス</label>

                    <select name="classNum">

                        <option value="">--------</option>

                        <c:forEach var="c"
                                   items="${classNumSet}">

                            <option value="${c}">
                                ${c}
                            </option>

                        </c:forEach>

                    </select>

                </div>

                <!-- 科目 -->
                <div class="item">

                    <label>科目</label>

                    <select name="subject">

                        <option value="">--------</option>

                        <c:forEach var="sub"
                                   items="${subjectList}">

                            <option value="${sub.cd}">
                                ${sub.name}
                            </option>

                        </c:forEach>

                    </select>

                </div>

                <!-- 回数 -->
                <div class="item">

                    <label>回数</label>

                    <select name="count">

                        <option value="">--------</option>
                        <option value="1">1</option>
                        <option value="2">2</option>

                    </select>

                </div>

                <!-- ボタン -->
                <div class="item">

                    <button type="submit">
                        検索
                    </button>

                </div>

            </div>

        </form>

    </div>

</div>

</body>
</html>