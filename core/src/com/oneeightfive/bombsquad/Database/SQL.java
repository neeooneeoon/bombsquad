package com.oneeightfive.bombsquad.Database;

import com.oneeightfive.bombsquad.Screens.HighScore;

import java.sql.*;

public class SQL {
    public Connection connect() {
        String url = "jdbc:sqlite:core/assets/saves/highscore.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void listAll() {
        String sql = "SELECT * FROM " + "highscore" + " ORDER BY score DESC";
        HighScore.highscores.clear();

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                HighScore.highscores.put(rs.getString("player_name"), rs.getInt("score"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addRecord(String name, int score) {
        String sql = "INSERT INTO highscore (player_name,score) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        listAll();

    }
}
