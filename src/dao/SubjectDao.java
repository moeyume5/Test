package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Subject; // Subjectクラスのパッケージに合わせてインポートを変更

public class SubjectDao extends Dao {

    private String baseSql = "select * from subject where cd=? and school_cd=?";

    public Subject get(String cd, String school_cd) throws Exception {
        Subject subject = new Subject();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(baseSql)) {
            statement.setString(1, cd);
            statement.setString(2, school_cd);
            try (ResultSet rSet = statement.executeQuery()) {
                if (rSet.next()) {
                    subject.setCd(rSet.getString("cd"));
                    subject.setName(rSet.getString("name"));
                    subject.setSchool_CD(rSet.getString("school_cd"));
                } else {
                    subject = null;
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return subject;
    }

    public List<Subject> filter(String school_cd) throws Exception {
        List<Subject> list = new ArrayList<>();
        String sql = "select * from subject where school_cd=?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, school_cd);
            try (ResultSet rSet = statement.executeQuery()) {
                while (rSet.next()) {
                    Subject subject = new Subject();
                    subject.setCd(rSet.getString("cd"));
                    subject.setName(rSet.getString("name"));
                    subject.setSchool_CD(rSet.getString("school_cd"));
                    list.add(subject);
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return list;
    }

    public boolean save(Subject subject) throws Exception {
        int count;
        String query;

        try (Connection connection = getConnection()) {
            Subject old = get(subject.getCd(), subject.getSchool_CD());
            if (old == null) {
                query = "insert into subject(cd, name, school_cd) values(?, ?, ?)";
            } else {
                query = "update subject set name=? where cd=? and school_cd=?";
            }

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                if (old == null) {
                    statement.setString(1, subject.getCd());
                    statement.setString(2, subject.getName());
                    statement.setString(3, subject.getSchool_CD());
                } else {
                    statement.setString(1, subject.getName());
                    statement.setString(2, subject.getCd());
                    statement.setString(3, subject.getSchool_CD());
                }
                count = statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        }

        return count > 0;
    }

    public boolean delete(String cd, String school_cd) throws Exception {
        int count;
        String query = "delete from subject where cd=? and school_cd=?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cd);
            statement.setString(2, school_cd);
            count = statement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }

        return count > 0;
    }

    public boolean update(Subject subject) throws Exception {
        String sql = "update subject set name=? where cd=? and school_cd=?";
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = getConnection(); // Daoクラスで定義されているメソッドでコネクションを取得する
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, subject.getName());
            stmt.setString(2, subject.getCd());
            stmt.setString(3, subject.getSchool_CD());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("データベースエラーが発生しました。");
        } finally {
            // リソースのクローズ
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean delete2(Subject subject) throws Exception {
        String sql = "delete from subject where cd=? and school_cd=?";
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = getConnection(); // データベース接続を取得
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, subject.getCd());
            stmt.setString(2, subject.getSchool_CD());

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("データベースエラーが発生しました。");
        } finally {
            // リソースのクローズ
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
