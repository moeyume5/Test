package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    public List<TestListSubject> filter(int entYear, String classNum, String subjectCd, String schoolCd) throws Exception {
        List<TestListSubject> testListSubjects = new ArrayList<>();
        String sql = "SELECT t.subject_cd, sub.name AS subject_name, st.class_num, st.ent_year, st.no AS student_no, st.name AS student_name, "
                   + "t.no AS test_num, t.point AS score "
                   + "FROM test t "
                   + "JOIN subject sub ON t.subject_cd = sub.cd "
                   + "JOIN student st ON t.student_no = st.no "
                   + "WHERE st.ent_year = ? "
                   + "AND st.class_num = ? "
                   + "AND t.subject_cd = ? "
                   + "AND st.school_cd = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // パラメータの設定とログ出力
            stmt.setInt(1, entYear);
            stmt.setString(2, classNum);
            stmt.setString(3, subjectCd);
            stmt.setString(4, schoolCd);
            System.out.println("Executing SQL: " + sql);
            System.out.println("Parameters: [" + entYear + ", " + classNum + ", " + subjectCd + ", " + schoolCd + "]");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TestListSubject testListSubject = new TestListSubject();
                    testListSubject.setEntYear(rs.getInt("ent_year"));
                    testListSubject.setClassNum(rs.getString("class_num"));
                    testListSubject.setSubjectCd(rs.getString("subject_cd"));
                    testListSubject.setSubjectName(rs.getString("subject_name"));
                    testListSubject.setStudentNo(rs.getString("student_no"));
                    testListSubject.setStudentName(rs.getString("student_name"));
                    testListSubject.putPoint(rs.getInt("test_num"), rs.getInt("score"));
                    testListSubjects.add(testListSubject);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("データベースエラーが発生しました。");
        }

        return testListSubjects;
    }
}
