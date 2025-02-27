package by.rublevskaya.mvcapp.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "katusha";
    private static final String PASSWORD = "katusha";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addRecord(Record record) {
        String query = "INSERT INTO records (full_name, birth_date, team, home_city, squad, position) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, record.getFullName());
            statement.setDate(2, Date.valueOf(record.getBirthDate()));
            statement.setString(3, record.getTeam());
            statement.setString(4, record.getHomeCity());
            statement.setString(5, record.getSquad());
            statement.setString(6, record.getPosition());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при добавлении записи: " + e.getMessage());
        }
    }

    public List<Record> getAllRecords() {
        String query = "SELECT * FROM records";
        List<Record> records = new ArrayList<>();
        try (Connection connection = connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                records.add(new Record(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("team"),
                        rs.getString("home_city"),
                        rs.getString("squad"),
                        rs.getString("position")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public List<Record> searchRecords(String fullName, String birthDate, String position, String squad, String team, String homeCity) {
        List<Record> records = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM records WHERE ");
        List<Object> parameters = new ArrayList<>();

        if (fullName != null && !fullName.isEmpty()) {
            query.append("(full_name ILIKE ? OR full_name ILIKE ? OR full_name ILIKE ?) AND ");
            parameters.add(fullName + "%");
            parameters.add("% " + fullName + "%");
            parameters.add("%" + fullName);
        }
        if (birthDate != null && !birthDate.isEmpty()) {
            query.append("birth_date = ? AND ");
            parameters.add(birthDate);
        }
        if (position != null && !position.isEmpty()) {
            query.append("position ILIKE ? AND ");
            parameters.add("%" + position + "%");
        }
        if (squad != null && !squad.isEmpty()) {
            query.append("squad ILIKE ? AND ");
            parameters.add("%" + squad + "%");
        }
        if (team != null && !team.isEmpty()) {
            query.append("team ILIKE ? AND ");
            parameters.add("%" + team + "%");
        }
        if (homeCity != null && !homeCity.isEmpty()) {
            query.append("home_city ILIKE ? AND ");
            parameters.add("%" + homeCity + "%");
        }
        if (query.toString().endsWith(" AND ")) {
            query.setLength(query.length() - 5);
        } else {
            return records;
        }

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                records.add(new Record(
                        resultSet.getInt("id"),
                        resultSet.getString("full_name"),
                        resultSet.getDate("birth_date").toLocalDate(),
                        resultSet.getString("team"),
                        resultSet.getString("home_city"),
                        resultSet.getString("squad"),
                        resultSet.getString("position")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public int deleteRecords(String fullName, String birthDate, String position, String squad, String team, String homeCity) {
        StringBuilder query = new StringBuilder("DELETE FROM records WHERE ");
        List<Object> parameters = new ArrayList<>();

        if (fullName != null && !fullName.isEmpty()) {
            query.append("(full_name ILIKE ? OR full_name ILIKE ? OR full_name ILIKE ?) AND ");
            parameters.add(fullName + "%");
            parameters.add("% " + fullName + "%");
            parameters.add("%" + fullName);
        }
        if (birthDate != null && !birthDate.isEmpty()) {
            query.append("birth_date = ? AND ");
            parameters.add(birthDate);
        }
        if (position != null && !position.isEmpty()) {
            query.append("position ILIKE ? AND ");
            parameters.add("%" + position + "%");
        }
        if (squad != null && !squad.isEmpty()) {
            query.append("squad ILIKE ? AND ");
            parameters.add("%" + squad + "%");
        }
        if (team != null && !team.isEmpty()) {
            query.append("team ILIKE ? AND ");
            parameters.add("%" + team + "%");
        }
        if (homeCity != null && !homeCity.isEmpty()) {
            query.append("home_city ILIKE ? AND ");
            parameters.add("%" + homeCity + "%");
        }
        if (query.toString().endsWith(" AND ")) {
            query.setLength(query.length() - 5);
        } else {
            return 0;
        }
        int affectedRows = 0;
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }
}