package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassNumDao extends Dao {

    private String baseSql = "select * from class_num where school_cd=?";

    public List<String> filter(String school) throws Exception {
        List<String> classNums = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(baseSql)) {
            statement.setString(1, school);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String classNum = resultSet.getString("class_num");
                    classNums.add(classNum);
                }
            }
        } catch (SQLException e) {
            throw e;
        }

        return classNums;
    }
}
////
