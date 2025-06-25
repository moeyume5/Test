package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;

public class StudentDao extends Dao {

    private String baseSql = "select * from student where school_cd=? ";

    public Student get(String no) throws Exception {
        Student student = new Student();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from student where no=?")) {
            statement.setString(1, no);
            try (ResultSet rSet = statement.executeQuery()) {
                if (rSet.next()) {
                    student.setNo(rSet.getString("no"));
                    student.setName(rSet.getString("name"));
                    student.setEntYear(rSet.getInt("ent_year"));
                    student.setClassNum(rSet.getString("class_num"));
                    student.setAttend(rSet.getBoolean("is_attend"));
                    student.setSchool(rSet.getString("school_cd"));
                } else {
                    student = null;
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return student;
    }

    private List<Student> postFilter(ResultSet rSet, String school) throws Exception {
        List<Student> list = new ArrayList<>();
        try {
            while (rSet.next()) {
                Student student = new Student();
                student.setNo(rSet.getString("no"));
                student.setName(rSet.getString("name"));
                student.setEntYear(rSet.getInt("ent_year"));
                student.setClassNum(rSet.getString("class_num"));
                student.setAttend(rSet.getBoolean("is_attend"));
                student.setSchool(school);
                list.add(student);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Student> filter(String school, int entYear, String classNum, boolean isAttend) throws Exception {
        List<Student> list = new ArrayList<>();
        String condition = "and ent_year=? and class_num=?";
        String order = " order by no asc";
        String conditionIsAttend = isAttend ? "and is_attend=true" : "";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order)) {
            statement.setString(1, school);
            statement.setInt(2, entYear);
            statement.setString(3, classNum);
            try (ResultSet rSet = statement.executeQuery()) {
                list = postFilter(rSet, school);
            }
        } catch (Exception e) {
            throw e;
        }

        return list;
    }

    public List<Student> filter(String school, int entYear, boolean isAttend) throws Exception {
        List<Student> list = new ArrayList<>();
        String condition = "and ent_year=?";
        String order = " order by no asc";
        String conditionIsAttend = isAttend ? "and is_attend=true" : "";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order)) {
            statement.setString(1, school);
            statement.setInt(2, entYear);
            try (ResultSet rSet = statement.executeQuery()) {
                list = postFilter(rSet, school);
            }
        } catch (Exception e) {
            throw e;
        }

        return list;
    }

    public List<Student> filter(String school, boolean isAttend) throws Exception {
        List<Student> list = new ArrayList<>();
        String order = " order by no asc";
        String conditionIsAttend = isAttend ? "and is_attend=true" : "";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(baseSql + conditionIsAttend + order)) {
            statement.setString(1, school);
            try (ResultSet rSet = statement.executeQuery()) {
                list = postFilter(rSet, school);
            }
        } catch (Exception e) {
            throw e;
        }

        return list;
    }

    public boolean save(Student student) throws Exception {
        int count;
        String query;

        try (Connection connection = getConnection()) {
            Student old = get(student.getNo());
            if (old == null) {
                query = "insert into student(no, name, ent_year, class_num, is_attend, school_cd) values(?, ?, ?, ?, ?, ?)";
            } else {
                query = "update student set name=?, ent_year=?, class_num=?, is_attend=? where no=?";
            }

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                if (old == null) {
                    statement.setString(1, student.getNo());
                    statement.setString(2, student.getName());
                    statement.setInt(3, student.getEntYear());
                    statement.setString(4, student.getClassNum());
                    statement.setBoolean(5, student.isAttend());
                    statement.setString(6, student.getSchool());
                } else {
                    statement.setString(1, student.getName());
                    statement.setInt(2, student.getEntYear());
                    statement.setString(3, student.getClassNum());
                    statement.setBoolean(4, student.isAttend());
                    statement.setString(5, student.getNo());
                }
                count = statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        }

        return count > 0;
    }

    public boolean isDuplicate(String studentNo) throws Exception {
        String query = "SELECT COUNT(*) FROM student WHERE no=?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, studentNo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public Student findByNo(String studentNo) throws Exception {
        Student student = null;
        String sql = "SELECT * FROM student WHERE no = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, studentNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    student = new Student();
                    student.setNo(rs.getString("no"));
                    student.setName(rs.getString("name"));
                    student.setEntYear(rs.getInt("ent_year"));
                    student.setClassNum(rs.getString("class_num"));
                    student.setAttend(rs.getBoolean("is_attend"));
                    student.setSchool(rs.getString("school_cd"));
                }
            }
        }
        return student;
    }

    public void update(Student student) throws Exception {
        String sql = "UPDATE student SET name = ?, ent_year = ?, class_num = ?, is_attend = ? WHERE no = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setInt(2, student.getEntYear());
            ps.setString(3, student.getClassNum());
            ps.setBoolean(4, student.isAttend());
            ps.setString(5, student.getNo());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
}
