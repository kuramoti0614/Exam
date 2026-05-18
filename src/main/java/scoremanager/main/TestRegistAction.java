package scoremanager.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Set;

import DAO.Dao;
import DAO.StudentDao;
import DAO.SubjectDao;
import DAO.TestDao;
import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest request,
                        HttpServletResponse response)
            throws Exception {

        request.setCharacterEncoding("UTF-8");

        // ▼ 検索条件
        String entYear  = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        String subject  = request.getParameter("subject");
        String countStr = request.getParameter("count");
        String schoolCd = request.getParameter("schoolCd");

        // ▼ 初回アクセス対策
        if (schoolCd == null || schoolCd.isEmpty()) {
            schoolCd = "oom";
        }

        // ▼ DAO
        StudentDao studentDao = new StudentDao();
        SubjectDao subjectDao = new SubjectDao();
        TestDao testDao = new TestDao();

        // ▼ プルダウン用データ
        Set<String> entYearSet =
                studentDao.getEntYearSet(schoolCd);

        Set<String> classNumSet =
                studentDao.getClassNumSet(schoolCd);

        List<Subject> subjectList =
                subjectDao.filterBySchool(schoolCd);

        Set<Integer> countList =
                testDao.getCountSet(schoolCd);

        // =====================================================
        // ▼ 登録処理
        // =====================================================
        String[] studentNoList =
                request.getParameterValues("studentNoList");

        if (studentNoList != null) {

            int count = Integer.parseInt(countStr);

            try (
                Connection con =
                        new Dao().getConnection();

                PreparedStatement insertSt =
                        con.prepareStatement(
                            "INSERT INTO test "
                          + "(student_no, subject_cd, school_cd, no, point, class_num) "
                          + "VALUES (?, ?, ?, ?, ?, ?)"
                        );

                PreparedStatement updateSt =
                        con.prepareStatement(
                            "UPDATE test SET point=? "
                          + "WHERE student_no=? "
                          + "AND subject_cd=? "
                          + "AND school_cd=? "
                          + "AND no=?"
                        );

            ) {

                for (String studentNo : studentNoList) {

                    String pointStr =
                            request.getParameter(
                                    "point_" + studentNo);

                    int point;

                    // ▼ 入力チェック
                    try {

                        point =
                                Integer.parseInt(pointStr);

                        // ▼ 0～100チェック
                        if (point < 0 || point > 100) {

                            request.setAttribute(
                                    "errorMessage",
                                    "0～100の値で設定してください"
                            );

                            // ▼ 再表示用
                            List<Student> studentList =
                                    studentDao.filter(
                                            entYear,
                                            classNum,
                                            schoolCd
                                    );

                            List<Test> testList =
                                    testDao.getTestList(
                                            studentList,
                                            subject,
                                            countStr,
                                            schoolCd
                                    );

                            request.setAttribute(
                                    "testList",
                                    testList
                            );

                            request.setAttribute(
                                    "subjectName",
                                    subjectDao.get(
                                            subject,
                                            new School()
                                    ).getName()
                            );

                            request.setAttribute(
                                    "entYear",
                                    entYear
                            );

                            request.setAttribute(
                                    "classNum",
                                    classNum
                            );

                            request.setAttribute(
                                    "subject",
                                    subject
                            );

                            request.setAttribute(
                                    "count",
                                    countStr
                            );

                            request.setAttribute(
                                    "schoolCd",
                                    schoolCd
                            );

                            request.setAttribute(
                                    "entYearSet",
                                    entYearSet
                            );

                            request.setAttribute(
                                    "classNumSet",
                                    classNumSet
                            );

                            request.setAttribute(
                                    "subjectList",
                                    subjectList
                            );

                            request.setAttribute(
                                    "countList",
                                    countList
                            );

                            request.getRequestDispatcher(
                                    "test_regist.jsp"
                            ).forward(request, response);

                            return;
                        }

                    } catch (NumberFormatException e) {

                        request.setAttribute(
                        		"error_" + studentNo,
                                "点数は0～100の整数で入力してください"
                        );

                        request.getRequestDispatcher(
                                "test_regist.jsp"
                        ).forward(request, response);

                        return;
                    }

                    // ▼ UPDATE
                    updateSt.setInt(1, point);
                    updateSt.setString(2, studentNo);
                    updateSt.setString(3, subject);
                    updateSt.setString(4, schoolCd);
                    updateSt.setInt(5, count);

                    int updated =
                            updateSt.executeUpdate();

                    // ▼ INSERT
                    if (updated == 0) {

                        insertSt.setString(1, studentNo);
                        insertSt.setString(2, subject);
                        insertSt.setString(3, schoolCd);
                        insertSt.setInt(4, count);
                        insertSt.setInt(5, point);
                        insertSt.setString(6, classNum);

                        insertSt.executeUpdate();
                    }
                }
            }

            // ▼ 完了画面
            request.getRequestDispatcher(
                    "test_regist_done.jsp"
            ).forward(request, response);

            return;
        }

        // =====================================================
        // ▼ 初回表示
        // =====================================================
        if (entYear == null || entYear.isEmpty()) {

            entYear = "";
            classNum = "";
            subject = "";
            countStr = "";

            request.setAttribute("entYear", entYear);
            request.setAttribute("classNum", classNum);
            request.setAttribute("subject", subject);
            request.setAttribute("count", countStr);

            request.setAttribute(
                    "entYearSet",
                    entYearSet
            );

            request.setAttribute(
                    "classNumSet",
                    classNumSet
            );

            request.setAttribute(
                    "subjectList",
                    subjectList
            );

            request.setAttribute(
                    "countList",
                    countList
            );

            request.getRequestDispatcher(
                    "test_regist.jsp"
            ).forward(request, response);

            return;
        }

        // =====================================================
        // ▼ 検索処理
        // =====================================================

        // ▼ 学生一覧
        List<Student> studentList =
                studentDao.filter(
                        entYear,
                        classNum,
                        schoolCd
                );

        // ▼ 成績一覧
        List<Test> testList =
                testDao.getTestList(
                        studentList,
                        subject,
                        countStr,
                        schoolCd
                );

        // ▼ 科目名
        School school = new School();
        school.setCd(schoolCd);

        Subject sub =
                subjectDao.get(subject, school);

        request.setAttribute(
                "subjectName",
                sub.getName()
        );

        // ▼ 検索結果
        request.setAttribute(
                "studentList",
                studentList
        );

        request.setAttribute(
                "testList",
                testList
        );

        // ▼ 値保持
        request.setAttribute("entYear", entYear);
        request.setAttribute("classNum", classNum);
        request.setAttribute("subject", subject);
        request.setAttribute("count", countStr);
        request.setAttribute("schoolCd", schoolCd);

        // ▼ プルダウン
        request.setAttribute(
                "entYearSet",
                entYearSet
        );

        request.setAttribute(
                "classNumSet",
                classNumSet
        );

        request.setAttribute(
                "subjectList",
                subjectList
        );

        request.setAttribute(
                "countList",
                countList
        );

        // ▼ JSPへ
        request.getRequestDispatcher(
                "test_regist.jsp"
        ).forward(request, response);
    }
}