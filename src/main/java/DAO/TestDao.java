package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao {

    public List<Test> findAll() throws Exception {

        List<Test> list = new ArrayList<>();

        String sql =
            "SELECT student_no, subject_cd, school_cd, no, point, class_num " +
            "FROM test ORDER BY student_no, subject_cd, no";

        try (
            Connection con = new Dao().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {

                Student student = new Student();
                student.setNo(rs.getString("student_no"));

                Subject subject = new Subject();
                subject.setCd(rs.getString("subject_cd"));

                School school = new School();
                school.setCd(rs.getString("school_cd"));

                Test test = new Test();
                test.setStudent(student);
                test.setSubject(subject);
                test.setSchool(school);
                test.setNo(rs.getInt("no"));
                test.setPoint(rs.getInt("point"));
                test.setClassNum(rs.getString("class_num"));

                list.add(test);
            }
        }

        return list;
    }
}
