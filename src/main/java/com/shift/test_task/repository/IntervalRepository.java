package com.shift.test_task.repository;

import com.shift.test_task.utils.DbUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IntervalRepository extends DbUtils {

    @PostConstruct
    private void CreateTable() {
        try(Connection connection = getConnection()) {
            Statement statement = connection.createStatement();

            String sql = "SELECT count(*)\n" +
                    "FROM information_schema.TABLES\n" +
                    "WHERE TABLE_NAME = 'INTERVALS';";
            ResultSet result = statement.executeQuery(sql);
            result.next();
            int tableCount = result.getInt(1);
            if (tableCount == 0) {
                sql = "CREATE TABLE INTERVALS (\n" +
                        "    IntervalsID INT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "    start_value VARCHAR(50),\n" +
                        "    end_value VARCHAR(50)\n" +
                        ");";
                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void AddIntegerIntervalInDb(Connection connection, List<Integer> interval) throws SQLException {
        Statement statement = connection.createStatement();
        String sql =  "INSERT INTO Intervals (start_value, end_value)\n" +
                "VALUES (" + interval.get(0) + ", " + interval.get(1) +");";
        statement.execute(sql);
    }

    public void AddCharacterIntervalInDb(Connection connection, List<Character> interval) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO Intervals (start_value, end_value)\n" +
                "VALUES (" + "'" + interval.get(0) + "'" + ", " + "'" +interval.get(1) + "'" +");";
        statement.execute(sql);
    }

    public List<Integer> GetIntegerIntervalFromDb(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT *\n" +
                "FROM intervals\n" +
                "WHERE\n" +
                "    START_VALUE NOT REGEXP '^[A-Za-z]'\n" +
                "    AND END_VALUE NOT REGEXP '^[A-Za-z]'" +
                "ORDER BY \n" +
                "    CAST(SUBSTRING(START_VALUE, 1, 1) AS INT) ASC,\n" +
                "    CAST(SUBSTRING(END_VALUE, 1, 1) AS INT) ASC\n" +
                "LIMIT 1;";
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        List<Integer> minInterval = new ArrayList<>();
        minInterval.add(resultSet.getInt(2));
        minInterval.add(resultSet.getInt(3));

        return minInterval;
    }

    public List<Character> GetCharacterIntervalFromDb(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT *\n" +
                "FROM intervals\n" +
                "WHERE\n" +
                "    SUBSTRING(START_VALUE, 1, 1) NOT REGEXP '^[0-9]'\n" +
                "    AND SUBSTRING(END_VALUE, 1, 1) NOT REGEXP '^[0-9]'\n" +
                "ORDER BY \n" +
                "    CAST(SUBSTRING(START_VALUE, 1, 1) AS CHAR) ASC,\n" +
                "    CAST(SUBSTRING(END_VALUE, 1, 1) AS CHAR) ASC\n" +
                "LIMIT 1;";
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        List<Character> minInterval = new ArrayList<>();
        minInterval.add(resultSet.getString(2).charAt(0));
        minInterval.add(resultSet.getString(3).charAt(0));

        return minInterval;
    }
}
