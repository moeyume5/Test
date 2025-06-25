package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.TestListStudent;

public class TestListStudentDao extends Dao {

    public List<TestListStudent> filter(String studentNo) throws Exception {
        List<TestListStudent> testListStudents = new ArrayList<>();
        String sql = "SELECT s.name AS subjectName, s.cd AS subjectCd, t.no AS num, t.point " +
                     "FROM test t " +
                     "JOIN subject s ON t.subject_cd = s.cd AND t.school_cd = s.school_cd " +
                     "WHERE t.student_no = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, studentNo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TestListStudent testListStudent = new TestListStudent();
                    testListStudent.setSubjectName(rs.getString("subjectName"));
                    testListStudent.setSubjectCd(rs.getString("subjectCd"));
                    testListStudent.setNum(rs.getInt("num"));
                    testListStudent.setPoint(rs.getInt("point"));
                    testListStudents.add(testListStudent);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("データベースエラーが発生しました。");
        }

        return testListStudents;
    }
}
