package com.mycompany.mavenproject1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class testdb {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {
            // Memuat driver JDBC untuk SQLite
            Class.forName("org.sqlite.JDBC");

            // Membuat koneksi ke database (akan membuat file database jika belum ada)
            connection = DriverManager.getConnection("jdbc:sqlite:p12.db");

            if (connection != null) {
                System.out.println("Database connected successfully!");

                // Membuat statement
                statement = connection.createStatement();
                
                // Membuat tabel tb1 jika belum ada
                String createTableSQL = "CREATE TABLE IF NOT EXISTS tb1 (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
                statement.executeUpdate(createTableSQL);

                // Menambahkan data contoh ke tabel tb1
                String insertSQL = "INSERT INTO tb1 (name) VALUES ('Heru'), ('Pratama'), ('Sample')";
                statement.executeUpdate(insertSQL);

                // Query untuk mengambil data dari tabel tb1
                String selectSQL = "SELECT * FROM tb1";
                ResultSet rs = statement.executeQuery(selectSQL);

                // Menampilkan data dari tabel tb1
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    System.out.println("ID: " + id + ", Name: " + name);
                }

                // Menutup ResultSet
                rs.close();
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to execute SQL statement.");
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
