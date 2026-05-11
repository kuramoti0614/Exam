<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成績一覧</title>

<style>

body{
    margin:0;
    font-family:sans-serif;
    background-color:#f5f5f5;
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
    font-size:32px;
    font-weight:bold;
    margin-bottom:15px;
}

.search-box{
    border:1px solid #dcdcdc;
    border-radius:5px;
    padding:20px;
    margin-bottom:20px;
}

.form-row{
    display:flex;
    align-items:flex-end;
    gap:20px;
    margin-bottom:15px;
}

.item{
    display:flex;
    flex-direction:column;
}

label{
    font-size:14px;
    margin-bottom:5px;
}

select,
input{
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

.line{
    border-top:1px solid #ddd;
    margin:15px 0;
}

.subject-name{
    margin-bottom:10px;
    font-size:18px;
}

table{
    width:100%;
    border-collapse:collapse;
    background-color:white;
}

th{
    border-bottom:2px solid #ddd;
    padding:10px;
    text-align:left;
}

td{
    border-bottom:1px solid #ddd;
    padding:10px;
}

</style>
</head>

<body>

<div class="main">

    <div class="title">
        成績一覧（科目）
    </div>

    <!-- 検索ボックス -->
    <div class="search-box">

        <!-- 科目情報 -->
        <form action="" method="post">

            <div class="form-row">

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

                <div class="item">

                    <button type="submit">
                        検索
                    </button>

                </div>

            </div>

        </form>

        <div class="line"></div>

        <!-- 学生情報 -->
        <form action="" method="post">

            <div class="form-row">

                <div class="item">

                    <label>学生番号</label>

                    <input type="text"
                           name="studentNo"
                           placeholder="学生番号を入力してください">

                </div>

                <div class="item">

                    <button type="submit">
                        検索
                    </button>

                </div>

            </div>

        </form>

    </div>

    <!-- 検索後だけ表示 -->
    <c:if test="${not empty testList}">

        <!-- 科目名 -->
        <div class="subject-name">
            科目：${subject.name}
        </div>

        <!-- 一覧 -->
        <table>

            <tr>
                <th>入学年度</th>
                <th>クラス</th>
                <th>学生番号</th>
                <th>氏名</th>
                <th>1回</th>
                <th>2回</th>
            </tr>

            <c:forEach var="test"
                       items="${testList}">

                <tr>

                    <td>${test.entYear}</td>

                    <td>${test.classNum}</td>

                    <td>${test.studentNo}</td>

                    <td>${test.studentName}</td>

                    <td>${test.point1}</td>

                    <td>${test.point2}</td>

                </tr>

            </c:forEach>

        </table>

    </c:if>

</div>

</body>
</html>