package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import bean.Student;
import bean.Test;

public class TestDao extends Dao {

	public List<Test> getTestList(
	        List<Student> studentList,
	        String subjectCd,
	        String count,
	        String schoolCd) throws Exception {

	    List<Test> list = new ArrayList<>();

	    String sql =
	        "SELECT point, class_num " +
	        "FROM test " +
	        "WHERE student_no = ? " +
	        "  AND subject_cd = ? " +
	        "  AND no = ? " +
	        "  AND school_cd = ?";

	    try (
	        Connection con = getConnection();
	        PreparedStatement ps = con.prepareStatement(sql)
	    ) {
	        for (Student stu : studentList) {

	            Test test = new Test();
	            test.setStudent(stu);                 // ← ここが重要
	            stu.setEntYear(stu.getEntYear());    // JSP が必要
	            test.setClassNum(stu.getClassNum());  // JSP が必要

	            // ▼ test テーブルを検索
	            ps.setString(1, stu.getNo());
	            ps.setString(2, subjectCd);
	            ps.setString(3, count);
	            ps.setString(4, schoolCd);

	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                test.setPoint(rs.getInt("point"));
	                test.setClassNum(rs.getString("class_num"));
	            } else {
	                test.setPoint(0); // 未登録なら 0 or 空欄
	            }

	            list.add(test);
	        }
	    }

	    return list;
	}
	
	
	
	public Set<Integer> getCountSet(String schoolCd) throws Exception {
	    Set<Integer> set = new TreeSet<>();

	    String sql = "SELECT DISTINCT no FROM test WHERE school_cd = ? ORDER BY no";

	    try (
	        Connection con = getConnection();
	        PreparedStatement ps = con.prepareStatement(sql)
	    ) {
	        ps.setString(1, schoolCd);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            set.add(rs.getInt("no"));
	        }
	    }
	    return set;
	}

	
	
	
	
}

