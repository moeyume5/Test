package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    private String baseSql = "SELECT Test.*, Student.*, Subject.*, School.* FROM test Test "
                             + "JOIN student Student ON Test.student_no = Student.no "
                             + "JOIN subject Subject ON Test.subject_cd = Subject.cd "
                             + "JOIN school School ON Test.school_cd = School.cd "
                             + "WHERE Test.school_cd = ?";

    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        String sql = baseSql + " AND Test.student_no = ? AND Test.subject_cd = ? AND Test.no = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, school.getCd());
            statement.setString(2, student.getNo());
            statement.setString(3, subject.getCd());
            statement.setInt(4, no);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapTest(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new Exception("テストの取得中にエラーが発生しました", e);
        }
    }

    public List<Test> postFilter(ResultSet resultSet, School school) throws SQLException {
        List<Test> list = new ArrayList<>();
        while (resultSet.next()) {
            Test test = mapTest(resultSet);
            if (test.getSchool().getCd().equals(school.getCd())) {
                list.add(test);
            }
        }
        return list;
    }

    public List<Test> filter(int entYear, String classNum, Subject subject, int no, School school) throws Exception {
        StringBuilder sqlBuilder = new StringBuilder(baseSql);
        List<Object> params = new ArrayList<>();
        params.add(school.getCd());

        if (entYear > 0) {
            sqlBuilder.append(" AND Student.ent_year = ?");
            params.add(entYear);
        }
        if (classNum != null && !classNum.isEmpty()) {
            sqlBuilder.append(" AND Test.class_num = ?");
            params.add(classNum);
        }
        if (subject != null && subject.getCd() != null && !subject.getCd().isEmpty()) {
            sqlBuilder.append(" AND Test.subject_cd = ?");
            params.add(subject.getCd());
        }
        if (no > 0) {
            sqlBuilder.append(" AND Test.no = ?");
            params.add(no);
        }

        String sql = sqlBuilder.toString();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }

            System.out.println("Executing SQL: " + sql);
            System.out.println("Parameters: " + params);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Test> testList = postFilter(resultSet, school);
                System.out.println("Test List Size: " + testList.size());
                return testList;
            }
        } catch (SQLException e) {
            throw new Exception("テストの検索中にエラーが発生しました", e);
        }
    }

    public boolean save(List<Test> list) throws Exception {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Test test : list) {
                    if (!save(test, connection)) {
                        throw new SQLException("テストの保存中にエラーが発生しました");
                    }
                }
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                throw new Exception("テストの保存中にエラーが発生しました", e);
            }
        }
    }

    public boolean save(Test test, Connection connection) throws SQLException {
        if (test == null) {
            throw new IllegalArgumentException("Test object cannot be null");
        }
        if (test.getStudent() == null) {
            throw new IllegalArgumentException("Student in Test object cannot be null");
        }
        if (test.getSubject() == null) {
            throw new IllegalArgumentException("Subject in Test object cannot be null");
        }
        if (test.getSchool() == null) {
            throw new IllegalArgumentException("School in Test object cannot be null");
        }

        String checkSql = "SELECT COUNT(*) FROM test WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
            checkStatement.setString(1, test.getStudent().getNo());
            checkStatement.setString(2, test.getSubject().getCd());
            checkStatement.setString(3, test.getSchool().getCd());
            checkStatement.setInt(4, test.getNo());
            try (ResultSet rs = checkStatement.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // レコードが存在する場合は更新
                    String updateSql = "UPDATE test SET point = ?, class_num = ? WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                        updateStatement.setInt(1, test.getPoint());
                        updateStatement.setString(2, test.getClassNum());
                        updateStatement.setString(3, test.getStudent().getNo());
                        updateStatement.setString(4, test.getSubject().getCd());
                        updateStatement.setString(5, test.getSchool().getCd());
                        updateStatement.setInt(6, test.getNo());
                        return updateStatement.executeUpdate() > 0;
                    }
                } else {
                    // レコードが存在しない場合は挿入
                    String insertSql = "INSERT INTO test (student_no, subject_cd, school_cd, no, point, class_num) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                        insertStatement.setString(1, test.getStudent().getNo());
                        insertStatement.setString(2, test.getSubject().getCd());
                        insertStatement.setString(3, test.getSchool().getCd());
                        insertStatement.setInt(4, test.getNo());
                        insertStatement.setInt(5, test.getPoint());
                        insertStatement.setString(6, test.getClassNum());
                        return insertStatement.executeUpdate() > 0;
                    }
                }
            }
        }
    }

    public boolean delete(List<Test> list) throws Exception {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Test test : list) {
                    if (!delete(test, connection)) {
                        throw new SQLException("テストの削除中にエラーが発生しました");
                    }
                }
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                throw new Exception("テストの削除中にエラーが発生しました", e);
            }
        }
    }

    public boolean delete(Test test, Connection connection) throws SQLException {
        String sql = "DELETE FROM test WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, test.getStudent().getNo());
            statement.setString(2, test.getSubject().getCd());
            statement.setString(3, test.getSchool().getCd());
            statement.setInt(4, test.getNo());
            return statement.executeUpdate() > 0;
        }
    }

    private Test mapTest(ResultSet resultSet) throws SQLException {
        Test test = new Test();

        // Create and set Student object
        Student student = new Student();
        student.setNo(resultSet.getString("student_no"));
        student.setName(resultSet.getString("name")); // assuming column name
        student.setEntYear(resultSet.getInt("ent_year"));
        student.setClassNum(resultSet.getString("class_num"));
        student.setAttend(resultSet.getBoolean("is_attend"));
        student.setSchool(resultSet.getString("school_cd"));
        test.setStudent(student);

        // Create and set Subject object
        Subject subject = new Subject();
        subject.setCd(resultSet.getString("subject_cd"));
        subject.setName(resultSet.getString("name")); // assuming column name
        test.setSubject(subject);

        // Create and set School object
        School school = new School();
        school.setCd(resultSet.getString("school_cd"));
        school.setName(resultSet.getString("name")); // assuming column name
        test.setSchool(school);

        // Set other Test properties
        test.setNo(resultSet.getInt("no"));
        test.setPoint(resultSet.getInt("point"));
        test.setClassNum(resultSet.getString("class_num"));

        return test;
    }
}
